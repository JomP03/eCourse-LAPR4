package meetinginvitationmanagement.application;

import meetinginvitationmanagement.domain.MeetingInvitation;
import meetinginvitationmanagement.repository.IMeetingInvitationRepository;

public class RejectMeetingInvitationController {

    private final IMeetingInvitationRepository meetingInvitationRepository;

    public RejectMeetingInvitationController(IMeetingInvitationRepository meetingInvitationRepository) {
        // Verify that the meetingInvitationRepository is not null
        if (meetingInvitationRepository == null) {
            throw new IllegalArgumentException("Parameter meetingInvitationRepository was null!");
        }

        this.meetingInvitationRepository = meetingInvitationRepository;
    }

    public void rejectMeetingInvitation(MeetingInvitation meetingInvitation) {
        // Verify that the meetingInvitation is not null
        if (meetingInvitation == null) {
            throw new IllegalArgumentException("Parameter meetingInvitation was null!");
        }

        // Reject the meeting invitation
        meetingInvitation.reject();

        // Save the meeting invitation
        meetingInvitationRepository.save(meetingInvitation);
    }
}
