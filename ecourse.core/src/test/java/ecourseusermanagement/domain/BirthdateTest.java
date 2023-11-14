package ecourseusermanagement.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BirthdateTest {

    @Test
    void ensureValidBirthdateIsAccepted() {
        // Arrange
        LocalDate date = LocalDate.of(2003, 3, 16);

        // Act
        UserBirthdate userBirthdate = new UserBirthdate(date);

        // Assert
        assertEquals(userBirthdate.toString(), date.toString());
    }

    @Test
    void ensureNullBirthdateIsRejected() {
        // AAA
        assertThrows(IllegalArgumentException.class, () -> new UserBirthdate(null));
    }

    @Test
    void ensureBirthdateInFutureIsRejected() {
        // Arrange
        LocalDate date = LocalDate.of(9999, 3, 16);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new UserBirthdate(date));
    }

    @Test
    void ensureTooOldBirthdateIsRejected() {
        // Arrange
        LocalDate date = LocalDate.of(1900, 3, 16);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new UserBirthdate(date));
    }

}