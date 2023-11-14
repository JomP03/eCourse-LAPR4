package ui.course.coursestate;

import eapli.framework.actions.*;

public class CloseCourseAction implements Action {

    @Override
    public boolean execute() {
        return new CloseCourseUI().show();
    }
}
