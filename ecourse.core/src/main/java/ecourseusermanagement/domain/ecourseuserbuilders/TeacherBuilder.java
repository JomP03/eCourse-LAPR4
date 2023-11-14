package ecourseusermanagement.domain.ecourseuserbuilders;

import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.domain.UserAcronym;
import ecourseusermanagement.domain.UserBirthdate;
import ecourseusermanagement.domain.UserTaxNumber;

public class TeacherBuilder implements IeCourseUserBuilder {

    private SystemUser systemUser;
    private UserTaxNumber userTaxNumber;
    private UserBirthdate userBirthdate;
    private UserAcronym userAcronym;


    @Override
    public TeacherBuilder withSystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
        return this;
    }

    /**
     * Add user tax number to the teacher builder.
     *
     * @param userTaxNumber the user tax number
     * @return the student builder
     */
    public TeacherBuilder withUserTaxNumber(UserTaxNumber userTaxNumber) {
        this.userTaxNumber = userTaxNumber;
        return this;
    }

    /**
     * Add user birthdate to the teacher builder.
     *
     * @param userBirthDate the user birthdate
     * @return the student builder
     */
    public TeacherBuilder withUserBirthDate(UserBirthdate userBirthDate) {
        this.userBirthdate = userBirthDate;
        return this;
    }

    /**
     * Add user acronym to the teacher builder.
     *
     * @param userAcronym the user acronym
     * @return the teacher builder
     */
    public TeacherBuilder withUserAcronym(UserAcronym userAcronym) {
        this.userAcronym = userAcronym;
        return this;
    }

    /**
     * Build the "Teacher".
     *
     * @return the eCourseUser
     */
    @Override
    public ECourseUser build() {
        return new ECourseUser(systemUser, userTaxNumber, userBirthdate, userAcronym);
    }
}
