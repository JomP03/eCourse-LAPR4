package boardmanagement.application;

import boardmanagement.domain.Board;
import boardmanagement.repository.IBoardRepository;
import ecourseusermanagement.domain.ECourseUser;

public class ArchiveBoardController {
    private final IBoardRepository boardRepository;

    public ArchiveBoardController(IBoardRepository boardRepository) {
        if(boardRepository == null)
            throw new IllegalArgumentException("boardRepository can't be null");
        this.boardRepository = boardRepository;
    }

    /**
     * Method to archive a board
     * @param board the board to archive
     * @param user the user that wants to archive the board
     */
    public void archiveBoard(Board board, ECourseUser user){
        if(board == null)
            throw new IllegalArgumentException("Board can't be null");
        board.archiveBoard(user);
        boardRepository.save(board);
    }
}
