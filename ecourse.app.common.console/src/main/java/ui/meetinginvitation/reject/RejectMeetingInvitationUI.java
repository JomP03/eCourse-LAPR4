package ui.meetinginvitation.reject;

import meetinginvitationmanagement.application.RejectMeetingInvitationController;
import meetinginvitationmanagement.domain.MeetingInvitation;
import persistence.PersistenceContext;
import ui.components.AbstractUI;

public class RejectMeetingInvitationUI extends AbstractUI {

    private final MeetingInvitation meetingInvitation;

    private final RejectMeetingInvitationController rejectMeetingInvitationController =
            new RejectMeetingInvitationController(PersistenceContext.repositories().meetingInvitations());

    /**
     * Instantiates a new Reject meeting invitation ui.
     *
     * @param meetingInvitation the meeting invitation
     */
    public RejectMeetingInvitationUI(MeetingInvitation meetingInvitation) {
        // verify if the meetingInvitation is null
        if (meetingInvitation == null) {
            throw new IllegalArgumentException("The meetingInvitation cannot be null.");
        }

        this.meetingInvitation = meetingInvitation;
    }

    @Override
    protected boolean doShow() {
        // Reject the meeting invitation
        rejectMeetingInvitationController.rejectMeetingInvitation(meetingInvitation);

        successMessage("Meeting invitation rejected successfully.");

        return true;
    }

    @Override
    public String headline() {
        return "Reject Meeting Invitation";
    }
}
