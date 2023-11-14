package meetingmanagement.application;

import ecourseusermanagement.application.UserSessionService;
import ecourseusermanagement.domain.ECourseUser;
import meetinginvitationmanagement.application.IMeetingInvitationProvider;
import meetinginvitationmanagement.domain.MeetingInvitation;
import meetingmanagement.application.service.MeetingProvider;
import meetingmanagement.domain.Meeting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListMeetingParticipantsController {
    private UserSessionService userSessionService;
    private MeetingProvider meetingProvider;
    private IMeetingInvitationProvider meetingInvitationProvider;
    private ECourseUser user;

    public ListMeetingParticipantsController(UserSessionService userSessionService, MeetingProvider meetingProvider, IMeetingInvitationProvider meetingInvitationProvider) {
        if(userSessionService == null){
            throw new IllegalArgumentException("userSessionService cannot be null.");
        }

        this.userSessionService = userSessionService;

        verifyUser();

        if(meetingProvider == null){
            throw new IllegalArgumentException("meetingProvider cannot be null.");
        }

        this.meetingProvider = meetingProvider;

        if(meetingInvitationProvider == null){
           throw new IllegalArgumentException("meetingInvitationProvider cannot be null.");
        }

        this.meetingInvitationProvider = meetingInvitationProvider;
    }

    /**
     * Verify if the user is logged in.
     */
    private void verifyUser(){
        Optional<ECourseUser> eCourseUserOptional = userSessionService.getLoggedUser();

        eCourseUserOptional.ifPresentOrElse(
                eCourseUser -> this.user = eCourseUser,
                () -> {
                    throw new IllegalStateException("No eCourse user found. Make sure you are registered.");
                }
        );
    }

    /**
     * Provide the meetings for the user.
     *
     * @return the list of meetings for the user
     */
    public List<Meeting> meetingsForUser(){
        List<Meeting> meetingsForUser = new ArrayList<>();

        meetingsForUser.addAll(meetingProvider.provideScheduledMeetingsByOwner(user));

        meetingsForUser.addAll(meetingProvider.provideMeetingsByInvitedUser(user));

        return meetingsForUser;
    }

    /**
     * Provide the invitations of a meeting.
     *
     * @param meeting - the meeting to find the invitations
     * @return the list of invitations of the meeting
     */
    public List<MeetingInvitation> findMeetingParticipants(Meeting meeting) {
        return meetingInvitationProvider.provideMeetingInvitationsByMeeting(meeting);
    }
}
