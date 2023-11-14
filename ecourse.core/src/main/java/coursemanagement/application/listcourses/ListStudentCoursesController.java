package coursemanagement.application.listcourses;

import coursemanagement.domain.Course;
import ecourseusermanagement.application.UserSessionService;
import ecourseusermanagement.domain.ECourseUser;

import java.util.Optional;

public class ListStudentCoursesController {
    private final StudentCoursesProvider studentCoursesProvider;
    private final UserSessionService userSessionService;

    /*
     * Instantiates a new ListStudentCoursesController.
     */
    public ListStudentCoursesController(StudentCoursesProvider studentCoursesProvider,
                                        UserSessionService userSessionService) {
        this.studentCoursesProvider = studentCoursesProvider;
        this.userSessionService = userSessionService;
    }

    /*
     * Returns all available courses for the logged student.
     *
     * @return all available courses for the logged student.
     */
    public Iterable<Course> findCourses() {
        Optional<ECourseUser> eCourseUser = userSessionService.getLoggedUser();
        if(eCourseUser.isEmpty())
            throw new IllegalStateException("eCourse Teacher must be registered.");
        return studentCoursesProvider.provideStudentAvailableCourses(eCourseUser.get());
    }
}
