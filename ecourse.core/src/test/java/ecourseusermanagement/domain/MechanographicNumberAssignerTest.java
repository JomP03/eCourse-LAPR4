package ecourseusermanagement.domain;

import ecourseusermanagement.repositories.IeCourseUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MechanographicNumberAssignerTest {

    private MechanographicNumberAssigner mechanographicNumberAssigner;
    private IeCourseUserRepository eCourseUserRepository;

    @Test
    void ensureMechNumAssignerThrowsExceptionWhenRepositoryIsNull() {
        // Arrange
        IeCourseUserRepository nullRepository = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new MechanographicNumberAssigner(nullRepository));
    }

    @Nested
    class WithSetupTestsNestedTest {

        @BeforeEach
        void setUp() {
            eCourseUserRepository = mock(IeCourseUserRepository.class);
            mechanographicNumberAssigner = new MechanographicNumberAssigner(eCourseUserRepository);
        }

        @Test
        void ensureMechanographicNumberAssignerReturnsCorrectMechanographicNumber() {
            // Arrange
            String currentYear = String.valueOf(LocalDate.now().getYear());
            UserMechanographicNumber lastMechNumInRepo = new UserMechanographicNumber(currentYear + "00001");
            when(eCourseUserRepository.findLastMechanographicNumberInYear(currentYear)).thenReturn(Optional.of(lastMechNumInRepo));
            UserMechanographicNumber expectedMechanographicNumber = new UserMechanographicNumber(currentYear + "00002");

            // Act
            UserMechanographicNumber actualMechanographicNumber = mechanographicNumberAssigner.newMechanographicNumber();

            // Assert
            assertEquals(expectedMechanographicNumber, actualMechanographicNumber);
        }

        @Test
        void ensureMechanographicNumberAssignerReturnsCorrectMechanographicNumberWhenLastMechanographicNumberIsNotPresent() {
            // Arrange
            String currentYear = String.valueOf(LocalDate.now().getYear());
            when(eCourseUserRepository.findLastMechanographicNumberInYear(currentYear)).thenReturn(Optional.empty());
            UserMechanographicNumber expectedMechanographicNumber = new UserMechanographicNumber(currentYear + "00001");

            // Act
            UserMechanographicNumber actualMechanographicNumber = mechanographicNumberAssigner.newMechanographicNumber();

            // Assert
            assertEquals(expectedMechanographicNumber, actualMechanographicNumber);
        }
    }
}