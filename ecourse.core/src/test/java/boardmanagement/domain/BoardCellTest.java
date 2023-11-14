package boardmanagement.domain;

import boardmanagement.domain.*;
import ecourseusermanagement.domain.*;
import org.junit.jupiter.api.*;
import postitmanagement.domain.*;

import static org.junit.jupiter.api.Assertions.*;

public class BoardCellTest {
    @Test
    public void ensureValidBoardCellIsAccepted() {
        // Arrange
        BoardRow boardRow = new BoardRow(new BoardLineTitle(BoardDataSource.boardRowsTest().get(0)), 1);
        BoardColumn boardColumn = new BoardColumn(new BoardLineTitle(BoardDataSource.boardColumnsTest().get(0)), 1);

        // Act
        BoardCell boardCell = new BoardCell(boardRow, boardColumn);

        String boardCellString = boardRow.toString() + boardColumn;

        // Assert
        assertEquals(boardCell.toString(), boardCellString);
    }

    @Test
    public void ensureBoarCellDoesNotGetCreatedWithNullRow() {
        // Arrange
        BoardColumn boardColumn = new BoardColumn(new BoardLineTitle("C1"), 1);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> new BoardCell(null, boardColumn));
    }


    @Test
    public void ensureBoarCellDoesNotGetCreatedWithNullColumn() {
        // Arrange
        BoardRow boardRow = new BoardRow(new BoardLineTitle("R1"), 1);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> new BoardCell(boardRow, null));
    }

    @Test
    public void ensureBoardCellReturnsCorrectBoardRow() {
        // Arrange
        BoardRow boardRow = new BoardRow(new BoardLineTitle(BoardDataSource.boardRowsTest().get(0)), 1);
        BoardColumn boardColumn =
                new BoardColumn(new BoardLineTitle(BoardDataSource.boardColumnsTest().get(0)), 1);

        // Act
        BoardCell boardCell = new BoardCell(boardRow, boardColumn);

        // Assert
        assertEquals(boardCell.row(), boardRow);
    }

    @Test
    public void ensureBoardCellReturnsCorrectBoardColumn() {
        // Arrange
        BoardRow boardRow = new BoardRow(new BoardLineTitle(BoardDataSource.boardRowsTest().get(0)), 1);
        BoardColumn boardColumn =
                new BoardColumn(new BoardLineTitle(BoardDataSource.boardColumnsTest().get(0)), 1);

        // Act
        BoardCell boardCell = new BoardCell(boardRow, boardColumn);

        // Assert
        assertEquals(boardCell.column(), boardColumn);
    }


    @Test
    void ensureBoardCellCoordinates() {
        // Arrange
        BoardRow boardRow = new BoardRow(new BoardLineTitle(BoardDataSource.boardRowsTest().get(0)), 1);
        BoardColumn boardColumn = new BoardColumn(new BoardLineTitle(BoardDataSource.boardColumnsTest().get(0)), 1);

        // Act
        BoardCell boardCell = new BoardCell(boardRow, boardColumn);

        // Assert
        assertEquals(boardCell.coordinates(), "1_1");
    }


    @Test
    void ensureBoardCellIdentity() {
        // Arrange
        BoardCell boardCell = BoardDataSource.boardCell();

        // Assert
        assertNull(boardCell.identity());
    }
}
