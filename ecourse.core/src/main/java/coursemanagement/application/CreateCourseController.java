package coursemanagement.application;

import coursemanagement.domain.*;
import coursemanagement.repository.*;
import eapli.framework.validations.*;

public class CreateCourseController {

    private final CourseRepository repo;

    // Using Dependency Injection, we can use any implementation of the CourseRepository.
    // Whether JpaCourseRepository or something like FileCourseRepository
    public CreateCourseController(CourseRepository repo) {
        Preconditions.nonNull(repo);
        this.repo = repo;
    }

    /**
     * Creates a new course and saves it to the database
     *
     * @param courseCode - Course's Code
     * @param courseName - Course's Name
     * @param courseDescription - Course's Description
     */
    public Course createCourse(String courseCode, String courseName, String courseDescription, int minNrEnrolledStudents, int maxNrEnrolledStudents) {
        Course course = new CourseBuilder(repo)
                .withCourseCode(courseCode)
                .withCourseName(courseName)
                .withCourseDescription(courseDescription)
                .withMinNrEnrolledStudents(minNrEnrolledStudents)
                .withMaxNrEnrolledStudents(maxNrEnrolledStudents)
                .build();

        return repo.save(course);
    }
}
