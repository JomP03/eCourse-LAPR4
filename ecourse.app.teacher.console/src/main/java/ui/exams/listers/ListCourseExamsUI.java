package ui.exams.listers;

import coursemanagement.application.TeacherCoursesProvider;
import coursemanagement.domain.Course;
import ecourseusermanagement.application.UserSessionService;
import exammanagement.application.CourseExamsProvider;
import exammanagement.application.ListCourseExamsController;
import exammanagement.domain.Exam;
import persistence.PersistenceContext;
import ui.components.AbstractUI;
import ui.components.ListSelector;
import ui.components.Sleeper;

import java.util.List;

public class ListCourseExamsUI extends AbstractUI {

    private final ListCourseExamsController listCourseExamsController = new ListCourseExamsController(
            new UserSessionService(PersistenceContext.repositories().eCourseUsers()), new TeacherCoursesProvider(
                    PersistenceContext.repositories().courses()), new CourseExamsProvider(
                            PersistenceContext.repositories().exams()));

    @Override
    protected boolean doShow() {
        try {
            // Course to select from
            List<Course> teacherActiveCourses = (List<Course>) listCourseExamsController.teacherCourses();

            if (teacherActiveCourses.isEmpty()) {
                errorMessage("You don't have any courses.");
                return false;
            }

            ListSelector<Course> courseListSelector = new ListSelector<>("Select a course",
                    teacherActiveCourses);

            if(!courseListSelector.showAndSelectWithExit()) return false;

            List<Exam> courseExams = (List<Exam>) listCourseExamsController.courseExams(
                    courseListSelector.getSelectedElement());

            if (courseExams.isEmpty()) {
                errorMessage("This course doesn't have any exams.");
                return false;
            }

            displayCourseExams(courseExams);

            Sleeper.sleep(2000);

            successMessage("Course exams listed successfully.");

        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    private void displayCourseExams(List<Exam> courseExams) {
        System.out.println("Course Exams:");
        System.out.println();

        for (Exam exam : courseExams) {
            System.out.println("Creator: " + exam.creator().email() + " | Course: " + exam.course().identity() + " | Exam Title: " + exam.title());
        }

        System.out.println();
    }

    @Override
    public String headline() {
        return "List Course Exams";
    }
}
