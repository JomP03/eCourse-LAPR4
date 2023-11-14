package enrolledstudentmanagement;

import coursemanagement.domain.Course;
import coursemanagement.domain.CourseDataSource;
import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.domain.UserDataSource;
import enrolledstudentmanagement.domain.EnrolledStudent;
import enrolledstudentmanagement.domain.EnrolledStudentBuilder;
import enrolledstudentmanagement.repository.EnrolledStudentRepository;
import enrollmentrequestmanagement.domain.EnrollmentRequest;
import enrollmentrequestmanagement.repository.EnrollmentRequestRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EnrolledStudentBuilderTest {

    private EnrolledStudentRepository enrolledStudentRepo;

    private EnrollmentRequestRepository enrollmentRequestRepo;

    private ECourseUser user;

    private Course course;

    private EnrollmentRequest enrollmentRequest;

    private EnrolledStudent enrolledStudent;

    @BeforeEach
    void clear() {
        enrolledStudentRepo = null;
        enrollmentRequestRepo = null;
        user = null;
        course = null;
        enrollmentRequest = null;
        enrolledStudent = null;
    }

    public void setUpValid() {
        // Valid Repositories
        this.enrolledStudentRepo = mock(EnrolledStudentRepository.class);

        this.enrollmentRequestRepo = mock(EnrollmentRequestRepository.class);

        // Valid Student
        this.user = UserDataSource.getTestStudent1();

        // Valid Course
        this.course = CourseDataSource.getTestCourse1();

        // Valid Enrollment Request
        this.enrollmentRequest = new EnrollmentRequest(course, user);

        // "Save" the Enrollment Request
        when(enrollmentRequestRepo.findByCourseECourseUser(course, user)).thenReturn(Optional.of(enrollmentRequest));
    }

    @Test
    public void ensureValidEnrolledStudentIsCreatedByTheBuilder() {
        // Arrange
        setUpValid();

        // Act
        final EnrolledStudent enrolledStudentTest =
                new EnrolledStudentBuilder(enrolledStudentRepo)
                        .withECourseUser(user)
                        .withCourse(course)
                        .build();

        // Assert
        Assertions.assertNotNull(enrolledStudentTest);
    }


    public void setUpValidButStudentIsAlreadyEnrolled() {
        // Valid Repositories
        this.enrolledStudentRepo = mock(EnrolledStudentRepository.class);

        this.enrollmentRequestRepo = mock(EnrollmentRequestRepository.class);

        // Valid Student
        this.user = UserDataSource.getTestStudent1();

        // Valid Course
        this.course = CourseDataSource.getTestCourse1();

        // Valid Enrolled Student
        enrolledStudent = new EnrolledStudent(course, user);

        // "Save" the Enrolled Student
        when(enrolledStudentRepo.isStudentAlreadyEnrolled(course, user)).thenReturn(true);
    }

    @Test
    public void ensureValidEnrolledStudentIsNotCreatedByTheBuilderWhenStudentIsAlreadyEnrolled() {
        // Arrange
        setUpValidButStudentIsAlreadyEnrolled();

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () -> new EnrolledStudentBuilder(enrolledStudentRepo)
                .withECourseUser(user)
                .withCourse(course)
                .build());
    }

}
