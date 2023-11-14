package boardmanagement.domain;

import appsettings.Application;
import eapli.framework.domain.model.AggregateRoot;
import ecourseusermanagement.domain.ECourseUser;
import postitmanagement.domain.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Board implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private BoardTitle boardTitle;

    @OneToMany(cascade = CascadeType.ALL)
    private List<BoardRow> rows;

    @OneToMany(cascade = CascadeType.ALL)
    private List<BoardColumn> columns;

    @OneToOne(cascade = CascadeType.ALL)
    private BoardPermission boardOwner;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<BoardPermission> boardUsersPermissions;

    @Enumerated(EnumType.STRING)
    private BoardState boardState;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BoardCell> boardCells;

    private static final Integer MAX_NUM_OF_ROWS = Application.settings().getMaxNumberOfRows();

    private static final Integer MAX_NUM_OF_COLUMNS = Application.settings().getMaxNumberOfColumns();

    public Board(final BoardPermission boardOwner, final String boardTitle,
                 final List<BoardRow> rows, final List<BoardColumn> columns) {

        if (boardOwner == null) {
            throw new IllegalArgumentException("Board owner cannot be null.");
        }

        this.boardOwner = boardOwner;
        this.boardUsersPermissions = new ArrayList<>();
        this.boardUsersPermissions.add(boardOwner);
        this.boardTitle = new BoardTitle(boardTitle);

        if (rows.isEmpty() || columns.isEmpty()) {
            throw new IllegalArgumentException("Board rows or columns cannot be empty.");
        }

        if (rows.size() > MAX_NUM_OF_ROWS) {
            throw new IllegalArgumentException("The number of rows must be less than the max value defined in the properties file.");
        }

        if (columns.size() > MAX_NUM_OF_COLUMNS) {
            throw new IllegalArgumentException("The number of columns must be less than the max value defined in the properties file.");
        }

        this.rows = rows;
        this.columns = columns;
        this.boardState = BoardState.ACTIVE;
        this.boardCells = new ArrayList<>();

        defineCells(rows, columns);
    }

    /* Checks if the board is active
     *
     * @return true if the board is active
     */
    public boolean isActive() {
        return this.boardState == BoardState.ACTIVE;
    }

    /* Returns the rows of the board
     *
     * @return the list of rows
     */
    public List<BoardRow> rows() {
        return this.rows;
    }

    /* Returns the columns of the board
     *
     * @return the list of columns
     */
    public List<BoardColumn> columns() {
        return this.columns;
    }

    /*
     * Changes the state of the board to "ARCHIVED"
     *
     * @param user - the user to check if they are the owner of the board
     */
    public void archiveBoard(ECourseUser user) {
        if (!isBoardOwner(user))
            throw new IllegalArgumentException("User is not the owner of the board.");

        if (this.boardState == BoardState.ARCHIVED)
            throw new IllegalArgumentException("Board is already archived.");

        this.boardState = BoardState.ARCHIVED;
    }

    /* Returns the state of the board
     *
     */
    public BoardState state() {
        return this.boardState;
    }

    /**
     * Tells if the user is the owner of the board
     *
     * @param user the user
     * @return the boolean
     */
    public boolean isBoardOwner(ECourseUser user) {
        return this.boardOwner.owner().email().equals(user.email());
    }

    /**
     * Users of the board and their permissions.
     *
     * @return the list of users of the board and their permissions
     */
    public List<BoardPermission> users() {
        return this.boardUsersPermissions;
    }

    /* Defines the cells of the board
     *
     * @return the list of cell
     * DISCLAIMER: This method will be used in the future regarding the implementation of the post-it features.
     */
    public void defineCells(List<BoardRow> rows, List<BoardColumn> columns) {
        for (BoardRow row : rows) {
            for (BoardColumn column : columns) {
                boardCells.add(new BoardCell(row, column));
            }
        }
    }

    /**
     * Finds a cell in the board using the row and column numbers
     *
     * @param row    the row number
     * @param column the column number
     * @return the cell
     */
    public BoardCell findCell(BoardLineNumber row, BoardLineNumber column) {
        for (BoardCell cell : boardCells) {
            if (cell.row().number() == row.number() && cell.column().number() == column.number()) {
                return cell;
            }
        }

        return null;
    }

    /**
     * All cells list.
     *
     * @return the list
     */
    public List<BoardCell> cells() {
        return this.boardCells;
    }

    /**
     * Has write permissions boolean.
     *
     * @param user the user
     * @return true if the user has write permissions, false otherwise
     */
    public boolean hasWritePermissions(ECourseUser user) {
        return Objects.requireNonNull(this.permissionForUser(user)).hasWritePermission();
    }

    private BoardPermission permissionForUser(ECourseUser user) {
        for (BoardPermission permission : this.boardUsersPermissions) {
            if (permission.owner().equals(user)) {
                return permission;
            }
        }

        return null;
    }

    protected Board() {
        // for ORM
    }

    public void undoPostIt(ECourseUser boardUser, BoardLineNumber row, BoardLineNumber column) {
        // Find the cell in which the post-it will be undo
        BoardCell cell = findCell(row, column);

        // Check if the cell exists
        if (cell == null) {
            throw new IllegalArgumentException("Cell does not exist.");
        }

        // Check if the cell has a post-it
//        if (!cell.hasPostIt()) {
//            throw new IllegalArgumentException("Cell does not have a post-it.");
//        }

        // Check if the user is the post-it owner
//        if (!cell.postIt().get().isOwner(boardUser)) {
//            throw new IllegalArgumentException("User is not the post-it owner.");
//        }

        // Verify if the last content is not empty
        // if (cell.postIt().get().lastContent().isEmpty()) {
        // throw new IllegalArgumentException("Post-it has no content to undo.");
        // }
    }

    @Override
    public boolean sameAs(Object other) {
        return false;
    }

    @Override
    public Long identity() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BoardTitle: " + boardTitle + "Board Dimensions: " + rows.size() + " lines x  " + columns.size() + " columns" + "Board State: " + boardState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board)) return false;

        Board board = (Board) o;

        return boardTitle.equals(board.boardTitle);
    }
}