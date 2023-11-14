package enrolledstudentmanagement.application;

import coursemanagement.domain.Course;
import ecourseusermanagement.domain.ECourseUser;
import enrolledstudentmanagement.domain.EnrolledStudent;
import enrolledstudentmanagement.domain.EnrolledStudentBuilder;
import enrolledstudentmanagement.repository.EnrolledStudentRepository;

import java.util.Optional;

public class RegisterEnrolledStudentController {

    private final EnrolledStudentRepository enrolledStudentRepository;

    /**
     * Instantiates a new Register enrolled student controller.
     *
     * @param enrolledStudentRepository the enrolled student repository
     */
    public RegisterEnrolledStudentController(EnrolledStudentRepository enrolledStudentRepository) {
        // verify if the enrolledStudentRepository is null
        if (enrolledStudentRepository == null) {
            throw new IllegalArgumentException("The enrolledStudentRepository cannot be null.");
        }

        this.enrolledStudentRepository = enrolledStudentRepository;
    }

    /**
     * Register enrolled student.
     *
     * @param course      the course
     * @param eCourseUser the e course user
     * @return the optional to the enrolled student
     */
    public Optional<EnrolledStudent> registerEnrolledStudent(Course course, ECourseUser eCourseUser) {
        final EnrolledStudent enrolledStudent;
        try {
            // Create the enrolled student
             enrolledStudent = new EnrolledStudentBuilder(enrolledStudentRepository)
                            .withECourseUser(eCourseUser)
                            .withCourse(course)
                            .build();

        } catch (IllegalArgumentException e) {
            // If the enrolled student was not created, return an empty optional
            return Optional.empty();
        }

        // Verify if the enrolled student was created
        if (enrolledStudent == null) return Optional.empty();

        // Save the enrolled student
        enrolledStudentRepository.save(enrolledStudent);
        return Optional.of(enrolledStudent);
    }

}
