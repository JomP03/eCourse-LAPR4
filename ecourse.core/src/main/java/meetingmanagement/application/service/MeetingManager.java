package meetingmanagement.application.service;

import ecourseusermanagement.domain.ECourseUser;
import meetinginvitationmanagement.application.IMeetingInvitationProvider;
import meetinginvitationmanagement.application.IMeetingInvitationSender;
import meetinginvitationmanagement.domain.MeetingInvitation;
import meetinginvitationmanagement.repository.IMeetingInvitationRepository;
import meetingmanagement.domain.IMeetingManager;
import meetingmanagement.domain.Meeting;
import meetingmanagement.repository.IMeetingRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MeetingManager implements IMeetingManager {

    private final IUserAvailabilityService userAvailabilityService;

    private final IMeetingRepository meetingRepository;

    private final IMeetingInvitationRepository meetingInvitationRepository;

    private final IMeetingInvitationSender meetingInvitationSender;

    private final IMeetingInvitationProvider meetingInvitationProvider;

    /**
     * Instantiates a new Meeting scheduler.
     *
     * @param userAvailabilityService the user availability service
     */
    public MeetingManager(IUserAvailabilityService userAvailabilityService,
                          IMeetingRepository meetingRepository,
                          IMeetingInvitationRepository meetingInvitationRepository,
                          IMeetingInvitationSender meetingInvitationSender,
                          IMeetingInvitationProvider meetingInvitationProvider) {
        // Verify if the meetingRepository is null
        if (userAvailabilityService == null) {
            throw new IllegalArgumentException("The user availability service cannot be null.");
        }

        this.userAvailabilityService = userAvailabilityService;

        // Verify if the meetingRepository is null
        if (meetingRepository == null) {
            throw new IllegalArgumentException("The meeting repository cannot be null.");
        }

        this.meetingRepository = meetingRepository;

        // Verify if the meetingInvitationRepository is null
        if (meetingInvitationRepository == null) {
            throw new IllegalArgumentException("The meeting invitation repository cannot be null.");
        }

        this.meetingInvitationRepository = meetingInvitationRepository;

        // Verify if the meetingInvitationSender is null
        if (meetingInvitationSender == null) {
            throw new IllegalArgumentException("The meeting invitation sender cannot be null.");
        }

        this.meetingInvitationSender = meetingInvitationSender;

        // Verify if the meetingInvitationProvider is null
        if (meetingInvitationProvider == null) {
            throw new IllegalArgumentException("The meeting invitation provider cannot be null.");
        }

        this.meetingInvitationProvider = meetingInvitationProvider;
    }

    private void sendMeetingInvitations(Meeting meeting, Iterable<ECourseUser> usersToInvite,
                                        List<ECourseUser> unavailableUsers) {
        // Send the meeting invitations
        for (ECourseUser userToInvite : usersToInvite) {

            try {
                meetingInvitationSender.sendInvitation(userToInvite, meeting);
            } catch (IllegalArgumentException ignored) {
                // If the user is not available to attend the meeting, add it to the list of unavailable users
                unavailableUsers.add(userToInvite);
            }

        }
    }

    @Override
    public String scheduleMeeting(ECourseUser meetingOwner, LocalDateTime meetingDate, Integer meetingDuration,
                                   Iterable<ECourseUser> usersToInvite) {
        // List of users that are available to attend the meeting
        List<ECourseUser> availableUsersToAttendMeeting = new ArrayList<>();

        // List of users that are not available to attend the meeting
        List<ECourseUser> unavailableUsersToAttendMeeting = new ArrayList<>();

        // Verify if the meetingOwner is available to attend the meeting
        if (!userAvailabilityService.isUserAvailable(meetingDate, meetingDuration, meetingOwner,
                unavailableUsersToAttendMeeting)) {
            throw new IllegalArgumentException("The meeting owner is not available to attend the meeting.");
        }

        // Verify which users to invite are available to attend the meeting
        for (ECourseUser userToInvite : usersToInvite) {
            if (userAvailabilityService.isUserAvailable(meetingDate, meetingDuration, userToInvite,
                    unavailableUsersToAttendMeeting) && !userToInvite.equals(meetingOwner)) {
                availableUsersToAttendMeeting.add(userToInvite);
            }
        }

        // Verify the list of available users is not empty
        if (availableUsersToAttendMeeting.isEmpty()) {
            throw new IllegalArgumentException("There are no available users to attend the meeting.");
        }

        // Create the meeting
        Meeting createdMeeting = meetingRepository.save(new Meeting(meetingDate, meetingDuration, meetingOwner));

        // Add the available users to attend the meeting
        sendMeetingInvitations(createdMeeting, availableUsersToAttendMeeting, unavailableUsersToAttendMeeting);

        // Create a report for the scheduled meeting
        return scheduledMeetingReport(createdMeeting, availableUsersToAttendMeeting, unavailableUsersToAttendMeeting);
    }

    private String scheduledMeetingReport(Meeting meeting, List<ECourseUser> availableUsersToAttendMeeting,
                                          List<ECourseUser> unavailableUsersToAttendMeeting) {
        // Create the report
        StringBuilder report = new StringBuilder();

        // Add the meeting details to the report
        report.append("Meeting details: ").append("\n").append(meeting.toString()).append("\n");

        // Add the available users to attend the meeting to the report
        report.append("Available users to attend the meeting: ").append("\n");
        for (ECourseUser availableUserToAttendMeeting : availableUsersToAttendMeeting) {
            report.append("- ").append(availableUserToAttendMeeting.email()).append("\n");
        }

        report.append("\n");

        // Add the unavailable users to attend the meeting to the report
        report.append("Unavailable users to attend the meeting: ").append("\n");
        for (ECourseUser unavailableUserToAttendMeeting : unavailableUsersToAttendMeeting) {
            report.append("-").append(unavailableUserToAttendMeeting.email()).append("\n");
        }

        return report.toString();
    }

    @Override
    public void cancelMeeting(Meeting meeting) {
        // Cancel the meeting
        meeting.cancelMeeting();
        meetingRepository.save(meeting);

        // Cancel the meeting invitations
        cancelMeetingInvitations(meeting);
    }

    private void cancelMeetingInvitations(Meeting meeting) {
        // Get the meeting invitations
        Iterable<MeetingInvitation> meetingInvitations = meetingInvitationProvider.provideMeetingInvitationsByMeeting(meeting);

        // Cancel the meeting invitations
        for (MeetingInvitation meetingInvitation : meetingInvitations) {
            meetingInvitation.cancel();
            meetingInvitationRepository.save(meetingInvitation);
        }
    }
}
