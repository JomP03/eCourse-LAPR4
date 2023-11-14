package ui.users.register.teacher;

import eapli.framework.actions.Action;

public class RegisterTeacherAction implements Action {

    @Override
    public boolean execute() {
        return new RegisterTeacherUI().show();
    }
}
