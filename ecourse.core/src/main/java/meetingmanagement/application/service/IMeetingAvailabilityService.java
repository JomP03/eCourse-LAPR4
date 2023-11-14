package meetingmanagement.application.service;

import ecourseusermanagement.domain.ECourseUser;

import java.time.LocalDateTime;

public interface IMeetingAvailabilityService {

    /**
     * User has overlapping meetings.
     *
     * @param dateTime the date time
     * @param duration the duration
     * @param user the user
     * @return the boolean
     */
    boolean hasMeeting(LocalDateTime dateTime, Integer duration, ECourseUser user);

}
