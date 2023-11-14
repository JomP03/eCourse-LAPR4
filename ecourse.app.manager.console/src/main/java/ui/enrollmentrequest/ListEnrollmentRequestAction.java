package ui.enrollmentrequest;

import eapli.framework.actions.Action;

public class ListEnrollmentRequestAction implements Action {

    @Override
    public boolean execute() {
        return new ListEnrollmentRequestUI().show();
    }

}
