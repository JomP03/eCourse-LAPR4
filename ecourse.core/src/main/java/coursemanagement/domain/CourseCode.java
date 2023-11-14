package coursemanagement.domain;

import eapli.framework.domain.model.*;
import eapli.framework.strings.util.*;

import javax.persistence.*;

@Embeddable
public class CourseCode implements ValueObject, Comparable<CourseCode> {

    private static final long serialVersionUID = 1L;

    @Column(unique = true)
    private String courseCode;

    protected CourseCode(final String courseCode) {
        if (StringPredicates.isNullOrEmpty(courseCode)) {
            throw new IllegalArgumentException(
                    "Course Code should neither be null nor empty");
        }

        this.courseCode = courseCode;
    }

    protected CourseCode() {
        // for ORM
    }


    /**
     * Transform a String into a Course Code
     *
     * @param courseCode code to create
     * @return New Course Code if valid
     */
    public static CourseCode valueOf(String courseCode) {
        return new CourseCode(courseCode);
    }


    /**
     * @return the courseCode as String
     */
    @Override
    public String toString() {
        return this.courseCode;
    }


    /**
     * @param o the courseCode to compare
     * @return the comparison result
     */
    @Override
    public int compareTo(CourseCode o) {
        return courseCode.compareTo(o.courseCode);
    }


    /**
     * @param o the courseCode to compare
     * @return the comparison result
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CourseCode that = (CourseCode) o;

        return courseCode.equals(that.courseCode);
    }
}
