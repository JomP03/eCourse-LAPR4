package meetingmanagement.application.service;

import ecourseusermanagement.domain.ECourseUser;
import meetingmanagement.domain.Meeting;

import java.util.List;

public interface IMeetingProvider {

    List<Meeting> provideScheduledMeetingsByOwner(ECourseUser user);

    List<Meeting> provideMeetingsByInvitedUser(ECourseUser user);
}
