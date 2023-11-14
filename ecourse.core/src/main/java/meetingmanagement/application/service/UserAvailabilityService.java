package meetingmanagement.application.service;

import classmanagement.application.ClassAvailabilityService;
import classmanagement.application.IClassAvailabilityService;
import classmanagement.application.IUserClassesProviderFactory;
import classmanagement.application.IUserClassesProvider;
import coursemanagement.application.IUserCoursesProviderFactory;
import coursemanagement.application.IUserCoursesProvider;
import ecourseusermanagement.domain.ECourseUser;

import java.time.LocalDateTime;
import java.util.List;

public class UserAvailabilityService implements IUserAvailabilityService {

    private final IMeetingAvailabilityService meetingAvailabilityService;

    private final IUserClassesProviderFactory listUserClassesFactory;

    private final IUserCoursesProviderFactory listUserCoursesFactory;

    private IUserClassesProvider listUserClassesService;

    private IUserCoursesProvider listUserCoursesService;

    /**
     * Instantiates a new User availability service.
     *
     * @param meetingAvailabilityService the meeting availability service
     * @param listUserClassesFactory     the list user classes factory
     * @param listUserCoursesFactory     the list user courses factory
     */
    public UserAvailabilityService(IMeetingAvailabilityService meetingAvailabilityService,
                                   IUserClassesProviderFactory listUserClassesFactory,
                                   IUserCoursesProviderFactory listUserCoursesFactory) {
        // Verify that the meetingAvailabilityService is not null
        if (meetingAvailabilityService == null) {
            throw new IllegalArgumentException("meetingAvailabilityService cannot be null");
        }

        this.meetingAvailabilityService = meetingAvailabilityService;

        // Verify that the listUserClassesFactory is not null
        if (listUserClassesFactory == null) {
            throw new IllegalArgumentException("listUserClassesFactory cannot be null");
        }

        this.listUserClassesFactory = listUserClassesFactory;

        // Verify that the listUserCoursesFactory is not null
        if (listUserCoursesFactory == null) {
            throw new IllegalArgumentException("listUserCoursesFactory cannot be null");
        }

        this.listUserCoursesFactory = listUserCoursesFactory;
    }

    private void classAvailabilityAccordingToRole(ECourseUser user) {
        // If the user is a student
        if (user.isStudent()) {
            listUserCoursesService = listUserCoursesFactory.createListStudentCoursesService();
            listUserClassesService = listUserClassesFactory.createListStudentClassesService();
        }

        // If the user is a teacher
        else if (user.isTeacher()) {
            listUserCoursesService = listUserCoursesFactory.createListTeacherCoursesService();
            listUserClassesService = listUserClassesFactory.createListTeacherClassesService();
        }

    }

    @Override
    public boolean isUserAvailable(LocalDateTime dateTime, Integer duration, ECourseUser user,
                                   List<ECourseUser> unavailableUsers) {

        // Verify if the user is a manager
        if (!user.isStudent() && !user.isTeacher()) {
            if (!meetingAvailabilityService.hasMeeting(dateTime, duration, user)) {
                return true;
            }

            unavailableUsers.add(user);
            return false;
        }

        // Get the listUserClassesService and listUserCoursesService according to the user role
        classAvailabilityAccordingToRole(user);

        IClassAvailabilityService classAvailabilityService = new ClassAvailabilityService(listUserCoursesService,
                listUserClassesService);

        if (!meetingAvailabilityService.hasMeeting(dateTime, duration, user) && !classAvailabilityService
                .hasClass(dateTime, duration, user)) {
            return true;
        }

        unavailableUsers.add(user);
        return false;
    }
}
