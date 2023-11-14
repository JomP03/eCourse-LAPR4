package questionmanagement;

import questionmanagment.domain.MultipleChoiceQuestion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class MultipleChoiceQuestionTest {

    @Test
    public void ensureValidMultipleChoiceQuestionIsAccepted() {
        // Arrange
        MultipleChoiceQuestion multipleChoiceQuestion = QuestionDataSource.multipleChoiceQuestion2();

        // Act
        String expected = "What is the capital of Spain?";

        // Assert
        Assertions.assertTrue(multipleChoiceQuestion.toString().contains(expected));
    }

    @Test
    public void ensureNullQuestionIsRejected() {
        // Arrange
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> options = Arrays.asList("Option 1", "Option 2");
        String correctOption = "Option 1";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new MultipleChoiceQuestion(null, options, correctOption, penalty, quotation));

    }

    @Test
    public void ensureNullPenaltyIsRejected() {
        // Arrange
        String question = "Question";
        Float quotation = 1f;
        List<String> options = Arrays.asList("Option 1", "Option 2");
        String correctOption = "Option 1";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new MultipleChoiceQuestion(question, options, correctOption, null, quotation));

    }

    @Test
    public void ensureNullQuotationIsRejected() {
        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        List<String> options = Arrays.asList("Option 1", "Option 2");
        String correctOption = "Option 1";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new MultipleChoiceQuestion(question, options, correctOption, penalty, null));

    }

    @Test
    public void ensureNullOptionsIsRejected() {
        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        String correctOption = "Option 1";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new MultipleChoiceQuestion(question, null, correctOption, penalty, quotation));

    }

    @Test
    public void ensureNullCorrectAnswerIsRejected() {
        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> options = Arrays.asList("Option 1", "Option 2");

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new MultipleChoiceQuestion(question, options, null, penalty, quotation));

    }

    @Test
    public void ensureEmptyOptionListIsRejected() {
        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> options = new ArrayList<>();
        String correctOption = "ok";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new MultipleChoiceQuestion(question, options, correctOption, penalty, quotation));

    }

    @Test
    public void ensureEmptyCorrectAnswerIsRejected() {
        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> options = Arrays.asList("Option 1", "Option 2");
        String correctOption = "";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new MultipleChoiceQuestion(question, options, correctOption, penalty, quotation));

    }

    @Test
    public void ensureOnly1OptionIsRejected() {
        // Arrange
        String question = "";
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> options = List.of("Option 1");
        String correctOption = "Option 1";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new MultipleChoiceQuestion(question, options, correctOption, penalty, quotation));

    }


    @Test
    public void ensureQuestionsIsNotCreatedWithOptionsListLessThan2() {
        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> options = List.of("Option 1");
        String correctOption = "Option 1";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new MultipleChoiceQuestion(question, options, correctOption, penalty, quotation));
    }


    @Test
    public void ensureQuestionIsNotCreatedWithListThatDoesNotContainAnswer() {
        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> options = List.of("Option 1", "Option 2");
        String correctOption = "Option 3";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new MultipleChoiceQuestion(question, options, correctOption, penalty, quotation));
    }


    @Test
    public void ensureQuestionsIsUpdateWithValidInformation() {
        // Arrange
        MultipleChoiceQuestion multipleChoiceQuestion = QuestionDataSource.multipleChoiceQuestion2();

        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> options = Arrays.asList("Option 1", "Option 2");
        String correctOption = "Option 1";

        // Act
        MultipleChoiceQuestion updatedQuestion = multipleChoiceQuestion.updateQuestion(
                question,penalty, quotation, options, correctOption);

        // Assert
        Assertions.assertTrue(multipleChoiceQuestion.sameAs(updatedQuestion));
    }


    @Test
    public void ensureQuestionIsNotUpdatedWithInvalidQuestion() {
        // Arrange
        MultipleChoiceQuestion multipleChoiceQuestion = QuestionDataSource.multipleChoiceQuestion2();

        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> options = Arrays.asList("Option 1", "Option 2");
        String correctOption = "Option 1";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                multipleChoiceQuestion.updateQuestion(null,penalty, quotation, options, correctOption));
    }


    @Test
    public void ensureQuestionIsNotUpdatedWithInvalidPenalty() {
        // Arrange
        MultipleChoiceQuestion multipleChoiceQuestion = QuestionDataSource.multipleChoiceQuestion2();

        String question = "Question";
        Float quotation = 1f;
        List<String> options = Arrays.asList("Option 1", "Option 2");
        String correctOption = "Option 1";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                multipleChoiceQuestion.updateQuestion(question, null, quotation, options, correctOption));
    }


    @Test
    public void ensureQuestionIsNotUpdatedWithInvalidQuotation() {
        // Arrange
        MultipleChoiceQuestion multipleChoiceQuestion = QuestionDataSource.multipleChoiceQuestion2();

        String question = "Question";
        Float penalty = 0.5f;
        List<String> options = Arrays.asList("Option 1", "Option 2");
        String correctOption = "Option 1";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                multipleChoiceQuestion.updateQuestion(question,penalty, null, options, correctOption));
    }


    @Test
    public void ensureQuestionIsNotUpdatedWithInvalidOptions() {
        // Arrange
        MultipleChoiceQuestion multipleChoiceQuestion = QuestionDataSource.multipleChoiceQuestion2();

        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        String correctOption = "Option 1";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () -> multipleChoiceQuestion.updateQuestion(question,penalty, quotation, null, correctOption));
    }


    @Test
    public void ensureQuestionIsNotUpdatedWithInvalidCorrectOption() {
        // Arrange
        MultipleChoiceQuestion multipleChoiceQuestion = QuestionDataSource.multipleChoiceQuestion2();

        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> options = Arrays.asList("Option 1", "Option 2");

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                multipleChoiceQuestion.updateQuestion(question,penalty, quotation, options, null));
    }


    @Test
    public void ensureQuestionIsNotUpdatedWithListSmallerThan2() {
        // Arrange
        MultipleChoiceQuestion multipleChoiceQuestion = QuestionDataSource.multipleChoiceQuestion2();

        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> options = List.of("Option 1");
        String correctOption = "Option 1";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                multipleChoiceQuestion.updateQuestion(question,penalty, quotation, options, correctOption));
    }


    @Test
    public void ensureQuestionIsNotUpdatedWithListThatDoesNotContainAnswer() {
        // Arrange
        MultipleChoiceQuestion multipleChoiceQuestion = QuestionDataSource.multipleChoiceQuestion2();

        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> options = List.of("Option 1", "Option 2");
        String correctOption = "Option 3";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                multipleChoiceQuestion.updateQuestion(question,penalty, quotation, options, correctOption));
    }

    @Test
    public void ensureQuestionKnowsHowToCorrectItself() {
        // Arrange
        MultipleChoiceQuestion multipleChoiceQuestion = QuestionDataSource.multipleChoiceQuestion2();
        float expected = 10f;

        // Act
        Float actual = multipleChoiceQuestion.correctStudentAnswer("Madrid");

        // Assert
        Assertions.assertEquals(expected, actual);
        Assertions.assertTrue(multipleChoiceQuestion.isCorrect("Madrid"));
    }

    @Test
    public void ensureQuestionKnowsHowToIncorrectItself() {
        // Arrange
        MultipleChoiceQuestion multipleChoiceQuestion = QuestionDataSource.multipleChoiceQuestion2();
        float expected = 0f;

        // Act
        Float actual = multipleChoiceQuestion.correctStudentAnswer("Lisbon");

        // Assert
        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(multipleChoiceQuestion.isCorrect("Lisbon"));
    }

    @Test
    public void ensureQuestionKnowsHowToIncorrectItselfWithNull() {
        // Arrange
        MultipleChoiceQuestion multipleChoiceQuestion = QuestionDataSource.multipleChoiceQuestion2();

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                multipleChoiceQuestion.correctStudentAnswer(null));
    }

    @Test
    public void ensureQuestionReturnsCorrectAnswer() {
        // Arrange
        MultipleChoiceQuestion multipleChoiceQuestion = QuestionDataSource.multipleChoiceQuestion2();
        String expected = "Madrid";

        // Act
        String actual = multipleChoiceQuestion.correctAnswer();

        // Assert
        Assertions.assertEquals(expected, actual);
    }


    @Test
    void ensureQuestionStringToFileMethod() {
        // Arrange
        MultipleChoiceQuestion multipleChoiceQuestion = QuestionDataSource.multipleChoiceQuestion2();
        String expected = "MULTIPLE_CHOICE:What is the capital of Spain?\n" +
                "CORANSW:Madrid\n" +
                "GRADE:10.0\n" +
                "ANSW:Lisbon\n" +
                "ANSW:Madrid\n" +
                "ANSW:Paris\n" +
                "ANSW:Berlin\n" +
                "ANSW:Rome\n";

        // Act
        String actual = multipleChoiceQuestion.stringToFile();

        // Assert
        Assertions.assertEquals(expected, actual);
    }


    @Test
    void ensureQuestionReturnsItsOptions() {
        // Arrange
        MultipleChoiceQuestion multipleChoiceQuestion = QuestionDataSource.multipleChoiceQuestion2();
        List<String> expected = List.of("Lisbon", "Madrid", "Paris", "Berlin", "Rome");

        // Act
        List<String> actual = multipleChoiceQuestion.options();

        // Assert
        Assertions.assertEquals(expected, actual);
    }
}
