package meetingmanagement.domain;

import ecourseusermanagement.domain.ECourseUser;

import java.time.LocalDateTime;

/**
 * The interface used to manage the creation of a meeting.
 */
public interface IMeetingManager {

    /**
     * Schedule a meeting.
     *
     * @param meetingOwner  the meeting owner
     * @param meetingDate   the meeting date
     * @param meetingDuration the meeting duration
     * @param usersToInvite the users to invite
     *
     * @return the meeting details
     */
    String scheduleMeeting(ECourseUser meetingOwner, LocalDateTime meetingDate, Integer meetingDuration,
                            Iterable<ECourseUser> usersToInvite);

    /**
     * Cancel a meeting.
     *
     * @param meeting the meeting to be canceled
     */
    void cancelMeeting(Meeting meeting);

}
