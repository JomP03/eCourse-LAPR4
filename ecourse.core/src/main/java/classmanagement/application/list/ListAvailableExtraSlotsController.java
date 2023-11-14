package classmanagement.application.list;

import classmanagement.domain.RecurrentClassWeekDay;
import classmanagement.domain.service.ClassScheduler;
import classmanagement.repository.ClassRepository;
import coursemanagement.domain.Course;
import ecourseusermanagement.application.UserSessionService;
import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.repositories.IeCourseUserRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;

public class ListAvailableExtraSlotsController {

    private final ClassScheduler classScheduler;

    private final UserSessionService userSessionService;

    private final Optional<ECourseUser> eCourseUser;

    /**
     * @param classRepository the class repository
     */
    public ListAvailableExtraSlotsController(ClassRepository classRepository, IeCourseUserRepository ieCourseUserRepository){
        if(classRepository == null)
            throw new IllegalArgumentException("The class repository can not be null.");
        if(ieCourseUserRepository == null)
            throw new IllegalArgumentException("The user repository can not be null.");

        this.classScheduler = new ClassScheduler(classRepository);
        this.userSessionService = new UserSessionService(ieCourseUserRepository);

        this.eCourseUser = userSessionService.getLoggedUser();
        if(this.eCourseUser.isEmpty())
            throw new IllegalStateException("eCourse Teacher must be registered.");
    }

    /**
     * Returns a map with the available extra class slots for the given course, also without the slots where the
     * teacher is involved in another class of another course
     *
     * @param courses the courses
     * @param chosenCourse the chosen course
     * @param startDay from this day
     * @param endDay to this day
     * @return A map with the available extra class slots for the schedule
     */
    public Map<RecurrentClassWeekDay, Map<LocalTime,LocalTime>> getAvailableExtraSlots(Iterable<Course> courses,
                                                                                       Course chosenCourse,
                                                                                       LocalDateTime startDay,
                                                                                       LocalDateTime endDay){

        return classScheduler.getAvailableExtraClassSlots(courses, chosenCourse, startDay, endDay, this.eCourseUser.get());
    }
}
