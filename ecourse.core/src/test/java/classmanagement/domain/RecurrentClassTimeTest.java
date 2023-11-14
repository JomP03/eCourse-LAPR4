package classmanagement.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RecurrentClassTimeTest {

    @Test
    public void ensureValidRecurrentClassTimeIsAccepted() {

        //Arrange
        String startTime = "10:00";

        //Act
        RecurrentClassTime recurrentClassTime = new RecurrentClassTime(startTime);

        //Assert
        Assertions.assertEquals(startTime.toString(), recurrentClassTime.getTime().toString());

    }

    @Test
    public void ensureNullRecurrentClassTimeIsRejected() {

        //Arrange
        String startTime = null;

        //Act
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RecurrentClassTime(startTime));

    }

    @Test
    public void ensureNegativeRecurrentClassTimeIsRejected() {

        //Arrange
        String startTime = "-1:00";

        //Act
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RecurrentClassTime(startTime));

    }

    @Test
    public void ensureRecurrentClassTimeGreaterThan24IsRejected() {

        //Arrange
        String startTime = "25:00";

        //Act
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RecurrentClassTime(startTime));

    }

    @Test
    public void ensureRecurrentClassTimeWithInvalidFormatIsRejected() {

        //Arrange
        String startTime = "25:00:00";

        //Act
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RecurrentClassTime(startTime));

    }

    @Test
    public void ensureRecurrentClassTimeWithLettersInsteadOfNumbersIsRejected() {

        //Arrange
        String startTime = "aa:0E";

        //Act
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RecurrentClassTime(startTime));

    }


}
