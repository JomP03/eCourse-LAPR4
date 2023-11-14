package ui.course.setteachers;

import eapli.framework.actions.Action;

public class SetCourseTeachersMenuAction implements Action {
    @Override
    public boolean execute() {
        return new SetTeachersMenu().show();
    }
}
