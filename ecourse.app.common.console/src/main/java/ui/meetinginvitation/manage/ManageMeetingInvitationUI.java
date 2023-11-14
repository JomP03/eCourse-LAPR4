package ui.meetinginvitation.manage;

import ecourseusermanagement.application.UserSessionService;
import meetinginvitationmanagement.application.ManageMeetingInvitationController;
import meetinginvitationmanagement.application.MeetingInvitationProvider;
import meetinginvitationmanagement.domain.MeetingInvitation;
import persistence.PersistenceContext;
import ui.components.AbstractUI;
import ui.components.ListSelector;
import ui.components.Sleeper;
import ui.meetinginvitation.reject.RejectMeetingInvitationAction;
import ui.meetinginvitation.accept.AcceptMeetingInvitationAction;

import java.util.List;

public class ManageMeetingInvitationUI extends AbstractUI {

    private final ManageMeetingInvitationController manageMeetingInvitationController =
            new ManageMeetingInvitationController(
                    new UserSessionService(PersistenceContext.repositories().eCourseUsers()),
                        new MeetingInvitationProvider(PersistenceContext.repositories().meetingInvitations()));

    @Override
    protected boolean doShow() {

        do {
            // Meeting invitations in "PENDING", "ACCEPTED", "REJECTED" state, to select from
            List<MeetingInvitation> meetingInvitations;

            try {
                meetingInvitations = (List<MeetingInvitation>) manageMeetingInvitationController
                        .userMeetingInvitations();

                if (meetingInvitations.isEmpty()) {
                    infoMessage("There are no meeting invitations to accept/reject.");
                    Sleeper.sleep(1000);
                    break;
                }

                ListSelector<MeetingInvitation> meetingInvitationSelector =
                        new ListSelector<>("Available meetings invitations:", meetingInvitations);

                if (!meetingInvitationSelector.showAndSelectWithExit()) return false;

                List<String> meetingInvitationOptions =
                        (List<String>) manageMeetingInvitationController.meetingInvitationOptions(
                        meetingInvitationSelector.getSelectedElement());

                ListSelector<String> meetingInvitationOptionsSelector =
                        new ListSelector<>("Available meeting invitation options:", meetingInvitationOptions);

                meetingInvitationOptionsSelector.showAndSelect();

                userInterfaceForMeetingInvitationOption(meetingInvitationOptionsSelector.getSelectedElement(),
                        meetingInvitationSelector.getSelectedElement());

            } catch (Exception e) {
                errorMessage(e.getMessage());
                Sleeper.sleep(1000);
            }

        } while (true);

        return false;
    }

    private void userInterfaceForMeetingInvitationOption(String option, MeetingInvitation meetingInvitation) {
        switch(option) {
            case "Accept":
                new AcceptMeetingInvitationAction(meetingInvitation).execute();
                break;
            case "Reject":
                new RejectMeetingInvitationAction(meetingInvitation).execute();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + option);
        }
    }

    @Override
    public String headline() {
        return "Manage Meeting Invitation";
    }
}
