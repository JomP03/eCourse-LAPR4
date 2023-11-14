package ui;

import ui.board.CreateBoardAction;
import ui.components.AbstractUI;
import ui.course.*;
import ui.enrollmentrequest.ListEnrollmentRequestAction;
import ui.meeting.ManageMeetingsMenuAction;
import ui.menu.Menu;
import ui.menu.MenuRenderer;
import ui.users.*;

public class ManagerMenuUI extends AbstractUI {

    @Override
    protected boolean doShow() {
        final Menu menu = buildManagerMenu();
        final MenuRenderer renderer = new MenuRenderer(menu);
        return renderer.render(MenuRenderer.PullOutActions.EXIT.message());
    }

    private Menu buildManagerMenu() {
        final Menu managerMenu = new Menu();

        managerMenu.addItem(1, "Manage Users", new ManageUsersAction());
        managerMenu.addItem(2, "Manage Courses", new ManageCoursesAction());
        managerMenu.addItem(3, "Manage Enrollment Requests", new ListEnrollmentRequestAction());
        managerMenu.addItem(4, "Manage Meetings", new ManageMeetingsMenuAction());
        managerMenu.addItem(5, "Create Board", new CreateBoardAction());

        return managerMenu;
    }

    @Override
    public String headline() {
        return "Manager Menu";
    }
}
