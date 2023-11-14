package ui.takenexams;

import coursemanagement.application.StudentCoursesProvider;
import coursemanagement.domain.Course;
import ecourseusermanagement.application.UserSessionService;
import persistence.PersistenceContext;
import ui.components.AbstractUI;
import ui.components.ListSelector;
import takenexammanagement.application.TakenExamProvider;
import takenexammanagement.application.listgrades.ListStudentGradesController;
import takenexammanagement.domain.TakenExam;

import java.util.List;

public class ListStudentGradesUI extends AbstractUI {

    private final ListStudentGradesController controller =
            new ListStudentGradesController(new UserSessionService(PersistenceContext.repositories().eCourseUsers()),
                    new StudentCoursesProvider(PersistenceContext.repositories().enrolledStudents()),
                    new TakenExamProvider(PersistenceContext.repositories().takenExams())
            );

    @Override
    protected boolean doShow() {



        Course selectedCourse = menuToSelectCourse();
        if(selectedCourse == null)
            return false;

        List<TakenExam> takenExams = controller.takenExamsFromCourse(selectedCourse);

        if(takenExams.isEmpty()) {
            infoMessage("You have not taken any exams in this course");
            return false;
        }

        displayGrades(takenExams, selectedCourse);

        return true;
    }

    /**
     * Displays the grades of the given taken exams
     * @param takenExams The taken exams to display the grades of
     */
    private void displayGrades(List<TakenExam> takenExams, Course course) {

        infoMessage("Grades for course -> " + course.identity());

        System.out.printf("%-25s | %s\n", "Exam Title", "Grade");
        System.out.println();

        for(TakenExam takenExam : takenExams) {
            Float grade = takenExam.grade();
            System.out.printf("%-25s | %.2f\n", takenExam.exam().title(), grade);
        }

        System.out.println();

    }

    /**
     * Asks the user to select a course from a list
     * @return The selected course
     */
    private Course menuToSelectCourse() {
        List<Course> courses = (List<Course>) controller.studentCourses();

        if(courses.isEmpty()) {
            infoMessage("You are not enrolled in any course");
            return null;
        }

        ListSelector<Course> courseSelector = new ListSelector<>("Select a course: ", courses);
        courseSelector.showAndSelectWithExit();

        return courseSelector.getSelectedElement();
    }

    @Override
    public String headline() {
        return "View A List Of My Grades";
    }
}
