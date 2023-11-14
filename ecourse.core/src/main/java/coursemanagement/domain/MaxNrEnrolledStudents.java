package coursemanagement.domain;

import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;

@Embeddable
public class MaxNrEnrolledStudents {

    private int maxNrEnrolledStudents;

    public MaxNrEnrolledStudents(int maxNrEnrolledStudents) {
        Preconditions.ensure(maxNrEnrolledStudents > 0, "Max number of enrolled students must be greater than 0");

        this.maxNrEnrolledStudents = maxNrEnrolledStudents;
    }

    protected MaxNrEnrolledStudents() {
        // for ORM only
    }


    /**
     * @return the maximum number of enrolled student as String
     */
    @Override
    public String toString() {
        return String.valueOf(this.maxNrEnrolledStudents);
    }
}
