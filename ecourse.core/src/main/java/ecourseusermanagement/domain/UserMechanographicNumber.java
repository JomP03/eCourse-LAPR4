package ecourseusermanagement.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
public class UserMechanographicNumber implements ValueObject {

    // For serialization purposes
    private static final long serialVersionUID = 1L;

    // Regex to validate the mechanographic number: YYYYNNNNN, where YYYY is the current year and NNNNN a number between 00001 and 99999
    private static final String REGEX_MECHANOGRAPHIC_NUMBER = "^[0-9]{4}[0-9]{5}$";

    @Column(unique = true)
    private String mechanographicNumber;

    /**
     * Instantiates a new User mechanographic number.
     *
     * @param mechanographicNumber the mechanographic number
     *
     * @throws IllegalArgumentException if the mechanographic number is null or empty
     * @throws IllegalArgumentException if the mechanographic number doesn't respect the pattern
     * @throws IllegalArgumentException if the mechanographic number is not from the current year
     */
    public UserMechanographicNumber(final String mechanographicNumber) {

        // Check if the mechanographic number is null or empty
        if (mechanographicNumber == null || mechanographicNumber.isEmpty()) {
            throw new IllegalArgumentException(
                    "A mechanographic number must be provided!");
        }

        // Check if the mechanographic number respects the pattern
        if (!mechanographicNumber.matches(REGEX_MECHANOGRAPHIC_NUMBER)) {
            throw new IllegalArgumentException(
                    "The mechanographic number must be in the format YYYYNNNNN, where YYYY is be the current year and NNNNN a number between 00001 and 99999!");
        }

        // Check if the mechanographic number is from the current year
        if (!mechanographicNumber.substring(0, 4).equals(String.valueOf(LocalDate.now().getYear()))) {
            throw new IllegalArgumentException(
                    "The mechanographic number must be from the current year!");
        }

        this.mechanographicNumber = mechanographicNumber;
    }

    protected UserMechanographicNumber() {
        // for ORM purposes
    }

    @Override
    public String toString() {
        return this.mechanographicNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserMechanographicNumber)) return false;

        UserMechanographicNumber that = (UserMechanographicNumber) o;

        return mechanographicNumber.equals(that.mechanographicNumber);
    }
}
