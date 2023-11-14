package coursemanagement.application;

import coursemanagement.domain.Course;
import ecourseusermanagement.domain.ECourseUser;

public interface IUserCoursesProvider {

    /**
     * List active user courses.
     *
     * @param user the user
     * @return the iterable
     */
    Iterable<Course> provideUserActiveCourses(ECourseUser user);

}
