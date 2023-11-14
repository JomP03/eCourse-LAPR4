package enrollmentrequestmanagement;

import coursemanagement.domain.Course;
import coursemanagement.domain.CourseDataSource;
import ecourseusermanagement.domain.*;
import enrollmentrequestmanagement.domain.EnrollmentRequest;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class EnrollmentRequestTest {

    private ECourseUser userStud;

    private Course course;

    @BeforeEach
    void clear() {
        userStud= null;
        course = null;
    }

    /* -- // -- */

    public void setUpValidCourseAndECourseUser() {
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
        final EnrollmentRequest enrollmentRequest = new EnrollmentRequest(this.course, this.userStud);

        // Assert
        Assertions.assertNotNull(enrollmentRequest);
    }

    @Test
    public void ensureValidEnrollmentRequestIsCreatedWithSupposedAuthorAndCourse() {
        // Arrange
        setUpValidCourseAndECourseUser();

        // Act
        final EnrollmentRequest enrollmentRequest = new EnrollmentRequest(this.course, this.userStud);
        String expected =  "Request Author: " + "stu1@gmail.com" + " | Course: " + "EAPLI";

        // Assert
        Assertions.assertEquals(expected, enrollmentRequest.toString());
    }

    @Test
    public void ensureThatTheEnrollmentRequestAuthorIsTheSameAsTheECourseUser() {
        // Arrange
        setUpValidCourseAndECourseUser();

        // Act
        final EnrollmentRequest enrollmentRequest = new EnrollmentRequest(this.course, this.userStud);

        // Assert
        Assertions.assertEquals(this.userStud, enrollmentRequest.author());
    }

    @Test
    public void ensureThatTheEnrollmentRequestCourseIsTheSameAsTheCourse() {
        // Arrange
        setUpValidCourseAndECourseUser();

        // Act
        final EnrollmentRequest enrollmentRequest = new EnrollmentRequest(this.course, this.userStud);

        // Assert
        Assertions.assertEquals(this.course, enrollmentRequest.requestedCourse());
    }

    @Test
    public void ensureThatEnrollmentRequestSameAs() {
        // Arrange
        setUpValidCourseAndECourseUser();

        // Act
        final EnrollmentRequest enrollmentRequest1 = new EnrollmentRequest(this.course, this.userStud);
        final EnrollmentRequest enrollmentRequest2 = new EnrollmentRequest(this.course, this.userStud);

        // Assert
        Assertions.assertTrue(enrollmentRequest1.sameAs(enrollmentRequest2));
    }

    @Test
    public void ensureThatEnrollmentRequestIsCreatedWithNullId() {
        // Arrange
        setUpValidCourseAndECourseUser();

        // Act
        final EnrollmentRequest enrollmentRequest = new EnrollmentRequest(this.course, this.userStud);

        // Assert
        Assertions.assertNull(enrollmentRequest.identity());
    }

    @Test
    public void ensureEnrollmentRequestIsCreatedWithPendingState() {
        // Arrange
        setUpValidCourseAndECourseUser();

        // Act
        final EnrollmentRequest enrollmentRequest = new EnrollmentRequest(this.course, this.userStud);

        // Assert
        Assertions.assertTrue(enrollmentRequest.pendingEnrollmentRequest());
    }

    @Test
    public void ensureEnrollmentRequestIsCreatedWithNoDecisionReason() {
        // Arrange
        setUpValidCourseAndECourseUser();

        // Act
        final EnrollmentRequest enrollmentRequest = new EnrollmentRequest(this.course, this.userStud);

        // Assert
        Assertions.assertNull(enrollmentRequest.verifyDecisionReason());
    }

    @Test
    public void ensureEnrollmentRequestStateCanBecomeApproved() {
        // Arrange
        setUpValidCourseAndECourseUser();

        // Act
        final EnrollmentRequest enrollmentRequest = new EnrollmentRequest(this.course, this.userStud);
        enrollmentRequest.approveEnrollmentRequest("Test Reason");

        // Assert
        Assertions.assertTrue(enrollmentRequest.approvedEnrollmentRequest());
    }

    @Test
    public void ensureEnrollmentRequestStateCanBecomeRejected() {
        // Arrange
        setUpValidCourseAndECourseUser();

        // Act
        final EnrollmentRequest enrollmentRequest = new EnrollmentRequest(this.course, this.userStud);
        enrollmentRequest.rejectEnrollmentRequest("Test Reason");

        // Assert
        Assertions.assertTrue(enrollmentRequest.rejectedEnrollmentRequest());
    }

    @Test
    public void ensureDefaultEnrollmentRequestDecisionReasonAfterApproval() {
        // Arrange
        setUpValidCourseAndECourseUser();

        // Act
        final EnrollmentRequest enrollmentRequest = new EnrollmentRequest(this.course, this.userStud);
        enrollmentRequest.approveEnrollmentRequest(null);

        // Assert
        Assertions.assertEquals("No reason given!", enrollmentRequest.verifyDecisionReason().toString());
    }

    /* -- // -- */

    public void setUpInvalidCourse() {
        // Valid Student
        this.userStud = UserDataSource.getTestStudent1();

        // Invalid Course
        this.course = null;
    }

    @Test
    public void ensureInvalidCourseEnrollmentRequestIsNotCreated() {
        // Arrange
        setUpInvalidCourse();

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> new EnrollmentRequest(this.course, this.userStud));
    }

    /* -- // -- */

    public void setUpInvalidECourseUser() {
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
        Assertions.assertThrows(IllegalArgumentException.class, () -> new EnrollmentRequest(this.course, this.userStud));
    }

    /* -- // -- */

    @Test
    public void ensureInvalidEnrollmentRequestIsNotCreated() {
        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> new EnrollmentRequest(this.course, this.userStud));
    }

    /* -- // -- */

}
