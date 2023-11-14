package ecourseusermanagement.domain;

import appsettings.Application;
import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.util.StringPredicates;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserAcronym implements ValueObject, Comparable<UserAcronym> {

    // For serialization purposes
    private static final long serialVersionUID = 1L;

    @Column(unique = true)
    private String acronym;

    private static final int MAX_LENGTH = Application.settings().getMaxTeacherAcronymLength();

    /**
     * Instantiates a new User acronym.
     *
     * @param acronym the acronym
     *
     * @throws IllegalArgumentException if the acronym is null or empty
     * @throws IllegalArgumentException if the acronym contains any character that is not a capital letter
     * @throws IllegalArgumentException if the acronym is longer than the maximum length
     */
    public UserAcronym(final String acronym) {
        // Check if the acronym is null or empty
        if (StringPredicates.isNullOrEmpty(acronym)) {
            throw new IllegalArgumentException(
                    "An Acronym must be provided!");
        }

        // Check if the acronym contains any character that is not a capital letter
        if (!acronym.matches("[A-Z]+")) {
            throw new IllegalArgumentException(
                    "Acronym can only contain capital letters");
        }

        // Check if the acronym is longer than the maximum length
        if (acronym.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "Acronym cant be longer than " + MAX_LENGTH + " characters");
        }

        this.acronym = acronym;
    }

    protected UserAcronym() {
        // for ORM purposes
    }

    @Override
    public String toString() {
        return this.acronym;
    }

    @Override
    public int compareTo(UserAcronym o) {
        return acronym.compareTo(o.acronym);
    }
}
