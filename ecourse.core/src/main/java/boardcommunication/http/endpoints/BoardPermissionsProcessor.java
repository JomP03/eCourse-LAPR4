package boardcommunication.http.endpoints;

import boardcommunication.http.HTTPmessage;
import boardcommunication.http.helpers.RequestUserProvider;
import boardmanagement.application.BoardsProvider;
import postitmanagement.application.IBoardsProvider;
import boardmanagement.domain.Board;
import ecourseusermanagement.domain.ECourseUser;
import persistence.PersistenceContext;

import java.util.Optional;

public class BoardPermissionsProcessor implements EndpointProcessor {

    private final RequestUserProvider requestUserProvider = new RequestUserProvider();
    private final IBoardsProvider boardsProvider = new BoardsProvider(PersistenceContext.repositories().boards());

    private HTTPmessage request;
    private HTTPmessage response;

    private static final String OWNER_REQUEST = "isOwner";
    private static final String WRITE_REQUEST = "hasWriteAccess";
    private static final String READ_REQUEST = "hasReadAccess";

    @Override
    public void processRequest(HTTPmessage request, HTTPmessage response) {
        this.request = request;
        this.response = response;
        String[] uriFields = request.getURI().split("/");
        String typeOfRequest = uriFields[uriFields.length - 1];

        switch (typeOfRequest) {
            case OWNER_REQUEST:
                // Check if the user is the owner of the board
                isOwner(uriFields);
                break;
            case WRITE_REQUEST:
                // Check if the user has write access to the board
                break;
            case READ_REQUEST:
                // Check if the user has read access to the board
                break;
        }
    }

    private void isOwner(String[] uriFields) {
        // Get the board id from the request
        String boardId = uriFields[uriFields.length - 2];
        // Get the user id from the request
        Optional<ECourseUser> user = requestUserProvider.getUserFromRequest(request);

        if (user.isPresent()) {
            // Check if the user is the owner of the board
            Optional<Board> board = boardsProvider.retrieveBoardById(Long.parseLong(boardId));
            if (board.isEmpty()) {
                response.setResponseStatus("404 Not Found");
                response.setContentFromString("Board not found", "text");
                return;
            }

            if (board.get().isBoardOwner(user.get())) {
                response.setResponseStatus("200 OK");
                response.setContentFromString("true", "text");
            } else {
                response.setResponseStatus("403 Forbidden");
                response.setContentFromString("false", "text");
            }
        } else {
            response.setResponseStatus("401 Unauthorized");
        }

    }
}
