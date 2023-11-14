package postitmanagement.application;

import boardlogmanagement.application.IBoardLogger;
import boardmanagement.domain.*;
import eapli.framework.validations.Preconditions;
import ecourseusermanagement.domain.*;
import postitmanagement.domain.*;
import postitmanagement.repository.*;

public class ChangePostItController {

    private final IBoardLogger logger;

    private IPostItManager postItManager;

    private final PostItRepository postItRepo;

    private final IBoardSynchronizer synchronizer;

    private static ChangePostItController instance;


    /**
     * Singleton
     * @param logger Board logger
     * @param postItManager Post-it manager
     * @param postItRepo Post-it repository
     * @param synchronizer Board synchronizer
     * @return Singleton instance
     */
    public static ChangePostItController getInstance(IBoardLogger logger, IPostItManager postItManager,
                                                     PostItRepository postItRepo, IBoardSynchronizer synchronizer) {
        if (instance == null) {
            instance = new ChangePostItController(logger, postItManager, postItRepo, synchronizer);
        }
        return instance;
    }


    private ChangePostItController(IBoardLogger logger, IPostItManager postItManager,
                                   PostItRepository postItRepo, IBoardSynchronizer synchronizer) {
        Preconditions.noneNull(logger, postItRepo, synchronizer);

        this.logger = logger;
        this.postItManager = postItManager;
        this.postItRepo = postItRepo;
        this.synchronizer = synchronizer;
    }


    public void updatePostItProvider() {
        postItManager = new PostItManager(new PostItProvider(postItRepo));
    }


    /**
     * Update post-it content.
     *
     * @param board      the board where the post-it is
     * @param oldRow     the row of where the post-it was
     * @param oldColumn  the column of where the post-it was
     * @param newRow     the row of where the post-it is
     * @param newColumn  the column of where the post-it is
     * @param oldContent the previous content of the post-it
     * @param newContent the current content of the post-it
     */
    public void changePostIt(ECourseUser user, Board board,
                             int oldRow, int oldColumn, int newRow, int newColumn,
                             String oldContent, String newContent, String color) {
        // Generate a unique lock key based on the row, column, and board ID
        String lockKeyOldCell = synchronizer.createBoardCellLock(board.identity(), oldRow, oldColumn);

        synchronized (synchronizer.getBoardCellLock(lockKeyOldCell)) {
            updatePostItProvider();
            PostIt postIt;
            synchronized (this) {
                postIt = postItManager.changePostItContent(user, board, oldRow, oldColumn,
                        oldContent, newContent, color);
            }

            // If the post-it was moved, then we need to lock the new cell
            if (checkIfPostItWasMoved(oldRow, oldColumn, newRow, newColumn)) {
                String lockKeyNewCell = synchronizer.createBoardCellLock(board.identity(), newRow, newColumn);

                synchronized (synchronizer.getBoardCellLock(lockKeyNewCell)) {
                    postIt = postItManager.movePostIt(user, board, postIt, newRow, newColumn);

                }
            }

            synchronized (this) {
                postItRepo.save(postIt);
            }
        }

        synchronized (this) {
            logger.logPostItUpdate(board, user, oldColumn, oldRow, newColumn, newRow, oldContent, newContent);
        }
    }

    private boolean checkIfPostItWasMoved(int oldRow, int oldColumn, int newRow, int newColumn) {
        return oldRow != newRow || oldColumn != newColumn;
    }
}
