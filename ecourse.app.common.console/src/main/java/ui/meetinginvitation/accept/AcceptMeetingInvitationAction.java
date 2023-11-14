package ui.meetinginvitation.accept;

import eapli.framework.actions.Action;
import meetinginvitationmanagement.domain.MeetingInvitation;

public class AcceptMeetingInvitationAction implements Action {

    private final MeetingInvitation meetingInvitation;

    /**
     * Instantiates a new Accept meeting invitation action.
     *
     * @param meetingInvitation the meeting invitation
     */
    public AcceptMeetingInvitationAction(MeetingInvitation meetingInvitation) {
        // Verify if the meeting invitation is not null
        if (meetingInvitation == null) {
            throw new IllegalArgumentException("Meeting invitation cannot be null.");
        }

        this.meetingInvitation = meetingInvitation;
    }

    @Override
    public boolean execute() {
        return new AcceptMeetingInvitationUI(meetingInvitation).show();
    }
}
