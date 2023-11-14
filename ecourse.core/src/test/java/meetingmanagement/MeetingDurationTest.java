package meetingmanagement;

import meetingmanagement.domain.MeetingDuration;
import org.junit.Assert;
import org.junit.Test;

public class MeetingDurationTest {

    // Valid MeetingDuration Tests

    @Test
    public void ensureMeetingDurationIsCreatedWithValidInformation() {
        // Arrange
        Integer duration = 60;

        // Act
        MeetingDuration meetingDuration = new MeetingDuration(duration);

        // Assert
        Assert.assertNotNull(meetingDuration);
    }

    // Invalid MeetingDurations Tests

    @Test
    public void ensureMeetingDurationIsNotCreatedWhenDuration() {
        // Assert
        Assert.assertThrows(IllegalArgumentException.class, () -> new MeetingDuration(null));
    }

    @Test
    public void ensureMeetingDurationIsNotCreatedWhenDurationIsInferiorToZero() {
        // Arrange
        Integer duration = -123;

        // Assert
        Assert.assertThrows(IllegalArgumentException.class, () -> new MeetingDuration(duration));
    }

}
