package ui.course.setteachers;

import eapli.framework.actions.Action;

public class AddTeachersToCourseAction implements Action {
    @Override
    public boolean execute() {
        return new AddTeachersToCourseUI().show();
    }
}
