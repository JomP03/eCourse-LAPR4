package ecourseusermanagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTaxNumberTest {

    @Test
    void ensureValidTaxNumberIsAccepted() {
        // Arrange
        String taxNumberString = "243989890";

        // Act
        UserTaxNumber taxNumber = new UserTaxNumber(taxNumberString);

        // Assert
        assertEquals(taxNumber.toString(), taxNumberString);
    }

    @Test
    void ensureEmptyTaxNumberIsRejected() {
        // Arrange
        String taxNumberString = "";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new UserTaxNumber(taxNumberString));
    }

    @Test
    void ensureNullTaxNumberIsRejected() {
        // Arrange
        String taxNumberString = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new UserTaxNumber(taxNumberString));
    }

    @Test
    void ensureTaxNumberWithLettersIsRejected() {
        // Arrange
        String taxNumberString = "243989890A";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new UserTaxNumber(taxNumberString));
    }

    @Test
    void ensureTaxNumberWithSpecialCharactersIsRejected() {
        // Arrange
        String taxNumberString = "243989890!";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new UserTaxNumber(taxNumberString));
    }

    @Test
    void ensureTaxNumberWithLengthDifferentThan9IsRejected() {
        // Arrange
        String taxNumberString = "24398989";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new UserTaxNumber(taxNumberString));
    }

    @Test
    void ensureNonRespectingPortugueseTaxNumberRulesIsRejected() {
        // Arrange
        String taxNumberString = "243989891";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new UserTaxNumber(taxNumberString));
    }




}