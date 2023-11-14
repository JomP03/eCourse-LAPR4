package enrollmentrequestmanagement.application.register;

import coursemanagement.application.coursestate.ListCourseByStateService;
import coursemanagement.domain.Course;
import ecourseusermanagement.application.UserSessionService;
import ecourseusermanagement.domain.ECourseUser;
import enrolledstudentmanagement.application.ListCourseStudentsController;
import enrollmentrequestmanagement.application.CourseEnrollmentService;
import enrollmentrequestmanagement.domain.EnrollmentRequest;
import enrollmentrequestmanagement.domain.EnrollmentRequestBuilder;
import enrollmentrequestmanagement.repository.EnrollmentRequestRepository;
import persistence.PersistenceContext;

import java.util.Optional;

public class RegisterEnrollmentRequestController {

    private final EnrollmentRequestRepository enrollmentRequestRepository;

    private final UserSessionService userSessionService;

    private final CourseEnrollmentService courseEnrollmentService;

    private ECourseUser student;

    /**
     * Instantiates a new Enrollment request controller.
     *
     * @param enrollmentRequestRepository the enrollment request repository
     */
    public RegisterEnrollmentRequestController(EnrollmentRequestRepository enrollmentRequestRepository) {
        // verify if the enrollmentRequestRepository is null
        if (enrollmentRequestRepository == null) {
            throw new IllegalArgumentException("The enrollmentRequestRepository cannot be null.");
        }

        this.enrollmentRequestRepository = enrollmentRequestRepository;

        this.userSessionService = new UserSessionService(PersistenceContext.repositories().eCourseUsers());

        this.courseEnrollmentService = new CourseEnrollmentService(enrollmentRequestRepository,
                PersistenceContext.repositories().enrolledStudents(),
                new ListCourseByStateService(PersistenceContext.repositories().courses()),
                new ListCourseStudentsController(PersistenceContext.repositories().courses()));

    }

    private void verifyStudent() {
        Optional<ECourseUser> eCourseUserOptional = userSessionService.getLoggedUser();

        eCourseUserOptional.ifPresentOrElse(
                eCourseUser -> this.student = eCourseUser,
                () -> {
                    throw new IllegalStateException("No eCourse user found. Make sure you are registered.");
                }
        );
    }

    /**
     * Gets courses that are open for enrollment,
     * and that the student has not yet requested enrollment or is already enrolled.
     *
     * @return the available courses
     */
    public Iterable<Course> getCoursesOpenForEnrollment() {
        // Make sure the student is logged in
        verifyStudent();

        return courseEnrollmentService.findAvailableCoursesForStudentToRequestEnrollment(student);
    }

    /**
     * Register enrollment request, using the EnrollmentRequestBuilder.
     * Must first verify if the eCourse user exists (UserSessionService).
     *
     * @param selectedCourse the selected course
     */
    public void registerEnrollmentRequest(final Course selectedCourse) {

        final EnrollmentRequest enrollmentRequest =
                new EnrollmentRequestBuilder(enrollmentRequestRepository, courseEnrollmentService)
                        .withCourse(selectedCourse)
                        .withECourseUser(student)
                        .build();

        enrollmentRequestRepository.save(enrollmentRequest);
    }

}
