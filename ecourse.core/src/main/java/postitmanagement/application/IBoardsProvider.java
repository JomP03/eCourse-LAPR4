package postitmanagement.application;

import boardmanagement.domain.Board;
import ecourseusermanagement.domain.ECourseUser;

import java.util.List;
import java.util.Optional;

public interface IBoardsProvider {

    /**
     * Retrieves all the boards a user has access to.
     *
     * @return the list of boards
     */
    List<Board> retrieveUserBoards(String email);

    /**
     * Retrieve board by id.
     *
     * @param id the board id
     * @return the board
     */
    Optional<Board> retrieveBoardById(long id);


    /**
     * Checks if a user can write on a board.
     * @param user the user
     * @param board the board
     * @return true if the user can write on the board, false otherwise
     */
    boolean userCanWrite(ECourseUser user, Board board);
}
