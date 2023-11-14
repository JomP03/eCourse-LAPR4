package ui.course.setteachers;

import coursemanagement.application.setteachers.AvailableTeachersService;
import coursemanagement.application.setteachers.SetCourseTeachersController;
import coursemanagement.domain.Course;
import ecourseusermanagement.domain.ECourseUser;
import persistence.PersistenceContext;
import ui.components.AbstractUI;
import ui.components.Console;
import ui.components.ListSelector;
import ui.components.Sleeper;

import java.util.ArrayList;
import java.util.List;

public class AddTeachersToCourseUI extends AbstractUI {
    private final SetCourseTeachersController controller = new SetCourseTeachersController(PersistenceContext.repositories().courses(), new AvailableTeachersService(PersistenceContext.repositories().eCourseUsers()));

    @Override
    protected boolean doShow() {
        Course course = null;
        try {
            // Select the course
            List<Course> courses = (List<Course>) controller.notClosedCourses();
            if (courses.isEmpty()) {
                infoMessage("There are no courses available to add teachers to.");
                Sleeper.sleep(1500);
                return false;
            }
            ListSelector<Course> courseSelector = new ListSelector<>("Courses:", courses);
            if (!courseSelector.showAndSelectWithExit()) return false;
            course = courseSelector.getSelectedElement();
        } catch (Exception e) {
            errorMessage(e.getMessage());
        }

        int wantsToAddTeachers;
        do {
            List<ECourseUser> availableTeachers = new ArrayList<>();
            try {
                // Select the teacher
                availableTeachers = (List<ECourseUser>) controller.availableTeachers(course);
                if (availableTeachers.isEmpty()) {
                    infoMessage("There are no teachers available to add to the course.");
                    Sleeper.sleep(1500);
                    break;
                }

                ListSelector<ECourseUser> teacherSelector = new ListSelector<>("Available Teachers:", availableTeachers);
                if (!teacherSelector.showAndSelectWithExit()) return false;
                assert course != null;
                course = controller.addTeacherToCourse(teacherSelector.getSelectedElement(), course);
                availableTeachers.remove(teacherSelector.getSelectedElement());
                successMessage("Teacher added successfully!");
                Sleeper.sleep(1500);
            } catch (Exception e) {
                errorMessage(e.getMessage());
                Sleeper.sleep(1700);
            }

            if (availableTeachers.isEmpty()) {
                infoMessage("There are no more teachers available to add to the course.");
                Sleeper.sleep(1000);
                break;
            }

            // Ask if the manager wants to add more teachers
            System.out.printf("Do you want to add more teachers to the course?%n%n1 - Yes%n0 - No%n%n");
            wantsToAddTeachers = Console.readOption(0, 1);

        } while (wantsToAddTeachers == 1);
        return true;
    }

    @Override
    public String headline() {
        return "Add teachers to course";
    }
}
