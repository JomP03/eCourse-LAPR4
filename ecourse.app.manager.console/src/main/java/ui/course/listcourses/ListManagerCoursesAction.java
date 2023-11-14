package ui.course.listcourses;

import eapli.framework.actions.Action;

public class ListManagerCoursesAction implements Action {

    @Override
    public boolean execute() {
        return new ListManagerCoursesUI().show();
    }
}
