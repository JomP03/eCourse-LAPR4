package boardmanagement.application;

import boardlogmanagement.application.IBoardLogger;
import boardmanagement.domain.*;
import boardmanagement.repository.IBoardRepository;
import eapli.framework.validations.Preconditions;
import ecourseusermanagement.application.UserSessionService;
import ecourseusermanagement.domain.ECourseUser;

import java.util.List;
import java.util.Optional;

public class CreateBoardController {

    private final IBoardRepository boardRepository;

    private final UserSessionService userSessionService;

    private final IBoardLogger boardLogger;

    public CreateBoardController(IBoardRepository boardRepository, UserSessionService userSessionService,
                                 IBoardLogger boardLogger) {
        Preconditions.nonNull(boardRepository);

        this.boardRepository = boardRepository;

        Preconditions.nonNull(userSessionService);

        this.userSessionService = userSessionService;

        Preconditions.nonNull(boardLogger);

        this.boardLogger = boardLogger;

        verifyUser();
    }

    private void verifyUser() {
        Optional<ECourseUser> eCourseUserOptional = userSessionService.getLoggedUser();

        // If the user is not logged in, throw an exception
        if (eCourseUserOptional.isEmpty()) {
            throw new IllegalStateException("The user must be logged in to create a board.");
        }

    }

    /**
     * Gets logged user.
     *
     * @return the logged user
     */
    public ECourseUser getLoggedUser() {
        return userSessionService.getLoggedUser().get();
    }

    /**
     * Validates the length of the board title
     *
     * @param boardTitle - Board's Title
     * @return true if the title is valid, false otherwise
     */
    public boolean validateBoardTitle(String boardTitle) {
        try {
            new BoardTitle(boardTitle);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    /**
     * Validates the length of the board column title
     *
     * @param title - Board Column's Title
     * @return true if the title is valid, false otherwise
     */
    public boolean validateBoardLineTitle(String title) {
        try {
            new BoardLineTitle(title);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    /*
     * Creates a new board and saves it to the database
     *
     * @param boardTitle - Board's Title
     * @param rows - Board's Rows
     * @param columns - Board's Columns
     */
    public Board createBoard(String boardTitle, List<String> rows, List<String> columns, ECourseUser boardOwner) {
        Board
            board = new BoardBuilder(boardRepository)
                    .withBoardOwner(boardOwner)
                    .withBoardTitle(boardTitle)
                    .withRows(rows)
                    .withColumns(columns)
                    .build();



        board = boardRepository.save(board);

        // Log the creation of the board
        boardLogger.logBoardCreation(board, boardOwner);

        return board;
    }
}
