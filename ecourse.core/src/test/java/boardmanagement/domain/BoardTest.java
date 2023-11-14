package boardmanagement.domain;

import ecourseusermanagement.domain.*;
import org.junit.jupiter.api.Test;
import postitmanagement.domain.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @Test
    public void ensureBoardIsCreatedWithValidInformation() {
        // Arrange
        String boardTitle = "Test Board";
        List<BoardRow> boardRows = new ArrayList<>();
        for (String title : BoardDataSource.boardRowsTest()) {
            boardRows.add(new BoardRow(new BoardLineTitle(title), 1));
        }
        List<BoardColumn> boardColumns = new ArrayList<>();
        for (String title : BoardDataSource.boardRowsTest()) {
            boardColumns.add(new BoardColumn(new BoardLineTitle(title), 1));
        }
        Board board = BoardDataSource.boardTest();

        // Act
        String boardString = "BoardTitle: " + boardTitle + "Board Dimensions: " + boardRows.size() + " lines x  " + boardColumns.size() + " columns" + "Board State: " + "ACTIVE";

        // Assert
        assertEquals(board.toString(), boardString);
    }


    @Test
    void ensureBoardIsNotCreatedWithNullUser() {
        // Arrange
        String boardTitle = "Test Board";
        List<BoardRow> boardRows = new ArrayList<>();
        for (String title : BoardDataSource.boardRowsTest()) {
            boardRows.add(new BoardRow(new BoardLineTitle(title), 1));
        }
        List<BoardColumn> boardColumns = new ArrayList<>();
        for (String title : BoardDataSource.boardRowsTest()) {
            boardColumns.add(new BoardColumn(new BoardLineTitle(title), 1));
        }

        // Assert
        assertThrows(IllegalArgumentException.class, () -> new Board(null, boardTitle,
                boardRows, boardColumns));
    }


    @Test
    void ensureBoardIsNotCreatedWithMoreRowsThanAllowed() {
        // Arrange
        BoardPermission permission = new BoardPermission(UserDataSource.getTestStudent1(), BoardPermissionType.OWNER);
        String boardTitle = "Test Board";
        List<BoardRow> boardRows = new ArrayList<>();
        List<BoardColumn> boardColumns = new ArrayList<>();
        for (String title : BoardDataSource.boardRowsTest()) {
            boardColumns.add(new BoardColumn(new BoardLineTitle(title), 1));
        }

        List<BoardRow> boardRowsToUse = new ArrayList<>();
        for (String title : BoardDataSource.boardRowsTest()) {
            boardRowsToUse.add(new BoardRow(new BoardLineTitle(title), 1));
        }

        for (int i = 0; i < 10; i++) {
            boardRows.addAll(boardRowsToUse);
        }

        // Assert
        assertThrows(IllegalArgumentException.class, () -> new Board(permission, boardTitle,
                boardRows, boardColumns));
    }


    @Test
    void ensureBoardIsNotCreatedWithMoreColumnsThanAllowed() {
        // Arrange
        BoardPermission permission = new BoardPermission(UserDataSource.getTestStudent1(), BoardPermissionType.OWNER);
        String boardTitle = "Test Board";
        List<BoardRow> boardRows = new ArrayList<>();
        for (String title : BoardDataSource.boardRowsTest()) {
            boardRows.add(new BoardRow(new BoardLineTitle(title), 1));
        }
        List<BoardColumn> boardColumns = new ArrayList<>();

        List<BoardColumn> boardColumnsToUse = new ArrayList<>();
        for (String title : BoardDataSource.boardRowsTest()) {
            boardColumnsToUse.add(new BoardColumn(new BoardLineTitle(title), 1));
        }

        for (int i = 0; i < 10; i++) {
            boardColumns.addAll(boardColumnsToUse);
        }

        // Assert
        assertThrows(IllegalArgumentException.class, () -> new Board(permission, boardTitle,
                boardRows, boardColumns));
    }


    @Test
    public void ensureBoardIsNotCreatedWithEmptyBoardTitle() {
        // Arrange
        String boardTitle = "";
        List<BoardRow> boardRows = new ArrayList<>();
        for (String title : BoardDataSource.boardRowsTest()) {
            boardRows.add(new BoardRow(new BoardLineTitle(title), 1));
        }
        List<BoardColumn> boardColumns = new ArrayList<>();
        for (String title : BoardDataSource.boardRowsTest()) {
            boardColumns.add(new BoardColumn(new BoardLineTitle(title), 1));
        }
        BoardPermission permission = new BoardPermission(UserDataSource.getTestStudent1(), BoardPermissionType.OWNER);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> new Board(permission, boardTitle, boardRows, boardColumns));
    }

    @Test
    public void ensureBoardIsNotCreatedWithNullBoardTitle() {
        // Arrange
        String boardTitle = null;
        List<BoardRow> boardRows = new ArrayList<>();
        for (String title : BoardDataSource.boardRowsTest()) {
            boardRows.add(new BoardRow(new BoardLineTitle(title), 1));
        }
        List<BoardColumn> boardColumns = new ArrayList<>();
        for (String title : BoardDataSource.boardRowsTest()) {
            boardColumns.add(new BoardColumn(new BoardLineTitle(title), 1));
        }
        BoardPermission permission = new BoardPermission(UserDataSource.getTestStudent1(), BoardPermissionType.OWNER);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> new Board(permission, boardTitle, boardRows, boardColumns));
    }

    @Test
    public void ensureBoardIsNotCreatedWithEmptyBoardRows() {
        // Arrange
        String boardTitle = "Test Board";
        List<BoardRow> boardRows = new ArrayList<>();
        List<BoardColumn> boardColumns = new ArrayList<>();
        for (String title : BoardDataSource.boardRowsTest()) {
            boardColumns.add(new BoardColumn(new BoardLineTitle(title), 1));
        }
        BoardPermission permission = new BoardPermission(UserDataSource.getTestStudent1(), BoardPermissionType.OWNER);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> new Board(permission, boardTitle, boardRows, boardColumns));
    }

    @Test
    public void ensureBoardIsNotCreatedWithEmptyBoardColumns() {
        // Arrange
        String boardTitle = "Test Board";
        List<BoardRow> boardRows = new ArrayList<>();
        for (String title : BoardDataSource.boardRowsTest()) {
            boardRows.add(new BoardRow(new BoardLineTitle(title), 1));
        }
        List<BoardColumn> boardColumns = new ArrayList<>();
        BoardPermission permission = new BoardPermission(UserDataSource.getTestStudent1(), BoardPermissionType.OWNER);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> new Board(permission, boardTitle, boardRows, boardColumns));
    }


    @Test
    void ensureBoardIsActive() {
        // Arrange
        Board board = BoardDataSource.boardWithPostIts();

        // Assert
        assertTrue(board.isActive());
    }


    @Test
    void ensureBoardRowsAreReturned() {
        // Arrange
        Board board = BoardDataSource.boardWithPostIts();

        // Assert
        assertEquals(board.rows(), BoardDataSource.boardRows());
    }


    @Test
    void ensureBoardColumnsAreReturned() {
        // Arrange
        Board board = BoardDataSource.boardWithPostIts();

        // Assert
        assertEquals(board.columns(), BoardDataSource.boardColumns());
    }

    @Test
    public void ensureBoardIsArchived(){
        // Arrange
        Board board = BoardDataSource.boardTest();

        // Act
        board.archiveBoard(UserDataSource.getTestStudent1());

        // Assert
        assertEquals(board.state(), BoardState.ARCHIVED);
    }


    @Test
    public void ensureBoardIsNotArchivedIfAlready() {
        // Arrange
        Board board = BoardDataSource.boardTest();
        board.archiveBoard(UserDataSource.getTestStudent1());

        // Assert
        assertThrows(IllegalArgumentException.class, () -> board.archiveBoard(UserDataSource.getTestStudent1()));
    }

    @Test
    public void ensureUsersThatAreNotOwnersCannotArchiveBoards(){
        // Arrange
        Board board = BoardDataSource.boardTest();

        assertThrows(IllegalArgumentException.class, () -> board.archiveBoard(UserDataSource.getTestStudent2()));
    }


    @Test
    void ensureBoardFindsCell() {
        // Arrange
        Board board = BoardDataSource.boardWithPostIts();
        BoardCell expectedCell = board.cells().get(0);

        // Act
        BoardCell actualCell = board.findCell(BoardLineNumber.valueOf(1), BoardLineNumber.valueOf(1));

        // Assert
        assertEquals(expectedCell, actualCell);
    }


    @Test
    void ensureBoardDoesNotFindsCellIfCellDoesNotExist() {
        // Arrange
        Board board = BoardDataSource.boardWithPostIts();

        // Assert
        assertNull(board.findCell(BoardLineNumber.valueOf(10), BoardLineNumber.valueOf(10)));
    }


    @Test
    void ensureBoardReturnsCells() {
        // Arrange
        Board board = BoardDataSource.boardWithOneCell();
        List<BoardCell> expectedCells = board.cells();

        // Act
        List<BoardCell> actualCells = new ArrayList<>();
        actualCells.add(board.findCell(BoardLineNumber.valueOf(1), BoardLineNumber.valueOf(1)));

        // Assert
        assertEquals(expectedCells, actualCells);
    }


    @Test
    void ensureBoardHasWritePermissionMethod() {
        // Arrange
        Board board = BoardDataSource.boardWithPostIts();
        ECourseUser user = UserDataSource.getTestStudent1();

        // Assert
        assertTrue(board.hasWritePermissions(user));
    }


    @Test
    void ensureBoardHasWritePermissionMethodWithInvalidUser() {
        // Arrange
        Board board = BoardDataSource.boardWithPostIts();
        ECourseUser user = UserDataSource.getTestStudent2();

        // Assert
        assertThrows(NullPointerException.class, () -> board.hasWritePermissions(user));
    }


    @Test
    void ensureBoardIsShared(){
        // Arrange
        Board board = BoardDataSource.boardTest();
        ECourseUser user = UserDataSource.getTestStudent2();
        ECourseUser user2 = UserDataSource.getTestTeacher1();
        BoardPermission permission = new BoardPermission(user, BoardPermissionType.WRITE);
        BoardPermission permission2 = new BoardPermission(user, BoardPermissionType.READ);
        BoardPermission permission3 = new BoardPermission(user2, BoardPermissionType.READ);
        List<BoardPermission> expectedPermissions = new ArrayList<>();
        expectedPermissions.add(new BoardPermission(UserDataSource.getTestStudent1(), BoardPermissionType.OWNER));
        expectedPermissions.add(permission);
        expectedPermissions.add(permission2);
        expectedPermissions.add(permission3);

        // Act
        board.users().add(permission);
        board.users().add(permission2);
        board.users().add(permission3);

        // Assert
        assertEquals(expectedPermissions.toString(), board.users().toString());
    }


    @Test
    void ensureBoardEqualsWithValidInformation() {
        // Arrange
        Board board = BoardDataSource.boardWithOneCell();
        Board board2 = BoardDataSource.boardWithOneCell();

        // Assert
        assertEquals(board, board2);
    }


    @Test
    void ensureBoardEqualsWithDifferentInformation() {
        // Arrange
        Board board = BoardDataSource.boardWithOneCell();
        Board board2 = BoardDataSource.boardWithPostIts();

        // Assert
        assertNotEquals(board, board2);
    }


    @Test
    void ensureBoardEqualsWithDifferentObjects() {
        // Arrange
        Board board = BoardDataSource.boardWithOneCell();
        Board board2 = BoardDataSource.boardWithPostIts();

        // Assert
        assertNotEquals(board, board2);
    }


    @Test
    void ensureBoardSameAsMethod() {
        // Arrange
        Board board = BoardDataSource.boardWithOneCell();
        Board board2 = BoardDataSource.boardWithOneCell();

        // Assert
        assertFalse(board.sameAs(board2));
    }


    @Test
    void ensureBoardIdentity() {
        // Arrange
        Board board = BoardDataSource.boardWithOneCell();

        // Assert
        assertNull(board.identity());
    }
}
