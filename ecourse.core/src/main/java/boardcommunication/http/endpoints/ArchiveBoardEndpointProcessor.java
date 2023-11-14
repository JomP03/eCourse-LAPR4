package boardcommunication.http.endpoints;

import boardcommunication.http.HTTPmessage;
import boardcommunication.http.helpers.RequestUserProvider;
import boardmanagement.application.ArchiveBoardController;
import boardmanagement.application.BoardsProvider;
import postitmanagement.application.IBoardsProvider;
import boardmanagement.domain.Board;
import ecourseusermanagement.domain.ECourseUser;
import persistence.PersistenceContext;

import java.util.Optional;

public class ArchiveBoardEndpointProcessor implements EndpointProcessor {

    private final ArchiveBoardController archiveBoardController = new ArchiveBoardController(PersistenceContext.repositories().boards());
    private final IBoardsProvider boardsProvider = new BoardsProvider(PersistenceContext.repositories().boards());
    private final RequestUserProvider requestUserProvider = new RequestUserProvider();
    private HTTPmessage request;
    private HTTPmessage response;

    @Override
    public void processRequest(HTTPmessage request, HTTPmessage response) {
        this.request = request;
        this.response = response;

        archiveBoard();
    }

    // Method that processes the request to archive a board
    private void archiveBoard() {
        if(request.getMethod().equals("POST")){
            Optional<ECourseUser> user = requestUserProvider.getUserFromRequest(request);
            if (user.isPresent()) {
                // Retrieve the board to archive from the id passed in the request
                Optional<Board> board = boardsProvider.retrieveBoardById(Long.parseLong(request.getContentAsString()));
                if (board.isPresent()) {
                    if (board.get().isBoardOwner(user.get())) {
                        try{
                            archiveBoardController.archiveBoard(board.get(), user.get());
                        } catch (Exception e){
                            respectiveResponse("Something went wrong", "500 Internal Server Error");
                        }
                        respectiveResponse("Board archived", "200 OK");
                    } else {
                        respectiveResponse("The user is not the owner of this board", "403 Forbidden");
                    }
                } else {
                    respectiveResponse("Board not found", "404 Not Found");
                }
            } else {
                respectiveResponse("No user logged in", "401 Unauthorized");
            }
        } else {
            response.setResponseStatus("404 Not Found");
            response.setContentFromString("Endpoint not found", "text");
        }
    }

    // Generic method to set the response status and content
    private void respectiveResponse(String message, String status){
        response.setResponseStatus(status);
        response.setContentFromString(message, "text");
    }
}
