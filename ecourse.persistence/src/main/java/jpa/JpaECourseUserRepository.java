package jpa;

import boardmanagement.domain.Board;
import eapli.framework.infrastructure.authz.domain.model.Username;
import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.domain.UserMechanographicNumber;
import ecourseusermanagement.domain.UserState;
import ecourseusermanagement.repositories.IeCourseUserRepository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JpaECourseUserRepository extends eCourseJpaRepositoryBase<ECourseUser, Long, Long>
        implements IeCourseUserRepository {

    public JpaECourseUserRepository() {
        super("id");
    }

    @Override
    public Optional<ECourseUser> findByEmail(String email) {
        // TypedQuery in JPQL that returns the user with the given username
        final TypedQuery<ECourseUser> query = entityManager().createQuery(
                "SELECT u FROM ECourseUser u WHERE u.email = :email AND u.userState = :userState",
                ECourseUser.class);
        query.setParameter("email", email);
        query.setParameter("userState", UserState.ENABLED);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserMechanographicNumber> findLastMechanographicNumberInYear(String year) {
        // TypedQuery in JPQL that returns the last mechanographic number in a given year
        final TypedQuery<UserMechanographicNumber> query = entityManager().createQuery(
                "SELECT u.userMechanographicNumber FROM ECourseUser u" +
                        " WHERE u.userMechanographicNumber.mechanographicNumber LIKE :year " +
                        "ORDER BY u.userMechanographicNumber.mechanographicNumber DESC",
                UserMechanographicNumber.class);
        query.setParameter("year", year + "%");

        try {
            return Optional.ofNullable(query.setMaxResults(1).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Iterable<ECourseUser> findAllEnabledUsers() {
        // TypedQuery in JPQL that returns all enabled users
        final TypedQuery<ECourseUser> query = entityManager().createQuery(
                "SELECT u FROM ECourseUser u WHERE u.userState = :userState",
                ECourseUser.class);
        query.setParameter("userState", UserState.ENABLED);

        return query.getResultList();
    }

    @Override
    public Iterable<ECourseUser> findAllDisabledUsers() {
        // TypedQuery in JPQL that returns all disabled users
        final TypedQuery<ECourseUser> query = entityManager().createQuery(
                "SELECT u FROM ECourseUser u WHERE u.userState = :userState",
                ECourseUser.class);
        query.setParameter("userState", UserState.DISABLED);

        return query.getResultList();
    }

    @Override
    public Iterable<ECourseUser> findAllTeachers() {
        List<ECourseUser> teachers = new ArrayList<>();
        findAll().forEach(user -> {
            if (user.isTeacher() && user.isEnabled()) {
                teachers.add(user);
            }
        });

        return teachers;
    }

    @Override
    public Iterable<ECourseUser> findAvailableUsersToShareBoardWith(Board board){
        final TypedQuery<ECourseUser> query = entityManager().createQuery(
                "SELECT u FROM ECourseUser u WHERE u.userState = :userState AND u NOT IN " +
                        "(SELECT p.eCourseUser FROM Board b JOIN b.boardUsersPermissions p WHERE b = :board)",
                ECourseUser.class);
        query.setParameter("userState", UserState.ENABLED);
        query.setParameter("board", board);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
