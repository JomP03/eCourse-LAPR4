package exammanagement.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class ExamOpenPeriodTest {

    @Test
    public void ensureValidExamOpenPeriodIsAccepted() {

        // Arrange
        LocalDateTime openDate = LocalDateTime.now().plusDays(1);
        LocalDateTime closeDate = LocalDateTime.now().plusDays(2);

        // Act
        ExamOpenPeriod examOpenPeriod = new ExamOpenPeriod(openDate, closeDate);

        String expected = "ExamOpenPeriod{openDate=" + openDate + ", closeDate=" + closeDate + "}";

        // Assert
        Assertions.assertEquals(examOpenPeriod.toString(), expected);
    }

    @Test
    public void ensureNullOpenDateIsRejected() {
        // Arrange
        LocalDateTime closeDate = LocalDateTime.now().plusDays(2);

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new ExamOpenPeriod(null, closeDate));

    }

    @Test
    public void ensureNullCloseDateIsRejected() {
        // Arrange
        LocalDateTime openDate = LocalDateTime.now().plusDays(1);

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new ExamOpenPeriod(openDate, null));

    }

    @Test
    public void ensureCloseDateBeforeNowIsRejected() {

        // Arrange
        LocalDateTime openDate = LocalDateTime.now().plusDays(1);
        LocalDateTime closeDate = LocalDateTime.now().minusDays(2);

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new ExamOpenPeriod(openDate, closeDate));

    }

    @Test
    public void ensureOpenDateAfterCloseDateIsRejected() {

        // Arrange
        LocalDateTime openDate = LocalDateTime.now().plusDays(2);
        LocalDateTime closeDate = LocalDateTime.now().plusDays(1);

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new ExamOpenPeriod(openDate, closeDate));

    }


    @Test
    void ensureExamOpenPeriodReturnsCloseDate() {
        // Arrange
        AutomatedExam exam = ExamDataSource.automatedExamText();
        LocalDateTime expected = LocalDateTime.now().plusDays(2);
        System.out.println(expected);

        // Act
        LocalDateTime actual = exam.openPeriod().closeDate();

        // Assert
        Assertions.assertEquals(expected.getYear(), actual.getYear());
        Assertions.assertEquals(expected.getMonth(), actual.getMonth());
        Assertions.assertEquals(expected.getDayOfMonth(), actual.getDayOfMonth());
        Assertions.assertEquals(expected.getHour(), actual.getHour());
        Assertions.assertEquals(expected.getMinute(), actual.getMinute());
    }


    @Test
    void ensureExamOpenPeriodIsCreatedWithCloseDateBeforeNow() {
        // Arrange
        LocalDateTime openDate = LocalDateTime.now().minusDays(4);
        LocalDateTime closeDate = LocalDateTime.now().minusDays(2);

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new ExamOpenPeriod(openDate, closeDate));
    }
}
