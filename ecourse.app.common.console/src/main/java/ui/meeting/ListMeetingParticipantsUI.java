package ui.meeting;

import appsettings.Application;
import ecourseusermanagement.application.UserSessionService;
import meetinginvitationmanagement.application.MeetingInvitationProvider;
import meetinginvitationmanagement.domain.MeetingInvitation;
import meetingmanagement.application.ListMeetingParticipantsController;
import meetingmanagement.application.service.MeetingProvider;
import meetingmanagement.domain.Meeting;
import persistence.PersistenceContext;
import ui.components.AbstractUI;
import ui.components.ListSelector;
import ui.components.Sleeper;

import java.util.List;

public class ListMeetingParticipantsUI extends AbstractUI {

    private final ListMeetingParticipantsController controller =
            new ListMeetingParticipantsController(
                    new UserSessionService(PersistenceContext.repositories().eCourseUsers()),
                    new MeetingProvider(PersistenceContext.repositories().meetings(), PersistenceContext.repositories().meetingInvitations()),
                    new MeetingInvitationProvider(PersistenceContext.repositories().meetingInvitations()));

    @Override
    protected boolean doShow() {

        List<Meeting> meetings = controller.meetingsForUser();

        if(meetings.isEmpty()){
            infoMessage("You have no meetings scheduled.");
            return false;
        }

        ListSelector<Meeting> selector = new ListSelector<>("Meetings", meetings);

        selector.showAndSelect();

        Meeting selectedMeeting = selector.getSelectedElement();

        if(selectedMeeting == null){
            infoMessage("No meeting selected.");
            return false;
        }

        List<MeetingInvitation> meetingInvitations = controller.findMeetingParticipants(selectedMeeting);

        if(meetingInvitations.isEmpty()){
            infoMessage("No participants found for the selected meeting.");
            return false;
        }

        System.out.print("Meeting Participants\n\n");

        for (MeetingInvitation participant : meetingInvitations) {
            if (Application.settings().isOperativeSystemLinux())
                System.out.printf("• Participant Email: %s | Invitation Status: %s\n", participant.invitedParticipant().email(), participant.invitationStatus());
            else
                System.out.printf("- Participant Email: %s | Invitation Status: %s\n", participant.invitedParticipant().email(), participant.invitationStatus());
        }

        System.out.println();

        Sleeper.sleep(1500);

        successMessage("Meeting Participants listed successfully.");

        return false;
    }

    @Override
    public String headline() {
        return "List Meeting Participants";
    }
}
