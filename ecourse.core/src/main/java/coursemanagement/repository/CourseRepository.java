package coursemanagement.repository;

import coursemanagement.domain.Course;
import coursemanagement.domain.CourseCode;
import coursemanagement.domain.CourseState;
import eapli.framework.domain.repositories.DomainRepository;
import ecourseusermanagement.domain.ECourseUser;

public interface CourseRepository extends DomainRepository<CourseCode, Course> {

    /**
     * Finds a course by its code
     *
     * @param code - course code
     * @return course
     */
    Course findByCode(String code);


    /**
     * Finds all courses depending on the state
     *
     * @param state - course state
     * @return all courses
     */
    Iterable<Course> findCoursesByState(CourseState ...state);

    /**
     * Finds all available courses for a teacher
     *
     * @param user - teacher
     * @return all available courses
     */
    Iterable<Course> findTeacherCourses(ECourseUser user);

    /*
     * Finds all courses, a student is enrolled to
     *
     * @param user - student
     *@return all courses
     */
    Iterable<Course> findStudentCourses(ECourseUser user);

    /**
     * Finds all courses teacher is enrolled that are active.
     *
     * @return all the courses teacher is enrolled that are active.
     */
    Iterable<Course> findTeacherActiveCourses(ECourseUser eCourseUser);

    /**
     * Finds all not closed courses without a teacher in charge.
     *
     * @return the iterable
     */
    Iterable<Course> findAllNotClosedCoursesWithoutTeacherInCharge();

    /**
     * Finds all not closed courses.
     *
     * @return the iterable
     */
    Iterable<Course> findAllNotClosedCourses();

    /**
     * Finds all the students from a course
     *
     * @param courseCode - course code
     * @return all students from a course
     */
    Iterable<ECourseUser> findStudentsFromCourse(String courseCode);
}