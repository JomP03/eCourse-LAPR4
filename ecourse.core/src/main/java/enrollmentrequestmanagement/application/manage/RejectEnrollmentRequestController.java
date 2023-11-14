package enrollmentrequestmanagement.application.manage;

import enrollmentrequestmanagement.domain.EnrollmentRequest;
import enrollmentrequestmanagement.repository.EnrollmentRequestRepository;

public class RejectEnrollmentRequestController {

    private final EnrollmentRequestRepository enrollmentRequestRepository;

    /**
     * Instantiates a new Reject enrollment request controller.
     *
     * @param enrollmentRequestRepository the enrollment request repository
     */
    public RejectEnrollmentRequestController(EnrollmentRequestRepository enrollmentRequestRepository) {
        // verify if the enrollmentRequestRepository is null
        if (enrollmentRequestRepository == null) {
            throw new IllegalArgumentException("The enrollmentRequestRepository cannot be null.");
        }

        this.enrollmentRequestRepository = enrollmentRequestRepository;
    }

    /**
     * Reject enrollment request (change the enrollment request state to REJECTED).
     *
     * @param enrollmentRequest        the enrollment request
     * @param enrollmentDecisionReason the enrollment decision reason
     */
    public void rejectEnrollmentRequest(EnrollmentRequest enrollmentRequest, String enrollmentDecisionReason) {
        if (enrollmentRequest == null) {
            throw new IllegalArgumentException("The enrollmentRequest cannot be null.");
        }

        // Reject the enrollment request and save back to the repository
        enrollmentRequest.rejectEnrollmentRequest(enrollmentDecisionReason);
        enrollmentRequestRepository.save(enrollmentRequest);
    }

}
