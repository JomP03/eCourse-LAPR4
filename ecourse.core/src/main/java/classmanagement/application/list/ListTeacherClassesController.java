package classmanagement.application.list;

import classmanagement.domain.RecurrentClass;
import classmanagement.repository.ClassRepository;
import ecourseusermanagement.application.UserSessionService;
import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.repositories.IeCourseUserRepository;

import java.util.Optional;

public class ListTeacherClassesController {

    private final ClassRepository classRepository;

    private final UserSessionService userSessionService;

    private final Optional<ECourseUser> eCourseUser;

    /**
     * Instantiates a controller for listing the classes of a teacher.
     * Receives the class repository as parameter. (DI)
     *
     * @param classRepository the class repository
     */
    public ListTeacherClassesController(ClassRepository classRepository, IeCourseUserRepository ieCourseUserRepository) {
        if (classRepository == null)
            throw new IllegalArgumentException("The class repository can not be null.");

        this.classRepository = classRepository;
        this.userSessionService = new UserSessionService(ieCourseUserRepository);

        this.eCourseUser = userSessionService.getLoggedUser();
        if(this.eCourseUser.isEmpty())
            throw new IllegalStateException("eCourse Teacher must be registered.");

    }

    /**
     * Lists the classes of a teacher.
     *
     * @return the classes of a teacher
     */
    public Iterable<RecurrentClass> getTeacherClasses() {
        return classRepository.findTeacherRecurrentClasses(this.eCourseUser.get());
    }
}