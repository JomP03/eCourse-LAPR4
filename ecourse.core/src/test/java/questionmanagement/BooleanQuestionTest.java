package questionmanagement;

import questionmanagment.domain.BooleanQuestion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BooleanQuestionTest {

    @Test
    public void ensureValidBooleanQuestionIsAccepted() {

        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        Boolean correctAnswer = true;

        // Act
        BooleanQuestion booleanQuestion = new BooleanQuestion(question, correctAnswer, penalty, quotation);

        String expected = "Question";

        // Assert
        Assertions.assertTrue(booleanQuestion.toString().contains(expected));
    }

    @Test
    public void ensureNullQuestionIsRejected() {

        // Arrange
        Float penalty = 0.5f;
        Float quotation = 1f;
        Boolean correctAnswer = true;

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new BooleanQuestion(null, correctAnswer, penalty, quotation));

    }

    @Test
    public void ensureNullPenaltyIsRejected() {

        // Arrange
        String question = "Question";
        Float quotation = 1f;
        Boolean correctAnswer = true;

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new BooleanQuestion(question, correctAnswer, null, quotation));

    }

    @Test
    public void ensureNullQuotationIsRejected() {

        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        Boolean correctAnswer = true;

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new BooleanQuestion(question, correctAnswer, penalty, null));

    }

    @Test
    public void ensureNullCorrectAnswerIsRejected() {

        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new BooleanQuestion(question, null, penalty, quotation));

    }

    @Test
    public void ensureNegativePenaltyIsRejected() {

        // Arrange
        String question = "Question";
        Float penalty = -0.5f;
        Float quotation = 1f;
        Boolean correctAnswer = true;

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new BooleanQuestion(question, correctAnswer, penalty, quotation));

    }

    @Test
    public void ensureNegativeQuotationIsRejected() {

        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = -1f;
        Boolean correctAnswer = true;

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new BooleanQuestion(question, correctAnswer, penalty, quotation));

    }


    @Test
    public void ensureQuestionsIsUpdateWithValidInformation() {
        // Arrange
        BooleanQuestion booleanQuestion = QuestionDataSource.booleanQuestion1();

        String newQuestion = "New Question";
        Float newPenalty = 0.5f;
        Float newQuotation = 1f;
        Boolean newCorrectAnswer = false;

        // Act
        BooleanQuestion updatedQuestion = booleanQuestion.updateQuestion(newQuestion, newPenalty, newQuotation, newCorrectAnswer);

        // Assert
        Assertions.assertTrue(booleanQuestion.sameAs(updatedQuestion));
    }


    @Test
    public void ensureQuestionsIsNotUpdateWithInvalidQuestion() {
        // Arrange
        BooleanQuestion booleanQuestion = QuestionDataSource.booleanQuestion1();

        Float newPenalty = 0.5f;
        Float newQuotation = 1f;
        Boolean newCorrectAnswer = false;

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                booleanQuestion.updateQuestion(
                        null, newPenalty, newQuotation, newCorrectAnswer));
    }


    @Test
    public void ensureQuestionsIsNotUpdateWithInvalidPenalty() {
        // Arrange
        BooleanQuestion booleanQuestion = QuestionDataSource.booleanQuestion1();

        String newQuestion = "New Question";
        Float newQuotation = 1f;
        Boolean newCorrectAnswer = false;

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                booleanQuestion.updateQuestion(newQuestion, null, newQuotation, newCorrectAnswer));
    }


    @Test
    public void ensureQuestionsIsNotUpdateWithInvalidQuotation() {
        // Arrange
        BooleanQuestion booleanQuestion = QuestionDataSource.booleanQuestion1();

        String newQuestion = "New Question";
        Float newPenalty = 0.5f;
        Boolean newCorrectAnswer = false;

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                booleanQuestion.updateQuestion(newQuestion, newPenalty, null, newCorrectAnswer));
    }


    @Test
    public void ensureQuestionsIsNotUpdateWithInvalidCorrectAnswer() {
        // Arrange
        BooleanQuestion booleanQuestion = QuestionDataSource.booleanQuestion1();

        String newQuestion = "New Question";
        Float newPenalty = 0.5f;
        Float newQuotation = 1f;

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                booleanQuestion.updateQuestion(newQuestion, newPenalty, newQuotation, null));
    }

    @Test
    public void ensureQuestionKnowsHowToCorrectItself() {
        // Arrange
        BooleanQuestion booleanQuestion = QuestionDataSource.booleanQuestion1();
        float expected = 10f;

        // Act
        float actual = booleanQuestion.correctStudentAnswer("true");

        // Assert
        Assertions.assertEquals(expected, actual);
        Assertions.assertTrue(booleanQuestion.isCorrect("true"));
    }

    @Test
    public void ensureQuestionKnowsHowToIncorrectItself() {
        // Arrange
        BooleanQuestion booleanQuestion = QuestionDataSource.booleanQuestion1();
        float expected = 0f;

        // Act
        float actual = booleanQuestion.correctStudentAnswer("false");

        // Assert
        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(booleanQuestion.isCorrect("false"));
    }

    @Test
    public void ensureQuestionKnowsHowToIncorrectItselfWithNullAnswer() {
        // Arrange
        BooleanQuestion booleanQuestion = QuestionDataSource.booleanQuestion1();

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                booleanQuestion.correctStudentAnswer(null));
    }

    @Test
    public void ensureQuestionReturnsTheCorrectAnswer() {
        // Arrange
        BooleanQuestion booleanQuestion = QuestionDataSource.booleanQuestion1();
        String expected = "true";

        // Act
        String actual = booleanQuestion.correctAnswer();

        // Assert
        Assertions.assertEquals(expected, actual);
    }


    @Test
    void ensureQuestionStringToFileMethod() {
        // Arrange
        BooleanQuestion booleanQuestion = QuestionDataSource.booleanQuestion1();
        String expected = "Boolean: The capital of Portugal is Lisbon.\n";

        // Act
        String actual = booleanQuestion.toString();

        // Assert
        Assertions.assertEquals(expected, actual);
    }
}
