package classmanagement.application;

import persistence.PersistenceContext;

public class UserClassesProviderFactory implements IUserClassesProviderFactory {
    @Override
    public IUserClassesProvider createListTeacherClassesService() {
        return new TeacherClassesProvider(PersistenceContext.repositories().classes());
    }

    @Override
    public IUserClassesProvider createListStudentClassesService() {
        return new StudentClassesProvider(PersistenceContext.repositories().classes());
    }
}
