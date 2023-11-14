package ui.course;

import eapli.framework.actions.*;

public class CreateCourseAction implements Action {

    @Override
    public boolean execute() {
        return new CreateCourseUI().show();
    }
}
