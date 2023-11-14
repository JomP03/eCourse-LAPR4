package ui.course.bulkenroll;

import eapli.framework.actions.Action;

public class BulkEnrollStudentsCsvAction implements Action{
    @Override
    public boolean execute() {
        return new BulkEnrollStudentsCsvUI().show();
    }
}
