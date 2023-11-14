package jpa;

import boardmanagement.domain.*;
import postitmanagement.domain.*;
import postitmanagement.repository.*;

import javax.persistence.*;
import java.util.*;

public class JpaPostItRepository extends eCourseJpaRepositoryBase<PostIt, Long, Long> implements PostItRepository {

    /**
     * Instantiates a new Jpa post-it repository.
     *
     * @param identityFieldName the identity field name
     */
    public JpaPostItRepository(String identityFieldName) {
        super(identityFieldName);
    }

    @Override
    public Optional<PostIt> findByBoardCell(BoardCell boardCell) {
        final TypedQuery<PostIt> query = entityManager().createQuery("SELECT p FROM PostIt p " +
                "WHERE p.boardCell.id = :boardCell", PostIt.class);

        query.setParameter("boardCell", boardCell.identity());

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<PostIt> findBoardPostIts(Iterable<BoardCell> boardCells) {
        final TypedQuery<PostIt> query = entityManager().createQuery("SELECT p FROM PostIt p " +
                "WHERE p.boardCell IN :boardCells", PostIt.class);

        query.setParameter("boardCells", boardCells);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }
}