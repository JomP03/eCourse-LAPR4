package meetinginvitationmanagement.application;

import ecourseusermanagement.application.UserSessionService;
import ecourseusermanagement.domain.ECourseUser;
import meetinginvitationmanagement.domain.MeetingInvitation;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ManageMeetingInvitationController {

    private final UserSessionService userSessionService;

    private final IMeetingInvitationProvider meetingInvitationProvider;

    private ECourseUser eCourseUser;

    /**
     * Instantiates a new Manage meeting invitation controller.
     *
     * @param userSessionService        the user session service
     * @param meetingInvitationProvider the meeting invitation provider
     */
    public ManageMeetingInvitationController(final UserSessionService userSessionService,
            final IMeetingInvitationProvider meetingInvitationProvider) {
        // Verify if the user session service is not null
        if (userSessionService == null) {
            throw new IllegalArgumentException("User session service cannot be null.");
        }

        this.userSessionService = userSessionService;

        // Verify if the meeting invitation provider is not null
        if (meetingInvitationProvider == null) {
            throw new IllegalArgumentException("Meeting invitation provider cannot be null.");
        }

        this.meetingInvitationProvider = meetingInvitationProvider;

        verifyUser();
    }

    private void verifyUser() {
        Optional<ECourseUser> eCourseUserOptional = userSessionService.getLoggedUser();

        eCourseUserOptional.ifPresentOrElse(
                eCourseUser -> this.eCourseUser = eCourseUser,
                () -> {
                    throw new IllegalStateException("No eCourse user found. Make sure you are registered.");
                }
        );
    }

    /**
     * User meeting invitations for the logged user.
     *
     * @return the iterable of meeting invitations
     */
    public Iterable<MeetingInvitation> userMeetingInvitations() {
        return meetingInvitationProvider.provideUserUncanceledMeetingInvitations(eCourseUser);
    }

    /**
     * This method creates a list with the options for the meeting invitation.
     *
     * @param meetingInvitation the meeting invitation
     * @return the iterable of strings (meeting invitation options)
     */
    public Iterable<String> meetingInvitationOptions(final MeetingInvitation meetingInvitation) {
        // List with the options for the meeting invitation
        List<String> meetingInvitationOptions =
                new java.util.ArrayList<>(Arrays.asList("Accept", "Reject"));

        // Remove the status that the meeting invitation already has and the pending status
        switch (meetingInvitation.invitationStatus()) {
            case ACCEPTED:
                meetingInvitationOptions.remove("Accept");
                break;
            case REJECTED:
                meetingInvitationOptions.remove("Reject");
                break;
            default:
                break;
        }

        return meetingInvitationOptions;
    }

}
