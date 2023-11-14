package jpa;

import ecourseusermanagement.domain.ECourseUser;
import meetingmanagement.domain.Meeting;
import meetingmanagement.domain.MeetingStatus;
import meetingmanagement.repository.IMeetingRepository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;


public class JpaMeetingRepository extends eCourseJpaRepositoryBase<Meeting, Long, Long> implements IMeetingRepository {

    /**
     * Instantiates a new Jpa meeting repository.
     *
     * @param identityFieldName the identity field name
     */
    public JpaMeetingRepository(String identityFieldName) {
        super(identityFieldName);
    }

    @Override
    public Iterable<Meeting> findScheduledMeetingByOwner(ECourseUser userToCheck) {
        final TypedQuery<Meeting> query = entityManager().createQuery("SELECT m FROM Meeting m " +
                "WHERE m.meetingOwner.email = :ownerEmail AND m.meetingStatus = :status ", Meeting.class);

        query.setParameter("ownerEmail", userToCheck.email());
        query.setParameter("status", MeetingStatus.SCHEDULED);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
