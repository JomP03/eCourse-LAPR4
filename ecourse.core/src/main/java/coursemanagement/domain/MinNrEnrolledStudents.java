package coursemanagement.domain;

import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;

@Embeddable
public class MinNrEnrolledStudents {

    private int minNrEnrolledStudents;

    public MinNrEnrolledStudents(int minNrEnrolledStudents) {
        Preconditions.ensure(minNrEnrolledStudents >= 0, "Min number of enrolled students must be greater or equal to 0");

        this.minNrEnrolledStudents = minNrEnrolledStudents;
    }

    protected MinNrEnrolledStudents() {
        // for ORM only
    }


    /**
     * @return the minimum number of enrolled student as String
     */
    @Override
    public String toString() {
        return String.valueOf(this.minNrEnrolledStudents);
    }
}
