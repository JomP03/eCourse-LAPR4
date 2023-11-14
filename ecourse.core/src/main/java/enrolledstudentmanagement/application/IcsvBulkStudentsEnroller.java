package enrolledstudentmanagement.application;

import coursemanagement.domain.Course;
import enrolledstudentmanagement.dto.BulkEnrollStudentsReport;

import java.io.File;

/**
 * The interface for the service to bulk enroll students from a CSV file.
 */
public interface IcsvBulkStudentsEnroller {
    /**
     * Bulk enroll students.
     *
     * @param course  the course
     * @param csvFile the csv file
     * @return the bulk enroll students report
     */
    BulkEnrollStudentsReport bulkEnrollStudents(Course course, File csvFile);
}
