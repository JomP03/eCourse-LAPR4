package meetingmanagement.application.service;

import ecourseusermanagement.domain.ECourseUser;
import meetinginvitationmanagement.repository.IMeetingInvitationRepository;
import meetingmanagement.domain.Meeting;
import meetingmanagement.repository.IMeetingRepository;

import java.util.List;

public class MeetingProvider implements IMeetingProvider {
    private final IMeetingRepository meetingRepository;
    private final IMeetingInvitationRepository meetingInvitationRepository;

    /**
     * Instantiates a new Meeting provider.
     *
     * @param meetingRepository the meeting repository
     */
    public MeetingProvider(IMeetingRepository meetingRepository, IMeetingInvitationRepository meetingInvitationRepository){
        if (meetingRepository == null) {
            throw new IllegalArgumentException("meetingRepository cannot be null.");
        }

        this.meetingRepository = meetingRepository;

        if(meetingInvitationRepository == null){
            throw new IllegalArgumentException("meetingInvitationRepository cannot be null.");
        }

        this.meetingInvitationRepository = meetingInvitationRepository;
    }


    /**
     * Provide the meetings that were scheduled by a user.
     *
     * @param user - the user to get the meetings
     * @return the list of meetings scheduled by the user
     */
    public List<Meeting> provideScheduledMeetingsByOwner(ECourseUser user){
        return (List<Meeting>) meetingRepository.findScheduledMeetingByOwner(user);
    }

    /**
        * Provide the meetings that a user was invited to.
        *
        * @param user - the user to get the meetings
        * @return the list of meetings the user was invited to
        */
    public List<Meeting> provideMeetingsByInvitedUser(ECourseUser user){
        return (List<Meeting>) meetingInvitationRepository.findAllMeetingsByInvitedUser(user);
    }
}
