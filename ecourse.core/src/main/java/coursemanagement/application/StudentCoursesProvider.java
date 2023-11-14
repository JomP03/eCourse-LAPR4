package coursemanagement.application;

import coursemanagement.domain.Course;
import ecourseusermanagement.domain.ECourseUser;
import enrolledstudentmanagement.repository.EnrolledStudentRepository;

public class StudentCoursesProvider implements IUserCoursesProvider {

    private final EnrolledStudentRepository enrolledStudentRepository;

    /**
     * Instantiates a new List student courses service.
     *
     * @param enrolledStudentRepository the enrolled student repository
     */
    public StudentCoursesProvider(EnrolledStudentRepository enrolledStudentRepository) {
        // Verify if courseRepository is null
        if (enrolledStudentRepository == null) {
            throw new IllegalArgumentException("Course repository cannot be null.");
        }

        this.enrolledStudentRepository = enrolledStudentRepository;
    }

    @Override
    public Iterable<Course> provideUserActiveCourses(ECourseUser user) {
        return enrolledStudentRepository.findStudentActiveCourses(user);
    }

    /**
     * Provide student in progress and closed courses iterable.
     *
     * @param student the student
     * @return the iterable
     */
    public Iterable<Course> provideStudentInProgressAndClosedCourses(ECourseUser student) {
        return enrolledStudentRepository.findStudentInProgressAndClosedCourses(student);
    }
}
