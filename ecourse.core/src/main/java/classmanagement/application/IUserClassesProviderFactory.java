package classmanagement.application;

/**
 * The interface List user classes factory.
 */
public interface IUserClassesProviderFactory {

    /**
     * Create list teacher classes service.
     *
     * @return the list user classes service
     */
    IUserClassesProvider createListTeacherClassesService();

    /**
     * Create list student classes service.
     *
     * @return the list user classes service
     */
    IUserClassesProvider createListStudentClassesService();

}
