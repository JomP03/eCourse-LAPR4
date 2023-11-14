package ui.enrollmentrequest;

import eapli.framework.actions.Action;

public class RegisterEnrollmentRequestAction implements Action {
    @Override
    public boolean execute() {
        return new RegisterEnrollmentRequestUI().show();
    }
}
