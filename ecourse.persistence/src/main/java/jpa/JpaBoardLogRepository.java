package jpa;

import boardlogmanagement.domain.*;
import boardlogmanagement.repository.*;

import javax.persistence.TypedQuery;

public class JpaBoardLogRepository extends eCourseJpaRepositoryBase<BoardLog, Long, Long> implements BoardLogRepository {

    public JpaBoardLogRepository() {
        super("long");
    }

    @Override
    public Iterable<BoardLog> findByBoardId(long boardId) {
        // Query that returns all the board logs WHERE board id is the specified one
        final TypedQuery<BoardLog> query = entityManager().createQuery("SELECT c FROM BoardLog c" +
                " WHERE c.board.id = :boardId", BoardLog.class);
        query.setParameter("boardId", boardId);

        return query.getResultList();
    }
}
