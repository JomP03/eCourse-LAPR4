package classmanagement.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class ClassOccurrenceTest {

    @Test
    public void ensureValidOccurrenceIsCreated(){
        //Arrange
        LocalDateTime date = LocalDateTime.of(6000, 12, 12, 12, 12);
        LocalDateTime oldDate = LocalDateTime.of(5000, 12, 12, 12, 12);

        //Act
        ClassOccurrence classOccurrence = new ClassOccurrence(date, oldDate, 1);

        //Assert
        Assertions.assertNotNull(classOccurrence);
    }

    @Test
    public void ensureNewDateBeforeNowIsRejected(){

        //Arrange
        LocalDateTime date = LocalDateTime.now().minusDays(1);
        LocalDateTime oldDate = LocalDateTime.of(5000, 12, 12, 12, 12);

        //Act
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ClassOccurrence(date, oldDate,1));
    }

    @Test
    public void ensureNullNewDateIsRejected(){
        //Arrange
        LocalDateTime date = null;
        LocalDateTime oldDate = LocalDateTime.of(5000, 12, 12, 12, 12);

        //Act
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ClassOccurrence(date, oldDate,1));
    }

    @Test
    public void ensureNullOldDateIsRejected(){
        //Arrange
        LocalDateTime date = LocalDateTime.of(6000, 12, 12, 12, 12);
        LocalDateTime oldDate = null;

        //Act
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ClassOccurrence(date, oldDate,1));
    }

    @Test
    public void ensureUpdateDateWithValidDateIsAccepted(){
        //Arrange
        LocalDateTime date = LocalDateTime.of(6000, 12, 12, 12, 12);
        LocalDateTime oldDate = LocalDateTime.of(5000, 12, 12, 12, 12);
        LocalDateTime newDate = LocalDateTime.of(7000, 12, 12, 12, 12);

        //Act
        ClassOccurrence classOccurrence = new ClassOccurrence(date, oldDate,1);
        classOccurrence.update(newDate,1);

        //Assert
        Assertions.assertEquals(newDate.toString(), classOccurrence.getNewDate().toString());
    }

    @Test
    public void ensureUpdateDateWithNullDateIsRejected(){
        //Arrange
        LocalDateTime date = LocalDateTime.of(6000, 12, 12, 12, 12);
        LocalDateTime oldDate = LocalDateTime.of(5000, 12, 12, 12, 12);
        LocalDateTime newDate = null;

        //Act
        ClassOccurrence classOccurrence = new ClassOccurrence(date, oldDate,1);

        //Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> classOccurrence.update(newDate,1));
    }

    @Test
    public void ensureUpdateDateWithDateBeforeNowIsRejected(){
        //Arrange
        LocalDateTime date = LocalDateTime.of(6000, 12, 12, 12, 12);
        LocalDateTime oldDate = LocalDateTime.of(5000, 12, 12, 12, 12);
        LocalDateTime newDate = LocalDateTime.now().minusDays(1);

        //Act
        ClassOccurrence classOccurrence = new ClassOccurrence(date, oldDate,1);

        //Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> classOccurrence.update(newDate,1));
    }


}
