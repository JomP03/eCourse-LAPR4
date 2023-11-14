package ui.classes;

import ui.components.AbstractUI;
import ui.menu.*;
import ui.classes.scheduleclass.*;
import ui.classes.scheduleextraclass.*;
import ui.classes.updateclass.*;

public class ManageClassesMenuUI extends AbstractUI {


    @Override
    protected boolean doShow() {
        final Menu menu = buildTeacherMenu();
        final var renderer = new MenuRenderer(menu);
        return renderer.render(MenuRenderer.PullOutActions.EXIT.message());
    }

    private Menu buildTeacherMenu() {
        final Menu classMenu = new Menu();

        classMenu.addItem(1, "Schedule a Class", new ClassScheduleAction());
        classMenu.addItem(2, "Schedule a Extra Class", new ExtraClassScheduleAction());
        classMenu.addItem(3, "Update Class Schedule", new UpdateClassScheduleAction());

        return classMenu;
    }

    @Override
    public String headline() {
        return "Manage Classes Menu";
    }
}
