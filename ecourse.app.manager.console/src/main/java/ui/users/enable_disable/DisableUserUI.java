package ui.users.enable_disable;

import ecourseusermanagement.application.DisableUserController;
import persistence.PersistenceContext;
import ui.components.AbstractUI;
import ui.components.Console;
import ui.components.Sleeper;
import ui.users.list.ListUsersUI;

public class DisableUserUI extends AbstractUI {

    private final DisableUserController controller = new DisableUserController(PersistenceContext.repositories().eCourseUsers());

    @Override
    protected boolean doShow() {
        var ui = new ListUsersUI();
        var wereUsersPrinted = ui.listEnabledUsers();
        if (!wereUsersPrinted) {
            return false;
        }
        var email = Console.readNonEmptyLine("Provide the email of the user to disable: ", "Email cannot be empty.");

        try {
            controller.disableUser(email);
            successMessage("User with email: " + email + " disabled successfully.");
            Sleeper.sleep(1000);
        } catch (IllegalArgumentException e) {
            errorMessage(e.getMessage());
            Sleeper.sleep(1000);
            return false;
        }
        return false;
    }

    @Override
    public String headline() {
        return "Disable User";
    }
}
