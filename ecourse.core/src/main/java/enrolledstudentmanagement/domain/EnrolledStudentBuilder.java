package enrolledstudentmanagement.domain;

import coursemanagement.domain.Course;
import ecourseusermanagement.domain.ECourseUser;
import enrolledstudentmanagement.repository.EnrolledStudentRepository;

public class EnrolledStudentBuilder {

    final EnrolledStudentRepository enrolledStudentRepository;

    private Course course;

    private ECourseUser eCourseUser;

    /**
     * Instantiates a new Enrollment request builder.
     *
     * @param enrolledStudentRepository the enrollment request repository
     */
    public EnrolledStudentBuilder(EnrolledStudentRepository enrolledStudentRepository) {
        // verify if the enrollmentRequestRepository is null
        if (enrolledStudentRepository == null) {
            throw new IllegalArgumentException("The repo cannot be null.");
        }

        this.enrolledStudentRepository = enrolledStudentRepository;

    }

    /**
     * With course enrolled student builder.
     *
     * @param course the course
     * @return the enrolled student builder
     */
    public EnrolledStudentBuilder withCourse(Course course) {
        this.course = course;
        return this;
    }

    /**
     * With e course user enrolled student builder.
     *
     * @param eCourseUser the e course user
     * @return the enrolled student builder
     */
    public EnrolledStudentBuilder withECourseUser(ECourseUser eCourseUser) {
        this.eCourseUser = eCourseUser;
        return this;
    }

    /**
     * Build an enrolled student, with course and eCourse user.
     * This method is also making sure that the enrolled student is not already enrolled in the course.
     *
     * @return the enrolled student
     */
    public EnrolledStudent build() {
        // verify if there is already an enrolled student for the selected course and eCourse user
        if (enrolledStudentRepository.isStudentAlreadyEnrolled(this.course, this.eCourseUser)) {
            throw new IllegalArgumentException("The student is already enrolled in the course.");
        }

        if (enrolledStudentRepository.isCourseFull(this.course).isPresent()) {
            throw new IllegalArgumentException("The course is full.");
        }

        return new EnrolledStudent(this.course, this.eCourseUser);
    }

}
