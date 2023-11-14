package boardmanagement.domain;

import boardmanagement.repository.IBoardRepository;
import ecourseusermanagement.domain.ECourseUser;

import java.util.ArrayList;
import java.util.List;

public class BoardBuilder {

    private final IBoardRepository boardRepository;

    private String boardTitle;
    private List<BoardColumn> columns;
    private List<BoardRow> rows;
    private BoardPermission boardOwner;

    public BoardBuilder(IBoardRepository boardRepository) {
        if(boardRepository == null) {
            throw new IllegalArgumentException("A Board Repository is required.");
        }
        this.boardRepository = boardRepository;
    }

    public BoardBuilder withBoardOwner(ECourseUser eCourseUser){
        this.boardOwner = new BoardPermission(eCourseUser, BoardPermissionType.OWNER);
        return this;
    }

    public BoardBuilder withBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
        return this;
    }

    public BoardBuilder withColumns(List<String> columnsTitles) {
        // Create the board columns
        List<BoardColumn> columns = new ArrayList<>();
        for (int i = 0; i < columnsTitles.size(); i++) {
            // Instantiate the board column
            BoardColumn boardColumn = new BoardColumn(new BoardLineTitle(columnsTitles.get(i)), i);

            // Assure that the column is not null
            columns.add(boardColumn);
        }

        this.columns = columns;
        return this;
    }

    public BoardBuilder withRows(List<String> rowsTitles) {
        // Create the board rows
        List<BoardRow> rows = new ArrayList<>();
        for (int i = 0; i < rowsTitles.size(); i++) {
            // Instantiate the board row
            BoardRow boardRows = new BoardRow(new BoardLineTitle(rowsTitles.get(i)), i);

            // Assure that the row is not null
            rows.add(boardRows);
        }

        this.rows = rows;
        return this;
    }

public Board build() {
        //Since the title is unique, this verification is required
        if(boardRepository.findByTitle(boardTitle) != null) {
            throw new IllegalArgumentException("Board with the introduced title already exists.");
        }

        if(boardOwner == null) {
            throw new IllegalArgumentException("A Board owner is required.");
        }

        if(columns == null || columns.isEmpty()) {
            throw new IllegalArgumentException("More than one board columns are required.");
        }

        if(rows == null || rows.isEmpty()) {
            throw new IllegalArgumentException("More than one board rows are required.");
        }

        return new Board(boardOwner,boardTitle, rows, columns);
    }
}
