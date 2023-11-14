package coursemanagement.application.coursestate;

import coursemanagement.domain.*;
import coursemanagement.repository.*;
import eapli.framework.validations.*;

public class OpenEnrollmentsController implements ChangeCourseStateController {

    final CourseRepository repo;
    final IListCourseByStateService listService;

    public OpenEnrollmentsController(CourseRepository repo, IListCourseByStateService listService) {
        Preconditions.nonNull(repo);
        Preconditions.nonNull(listService);

        this.repo = repo;
        this.listService = listService;
    }


    /**
     * Finds all courses with state OPEN
     *
     * @return all courses with state OPEN
     */
    @Override
    public Iterable<Course> findCourses() {
        return listService.findCoursesByState(CourseState.OPEN);
    }


    /**
     * Changes the course state to ENROLL
     *
     * @param course the course to change the state
     */
    @Override
    public void changeCourseState(Course course) {
        course.openEnrollments();

        repo.save(course);
    }
}
