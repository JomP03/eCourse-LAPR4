package coursemanagement.application.coursestate;

import coursemanagement.domain.*;
import coursemanagement.repository.*;
import eapli.framework.validations.*;
import enrolledstudentmanagement.repository.*;

public class CloseEnrollmentsController implements ChangeCourseStateController {

    private final CourseRepository CRepo;
    private final EnrolledStudentRepository ERepo;
    private final IListCourseByStateService listService;

    public CloseEnrollmentsController(CourseRepository CRepo,
                                      EnrolledStudentRepository Erepo,
                                      IListCourseByStateService listService) {
        Preconditions.nonNull(CRepo);
        Preconditions.nonNull(Erepo);
        Preconditions.nonNull(listService);

        this.ERepo = Erepo;
        this.CRepo = CRepo;
        this.listService = listService;
    }


    /**
     * Finds all courses with state ENROLL
     *
     * @return all courses with state ENROLL
     */
    @Override
    public Iterable<Course> findCourses() {
        return listService.findCoursesByState(CourseState.ENROLL);
    }


    /**
     * Changes the course state to IN_PROGRESS
     *
     * @param course the course to change the state
     */
    @Override
    public void changeCourseState(Course course) {
        if (!ERepo.doesCourseHasMinimumNumberEnrolledStudents(course))
            throw new IllegalArgumentException("Course does not have the minimum number of enrolled students");

        course.closeEnrollments();
        CRepo.save(course);
    }
}
