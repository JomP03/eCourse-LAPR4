package ui.course.coursestate;

import eapli.framework.actions.*;

public class OpenEnrollmentsAction implements Action {

    @Override
    public boolean execute() {
        return new OpenEnrollmentsUI().show();
    }
}
