package postitmanagement.application;

import boardlogmanagement.application.IBoardLogger;
import boardmanagement.domain.Board;
import boardmanagement.domain.BoardLineNumber;
import ecourseusermanagement.domain.ECourseUser;
import postitmanagement.domain.IPostItManager;
import postitmanagement.domain.PostIt;
import postitmanagement.domain.PostItManager;
import postitmanagement.repository.PostItRepository;

public class CreatePostItController {


    private final PostItRepository postItRepository;


    private IPostItManager postItManager;


    private final IBoardLogger boardLogger;


    private final IBoardSynchronizer boardSynchronizer;


    private static CreatePostItController instance;


    /**
     * Gets the singleton instance of this class.
     *
     * @param postItRepository   the post-it repository
     * @param boardLogger        the board logger
     * @param boardSynchronizer  the board synchronizer
     * @return the instance
     */
    public static CreatePostItController getInstance(PostItRepository postItRepository, IBoardLogger boardLogger,
                                                     IBoardSynchronizer boardSynchronizer,
                                                     IPostItManager postItManager) {
        if (instance == null)
            return instance = new CreatePostItController(postItRepository, boardLogger,
                    boardSynchronizer, postItManager);

        return instance;
    }


    /**
     * Instantiates a new CreatePostItController.
     *
     * @param postItRepository   the post-it repository
     * @param boardLogger        the board logger
     * @param boardSynchronizer  the board synchronizer
     */
    private CreatePostItController(PostItRepository postItRepository, IBoardLogger boardLogger,
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
     * This method is responsible for requesting the creation of a post-it.
     * To do so, it must first talk to the board to create the post-it.
     * Then, it must talk to the logger to log the creation of the post-it.
     * Finally, it must talk to the repository to save the board.
     *
     * @param board   the board
     * @param content the content
     * @param color   the color
     * @param row     the row
     * @param column  the column
     */
    public void createPostIt(Board board, String content, String color, ECourseUser postItCreator,
                             Integer row, Integer column) {
        // Request a lock key for this cell
        String lockKey = boardSynchronizer.createBoardCellLock(board.identity(), row, column);

        // Acquire the lock using the generated lock key
        synchronized (boardSynchronizer.getBoardCellLock(lockKey)) {
            // Update the post-it manager
            updatePostItManager();

            // Verify if the user has write access permission
            if (!board.hasWritePermissions(postItCreator) && !board.isBoardOwner(postItCreator)) {
                throw new IllegalArgumentException("You do not have write access permission!");
            }

            // Create the post-it
            PostIt postIt = postItManager.createPostIt(board, BoardLineNumber.valueOf(row),
                    BoardLineNumber.valueOf(column), postItCreator, content, color);

            synchronized (this) {
                // Save the post-it
                postItRepository.save(postIt);
            }

        }

        synchronized (this) {
            boardLogger.logPostItCreation(board, postItCreator);
        }
    }

    private void updatePostItManager() {
        postItManager = new PostItManager(new PostItProvider(postItRepository));
    }
}