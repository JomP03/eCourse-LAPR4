package boardcommunication.http.endpoints;

import boardcommunication.http.HTTPmessage;
import boardcommunication.http.dto.*;
import boardcommunication.http.dto.CreatePostItRequest;
import boardcommunication.http.helpers.LocalDateAdapter;
import boardcommunication.http.helpers.LocalDateTimeAdapter;
import boardcommunication.http.helpers.RequestUserProvider;
import boardlogmanagement.application.BoardLogFactory;
import boardlogmanagement.application.BoardLogger;
import boardmanagement.application.BoardsProvider;
import postitmanagement.application.ChangePostItController;
import postitmanagement.application.CreatePostItController;
import postitmanagement.application.*;
import boardmanagement.domain.*;
import postitmanagement.application.IPostItProvider;
import postitmanagement.domain.PostItManager;
import postitmanagement.application.PostItProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ecourseusermanagement.domain.ECourseUser;
import persistence.PersistenceContext;
import postitmanagement.application.UndoPostItChangeController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


public class PostItsEndpointProcessor implements EndpointProcessor {

    private final CreatePostItController createPostItController = CreatePostItController.getInstance(
            PersistenceContext.repositories().postIts(),
            new BoardLogger(new BoardLogFactory(), PersistenceContext.repositories().boardLogs()),
            BoardSynchronizer.getInstance(),
            new PostItManager(new PostItProvider(PersistenceContext.repositories().postIts())));

    private final UndoPostItChangeController undoPostItChangeController = UndoPostItChangeController.getInstance(
            PersistenceContext.repositories().postIts(),
            new BoardLogger(new BoardLogFactory(), PersistenceContext.repositories().boardLogs()),
            BoardSynchronizer.getInstance(),
            new PostItManager(new PostItProvider(PersistenceContext.repositories().postIts()))
    );

    private final ChangePostItController changePostItController = ChangePostItController.getInstance(
            new BoardLogger(new BoardLogFactory(), PersistenceContext.repositories().boardLogs()),
            new PostItManager(new PostItProvider(PersistenceContext.repositories().postIts())),
            PersistenceContext.repositories().postIts(), BoardSynchronizer.getInstance());


    private final RequestUserProvider requestUserProvider = new RequestUserProvider();

    private final IBoardsProvider boardsProvider = new BoardsProvider(PersistenceContext.repositories().boards());

    private final IPostItProvider postItProvider = new PostItProvider(PersistenceContext.repositories().postIts());

    private HTTPmessage request;
    private HTTPmessage response;

    @Override
    public void processRequest(HTTPmessage request, HTTPmessage response) {
        this.request = request;
        this.response = response;

        redirectAccordingToMethod();
    }

    private void redirectAccordingToMethod() {
        if (request.getMethod().equals("POST")) {
            redirectAccordingToPostEndpoint();
        } else {
            response.setResponseStatus("404 Not Found");
            response.setContentFromString("Endpoint not found", "text");
        }
    }

    private void redirectAccordingToPostEndpoint() {
        Optional<ECourseUser> user = requestUserProvider.getUserFromRequest(request);

        if (user.isPresent()) {
            String[] uriFields = request.getURI().split("/");

            switch (uriFields[2]) {
                case "prm":
                    userPermissions(user.get());
                    break;

                case "find":

                case "upt":
                    findPostIt();
                    break;

                case "crt":
                    createPostIt(user.get());
                    break;

                case "chg":
                    changePostItRequest(user.get());
                    break;

                case "uno":
                    undoPostIt(user.get());
                    break;
            }

        } else {
            response.setResponseStatus("401 Unauthorized");
            response.setContentFromString("Unauthorized", "text");
        }
    }

    private void userPermissions(ECourseUser eCourseUser) {
        // Get the board
        Optional<Board> board = boardsProvider.retrieveBoardById(Long.parseLong(request.getContentAsString()));

        if (board.isPresent()) {

            // Get the board
            Board obtainedBoard = board.get();

            // Convert the post-it to JSON
            Gson toGson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .setPrettyPrinting()
                    .create();

            String postItJson = toGson.toJson(boardsProvider.userCanWrite(eCourseUser, obtainedBoard));

            // If the post-it exists, return it
            response.setResponseStatus("200 OK");
            response.setContentFromString(postItJson, "application/json");

        } else {
            // If the board doesn't exist, return an error
            response.setResponseStatus("404 Not Found");
            response.setContentFromString("Board not found", "text");
        }

    }

