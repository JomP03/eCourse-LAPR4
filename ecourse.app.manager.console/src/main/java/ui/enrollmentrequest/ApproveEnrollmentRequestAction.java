package ui.enrollmentrequest;

import eapli.framework.actions.Action;
import enrollmentrequestmanagement.domain.EnrollmentRequest;

public class ApproveEnrollmentRequestAction implements Action {

    private final EnrollmentRequest enrollmentRequest;

    public ApproveEnrollmentRequestAction(EnrollmentRequest enrollmentRequest) {
        // verify if the enrollmentRequest is null
        if (enrollmentRequest == null) {
            throw new IllegalArgumentException("The enrollmentRequest cannot be null.");
        }

        this.enrollmentRequest = enrollmentRequest;
    }

    @Override
    public boolean execute() {
        return new ApproveEnrollmentRequestUI(enrollmentRequest).show();
    }

}
