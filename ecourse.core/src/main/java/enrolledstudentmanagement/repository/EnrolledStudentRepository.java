package enrolledstudentmanagement.repository;

import coursemanagement.domain.Course;
import eapli.framework.domain.repositories.DomainRepository;
import ecourseusermanagement.domain.ECourseUser;
import enrolledstudentmanagement.domain.EnrolledStudent;

import java.util.Optional;

public interface EnrolledStudentRepository extends DomainRepository<Long, EnrolledStudent> {

    /**
     * Find by course e course user optional.
     *
     * @param course  the course
     * @param student the student
     * @return the optional
     */
    Optional<EnrolledStudent> findByCourseECourseUser(Course course, ECourseUser student);

    /**
     * Is student already enrolled boolean.
     *
     * @param course  the course
     * @param student the student
     * @return the boolean
     */
    boolean isStudentAlreadyEnrolled(Course course, ECourseUser student);

    /**
     * Find courses student enrolled iterable.
     *
     * @param student the student
     * @return the iterable
     */
    Iterable<Course> findCoursesStudentEnrolled(ECourseUser student);

    /**
     * Find student active courses iterable.
     *
     * @param eCourseUser the e course user
     * @return the iterable
     */
    Iterable<Course> findStudentActiveCourses(ECourseUser eCourseUser);

    /**
     * Find if the course is full boolean.
     *
     * @param course the course
     * @return the boolean
     */
    Optional<Course> isCourseFull(Course course);


    /**
     * Find if the course has the minimum number of enrolled students boolean.
     *
     * @param course the course
     * @return the boolean
     */
    boolean doesCourseHasMinimumNumberEnrolledStudents(Course course);

    /**
     * Find in progress and closed courses from a student.
     * @param student the student
     * @return the iterable
     */
    Iterable<Course> findStudentInProgressAndClosedCourses(ECourseUser student);
}
