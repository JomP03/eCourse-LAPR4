package classmanagement.application;

import classmanagement.domain.ExtraClass;
import classmanagement.domain.RecurrentClass;
import classmanagement.repository.ClassRepository;
import coursemanagement.domain.Course;
import ecourseusermanagement.domain.ECourseUser;

import java.time.LocalDateTime;

public class TeacherClassesProvider implements IUserClassesProvider {

    private final ClassRepository classRepository;

    /**
     * Instantiates a new List student classes.
     *
     * @param classRepository the class repository
     */
    public TeacherClassesProvider(ClassRepository classRepository) {
        // Verify that the classRepository is not null
        if (classRepository == null) {
            throw new IllegalArgumentException("The classRepository cannot be null.");
        }

        this.classRepository = classRepository;
    }

    @Override
    public Iterable<RecurrentClass> listUserRecurrentClasses(Course course, LocalDateTime meetingToCheckDate,
                                                             ECourseUser userToCheck) {
        return classRepository.findRecurrentClassesForTeacher(course, userToCheck);
    }

    @Override
    public Iterable<ExtraClass> listUserExtraClasses(Course course, LocalDateTime meetingToCheckDate,
                                                     ECourseUser userToCheck) {
        return classRepository.findExtraClassesForTeacher(course, userToCheck);
    }
}
