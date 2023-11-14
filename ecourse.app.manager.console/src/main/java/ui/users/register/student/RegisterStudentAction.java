package ui.users.register.student;

import eapli.framework.actions.Action;

public class RegisterStudentAction implements Action {

    @Override
    public boolean execute() {
        return new RegisterStudentUI().show();
    }
}
