package enrolledstudentmanagement.application;

import coursemanagement.application.coursestate.ListCourseByStateService;
import coursemanagement.domain.Course;
import coursemanagement.domain.CourseState;
import enrolledstudentmanagement.dto.BulkEnrollStudentsReport;

import java.io.File;

/**
 * The Controller used to Bulk enroll students by a csv file.
 */
public class BulkEnrollStudentsCsvController {
    private final ListCourseByStateService listCourseByStateService;
    private final IcsvBulkStudentsEnroller csvBulkStudentsEnroller;

    /**
     * Instantiates a new Bulk enroll students csv controller.
     *
     * @param listCourseByStateService the list course by state service
     * @param csvBulkStudentsEnroller  the csv bulk students enroller
     */
    public BulkEnrollStudentsCsvController(ListCourseByStateService listCourseByStateService,
                                           IcsvBulkStudentsEnroller csvBulkStudentsEnroller) {
        if (listCourseByStateService == null)
            throw new IllegalArgumentException("A listCourseByStateService must be provided");
        this.listCourseByStateService = listCourseByStateService;

        if (csvBulkStudentsEnroller == null)
            throw new IllegalArgumentException("A csvBulkStudentsEnroller must be provided");
        this.csvBulkStudentsEnroller = csvBulkStudentsEnroller;
    }

    /**
     * List enrollable courses.
     *
     * @return the iterable
     */
    public Iterable<Course> listEnrollableCourses() {
        return listCourseByStateService.findCoursesByState(CourseState.ENROLL);
    }

    /**
     * Bulk enroll students bulk enroll students.
     *
     * @param course  the course
     * @param csvFile the csv file
     * @return the report of the bulk enroll students
     */
    public BulkEnrollStudentsReport bulkEnrollStudents(Course course, File csvFile) {
        return csvBulkStudentsEnroller.bulkEnrollStudents(course, csvFile);
    }
}
