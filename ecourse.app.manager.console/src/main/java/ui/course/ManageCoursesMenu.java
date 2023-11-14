package ui.course;

import ui.components.AbstractUI;
import ui.course.bulkenroll.BulkEnrollStudentsCsvAction;
import ui.course.coursestate.*;
import ui.course.listcourses.ListManagerCoursesAction;
import ui.course.setteachers.SetCourseTeachersMenuAction;
import ui.menu.Menu;
import ui.menu.MenuRenderer;

public class ManageCoursesMenu extends AbstractUI {

    @Override
    protected boolean doShow() {
        final Menu menu = buildCoursesMenu();
        final MenuRenderer renderer = new MenuRenderer(menu);
        return renderer.render(MenuRenderer.PullOutActions.BACK.message());
    }

    private Menu buildCoursesMenu() {
        final Menu coursesMenu = new Menu();

        coursesMenu.addItem(1, "Create a Course", new CreateCourseAction());
        coursesMenu.addItem(2, "Open Course", new OpenCourseAction());
        coursesMenu.addItem(3, "Open Enrollments", new OpenEnrollmentsAction());
        coursesMenu.addItem(4, "Close Enrollments", new CloseEnrollmentsAction());
        coursesMenu.addItem(5, "Close Course", new CloseCourseAction());
        coursesMenu.addItem(6, "Set Course Teachers", new SetCourseTeachersMenuAction());
        coursesMenu.addItem(7, "List All Courses", new ListManagerCoursesAction());
        coursesMenu.addItem(8, "Bulk Enroll Students by CSV file", new BulkEnrollStudentsCsvAction());

        return coursesMenu;
    }

    @Override
    public String headline() {
        return "Manage Courses Menu";
    }
}

