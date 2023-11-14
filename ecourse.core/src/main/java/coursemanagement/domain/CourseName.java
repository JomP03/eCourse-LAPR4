package coursemanagement.domain;

import eapli.framework.domain.model.*;
import eapli.framework.strings.util.*;

import javax.persistence.*;

@Embeddable
public class CourseName implements ValueObject {

    private static final long serialVersionUID = 1L;

    @Column
    private String courseName;

    protected CourseName(final String courseName) {

        if (StringPredicates.isNullOrEmpty(courseName)) {
            throw new IllegalArgumentException(
                    "Course Name should neither be null nor empty");
        }

        this.courseName = courseName;
    }

    protected CourseName() {
        // for ORM
    }


    /**
     * @return the courseName as String
     */
    @Override
    public String toString() {
        return this.courseName;
    }

}
