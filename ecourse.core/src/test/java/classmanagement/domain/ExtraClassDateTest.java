package classmanagement.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class ExtraClassDateTest {

    @Test
    public void ensureValidDateIsAccepted(){

            //Arrange
            LocalDateTime date = LocalDateTime.of(6000, 12, 12, 12, 12);

            //Act
            ExtraClassDate extraClassDate = new ExtraClassDate(date);

            //Assert
            Assertions.assertEquals(date.toString(), extraClassDate.getDate().toString());

    }

    @Test
    public void ensureDateBeforeTodayIsRejected(){

        //Arrange
        LocalDateTime date = LocalDateTime.now().minusDays(1);

        //Act
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ExtraClassDate(date));

    }

    @Test
    public void ensureNullDateIsRejected(){

        //Arrange
        LocalDateTime date = null;

        //Act
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ExtraClassDate(date));

    }

    @Test
    public void ensureSameDayButHourBeforeNowIsRejected(){

        //Arrange
        LocalDateTime date = LocalDateTime.now().minusHours(1);

        //Act
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ExtraClassDate(date));

    }
}
