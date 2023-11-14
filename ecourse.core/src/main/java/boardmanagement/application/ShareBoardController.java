package boardmanagement.application;

import boardmanagement.domain.Board;
import boardmanagement.repository.IBoardRepository;
import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.repositories.IeCourseUserRepository;

import java.util.List;

public class ShareBoardController {
    private final IeCourseUserRepository userRepository;
    private final BoardPermissionHandler boardPermissionHandler;
    private final IBoardRepository boardRepository;

    public ShareBoardController(IeCourseUserRepository userRepository, BoardPermissionHandler boardPermissionHandler, IBoardRepository boardRepository) {
        if (userRepository == null)
            throw new IllegalArgumentException("User Repository cannot be null.");

        this.userRepository = userRepository;

        if (boardPermissionHandler == null)
            throw new IllegalArgumentException("Board Permission Handler cannot be null.");

        this.boardPermissionHandler = boardPermissionHandler;

        if (boardRepository == null)
            throw new IllegalArgumentException("Board Repository cannot be null.");

        this.boardRepository = boardRepository;
    }

    /**
     * Returns all users that are available to share a board with.
     *
     * @param board - the board
     * @return the list of users that are available to share a board with.
     */
    public List<ECourseUser> availableUsersToShareBoardWith(Board board, ECourseUser user) {
        if (!board.isBoardOwner(user)) {
            throw new IllegalArgumentException("User is not the owner of the board.");
        }
        return (List<ECourseUser>) userRepository.findAvailableUsersToShareBoardWith(board);
    }

    /**
     * Shares a board with a user.
     *
     * @param board - the board
     * @param userId  - the user
     * @param read  - whether the user can read the board
     * @param write - whether the user can write to the board
     */
    public void shareBoard(Board board, Long userId, boolean read, boolean write) {
        boardPermissionHandler.handleBoardSharing(board, userId, read, write);
        boardRepository.save(board);
    }
}
