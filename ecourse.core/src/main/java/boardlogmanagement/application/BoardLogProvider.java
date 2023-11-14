package boardlogmanagement.application;

import boardlogmanagement.domain.BoardLog;
import boardlogmanagement.repository.BoardLogRepository;

public class BoardLogProvider implements IBoardLogProvider {


    BoardLogRepository boardLogRepository;


    public BoardLogProvider(BoardLogRepository boardLogRepository) {

        if (boardLogRepository == null) {
            throw new IllegalArgumentException("The boardLogRepository cannot be null.");
        }

        this.boardLogRepository = boardLogRepository;
    }


    @Override
    public Iterable<BoardLog> retrieveBoardLogsByBoardID(long boardId) {
        return boardLogRepository.findByBoardId(boardId);
    }
}
