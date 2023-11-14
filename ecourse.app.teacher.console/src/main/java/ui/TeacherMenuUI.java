package ui;

import ui.board.CreateBoardAction;
import ui.components.AbstractUI;
import ui.meeting.*;
import ui.menu.Menu;
import ui.menu.MenuRenderer;
import ui.classes.*;
import ui.courses.*;
import ui.exams.*;
import ui.question.*;

public class TeacherMenuUI extends AbstractUI {


    @Override
    protected boolean doShow() {
        final Menu menu = buildTeacherMenu();
        final var renderer = new MenuRenderer(menu);
        return renderer.render(MenuRenderer.PullOutActions.EXIT.message());
    }

    private Menu buildTeacherMenu() {
        final Menu teacherMenu = new Menu();

        teacherMenu.addItem(1, "Manage Classes", new ManageClassesMenuAction());
        teacherMenu.addItem(2, "Manage Courses", new ManageCoursesMenuAction());
        teacherMenu.addItem(3, "Manage Exams", new ManageExamsMenuAction());
        teacherMenu.addItem(4, "Manage Questions", new ManageQuestionsMenuAction());
        teacherMenu.addItem(5, "Manage Meetings", new ManageMeetingsMenuAction());
        teacherMenu.addItem(6,"Create Board", new CreateBoardAction());

        return teacherMenu;
    }

    @Override
    public String headline() {
        return "Teacher Menu";
    }
}
