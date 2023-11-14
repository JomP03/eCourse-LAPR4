package postitmanagement.application;

import boardmanagement.domain.*;
import postitmanagement.domain.PostIt;

import java.util.List;
import java.util.Optional;

public interface IPostItProvider {


    /**
     * Find post it by the board cell
     *
     * @param cell the board cell
     * @return the optional of the post it
     */
    Optional<PostIt> providePostItByCell(BoardCell cell);


    /**
     * Check if the board cell has a post it
     * @param cell the board cell
     * @return true if the board cell has a post-it, false otherwise
     */
    boolean hasPostIt(BoardCell cell);


    /**
     * Find all post-it in a board
     * @param boardCells the board cells
     * @return the list of post-its
     */
    List<PostIt> boardPostIts(Iterable<BoardCell> boardCells);


}