package classmanagement.application.list;

import classmanagement.domain.RecurrentClass;
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

public class ListAvailableSlotsForUpdateController {

    private final ClassScheduler classScheduler;

    private final UserSessionService userSessionService;

    private final Optional<ECourseUser> eCourseUser;

    /**
     * @param classRepository the class repository
     */
    public ListAvailableSlotsForUpdateController(ClassRepository classRepository, IeCourseUserRepository ieCourseUserRepository){
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
     * @return the available slots for the update of a class
     */
    public Map<RecurrentClassWeekDay, Map<LocalTime,LocalTime>> getAvailableUpdateSlots(Iterable<Course> teacherCourses,
                                                                                        RecurrentClass recurrentClass,
                                                                                        LocalDateTime startTime,
                                                                                        LocalDateTime endTime,
                                                                                        LocalDateTime classOldDate){

        return classScheduler.getAvailableUpdateSlots(teacherCourses, recurrentClass, startTime, endTime,
                classOldDate, this.eCourseUser.get()
        );
    }
}
