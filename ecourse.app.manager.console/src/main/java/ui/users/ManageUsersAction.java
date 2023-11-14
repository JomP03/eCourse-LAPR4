package ui.users;

import eapli.framework.actions.*;

public class ManageUsersAction implements Action {
    @Override
    public boolean execute() {
        return new ManageUsersMenu().show();
    }
}
