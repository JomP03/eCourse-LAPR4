package ui.meetinginvitation.accept;

import meetinginvitationmanagement.application.AcceptMeetingInvitationController;
import meetinginvitationmanagement.domain.MeetingInvitation;
import persistence.PersistenceContext;
import ui.components.AbstractUI;

public class AcceptMeetingInvitationUI extends AbstractUI {

    private final MeetingInvitation meetingInvitation;

    private final AcceptMeetingInvitationController acceptMeetingInvitationController =
            new AcceptMeetingInvitationController(PersistenceContext.repositories().meetingInvitations());

    /**
     * Instantiates a new Accept meeting invitation ui.
     *
     * @param meetingInvitation the meeting invitation
     */
    public AcceptMeetingInvitationUI(MeetingInvitation meetingInvitation) {
        // verify if the meetingInvitation is null
        if (meetingInvitation == null) {
            throw new IllegalArgumentException("The meetingInvitation cannot be null.");
        }

        this.meetingInvitation = meetingInvitation;
    }

    @Override
    protected boolean doShow() {
        // Accept the meeting invitation
        acceptMeetingInvitationController.acceptMeetingInvitation(meetingInvitation);

        successMessage("Meeting invitation accepted successfully.");

        return true;
    }

    @Override
    public String headline() {
        return "Accept Meeting Invitation";
    }
}
