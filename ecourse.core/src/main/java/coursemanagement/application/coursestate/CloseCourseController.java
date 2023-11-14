package coursemanagement.application.coursestate;

import coursemanagement.domain.*;
import coursemanagement.repository.*;
import eapli.framework.validations.*;

public class CloseCourseController implements ChangeCourseStateController {

    final CourseRepository repo;
    final IListCourseByStateService listService;

    public CloseCourseController(CourseRepository repo, IListCourseByStateService listService) {
        Preconditions.nonNull(repo);
        Preconditions.nonNull(listService);

        this.repo = repo;
        this.listService = listService;
    }


    /**
     * Finds all courses with state ENROLL
     *
     * @return all courses with state ENROLL
     */
    @Override
    public Iterable<Course> findCourses() {
        return listService.findCoursesByState(CourseState.IN_PROGRESS);
    }


    /**
     * Changes the course state to IN_PROGRESS
     *
     * @param course the course to change the state
     */
    @Override
    public void changeCourseState(Course course) {
        course.closeCourse();

        repo.save(course);
    }
}
