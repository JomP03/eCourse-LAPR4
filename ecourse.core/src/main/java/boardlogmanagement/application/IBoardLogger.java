package boardlogmanagement.application;

import boardmanagement.domain.Board;
import boardlogmanagement.domain.BoardLog;
import ecourseusermanagement.domain.ECourseUser;

public interface IBoardLogger {

    /**
     * Creates a BoardLog for the creation of a post-it.
     * @param board the board
     * @param user the user
     */
    void logBoardCreation(Board board, ECourseUser user);

    /**
     * Creates a BoardLog for the creation of a post-it.
     *
     * @param board the board
     */
    void logPostItCreation(Board board, ECourseUser user);

    /**
     * Creates a BoardLog for the update of a post-it.
     *
     * @param board the board
     */
    void logPostItUpdate(Board board, ECourseUser user, int oldColumn, int oldRow, int newColumn, int newRow, String oldContent, String newContent);


    /**
     * Creates a BoardLog for the deletion of a post-it.
     *
     * @param board the board
     */
    void logPostItDeletion(Board board, ECourseUser user);

    /**
     * Creates a BoardLog for the undo of a post-it.
     *
     * @param board the board
     */
    void logPostItUndo(Board board, ECourseUser user);

}
