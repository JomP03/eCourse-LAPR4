package classmanagement.application.recurrent;

import classmanagement.application.list.ListAvailableRecurrentClassSlotsController;
import classmanagement.domain.RecurrentClassWeekDay;
import classmanagement.domain.service.ClassScheduler;
import classmanagement.repository.ClassRepository;
import coursemanagement.application.listcourses.ListTeacherCoursesController;
import coursemanagement.domain.Course;
import coursemanagement.repository.CourseRepository;
import ecourseusermanagement.application.UserSessionService;
import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.repositories.IeCourseUserRepository;

import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;


public class ScheduleRecurrentClassController {

    private final ClassScheduler classScheduler;

    private final UserSessionService userSessionService;

    private final ListAvailableRecurrentClassSlotsController listAvailableRecurrentClassSlotsController;

    private final ListTeacherCoursesController listTeacherCoursesController;

    private final Optional<ECourseUser> eCourseUser;

    /**
     * Instantiates a controller for scheduling a class.
     * Receives the class repository and class factory as parameter. (DI)
     *
     * @param classRepository the class repository
     */
    public ScheduleRecurrentClassController(ClassRepository classRepository, IeCourseUserRepository ieCourseUserRepository,
                                            CourseRepository courseRepository) {
        if(classRepository == null)
            throw new IllegalArgumentException("The class repository can not be null.");
        if(ieCourseUserRepository == null)
            throw new IllegalArgumentException("The user repository can not be null.");
        if(courseRepository == null)
            throw new IllegalArgumentException("The course repository can not be null.");

        this.classScheduler = new ClassScheduler(classRepository);
        this.userSessionService = new UserSessionService(ieCourseUserRepository);
        this.listAvailableRecurrentClassSlotsController = new ListAvailableRecurrentClassSlotsController(classRepository, ieCourseUserRepository);
        this.listTeacherCoursesController = new ListTeacherCoursesController(courseRepository, userSessionService);

        this.eCourseUser = userSessionService.getLoggedUser();
        if(this.eCourseUser.isEmpty())
            throw new IllegalStateException("eCourse Teacher must be registered.");
    }

    /**
     * Schedule a Recurrent Class.
     * @param classTitle the title of the class
     * @param classDuration the duration of the class
     * @param recurrentClassTime the time of the class (hour)
     * @param recurrentClassWeekDay the day of the week of the class
     * @param classCourse the course of the class
     */
    public boolean scheduleRecurrentClass(String classTitle, Integer classDuration, String recurrentClassTime,
                                          RecurrentClassWeekDay recurrentClassWeekDay, Course classCourse,
                                          Iterable<Course> courses) {

        return classScheduler.scheduleRecurrentClass(classTitle, classDuration, recurrentClassTime, recurrentClassWeekDay,
                classCourse, this.eCourseUser.get(),courses
        );
    }

    /**
     *  Returns a map with the available recurrent class slots for the given course, also without the slots where the
     *  teacher is involved in another class of another course
     *
     * @param courses the courses
     * @param chosenCourse the chosen course
     * @return A map with the available recurrent class slots for the schedule
     */
    public Map<RecurrentClassWeekDay, Map<LocalTime,LocalTime>> getAllAvailableRecurrentSlots(Iterable<Course> courses,
                                                                                              Course chosenCourse){

        return listAvailableRecurrentClassSlotsController.getAllAvailableRecurrentSlots(courses, chosenCourse);
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
     * Validates the class title.
     *
     * @param title the title
     * @return true, if successful
     */
    public boolean validateClassTitle(String title) {
        return classScheduler.validateClassTitle(title);
    }
}
