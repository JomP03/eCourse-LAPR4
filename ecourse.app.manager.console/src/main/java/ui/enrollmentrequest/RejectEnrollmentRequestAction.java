package ui.enrollmentrequest;

import eapli.framework.actions.Action;
import enrollmentrequestmanagement.domain.EnrollmentRequest;

public class RejectEnrollmentRequestAction implements Action {

    private final EnrollmentRequest enrollmentRequest;

    public RejectEnrollmentRequestAction(EnrollmentRequest enrollmentRequest) {
        // verify if the enrollmentRequest is null
        if (enrollmentRequest == null) {
            throw new IllegalArgumentException("The enrollmentRequest cannot be null.");
        }

        this.enrollmentRequest = enrollmentRequest;
    }

    @Override
    public boolean execute() {
        return new RejectEnrollmentRequestUI(enrollmentRequest).show();
    }

}
