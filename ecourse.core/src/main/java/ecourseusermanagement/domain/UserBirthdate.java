package ecourseusermanagement.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
public class UserBirthdate implements ValueObject {

    // For serialization purposes
    private static final long serialVersionUID = 1L;

    private static final int MAX_AGE = 115;

    @Column
    private LocalDate birthdate;

    /**
     * Instantiates a new User birthdate.
     *
     * @param birthdate the birthdate
     *
     * @throws IllegalArgumentException if the birthdate is null
     * @throws IllegalArgumentException if the birthdate is in the future
     * @throws IllegalArgumentException if the user is too old
     */
    public UserBirthdate(final LocalDate birthdate) {

        // Check if the birthdate is null
        if (birthdate == null) {
            throw new IllegalArgumentException(
                    "A Birthdate must be provided!");
        }

        // Check if the birthdate is in the future
        if (birthdate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "A user can't be born in the future!");
        }

        // Check if the user is more than max age years old
        if (birthdate.isBefore(LocalDate.now().minusYears(MAX_AGE))) {
            throw new IllegalArgumentException(
                    "We're sorry, but it seems the user is too old to be registered in our system.");
        }

        this.birthdate = birthdate;
    }

    protected UserBirthdate() {
        // for ORM purposes
    }

    @Override
    public String toString() {
        return this.birthdate.toString();
    }

}
