package postitmanagement.repository;

import boardmanagement.domain.*;
import eapli.framework.domain.repositories.*;
import postitmanagement.domain.*;

import java.util.*;

public interface PostItRepository extends DomainRepository<Long, PostIt> {


    /**
     * Finds a post-it by its board cell
     *
     * @param boardCell the board cell
     * @return the post-it
     */
    Optional<PostIt> findByBoardCell(BoardCell boardCell);


    /**
     * Finds all post-it in a board
     * @param boardCells the board cells
     * @return the list of post-its
     */
    List<PostIt> findBoardPostIts(Iterable<BoardCell> boardCells);


}
