package boardmanagement.repository;

import boardmanagement.domain.Board;
import eapli.framework.domain.repositories.DomainRepository;
import ecourseusermanagement.domain.ECourseUser;

import java.util.Optional;

public interface IBoardRepository extends DomainRepository<Long, Board> {
    /**
     * Finds a board by its title
     *
     * @param title the title
     * @return the board
     */
    Board findByTitle(String title);

    /**
     * Finds all boards that a user has access to
     *
     * @param email the email
     * @return the iterable of boards
     */
    Iterable<Board> findAllUserBoards(String email);


    /**
     * Checks if a user can write on a board.
     * @param user the user
     * @param board the board
     * @return true if the user can write on the board, false otherwise
     */
    boolean userCanWrite(ECourseUser user, Board board);
}
