package ecourseusermanagement.application.register;

import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.domain.UserBirthdate;
import ecourseusermanagement.domain.UserTaxNumber;
import ecourseusermanagement.domain.ecourseuserbuilders.IeCourseUserBuilderFactory;
import ecourseusermanagement.domain.ecourseuserbuilders.StudentBuilder;
import ecourseusermanagement.repositories.IeCourseUserRepository;
import usermanagement.application.CreateSystemUserController;
import usermanagement.domain.ECourseRoles;

import java.util.Set;


public class RegisterStudentController {

    // Controller for creating a system user
    private final CreateSystemUserController createSystemUserController = new CreateSystemUserController();
    private final IeCourseUserRepository userRepository;
    private final IeCourseUserBuilderFactory userBuilderFactory;


    /**
     * Instantiates a controller for registering a student.
     * Receives the user repository and the user builder factory as parameters. (DI)
     *
     * @param userRepository     the user repository
     * @param userBuilderFactory the user builder factory
     */
    public RegisterStudentController(IeCourseUserRepository userRepository,
                                     IeCourseUserBuilderFactory userBuilderFactory) {
        if (userRepository == null) {
            throw new IllegalArgumentException("User repository must be provided.");
        }
        this.userRepository = userRepository;
        if (userBuilderFactory == null) {
            throw new IllegalArgumentException("User builder factory must be provided.");
        }
        this.userBuilderFactory = userBuilderFactory;
    }

    /**
     * Registers a student.
     *
     * @param username  the username
     * @param pwd       the pwd
     * @param firstName the first name
     * @param lastName  the last name
     * @param email     the email
     * @param birthdate the birthdate
     * @param taxNumber the tax number
     * @return the student
     */
    public ECourseUser registerStudent(String username, String pwd, String firstName, String lastName, String email,
                                       UserBirthdate birthdate, UserTaxNumber taxNumber) throws IllegalArgumentException {
        // Create a system user
        Set<Role> roles = Set.of(ECourseRoles.STUDENT);
        SystemUser systemUser;
        try {
            systemUser = createSystemUserController.addUser(username, pwd, firstName, lastName, email, roles);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        // Create a builder for the student
        StudentBuilder builder = userBuilderFactory.createStudentBuilder();

        // Build the student
        builder.withSystemUser(systemUser)
                .withUserBirthDate(birthdate)
                .withUserTaxNumber(taxNumber);
        ECourseUser student = builder.build();

        // Save the student
        return userRepository.save(student);
    }
}
