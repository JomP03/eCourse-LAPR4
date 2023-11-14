package classmanagement.application.recurrent;

import classmanagement.application.list.ListAvailableSlotsForUpdateController;
import classmanagement.application.list.ListTeacherClassesController;
import classmanagement.domain.RecurrentClass;
import classmanagement.domain.RecurrentClassWeekDay;
import classmanagement.domain.service.ClassScheduler;
import classmanagement.repository.ClassRepository;
import coursemanagement.application.listcourses.ListTeacherCoursesController;
import coursemanagement.domain.Course;
import coursemanagement.repository.CourseRepository;
import ecourseusermanagement.application.UserSessionService;
import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.repositories.IeCourseUserRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UpdateClassController {

    private final ClassScheduler classScheduler;

    private final UserSessionService userSessionService;

    private final ListTeacherClassesController listTeacherClassesController;

    private final ListAvailableSlotsForUpdateController listAvailableSlotsForUpdateController;

    private final ListTeacherCoursesController listTeacherCoursesController;

    private final Optional<ECourseUser> eCourseUser;

    /**
     * Instantiates a controller for updating a class.
     * Receives the class repository and class factory as parameter. (DI)
     *
     * @param classRepository the class repository
     * @param ieCourseUserRepository the user repository
     */
    public UpdateClassController(ClassRepository classRepository, IeCourseUserRepository ieCourseUserRepository,
                                 CourseRepository courseRepository) {
        if (classRepository == null)
            throw new IllegalArgumentException("The class repository can not be null.");
        if (ieCourseUserRepository == null)
            throw new IllegalArgumentException("The user repository can not be null.");
        if (courseRepository == null)
            throw new IllegalArgumentException("The course repository can not be null.");

        this.classScheduler = new ClassScheduler(classRepository);
        this.userSessionService = new UserSessionService(ieCourseUserRepository);
        this.listTeacherClassesController = new ListTeacherClassesController(classRepository, ieCourseUserRepository);
        this.listAvailableSlotsForUpdateController = new ListAvailableSlotsForUpdateController(classRepository, ieCourseUserRepository);
        this.listTeacherCoursesController = new ListTeacherCoursesController(courseRepository, userSessionService);

        this.eCourseUser = userSessionService.getLoggedUser();
        if(this.eCourseUser.isEmpty())
            throw new IllegalStateException("eCourse Teacher must be registered.");
    }

    /**
     * Finds Teacher's classes.
     * @return Teacher's classes
     */
    public Iterable<RecurrentClass> getTeacherClasses() {
        return listTeacherClassesController.getTeacherClasses();
    }

    /**
     * Returns an iterable with the available courses for the teacher
     *
     * @return An iterable with the available courses for the teacher
     */
    public Iterable<Course> getAvailableTeacherCourses() {
        return listTeacherCoursesController.findCourses();
    }

    /**
     * @return the available slots for the update of a class
     */
    public Map<RecurrentClassWeekDay, Map<LocalTime,LocalTime>> getAvailableUpdateSlots(Iterable<Course> teacherCourses,
                                                                                        RecurrentClass recurrentClass,
                                                                                        LocalDateTime startTime,
                                                                                        LocalDateTime endTime,
                                                                                        LocalDateTime classOldDate){

        return listAvailableSlotsForUpdateController.getAvailableUpdateSlots(teacherCourses, recurrentClass, startTime,
                endTime, classOldDate
        );
    }

    public boolean updateClass(Iterable<Course> teacherCourses, RecurrentClass selectedClass, LocalDateTime localDateTime,
                               LocalDateTime localDateTime1, RecurrentClassWeekDay day, LocalTime startTime,
                               LocalDateTime classOldDate, Integer duration) {

        return classScheduler.updateClass(teacherCourses, selectedClass, localDateTime, localDateTime1, day, startTime,
                classOldDate, this.eCourseUser.get(), duration
        );
    }

    public List<LocalDateTime> getClassOccurrences(RecurrentClass selectedClass) {
        return classScheduler.getClassOccurrences(selectedClass);
    }
}