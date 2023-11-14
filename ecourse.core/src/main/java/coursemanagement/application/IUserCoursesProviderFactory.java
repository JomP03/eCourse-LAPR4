package coursemanagement.application;

/**
 * The interface List user courses factory.
 */
public interface IUserCoursesProviderFactory {

    /**
     * Create list teacher courses service.
     *
     * @return the list user courses service
     */
    IUserCoursesProvider createListTeacherCoursesService();

    /**
     * Create list student courses service.
     *
     * @return the list user courses service
     */
    IUserCoursesProvider createListStudentCoursesService();

}
