package ui.users.register.manager;

import eapli.framework.actions.Action;

public class RegisterManagerAction implements Action {

    @Override
    public boolean execute() {
        return new RegisterManagerUI().show();
    }
}
