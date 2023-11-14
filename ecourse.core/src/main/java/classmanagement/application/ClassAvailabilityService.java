package classmanagement.application;

import classmanagement.domain.ExtraClass;
import classmanagement.domain.RecurrentClass;
import coursemanagement.application.IUserCoursesProvider;
import coursemanagement.domain.Course;
import ecourseusermanagement.domain.ECourseUser;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ClassAvailabilityService implements IClassAvailabilityService {

    private final IUserCoursesProvider listUserCoursesService;

    private final IUserClassesProvider listUserClassesService;

    /**
     * Instantiates a new Class availability service.
     *
     * @param listUserCoursesService the list user courses service
     * @param listUserClassesService the list user classes service
     */
    public ClassAvailabilityService(IUserCoursesProvider listUserCoursesService,
                                    IUserClassesProvider listUserClassesService) {
        // Verify that the courseRepository is not null
        if (listUserCoursesService == null) {
            throw new IllegalArgumentException("courseRepository cannot be null");
        }

        this.listUserCoursesService = listUserCoursesService;

        // Verify that the listUserClassesService is not null
        if (listUserClassesService == null) {
            throw new IllegalArgumentException("listUserClassesService cannot be null");
        }

        this.listUserClassesService = listUserClassesService;
    }


    private LocalTime addDurationDate(LocalTime date, Integer duration) {
        return date.plusMinutes(duration);
    }

    /**
     * Checks if the event overlaps with the user's recurrent classes.
     *
     * @param userCourse the course to check
     * @param dateTime the date to check
     * @param duration the duration of the event
     * @param user the user to check
     * @return true if the event overlaps with the user's recurrent classes
     */
    private boolean isRecurrentClassOverlapping(Course userCourse, LocalDateTime dateTime, Integer duration,
                                                ECourseUser user) {
        List<RecurrentClass> userRecurrentClasses =
                (List<RecurrentClass>) listUserClassesService.listUserRecurrentClasses(userCourse, dateTime, user);

        if (userRecurrentClasses.isEmpty()) return false;

        boolean isOverlapping = false;

        for (RecurrentClass userRecurrentClass : userRecurrentClasses) {
            // Verify if the recurrent class is on the same day as the meeting
            if (!userRecurrentClass.getWeekDay().toString().equals(dateTime.getDayOfWeek().toString())) {
                continue;
            }

            // Start and end times for the recurrent class
            LocalTime userRecurrentClassStartTime = userRecurrentClass.getClassTime();
            LocalTime userRecurrentClassEndTime = addDurationDate(userRecurrentClassStartTime,
                    userRecurrentClass.getClassDuration());

            // Start and end times for the meeting to check
            LocalTime toCheckStartTime = dateTime.toLocalTime();
            LocalTime toCheckEndTime = addDurationDate(toCheckStartTime, duration);

            // Check if the start and end times are both before the user recurrent class start time
            if (toCheckStartTime.isBefore(userRecurrentClassStartTime) &&
                    toCheckEndTime.isBefore(userRecurrentClassStartTime)) {
                isOverlapping =  true;
            }

            // Check if the start and end times are both after the user recurrent class end time
            if (toCheckStartTime.isAfter(userRecurrentClassEndTime)) {
                isOverlapping =  true;
            }
        }

        return isOverlapping;
    }

    /**
     * Checks if the event overlaps with the user's extra classes.
     *
     * @param userCourse the course to check
     * @param dateTime the date to check
     * @param duration the duration of the event
     * @param user the user to check
     * @return true if the event overlaps with the user's recurrent classes
     */
    private boolean isExtraClassOverlapping(Course userCourse, LocalDateTime dateTime, Integer duration,
                                            ECourseUser user) {
        List<ExtraClass> userExtraClasses =
                (List<ExtraClass>) listUserClassesService.listUserExtraClasses(userCourse, dateTime, user);

        if (userExtraClasses.isEmpty()) return false;

        boolean isOverlapping = false;

        for (ExtraClass userExtraClass : userExtraClasses) {
            // Verify if the extra class is on the same day as the meeting
            if (!userExtraClass.getsDate().toLocalDate().equals(dateTime.toLocalDate())) {
                continue;
            }

            // Start and end times for the extra class
            LocalTime userExtraClassStartTime = userExtraClass.getsDate().toLocalTime();
            LocalTime userExtraClassEndTime = addDurationDate(userExtraClassStartTime,
                    userExtraClass.getClassDuration());

            // Start and end times to check
            LocalTime toCheckStartTime = dateTime.toLocalTime();
            LocalTime toCheckEndTime = addDurationDate(toCheckStartTime, duration);

            // Check if the start and end times are both before the user extra class start time
            if (toCheckStartTime.isBefore(userExtraClassStartTime) &&
                    toCheckEndTime.isBefore(userExtraClassStartTime)) {
                isOverlapping = true;
            }

            // Check if the start and end times are both after the user extra class end time
            if (toCheckStartTime.isAfter(userExtraClassEndTime)) {
                isOverlapping = true;
            }
        }

        return isOverlapping;
    }

    @Override
    public boolean hasClass(LocalDateTime dateTime, Integer duration, ECourseUser user) {
        // Get the student courses
        List<Course> userCourses = (List<Course>) listUserCoursesService.provideUserActiveCourses(user);

        if (userCourses.isEmpty()) return false;

        boolean isOverlapping = false;

        // Iterate through the student courses
        for (Course userCourse : userCourses) {
            // Check if the student course has overlapping classes
            if (isRecurrentClassOverlapping(userCourse, dateTime, duration, user) ||
                    isExtraClassOverlapping(userCourse, dateTime, duration, user)) {
                isOverlapping = true;
            }
        }

        return isOverlapping;
    }

}
