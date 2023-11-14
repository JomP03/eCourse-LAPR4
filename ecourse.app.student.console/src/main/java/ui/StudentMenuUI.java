package ui;

import ui.board.CreateBoardAction;
import ui.components.AbstractUI;
import ui.meeting.ManageMeetingsMenuAction;
import ui.menu.Menu;
import ui.menu.MenuRenderer;
import ui.courses.ListStudentCoursesAction;
import ui.enrollmentrequest.RegisterEnrollmentRequestAction;
import ui.exams.*;
import ui.exams.TakeAutomatedExamAction;
import ui.takenexams.ListStudentGradesAction;

public class StudentMenuUI extends AbstractUI {


    @Override
    protected boolean doShow() {
        final Menu menu = buildStudentMenu();
        final var renderer = new MenuRenderer(menu);
        return renderer.render(MenuRenderer.PullOutActions.EXIT.message());
    }

    private Menu buildStudentMenu() {
        final Menu studentMenu = new Menu();

        studentMenu.addItem(1, "Request Enrollment in a Course", new RegisterEnrollmentRequestAction());
        studentMenu.addItem(2, "List Available Courses", new ListStudentCoursesAction());
        studentMenu.addItem(3, "Create Board", new CreateBoardAction());
        studentMenu.addItem(4, "View A List Of My Future Exams", new ListFutureExamsAction());
        studentMenu.addItem(5, "Manage Meetings", new ManageMeetingsMenuAction());
        studentMenu.addItem(6, "Take Automated Exam", new TakeAutomatedExamAction());
        studentMenu.addItem(7, "Take Formative Exam", new TakeFormativeExamAction());
        studentMenu.addItem(8, "View A List Of My Grades", new ListStudentGradesAction());

        return studentMenu;
    }

    @Override
    public String headline() {
        return "Student Menu";
    }
}
