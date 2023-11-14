package jpa;

import coursemanagement.domain.Course;
import ecourseusermanagement.domain.ECourseUser;
import enrolledstudentmanagement.domain.EnrolledStudent;
import enrolledstudentmanagement.repository.EnrolledStudentRepository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;

public class JpaEnrolledStudentRepository extends eCourseJpaRepositoryBase<EnrolledStudent, Long, Long> implements EnrolledStudentRepository {

    /**
     * Instantiates a new Jpa enrolled student repository.
     *
     * @param identityFieldName the identity field name
     */
    public JpaEnrolledStudentRepository(String identityFieldName) {
        super(identityFieldName);
    }

    /**
     * This method is used to create a query to retrieve an enrolled student by course and student.
     *
     * @param course  compare
     * @param student to compare
     * @return an optional with the enrollment request
     */
    @Override
    public Optional<EnrolledStudent> findByCourseECourseUser(Course course, ECourseUser student) {
        final TypedQuery<EnrolledStudent> query = entityManager().createQuery("SELECT ers " +
                "FROM EnrolledStudent ers WHERE ers.course.courseCode = :courseCode " +
                "AND ers.eCourseUser.email = :email", EnrolledStudent.class);
        query.setParameter("courseCode", course.identity());
        query.setParameter("email", student.email());

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    /**
     * This method is used to verify if a student is already enrolled in a course.
     *
     * @param course  compare
     * @param student to compare
     * @return true if the student is already enrolled in the course, false otherwise
     */
    @Override
    public boolean isStudentAlreadyEnrolled(Course course, ECourseUser student) {
        return findByCourseECourseUser(course, student).isPresent();
    }

    /**
     * This method is used to create a query to retrieve all courses that a student is enrolled.
     *
     * @param student to compare
     * @return an iterable with the courses
     */
    @Override
    public Iterable<Course> findCoursesStudentEnrolled(ECourseUser student) {
        final TypedQuery<Course> query = entityManager().createQuery("SELECT es.course " +
                        "FROM EnrolledStudent es WHERE es.eCourseUser.email = :email ",
                Course.class);
        query.setParameter("email", student.email());

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Iterable<Course> findStudentActiveCourses(ECourseUser eCourseUser) {
        final TypedQuery<Course> query = entityManager().createQuery("SELECT es.course " +
                        "FROM EnrolledStudent es WHERE es.eCourseUser.email = :email " +
                        "AND (es.course.courseState = 'IN_PROGRESS' OR es.course.courseState = 'ENROLL')",Course.class);
        query.setParameter("email", eCourseUser.email());

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Optional<Course> isCourseFull(Course course) {
        final TypedQuery<Course> query = entityManager().createQuery("SELECT es.course " +
                        "FROM EnrolledStudent es WHERE es.course.courseCode = :courseCode " +
                        "AND es.course.maxNrEnrolledStudents.maxNrEnrolledStudents <= (SELECT COUNT(es2) " +
                        "FROM EnrolledStudent es2 " +
                        "WHERE es2.course.courseCode = :courseCode)", Course.class);

        query.setParameter("courseCode", course.identity());

        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }


    @Override
    public boolean doesCourseHasMinimumNumberEnrolledStudents(Course course) {
        final TypedQuery<Long> query = entityManager().createQuery("SELECT COUNT(es) " +
                        "FROM EnrolledStudent es WHERE es.course.courseCode = :courseCode",
                Long.class);

        query.setParameter("courseCode", course.identity());

        try {
            return query.getSingleResult() >= course.minNrEnrolledStudents();
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public Iterable<Course> findStudentInProgressAndClosedCourses(ECourseUser student) {
        final TypedQuery<Course> query = entityManager().createQuery("SELECT es.course " +
                        "FROM EnrolledStudent es WHERE es.eCourseUser.email = :email " +
                        "AND (es.course.courseState = 'IN_PROGRESS' OR es.course.courseState = 'CLOSED')",
                Course.class);
        query.setParameter("email", student.email());

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}

