package boardmanagement.domain;

import javax.persistence.*;

/**
 * The type Board cell.
 */
@Entity
public class BoardCell{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    BoardRow row;
    @OneToOne(cascade = CascadeType.ALL)
    BoardColumn column;

    public BoardCell(BoardRow row, BoardColumn column) {
        if (row == null) {
            throw new IllegalArgumentException("BoardCell must have a row.");
        }
        if (column == null) {
            throw new IllegalArgumentException("BoardCell must have a column.");
        }
        this.row = row;
        this.column = column;
    }

    public BoardCell() {
        // for ORM
    }

    /**
     * Cell row in the board.
     * @return the board row
     */
    public BoardRow row() {
        return row;
    }

    /**
     * Cell column in the board.
     * @return the board column
     */
    public BoardColumn column() {
        return column;
    }

    /**
     * Coordinates string.
     * @return the string with the coordinates
     */
    public String coordinates() {
        return this.row.number() + "_" + this.column.number();
    }

    public String toString() {
        return this.row.toString() + this.column.toString();
    }

    public Object identity() {
        return this.id;
    }
}
