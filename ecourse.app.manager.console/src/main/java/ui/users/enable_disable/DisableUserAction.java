package ui.users.enable_disable;

import eapli.framework.actions.Action;

public class DisableUserAction implements Action {
    @Override
    public boolean execute() {
        return new DisableUserUI().show();
    }
}
