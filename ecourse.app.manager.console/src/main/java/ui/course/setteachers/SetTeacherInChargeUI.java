package ui.course.setteachers;

import coursemanagement.application.setteachers.AvailableTeachersService;
import coursemanagement.application.setteachers.SetCourseTeachersController;
import coursemanagement.domain.Course;
import ecourseusermanagement.domain.ECourseUser;
import persistence.PersistenceContext;
import ui.components.AbstractUI;
import ui.components.ListSelector;
import ui.components.Sleeper;

import java.util.List;

public class SetTeacherInChargeUI extends AbstractUI {
    private final SetCourseTeachersController controller = new SetCourseTeachersController(PersistenceContext.repositories().courses(), new AvailableTeachersService(PersistenceContext.repositories().eCourseUsers()));

    @Override
    protected boolean doShow() {
        try {
            Course course;
            // Select the course
            List<Course> courses = (List<Course>) controller.notClosedCoursesWithoutTeacherInCharge();
            if (courses.isEmpty()) {
                infoMessage("There are no courses available to set a teacher in charge.");
                Sleeper.sleep(1500);
                return false;
            }
            ListSelector<Course> courseSelector = new ListSelector<>("Courses Without Teacher In Charge:", courses);
            if (!courseSelector.showAndSelectWithExit()) return false;
            course = courseSelector.getSelectedElement();

            // Select the teacher
            List<ECourseUser> availableTeachers = (List<ECourseUser>) controller.availableTeachers(course);
            if (availableTeachers.isEmpty()) {
                infoMessage("There are no teachers available to be the responsible of this course.");
                Sleeper.sleep(1500);
                return true;
            }


            ListSelector<ECourseUser> teacherSelector = new ListSelector<>("Available Teachers:", availableTeachers);
            if (!teacherSelector.showAndSelectWithExit()) return false;
            controller.defineCourseTeacherInCharge(course, teacherSelector.getSelectedElement());

            // Show success message
            successMessage("Teacher in charge defined successfully");
            Sleeper.sleep(1500);
        } catch (Exception e) {
            errorMessage(e.getMessage());
            Sleeper.sleep(1700);
        }
        return true;
    }

    @Override
    public String headline() {
        return "Set Course Teachers";
    }
}
