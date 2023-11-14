package meetingmanagement;

import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.domain.UserDataSource;
import meetingmanagement.domain.Meeting;
import meetingmanagement.domain.MeetingStatus;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class MeetingTest {


    @Test
    public void ensureMeetingIsCreatedWithValidInformation() {
        // Arrange
        LocalDateTime meetingDate = LocalDateTime.of(2026, 5, 5, 5, 5);
        Integer meetingDuration = 45;
        ECourseUser meetingOwner = UserDataSource.getTestManager1();

        Meeting meeting = new Meeting(meetingDate, meetingDuration, meetingOwner);

        // Assert
        Assert.assertNotNull(meeting);
    }

    @Test
    public void ensureMeetingIsCreatedInScheduledState() {
        // Arrange
        LocalDateTime meetingDate = LocalDateTime.of(2026, 5, 5, 5, 5);
        Integer meetingDuration = 45;
        ECourseUser meetingOwner = UserDataSource.getTestManager1();

        Meeting meeting = new Meeting(meetingDate, meetingDuration, meetingOwner);

        // Assert
        Assert.assertEquals(MeetingStatus.SCHEDULED, meeting.currentStatus());
    }

    // Invalid Meetings Tests

    @Test
    public void ensureMeetingIsNotCreatedWithInvalidDate() {
        // Arrange
        Integer meetingDuration = 45;
        ECourseUser meetingOwner = UserDataSource.getTestManager1();

        // Assert
        Assert.assertThrows(IllegalArgumentException.class, () -> new Meeting(null, meetingDuration, meetingOwner));
    }

    @Test
    public void ensureMeetingIsNotCreatedWithInvalidDuration() {
        // Arrange
        LocalDateTime meetingDate = LocalDateTime.of(2026, 5, 5, 5, 5);
        ECourseUser meetingOwner = UserDataSource.getTestManager1();

        // Assert
        Assert.assertThrows(IllegalArgumentException.class, () -> new Meeting(meetingDate, null, meetingOwner));
    }

    @Test
    public void ensureMeetingIsNotCreatedWithInvalidOwner() {
        // Arrange
        LocalDateTime meetingDate = LocalDateTime.of(2026, 5, 5, 5, 5);
        Integer meetingDuration = 45;

        // Assert
        Assert.assertThrows(IllegalArgumentException.class, () -> new Meeting(meetingDate, meetingDuration, null));
    }

    @Test
    public void ensureCancellableMeetingIsCancelled() {
        // Arrange
        LocalDateTime meetingDate = LocalDateTime.of(2026, 5, 5, 5, 5);
        Integer meetingDuration = 45;
        ECourseUser meetingOwner = UserDataSource.getTestManager1();

        Meeting meeting = new Meeting(meetingDate, meetingDuration, meetingOwner);

        // Act
        meeting.cancelMeeting();

        // Assert
        Assert.assertEquals(MeetingStatus.CANCELLED, meeting.currentStatus());
    }

    @Test (expected = IllegalStateException.class)
    public void ensureCancellingCanceledMeetingThrowsException() {
        // Arrange
        LocalDateTime meetingDate = LocalDateTime.of(2026, 5, 5, 5, 5);
        Integer meetingDuration = 45;
        ECourseUser meetingOwner = UserDataSource.getTestManager1();

        Meeting meeting = new Meeting(meetingDate, meetingDuration, meetingOwner);

        // Act
        meeting.cancelMeeting();
        meeting.cancelMeeting();
    }

    @Test (expected = IllegalStateException.class)
    public void ensureCancellingFinishedMeetingThrowsException() {
        // Arrange
        LocalDateTime meetingDate = LocalDateTime.of(2026, 5, 5, 5, 5);
        Integer meetingDuration = 45;
        ECourseUser meetingOwner = UserDataSource.getTestManager1();

        Meeting meeting = new Meeting(meetingDate, meetingDuration, meetingOwner);

        // Act
        meeting.startMeeting();
        meeting.finishMeeting();
        meeting.cancelMeeting();
    }

    @Test (expected = IllegalStateException.class)
    public void ensureCancellingInProgressMeetingThrowsException() {
        // Arrange
        LocalDateTime meetingDate = LocalDateTime.of(2026, 5, 5, 5, 5);
        Integer meetingDuration = 45;
        ECourseUser meetingOwner = UserDataSource.getTestManager1();

        Meeting meeting = new Meeting(meetingDate, meetingDuration, meetingOwner);

        // Act
        meeting.startMeeting();
        meeting.cancelMeeting();
    }

    @Test
    public void ensureStartingScheduledMeetingStartsMeeting() {
        // Arrange
        LocalDateTime meetingDate = LocalDateTime.of(2026, 5, 5, 5, 5);
        Integer meetingDuration = 45;
        ECourseUser meetingOwner = UserDataSource.getTestManager1();

        Meeting meeting = new Meeting(meetingDate, meetingDuration, meetingOwner);

        // Act
        meeting.startMeeting();

        // Assert
        Assert.assertEquals(MeetingStatus.IN_PROGRESS, meeting.currentStatus());
    }

    @Test (expected = IllegalStateException.class)
    public void ensureStartingCanceledMeetingThrowsException() {
        // Arrange
        LocalDateTime meetingDate = LocalDateTime.of(2026, 5, 5, 5, 5);
        Integer meetingDuration = 45;
        ECourseUser meetingOwner = UserDataSource.getTestManager1();

        Meeting meeting = new Meeting(meetingDate, meetingDuration, meetingOwner);

        // Act
        meeting.cancelMeeting();
        meeting.startMeeting();
    }

    @Test (expected = IllegalStateException.class)
    public void ensureStartingFinishedMeetingThrowsException() {
        // Arrange
        LocalDateTime meetingDate = LocalDateTime.of(2026, 5, 5, 5, 5);
        Integer meetingDuration = 45;
        ECourseUser meetingOwner = UserDataSource.getTestManager1();

        Meeting meeting = new Meeting(meetingDate, meetingDuration, meetingOwner);

        // Act
        meeting.startMeeting();
        meeting.finishMeeting();
        meeting.startMeeting();
    }

    @Test (expected = IllegalStateException.class)
    public void ensureStartingInProgressMeetingThrowsException() {
        // Arrange
        LocalDateTime meetingDate = LocalDateTime.of(2026, 5, 5, 5, 5);
        Integer meetingDuration = 45;

        ECourseUser meetingOwner = UserDataSource.getTestManager1();

        Meeting meeting = new Meeting(meetingDate, meetingDuration, meetingOwner);

        // Act
        meeting.startMeeting();
        meeting.startMeeting();
    }

    @Test
    public void ensureFinishingInProgressMeetingFinishesMeeting() {
        // Arrange
        LocalDateTime meetingDate = LocalDateTime.of(2026, 5, 5, 5, 5);
        Integer meetingDuration = 45;

        ECourseUser meetingOwner = UserDataSource.getTestManager1();

        Meeting meeting = new Meeting(meetingDate, meetingDuration, meetingOwner);

        // Act
        meeting.startMeeting();
        meeting.finishMeeting();

        // Assert
        Assert.assertEquals(MeetingStatus.FINISHED, meeting.currentStatus());
    }

    @Test (expected = IllegalStateException.class)
    public void ensureFinishingScheduledMeetingThrowsException() {
        // Arrange
        LocalDateTime meetingDate = LocalDateTime.of(2026, 5, 5, 5, 5);
        Integer meetingDuration = 45;

        ECourseUser meetingOwner = UserDataSource.getTestManager1();

        Meeting meeting = new Meeting(meetingDate, meetingDuration, meetingOwner);

        // Act
        meeting.finishMeeting();
    }

    @Test (expected = IllegalStateException.class)
    public void ensureFinishingCanceledMeetingThrowsException() {
        // Arrange
        LocalDateTime meetingDate = LocalDateTime.of(2026, 5, 5, 5, 5);
        Integer meetingDuration = 45;

        ECourseUser meetingOwner = UserDataSource.getTestManager1();

        Meeting meeting = new Meeting(meetingDate, meetingDuration, meetingOwner);

        // Act
        meeting.cancelMeeting();
        meeting.finishMeeting();
    }

    @Test (expected = IllegalStateException.class)
    public void ensureFinishingFinishedMeetingThrowsException() {
        // Arrange
        LocalDateTime meetingDate = LocalDateTime.of(2026, 5, 5, 5, 5);
        Integer meetingDuration = 45;

        ECourseUser meetingOwner = UserDataSource.getTestManager1();

        Meeting meeting = new Meeting(meetingDate, meetingDuration, meetingOwner);

        // Act
        meeting.startMeeting();
        meeting.finishMeeting();
        meeting.finishMeeting();
    }

    @Test (expected = IllegalStateException.class)
    public void ensureFinishingNotStartedMeetingThrowsException() {
        // Arrange
        LocalDateTime meetingDate = LocalDateTime.of(2026, 5, 5, 5, 5);
        Integer meetingDuration = 45;

        ECourseUser meetingOwner = UserDataSource.getTestManager1();

        Meeting meeting = new Meeting(meetingDate, meetingDuration, meetingOwner);

        // Act
        meeting.finishMeeting();
    }

    @Test
    public void ensureMeetingOwnerIsReturned() {
        // Arrange
        LocalDateTime meetingDate = LocalDateTime.of(2026, 5, 5, 5, 5);
        Integer meetingDuration = 45;

        ECourseUser meetingOwner = UserDataSource.getTestManager1();

        // Act
        Meeting meeting = new Meeting(meetingDate, meetingDuration, meetingOwner);

        // Assert
        Assert.assertEquals(meetingOwner, meeting.meetingOwner());
    }

    @Test
    public void ensureMeetingDateIsReturned() {
        // Arrange
        LocalDateTime meetingDate = LocalDateTime.of(2026, 5, 5, 5, 5);
        Integer meetingDuration = 45;

        ECourseUser meetingOwner = UserDataSource.getTestManager1();

        // Act
        Meeting meeting = new Meeting(meetingDate, meetingDuration, meetingOwner);

        // Assert
        Assert.assertEquals(meetingDate, meeting.scheduledMeetingDate().retrieveDate());
    }

    @Test
    public void ensureMeetingDurationIsReturned() {
        // Arrange
        LocalDateTime meetingDate = LocalDateTime.of(2026, 5, 5, 5, 5);
        Integer meetingDuration = 45;

        ECourseUser meetingOwner = UserDataSource.getTestManager1();

        // Act
        Meeting meeting = new Meeting(meetingDate, meetingDuration, meetingOwner);

        // Assert
        Assert.assertEquals(meetingDuration, meeting.expectedDuration().retrieveDuration());
    }

    @Test
    public void ensureToStringReturnsString() {
        // Arrange
        LocalDateTime meetingDate = LocalDateTime.of(2026, 5, 5, 5, 5);
        Integer meetingDuration = 45;
        ECourseUser meetingOwner = UserDataSource.getTestManager1();
        Meeting meeting = new Meeting(meetingDate, meetingDuration, meetingOwner);

        // Act
        String actualString = meeting.toString();

        // Assert a string is returned
        Assert.assertNotNull(actualString);
    }

    @Test
    public void ensureSameMeetingIsEqual() {
        // Arrange
        LocalDateTime meetingDate = LocalDateTime.of(2026, 5, 5, 5, 5);
        Integer meetingDuration = 45;
        ECourseUser meetingOwner = UserDataSource.getTestManager1();
        Meeting meeting = new Meeting(meetingDate, meetingDuration, meetingOwner);

        // Act
        boolean actualResult = meeting.sameAs(meeting);

        // Assert
        Assert.assertTrue(actualResult);
    }

    @Test
    public void ensureDifferentMeetingIsNotEqual() {
        // Arrange
        LocalDateTime meetingDate1 = LocalDateTime.of(2026, 5, 5, 5, 5);
        LocalDateTime meetingDate2 = LocalDateTime.of(2026, 5, 5, 5, 6);
        Integer meetingDuration = 45;
        ECourseUser meetingOwner = UserDataSource.getTestManager1();
        Meeting meeting1 = new Meeting(meetingDate1, meetingDuration, meetingOwner);
        Meeting meeting2 = new Meeting(meetingDate2, meetingDuration, meetingOwner);

        // Act
        boolean actualResult = meeting1.sameAs(meeting2);

        // Assert
        Assert.assertFalse(actualResult);
    }

    @Test
    public void ensureNotMeetingClassIsNotEqual() {
        // Arrange
        LocalDateTime meetingDate = LocalDateTime.of(2026, 5, 5, 5, 5);
        Integer meetingDuration = 45;
        ECourseUser meetingOwner = UserDataSource.getTestManager1();
        Meeting meeting = new Meeting(meetingDate, meetingDuration, meetingOwner);

        // Act
        boolean actualResult = meeting.sameAs(meetingDate);

        // Assert
        Assert.assertFalse(actualResult);
    }

}
