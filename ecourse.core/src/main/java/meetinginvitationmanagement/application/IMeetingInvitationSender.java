package meetinginvitationmanagement.application;

import ecourseusermanagement.domain.ECourseUser;
import meetingmanagement.domain.Meeting;

public interface IMeetingInvitationSender {

    /**
     * Send an invitation to a user for the meeting.
     *
     * @param userToInvite the user to invite
     * @param meeting      the meeting
     */
    void sendInvitation(ECourseUser userToInvite, Meeting meeting);

}
