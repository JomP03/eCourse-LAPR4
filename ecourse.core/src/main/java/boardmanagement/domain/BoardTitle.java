package boardmanagement.domain;

import eapli.framework.strings.util.StringPredicates;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BoardTitle implements Comparable<BoardTitle> {
    private static final int MAX_LENGTH = 40;
    @Column(unique = true)
    private String boardTitle;

    public BoardTitle(final String boardTitle) {
        if (StringPredicates.isNullOrEmpty(boardTitle)) {
            throw new IllegalArgumentException(
                    "Board Title should neither be null nor empty");
        }

        if (boardTitle.length() > MAX_LENGTH) throw new IllegalArgumentException("Board title is too long. (40 characters max.)");

        this.boardTitle = boardTitle;
    }

    protected BoardTitle() {
        // for ORM
    }

    /* Returns the board title
     *
     * @return the board title
     */
    public static BoardTitle valueOf(String boardTitle) {
        return new BoardTitle(boardTitle);
    }

    /* Returns the board title
     *
     * @return the board title
     */
    @Override
    public String toString() {
        return this.boardTitle;
    }

    /* Compares two board titles
     *
     * @param o the board title to compare
     * @return the comparison result
     */
    @Override
    public int compareTo(BoardTitle o) {
        return boardTitle.compareTo(o.boardTitle);
    }

    /*
     * @param o the board title to compare
     *
     * @return the comparison result
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardTitle)) return false;

        BoardTitle that = (BoardTitle) o;

        return boardTitle.equals(that.boardTitle);
    }
}
