package ecourseusermanagement.domain.ecourseuserbuilders;

import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import ecourseusermanagement.domain.ECourseUser;

/**
 * The interface Ie course user builder.
 */
public interface IeCourseUserBuilder {

    /**
     * Add system user to the builder.
     *
     * @param systemUser the system user
     * @return the ie course user builder
     */
    IeCourseUserBuilder withSystemUser(SystemUser systemUser);


    /**
     * Build the ECourseUser.
     *
     * @return the eCourseUser
     */
    ECourseUser build();
}
