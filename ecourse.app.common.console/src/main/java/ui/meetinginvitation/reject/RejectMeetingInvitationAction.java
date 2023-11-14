package ui.meetinginvitation.reject;

import eapli.framework.actions.Action;
import meetinginvitationmanagement.domain.MeetingInvitation;

public class RejectMeetingInvitationAction implements Action {

    private final MeetingInvitation meetingInvitation;

    /**
     * Instantiates a new Accept meeting invitation action.
     *
     * @param meetingInvitation the meeting invitation
     */
    public RejectMeetingInvitationAction(MeetingInvitation meetingInvitation) {
        // Verify if the meeting invitation is not null
        if (meetingInvitation == null) {
            throw new IllegalArgumentException("Meeting invitation cannot be null.");
        }

        this.meetingInvitation = meetingInvitation;
    }

    @Override
    public boolean execute() {
        return new RejectMeetingInvitationUI(meetingInvitation).show();
    }
}
