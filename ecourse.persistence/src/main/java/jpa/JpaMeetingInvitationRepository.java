package jpa;

import ecourseusermanagement.domain.ECourseUser;
import meetinginvitationmanagement.domain.MeetingInvitation;
import meetinginvitationmanagement.repository.IMeetingInvitationRepository;
import meetingmanagement.domain.Meeting;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;

public class JpaMeetingInvitationRepository extends eCourseJpaRepositoryBase<MeetingInvitation, Long, Long>
        implements IMeetingInvitationRepository {

    /**
     * Instantiates a new Jpa meeting invitation repository.
     *
     * @param identityFieldName the identity field name
     */
    public JpaMeetingInvitationRepository(String identityFieldName) {
        super(identityFieldName);
    }

    @Override
    public Optional<MeetingInvitation> findMeetingInvitationByECourseUserAndMeeting(ECourseUser eCourseUser, Meeting meeting) {
        final TypedQuery<MeetingInvitation> query = entityManager().createQuery("SELECT mi " +
                "FROM MeetingInvitation mi " +
                    "WHERE mi.invitedParticipant.email = :participantEmail " +
                        "AND mi.meeting.meetingDate.meetingDate = :meetingDate " +
                            "AND mi.meeting.meetingOwner.id = :meetingOwner", MeetingInvitation.class);

        query.setParameter("participantEmail", eCourseUser.email());
        query.setParameter("meetingDate", meeting.scheduledMeetingDate().retrieveDate());
        query.setParameter("meetingOwner", meeting.meetingOwner().identity());

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Iterable<Meeting> findMeetingsByInvitedUser(ECourseUser userToCheck) {
        final TypedQuery<Meeting> query = entityManager().createQuery("SELECT mi.meeting " +
                "FROM MeetingInvitation mi " +
                    "WHERE mi.invitedParticipant.email = :participantEmail " +
                        "AND mi.meeting.meetingStatus = 'SCHEDULED' " +
                            "AND mi.invitationStatus <> 'CANCELED'", Meeting.class);

        query.setParameter("participantEmail", userToCheck.email());

        return query.getResultList();
    }

    @Override
    public Iterable<Meeting> findAllMeetingsByInvitedUser(ECourseUser userToCheck) {
        final TypedQuery<Meeting> query = entityManager().createQuery("SELECT mi.meeting " +
                "FROM MeetingInvitation mi " +
                "WHERE mi.invitedParticipant.email = :participantEmail ", Meeting.class);

        query.setParameter("participantEmail", userToCheck.email());

        return query.getResultList();
    }

    @Override
    public Iterable<MeetingInvitation> findMeetingInvitationsByMeeting(Meeting meeting) {
        final TypedQuery<MeetingInvitation> query = entityManager().createQuery("SELECT mi " +
                "FROM MeetingInvitation mi " +
                    "WHERE mi.meeting.meetingDate.meetingDate = :meetingDate " +
                        "AND mi.meeting.meetingOwner.id = :meetingOwner", MeetingInvitation.class);

        query.setParameter("meetingDate", meeting.scheduledMeetingDate().retrieveDate());
        query.setParameter("meetingOwner", meeting.meetingOwner().identity());

        return query.getResultList();
    }

    @Override
    public Iterable<MeetingInvitation> findUserUncanceledMeetingInvitations(ECourseUser user) {
        final TypedQuery<MeetingInvitation> query = entityManager().createQuery("SELECT mi " +
                "FROM MeetingInvitation mi " +
                "WHERE mi.invitationStatus <> 'CANCELED' " +
                "AND mi.meeting.meetingDate.meetingDate > CURRENT_DATE " +
                "AND mi.invitedParticipant.email = :userEmail", MeetingInvitation.class);

        query.setParameter("userEmail", user.email());

        return query.getResultList();
    }
}
