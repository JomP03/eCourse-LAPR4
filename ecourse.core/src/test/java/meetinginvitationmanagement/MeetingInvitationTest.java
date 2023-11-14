package meetinginvitationmanagement;

import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.domain.UserDataSource;
import meetinginvitationmanagement.domain.InvitationStatus;
import meetinginvitationmanagement.domain.MeetingInvitation;
import meetingmanagement.domain.Meeting;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class MeetingInvitationTest {

    private MeetingInvitation meetingInvitation;

    // Valid MeetingInvitation Tests

    @Test
    public void ensureMeetingInvitationIsCreatedWithInformation() {
        // Arrange
        LocalDateTime meetingDate = LocalDateTime.of(2026, 5, 5, 5, 5);
        Integer meetingDuration = 60;
        ECourseUser meetingOwner = UserDataSource.getTestManager1();

        Meeting meeting = new Meeting(meetingDate, meetingDuration, meetingOwner);

        // Invitation
        ECourseUser invite = UserDataSource.getTestStudent1();

        // Act
        MeetingInvitation meetingInvitation1 = new MeetingInvitation(invite, meeting);

        // Assert
        Assert.assertNotNull(meetingInvitation1);
    }

    @Test
    public void ensureMeetingInvitationIsCreatedWithValidInformation() {
        // Arrange
        LocalDateTime meetingDate = LocalDateTime.of(2026, 5, 5, 5, 5);
        Integer meetingDuration = 60;
        ECourseUser meetingOwner = UserDataSource.getTestManager1();

        Meeting meeting = new Meeting(meetingDate, meetingDuration, meetingOwner);

        // Invitation
        ECourseUser invite = UserDataSource.getTestStudent1();

        // Act
        MeetingInvitation meetingInvitation1 = new MeetingInvitation(invite, meeting);

        String meetingInvitationDetails = "Meeting Owner: " + "man1@gmail.com" + " | Meeting Date: " +
                "2026-05-05T05:05" + " | Invitation Status - " + "PENDING";

        // Assert
        Assert.assertEquals(meetingInvitation1.toString(), meetingInvitationDetails);
    }

    @Test
    public void ensureMeetingInvitationSameAs() {
        // Arrange
        LocalDateTime meetingDate = LocalDateTime.of(2026, 5, 5, 5, 5);
        Integer meetingDuration = 60;
        ECourseUser meetingOwner = UserDataSource.getTestManager1();

        Meeting meeting = new Meeting(meetingDate, meetingDuration, meetingOwner);

        // Invitation
        ECourseUser invite = UserDataSource.getTestStudent1();

        // Act
        MeetingInvitation meetingInvitation1 = new MeetingInvitation(invite, meeting);
        MeetingInvitation meetingInvitation2 = new MeetingInvitation(invite, meeting);

        // Assert
        Assert.assertTrue(meetingInvitation1.sameAs(meetingInvitation2));
    }

    @Test
    public void ensureMeetingInvitationIsCreatedWithNullId() {
        // Arrange
        LocalDateTime meetingDate = LocalDateTime.of(2026, 5, 5, 5, 5);
        Integer meetingDuration = 60;
        ECourseUser meetingOwner = UserDataSource.getTestManager1();

        Meeting meeting = new Meeting(meetingDate, meetingDuration, meetingOwner);

        // Invitation
        ECourseUser invite = UserDataSource.getTestStudent1();

        // Act
        MeetingInvitation meetingInvitation1 = new MeetingInvitation(invite, meeting);

        // Assert
        Assert.assertNull(meetingInvitation1.identity());
    }

    // Invalid MeetingInvitation Tests

    @Test
    public void ensureMeetingInvitationIsNotCreatedWithInvalidUserToInvite() {
        // Arrange
        LocalDateTime meetingDate = LocalDateTime.of(2026, 5, 5, 5, 5);
        Integer meetingDuration = 60;
        ECourseUser meetingOwner = UserDataSource.getTestManager1();

        Meeting meeting = new Meeting(meetingDate, meetingDuration, meetingOwner);

        // Assert
        Assert.assertThrows(IllegalArgumentException.class, () -> new MeetingInvitation(null, meeting));
    }

    @Test
    public void ensureMeetingInvitationIsNotCreatedWithInvalidMeeting() {
        // Invitation
        ECourseUser invite = UserDataSource.getTestStudent1();

        // Assert
        Assert.assertThrows(IllegalArgumentException.class, () -> new MeetingInvitation(invite, null));
    }

    void setUp() {
        // Arrange
        LocalDateTime meetingDate = LocalDateTime.of(2026, 5, 5, 5, 5);
        Integer meetingDuration = 60;
        ECourseUser meetingOwner = UserDataSource.getTestManager1();

        Meeting meeting = new Meeting(meetingDate, meetingDuration, meetingOwner);

        // Invitation
        ECourseUser invite = UserDataSource.getTestStudent1();

        this.meetingInvitation = new MeetingInvitation(invite, meeting);
    }

    @Test
    public void ensurePendingMeetingInvitationCanBeAccepted() {
        // Arrange
        setUp();

        // Act
        this.meetingInvitation.accept();

        // Assert
        Assert.assertEquals(this.meetingInvitation.invitationStatus(), InvitationStatus.ACCEPTED);
    }

    @Test
    public void ensurePendingMeetingInvitationCanBeRejected() {
        // Arrange
        setUp();

        // Act
        this.meetingInvitation.reject();

        // Assert
        Assert.assertEquals(this.meetingInvitation.invitationStatus(), InvitationStatus.REJECTED);
    }

    @Test
    public void ensureAcceptedMeetingInvitationCanNotBeAccepted() {
        // Arrange
        setUp();

        // Act
        this.meetingInvitation.accept();

        // Assert
        Assert.assertThrows(IllegalStateException.class, () -> this.meetingInvitation.accept());
    }

    @Test
    public void ensureRejectedMeetingInvitationCanNotBeRejected() {
        // Arrange
        setUp();

        // Act
        this.meetingInvitation.reject();

        // Assert
        Assert.assertThrows(IllegalStateException.class, () -> this.meetingInvitation.reject());
    }

    @Test
    public void ensureCanceledMeetingInvitationCanNotBeAccepted() {
        // Arrange
        setUp();

        // Act
        this.meetingInvitation.cancel();

        // Assert
        Assert.assertThrows(IllegalStateException.class, () -> this.meetingInvitation.accept());
    }

    @Test
    public void ensureCanceledMeetingInvitationCanNotBeRejected() {
        // Arrange
        setUp();

        // Act
        this.meetingInvitation.cancel();

        // Assert
        Assert.assertThrows(IllegalStateException.class, () -> this.meetingInvitation.reject());
    }

    @Test
    public void ensureCanceledMeetingInvitationCanNotBeCanceled() {
        // Arrange
        setUp();

        // Act
        this.meetingInvitation.cancel();

        // Assert
        Assert.assertThrows(IllegalStateException.class, () -> this.meetingInvitation.cancel());
    }

    @Test
    public void ensureInvitedParticipantIsReturned() {
        // Arrange
        setUp();

        // Act
        ECourseUser invitedParticipant = this.meetingInvitation.invitedParticipant();

        // Assert
        Assert.assertEquals(invitedParticipant, UserDataSource.getTestStudent1());
    }

    @Test
    public void ensureCancellableMeetingInvitationIsCancelled() {
        // Arrange
        setUp();

        // Act
        this.meetingInvitation.cancel();

        // Assert
        Assert.assertEquals(this.meetingInvitation.invitationStatus(), InvitationStatus.CANCELED);
    }

    @Test (expected = IllegalStateException.class)
    public void ensureCanceledMeetingInvitationCannotBeCancelled() {
        // Arrange
        setUp();

        // Act
        this.meetingInvitation.cancel();
        this.meetingInvitation.cancel();
    }

    @Test (expected = IllegalStateException.class)
    public void ensureCancelledMeetingInvitationCannotBeAccepted() {
        // Arrange
        setUp();

        // Act
        this.meetingInvitation.cancel();
        this.meetingInvitation.accept();
    }

    @Test (expected = IllegalStateException.class)
    public void ensureCancelledMeetingInvitationCannotBeRejected() {
        // Arrange
        setUp();

        // Act
        this.meetingInvitation.cancel();
        this.meetingInvitation.reject();
    }

}
