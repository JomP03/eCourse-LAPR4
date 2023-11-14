package ecourseusermanagement.application;

import ecourseusermanagement.domain.ECourseUser;

import java.util.Optional;

public interface IUsersProvider {

    /**
     * Retrieve user by id.
     *
     * @param id the id of the user
     *
     * @return the user with the given id
     */
    Optional<ECourseUser> retrieveUserById(Long id);
}
