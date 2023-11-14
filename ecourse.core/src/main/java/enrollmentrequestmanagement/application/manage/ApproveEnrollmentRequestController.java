package enrollmentrequestmanagement.application.manage;

import enrolledstudentmanagement.application.RegisterEnrolledStudentController;
import enrollmentrequestmanagement.domain.EnrollmentRequest;
import enrollmentrequestmanagement.repository.EnrollmentRequestRepository;

public class ApproveEnrollmentRequestController {

    private final EnrollmentRequestRepository enrollmentRequestRepository;

    private final RegisterEnrolledStudentController registerEnrolledStudentController;

    private final RejectEnrollmentRequestController rejectEnrollmentRequestController;

    /**
     * Instantiates a new Approve enrollment request controller.
     *
     * @param enrollmentRequestRepository the enrollment request repository
     */
    public ApproveEnrollmentRequestController(EnrollmentRequestRepository enrollmentRequestRepository,
                                              RegisterEnrolledStudentController registerEnrolledStudentController,
                                              RejectEnrollmentRequestController rejectEnrollmentRequestController) {
        // Verify if the enrollmentRequestRepository is null
        if (enrollmentRequestRepository == null) {
            throw new IllegalArgumentException("The enrollmentRequestRepository cannot be null.");
        }

        this.enrollmentRequestRepository = enrollmentRequestRepository;

        // Verify if the registerEnrolledStudentController is null
        if (registerEnrolledStudentController == null) {
            throw new IllegalArgumentException("The registerEnrolledStudentController cannot be null.");
        }

        this.registerEnrolledStudentController = registerEnrolledStudentController;

        // Verify if the rejectEnrollmentRequestController is null
        if (rejectEnrollmentRequestController == null) {
            throw new IllegalArgumentException("The rejectEnrollmentRequestController cannot be null.");
        }

        this.rejectEnrollmentRequestController = rejectEnrollmentRequestController;
    }

    private void rejectEnrollmentRequest(EnrollmentRequest enrollmentRequest, String enrollmentDecisionReason) {
        // verify if the enrollmentRequest is null
        if (enrollmentRequest == null) {
            throw new IllegalArgumentException("The enrollmentRequest cannot be null.");
        }

        // reject the enrollment request and save back to the repository
        rejectEnrollmentRequestController.rejectEnrollmentRequest(enrollmentRequest, enrollmentDecisionReason);
    }

    /**
     * Approve enrollment request.
     * The system checks if there is not a previous enrollment in a course from the same student.
     *
     * @param enrollmentRequest        the enrollment request
     * @param enrollmentDecisionReason the enrollment decision reason
     */
    public void approveEnrollmentRequest(EnrollmentRequest enrollmentRequest, String enrollmentDecisionReason) {
        // verify if the enrollmentRequest is null
        if (enrollmentRequest == null) {
            throw new IllegalArgumentException("The enrollmentRequest cannot be null.");
        }

        // Verify if the enrolled student was created
        if (registerEnrolledStudentController.registerEnrolledStudent(enrollmentRequest.requestedCourse(),
                enrollmentRequest.author()).isPresent()) {
            enrollmentRequest.approveEnrollmentRequest(enrollmentDecisionReason);
            enrollmentRequestRepository.save(enrollmentRequest);
        }

        else {
            // Update the enrollment decision reason
            enrollmentDecisionReason = "The student is already enrolled in the course or course is full.";
            rejectEnrollmentRequest(enrollmentRequest, enrollmentDecisionReason);
            throw new IllegalArgumentException("The student is already enrolled in the course or course is full.");
        }
    }

}
