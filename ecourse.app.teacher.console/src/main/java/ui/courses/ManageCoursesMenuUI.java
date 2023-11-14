package ui.courses;

import ui.components.AbstractUI;
import ui.menu.*;

public class ManageCoursesMenuUI extends AbstractUI {


    @Override
    protected boolean doShow() {
        final Menu menu = buildTeacherMenu();
        final var renderer = new MenuRenderer(menu);
        return renderer.render(MenuRenderer.PullOutActions.EXIT.message());
    }

    private Menu buildTeacherMenu() {
        final Menu courseMenu = new Menu();

        courseMenu.addItem(1, "List Available Courses", new ListTeacherCoursesAction());

        return courseMenu;
    }

    @Override
    public String headline() {
        return "Manage Course Menu";
    }
}
