package ecourseusermanagement.application;

import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.repositories.IeCourseUserRepository;

import java.util.List;

/**
 * The type List users controller.
 */
public class ListUsersController {

    private final IeCourseUserRepository userRepository;

        private final UserSessionService userSessionService;

    /**
     * Instantiates a controller for the listing users functionality.
     * Receives the user repository as parameter. (DI)
     *
     * @param userRepository the user repository
     */
    public ListUsersController(IeCourseUserRepository userRepository) {
        if ( userRepository == null ) {
            throw new IllegalArgumentException("User repository must be provided.");
        }
        this.userRepository = userRepository;
        this.userSessionService = new UserSessionService(userRepository);
    }

    /**
     * Returns all users.
     *
     * @return the iterable
     */
    public Iterable<ECourseUser> allUsers() {
        return userRepository.findAll();
    }

    /**
     * Returns all users that are enabled.
     *
     * @return the iterable
     */
    public Iterable<ECourseUser> enabledUsers() {
        List<ECourseUser> users = (List<ECourseUser>) userRepository.findAllEnabledUsers();
        // Remove the logged user from the list
        users.remove(userSessionService.getLoggedUser());

        return users;
    }

    /**
     * Returns all users that are disabled.
     *
     * @return the iterable
     */
    public Iterable<ECourseUser> disabledUsers() {
        return userRepository.findAllDisabledUsers();
    }
}
