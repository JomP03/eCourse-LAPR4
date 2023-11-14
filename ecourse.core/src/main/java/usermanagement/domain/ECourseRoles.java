package usermanagement.domain;

import eapli.framework.infrastructure.authz.domain.model.Role;

/**
 * The type E course roles.
 */
public class ECourseRoles {

    public static final Role SUPERUSER = Role.valueOf("SUPER_USER");
    public static final Role STUDENT = Role.valueOf("STUDENT");
    public static final Role TEACHER = Role.valueOf("TEACHER");
    public static final Role MANAGER = Role.valueOf("MANAGER");


    /**
     * Method that returns the existing roles (only domain roles)
     *
     * @return the role array
     */
    public static Role[] existingRoles() {
        return new Role[]{STUDENT, TEACHER, MANAGER};
    }

}