    private void changePostItRequest(ECourseUser user) {
        // Get the request content
        String requestContent = request.getContentAsString();

        // Transform the json into a java object
        Gson gson = new Gson();
        ChangePostItRequest changeRequest = gson.fromJson(requestContent, ChangePostItRequest.class);

        // Get the board
        Optional<Board> board = boardsProvider.retrieveBoardById(changeRequest.boardId);

        // If the board exists
        if (board.isPresent()) {
            String errorMessage = "";
            boolean receivedException = false;
            try {

                // Change the post-it - synchronized to avoid concurrency problems
                changePostItController.changePostIt(user, board.get(),
                        changeRequest.oldRow, changeRequest.oldColumn,
                        changeRequest.newRow, changeRequest.newColumn,
                        changeRequest.oldContent, changeRequest.newContent, changeRequest.color);

            } catch (IllegalArgumentException e) {
                receivedException = true;

                // Attribute the error message
                if (e.getMessage().equals("The content cannot be empty."))
                    errorMessage = e.getMessage();
                else
                    errorMessage = "It was impossible to change the post-it.";

                response.setResponseStatus("400 Bad Request");
                response.setContentFromString(errorMessage, "text/plain");

            } catch (IllegalStateException e) {
                receivedException = true;

                // Attribute the error message
                if (e.getMessage().equals("There is no post-it in the cell.") ||
                        e.getMessage().equals("There is already a post-it in the given cell."))
                    errorMessage = e.getMessage();
                else
                    errorMessage = "It was impossible to change the post-it.";

                response.setResponseStatus("400 Bad Request");
                response.setContentFromString(errorMessage, "text/plain");

            } catch (Exception e) {
                receivedException = true;
                errorMessage = "Oops! Please try again!";
            }

            // Check if we received an exception
            if (!receivedException) {
                response.setResponseStatus("200 OK");
                response.setContentFromString("Success", "text/plain");
            } else {
                response.setResponseStatus("400 Bad Request");
                response.setContentFromString(errorMessage, "text/plain");
            }
        } else {
            // If the board doesn't exist
            response.setResponseStatus("404 Not Found");
            response.setContentFromString("Board not found", "text/plain");
        }
    }

    private void findPostIt() {
        // Get the board
        Optional<Board> board = boardsProvider.retrieveBoardById(Long.parseLong(request.getContentAsString()));

        // If the board exists
        if (board.isPresent()) {

            // Get the board
            Board obtainedBoard = board.get();

            // All the cells in the board
            List<BoardCell> cells = obtainedBoard.cells();

            // Convert the post-it to JSON
            Gson toGson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .setPrettyPrinting()
                    .create();

            String postItJson = toGson.toJson(postItProvider.boardPostIts(cells));

            // If the post-it exists, return it
            response.setResponseStatus("200 OK");
            response.setContentFromString(postItJson, "application/json");

        } else {
            // If the board doesn't exist, return an error
            response.setResponseStatus("404 Not Found");
            response.setContentFromString("Board not found", "text");
        }

    }

    private void createPostIt(ECourseUser eCourseUser) {
        // Get the request content
        String requestContent = request.getContentAsString();

        // Transform the json into a java object
        Gson gson = new Gson();
        CreatePostItRequest createPostItRequest = gson.fromJson(requestContent, CreatePostItRequest.class);

        // Get the board
        Optional<Board> board = boardsProvider.retrieveBoardById(Long.parseLong(createPostItRequest.boardId));

        // If the board exists
        if (board.isPresent()) {

            // Verify if an exception was thrown
            boolean exceptionThrown = false;

            // Error message in exception case
            String errorMessage = "";

            // Create the post-it
            try {

                createPostItController.createPostIt(board.get(), createPostItRequest.content,
                        createPostItRequest.color, eCourseUser, createPostItRequest.row, createPostItRequest.column);

            } catch (Exception e) {
                exceptionThrown = true;
                errorMessage = e.getMessage();
            }

            // Check if exception was thrown
            if (exceptionThrown) {
                response.setResponseStatus("400 Bad Request");
                response.setContentFromString(errorMessage, "text/plain");

            } else {
                response.setResponseStatus("200 OK");
                response.setContentFromString("Success", "text/plain");
            }

        } else {
            // If the post-it doesn't exist, return an empty post-it
            response.setResponseStatus("404 Not Found");
            response.setContentFromString("Board not found", "text");
        }
    }


    private void undoPostIt(ECourseUser eCourseUser) {
        // Get the request content
        String requestContent = request.getContentAsString();

        // Transform the json into a java object
        Gson gson = new Gson();
        UndoPostItChangeRequest undoPostItRequest = gson.fromJson(requestContent, UndoPostItChangeRequest.class);

        // Get the board
        Optional<Board> board = boardsProvider.retrieveBoardById(Long.parseLong(undoPostItRequest.boardId));

        // If the board exists
        if (board.isPresent()) {

            // Verify if an exception was thrown
            boolean exceptionThrown = false;

            // Error message in exception case
            String errorMessage = "";

            // Create the post-it
            try {

                undoPostItChangeController.undoPostItChange(board.get(), eCourseUser,
                        undoPostItRequest.row, undoPostItRequest.column);

            } catch (IllegalStateException e) {
                exceptionThrown = true;
                errorMessage = e.getMessage();
            } catch (Exception e) {
                exceptionThrown = true;
                errorMessage = "Oops! Please try again! ";
            }

            // Check if exception was thrown
            if (exceptionThrown) {
                response.setResponseStatus("400 Bad Request");
                response.setContentFromString(errorMessage, "text/plain");

            } else {
                response.setResponseStatus("200 OK");
                response.setContentFromString("Success", "text/plain");
            }

        } else {
            // If the post-it doesn't exist, return an empty post-it
            response.setResponseStatus("404 Not Found");
            response.setContentFromString("Board not found", "text");
        }
    }
}