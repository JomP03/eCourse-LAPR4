package questionmanagement;

import questionmanagment.domain.NumericalQuestion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NumericalQuestionTest {

    @Test
    public void ensureValidNumericalQuestionIsAccepted() {

        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        String correctAnswer = "1";

        // Act
        NumericalQuestion numericalQuestion = new NumericalQuestion(question, correctAnswer, penalty, quotation);

        String expected = "Question";

        // Assert
        Assertions.assertTrue(numericalQuestion.toString().contains(expected));
    }

    @Test
    public void ensureNullQuestionIsRejected() {

        // Arrange
        Float penalty = 0.5f;
        Float quotation = 1f;
        String correctAnswer = "1";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new NumericalQuestion(null, correctAnswer, penalty, quotation));

    }

    @Test
    public void ensureNullPenaltyIsRejected() {

        // Arrange
        String question = "Question";
        Float quotation = 1f;
        String correctAnswer = "1";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new NumericalQuestion(question, correctAnswer, null, quotation));

    }

    @Test
    public void ensureNullQuotationIsRejected() {

        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        String correctAnswer = "1";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new NumericalQuestion(question, correctAnswer, penalty, null));

    }

    @Test
    public void ensureNullCorrectAnswerIsRejected() {

        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new NumericalQuestion(question, null, penalty, quotation));

    }

    @Test
    public void ensureEmptyCorrectAnswerIsRejected() {

        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        String correctAnswer = "";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new NumericalQuestion(question, correctAnswer, penalty, quotation));

    }


    @Test
    public void ensureQuestionIsUpdatedWithValidInformation() {
        // Arrange
        NumericalQuestion numericalQuestion = QuestionDataSource.numericalQuestion1();

        String newQuestion = "New Question";
        Float newPenalty = 0.5f;
        Float newQuotation = 1f;
        String newCorrectAnswer = "2";

        // Act
        NumericalQuestion updatedNumericalQuestion = numericalQuestion.updateQuestion(
                newQuestion, newPenalty, newQuotation, newCorrectAnswer);

        // Assert
        Assertions.assertTrue(numericalQuestion.sameAs(updatedNumericalQuestion));
    }


    @Test
    public void ensureQuestionIsNotUpdatedWithInvalidQuestion() {
        // Arrange
        NumericalQuestion numericalQuestion = QuestionDataSource.numericalQuestion1();

        Float newPenalty = 0.5f;
        Float newQuotation = 1f;
        String newCorrectAnswer = "2";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                numericalQuestion.updateQuestion(null, newPenalty, newQuotation, newCorrectAnswer));
    }


    @Test
    public void ensureQuestionIsNotUpdatedWithInvalidPenalty() {
        // Arrange
        NumericalQuestion numericalQuestion = QuestionDataSource.numericalQuestion1();

        String newQuestion = "New Question";
        Float newQuotation = 1f;
        String newCorrectAnswer = "2";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                numericalQuestion.updateQuestion(newQuestion, null, newQuotation, newCorrectAnswer));
    }


    @Test
    public void ensureQuestionIsNotUpdatedWithInvalidQuotation() {
        // Arrange
        NumericalQuestion numericalQuestion = QuestionDataSource.numericalQuestion1();

        String newQuestion = "New Question";
        Float newPenalty = 0.5f;
        String newCorrectAnswer = "2";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                numericalQuestion.updateQuestion(newQuestion, newPenalty, null, newCorrectAnswer));
    }


    @Test
    public void ensureQuestionIsNotUpdatedWithInvalidCorrectAnswer() {
        // Arrange
        NumericalQuestion numericalQuestion = QuestionDataSource.numericalQuestion1();

        String newQuestion = "New Question";
        Float newPenalty = 0.5f;
        Float newQuotation = 1f;

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                numericalQuestion.updateQuestion(newQuestion, newPenalty, newQuotation, null));
    }

    @Test
    public void ensureQuestionKnowsHowToCorrectItself() {
        // Arrange
        NumericalQuestion numericalQuestion = QuestionDataSource.numericalQuestion1();
        float expected = 10f;

        // Act
        Float actual = numericalQuestion.correctStudentAnswer("3.14");

        // Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void ensureQuestionKnowsHowToCorrectItselfWithWrongAnswer() {
        // Arrange
        NumericalQuestion numericalQuestion = QuestionDataSource.numericalQuestion1();
        float expected = 0f;

        // Act
        Float actual = numericalQuestion.correctStudentAnswer("3.15");

        // Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void ensureQuestionKnowsHowToCorrectItselfWithNullAnswer() {
        // Arrange
        NumericalQuestion numericalQuestion = QuestionDataSource.numericalQuestion1();

        //Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                numericalQuestion.correctStudentAnswer(null));
    }

    @Test
    public void ensureQuestionRetunsCorrectAnswer() {
        // Arrange
        NumericalQuestion numericalQuestion = QuestionDataSource.numericalQuestion1();
        String expected = "3.14";

        // Act
        String actual = numericalQuestion.correctAnswer();

        // Assert
        Assertions.assertEquals(expected, actual);
    }


    @Test
    void ensureQuestionToString() {
        // Arrange
        NumericalQuestion numericalQuestion = QuestionDataSource.numericalQuestion1();
        String expected = "Numerical: What is the value of PI?\n";

        // Act
        String actual = numericalQuestion.toString();

        // Assert
        Assertions.assertEquals(expected, actual);
    }


    @Test
    void ensureQuestionStringToFileMethod() {
        // Arrange
        NumericalQuestion numericalQuestion = QuestionDataSource.numericalQuestion1();
        String expected = "NUMERICAL:What is the value of PI?\n" +
                "ANSW:3.14\n" +
                "GRADE:10.0\n";

        // Act
        String actual = numericalQuestion.stringToFile();

        // Assert
        Assertions.assertEquals(expected, actual);
    }


    @Test
    void ensureQuestionCorrectsItself() {
        // Arrange
        NumericalQuestion numericalQuestion = QuestionDataSource.numericalQuestion1();

        // Act
        boolean actual = numericalQuestion.isCorrect("3.14");

        // Assert
        Assertions.assertTrue(actual);
    }
}
