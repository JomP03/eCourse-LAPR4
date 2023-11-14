package classmanagement.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClassDurationTest {

    @Test
    public void ensureValidClassDurationIsAccepted() {
        // Arrange
        Integer classDuration = 60;

        // Act
        ClassDuration classDurationObject = new ClassDuration(classDuration);

        // Assert
        Assertions.assertEquals(classDurationObject.getClassDuration(), classDuration);
    }

    @Test
    public void ensure0MinuteClassDurationIsRejected() {
        // Arrange
        Integer classDuration = 0;

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ClassDuration(classDuration);
        });
    }

    @Test
    public void ensureNegativeClassDurationIsRejected() {
        // Arrange
        Integer classDuration = -1;

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ClassDuration(classDuration);
        });
    }

    @Test
    public void ensureUpdateDurationWithValidDurationIsAccepted() {
        // Arrange
        Integer classDuration = 60;
        Integer newClassDuration = 90;
        ClassDuration classDurationObject = new ClassDuration(classDuration);

        // Act
        classDurationObject.updateDuration(newClassDuration);

        // Assert
        Assertions.assertEquals(classDurationObject.getClassDuration(), newClassDuration);
    }

    @Test
    public void ensureUpdateDurationWith0MinuteClassDurationIsRejected() {
        // Arrange
        Integer classDuration = 60;
        Integer newClassDuration = 0;
        ClassDuration classDurationObject = new ClassDuration(classDuration);

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            classDurationObject.updateDuration(newClassDuration);
        });
    }

    @Test
    public void ensureUpdateDurationWithNegativeClassDurationIsRejected() {
        // Arrange
        Integer classDuration = 60;
        Integer newClassDuration = -1;
        ClassDuration classDurationObject = new ClassDuration(classDuration);

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            classDurationObject.updateDuration(newClassDuration);
        });
    }
}
