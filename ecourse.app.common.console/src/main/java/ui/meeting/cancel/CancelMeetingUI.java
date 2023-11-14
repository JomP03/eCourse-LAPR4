package ui.meeting.cancel;

import classmanagement.application.UserClassesProviderFactory;
import coursemanagement.application.UserCoursesProviderFactory;
import ecourseusermanagement.application.UserSessionService;
import meetinginvitationmanagement.application.MeetingInvitationProvider;
import meetinginvitationmanagement.application.MeetingInvitationSender;
import meetingmanagement.application.CancelMeetingController;
import meetingmanagement.application.service.MeetingAvailabilityService;
import meetingmanagement.application.service.MeetingManager;
import meetingmanagement.application.service.MeetingProvider;
import meetingmanagement.application.service.UserAvailabilityService;
import meetingmanagement.domain.Meeting;
import persistence.PersistenceContext;
import ui.components.AbstractUI;
import ui.components.ListSelector;
import ui.components.Sleeper;

import java.util.List;

public class CancelMeetingUI extends AbstractUI {

    private final CancelMeetingController cancelMeetingController = new CancelMeetingController(
            new MeetingProvider(PersistenceContext.repositories().meetings(),
                    PersistenceContext.repositories().meetingInvitations()),
            new MeetingManager(
                    new UserAvailabilityService(
                            new MeetingAvailabilityService(
                                    PersistenceContext.repositories().meetings(),
                                    PersistenceContext.repositories().meetingInvitations()),
                            new UserClassesProviderFactory(),
                            new UserCoursesProviderFactory()),
                    PersistenceContext.repositories().meetings(), PersistenceContext.repositories().meetingInvitations(),
                    new MeetingInvitationSender(PersistenceContext.repositories().meetingInvitations()),
                    new MeetingInvitationProvider(PersistenceContext.repositories().meetingInvitations()
                    )),
            new UserSessionService(PersistenceContext.repositories().eCourseUsers()));

    @Override
    protected boolean doShow() {
        List<Meeting> meetings;

        // Get the meetings that the user can cancel
        try {
            meetings = cancelMeetingController.listCancelableMeetings();

            if (meetings.isEmpty()) {
                infoMessage("You have no meetings to cancel.");
                return false;
            }

            ListSelector<Meeting> meetingSelector = new ListSelector<>("Choose the meeting you want to cancel:", meetings);

            // Show the meetings and ask the user to select one
            if (!meetingSelector.showAndSelectWithExit()) return false;

            // Cancel the meeting
            cancelMeetingController.cancelMeeting(meetingSelector.getSelectedElement());

            // Show success message
            successMessage("Meeting canceled successfully.");

            // Wait for 1.5 seconds
            Sleeper.sleep(1500);

            return true;
        } catch (Exception e) {
            errorMessage(e.getMessage());

            // Wait for 1.5 seconds
            Sleeper.sleep(1500);
            return false;
        }
    }

    @Override
    public String headline() {
        return "Cancel Meeting";
    }
}
