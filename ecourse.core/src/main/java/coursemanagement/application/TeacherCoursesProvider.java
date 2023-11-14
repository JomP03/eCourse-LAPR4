package coursemanagement.application;

import coursemanagement.domain.Course;
import coursemanagement.domain.CourseState;
import coursemanagement.repository.CourseRepository;
import ecourseusermanagement.domain.ECourseUser;

import java.util.Collections;
import java.util.List;

public class TeacherCoursesProvider implements IUserCoursesProvider {

    private final CourseRepository courseRepository;

    /**
     * Instantiates a new List student courses service.
     *
     * @param courseRepository the course repository
     */
    public TeacherCoursesProvider(CourseRepository courseRepository) {
        // Verify if courseRepository is null
        if (courseRepository == null) {
            throw new IllegalArgumentException("Course repository cannot be null.");
        }

        this.courseRepository = courseRepository;
    }

    @Override
    public Iterable<Course> provideUserActiveCourses(ECourseUser user) {
        return courseRepository.findTeacherActiveCourses(user);
    }

    /**
     * List user courses iterable.
     *
     * @param user the user
     * @return the iterable
     */
    public Iterable<Course> provideUserCourses(ECourseUser user) {
        return courseRepository.findTeacherCourses(user);
    }


    public Iterable<Course> provideTeacherGradedCourses(ECourseUser user) {
        List<Course> inProgressAndClosedCourses = (List<Course>)
                courseRepository.findCoursesByState(CourseState.IN_PROGRESS, CourseState.CLOSED);

        List<Course> teacherCourses = (List<Course>) provideUserCourses(user);

        return inProgressAndClosedCourses.retainAll(teacherCourses)
                ? inProgressAndClosedCourses : Collections.emptyList();
    }
}
