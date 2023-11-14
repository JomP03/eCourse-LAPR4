package boardlogmanagement.application;

import boardlogmanagement.domain.*;
import boardlogmanagement.repository.BoardLogRepository;
import boardmanagement.domain.Board;
import boardmanagement.domain.BoardOperationType;
import ecourseusermanagement.domain.ECourseUser;

public class BoardLogger implements IBoardLogger {

    private final BoardLogFactory boardLogFactory;

    private final BoardLogRepository boardLogRepository;

    /**
     * Instantiates a new Board logger.
     *
     * @param boardLogFactory the board log factory
     */
    public BoardLogger(BoardLogFactory boardLogFactory, BoardLogRepository boardLogRepository) {
        // Verify that the boardLogFactory is not null
        if (boardLogFactory == null) {
            throw new IllegalArgumentException("BoardLogFactory cannot be null");
        }

        // Verify that the boardLogRepository is not null
        if (boardLogRepository == null) {
            throw new IllegalArgumentException("BoardLogRepository cannot be null");
        }

        this.boardLogRepository = boardLogRepository;

        this.boardLogFactory = boardLogFactory;
    }

    @Override
    public void logBoardCreation(Board board, ECourseUser user) {
        boardLogRepository.save(boardLogFactory.createBoardLogAccordingToOperationType(board, BoardOperationType.CREATE_BOARD,
                user, BoardLogComponent.BOARD));
    }

    @Override
    public void logPostItCreation(Board board, ECourseUser user) {
        boardLogRepository.save(boardLogFactory.createBoardLogAccordingToOperationType(board, BoardOperationType.CREATE_POSTIT, user,
                BoardLogComponent.POST_IT));
    }

    @Override
    public void logPostItUpdate(Board board, ECourseUser user, int oldColumn, int oldRow, int newColumn, int newRow, String oldContent, String newContent) {
            boardLogRepository.save(boardLogFactory.createUpdatedPostItLog(board, BoardOperationType.UPDATE_POSTIT, user,
                    BoardLogComponent.POST_IT, oldColumn, oldRow, newColumn, newRow, oldContent, newContent));
    }

    @Override
    public void logPostItDeletion(Board board, ECourseUser user) {
        boardLogRepository.save(boardLogFactory.createBoardLogAccordingToOperationType(board, BoardOperationType.DELETE_POSTIT, user,
                BoardLogComponent.POST_IT));
    }

    @Override
    public void logPostItUndo(Board board, ECourseUser user) {
        boardLogRepository.save(boardLogFactory.createBoardLogAccordingToOperationType(board, BoardOperationType.UNDO_POSTIT, user,
                BoardLogComponent.POST_IT));
    }
}
