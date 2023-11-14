package boardmanagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTitleTest {
    @Test
    public void ensureValidBoardTitleIsAccepted() {
        // Arrange
        String boardTitleString = "LAPR4 Board";

        // Act
        BoardTitle boardTitle = new BoardTitle(boardTitleString);

        // Assert
        assertEquals(boardTitle.toString(), boardTitleString);
    }

    @Test
    public void ensureEmptyBoardTitleIsRejected() {
        // Arrange
        String boardTitleString = "";

        // Assert
        assertThrows(IllegalArgumentException.class, () -> new BoardTitle(boardTitleString));
    }

    @Test
    public void ensureNullBoardTitleIsRejected() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> new BoardTitle(null));
    }

    @Test
    public void ensureBoardTitleEqualsSameTitle() {
        // Arrange
        String boardTitleString = "LAPR4 Board";
        BoardTitle boardTitle = new BoardTitle(boardTitleString);
        BoardTitle boardTitle2 = new BoardTitle(boardTitleString);

        // Assert
        assertEquals(boardTitle, boardTitle2);
    }

    @Test
    public void ensureBoardTitleIsTheSame() {
        // Arrange
        String boardTitleString = "LAPR4 Board";
        BoardTitle boardTitle = new BoardTitle(boardTitleString);

        // Assert
        assertEquals(boardTitle, boardTitle);
    }

    @Test
    public void ensureBoardTitleEqualsDifferentTitle() {
        // Arrange
        String boardTitleString = "LAPR4 Board";
        String boardTitleString2 = "LAPR5 Board";
        BoardTitle boardTitle = new BoardTitle(boardTitleString);
        BoardTitle boardTitle2 = new BoardTitle(boardTitleString2);

        // Assert
        assertNotEquals(boardTitle, boardTitle2);
    }

    @Test
    public void ensureBoardTitleIsNotTheSame() {
        // Arrange
        String boardTitleString = "LAPR4 Board";
        String boardTitleString2 = "LAPR5 Board";
        BoardTitle boardTitle = new BoardTitle(boardTitleString);
        BoardTitle boardTitle2 = new BoardTitle(boardTitleString2);

        // Assert
        assertEquals(-1, boardTitle.compareTo(boardTitle2));
    }


    @Test
    void ensureBoardTitleValueOfMethod() {
        // Arrange
        String boardTitleString = "LAPR4 Board";
        BoardTitle boardTitle = new BoardTitle(boardTitleString);

        // Act
        BoardTitle boardTitle2 = BoardTitle.valueOf(boardTitleString);

        // Assert
        assertEquals(boardTitle, boardTitle2);
    }
}
