package jpa;

import boardmanagement.domain.Board;
import boardmanagement.domain.BoardPermission;
import boardmanagement.domain.BoardPermissionType;
import boardmanagement.domain.BoardState;
import boardmanagement.repository.IBoardRepository;
import ecourseusermanagement.domain.ECourseUser;

import javax.persistence.TypedQuery;

public class JpaBoardRepository extends eCourseJpaRepositoryBase<Board, Long, Long> implements IBoardRepository {
    public JpaBoardRepository() {
        super("id");
    }

    @Override
    public Board findByTitle(String title) {
        // Query that returns the board with the specified title and not archived
        final TypedQuery<Board> query = entityManager().createQuery("SELECT c FROM Board c" +
                " WHERE c.boardTitle.boardTitle = :title AND c.boardState = :state", Board.class);
        query.setParameter("title", title);
        query.setParameter("state", BoardState.ACTIVE);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Iterable<Board> findAllUserBoards(String email) {
        // Query that returns all the boards WHERE boardUsersPermissions contains a user with the specified email
        TypedQuery<Board> query = entityManager().createQuery(
                "SELECT DISTINCT b FROM Board b WHERE b.id IN " +
                        "(SELECT b2.id FROM Board b2 JOIN b2.boardUsersPermissions bp " +
                        "WHERE bp.eCourseUser.email = :email) AND b.boardState = :state", Board.class);
        query.setParameter("email", email);
        query.setParameter("state", BoardState.ACTIVE);

        return query.getResultList();
    }

    @Override
    public boolean userCanWrite(ECourseUser user, Board board) {
        // Query to find if the user has WRITE permission on the board
        TypedQuery<Board> query = entityManager().createQuery(
                "SELECT b FROM Board b JOIN b.boardUsersPermissions bp" +
                        " WHERE b.id = :boardId AND bp.eCourseUser.email = :email " +
                        "AND (bp.boardPermissionType = 'WRITE' OR bp.boardPermissionType = 'OWNER')", Board.class);

        query.setParameter("boardId", board.identity());
        query.setParameter("email", user.email());

        try {
            query.getSingleResult();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
