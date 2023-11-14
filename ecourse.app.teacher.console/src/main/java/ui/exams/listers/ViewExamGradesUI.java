package ui.exams.listers;

import coursemanagement.application.TeacherCoursesProvider;
import coursemanagement.domain.Course;
import ecourseusermanagement.application.UserSessionService;
import exammanagement.application.CourseExamsProvider;
import exammanagement.domain.Exam;
import persistence.PersistenceContext;
import takenexammanagement.application.TakenExamProvider;
import takenexammanagement.application.ViewExamGradesController;
import takenexammanagement.domain.TakenExam;
import ui.components.AbstractUI;
import ui.components.ListSelector;
import ui.components.Sleeper;

import java.util.*;

public class ViewExamGradesUI extends AbstractUI {

    private final ViewExamGradesController controller = new ViewExamGradesController(
            new UserSessionService(PersistenceContext.repositories().eCourseUsers()),
            new TeacherCoursesProvider(PersistenceContext.repositories().courses()),
            new CourseExamsProvider(PersistenceContext.repositories().exams()),
            new TakenExamProvider(PersistenceContext.repositories().takenExams())
    );

    @Override
    protected boolean doShow() {

        Course selectedCourse = menuToSelectCourse((List<Course>) controller.listTeacherCourses());

        if (selectedCourse == null)
            return false;

        boolean wantsToExit;
        do {
            Exam selectedExam = menuToSelectExam((List<Exam>) controller.listCourseExams(selectedCourse));

            if (selectedExam == null)
                return false;

            List<TakenExam> takenExams = (List<TakenExam>) controller.listTakenExams(selectedExam);

            if (takenExams.isEmpty()) {
                infoMessage("The exam has not been taken yet.");
                Sleeper.sleep(1500);
            }
            else {
                displayGrades((List<TakenExam>) controller.listTakenExams(selectedExam));
            }



            wantsToExit = checkIfWantsToExit();
        } while (!wantsToExit);


        return false;
    }


    private Course menuToSelectCourse(List<Course> listTeacherCourses) {
        if (listTeacherCourses.isEmpty()) {
            infoMessage("You have no courses.");
            return null;
        }

        ListSelector<Course> selector = new ListSelector<>("Select a Course", listTeacherCourses);
        if (!selector.showAndSelectWithExit())
            return null;

        return selector.getSelectedElement();
    }


    private Exam menuToSelectExam(List<Exam> listCourseExams) {
        if (listCourseExams.isEmpty()) {
            infoMessage("The course has no exams.");
            Sleeper.sleep(1500);
            return null;
        }
        List<String> examTitles = new ArrayList<>();
        for (Exam exam : listCourseExams)
            examTitles.add(exam.title());

        ListSelector<String> selector = new ListSelector<>("Select an Exam", examTitles);
        if (!selector.showAndSelectWithExit())
            return null;

        return listCourseExams.get(selector.getSelectedOption() - 1);
    }


    private boolean checkIfWantsToExit() {
        ListSelector<String> selector = new ListSelector<>("Do you want to see other exams?",
                List.of("Yes", "No"));
        selector.showAndSelect();

        return selector.getSelectedOption() == 2;
    }


    /**
     * Show the grades and the student's name of the selected exam.
     *
     * @param takenExams the taken exams
     */
    private void displayGrades(List<TakenExam> takenExams) {
        System.out.printf("%-20s | %s\n", "Student", "Grade");
        for (TakenExam takenExam : takenExams)
            System.out.printf("%-20s | %.2f\n", takenExam.student().username(), takenExam.grade());
        System.out.println();
        Sleeper.sleep(2000);
    }

    @Override
    public String headline() {
        return "View Exam Grades";
    }
}
