package ui.course;

import eapli.framework.actions.*;

public class ManageCoursesAction implements Action {
    @Override
    public boolean execute() {
        return new ManageCoursesMenu().show();
    }
}
