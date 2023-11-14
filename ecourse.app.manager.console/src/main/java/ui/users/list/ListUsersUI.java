package ui.users.list;

import ecourseusermanagement.application.ListUsersController;
import ecourseusermanagement.domain.ECourseUser;
import persistence.PersistenceContext;
import ui.components.AbstractUI;
import ui.components.ListPrinter;
import ui.components.ListSelector;
import ui.components.Sleeper;

import java.util.List;

public class ListUsersUI extends AbstractUI {

    private final ListUsersController controller = new ListUsersController(PersistenceContext.repositories().eCourseUsers());

    @Override
    protected boolean doShow() {

        ListSelector<String> selector = new ListSelector<>("Select an option:", List.of("All", "Enabled", "Disabled"));
        selector.showAndSelectWithExit();
        switch (selector.getSelectedOption()) {
            case 1:
                System.out.println();
                listAllUsers();
                break;
            case 2:
                System.out.println();
                listEnabledUsers();
                break;
            case 3:
                System.out.println();
                listDisabledUsers();
                break;
            default:
                break;
        }

        return false;
    }


    /**
     * Lists all users.
     */
    public void listAllUsers() {
        List<ECourseUser> users = (List<ECourseUser>) controller.allUsers();
        if (users.isEmpty()) {
            System.out.println("There are no users in the system.");
            Sleeper.sleep(1000);
            return;
        }
        printUsers(users);
    }

    /**
     * Lists enabled users.
     *
     * @return true if there are enabled users, false otherwise
     */
    public boolean listEnabledUsers() {
        List<ECourseUser> users = (List<ECourseUser>) controller.enabledUsers();
        if (users.isEmpty()) {
            System.out.println("There are no enabled users in the system.");
            Sleeper.sleep(1000);
            return false;
        }
        printUsers(users);
        return true;
    }

    /**
     * Lists disabled users.
     *
     * @return true if there are disabled users, false otherwise
     */
    public boolean listDisabledUsers() {
        List<ECourseUser> users = (List<ECourseUser>) controller.disabledUsers();
        if (users.isEmpty()) {
            System.out.println("There are no disabled users in the system.");
            Sleeper.sleep(1000);
            return false;
        }
        printUsers(users);
        return true;
    }

    private void printUsers(List<ECourseUser> users) {
        ListPrinter<ECourseUser> printer = new ListPrinter<>("Users:", users);
        printer.showList();
        Sleeper.sleep(1000);
    }

    @Override
    public String headline() {
        return "List Users";
    }
}
