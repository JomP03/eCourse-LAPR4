package boardmanagement.domain;

import boardmanagement.domain.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardLineNumberTest {
    @Test
    public void ensureValidBoardLineNumberIsAccepted(){
        // Arrange
        int boardLineNumber = 2;

        // Act
        BoardLineNumber boardLineNumberTest = new BoardLineNumber(boardLineNumber);
        String boardLineNumberString = Integer.toString(boardLineNumber);

        // Assert
        assertEquals(boardLineNumberTest.toString(), boardLineNumberString);
    }

    @Test
    public void ensureInvalidBoardLineNumberIsRejected() {
        // Arrange
        int boardLineNumber = -1;

        // Assert
        assertThrows(IllegalArgumentException.class, () -> new BoardLineNumber(boardLineNumber));
    }


    @Test
    void ensureBoardLineNumberReturnsItsNumber() {
        // Arrange
        int boardLineNumber = 2;
        BoardLineNumber boardLineNumberTest = new BoardLineNumber(boardLineNumber);

        // Assert
        assertEquals(boardLineNumberTest.number(), boardLineNumber);
    }


    @Test
    void ensureValueOfMethodWorks() {
        // Arrange
        int boardLineNumber = 2;
        BoardLineNumber boardLineNumberTest = new BoardLineNumber(boardLineNumber);

        // Act
        BoardLineNumber boardLineNumberTest2 = BoardLineNumber.valueOf(boardLineNumber);

        // Assert
        assertEquals(boardLineNumberTest, boardLineNumberTest2);
    }


    @Test
    void ensureBoardLineNumberEqualsWithValidInformation() {
        // Arrange
        int boardLineNumber = 2;
        BoardLineNumber boardLineNumberTest = new BoardLineNumber(boardLineNumber);

        // Act
        BoardLineNumber boardLineNumberTest2 = new BoardLineNumber(boardLineNumber);

        // Assert
        assertEquals(boardLineNumberTest, boardLineNumberTest2);
    }


    @Test
    void ensureBoardLineNumberDoesNotEqualWithInvalidInformation() {
        // Arrange
        int boardLineNumber = 2;
        BoardLineNumber boardLineNumberTest = new BoardLineNumber(boardLineNumber);

        // Act
        BoardLineNumber boardLineNumberTest2 = new BoardLineNumber(3);

        // Assert
        assertNotEquals(boardLineNumberTest, boardLineNumberTest2);
    }


    @Test
    void ensureBoardLineNumberEqualsWithDifferentObject() {
        // Arrange
        int boardLineNumber = 2;
        BoardLineNumber boardLineNumberTest = new BoardLineNumber(boardLineNumber);

        // Act
        String boardLineNumberTest2 = "2";

        // Assert
        assertNotEquals(boardLineNumberTest, boardLineNumberTest2);
    }
}
