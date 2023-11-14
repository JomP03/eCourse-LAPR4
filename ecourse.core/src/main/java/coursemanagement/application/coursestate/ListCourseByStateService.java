package coursemanagement.application.coursestate;

import coursemanagement.domain.*;
import coursemanagement.repository.*;

public class ListCourseByStateService implements IListCourseByStateService {

    CourseRepository courseRepo;

    public ListCourseByStateService(CourseRepository courseRepo) {
        this.courseRepo = courseRepo;
    }

    /**
     * Find courses by state, allowing to get all courses with a given state.
     *
     * @param state the state
     * @return the iterable
     */
    public Iterable<Course> findCoursesByState(CourseState state) {
        Iterable<Course> courses = courseRepo.findCoursesByState(state);

        if (courses == null) {
            throw new IllegalArgumentException("No courses found");
        }

        return courses;
    }
}
