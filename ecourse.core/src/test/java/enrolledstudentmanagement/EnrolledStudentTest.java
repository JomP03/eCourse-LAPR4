package enrolledstudentmanagement;

import coursemanagement.domain.Course;
import coursemanagement.domain.CourseDataSource;
import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.domain.UserDataSource;
import enrolledstudentmanagement.domain.EnrolledStudent;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class EnrolledStudentTest {

    private ECourseUser user;

    private Course course;


    @BeforeEach
    void clear() {
        user = null;
        course = null;
    }

    /* -- // -- */

    public void setUpValid() {
        // Valid Student
        this.user = UserDataSource.getTestStudent1();

        // Valid Course
        this.course = CourseDataSource.getTestCourse1();

    }

    @Test
    public void ensureValidEnrolledStudentIsCreated() {
        // Arrange
        setUpValid();

        // Act
        final EnrolledStudent enrolledStudent = new EnrolledStudent(this.course, this.user);

        // Assert
        Assertions.assertNotNull(enrolledStudent);
    }

    @Test
    public void ensureValidEnrolledStudentHasSupposedData() {
        // Arrange
        setUpValid();

        // Act
        final EnrolledStudent enrolledStudent = new EnrolledStudent(this.course, this.user);
        String expected = "Course: " + course + " | eCourseUser=" + user;

        // Assert
        Assertions.assertEquals(expected, enrolledStudent.toString());
    }

    @Test
    public void ensureEnrolledStudentEquals() {
        // Arrange
        setUpValid();

        // Act
        final EnrolledStudent enrolledStudent = new EnrolledStudent(this.course, this.user);
        final EnrolledStudent enrolledStudent2 = new EnrolledStudent(this.course, this.user);

        // Assert
        Assertions.assertTrue(enrolledStudent.sameAs(enrolledStudent2));
    }

    @Test
    public void ensureEnrolledStudentHasNullIdWhenCreated() {
        // Arrange
        setUpValid();

        // Act
        final EnrolledStudent enrolledStudent = new EnrolledStudent(this.course, this.user);

        // Assert
        Assertions.assertNull(enrolledStudent.identity());
    }

    /* -- // -- */

    public void setUpInvalidCourse() {
        // Valid Student
        this.user = UserDataSource.getTestStudent1();

    }

    @Test
    public void ensureInvalidCourseEnrolledStudentIsNotCreated() {
        // Arrange
        setUpInvalidCourse();

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> new EnrolledStudent(this.course, this.user));
    }

    /* -- // -- */

    public void setUpInvalidECourseUser() {
        // Valid Course
        this.course = CourseDataSource.getTestCourse1();
    }

    @Test
    public void ensureInvalidECourseUserEnrolledStudentIsNotCreated() {
        // Arrange
        setUpInvalidECourseUser();

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> new EnrolledStudent(this.course, this.user));
    }

    /* -- // -- */

    @Test
    public void ensureInvalidEnrolledStudentIsNotCreated() {
        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> new EnrolledStudent(this.course, this.user));
    }

    /* -- // -- */

}
