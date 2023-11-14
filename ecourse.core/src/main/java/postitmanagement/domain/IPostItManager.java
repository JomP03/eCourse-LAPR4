package postitmanagement.domain;

import boardmanagement.domain.Board;
import boardmanagement.domain.BoardLineNumber;
import ecourseusermanagement.domain.ECourseUser;
import postitmanagement.domain.PostIt;

public interface IPostItManager {

    /**
     * Creates a post-it.
     * This method must verify if there is already a post-it in the given position.
     * @param board the board
     * @param row the row
     * @param column the column
     * @param postItCreator the post-it creator
     * @param postItContent the post-it content
     * @param postItColor the post-it color
     * @return the created post-it
     */
    PostIt createPostIt(Board board, BoardLineNumber row, BoardLineNumber column, ECourseUser postItCreator,
                        String postItContent, String postItColor);

    /**
     * Changes a post-it.
     * @param user         the user who is changing the post-it
     * @param board        the board where the post-it is
     * @param oldRow       the row of where the post-it was
     * @param oldColumn    the column of where the post-it was
     * @param oldContent   the old content of the post-it
     * @param newContent   the new content of the post-it
     * @param color        the color of the post-it
     * @return the changed post-it
     */
    PostIt changePostItContent(ECourseUser user, Board board,
                               int oldRow, int oldColumn, String oldContent, String newContent, String color);

    /**
     * Moves a post-it to another cell.
     * @param user        the user who is moving the post-it
     * @param board       the board where the post-it is
     * @param postIt      the post-it to be moved
     * @param newRow      the new row of the post-it
     * @param newColumn   the new column of the post-it
     * @return the moved post-it
     */
    PostIt movePostIt(ECourseUser user, Board board, PostIt postIt, int newRow, int newColumn);


    /**
     * Undo post-it last change
     *
     * @param board            the board the post-it belongs to
     * @param boardUser        the board user trying to undo the post-it
     * @param row  the row where the post-it is placed
     * @param column the column where the post-it is placed
     * @return the post it
     */
    PostIt undoPostItChange(Board board, ECourseUser boardUser, BoardLineNumber row, BoardLineNumber column);


}