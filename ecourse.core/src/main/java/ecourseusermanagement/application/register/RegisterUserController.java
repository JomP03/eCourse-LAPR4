package ecourseusermanagement.application.register;

import eapli.framework.infrastructure.authz.domain.model.Role;
import usermanagement.domain.ECourseRoles;

public class RegisterUserController
{

    /**
     * Instantiates the common controller for all the use cases related to the registration of users
     *
     */
    public RegisterUserController()
    {
    }

    /**
     * Method that returns the existing roles (only domain roles)
     *
     * @return the role array
     */
    public Role[] existingRoles()
    {
        return ECourseRoles.existingRoles();
    }
}
