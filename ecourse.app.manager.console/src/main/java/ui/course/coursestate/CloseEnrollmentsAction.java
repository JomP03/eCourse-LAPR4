package ui.course.coursestate;

import eapli.framework.actions.Action;

public class CloseEnrollmentsAction implements Action {

    @Override
    public boolean execute() {
        return new CloseEnrollmentsUI().show();
    }
}
