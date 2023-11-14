package ui.course.setteachers;

import eapli.framework.actions.Action;

public class SetTeacherInChargeAction implements Action {
    @Override
    public boolean execute() {
        return new SetTeacherInChargeUI().show();
    }
}
