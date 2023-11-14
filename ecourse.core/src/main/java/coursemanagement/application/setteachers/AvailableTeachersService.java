package coursemanagement.application.setteachers;

import coursemanagement.domain.Course;
import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.repositories.IeCourseUserRepository;

import java.util.List;

public class AvailableTeachersService implements IAvailableTeachersService {

    private final IeCourseUserRepository eCourseUserRepository;

    /**
     * Instantiates a new Available teachers service.
     *
     * @param eCourseUserRepository the e course user repository
     * @throws IllegalArgumentException if the user repository is null
     */
    public AvailableTeachersService(IeCourseUserRepository eCourseUserRepository) {
        if (eCourseUserRepository == null)
            throw new IllegalArgumentException("User repository must be provided.");
        this.eCourseUserRepository = eCourseUserRepository;
    }

    @Override
    public Iterable<ECourseUser> availableTeachers(Course course) {
        List<ECourseUser> allTeachers = (List<ECourseUser>) eCourseUserRepository.findAllTeachers();
        return filterNotAssignedTeachers(allTeachers, course.teachers());
    }

    private List<ECourseUser> filterNotAssignedTeachers(List<ECourseUser> allTeachers, List<ECourseUser> teachersInCourse) {
        allTeachers.removeAll(teachersInCourse);
        return allTeachers;
    }
}
