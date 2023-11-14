package boardmanagement.domain;

import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.domain.UserDataSource;
import java.util.ArrayList;
import java.util.List;

public class BoardDataSource {
    public static List<String> boardRowsTest(){

        // List of board line titles (string) with 3 elements
        List<String> boardRows = new ArrayList<>();

        boardRows.add(rowTitleTest());
        boardRows.add(rowTitleTest());
        boardRows.add(rowTitleTest());

        return boardRows;
    }

    public static List<String> boardColumnsTest(){

        // List of board line titles (string) with 3 elements
        List<String> boardColumns = new ArrayList<>();

        boardColumns.add(columnTitleTest());
        boardColumns.add(columnTitleTest());
        boardColumns.add(columnTitleTest());

        return boardColumns;
    }

    public static List<BoardRow> boardRows() {
        return List.of(
                new BoardRow(new BoardLineTitle("Row 1"), 1),
                new BoardRow(new BoardLineTitle("Row 2"), 2),
                new BoardRow(new BoardLineTitle("Row 3"), 3)
        );
    }

    public static List<BoardColumn> boardColumns() {
        return List.of(
                new BoardColumn(new BoardLineTitle("Column 1"), 1),
                new BoardColumn(new BoardLineTitle("Column 2"), 2),
                new BoardColumn(new BoardLineTitle("Column 3"), 3)
        );
    }

    public static Board boardTest(){
        String boardTitle = "Test Board";
        List<BoardRow> boardRows = new ArrayList<>();
        for (String title : BoardDataSource.boardRowsTest()) {
            boardRows.add(new BoardRow(new BoardLineTitle(title), 1));
        }
        List<BoardColumn> boardColumns = new ArrayList<>();
        for (String title : BoardDataSource.boardRowsTest()) {
            boardColumns.add(new BoardColumn(new BoardLineTitle(title), 1));
        }
        BoardPermission permission = new BoardPermission(UserDataSource.getTestStudent1(), BoardPermissionType.OWNER);

        return new Board(permission, boardTitle, boardRows, boardColumns);
    }

    public static Board boardWithPostIts() {
        return new Board(new BoardPermission(UserDataSource.getTestStudent1(), BoardPermissionType.OWNER),
                "Test Board", boardRows(), boardColumns());
    }

    public static Board boardWithOneCell() {
        List<BoardRow> rows = List.of(
                new BoardRow(new BoardLineTitle("Row 1"), 1)
        );
        List<BoardColumn> columns = List.of(
                new BoardColumn(new BoardLineTitle("Column 1"), 1)
        );
        return new Board(new BoardPermission(UserDataSource.getTestStudent1(), BoardPermissionType.OWNER),
                "One Cell Board", rows, columns);
    }

    public static BoardPermission boardPermissionTest(){
        BoardPermissionType permissionType = BoardPermissionType.OWNER;
        ECourseUser user = UserDataSource.getTestStudent1();
        return new BoardPermission(user, permissionType);
    }

    public static BoardCell boardCell(){
        BoardRow row = new BoardRow(new BoardLineTitle(rowTitleTest()), 1);
        BoardColumn column = new BoardColumn(new BoardLineTitle(columnTitleTest()), 1);
        return new BoardCell(row, column);
    }

    public static String rowTitleTest(){
        return "Test Row";
    }

    public static String columnTitleTest(){
        return "Test Column";
    }

}
