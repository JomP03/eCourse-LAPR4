package meetingmanagement.application;

import ecourseusermanagement.application.UserSessionService;
import ecourseusermanagement.domain.ECourseUser;
import meetingmanagement.application.service.IMeetingProvider;
import meetingmanagement.domain.IMeetingManager;
import meetingmanagement.domain.Meeting;

import java.util.List;
import java.util.Optional;

/**
 * The type Cancel meeting controller.
 */
public class CancelMeetingController {

    private final IMeetingProvider meetingProvider;
    private final IMeetingManager meetingManager;
    private final UserSessionService userSessionService;

    /**
     * Instantiates a new Cancel meeting controller.
     *
     * @param meetingProvider    the meeting provider
     * @param meetingManager     the meeting manager
     * @param userSessionService the user session service
     */
    public CancelMeetingController(IMeetingProvider meetingProvider, IMeetingManager meetingManager, UserSessionService userSessionService) {
        // Check if the meeting provider is not null
        if (meetingProvider == null) {
            throw new IllegalArgumentException("MeetingProvider cannot be null.");
        }
        this.meetingProvider = meetingProvider;

        // Check if the meeting manager is not null
        if (meetingManager == null) {
            throw new IllegalArgumentException("MeetingManager cannot be null.");
        }
        this.meetingManager = meetingManager;

        // Check if the user session service is not null
        if (userSessionService == null) {
            throw new IllegalArgumentException("UserSessionService cannot be null.");
        }
        this.userSessionService = userSessionService;
    }

    /**
     * Cancel a meeting.
     *
     * @param meeting the meeting to be canceled
     */
    public void cancelMeeting(Meeting meeting) {
        meetingManager.cancelMeeting(meeting);
    }

    /**
     * List cancelable meetings list.
     *
     * @return the list of cancelable meetings
     */
    public List<Meeting> listCancelableMeetings() {
        // Get the logged user
        Optional<ECourseUser> eCourseUserOptional = userSessionService.getLoggedUser();
        // Check if there is a logged user
        if (eCourseUserOptional.isEmpty()) {
            throw new IllegalStateException("No user found. Make sure you are logged.");
        }

        return meetingProvider.provideScheduledMeetingsByOwner(eCourseUserOptional.get());
    }
}
