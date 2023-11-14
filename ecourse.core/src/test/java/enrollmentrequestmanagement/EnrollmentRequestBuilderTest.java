package enrollmentrequestmanagement;

import coursemanagement.domain.Course;
import coursemanagement.domain.CourseDataSource;
import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.domain.UserDataSource;
import enrolledstudentmanagement.repository.EnrolledStudentRepository;
import enrollmentrequestmanagement.application.CourseEnrollmentService;
import enrollmentrequestmanagement.domain.EnrollmentRequest;
import enrollmentrequestmanagement.domain.EnrollmentRequestBuilder;
import enrollmentrequestmanagement.repository.EnrollmentRequestRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EnrollmentRequestBuilderTest {

    private EnrollmentRequestRepository enrollmentRequestRepo;

    private CourseEnrollmentService courseEnrollmentService;

    private EnrolledStudentRepository enrolledStudentRepo;

    private ECourseUser userStud;

    private Course course;

    @BeforeEach
    void clear() {
        enrollmentRequestRepo = null;
        courseEnrollmentService = null;
        enrolledStudentRepo = null;
        userStud= null;
        course = null;
    }

    /* -- // -- */

    public void setUpValidCourseAndECourseUser() {
        // Valid Repositories
        this.enrollmentRequestRepo = mock(EnrollmentRequestRepository.class);

        this.enrolledStudentRepo = mock(EnrolledStudentRepository.class);

        // Valid Service
        this.courseEnrollmentService = new CourseEnrollmentService(this.enrollmentRequestRepo,
                enrolledStudentRepo, null, null);

        // Valid Student
        this.userStud = UserDataSource.getTestStudent1();

        // Valid Course
        this.course = CourseDataSource.getTestCourse1();
    }

    @Test
    public void ensureValidEnrollmentRequestIsCreated() {
        // Arrange
        setUpValidCourseAndECourseUser();

        // Act
        final EnrollmentRequest enrollmentRequest = new EnrollmentRequestBuilder(this.enrollmentRequestRepo,
                this.courseEnrollmentService)
                .withECourseUser(this.userStud)
                .withCourse(this.course)
                .build();

        // Assert
        Assertions.assertNotNull(enrollmentRequest);
    }

    /* -- // -- */

    public void setUpValidCourseAndECourseUserButDuplicateRequest() {
        // Valid Repositories
        this.enrollmentRequestRepo = mock(EnrollmentRequestRepository.class);

        this.enrolledStudentRepo = mock(EnrolledStudentRepository.class);

        // Valid Service
        this.courseEnrollmentService = new CourseEnrollmentService(this.enrollmentRequestRepo,
                enrolledStudentRepo, null, null);

        // Invalid Student
        this.userStud = UserDataSource.getTestStudent1();

        // Valid Course
        this.course = CourseDataSource.getTestCourse1();

        // Duplicate Request
        EnrollmentRequest enrollmentRequest = new EnrollmentRequestBuilder(this.enrollmentRequestRepo,
                this.courseEnrollmentService)
                .withECourseUser(this.userStud)
                .withCourse(this.course)
                .build();

        // Add Duplicate Request
        when(this.courseEnrollmentService.findByCourseECourseUser(this.course, this.userStud))
                .thenReturn(Optional.ofNullable(enrollmentRequest));
    }

    @Test
    public void ensureValidEnrollmentRequestIsNotCreatedDueToDuplicateRequest() {
        // Arrange
        setUpValidCourseAndECourseUserButDuplicateRequest();

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> new EnrollmentRequestBuilder(
                this.enrollmentRequestRepo,
                this.courseEnrollmentService)
                .withECourseUser(this.userStud)
                .withCourse(this.course)
                .build());
    }

    /* -- // -- */

    public void setUpInvalidCourse() {
        // Valid Repositories
        this.enrollmentRequestRepo = mock(EnrollmentRequestRepository.class);

        this.enrolledStudentRepo = mock(EnrolledStudentRepository.class);

        // Valid Service
        this.courseEnrollmentService = new CourseEnrollmentService(this.enrollmentRequestRepo,
                enrolledStudentRepo, null, null);

        // Invalid Student
        this.userStud = UserDataSource.getTestStudent1();

        // Invalid Course
        this.course = null;
    }

    @Test
    public void ensureInvalidCourseEnrollmentRequestIsNotCreated() {
        // Arrange
        setUpInvalidCourse();

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> new EnrollmentRequestBuilder(
                this.enrollmentRequestRepo,
                this.courseEnrollmentService)
                .withECourseUser(this.userStud)
                .withCourse(this.course)
                .build());
    }

    /* -- // -- */

    public void setUpInvalidECourseUser() {
        // Valid Repositories
        this.enrollmentRequestRepo = mock(EnrollmentRequestRepository.class);

        this.enrolledStudentRepo = mock(EnrolledStudentRepository.class);

        // Valid Service
        this.courseEnrollmentService = new CourseEnrollmentService(this.enrollmentRequestRepo,
                enrolledStudentRepo, null, null);

        // Invalid Student
        this.userStud = null;

        // Valid Course
        this.course = CourseDataSource.getTestCourse1();
    }

    @Test
    public void ensureInvalidECourseUserEnrollmentRequestIsNotCreated() {
        // Arrange
        setUpInvalidECourseUser();

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> new EnrollmentRequestBuilder(
                this.enrollmentRequestRepo,
                this.courseEnrollmentService)
                .withECourseUser(this.userStud)
                .withCourse(this.course)
                .build());
    }

}
