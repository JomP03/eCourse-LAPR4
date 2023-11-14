package meetingmanagement.application.service;

import ecourseusermanagement.domain.ECourseUser;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The interface User availability service.
 */
public interface IUserAvailabilityService {

    /**
     * This method checks if the user is available for the given date time and duration.
     * If the user is available, it returns true, otherwise false.
     *
     * @param dateTime the date time
     * @param duration the duration
     * @param user the user
     * @return the boolean
     */
    boolean isUserAvailable(LocalDateTime dateTime, Integer duration, ECourseUser user,
                            List<ECourseUser> unavailableUsers);

}
