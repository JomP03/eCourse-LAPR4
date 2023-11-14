package enrollmentrequestmanagement.application.manage;

import coursemanagement.application.coursestate.ListCourseByStateService;
import coursemanagement.domain.Course;
import coursemanagement.domain.CourseState;
import enrollmentrequestmanagement.domain.EnrollmentRequest;
import enrollmentrequestmanagement.domain.EnrollmentRequestState;
import enrollmentrequestmanagement.repository.EnrollmentRequestRepository;
import persistence.PersistenceContext;

public class ListEnrollmentRequestController {

    private final ListCourseByStateService listCourseByStateService;

    private final EnrollmentRequestRepository enrollmentRequestRepository;

    /**
     * Instantiates a new Manage enrollment request controller.
     *
     * @param enrollmentRequestRepository the enrollment request repository
     */
    public ListEnrollmentRequestController(EnrollmentRequestRepository enrollmentRequestRepository) {

        this.listCourseByStateService = new ListCourseByStateService(PersistenceContext.repositories().courses());

        // verify if the enrollmentRequestRepository is null
        if (enrollmentRequestRepository == null) {
            throw new IllegalArgumentException("The enrollmentRequestRepository cannot be null.");
        }

        this.enrollmentRequestRepository = enrollmentRequestRepository;
    }

    /**
     * Gets courses in "ENROLL" state.
     *
     * @return the courses open for enrollment
     */
    public Iterable<Course> getCoursesOpenForEnrollment() {
        return listCourseByStateService.findCoursesByState(CourseState.ENROLL);
    }

    /**
     * Gets enrollment requests in "PENDING" state.
     *
     * @param selectedCourse the selected course
     * @return the pending enrollment requests
     */
    public Iterable<EnrollmentRequest> getEnrollmentRequestsForCourse(Course selectedCourse) {
        return enrollmentRequestRepository.findByCourseAndState(selectedCourse, EnrollmentRequestState.PENDING);
    }

}
