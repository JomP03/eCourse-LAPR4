package ecourseusermanagement.application.register;

import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.domain.UserAcronym;
import ecourseusermanagement.domain.UserBirthdate;
import ecourseusermanagement.domain.UserTaxNumber;
import ecourseusermanagement.domain.ecourseuserbuilders.IeCourseUserBuilderFactory;
import ecourseusermanagement.domain.ecourseuserbuilders.TeacherBuilder;
import ecourseusermanagement.repositories.IeCourseUserRepository;
import usermanagement.application.CreateSystemUserController;
import usermanagement.domain.ECourseRoles;

import java.util.Set;

public class RegisterTeacherController {

    // Controller for creating a system user
    private final CreateSystemUserController createSystemUserController = new CreateSystemUserController();
    private final IeCourseUserRepository userRepository;
    private final IeCourseUserBuilderFactory userBuilderFactory;

    /**
     * Instantiates a controller for registering a teacher.
     * Receives the user repository and the user builder factory as parameters. (DI)
     *
     * @param userRepository     the user repository
     * @param userBuilderFactory the user builder factory
     */
    public RegisterTeacherController(IeCourseUserRepository userRepository,
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
     * Registers a teacher.
     *
     * @param username  the username
     * @param pwd       the pwd
     * @param firstName the first name
     * @param lastName  the last name
     * @param email     the email
     * @param acronym   the acronym
     * @param birthdate the birthdate
     * @param taxNumber the tax number
     * @return the e course user
     */
    public ECourseUser registerTeacher(String username, String pwd, String firstName, String lastName, String email,
                                       UserAcronym acronym, UserBirthdate birthdate, UserTaxNumber taxNumber) {
        // Create a system user
        Set<Role> roles = Set.of(ECourseRoles.TEACHER);
        SystemUser systemUser;
        try {
            systemUser = createSystemUserController.addUser(username, pwd, firstName, lastName, email, roles);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        // Create a builder for the teacher
        TeacherBuilder builder = userBuilderFactory.createTeacherBuilder();

        // Build the teacher
        builder.withSystemUser(systemUser)
                .withUserAcronym(acronym)
                .withUserBirthDate(birthdate)
                .withUserTaxNumber(taxNumber);
        ECourseUser teacher = builder.build();

        // Save the teacher
        return userRepository.save(teacher);
    }


}
