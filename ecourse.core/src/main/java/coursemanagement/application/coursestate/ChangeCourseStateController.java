package coursemanagement.application.coursestate;

import coursemanagement.domain.*;

public interface ChangeCourseStateController {

    /**
     * Finds all courses depending on the state
     *
     * @return all courses
     */
    Iterable<Course> findCourses();


    /**
     * Changes the course state
     *
     * @param course - course to change state
     */
    void changeCourseState(Course course);
}
