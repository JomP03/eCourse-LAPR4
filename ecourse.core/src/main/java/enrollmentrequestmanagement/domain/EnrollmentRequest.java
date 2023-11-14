package enrollmentrequestmanagement.domain;

import coursemanagement.domain.Course;
import eapli.framework.domain.model.AggregateRoot;
import ecourseusermanagement.domain.ECourseUser;

import javax.persistence.*;

@Entity
public class EnrollmentRequest implements AggregateRoot<Long> {

    // For ORM serialization
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Course course;

    @OneToOne
    private ECourseUser eCourseUser;

    @Enumerated(EnumType.STRING)
    private EnrollmentRequestState enrollmentRequestState;

    @Embedded
    private EnrollmentDecisionReason enrollmentDecisionReason;

    protected EnrollmentRequest() {
        // for ORM
    }

    /**
     * Instantiates a new Enrollment request.
     *
     * @param course the course
     * @param eCourseUser the eCourse user
     */
    public EnrollmentRequest(final Course course, final ECourseUser eCourseUser) {

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

        this.enrollmentRequestState = EnrollmentRequestState.PENDING;

    }

    /**
     * Enrollment request author.
     *
     * @return the eCourse user
     */
    public ECourseUser author() {
        return this.eCourseUser;
    }

    /**
     * Enrollment request selected course.
     *
     * @return the course
     */
    public Course requestedCourse() {
        return this.course;
    }

    /**
     * Was enrollment request approved?
     *
     * @return the boolean
     */
    public boolean approvedEnrollmentRequest() {
        return this.enrollmentRequestState == EnrollmentRequestState.APPROVED;
    }

    /**
     * Was enrollment request rejected?
     *
     * @return the boolean
     */
    public boolean rejectedEnrollmentRequest() {
        return this.enrollmentRequestState == EnrollmentRequestState.REJECTED;
    }

    /**
     * Is enrollment request still pending?
     *
     * @return the boolean
     */
    public boolean pendingEnrollmentRequest() {
        return this.enrollmentRequestState == EnrollmentRequestState.PENDING;
    }

    /**
     * Approve enrollment request and define the enrollment decision reason.
     *
     * @throws IllegalStateException if enrollment request is not pending
     */
    public void approveEnrollmentRequest(String decisionReason) {
        if (!this.pendingEnrollmentRequest()) {
            throw new IllegalStateException("Enrollment request is not pending");
        }

        this.enrollmentRequestState = EnrollmentRequestState.APPROVED;

        this.enrollmentDecisionReason = new EnrollmentDecisionReason(decisionReason);
    }

    /**
     * Reject enrollment request and define the enrollment decision reason.
     *
     * @throws IllegalStateException if enrollment request is not pending
     */
    public void rejectEnrollmentRequest(String decisionReason) {
        if (!this.pendingEnrollmentRequest()) {
            throw new IllegalStateException("Enrollment request is not pending");
        }

        this.enrollmentRequestState = EnrollmentRequestState.REJECTED;

        this.enrollmentDecisionReason = new EnrollmentDecisionReason(decisionReason);
    }

    /**
     * Verify decision reason that lead to approval or rejection.
     *
     * @return the enrollment decision reason
     */
    public EnrollmentDecisionReason verifyDecisionReason() {
        return this.enrollmentDecisionReason;
    }

    @Override
    public String toString() {
        return "Request Author: " + eCourseUser.email() +
                " | Course: " + course.identity();
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof EnrollmentRequest)) {
            return false;
        }

        final EnrollmentRequest that = (EnrollmentRequest) other;

        return this.course.sameAs(that.course) && this.eCourseUser.sameAs(that.eCourseUser);
    }

    @Override
    public Long identity() {
        return this.id;
    }
}
