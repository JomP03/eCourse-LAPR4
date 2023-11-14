package ecourseusermanagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMechanographicNumberTest {

    @Test
    void ensureValidMechanographicNumberIsAccepted() {
        // Arrange
        String currentYear = String.valueOf(java.time.Year.now().getValue());
        String mechanographicNumberString = currentYear + "00001";

        // Act
        UserMechanographicNumber mechanographicNumber = new UserMechanographicNumber(mechanographicNumberString);

        // Assert
        assertEquals(mechanographicNumber.toString(), mechanographicNumberString);
    }

    @Test
    void ensureEmptyMechanographicNumberIsRejected() {
        // Arrange
        String mechanographicNumberString = "";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new UserMechanographicNumber(mechanographicNumberString));
    }

    @Test
    void ensureNullMechanographicNumberIsRejected() {
        // AAA
        assertThrows(IllegalArgumentException.class, () -> new UserMechanographicNumber(null));
    }

    @Test
    void ensureDifferentYearMechanographicNumberIsRejected() {
        // Arrange
        String currentYear = String.valueOf(java.time.Year.now().getValue() - 1);
        String mechanographicNumberString = currentYear + "00001";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new UserMechanographicNumber(mechanographicNumberString));
    }

    @Test
    void ensureMechanographicNumberWithLettersIsRejected() {
        // Arrange
        String currentYear = String.valueOf(java.time.Year.now().getValue());
        String mechanographicNumberString = currentYear + "0000A";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new UserMechanographicNumber(mechanographicNumberString));
    }

    @Test
    void ensureMechanographicNumberWithNoYearIsRejected() {
        // Arrange
        String mechanographicNumberString = "00001";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new UserMechanographicNumber(mechanographicNumberString));
    }

    @Test
    void ensureMechanographicNumberWithNoNumberIsRejected() {
        // Arrange
        String currentYear = String.valueOf(java.time.Year.now().getValue());
        String mechanographicNumberString = currentYear + "AAAAA";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new UserMechanographicNumber(mechanographicNumberString));
    }
}