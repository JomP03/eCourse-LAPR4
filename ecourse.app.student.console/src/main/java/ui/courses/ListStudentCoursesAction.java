package ui.courses;

import eapli.framework.actions.Action;

public class ListStudentCoursesAction implements Action {
    @Override public boolean execute(){
        return new ListStudentCoursesUI().show();
    }
}
