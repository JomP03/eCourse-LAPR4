package ecourseusermanagement.application.register;

import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.domain.ecourseuserbuilders.IeCourseUserBuilderFactory;
import ecourseusermanagement.domain.ecourseuserbuilders.ManagerBuilder;
import ecourseusermanagement.repositories.IeCourseUserRepository;
import usermanagement.application.CreateSystemUserController;
import usermanagement.domain.ECourseRoles;

import java.util.Set;

public class RegisterManagerController {

    // Controller for creating a system user
    private final CreateSystemUserController createSystemUserController = new CreateSystemUserController();
    private final IeCourseUserRepository userRepository;
    private final IeCourseUserBuilderFactory userBuilderFactory;

    /**
     * Instantiates a controller for registering a manager.
     * Receives the user repository and the user builder factory as parameters. (DI)
     *
     * @param userRepository     the user repository
     * @param userBuilderFactory the user builder factory
     */
    public RegisterManagerController(IeCourseUserRepository userRepository,
                                     IeCourseUserBuilderFactory userBuilderFactory) {
        if ( userRepository == null ) {
            throw new IllegalArgumentException("User repository must be provided.");
        }
        this.userRepository = userRepository;
        if ( userBuilderFactory == null ) {
            throw new IllegalArgumentException("User builder factory must be provided.");
        }
        this.userBuilderFactory = userBuilderFactory;
    }

    /**
     * Registers a manager.
     *
     * @param username  the username
     * @param pwd       the pwd
     * @param firstName the first name
     * @param lastName  the last name
     * @param email     the email
     * @return the manager
     */
    public ECourseUser registerManager(String username, String pwd, String firstName, String lastName, String email) {
        // Create a system user
        Set<Role> roles = Set.of(ECourseRoles.MANAGER);
        SystemUser systemUser;
        try {
            systemUser = createSystemUserController.addUser(username, pwd, firstName, lastName, email, roles);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        // Create a builder for the manager
        ManagerBuilder builder = userBuilderFactory.createManagerBuilder();

        // Build the manager
        builder.withSystemUser(systemUser);
        ECourseUser manager = builder.build();

        // Save the manager
        return userRepository.save(manager);
    }

}
