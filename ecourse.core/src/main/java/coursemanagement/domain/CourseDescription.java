package coursemanagement.domain;

import eapli.framework.domain.model.*;
import eapli.framework.strings.util.*;

import javax.persistence.*;

@Embeddable
public class CourseDescription implements ValueObject {

    private static final long serialVersionUID = 1L;

    @Column
    private String courseDescription;

    protected CourseDescription(final String courseDescription) {

        if (StringPredicates.isNullOrEmpty(courseDescription)) {
            throw new IllegalArgumentException(
                    "Course Description should neither be null nor empty");
        }

        if (StringPredicates.isSingleWord(courseDescription)) {
            throw new IllegalArgumentException(
                    "Course Description should be a sentence");
        }

        this.courseDescription = courseDescription;
    }

    protected CourseDescription() {
        // for ORM
    }


    /**
     * @return the courseDescription as String
     */
    @Override
    public String toString() {
        return this.courseDescription;
    }
}
