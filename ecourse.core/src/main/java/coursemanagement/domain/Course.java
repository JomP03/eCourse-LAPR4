package coursemanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;
import ecourseusermanagement.domain.ECourseUser;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Course implements AggregateRoot<CourseCode> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private CourseCode courseCode;
    @Embedded
    private CourseName courseName;
    @Embedded
    private CourseDescription courseDescription;
    @Enumerated(EnumType.STRING)
    private CourseState courseState;
    @Embedded
    private MinNrEnrolledStudents minNrEnrolledStudents;
    @Embedded
    private MaxNrEnrolledStudents maxNrEnrolledStudents;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<ECourseUser> courseTeachers;
    @ManyToOne(fetch = FetchType.EAGER)
    private ECourseUser teacherInCharge;


    public Course(final String courseCode, final String courseName, final String courseDescription,
                  final int minNrEnrolledStudents, final int maxNrEnrolledStudents) {
        Preconditions.ensure(maxNrEnrolledStudents > minNrEnrolledStudents,
                "Max number of enrolled students must be greater than min number of enrolled students");

        this.courseCode = new CourseCode(courseCode);
        this.courseName = new CourseName(courseName);
        this.courseDescription = new CourseDescription(courseDescription);
        this.courseState = CourseState.CLOSE;
        this.minNrEnrolledStudents = new MinNrEnrolledStudents(minNrEnrolledStudents);
        this.maxNrEnrolledStudents = new MaxNrEnrolledStudents(maxNrEnrolledStudents);
        courseTeachers = new ArrayList<>();
    }


    /**
     * Checks if the course is close
     *
     * @return true if the course is close
     */
    public boolean isClose() {
        return this.courseState == CourseState.CLOSE;
    }

    /**
     * Changes the course state to OPEN
     */
    public void openCourse() {
        if (this.courseState != CourseState.CLOSE) {
            throw new IllegalStateException("The course is not in a close state. You can't open it.");
        }

        this.courseState = CourseState.OPEN;
    }

    /**
     * Checks if the course is open
     *
     * @return true if the course is open
     */
    public boolean isOpen() {
        return this.courseState == CourseState.OPEN;
    }

    /**
     * Changes the course state to ENROLL
     */
    public void openEnrollments() {
        if (this.courseState != CourseState.OPEN) {
            throw new IllegalStateException("The course is not open. You can't open enrollments.");
        }

        this.courseState = CourseState.ENROLL;
    }

    /**
     * Checks if the course is in enrollment state
     *
     * @return true if the course is in enrollment state
     */
    public boolean areEnrollmentsOpen() {
        return this.courseState == CourseState.ENROLL;
    }

    /**
     * Changes the course state to IN_PROGRESS
     */
    public void closeEnrollments() {
        if (this.courseState != CourseState.ENROLL) {
            throw new IllegalStateException("The course is not in an enroll state. You can't close enrollments.");
        }

        this.courseState = CourseState.IN_PROGRESS;
    }

    /**
     * Checks if the course is in progress state
     *
     * @return true if the course is in progress state
     */
    public boolean isInProgress() {
        return this.courseState == CourseState.IN_PROGRESS;
    }

    /**
     * Changes the course state to CLOSED
     */
    public void closeCourse() {
        if (this.courseState != CourseState.IN_PROGRESS) {
            throw new IllegalStateException("The course is not in progress. You can't close the course.");
        }

        this.courseState = CourseState.CLOSED;
    }

    /**
     * Checks if the course is closed state
     *
     * @return true if the course is closed state
     */
    public boolean isClosed() {
        return this.courseState == CourseState.CLOSED;
    }

    /**
     * Defines the teacher in charge of the course
     *
     * @param teacherInCharge the teacher in charge
     * @throws IllegalArgumentException if the teacher is null or already defined
     */
    public void defineTeacherInCharge(ECourseUser teacherInCharge) {
        if (teacherInCharge == null) {
            throw new IllegalArgumentException("The teacher in charge can't be null.");
        }
        if (this.teacherInCharge != null) {
            throw new IllegalArgumentException("The teacher in charge is already defined.");
        }
        if (courseTeachers.contains(teacherInCharge)) {
            throw new IllegalArgumentException("The teacher is already in the course.");
        }
        if (!teacherInCharge.isTeacher()) {
            throw new IllegalArgumentException("The teacher in charge must be a teacher.");
        }
        this.teacherInCharge = teacherInCharge;
        this.courseTeachers.add(teacherInCharge);
    }

    /**
     * Adds a teacher to the course.
     *
     * @param teacher the teacher
     * @throws IllegalArgumentException if the teacher is null or already in the course
     */
    public void addTeacher(ECourseUser teacher) {
        if (teacher == null) {
            throw new IllegalArgumentException("The teacher can't be null.");
        }
        if (courseTeachers.contains(teacher)) {
            throw new IllegalArgumentException("The teacher is already in the course.");
        }
        if (!teacher.isTeacher()) {
            throw new IllegalArgumentException("Only teachers can be teachers in a course.");
        }
        courseTeachers.add(teacher);
    }

    /**
     * Returns the teacher in charge of the course
     *
     * @return the e course user
     */
    public ECourseUser teacherInCharge() {
        return teacherInCharge;
    }


    /**
     * @return the course teachers
     */
    public List<ECourseUser> teachers() {
        return courseTeachers;
    }


    /**
     * @return the max number of enrolled students
     */
    public Integer maxNrEnrolledStudents() {
        return Integer.valueOf(this.maxNrEnrolledStudents.toString());
    }


    /**
     * @return the min number of enrolled students
     */
    public Integer minNrEnrolledStudents() {
        return Integer.valueOf(this.minNrEnrolledStudents.toString());
    }


    /**
     * @return the course as String
     */
    @Override
    public String toString() {
        return "CourseCode: " + courseCode + " | CourseName: " + courseName + " | CourseState: " + courseState;
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof Course)) {
            return false;
        }

        final Course that = (Course) other;
        if (this.equals(that)) {
            return true;
        }

        return identity().equals(that.identity());
    }

    /**
     * @return the courseCode
     */
    @Override
    public CourseCode identity() {
        return courseCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;

        Course course = (Course) o;

        return courseCode.equals(course.courseCode);
    }

    protected Course() {
        // for ORM only
    }
}
