package ecourseusermanagement.domain.ecourseuserbuilders;

import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import ecourseusermanagement.domain.*;

/**
 * The type Student builder.
 */
public class StudentBuilder implements IeCourseUserBuilder{

    private SystemUser systemUser;
    private UserTaxNumber userTaxNumber;
    private UserBirthdate userBirthdate;

    private final IMechanographicNumberAssigner mechanographicNumberAssigner;

    /**
     * Instantiates a new Student builder.
     * Receives the mechanographic number assigner as a dependency (DI pattern)
     *
     * @param mechanographicNumberAssigner the mechanographic number assigner
     */
    public StudentBuilder(IMechanographicNumberAssigner mechanographicNumberAssigner) {
        if (mechanographicNumberAssigner == null) {
            throw new IllegalArgumentException("Mechanographic number assigner must be provided!");
        }
        this.mechanographicNumberAssigner = mechanographicNumberAssigner;
    }


    @Override
    public StudentBuilder withSystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
        return this;
    }

    /**
     * Add user tax number to the student builder.
     *
     * @param userTaxNumber the user tax number
     * @return the student builder
     */
    public StudentBuilder withUserTaxNumber(UserTaxNumber userTaxNumber) {
        this.userTaxNumber = userTaxNumber;
        return this;
    }

    /**
     * Add user birthdate to the student builder.
     *
     * @param userBirthDate the user birthdate
     * @return the student builder
     */
    public StudentBuilder withUserBirthDate(UserBirthdate userBirthDate) {
        this.userBirthdate = userBirthDate;
        return this;
    }


    /**
     * Build the "Student".
     *
     * @return the eCourseUser
     */
    @Override
    public ECourseUser build() {
        // Ask the mechanographic number assigner for a new mechanographic number
        UserMechanographicNumber userMechanographicNumber = this.mechanographicNumberAssigner.newMechanographicNumber();

        // Return the student
        return new ECourseUser(this.systemUser, this.userTaxNumber, this.userBirthdate, userMechanographicNumber);
    }
}
