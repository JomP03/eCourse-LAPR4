package postitmanagement.application;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BoardSynchronizer implements IBoardSynchronizer {


    private static BoardSynchronizer instance;


    /**
     * Gets the singleton instance of this class.
     *
     * @return the instance
     */
    public static BoardSynchronizer getInstance() {
        if (instance == null) {
            instance = new BoardSynchronizer();
        }
        return instance;
    }


    private BoardSynchronizer() {}


    // The locks for each board
    private final Map<String, Object> boardLock = new HashMap<>();


    // The locks for each cell
    private final ConcurrentHashMap<String, Object> boardCellLock = new ConcurrentHashMap<>();


    @Override
    public Object getBoardLock(String lockKey) {
        return boardLock.computeIfAbsent(lockKey, k -> new Object());
    }


    @Override
    public Object getBoardCellLock(String lockKey) {
        return boardCellLock.computeIfAbsent(lockKey, k -> new Object());
    }


    @Override
    public String createBoardCellLock(Long boardId, int row, int column) {
        return String.format("%d-%d-%d", boardId, row, column);
    }


}