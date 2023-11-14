package meetinginvitationmanagement.application;

import ecourseusermanagement.domain.ECourseUser;
import meetinginvitationmanagement.domain.MeetingInvitation;
import meetingmanagement.domain.Meeting;

import java.util.List;

public interface IMeetingInvitationProvider {

    /**
     * Provide user uncanceled meeting invitations.
     * Basically, all the invitations for meetings that have not been canceled, and that are
     * not fished or in progress.
     *
     * @param user the user
     * @return the iterable of meeting invitations
     */
    Iterable<MeetingInvitation> provideUserUncanceledMeetingInvitations(ECourseUser user);

    /**
     * Provide meeting invitations.
     *
     * @param meeting the meeting
     * @return the list of meeting invitations for that meeting
     */
    List<MeetingInvitation> provideMeetingInvitationsByMeeting(Meeting meeting);
}
