package classmanagement.application.extra;

import classmanagement.application.list.ListAvailableExtraSlotsController;
import classmanagement.domain.RecurrentClassWeekDay;
import classmanagement.domain.service.ClassScheduler;
import classmanagement.repository.ClassRepository;
import coursemanagement.application.listcourses.ListTeacherCoursesController;
import coursemanagement.domain.Course;
import coursemanagement.repository.CourseRepository;
import ecourseusermanagement.application.UserSessionService;
import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.repositories.IeCourseUserRepository;
import enrolledstudentmanagement.application.ListCourseStudentsController;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;


public class ScheduleExtraClassController {

    private final ClassScheduler classScheduler;

    private final UserSessionService userSessionService;

    private final ListTeacherCoursesController listTeacherCoursesController;

    private final ListAvailableExtraSlotsController listAvailableExtraSlotsController;

    private final ListCourseStudentsController listCourseStudentsController;

    private final Optional<ECourseUser> eCourseUser;

    /**
     * Instantiates a controller for scheduling a class.
     * Receives the class repository and class factory as parameter. (DI)
     *
     * @param classRepository the class repository
     */
    public ScheduleExtraClassController(ClassRepository classRepository, IeCourseUserRepository ieCourseUserRepository,
                                        CourseRepository courseRepository) {
        if(classRepository == null)
            throw new IllegalArgumentException("The class repository can not be null.");
        if(ieCourseUserRepository == null)
            throw new IllegalArgumentException("The user repository can not be null.");
        if(courseRepository == null)
            throw new IllegalArgumentException("The course repository can not be null.");

        this.classScheduler = new ClassScheduler(classRepository);
        this.userSessionService = new UserSessionService(ieCourseUserRepository);
        this.listTeacherCoursesController = new ListTeacherCoursesController(courseRepository, userSessionService);
        this.listAvailableExtraSlotsController = new ListAvailableExtraSlotsController(classRepository, ieCourseUserRepository);
        this.listCourseStudentsController = new ListCourseStudentsController(courseRepository);

        this.eCourseUser = userSessionService.getLoggedUser();
        if(this.eCourseUser.isEmpty())
            throw new IllegalStateException("eCourse Teacher must be registered.");

    }

    /**
     * Schedule an Extra Class.
     */
    public boolean scheduleExtraClass(Iterable<Course> courses, Course chosenCourse, LocalDateTime startWeekDay,
                                      LocalDateTime endWeekDay, RecurrentClassWeekDay day, LocalTime startTime,
                                      Integer duration, String title, Iterable<ECourseUser> participants) {

        return classScheduler.scheduleExtraClass(courses, chosenCourse, startWeekDay, endWeekDay, day,
                startTime, duration, title, participants, this.eCourseUser.get()
        );
    }

    /**
     * Returns a map with the available extra class slots for the given course, also without the slots where the teacher
     * is involved in another class of another course
     *
     * @param courses the courses
     * @param chosenCourse the chosen course
     * @param startDay from this day
     * @param endDay to this day
     * @return A map with the available extra class slots for the schedule
     */
    public Map<RecurrentClassWeekDay, Map<LocalTime,LocalTime>> getAvailableExtraClassSlots(Iterable<Course> courses,
                                                                                            Course chosenCourse,
                                                                                            LocalDateTime startDay,
                                                                                            LocalDateTime endDay) {

        return listAvailableExtraSlotsController.getAvailableExtraSlots(courses, chosenCourse, startDay, endDay);
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
     * Returns all students enrolled in a course
     * @param courseCode course code
     * @return all students enrolled in a course
     */
    public Iterable<ECourseUser> getStudentsFromCourse(String courseCode) {
        return listCourseStudentsController.getStudentsFromCourse(courseCode);
    }

    public boolean validateClassTitle(String title) {
        return classScheduler.validateClassTitle(title);
    }
}
