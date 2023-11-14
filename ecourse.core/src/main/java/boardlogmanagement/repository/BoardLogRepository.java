package boardlogmanagement.repository;

import boardlogmanagement.domain.*;
import eapli.framework.domain.repositories.*;

public interface BoardLogRepository extends DomainRepository<Long, BoardLog> {

    /*
     * Find by board logs by board id.
     */
    Iterable<BoardLog> findByBoardId(long boardId);
}
