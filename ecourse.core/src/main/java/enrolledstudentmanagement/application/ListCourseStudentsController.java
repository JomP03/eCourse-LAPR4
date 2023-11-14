package enrolledstudentmanagement.application;

import coursemanagement.repository.CourseRepository;
import ecourseusermanagement.domain.ECourseUser;

public class ListCourseStudentsController {

    CourseRepository courseRepository;

    public ListCourseStudentsController(CourseRepository courseRepository) {
        if(courseRepository == null)
            throw new IllegalArgumentException();

        this.courseRepository = courseRepository;
    }


    /**
     * Returns all students enrolled in a course
     * @param courseCode course code
     * @return all students enrolled in a course
     */
    public Iterable<ECourseUser> getStudentsFromCourse(String courseCode) {
        return courseRepository.findStudentsFromCourse(courseCode);
    }








}
