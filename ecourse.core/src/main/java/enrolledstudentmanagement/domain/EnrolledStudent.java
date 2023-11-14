package enrolledstudentmanagement.domain;

import coursemanagement.domain.Course;
import eapli.framework.domain.model.AggregateRoot;
import ecourseusermanagement.domain.ECourseUser;

import javax.persistence.*;

@Entity
public class EnrolledStudent implements AggregateRoot<Long> {

    // For ORM serialization
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Course course;

    @OneToOne
    private ECourseUser eCourseUser;

    protected EnrolledStudent() {
        // for ORM
    }

    /**
     * Instantiates a new Enrolled student.
     *
     * @param course      the course
     * @param eCourseUser the eCourse user
     */
    public EnrolledStudent(final Course course, final ECourseUser eCourseUser) {

        // verify if the course is not null
        if (course == null) {
            throw new IllegalArgumentException("Course can't be null");
        }

        this.course = course;

        // verify if the eCourseUser is not null
        if (eCourseUser == null) {
            throw new IllegalArgumentException("ECourseUser can't be null");
        }

        this.eCourseUser = eCourseUser;

    }

    @Override
    public String toString() {
        return "Course: " + course +
                " | eCourseUser=" + eCourseUser;
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof EnrolledStudent)) {
            return false;
        }

        final EnrolledStudent that = (EnrolledStudent) other;

        return this.course.sameAs(that.course) && this.eCourseUser.sameAs(that.eCourseUser);
    }

    @Override
    public Long identity() {
        return this.id;
    }
}
