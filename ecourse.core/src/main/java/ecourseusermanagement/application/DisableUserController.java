package ecourseusermanagement.application;

import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.repositories.IeCourseUserRepository;

public class DisableUserController {

    private final IeCourseUserRepository userRepository;

    /**
     * Instantiates a controller for disabling a user.
     * Receives the user repository as parameter. (DI)
     *
     * @param userRepository the user repository
     */
    public DisableUserController(IeCourseUserRepository userRepository) {
        if (userRepository == null) {
            throw new IllegalArgumentException("User repository must be provided.");
        }
        this.userRepository = userRepository;
    }

    /**
     * Disable user
     *
     * @param email the email of the user to disable
     * @throws IllegalArgumentException if the user is not found
     */
    public void disableUser(String email) {
        ECourseUser userToDisable = userRepository.findByEmail(email).orElseThrow(() ->
                new IllegalArgumentException("User not found!"));
        userToDisable.disable();
        userRepository.save(userToDisable);
    }
}
