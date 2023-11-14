package boardmanagement.domain;

import boardmanagement.domain.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardLineTest {

    @Test
    public void ensureValidBoardLineIsAccepted() {
        // Arrange
        BoardLineTitle boardLineTitle = new BoardLineTitle("Board Line Test Title");
        int boardLineNumber = 2;

        // Act
        BoardLine boardLine = new BoardLine(boardLineTitle, boardLineNumber);

        String boardLineString = "Title: " + boardLineTitle + " | Number: " + boardLineNumber;

        // Assert
        assertEquals(boardLine.toString(), boardLineString);
    }

    @Test
    public void ensureNullBoardLineTitleIsRejected() {
        // Arrange
        BoardLineTitle boardLineTitle = null;
        int boardLineNumber = 2;

        // Assert
        assertThrows(IllegalArgumentException.class, () -> new BoardLine(boardLineTitle, boardLineNumber));
    }

    @Test
    public void ensureInvalidBoardLineNumberIsRejected() {
        // Arrange
        BoardLineTitle boardLineTitle = new BoardLineTitle("Board Line Test Title");
        int boardLineNumber = -2;

        // Assert
        assertThrows(IllegalArgumentException.class, () -> new BoardLine(boardLineTitle, boardLineNumber));
    }

    @Test
    public void ensureBoardLineReturnsCorrectBoardLineNumber(){
        // Arrange
        BoardLineTitle boardLineTitle = new BoardLineTitle("Board Line Test Title");
        int boardLineNumber = 2;

        // Act
        BoardLine boardLine = new BoardLine(boardLineTitle, boardLineNumber);

        // Assert
        assertEquals(boardLine.number(), boardLineNumber);
    }
}
