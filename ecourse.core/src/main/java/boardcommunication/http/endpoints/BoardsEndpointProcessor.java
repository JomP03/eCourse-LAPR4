package boardcommunication.http.endpoints;

import boardcommunication.http.dto.BoardCreationRequest;
import boardcommunication.http.helpers.LocalDateAdapter;
import boardcommunication.http.helpers.LocalDateTimeAdapter;
import boardcommunication.http.HTTPmessage;
import boardcommunication.http.helpers.RequestUserProvider;
import boardlogmanagement.application.BoardLogFactory;
import boardlogmanagement.application.BoardLogger;
import boardmanagement.application.BoardsProvider;
import boardmanagement.application.CreateBoardController;
import postitmanagement.application.IBoardsProvider;
import boardmanagement.domain.Board;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ecourseusermanagement.application.UserSessionService;
import ecourseusermanagement.domain.ECourseUser;
import persistence.PersistenceContext;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class BoardsEndpointProcessor implements EndpointProcessor {

    private static final int SPECIFIC_BOARD_URI_LENGTH = 3;
    private final IBoardsProvider boardsProvider = new BoardsProvider(PersistenceContext.repositories().boards());
    private final RequestUserProvider requestUserProvider = new RequestUserProvider();
    private HTTPmessage request;
    private HTTPmessage response;

    @Override
    public void processRequest(HTTPmessage request, HTTPmessage response) {
        this.request = request;
        this.response = response;

        redirectAccordingToMethod();
    }

    private void redirectAccordingToMethod() {
        switch (request.getMethod()) {
            case "GET":
                redirectAccordingToGetEndpoint();
                break;
            case "POST":
                createBoardRequest();
                break;
            default:
                response.setResponseStatus("404 Not Found");
                response.setContentFromString("Endpoint not found", "text");
                break;
        }
    }

    private final CreateBoardController createBoardController =
            new CreateBoardController(PersistenceContext.repositories().boards(),
                    new UserSessionService(PersistenceContext.repositories().eCourseUsers()),
                    new BoardLogger(new BoardLogFactory(), PersistenceContext.repositories().boardLogs()));

    private void createBoardRequest() {
        Optional<ECourseUser> user = requestUserProvider.getUserFromRequest(request);
        if (user.isPresent()) {
            // Get the request content
            String requestContent = request.getContentAsString();

            // Transform the json into a java object
            Gson gson = new Gson();
            BoardCreationRequest boardCreationRequest = gson.fromJson(requestContent, BoardCreationRequest.class);

            // Create the board
            try {
                Board board = createBoardController.createBoard(boardCreationRequest.boardTitle,
                        boardCreationRequest.boardRows,
                        boardCreationRequest.boardColumns,
                        user.get());

                // Set the response
                response.setResponseStatus("200 Ok");
                response.setContentFromString(board.identity().toString(), "text");
            } catch (IllegalArgumentException e) {
                response.setResponseStatus("400 Bad Request");
                response.setContentFromString(e.getMessage(), "text");
            } catch (Exception e) {
                response.setResponseStatus("500 Internal Server Error");
                response.setContentFromString("Something went wrong", "text");
            }

        } else {
            response.setResponseStatus("401 Unauthorized");
            response.setContentFromString("No user logged in", "text");
        }
    }

    private void redirectAccordingToGetEndpoint() {
        Optional<ECourseUser> user = requestUserProvider.getUserFromRequest(request);
        if (user.isPresent()) {
            String[] uriFields = request.getURI().split("/");
            // Check if the request is for a specific board
            if (uriFields.length == SPECIFIC_BOARD_URI_LENGTH) {
                getSpecificBoard(uriFields[2]);
            } else {
                // Get all the user's boards
                getAllUserBoards(user.get());
            }
        } else {
            response.setResponseStatus("401 Unauthorized");
            response.setContentFromString("No user logged in", "text");
        }
    }

    private void getAllUserBoards(ECourseUser user) {
        // Get all the user's boards
        List<Board> boards = boardsProvider.retrieveUserBoards(user.email());
        // Convert the boards to JSON
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting()
                .create();
        String boardsJson = gson.toJson(boards);

        // Set the response
        response.setResponseStatus("200 Ok");
        response.setContentFromString(boardsJson, "application/json");
    }

    private void getSpecificBoard(String boardId) {
        // Get the board with the given id
        Optional<Board> board = boardsProvider.retrieveBoardById(Long.parseLong(boardId));
        // Check if the board exists
        if (board.isPresent()) {
            // Convert the board to JSON
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .setPrettyPrinting()
                    .create();
            String boardJson = gson.toJson(board.get());
            // Set the response
            response.setResponseStatus("200 Ok");
            response.setContentFromString(boardJson, "application/json");
        } else {
            response.setResponseStatus("404 Not Found");
            response.setContentFromString("Board not found", "text");
        }
    }
}
