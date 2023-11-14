package postitmanagement.application;

public interface IBoardSynchronizer {


    /**
     * Gets the lock for a board.
     *
     * @param lockKey the lock key
     * @return the lock for the board
     */
    Object getBoardLock(String lockKey);


    /**
     * Gets the lock for a board cell.
     * @param lockKey the lock key
     * @return the lock for the board cell
     */
    Object getBoardCellLock(String lockKey);


    /**
     * Creates a lock for a board cell.
     * @param boardId the board id
     * @param row the row
     * @param column the column
     * @return the lock key
     */
    String createBoardCellLock(Long boardId, int row, int column);


}
