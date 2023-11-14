package boardmanagement.domain;
import postitmanagement.domain.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BoardLineNumber{
    @Column
    private int boardLineNumber;

    public BoardLineNumber(final int boardLineNumber) {
        if(boardLineNumber < 0){
            throw new IllegalArgumentException("Board Line Number shouldn't be negative");
        }
        this.boardLineNumber = boardLineNumber;
    }

    protected BoardLineNumber() {
        // for ORM
    }

    /**
     * Line number int.
     *
     * @return the int
     */
    public int number(){
        return this.boardLineNumber;
    }

    public static BoardLineNumber valueOf(int oldRow) {
        return new BoardLineNumber(oldRow);
    }

    @Override
    public String toString() {
        return Integer.toString(this.boardLineNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardLineNumber)) return false;

        BoardLineNumber boardLineNumber = (BoardLineNumber) o;

        return this.boardLineNumber == boardLineNumber.boardLineNumber;
    }
}
