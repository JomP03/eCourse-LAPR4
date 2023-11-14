package meetingmanagement.application.service;

import ecourseusermanagement.domain.ECourseUser;
import meetinginvitationmanagement.repository.IMeetingInvitationRepository;
import meetingmanagement.domain.Meeting;
import meetingmanagement.repository.IMeetingRepository;

import java.time.LocalDateTime;
import java.util.List;

public class MeetingAvailabilityService implements IMeetingAvailabilityService {

    private final IMeetingRepository meetingRepository;

    private final IMeetingInvitationRepository meetingInvitationRepository;

    /**
     * Instantiates a new meeting availability service.
     *
     * @param meetingRepository the meeting repository
     */
    public MeetingAvailabilityService(IMeetingRepository meetingRepository,
                                      IMeetingInvitationRepository meetingInvitationRepository) {
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
    }

    private Iterable<Meeting> findUserToCheckMeetingsAsOwner(ECourseUser userToCheck) {
        return meetingRepository.findScheduledMeetingByOwner(userToCheck);
    }

    private Iterable<Meeting> findUserToCheckMeetingsAsInvitedUser(ECourseUser userToCheck) {
        return meetingInvitationRepository.findMeetingsByInvitedUser(userToCheck);
    }

    private LocalDateTime addDurationToMeetingDate(LocalDateTime meetingDate, Integer meetingDuration) {
        return meetingDate.plusMinutes(meetingDuration);
    }

    /**
     * Checks if the meeting to check overlaps the userToInviteMeeting.
     *
     * @param userToInviteMeeting already scheduled meeting that the user is expected to attend
     * @param meetingToCheckDate the date of the meeting to check
     * @param meetingToCheckDuration the duration of the meeting to check
     * @return true if the meeting to check overlaps the userToInviteMeeting, false otherwise
     */
    private boolean isTimeOverlapping(Meeting userToInviteMeeting, LocalDateTime meetingToCheckDate,
                                      Integer meetingToCheckDuration) {
        // Expected date for userToInviteMeeting to end
        LocalDateTime userToInviteMeetingEndDate =
                addDurationToMeetingDate(userToInviteMeeting.scheduledMeetingDate().retrieveDate(),
                        userToInviteMeeting.expectedDuration().retrieveDuration());

        // Expected date for meetingToSchedule to end
        LocalDateTime meetingToCheckEndDate = addDurationToMeetingDate(meetingToCheckDate, meetingToCheckDuration);

        // Verify if the userToInviteMeeting overlaps the meetingToCheck
        if (meetingToCheckDate.isBefore(userToInviteMeeting.scheduledMeetingDate().retrieveDate()) &&
                meetingToCheckEndDate.isBefore(userToInviteMeeting.scheduledMeetingDate().retrieveDate())) {
            return false;
        }

        else return !meetingToCheckDate.isAfter(userToInviteMeetingEndDate);
    }

    @Override
    public boolean hasMeeting(LocalDateTime dateTime, Integer duration, ECourseUser user) {
        // Retrieve the meetings of the user to check where he is the owner
        List<Meeting> userToInviteMeetings = (List<Meeting>) findUserToCheckMeetingsAsOwner(user);

        // Retrieve the meetings of the user to check where he is an invited user
        userToInviteMeetings.addAll((List<Meeting>) findUserToCheckMeetingsAsInvitedUser(user));

        if (userToInviteMeetings.isEmpty()) return false;

        boolean isOverlapping = false;

        // Verify if the userToCheck has a meeting scheduled for the same date and time
        for (Meeting userToInviteMeeting : userToInviteMeetings) {
            if (isTimeOverlapping(userToInviteMeeting, dateTime, duration)) {
                isOverlapping = true;
            }
        }

        return isOverlapping;
    }
}
