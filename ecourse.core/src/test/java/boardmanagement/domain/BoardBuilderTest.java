package boardmanagement.domain;

import boardmanagement.repository.IBoardRepository;
import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.domain.UserDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BoardBuilderTest {
    IBoardRepository boardRepo;

    @BeforeEach
    void setUp() {
        boardRepo = mock(IBoardRepository.class);
    }

    @Test
    public void ensureBoardBuilderReturnsCorrectBoard(){
        // Arrange
        BoardBuilder boardBuilder = new BoardBuilder(boardRepo);
        String boardTitle = "Test Board";
        List<String> boardRows = BoardDataSource.boardRowsTest();
        List<String> boardColumns = BoardDataSource.boardColumnsTest();
        ECourseUser user = UserDataSource.getTestStudent1();

        // Act
        Board board = boardBuilder
                .withBoardOwner(user)
                .withBoardTitle(boardTitle)
                .withRows(boardRows)
                .withColumns(boardColumns)
                .build();

       String boardString = "BoardTitle: " + boardTitle + "Board Dimensions: " + boardRows.size() + " lines x  " + boardColumns.size() + " columns" + "Board State: " + "ACTIVE";

       // Assert
        assertEquals(board.toString(), boardString);
    }

    @Test
    public void ensureBoardBuilderThrowsExceptionWhenBoardTitleAlreadyExists() {
        // Arrange
        BoardBuilder boardBuilder = new BoardBuilder(boardRepo);
        String boardTitle = "Test Board";
        List<String> boardRows = BoardDataSource.boardRowsTest();
        List<String> boardColumns = BoardDataSource.boardColumnsTest();
        ECourseUser user = UserDataSource.getTestStudent1();
        Board board = boardBuilder
                .withBoardOwner(user)
                .withBoardTitle(boardTitle)
                .withRows(boardRows)
                .withColumns(boardColumns)
                .build();

        when(boardRepo.findByTitle(boardTitle)).thenReturn(board);

        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            boardBuilder
                    .withBoardOwner(user)
                    .withBoardTitle(boardTitle)
                    .withRows(boardRows)
                    .withColumns(boardColumns)
                    .build();
        });
    }
}
