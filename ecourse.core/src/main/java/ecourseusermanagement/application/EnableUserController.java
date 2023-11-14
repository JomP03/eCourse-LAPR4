package ecourseusermanagement.application;

import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.repositories.IeCourseUserRepository;

public class EnableUserController {

    private final IeCourseUserRepository userRepository;

    /**
     * Instantiates a controller for enabling a user.
     * Receives the user repository as parameter. (DI)
     *
     * @param userRepository the user repository
     */
    public EnableUserController(IeCourseUserRepository userRepository) {
        if (userRepository == null) {
            throw new IllegalArgumentException("User repository must be provided.");
        }
        this.userRepository = userRepository;
    }

    /**
     * Enable user
     *
     * @param email the email of the user to enable
     * @throws IllegalArgumentException if the user is not found
     */
    public void enableUser(String email) {
        ECourseUser userToEnable = userRepository.findByEmail(email).orElseThrow(() ->
                new IllegalArgumentException("User not found!"));
        userToEnable.enable();
        userRepository.save(userToEnable);
    }
}
