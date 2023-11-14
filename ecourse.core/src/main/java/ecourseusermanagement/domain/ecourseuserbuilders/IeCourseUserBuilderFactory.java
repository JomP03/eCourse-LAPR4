package ecourseusermanagement.domain.ecourseuserbuilders;

/**
 * The interface for the factory of eCourseUser builders.
 * This interface is used to create a new builder for a specific type of eCourseUser.
 */
public interface IeCourseUserBuilderFactory {

    /**
     * Creates a new builder for the eCourseUser that is a student
     *
     * @return the builder
     */
    StudentBuilder createStudentBuilder();

    /**
     * Creates a new builder for the eCourseUser that is a teacher
     *
     * @return the builder
     */
    TeacherBuilder createTeacherBuilder();

    /**
     * Creates a new builder for the eCourseUser that is a manager
     *
     * @return the builder
     */
    ManagerBuilder createManagerBuilder();
}
