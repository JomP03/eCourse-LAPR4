package ui.users.enable_disable;

import ecourseusermanagement.application.EnableUserController;
import persistence.PersistenceContext;
import ui.components.AbstractUI;
import ui.components.Console;
import ui.components.Sleeper;
import ui.users.list.ListUsersUI;

public class EnableUserUI extends AbstractUI {

    private final EnableUserController controller = new EnableUserController(PersistenceContext.repositories().eCourseUsers());

    @Override
    protected boolean doShow() {
        var ui = new ListUsersUI();
        var wereUsersPrinted = ui.listDisabledUsers();
        if (!wereUsersPrinted) {
            return false;
        }
        var email = Console.readNonEmptyLine("Provide the email of the user to enable: ", "Email cannot be empty.");

        try {
            controller.enableUser(email);
            successMessage("User with email: " + email + " enabled successfully.");
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
        return "Enable User";
    }
}
