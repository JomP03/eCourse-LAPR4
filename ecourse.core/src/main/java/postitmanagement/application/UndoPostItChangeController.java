package postitmanagement.application;

import boardlogmanagement.application.IBoardLogger;
import boardmanagement.domain.Board;
import boardmanagement.domain.BoardLineNumber;
import ecourseusermanagement.domain.ECourseUser;
import postitmanagement.domain.IPostItManager;
import postitmanagement.domain.PostIt;
import postitmanagement.domain.PostItManager;
import postitmanagement.repository.PostItRepository;

public class UndoPostItChangeController {

    private final PostItRepository postItRepository;


    private IPostItManager postItManager;


    private final IBoardLogger boardLogger;


    private final IBoardSynchronizer boardSynchronizer;


    private static UndoPostItChangeController instance;


    /**
     * Gets the singleton instance of this class.
     *
     * @param postItRepository  the post-it repository
     * @param boardLogger       the board logger
     * @param boardSynchronizer the board synchronizer
     * @return the instance
     */
    public static UndoPostItChangeController getInstance(PostItRepository postItRepository, IBoardLogger boardLogger,
                                                         IBoardSynchronizer boardSynchronizer,
                                                         IPostItManager postItManager) {
        if (instance == null)
            return instance = new UndoPostItChangeController(postItRepository, boardLogger,
                    boardSynchronizer, postItManager);

        return instance;
    }


    /**
     * Instantiates a new CreatePostItController.
     *
     * @param postItRepository  the post-it repository
     * @param boardLogger       the board logger
     * @param boardSynchronizer the board synchronizer
     */
    private UndoPostItChangeController(PostItRepository postItRepository, IBoardLogger boardLogger,
                                       IBoardSynchronizer boardSynchronizer, IPostItManager postItManager) {
        if (postItRepository == null) {
            throw new IllegalArgumentException("The board repository cannot be null");
        }

        this.postItRepository = postItRepository;

        if (boardLogger == null) {
            throw new IllegalArgumentException("The board logger cannot be null");
        }

        this.boardLogger = boardLogger;

        if (boardSynchronizer == null) {
            throw new IllegalArgumentException("The board synchronizer cannot be null");
        }

        this.boardSynchronizer = boardSynchronizer;

        if (postItManager == null) {
            throw new IllegalArgumentException("The post-it manager cannot be null");
        }

        this.postItManager = postItManager;
    }


    /**
     * This method is responsible for requesting the undoing of the last change to a post-it.
     * It is synchronized using the IBoardSynchronizer interface.
     *
     * @param board     the board the post-it belongs to
     * @param boardUser the user trying to undo the post-it changes
     * @param row       the row where the post-it is placed
     * @param column    the column where the post-it is placed
     */
    public void undoPostItChange(Board board, ECourseUser boardUser, Integer row, Integer column) {
        // Request a lock key for this cell
        String lockKey = boardSynchronizer.createBoardCellLock(board.identity(), row, column);

        // Acquire the lock using the generated lock key
        synchronized (boardSynchronizer.getBoardCellLock(lockKey)) {
            // Update the post-it manager
            updatePostItManager();

            // Verify if the user has write access permission
            if (!board.hasWritePermissions(boardUser) && !board.isBoardOwner(boardUser)) {
                throw new IllegalArgumentException("You do not have write access permission!");
            }

            synchronized (this) {
                // Undo the last change to the post-it
                PostIt postIt = postItManager.undoPostItChange(board, boardUser, BoardLineNumber.valueOf(row),
                        BoardLineNumber.valueOf(column));
                
                // Save the post-it
                postItRepository.save(postIt);
            }

        }

        synchronized (this) {
            boardLogger.logPostItUndo(board, boardUser);
        }
    }

    private void updatePostItManager() {
        postItManager = new PostItManager(new PostItProvider(postItRepository));
    }

}
