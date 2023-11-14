package classmanagement.application.list;

import classmanagement.domain.service.ClassScheduler;
import classmanagement.domain.RecurrentClassWeekDay;
import classmanagement.repository.ClassRepository;
import coursemanagement.domain.Course;
import ecourseusermanagement.application.UserSessionService;
import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.repositories.IeCourseUserRepository;

import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;

public class ListAvailableRecurrentClassSlotsController {

    private final ClassScheduler classScheduler;

    private final UserSessionService userSessionService;

    private final Optional<ECourseUser> eCourseUser;

    /**
     * @param classRepository the class repository
     */
    public ListAvailableRecurrentClassSlotsController(ClassRepository classRepository,
                                                      IeCourseUserRepository ieCourseUserRepository){

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
     *  Returns a map with the available recurrent class slots for the given course and removed slots where the teacher is involved in another class of the
     *
     * @param courses the courses
     * @param chosenCourse the chosen course
     * @return A map with the available recurrent class slots for the schedule
     */
    public Map<RecurrentClassWeekDay, Map<LocalTime,LocalTime>> getAllAvailableRecurrentSlots(Iterable<Course> courses,
                                                                                              Course chosenCourse){

        return classScheduler.getAvailableCourseTeacherIntersectionRecurrentSlots(courses, this.eCourseUser.get(), chosenCourse);
    }



}
