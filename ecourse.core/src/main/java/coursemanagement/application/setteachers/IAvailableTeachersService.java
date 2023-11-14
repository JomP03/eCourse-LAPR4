package coursemanagement.application.setteachers;

import coursemanagement.domain.Course;
import ecourseusermanagement.domain.ECourseUser;

/**
 * The interface used to get the available teachers to add to a course.
 * An available teacher is a teacher that is not already assigned to the course.
 */
public interface IAvailableTeachersService {

    /**
     * Returns the available teachers to add to a course.
     *
     * @return the iterable
     */
    Iterable<ECourseUser> availableTeachers(Course course);
}
