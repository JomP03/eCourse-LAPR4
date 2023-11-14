package meetinginvitationmanagement.repository;

import eapli.framework.domain.repositories.DomainRepository;
import ecourseusermanagement.domain.ECourseUser;
import meetinginvitationmanagement.domain.MeetingInvitation;
import meetingmanagement.domain.Meeting;

import java.util.Optional;

public interface IMeetingInvitationRepository extends DomainRepository<Long, MeetingInvitation> {

    /**
     * Find meeting invitation by e course user and meeting invitation.
     *
     * @param eCourseUser the e course user
     * @param meeting     the meeting
     * @return the meeting invitation
     */
    Optional<MeetingInvitation> findMeetingInvitationByECourseUserAndMeeting(ECourseUser eCourseUser, Meeting meeting);

    /**
     * Find meetings by invited user and status as accepted.
     *
     * @param userToCheck the user to check
     * @return the iterable
     */
    Iterable<Meeting> findMeetingsByInvitedUser(ECourseUser userToCheck);

    /**
     * Find all meetings by invited user.
     *
     * @param userToCheck the user to check
     * @return the iterable
     */
    Iterable<Meeting> findAllMeetingsByInvitedUser(ECourseUser userToCheck);

    /**
     * Find meeting invitations by meeting iterable.
     *
     * @param meeting the meeting
     * @return the iterable
     */
    Iterable<MeetingInvitation> findMeetingInvitationsByMeeting(Meeting meeting);

    /**
     * Find user uncanceled meeting invitations.
     *
     * @param user the user
     * @return the iterable
     */
    Iterable<MeetingInvitation> findUserUncanceledMeetingInvitations(ECourseUser user);

}
