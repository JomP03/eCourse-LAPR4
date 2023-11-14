package coursemanagement.application.coursestate;

import coursemanagement.domain.*;

public interface IListCourseByStateService {

    /**
     * Find courses by state, allowing to get all courses with a given state.
     *
     * @param state the state
     * @return the iterable
     */
    Iterable<Course> findCoursesByState(CourseState state);
}
