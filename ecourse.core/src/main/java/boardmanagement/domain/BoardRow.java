package boardmanagement.domain;

import javax.persistence.Entity;

@Entity
public class BoardRow extends BoardLine {

    public BoardRow(final BoardLineTitle boardLineTitle, final int boardLineNumber) {
        super(boardLineTitle, boardLineNumber);
    }

    public BoardRow() {
        // for ORM
    }


    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
