package ecourseusermanagement.application;

import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.repositories.IeCourseUserRepository;

import java.util.Optional;

public class UsersProvider implements IUsersProvider{
    private final IeCourseUserRepository userRepository;
    public UsersProvider(IeCourseUserRepository userRepository) {
        if (userRepository == null) {
            throw new IllegalArgumentException("The userRepository cannot be null.");
        }
        this.userRepository = userRepository;
    }

    @Override
    public Optional<ECourseUser> retrieveUserById(Long id) {
        return userRepository.ofIdentity(id);
    }
}
