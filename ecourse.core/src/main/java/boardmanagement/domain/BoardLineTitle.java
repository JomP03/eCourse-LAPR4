package boardmanagement.domain;

import eapli.framework.strings.util.StringPredicates;
import eapli.framework.validations.Preconditions;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BoardLineTitle {
    private static final int MAX_LENGTH = 23;
    @Column
    private String boardLineTitle;

    public BoardLineTitle(final String boardLineTitle) {

        if(boardLineTitle.length() > MAX_LENGTH) throw new IllegalArgumentException("Board Line Title is too long. (23 characters max.)");

        this.boardLineTitle = boardLineTitle;
    }

    protected BoardLineTitle() {
        // for ORM
    }

    @Override
    public String toString() {
        return this.boardLineTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardLineTitle)) return false;

        BoardLineTitle postIt = (BoardLineTitle) o;

        return this.boardLineTitle.equals(postIt.boardLineTitle);
    }
}
