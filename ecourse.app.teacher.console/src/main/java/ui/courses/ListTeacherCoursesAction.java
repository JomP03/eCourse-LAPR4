package ui.courses;

import eapli.framework.actions.Action;

public class ListTeacherCoursesAction implements Action {

    @Override
    public boolean execute() {
        return new ListTeacherCoursesUI().show();
    }
}