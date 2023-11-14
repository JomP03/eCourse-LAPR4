package ui.course.setteachers;

import ui.components.AbstractUI;
import ui.menu.Menu;
import ui.menu.MenuRenderer;

public class SetTeachersMenu extends AbstractUI {

    @Override
    protected boolean doShow() {
        final Menu menu = buildSetTeachersMenu();
        final MenuRenderer renderer = new MenuRenderer(menu);
        return renderer.render(MenuRenderer.PullOutActions.BACK.message());
    }

    private Menu buildSetTeachersMenu() {
        final Menu coursesMenu = new Menu();

        coursesMenu.addItem(1, "Define a Course Teacher in charge", new SetTeacherInChargeAction());
        coursesMenu.addItem(2, "Add Teachers to a Course", new AddTeachersToCourseAction());

        return coursesMenu;
    }

    @Override
    public String headline() {
        return "Set Course Teachers Menu";
    }
}