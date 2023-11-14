package questionmanagement;

import questionmanagment.domain.ShortAnswerQuestion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ShortAnswerQuestionTest {

    @Test
    public void ensureValidShortAnswerQuestionIsAccepted() {

        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        String correctAnswer = "Answer";

        // Act
        ShortAnswerQuestion shortAnswerQuestion = new ShortAnswerQuestion(question, correctAnswer, penalty, quotation);

        String expected = "Question";

        // Assert
        Assertions.assertTrue(shortAnswerQuestion.toString().contains(expected));
    }

    @Test
    public void ensureNullQuestionIsRejected() {

        // Arrange
        Float penalty = 0.5f;
        Float quotation = 1f;
        String correctAnswer = "Answer";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ShortAnswerQuestion(null, correctAnswer, penalty, quotation));

    }

    @Test
    public void ensureNullPenaltyIsRejected() {

        // Arrange
        String question = "Question";
        Float quotation = 1f;
        String correctAnswer = "Answer";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new ShortAnswerQuestion(question, correctAnswer, null, quotation));

    }

    @Test
    public void ensureNullQuotationIsRejected() {

        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        String correctAnswer = "Answer";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new ShortAnswerQuestion(question, correctAnswer, penalty, null));

    }

    @Test
    public void ensureNullCorrectAnswerIsRejected() {

        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new ShortAnswerQuestion(question, null, penalty, quotation));

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
                new ShortAnswerQuestion(question, correctAnswer, penalty, quotation));

    }


    @Test
    public void ensureQuestionIsUpdatedWithValidInformation() {
        // Arrange
        ShortAnswerQuestion shortAnswerQuestion = QuestionDataSource.shortAnswerQuestion2();

        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        String correctAnswer = "Answer";

        // Act
        ShortAnswerQuestion updatedQuestion = shortAnswerQuestion.updateQuestion(
                question, penalty, quotation, correctAnswer);

        // Assert
        Assertions.assertTrue(shortAnswerQuestion.sameAs(updatedQuestion));
    }


    @Test
    public void ensureQuestionIsNotUpdatedWithInvalidQuestion() {
        // Arrange
        ShortAnswerQuestion shortAnswerQuestion = QuestionDataSource.shortAnswerQuestion2();

        Float penalty = 0.5f;
        Float quotation = 1f;
        String correctAnswer = "Answer";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                shortAnswerQuestion.updateQuestion(null, penalty, quotation, correctAnswer));
    }


    @Test
    public void ensureQuestionIsNotUpdatedWithInvalidPenalty() {
        // Arrange
        ShortAnswerQuestion shortAnswerQuestion = QuestionDataSource.shortAnswerQuestion2();

        String question = "Question";
        Float quotation = 1f;
        String correctAnswer = "Answer";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                shortAnswerQuestion.updateQuestion(question, null, quotation, correctAnswer));
    }


    @Test
    public void ensureQuestionIsNotUpdatedWithInvalidQuotation() {
        // Arrange
        ShortAnswerQuestion shortAnswerQuestion = QuestionDataSource.shortAnswerQuestion2();

        String question = "Question";
        Float penalty = 0.5f;
        String correctAnswer = "Answer";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                shortAnswerQuestion.updateQuestion(question, penalty, null, correctAnswer));
    }


    @Test
    public void ensureQuestionIsNotUpdatedWithInvalidCorrectAnswer() {
        // Arrange
        ShortAnswerQuestion shortAnswerQuestion = QuestionDataSource.shortAnswerQuestion2();

        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                shortAnswerQuestion.updateQuestion(question, penalty, quotation, null));
    }

    @Test
    public void ensureQuestionKnowsHowToCorrectItself() {
        // Arrange
        ShortAnswerQuestion shortAnswerQuestion = QuestionDataSource.shortAnswerQuestion2();
        float expected = 10f;

        // Act
        Float actual = shortAnswerQuestion.correctStudentAnswer("Russia");

        // Assert
        Assertions.assertEquals(expected, actual);
        Assertions.assertTrue(shortAnswerQuestion.isCorrect("Russia"));
    }

    @Test
    public void ensureQuestionKnowsHowToCorrectItselfWithWrongAnswer() {
        // Arrange
        ShortAnswerQuestion shortAnswerQuestion = QuestionDataSource.shortAnswerQuestion2();
        float expected = 0f;

        // Act
        Float actual = shortAnswerQuestion.correctStudentAnswer("Wrong answer");

        // Assert
        Assertions.assertEquals(expected, actual);
        Assertions.assertFalse(shortAnswerQuestion.isCorrect("Wrong answer"));
    }

    @Test
    public void ensureQuestionKnowsHowToCorrectItselfWithNullAnswer() {
        // Arrange
        ShortAnswerQuestion shortAnswerQuestion = QuestionDataSource.shortAnswerQuestion2();

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                shortAnswerQuestion.correctStudentAnswer(null));
    }

    // Test the equals method
    @Test
    public void ensureQuestionsEqualMethod() {
        // Arrange
        ShortAnswerQuestion shortAnswerQuestion = QuestionDataSource.shortAnswerQuestion2();
        ShortAnswerQuestion shortAnswerQuestion2 = QuestionDataSource.shortAnswerQuestion2();

        // Act
        boolean actual = shortAnswerQuestion.equals(shortAnswerQuestion2);

        // Assert
        Assertions.assertTrue(actual);
    }

    @Test
    public void ensureQuestionsNotEqualMethod2() {
        // Arrange
        ShortAnswerQuestion shortAnswerQuestion = QuestionDataSource.shortAnswerQuestion2();
        String s = "Testes";

        // Assert
        Assertions.assertNotEquals(shortAnswerQuestion, s);
    }


    @Test
    void ensureQuestionStringToFileMethod() {
        // Arrange
        ShortAnswerQuestion shortAnswerQuestion = QuestionDataSource.shortAnswerQuestion2();
        String expected = "SHORT_ANSWER:Which country has the largest area in the world?\n" +
                "ANSW:Russia\n" +
                "GRADE:10.0\n";

        // Act
        String actual = shortAnswerQuestion.stringToFile();

        // Assert
        Assertions.assertEquals(expected, actual);
    }
}
