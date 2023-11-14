package coursemanagement.application.listcourses;

import coursemanagement.domain.Course;
import coursemanagement.repository.CourseRepository;
import ecourseusermanagement.application.UserSessionService;
import ecourseusermanagement.domain.ECourseUser;

import java.util.Optional;

public class ListTeacherCoursesController {
    private final CourseRepository courseRepository;

    private final UserSessionService userSessionService;

    private ECourseUser teacher;

    /*
     * Instantiates a new ListStudentAvailableCoursesController.
     *
     * @param courseRepository represents the course repository.
     */
    public ListTeacherCoursesController(CourseRepository courseRepository, UserSessionService userSessionService){
        if(courseRepository == null){
            throw new IllegalArgumentException("The Course Repository cannot be null.");
        }
        this.courseRepository = courseRepository;

        this.userSessionService = userSessionService;

        verifyLoggedUser();
    }

    private void verifyLoggedUser() {
        Optional<ECourseUser> eCourseUserOptional = userSessionService.getLoggedUser();

        eCourseUserOptional.ifPresentOrElse(
                eCourseUser -> this.teacher = eCourseUser,
                () -> {
                    throw new IllegalStateException("No eCourse user found. Make sure you are registered.");
                });
    }

    /**
     * Returns all available courses for the logged teacher.
     *
     * @return all available courses for the logged teacher.
     */
    public Iterable<Course> findCourses() {
        return courseRepository.findTeacherCourses(teacher);
    }
}
