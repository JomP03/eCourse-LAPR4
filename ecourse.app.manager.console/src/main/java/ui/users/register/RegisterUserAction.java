package ui.users.register;

import eapli.framework.actions.Action;

public class RegisterUserAction implements Action {
    @Override
    public boolean execute() {
        return new RegisterUserUI().show();
    }
}
