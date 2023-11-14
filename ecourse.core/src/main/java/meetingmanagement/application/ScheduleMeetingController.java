package meetingmanagement.application;

import ecourseusermanagement.application.UserSessionService;
import ecourseusermanagement.application.ListUsersController;
import ecourseusermanagement.domain.ECourseUser;
import meetingmanagement.application.service.MeetingManager;

import java.time.LocalDateTime;
import java.util.Optional;

public class ScheduleMeetingController {

    private final UserSessionService userSessionService;

    private final ListUsersController listUsersController;

    private final MeetingManager meetingManager;

    private ECourseUser meetingOwner;

    /**
     * Instantiates a new Schedule meeting controller.
     *
     * @param userSessionService  the user session service
     * @param listUsersController the list users controller
     * @param meetingManager    the meeting scheduler
     *
     * @throws IllegalArgumentException if any of the parameters is null
     */
    public ScheduleMeetingController(UserSessionService userSessionService, ListUsersController listUsersController,
                                     MeetingManager meetingManager) {
        // Verify if the userSessionService is null
        if (userSessionService == null) {
            throw new IllegalArgumentException("The userSessionService cannot be null.");
        }

        this.userSessionService = userSessionService;

        // Verify if the listUsersController is null
        if (listUsersController == null) {
            throw new IllegalArgumentException("The listUsersController cannot be null.");
        }

        this.listUsersController = listUsersController;

        // Verify if the meetingManager is null
        if (meetingManager == null) {
            throw new IllegalArgumentException("The meetingScheduler cannot be null.");
        }

        this.meetingManager = meetingManager;

        // Make sure the user is logged in
        verifyUser();
    }

    private void verifyUser() {
        Optional<ECourseUser> eCourseUserOptional = userSessionService.getLoggedUser();

        eCourseUserOptional.ifPresentOrElse(
                eCourseUser -> this.meetingOwner = eCourseUser,
                () -> {
                    throw new IllegalStateException("No eCourse user found. Make sure you are registered.");
                }
        );
    }

    /**
     * List invitable users (enabled in the system).
     *
     * @return the iterable
     */
    public Iterable<ECourseUser> listInvitableUsers() {
        return listUsersController.enabledUsers();
    }

    /**
     * Attempt to schedule the meeting with the given data by the meeting owner.
     *
     * @param meetingDate     the meeting date
     * @param meetingDuration the meeting duration
     * @param usersToInvite   the users to invite
     * @return the meeting
     */
    public String scheduleMeeting(LocalDateTime meetingDate, Integer meetingDuration,
                                   Iterable<ECourseUser> usersToInvite) {
        // Attempt to schedule the requested meeting
        return meetingManager.scheduleMeeting(meetingOwner, meetingDate, meetingDuration, usersToInvite);
    }

}