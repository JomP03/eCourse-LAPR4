package boardlogmanagement.application;

import boardlogmanagement.domain.BoardLog;

public interface IBoardLogProvider {

    /**
     * Provides the logs of a board.
     * @return the board logs
     */
    Iterable<BoardLog> retrieveBoardLogsByBoardID(long boardId);
}
