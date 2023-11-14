package ui.enrollmentrequest;

import enrolledstudentmanagement.application.RegisterEnrolledStudentController;
import enrollmentrequestmanagement.application.manage.ApproveEnrollmentRequestController;
import enrollmentrequestmanagement.application.manage.RejectEnrollmentRequestController;
import enrollmentrequestmanagement.domain.EnrollmentRequest;
import persistence.PersistenceContext;
import ui.components.AbstractUI;
import ui.components.Console;
import ui.components.Sleeper;

public class ApproveEnrollmentRequestUI extends AbstractUI {

    private final EnrollmentRequest enrollmentRequest;

    private final ApproveEnrollmentRequestController approveEnrollmentRequestController =
            new ApproveEnrollmentRequestController(PersistenceContext.repositories().enrollmentRequests(),
                    new RegisterEnrolledStudentController(PersistenceContext.repositories().enrolledStudents()),
                        new RejectEnrollmentRequestController(PersistenceContext.repositories().enrollmentRequests()));

    public ApproveEnrollmentRequestUI(EnrollmentRequest enrollmentRequest) {
        // verify if the enrollmentRequest is null
        if (enrollmentRequest == null) {
            throw new IllegalArgumentException("The enrollmentRequest cannot be null.");
        }

        this.enrollmentRequest = enrollmentRequest;
    }

    @Override
    protected boolean doShow() {
        // Request the enrollment decision reason
        String enrollmentDecisionReason = Console.readLine("Introduce approval reason: ");
        System.out.println();

        try {
            // Approve the enrollment request
            approveEnrollmentRequestController.approveEnrollmentRequest(enrollmentRequest, enrollmentDecisionReason);
            successMessage("Enrollment Request approved successfully");
        } catch (IllegalArgumentException e) {
            errorMessage(e.getMessage());
            Sleeper.sleep(1000);
            return false;
        }

        return false;
    }

    @Override
    public String headline() {
        return "Approve Enrollment Request";
    }

}
