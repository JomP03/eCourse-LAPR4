package jpa;

import coursemanagement.domain.Course;
import ecourseusermanagement.domain.ECourseUser;
import enrollmentrequestmanagement.domain.EnrollmentRequest;
import enrollmentrequestmanagement.domain.EnrollmentRequestState;
import enrollmentrequestmanagement.repository.EnrollmentRequestRepository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;

public class JpaEnrollmentRequestRepository extends eCourseJpaRepositoryBase<EnrollmentRequest, Long, Long> implements EnrollmentRequestRepository {


    /**
     * Instantiates a new Jpa enrollment request repository.
     *
     * @param identityFieldName the identity field name
     */
    public JpaEnrollmentRequestRepository(String identityFieldName) {
        super(identityFieldName);
    }

    /**
     * This method is used to create a query to retrieve an enrollment request by course and student.
     *
     * @param course  compare
     * @param student to compare
     * @return an optional with the enrollment request
     */
    @Override
    public Optional<EnrollmentRequest> findByCourseECourseUser(Course course, ECourseUser student) {
        final TypedQuery<EnrollmentRequest> query = entityManager().createQuery("SELECT err " +
                "FROM EnrollmentRequest err WHERE err.course.courseCode = :courseCode " +
                "AND err.eCourseUser.email = :email", EnrollmentRequest.class);
        query.setParameter("courseCode", course.identity());
        query.setParameter("email", student.email());

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    /**
     * This method is used to create a query to retrieve an iterable if enrollment requests by course and state.
     *
     * @param course the course to be compared
     * @param state the state to be compared
     * @return an iterable with the enrollment requests
     */
    @Override
    public Iterable<EnrollmentRequest> findByCourseAndState(Course course, EnrollmentRequestState state) {
        final TypedQuery<EnrollmentRequest> query = entityManager().createQuery("SELECT err " +
                "FROM EnrollmentRequest err WHERE err.course.courseCode = :courseCode " +
                "AND err.enrollmentRequestState = :state", EnrollmentRequest.class);
        query.setParameter("courseCode", course.identity());
        query.setParameter("state", state);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Iterable<Course> findCoursesStudentRequestedEnrollment(ECourseUser student) {
        final TypedQuery<Course> query = entityManager().createQuery("SELECT err.course " +
                "FROM EnrollmentRequest err WHERE err.eCourseUser.email = :email ",
                Course.class);
        query.setParameter("email", student.email());

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

}
