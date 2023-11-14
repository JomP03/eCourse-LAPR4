package ui.exams;

import ui.components.AbstractUI;
import ui.menu.*;
import ui.exams.automativeexam.*;
import ui.exams.formativeexam.*;
import ui.exams.listers.*;

public class ManageExamsMenuUI extends AbstractUI {


    @Override
    protected boolean doShow() {
        final Menu menu = buildTeacherMenu();
        final var renderer = new MenuRenderer(menu);
        return renderer.render(MenuRenderer.PullOutActions.EXIT.message());
    }

    private Menu buildTeacherMenu() {
        final Menu examMenu = new Menu();

        examMenu.addItem(1, "Create Automated Exam", new CreateAutomatedExamAction());
        examMenu.addItem(2, "Update Automated Exam", new UpdateAutomatedExamAction());
        examMenu.addItem(3, "Create Formative Exam", new CreateFormativeExamAction());
        examMenu.addItem(4, "Update Formative Exam", new UpdateFormativeExamAction());
        examMenu.addItem(5, "List Course Exams", new ListCourseExamsAction());
        examMenu.addItem(6, "View Exam Grades", new ViewExamGradesAction());

        return examMenu;
    }

    @Override
    public String headline() {
        return "Manage Exam Menu";
    }
}
