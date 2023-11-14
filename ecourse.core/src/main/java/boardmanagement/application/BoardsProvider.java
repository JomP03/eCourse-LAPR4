package boardmanagement.application;

import boardmanagement.domain.Board;
import boardmanagement.repository.IBoardRepository;
import ecourseusermanagement.domain.ECourseUser;
import postitmanagement.application.IBoardsProvider;

import java.util.List;
import java.util.Optional;

public class BoardsProvider implements IBoardsProvider {
    private final IBoardRepository boardRepository;

    /**
     * Instantiates a new Boards provider.
     *
     * @param boardRepository the board repository (DI)
     */
    public BoardsProvider(IBoardRepository boardRepository) {
        if (boardRepository == null) {
            throw new IllegalArgumentException("The boardRepository cannot be null.");
        }
        this.boardRepository = boardRepository;
    }

    @Override
    public List<Board> retrieveUserBoards(String email) {
        return (List<Board>) boardRepository.findAllUserBoards(email);
    }

    @Override
    public Optional<Board> retrieveBoardById(long id) {
        return boardRepository.ofIdentity(id);
    }

    @Override
    public boolean userCanWrite(ECourseUser user, Board board) {
        return boardRepository.userCanWrite(user, board);
    }
}
