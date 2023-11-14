package meetinginvitationmanagement.application;

import meetinginvitationmanagement.domain.MeetingInvitation;
import meetinginvitationmanagement.repository.IMeetingInvitationRepository;

public class AcceptMeetingInvitationController {

    private final IMeetingInvitationRepository meetingInvitationRepository;

    public AcceptMeetingInvitationController(IMeetingInvitationRepository meetingInvitationRepository) {
        // Verify that the meetingInvitationRepository is not null
        if (meetingInvitationRepository == null) {
            throw new IllegalArgumentException("Parameter meetingInvitationRepository was null!");
        }

        this.meetingInvitationRepository = meetingInvitationRepository;
    }

    public void acceptMeetingInvitation(MeetingInvitation meetingInvitation) {
        // Verify that the meetingInvitation is not null
        if (meetingInvitation == null) {
            throw new IllegalArgumentException("Parameter meetingInvitation was null!");
        }

        // Reject the meeting invitation
        meetingInvitation.accept();

        // Save the meeting invitation
        meetingInvitationRepository.save(meetingInvitation);
    }

}
