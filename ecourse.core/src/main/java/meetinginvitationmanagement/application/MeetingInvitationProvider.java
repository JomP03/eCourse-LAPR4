package meetinginvitationmanagement.application;

import ecourseusermanagement.domain.ECourseUser;
import meetinginvitationmanagement.domain.MeetingInvitation;
import meetinginvitationmanagement.repository.IMeetingInvitationRepository;
import meetingmanagement.domain.Meeting;

import java.util.List;

public class MeetingInvitationProvider implements IMeetingInvitationProvider {

    private final IMeetingInvitationRepository meetingInvitationRepository;

    /**
     * Instantiates a new Meeting invitation provider.
     *
     * @param meetingInvitationRepository the meeting invitation repository
     */
    public MeetingInvitationProvider(final IMeetingInvitationRepository meetingInvitationRepository) {
        // Verify if the meeting invitation repository is not null
        if (meetingInvitationRepository == null) {
            throw new IllegalArgumentException("Meeting invitation repository cannot be null.");
        }

        this.meetingInvitationRepository = meetingInvitationRepository;
    }

    @Override
    public Iterable<MeetingInvitation> provideUserUncanceledMeetingInvitations(ECourseUser user) {
        return meetingInvitationRepository.findUserUncanceledMeetingInvitations(user);
    }

    @Override
    public List<MeetingInvitation> provideMeetingInvitationsByMeeting(Meeting meeting){
        return (List<MeetingInvitation>) meetingInvitationRepository.findMeetingInvitationsByMeeting(meeting);
    }

}
