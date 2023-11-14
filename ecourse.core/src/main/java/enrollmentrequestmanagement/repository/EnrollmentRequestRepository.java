package enrollmentrequestmanagement.repository;

import coursemanagement.domain.Course;
import eapli.framework.domain.repositories.DomainRepository;
import ecourseusermanagement.domain.ECourseUser;
import enrollmentrequestmanagement.domain.EnrollmentRequest;
import enrollmentrequestmanagement.domain.EnrollmentRequestState;

import java.util.Optional;

public interface EnrollmentRequestRepository extends DomainRepository<Long, EnrollmentRequest> {

    Optional<EnrollmentRequest> findByCourseECourseUser(Course course, ECourseUser student);

    Iterable<EnrollmentRequest> findByCourseAndState(Course course, EnrollmentRequestState state);

    Iterable<Course> findCoursesStudentRequestedEnrollment(ECourseUser student);

}
