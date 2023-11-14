package ui.course.coursestate;

import eapli.framework.actions.*;

public class OpenCourseAction implements Action {

    @Override
    public boolean execute() {
        return new OpenCourseUI().show();
    }
}
