package ecourseusermanagement.domain;

import appsettings.Application;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserAcronymTest {

    @Test
    public void ensureValidAcronymIsAccepted() {
        // Arrange
        String acronymString = "GCJ";

        // Act
        UserAcronym acronym = new UserAcronym(acronymString);

        // Assert
        Assertions.assertEquals(acronym.toString(), acronymString);
    }

    @Test
    public void ensureEmptyAcronymIsRejected() {
        // Arrange
        String acronymString = "";

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> new UserAcronym(acronymString));
    }

    @Test
    public void ensureNullAcronymIsRejected() {
        // AAA
        Assertions.assertThrows(IllegalArgumentException.class, () -> new UserAcronym(null));
    }

    @Test
    public void ensureAcronymWithNonCapitalLettersIsRejected() {
        // Arrange
        String acronymString = "Gcj";

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> new UserAcronym(acronymString));
    }

    @Test
    public void ensureAcronymWithNumbersIsRejected() {
        // Arrange
        String acronymString = "GC1";

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> new UserAcronym(acronymString));
    }

    @Test
    public void ensureAcronymWithSpecialCharactersIsRejected() {
        // Arrange
        String acronymString = "GC!";

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> new UserAcronym(acronymString));
    }

    @Test
    void ensureAcronymWithBiggerThanMaxLengthThrowsException() {
        // Arrange
        int maxLength = Application.settings().getMaxTeacherAcronymLength();
        String acronymString = "G".repeat(maxLength + 1);

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> new UserAcronym(acronymString));
    }

    @Test
    void ensureCompareToReturnsZeroWhenAcronymsAreEqual() {
        // Arrange
        String acronymString = "GCJ";
        UserAcronym acronym = new UserAcronym(acronymString);

        // Act
        int result = acronym.compareTo(new UserAcronym(acronymString));

        // Assert
        Assertions.assertEquals(0, result);
    }
}
