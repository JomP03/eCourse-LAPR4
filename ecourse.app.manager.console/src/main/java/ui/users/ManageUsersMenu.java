package ui.users;

import ui.components.AbstractUI;
import ui.menu.*;
import ui.users.enable_disable.*;
import ui.users.list.*;
import ui.users.register.*;

public class ManageUsersMenu extends AbstractUI {

    @Override
    protected boolean doShow() {
        final Menu menu = buildUsersMenu();
        final MenuRenderer renderer = new MenuRenderer(menu);
        return renderer.render(MenuRenderer.PullOutActions.BACK.message());
    }

    private Menu buildUsersMenu() {
        final Menu usersMenu = new Menu();

        usersMenu.addItem(1, "Register a User", new RegisterUserAction());
        usersMenu.addItem(2, "List Users", new ListUsersAction());
        usersMenu.addItem(3, "Disable a User", new DisableUserAction());
        usersMenu.addItem(4, "Enable a User", new EnableUserAction());

        return usersMenu;
    }


    @Override
    public String headline() {
        return "Manage Users Menu";
    }
}
