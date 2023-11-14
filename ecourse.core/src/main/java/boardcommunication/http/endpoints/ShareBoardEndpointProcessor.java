package boardcommunication.http.endpoints;

import boardcommunication.http.HTTPmessage;
import boardcommunication.http.dto.ShareBoardRequest;
import boardcommunication.http.helpers.LocalDateAdapter;
import boardcommunication.http.helpers.LocalDateTimeAdapter;
import boardcommunication.http.helpers.RequestUserProvider;
import boardmanagement.application.BoardPermissionHandler;
import boardmanagement.application.BoardsProvider;
import boardmanagement.application.ShareBoardController;
import boardmanagement.domain.Board;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ecourseusermanagement.domain.ECourseUser;
import persistence.PersistenceContext;
import ecourseusermanagement.application.UsersProvider;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ShareBoardEndpointProcessor implements EndpointProcessor {

    private final RequestUserProvider requestUserProvider = new RequestUserProvider();
    private final ShareBoardController shareBoardController =
            new ShareBoardController(PersistenceContext.repositories().eCourseUsers(),
                    new BoardPermissionHandler(new UsersProvider(PersistenceContext.repositories().eCourseUsers())),
                    PersistenceContext.repositories().boards());
    private final BoardsProvider boardsProvider = new BoardsProvider(PersistenceContext.repositories().boards());
    private HTTPmessage request;
    private HTTPmessage response;
    private static final int SPECIFIC_BOARD_URI_LENGTH = 3;

    public void processRequest(HTTPmessage request, HTTPmessage response) {
        this.request = request;
        this.response = response;

        redirectAccordingToMethod();
    }

    private void redirectAccordingToMethod() {
        switch (request.getMethod()) {
            case "GET":
                getAvailableUsersToShareBoardWith();
                break;
            case "POST":
                shareBoard();
                break;
            default:
                response.setResponseStatus("404 Not Found");
                response.setContentFromString("Endpoint not found", "text");
                break;
        }
    }

    private void getAvailableUsersToShareBoardWith() {
        Optional<ECourseUser> user = requestUserProvider.getUserFromRequest(request);
        if (user.isPresent()) {
            String[] uriFields = request.getURI().split("/");
            // Get all users that are available to share a board with
            if (uriFields.length == SPECIFIC_BOARD_URI_LENGTH) {
                Optional<Board> board = boardsProvider.retrieveBoardById(Long.parseLong(uriFields[2]));
                if (board.isPresent()) {
                    if (board.get().isBoardOwner(user.get())) {
                        List<ECourseUser> availableUsers = shareBoardController.availableUsersToShareBoardWith(board.get(), user.get());
                        Gson gson = new GsonBuilder()
                                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                                .setPrettyPrinting()
                                .create();
                        String availableUsersJson = gson.toJson(availableUsers);
                        response.setResponseStatus("200 OK");
                        response.setContentFromString(availableUsersJson, "application/json");
                    } else {
                        response.setResponseStatus("403 Forbidden");
                        response.setContentFromString("The user is not the owner of this board", "text");
                    }
                } else {
                    response.setResponseStatus("404 Not Found");
                    response.setContentFromString("Board not found", "text");
                }
            } else {
                response.setResponseStatus("404 Not Found");
                response.setContentFromString("Endpoint not found", "text");
            }
        } else {
            response.setResponseStatus("401 Unauthorized");
            response.setContentFromString("Unauthorized", "text");
        }
    }

    private void shareBoard() {
        // Get the request content
        String requestContent = request.getContentAsString();

        // Transform the json into a java object
        Gson gson = new Gson();
        ShareBoardRequest shareBoardRequest = gson.fromJson(requestContent, ShareBoardRequest.class);

        Optional<ECourseUser> sessionUser = requestUserProvider.getUserFromRequest(request);
        if (sessionUser.isPresent()) {
            Optional<Board> board = boardsProvider.retrieveBoardById(Long.parseLong(shareBoardRequest.boardId));
            if (board.isPresent()) {
                for (ShareBoardRequest.User user : shareBoardRequest.users) {
                    shareBoardController.shareBoard(board.get(), Long.parseLong(user.userId), user.read, user.write);
                }
                response.setResponseStatus("200 OK");
                response.setContentFromString("Board shared", "text");
            } else {
                response.setResponseStatus("404 Not Found");
                response.setContentFromString("Board not found", "text");
            }
        } else {
            response.setResponseStatus("401 Unauthorized");
            response.setContentFromString("Unauthorized", "text");
        }
    }
}
