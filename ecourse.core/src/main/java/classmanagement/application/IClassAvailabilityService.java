package classmanagement.application;

import ecourseusermanagement.domain.ECourseUser;

import java.time.LocalDateTime;

/**
 * The interface Class availability service.
 */
public interface IClassAvailabilityService {

    /**
     * User has overlapping classes.
     *
     * @param dateTime the date time
     * @param duration the duration
     * @param user     the user
     * @return the boolean
     */
    boolean hasClass(LocalDateTime dateTime, Integer duration, ECourseUser user);

}
