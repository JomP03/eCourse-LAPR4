package classmanagement.repository;


import classmanagement.domain.Class;
import classmanagement.domain.ClassOccurrence;
import classmanagement.domain.ExtraClass;
import classmanagement.domain.RecurrentClass;
import coursemanagement.domain.Course;
import eapli.framework.domain.repositories.DomainRepository;
import ecourseusermanagement.domain.ECourseUser;

import java.time.LocalDateTime;

/**
 * The interface for a class repository.
 */
public interface ClassRepository extends DomainRepository<Long, Class> {

    /**
     * Find all recurrent classes of a course.
     *
     * @param courseCode the course code
     * @return the iterable of recurrent classes of the course
     */
    Iterable<RecurrentClass> findCourseRecurrentClasses(String courseCode);

    /**
     * Find by class title.
     * @param classTitle the class title
     * @return the optional
     */
    RecurrentClass findRecurrentClassByTitle(String classTitle);

    /**
     * Find course recurrent classes where teacher involved.
     *
     * @param courseCode the course code
     * @param user the user
     * @return the iterable
     */
    Iterable<RecurrentClass> findCourseRecurrentClassesWhereTeacherInvolved(String courseCode, ECourseUser user);


    /**
     * Find extra class by course code and date range.
     * @param courseCode the course code
     * @param startDay the start day
     * @param endDay the end day
     * @return the iterable
     */
    Iterable<ExtraClass> findExtraClassByCourseCodeAndDateRange(String courseCode, LocalDateTime startDay, LocalDateTime endDay);

    /**
     * Find extra class by date range and teacher with non matching course.
     * @param courseCode the course code
     * @param startDay the start day
     * @param endDay the end day
     * @param teacher the teacher
     * @return the iterable
     */
    Iterable<ExtraClass> findExtraClassByDateRangeAndTeacherWithNonMatchingCourse(String courseCode, LocalDateTime startDay, LocalDateTime endDay, ECourseUser teacher);

    /**
     * Find student extra class in specific date iterable.
     *
     * @param course      the course
     * @param student     the student
     * @return the iterable
     */
    Iterable<ExtraClass> findExtraClassesForStudent(Course course, ECourseUser student);

    /**
     * Find recurrent class in specific date for student iterable.
     *
     * @param course      the course
     * @return the iterable
     */
    Iterable<RecurrentClass> findRecurrentClassesForStudent(Course course);

    /**
     * Find recurrent class in specific date for teacher iterable.
     *
     * @param course      the course
     * @param teacher     the teacher
     * @return the iterable
     */
    Iterable<RecurrentClass> findRecurrentClassesForTeacher(Course course, ECourseUser teacher);


    /**
     * Find extra class in specific date for teacher iterable.
     *
     * @param course      the course
     * @param teacher     the teacher
     * @return the iterable
     */
    Iterable<ExtraClass> findExtraClassesForTeacher(Course course, ECourseUser teacher);
    /**
     * Find extra class by title.
     *
     * @param title the title
     * @return the extra class
     */
    ExtraClass findExtraClassByTitle(String title);

    /**
     * Find teacher recurrent classes iterable.
     *
     * @param eCourseUser the e course user
     * @return the iterable
     */
    Iterable<RecurrentClass> findTeacherRecurrentClasses(ECourseUser eCourseUser);
}
