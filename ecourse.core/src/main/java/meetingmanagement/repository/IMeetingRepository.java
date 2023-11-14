package meetingmanagement.repository;

import eapli.framework.domain.repositories.DomainRepository;
import ecourseusermanagement.domain.ECourseUser;
import meetingmanagement.domain.Meeting;

public interface IMeetingRepository extends DomainRepository<Long, Meeting> {

    /**
     * Retrieves the meetings of the user to check.
     *
     * @param userToCheck the user to check
     * @return the meetings of the user to check
     */
    Iterable<Meeting> findScheduledMeetingByOwner(ECourseUser userToCheck);

}
