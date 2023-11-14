import coursemanagement.application.setteachers.AvailableTeachersService;
import coursemanagement.application.setteachers.SetCourseTeachersController;
import coursemanagement.domain.Course;
import eapli.framework.actions.Action;
import ecourseusermanagement.domain.ECourseUser;
import persistence.PersistenceContext;

import java.util.List;

public class CourseTeachersBootstrapper implements Action {

    private final SetCourseTeachersController controller =
            new SetCourseTeachersController(PersistenceContext.repositories().courses(),
                    new AvailableTeachersService(PersistenceContext.repositories().eCourseUsers()));

    @Override
    public boolean execute() {
        setSomeTeachersInCharge();
        addTeachersToCourses();
        return true;
    }

    private void setSomeTeachersInCharge() {
        List<Course> courses = (List<Course>) controller.notClosedCoursesWithoutTeacherInCharge();
        if (courses.isEmpty()) {
            System.out.println("Cant bootstrap course teachers in charge. No courses available.");
            return;
        }

        // Generate a random number of courses that will have a teacher in charge, between 1 and courses.size()
        int nCoursesToBootstrap = (int) (Math.random() * (courses.size() - 1)) + 1;

        for (int i = 0; i < nCoursesToBootstrap; i++) {
            Course course = courses.get(i);
            List<ECourseUser> teachers = (List<ECourseUser>) controller.availableTeachers(course);
            if (!teachers.isEmpty()) {
                int randomTeacherIndex = (int) (Math.random() * (teachers.size() - 1));
                controller.defineCourseTeacherInCharge(course, teachers.get(randomTeacherIndex));
            }
        }
    }

    private void addTeachersToCourses() {
        List<Course> courses = (List<Course>) controller.notClosedCourses();
        if (courses.isEmpty()) {
            System.out.println("Cant bootstrap course teachers in charge. No courses available.");
            return;
        }

        for (Course course : courses) {
            Course curCourse = course;
            List<ECourseUser> teachers = (List<ECourseUser>) controller.availableTeachers(curCourse);
            if (!teachers.isEmpty()) {
                int nTeachersToBootstrap = 3; //(int) (Math.random() * (teachers.size() - 1)) + 1;
                for (int i = 0; i < nTeachersToBootstrap; i++) {
                    ECourseUser teacher = teachers.get(i);
                    curCourse = controller.addTeacherToCourse(teacher, curCourse);
                }
            }
        }
    }


}
