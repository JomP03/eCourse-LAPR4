package ecourseusermanagement.repositories;

import boardmanagement.domain.Board;
import eapli.framework.domain.repositories.DomainRepository;
import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.domain.UserMechanographicNumber;

import java.util.Optional;

/**
 * The interface for a eCourseUser repository.
 */
public interface IeCourseUserRepository extends DomainRepository<Long, ECourseUser> {

    /**
     * Find user by email.
     *
     * @param email the email
     * @return the optional
     */
    Optional<ECourseUser> findByEmail(String email);

    /**
     * Find last mechanographic number in year.
     *
     * @param year the year
     * @return the last mechanographic number in the given year or empty
     */
    Optional<UserMechanographicNumber> findLastMechanographicNumberInYear(String year);

    /**
     * Returns all users that are enabled.
     *
     * @return the iterable of users
     */
    Iterable<ECourseUser> findAllEnabledUsers();

    /**
     * Returns all users that are disabled.
     *
     * @return the iterable of users
     */
    Iterable<ECourseUser> findAllDisabledUsers();

    /**
     * Returns all the teachers in the system.
     *
     * @return the iterable
     */
    Iterable<ECourseUser> findAllTeachers();

    /**
     * Returns all users in the system that are not in the boardUsersPermission list.
     * @param board the board
     * @return the iterable of users that are not in the boardUsersPermission list
     */
    Iterable<ECourseUser> findAvailableUsersToShareBoardWith(Board board);
}
