package ui.users.list;

import eapli.framework.actions.Action;

public class ListUsersAction implements Action {
    @Override
    public boolean execute() {
        return new ListUsersUI().show();
    }
}
