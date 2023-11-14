package meetinginvitationmanagement.application;

import ecourseusermanagement.domain.ECourseUser;
import meetinginvitationmanagement.domain.MeetingInvitation;
import meetinginvitationmanagement.repository.IMeetingInvitationRepository;
import meetingmanagement.domain.Meeting;

import java.util.Optional;

public class MeetingInvitationSender implements IMeetingInvitationSender {

    private final IMeetingInvitationRepository meetingInvitationRepository;

    /**
     * Instantiates a new Meeting invitation sender.
     *
     * @param meetingInvitationRepository the meeting invitation repository
     */
    public MeetingInvitationSender(IMeetingInvitationRepository meetingInvitationRepository) {
        // Verify if the meetingInvitationRepository is null
        if (meetingInvitationRepository == null) {
            throw new IllegalArgumentException("The meeting invitation repository cannot be null.");
        }

        this.meetingInvitationRepository = meetingInvitationRepository;
    }

    private Optional<MeetingInvitation> checkIfMeetingInvitationAlreadyExists(ECourseUser userToInvite,
                                                                              Meeting meeting) {
        return meetingInvitationRepository.findMeetingInvitationByECourseUserAndMeeting(userToInvite, meeting);
    }

    @Override
    public void sendInvitation(ECourseUser userToInvite, Meeting meeting) {
        // Verify if the userToInvite is null
        if (userToInvite == null) {
            throw new IllegalArgumentException("The user to invite cannot be null.");
        }

        // Verify if the meeting is null
        if (meeting == null) {
            throw new IllegalArgumentException("The meeting cannot be null.");
        }

        // Verify if the meeting invitation already exists
        if (checkIfMeetingInvitationAlreadyExists(userToInvite, meeting).isPresent()) {
            throw new IllegalArgumentException("The meeting invitation already exists.");
        }

        // Create the meeting invitation
        MeetingInvitation meetingInvitation = new MeetingInvitation(userToInvite, meeting);

        // Save the meeting invitation
        meetingInvitationRepository.save(meetingInvitation);
    }
}
