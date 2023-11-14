package ui.courses;

import eapli.framework.actions.*;

public class ManageCoursesMenuAction implements Action {
    @Override
    public boolean execute() {
        return new ManageCoursesMenuUI().show();
    }
}
