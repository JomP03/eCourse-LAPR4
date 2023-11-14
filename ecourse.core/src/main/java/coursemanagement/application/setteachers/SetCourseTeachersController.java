package coursemanagement.application.setteachers;

import coursemanagement.domain.Course;
import coursemanagement.repository.CourseRepository;
import ecourseusermanagement.domain.ECourseUser;

/**
 * Controller for setting the teachers of a course.
 */
public class SetCourseTeachersController {
    private final CourseRepository courseRepository;
    private final IAvailableTeachersService availableTeachersService;

    /**
     * Instantiates a new controller for setting the teachers of a course.
     *
     * @param courseRepository         the course repository
     * @param availableTeachersService the available teachers service
     */
    public SetCourseTeachersController(CourseRepository courseRepository, IAvailableTeachersService availableTeachersService) {
        this.courseRepository = courseRepository;
        this.availableTeachersService = availableTeachersService;
    }

    /**
     * Returns the not closed courses without a teacher in charge iterable.
     *
     * @return the courses
     */
    public Iterable<Course> notClosedCoursesWithoutTeacherInCharge() {
        return courseRepository.findAllNotClosedCoursesWithoutTeacherInCharge();
    }

    /**
     * Returns the not closed courses.
     *
     * @return the courses
     */
    public Iterable<Course> notClosedCourses() {
        return courseRepository.findAllNotClosedCourses();
    }

    /**
     * Returns the available teachers for the course.
     *
     * @return the teachers
     */
    public Iterable<ECourseUser> availableTeachers(Course course) {
        return availableTeachersService.availableTeachers(course);
    }

    /**
     * Defines the course teacher in charge.
     *
     * @param teacher the teacher
     */
    public void defineCourseTeacherInCharge(Course course, ECourseUser teacher) {
        course.defineTeacherInCharge(teacher);
        courseRepository.save(course);
    }

    /**
     * Adds a teacher to the course.
     *
     * @param teacher the teacher
     */
    public Course addTeacherToCourse(ECourseUser teacher, Course course) {
        course.addTeacher(teacher);
        return courseRepository.save(course);
    }
}
