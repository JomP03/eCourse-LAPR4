package boardlogmanagement.application;

import boardlogmanagement.domain.BoardLog;
import boardlogmanagement.domain.BoardLogComponent;
import boardmanagement.domain.*;
import eapli.framework.validations.*;
import ecourseusermanagement.domain.ECourseUser;

public class BoardLogFactory {

    /**
     * Create board log according to operation type board log.
     *
     * @param operationType the operation type
     * @return the board log
     */
    public BoardLog createBoardLogAccordingToOperationType(Board board, BoardOperationType operationType,
                                                           ECourseUser user, BoardLogComponent boardLogComponent) {
        Preconditions.noneNull(board, operationType, user, boardLogComponent);

        return new BoardLog(board, boardLogComponent, operationType, user);
    }

    /*
     * Create updated Post-it log
     */
    public BoardLog createUpdatedPostItLog(Board board, BoardOperationType operationType,
                                           ECourseUser user, BoardLogComponent boardLogComponent, int oldColumn,
                                           int oldRow, int newColumn, int newRow, String oldContent, String newContent) {

        Preconditions.noneNull(board, operationType, user, boardLogComponent);

        return new BoardLog(board, boardLogComponent, operationType, user, oldColumn, oldRow, newColumn, newRow, oldContent, newContent);
    }

}