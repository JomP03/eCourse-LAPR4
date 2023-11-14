package jpa;

import coursemanagement.domain.Course;
import coursemanagement.domain.CourseCode;
import coursemanagement.domain.CourseState;
import coursemanagement.repository.CourseRepository;
import ecourseusermanagement.domain.ECourseUser;

import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;

public class JpaCourseRepository extends eCourseJpaRepositoryBase<Course, CourseCode, CourseCode> implements CourseRepository {

    public JpaCourseRepository() {
        super("courseCode");
    }

    @Override
    public Course findByCode(String courseCode) {
        final TypedQuery<Course> query = entityManager().createQuery(
                "SELECT c FROM Course c WHERE c.courseCode.courseCode = :code", Course.class);
        query.setParameter("code", courseCode);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Iterable<Course> findCoursesByState(CourseState... state) {
        List<CourseState> stateList = Arrays.asList(state);

        final TypedQuery<Course> query = entityManager().createQuery(
                "SELECT c FROM Course c WHERE c.courseState IN :state", Course.class);
        query.setParameter("state", stateList);

        return query.getResultList();
    }

    /*
     * Finds all available courses for a teacher
     *
     * @param user - Session user
     * @return all available courses
     */
    @Override
    public Iterable<Course> findTeacherCourses(ECourseUser user) {
        final TypedQuery<Course> query = entityManager().createQuery(
                "SELECT c FROM Course c WHERE :user MEMBER OF c.courseTeachers", Course.class);
        query.setParameter("user", user);

        return query.getResultList();
    }

    /*
     * Finds all courses, a student is enrolled to
     *
     * @param user - student
     * @return all courses
     */
    @Override
    public Iterable<Course> findStudentCourses(ECourseUser user) {
        final TypedQuery<Course> query = entityManager().createQuery(
                "SELECT s.course FROM EnrolledStudent s WHERE s.eCourseUser = :user", Course.class);
        query.setParameter("user", user);

        return query.getResultList();
    }

    @Override
    public Iterable<Course> findTeacherActiveCourses(ECourseUser eCourseUser) {
        final TypedQuery<Course> query = entityManager().createQuery(
                "SELECT c FROM Course c " +
                        "INNER JOIN c.courseTeachers t " +
                        "WHERE t.id = :user " +
                        "AND c.courseState != 'CLOSED'",
                Course.class);
        query.setParameter("user", eCourseUser.identity());

        try {
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Iterable<Course> findAllNotClosedCoursesWithoutTeacherInCharge() {
        final TypedQuery<Course> query = entityManager().createQuery(
                "SELECT c FROM Course c WHERE c.courseState <> 'CLOSED' AND c.teacherInCharge IS NULL", Course.class);

        return query.getResultList();
    }

    @Override
    public Iterable<Course> findAllNotClosedCourses() {
        final TypedQuery<Course> query = entityManager().createQuery(
                "SELECT c FROM Course c WHERE c.courseState <> 'CLOSED'", Course.class);

        return query.getResultList();
    }

    @Override
    public Iterable<ECourseUser> findStudentsFromCourse(String courseCode) {
        final TypedQuery<ECourseUser> query = entityManager().createQuery(
                "SELECT s.eCourseUser FROM EnrolledStudent s WHERE s.course.courseCode.courseCode = :code AND s.eCourseUser.userState = 'DISABLED'", ECourseUser.class);
        query.setParameter("code", courseCode);

        return query.getResultList();
    }
}