package enrollmentrequestmanagement.domain;

import coursemanagement.domain.Course;
import ecourseusermanagement.domain.ECourseUser;
import enrollmentrequestmanagement.application.CourseEnrollmentService;
import enrollmentrequestmanagement.repository.EnrollmentRequestRepository;

public class EnrollmentRequestBuilder {

    final EnrollmentRequestRepository enrollmentRequestRepository;

    final CourseEnrollmentService courseEnrollmentService;

    private Course course;

    private ECourseUser eCourseUser;

    /**
     * Instantiates a new Enrollment request builder.
     *
     * @param enrollmentRequestRepository the enrollment request repository
     */
    public EnrollmentRequestBuilder(EnrollmentRequestRepository enrollmentRequestRepository, CourseEnrollmentService courseEnrollmentService) {
        // verify if the enrollmentRequestRepository is null
        if (enrollmentRequestRepository == null) {
            throw new IllegalArgumentException("The repo cannot be null.");
        }

        this.enrollmentRequestRepository = enrollmentRequestRepository;

        // verify if the courseEnrollmentService is null
        if (courseEnrollmentService == null) {
            throw new IllegalArgumentException("The courseEnrollmentService cannot be null.");
        }

        this.courseEnrollmentService = courseEnrollmentService;
    }

    /**
     * With course enrollment request builder.
     *
     * @param course the selected course
     * @return the enrollment request builder
     */
    public EnrollmentRequestBuilder withCourse(Course course) {
        this.course = course;
        return this;
    }

    /**
     * With eCourse user enrollment request builder.
     *
     * @param eCourseUser the e course user
     * @return the enrollment request builder
     */
    public EnrollmentRequestBuilder withECourseUser(ECourseUser eCourseUser) {
        this.eCourseUser = eCourseUser;
        return this;
    }

    public EnrollmentRequest build() {
        // verify if there is already an enrollment request for the selected course and eCourse user
        if (courseEnrollmentService.findByCourseECourseUser(this.course, this.eCourseUser).isPresent())
            throw new IllegalArgumentException("Enrollment request already exists");

        return new EnrollmentRequest(this.course, this.eCourseUser);
    }

}
