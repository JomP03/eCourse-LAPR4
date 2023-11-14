package exammanagement.domain;

import questionmanagment.domain.BooleanQuestion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import questionmanagment.domain.*;

import java.util.ArrayList;
import java.util.List;

public class ExamSectionTest {

    @Test
    public void ensureValidExamSectionIsAccepted() {
        // Arrange
        String description = "Description";
        List<Question> questions = new ArrayList<>();
        questions.add(new BooleanQuestion("Question", true, 0.5f, 1f));

        // Act
        ExamSection examSection = new ExamSection(description, questions);

        String expected = "Description";

        // Assert
        Assertions.assertTrue(examSection.toString().contains(expected));
    }

    @Test
    public void ensureNullDescriptionIsRejected() {
        // Arrange
        List<Question> questions = new ArrayList<>();
        questions.add(new BooleanQuestion("Question", true, 0.5f, 1f));

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new ExamSection(null, questions));

    }

    @Test
    public void ensureNullQuestionsAreRejected() {
        // Arrange
        String description = "Description";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new ExamSection(description, null));

    }

    @Test
    public void ensureEmptyQuestionsIsRejected() {

        // Arrange
        String description = "Description";
        List<Question> questions = new ArrayList<>();

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new ExamSection(description, questions));

    }


    @Test
    void ensureSectionValidatesQuestion() {
        // Arrange
        String description = "Description";
        List<Question> questions = new ArrayList<>();
        questions.add(null);

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new ExamSection(description, questions));

    }


    @Test
    void ensureSectionReturnsItsDescription() {
        // Arrange
        ExamSection examSection = ExamDataSource.sectionTest();
        String description = "Description";

        // Assert
        Assertions.assertEquals(description, examSection.description());
    }


    @Test
    void ensureSectionUpdatesItsDescription() {
        // Arrange
        ExamSection examSection = ExamDataSource.sectionTest();
        String description = "New Description";

        // Act
        examSection.updateDescription(description);

        // Assert
        Assertions.assertEquals(description, examSection.description());
    }


    @Test
    void ensureSectionDoesNotUpdatesItsDescriptionsIfEmpty() {
        // Arrange
        ExamSection examSection = ExamDataSource.sectionTest();
        String description = "";

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                examSection.updateDescription(description));
    }


    @Test
    void ensureSectionDoesNotUpdatesItsDescriptionsIfNull() {
        // Arrange
        ExamSection examSection = ExamDataSource.sectionTest();

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                examSection.updateDescription(null));
    }
}
