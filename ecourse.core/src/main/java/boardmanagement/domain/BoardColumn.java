package boardmanagement.domain;

import javax.persistence.Entity;

@Entity
public class BoardColumn extends BoardLine {
    public BoardColumn(final BoardLineTitle boardLineTitle, final int boardLineNumber) {
        super(boardLineTitle, boardLineNumber);
    }

    public BoardColumn() {
        // for ORM
    }
}
