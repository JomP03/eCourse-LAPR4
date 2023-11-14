package enrollmentrequestmanagement.application;

import coursemanagement.application.coursestate.ListCourseByStateService;
import coursemanagement.domain.Course;
import coursemanagement.domain.CourseState;
import ecourseusermanagement.domain.ECourseUser;
import enrolledstudentmanagement.application.ListCourseStudentsController;
import enrolledstudentmanagement.repository.EnrolledStudentRepository;
import enrollmentrequestmanagement.domain.EnrollmentRequest;
import enrollmentrequestmanagement.repository.EnrollmentRequestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseEnrollmentService {

    private final EnrollmentRequestRepository enrollmentRequestRepository;

    private final EnrolledStudentRepository enrolledStudentRepository;

    private final ListCourseByStateService listCourseByStateService;

    private final ListCourseStudentsController listCourseStudentsController;

    /**
     * Instantiates a new Course enrollment service.
     *
     * @param enrollmentRequestRepository the enrollment request repository, using dependency injection
     */
    public CourseEnrollmentService(EnrollmentRequestRepository enrollmentRequestRepository,
                                   EnrolledStudentRepository enrolledStudentRepository,
                                   ListCourseByStateService listCourseByStateService,
                                   ListCourseStudentsController listCourseStudentsController) {
        // verify if the enrollmentRequestRepository is null
        if (enrollmentRequestRepository == null) {
            throw new IllegalArgumentException("The enrollmentRequestRepository cannot be null.");
        }

        this.enrollmentRequestRepository = enrollmentRequestRepository;

        // verify if the enrolledStudentRepository is null
        if (enrolledStudentRepository == null) {
            throw new IllegalArgumentException("The enrolledStudentRepository cannot be null.");
        }

        this.enrolledStudentRepository = enrolledStudentRepository;

        this.listCourseByStateService = listCourseByStateService;

        this.listCourseStudentsController = listCourseStudentsController;
    }

    /**
     * Find by course and eCourse user.
     *
     * @param course the course
     * @param student the student
     * @return the boolean (success or failure)
     */
    public Optional<EnrollmentRequest> findByCourseECourseUser(Course course, ECourseUser student) {
        return enrollmentRequestRepository.findByCourseECourseUser(course, student);
    }

    private Iterable<Course> findCoursesStudentRequestedEnrollment(ECourseUser student) {
        return enrollmentRequestRepository.findCoursesStudentRequestedEnrollment(student);
    }

    private Iterable<Course> findCoursesStudentEnrolled(ECourseUser student) {
        return enrolledStudentRepository.findCoursesStudentEnrolled(student);
    }

    /**
     * Find available courses for student to request enrollment (student is not enrolled and has not requested enrollment)
     *
     * @param student the student
     * @return the iterable
     */
    public Iterable<Course> findAvailableCoursesForStudentToRequestEnrollment(ECourseUser student) {
        // All courses that are open for enrollment
        List<Course> enrollStateCourses = (List<Course>) listCourseByStateService.findCoursesByState(CourseState.ENROLL);

        // All courses that are open for enrollment and are not full
        List<Course> enrollStateCoursesNotFull = new ArrayList<>();

        for (Course course : enrollStateCourses) {
            List<ECourseUser> enrolledStudents =
                    (List<ECourseUser>) listCourseStudentsController.getStudentsFromCourse(course.identity().toString());

            if (enrolledStudents.size() < course.maxNrEnrolledStudents()) enrollStateCoursesNotFull.add(course);
        }

        // All courses that the student has requested enrollment or is already enrolled
        List<Course> coursesStudentRequestedEnrollment = (List<Course>) findCoursesStudentRequestedEnrollment(student);
        List<Course> coursesStudentEnrolled = (List<Course>) findCoursesStudentEnrolled(student);

        // Must remove the courses that the student has requested enrollment or is already enrolled
        enrollStateCoursesNotFull.removeAll(coursesStudentRequestedEnrollment);
        enrollStateCoursesNotFull.removeAll(coursesStudentEnrolled);

        return enrollStateCoursesNotFull;
    }

}
