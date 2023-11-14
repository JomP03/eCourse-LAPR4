package ecourseusermanagement.domain.ecourseuserbuilders;

import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import ecourseusermanagement.domain.ECourseUser;

public class ManagerBuilder implements IeCourseUserBuilder {

    private SystemUser systemUser;

    @Override
    public ManagerBuilder withSystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
        return this;
    }

    /**
     * Build the "Manager".
     *
     * @return the eCourseUser
     */
    @Override
    public ECourseUser build() {
        return new ECourseUser(this.systemUser);
    }
}
