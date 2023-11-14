package meetingmanagement;

import meetingmanagement.domain.MeetingDate;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class MeetingDateTest {

    // Valid MeetingDate Tests

    @Test
    public void ensureMeetingDateIsCreatedWithValidInformation() {
        // Arrange
        LocalDateTime dateToSchedule = LocalDateTime.of(2026, 5, 5, 5, 5);

        // Act
        MeetingDate meetingDate = new MeetingDate(dateToSchedule);

        // Assert
        Assert.assertNotNull(meetingDate);
    }

    // Invalid MeetingDate Tests

    @Test
    public void ensureMeetingDateIsNotCreatedWhenDateIsNull() {
        // Assert
        Assert.assertThrows(IllegalArgumentException.class, () -> new MeetingDate(null));
    }

    @Test
    public void ensureMeetingDateIsNotCreatedWhenScheduleAttemptIsInThePast() {
        // Arrange
        LocalDateTime dateToSchedule = LocalDateTime.of(2012, 5, 5, 5, 5);

        // Assert
        Assert.assertThrows(IllegalArgumentException.class, () -> new MeetingDate(dateToSchedule));
    }

}
