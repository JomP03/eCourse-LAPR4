package coursemanagement.application;

import persistence.PersistenceContext;

public class UserCoursesProviderFactory implements IUserCoursesProviderFactory {
    @Override
    public IUserCoursesProvider createListTeacherCoursesService() {
        return new TeacherCoursesProvider(PersistenceContext.repositories().courses());
    }

    @Override
    public IUserCoursesProvider createListStudentCoursesService() {
        return new StudentCoursesProvider(PersistenceContext.repositories().enrolledStudents());
    }
}
