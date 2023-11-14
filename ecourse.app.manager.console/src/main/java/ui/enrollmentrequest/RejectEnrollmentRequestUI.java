package ui.enrollmentrequest;

import enrollmentrequestmanagement.application.manage.RejectEnrollmentRequestController;
import enrollmentrequestmanagement.domain.EnrollmentRequest;
import persistence.PersistenceContext;
import ui.components.AbstractUI;
import ui.components.Console;
import ui.components.Sleeper;

public class RejectEnrollmentRequestUI extends AbstractUI {

    private final EnrollmentRequest enrollmentRequest;

    private final RejectEnrollmentRequestController rejectEnrollmentRequestController =
            new RejectEnrollmentRequestController(PersistenceContext.repositories().enrollmentRequests());

    public RejectEnrollmentRequestUI(EnrollmentRequest enrollmentRequest) {
        // verify if the enrollmentRequest is null
        if (enrollmentRequest == null) {
            throw new IllegalArgumentException("The enrollmentRequest cannot be null.");
        }

        this.enrollmentRequest = enrollmentRequest;
    }

    @Override
    protected boolean doShow() {
        // Request the enrollment decision reason
        String enrollmentDecisionReason = Console.readLine("Introduce rejection reason: ");
        System.out.println();

        try {
            // Reject the enrollment request
            rejectEnrollmentRequestController.rejectEnrollmentRequest(enrollmentRequest, enrollmentDecisionReason);
            successMessage("Enrollment Request rejected successfully");
        } catch (IllegalArgumentException e) {
            errorMessage(e.getMessage());
            Sleeper.sleep(1000);
            return false;
        }

        return false;

    }

    @Override
    public String headline() {
        return "Reject Enrollment Request";
    }

}
