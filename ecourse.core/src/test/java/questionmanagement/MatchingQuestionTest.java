package questionmanagement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import questionmanagment.domain.*;

import java.util.*;

public class MatchingQuestionTest {

    @Test
    public void ensureValidMatchingQuestionIsAccepted() {

        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> leftOptions = List.of("1", "2", "3");
        List<String> rightOptions = List.of("a", "b", "c");

        // Act
        MatchingQuestion matchingQuestion = new MatchingQuestion(question, penalty, quotation, leftOptions, rightOptions);

        String expected = "Question";

        // Assert
        Assertions.assertTrue(matchingQuestion.toString().contains(expected));
    }

    @Test
    public void ensureNullQuestionIsRejected() {

        // Arrange
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> leftOptions = List.of("1", "2", "3");
        List<String> rightOptions = List.of("a", "b", "c");

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new MatchingQuestion(null, penalty, quotation, leftOptions, rightOptions));

    }

    @Test
    public void ensureNullPenaltyIsRejected() {

        // Arrange
        String question = "Question";
        Float quotation = 1f;
        List<String> leftOptions = List.of("1", "2", "3");
        List<String> rightOptions = List.of("a", "b", "c");

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new MatchingQuestion(question, null, quotation, leftOptions, rightOptions));

    }

    @Test
    public void ensureNullQuotationIsRejected() {

        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        List<String> leftOptions = List.of("1", "2", "3");
        List<String> rightOptions = List.of("a", "b", "c");

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () -> new MatchingQuestion(question, penalty, null, leftOptions, rightOptions));

    }

    @Test
    public void ensureNullLeftOptionsIsRejected() {

        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> rightOptions = List.of("a", "b", "c");

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () -> new MatchingQuestion(question, penalty, quotation, null, rightOptions));

    }

    @Test
    public void ensureNullRightOptionsIsRejected() {

        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> leftOptions = List.of("1", "2", "3");

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new MatchingQuestion(question, penalty, quotation, leftOptions, null));

    }

    @Test
    public void ensureLeftOptionsSizeIsNotEqualToRightOptionsSizeIsRejected() {

        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> leftOptions = List.of("1", "2", "3");
        List<String> rightOptions = List.of("a", "b");

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new MatchingQuestion(question, penalty, quotation, leftOptions, rightOptions));

    }

    @Test
    public void ensureOneEmptyOptionInLeftOptionsIsRejected() {

        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> leftOptions = List.of("1", "", "3");
        List<String> rightOptions = List.of("a", "b", "c");

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new MatchingQuestion(question, penalty, quotation, leftOptions, rightOptions));

    }

    @Test
    public void ensureOneEmptyOptionInRightOptionsIsRejected() {

        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> leftOptions = List.of("1", "2", "3");
        List<String> rightOptions = List.of("a", "", "c");

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new MatchingQuestion(question, penalty, quotation, leftOptions, rightOptions));

    }


    @Test
    public void ensureQuestionIsNotCreatedWithEmptyLeftList() {
        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> leftOptions = new ArrayList<>();
        List<String> rightOptions = List.of("a", "b", "c");

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new MatchingQuestion(question, penalty, quotation, leftOptions, rightOptions));
    }


    @Test
    public void ensureQuestionIsNotCreatedWithEmptyRightList() {
        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> leftOptions = List.of("1", "2", "3");
        List<String> rightOptions = new ArrayList<>();

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new MatchingQuestion(question, penalty, quotation, leftOptions, rightOptions));
    }


    @Test
    public void ensureQuestionsIsUpdateWithValidInformation() {
        // Arrange
        MatchingQuestion matchingQuestion = QuestionDataSource.matchingQuestion1();

        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> leftOptions = List.of("1", "2", "3");
        List<String> rightOptions = List.of("a", "b", "c");

        // Act
        MatchingQuestion updatedQuestion = matchingQuestion.updateQuestion(question, penalty, quotation, leftOptions, rightOptions);

        // Assert
        Assertions.assertTrue(matchingQuestion.sameAs(updatedQuestion));
    }


    @Test
    public void ensureQuestionIsNotUpdatedWithInvalidQuestion() {
        // Arrange
        MatchingQuestion matchingQuestion = QuestionDataSource.matchingQuestion1();

        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> leftOptions = List.of("1", "2", "3");
        List<String> rightOptions = List.of("a", "b", "c");

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                matchingQuestion.updateQuestion(null, penalty, quotation, leftOptions, rightOptions));
    }


    @Test
    public void ensureQuestionIsNotUpdatedWithInvalidPenalty() {
        // Arrange
        MatchingQuestion matchingQuestion = QuestionDataSource.matchingQuestion1();

        String question = "Question";
        Float quotation = 1f;
        List<String> leftOptions = List.of("1", "2", "3");
        List<String> rightOptions = List.of("a", "b", "c");

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                matchingQuestion.updateQuestion(question, null, quotation, leftOptions, rightOptions));
    }


    @Test
    public void ensureQuestionIsNotUpdatedWithInvalidQuotation() {
        // Arrange
        MatchingQuestion matchingQuestion = QuestionDataSource.matchingQuestion1();

        String question = "Question";
        Float penalty = 0.5f;
        List<String> leftOptions = List.of("1", "2", "3");
        List<String> rightOptions = List.of("a", "b", "c");

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                matchingQuestion.updateQuestion(question, penalty, null, leftOptions, rightOptions));
    }


    @Test
    public void ensureQuestionIsNotUpdatedWithInvalidLeftOptions() {
        // Arrange
        MatchingQuestion matchingQuestion = QuestionDataSource.matchingQuestion1();

        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> rightOptions = List.of("a", "b", "c");

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                matchingQuestion.updateQuestion(question, penalty, quotation, null, rightOptions));
    }


    @Test
    public void ensureQuestionIsNotUpdatedWithInvalidRightOptions() {
        // Arrange
        MatchingQuestion matchingQuestion = QuestionDataSource.matchingQuestion1();

        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> leftOptions = List.of("1", "2", "3");

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                matchingQuestion.updateQuestion(question, penalty, quotation, leftOptions, null));
    }


    @Test
    public void ensureQuestionIsNotUpdatedWithEmptyLeftList() {
        // Arrange
        MatchingQuestion matchingQuestion = QuestionDataSource.matchingQuestion1();

        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> leftOptions = new ArrayList<>();
        List<String> rightOptions = List.of("a", "b", "c");

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                matchingQuestion.updateQuestion(question, penalty, quotation, leftOptions, rightOptions));
    }


    @Test
    public void ensureQuestionIsNotUpdatedWithEmptyRightList() {
        // Arrange
        MatchingQuestion matchingQuestion = QuestionDataSource.matchingQuestion1();

        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> leftOptions = List.of("1", "2", "3");
        List<String> rightOptions = new ArrayList<>();

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                matchingQuestion.updateQuestion(question, penalty, quotation, leftOptions, rightOptions));
    }


    @Test
    public void ensureQuestionIsNotUpdatedWithDifferentNumberOfLeftOptions() {
        // Arrange
        MatchingQuestion matchingQuestion = QuestionDataSource.matchingQuestion1();

        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> leftOptions = List.of("1", "2");
        List<String> rightOptions = List.of("a", "b", "c");

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                matchingQuestion.updateQuestion(question, penalty, quotation, leftOptions, rightOptions));
    }


    @Test
    public void ensureQuestionIsNotUpdatedWithDifferentNumberOfRightOptions() {
        // Arrange
        MatchingQuestion matchingQuestion = QuestionDataSource.matchingQuestion1();

        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> leftOptions = List.of("1", "2", "3");
        List<String> rightOptions = List.of("a", "b");

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                matchingQuestion.updateQuestion(question, penalty, quotation, leftOptions, rightOptions));
    }


    @Test
    public void ensureQuestionIsNotUpdatedWithEmptyValuesOnLeftList() {
        // Arrange
        MatchingQuestion matchingQuestion = QuestionDataSource.matchingQuestion1();

        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> leftOptions = List.of("", "2", "3");
        List<String> rightOptions = List.of("a", "b", "c");

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                matchingQuestion.updateQuestion(question, penalty, quotation, leftOptions, rightOptions));
    }


    @Test
    public void ensureQuestionIsNotUpdatedWithEmptyValuesOnRightList() {
        // Arrange
        MatchingQuestion matchingQuestion = QuestionDataSource.matchingQuestion1();

        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<String> leftOptions = List.of("1", "2", "3");
        List<String> rightOptions = List.of("", "b", "c");

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                matchingQuestion.updateQuestion(question, penalty, quotation, leftOptions, rightOptions));
    }


    @Test
    public void ensureQuestionKnowsHowToCorrectItself() {
        // Arrange
        MatchingQuestion matchingQuestion = QuestionDataSource.matchingQuestion1();
        String correctAnswer = "Portugal-Lisbon,Spain-Madrid,France-Paris,Germany-Berlin,Italy-Rome";
        float expectedGrade = 50;

        // Act
        Float actual = matchingQuestion.correctStudentAnswer(correctAnswer);

        // Assert
        Assertions.assertEquals(expectedGrade, actual);
        Assertions.assertTrue(matchingQuestion.isCorrect(correctAnswer));
    }

    @Test
    public void ensureQuestionKnowsHowToIncorrectItself() {
        // Arrange
        MatchingQuestion matchingQuestion = QuestionDataSource.matchingQuestion1();
        String correctAnswer = "Portugal-Lisbon,Spain-Madrid,France-Paris,Germany-Berlin,Italy-Rome";
        float expectedGrade = 40;

        // Act
        Float actual = matchingQuestion.correctStudentAnswer("Portugal-Lisbon,Spain-Madrid,France-Paris,Germany-Berlin,Italy-Berlin");

        // Assert
        Assertions.assertEquals(expectedGrade, actual);
        //Assertions.assertFalse(matchingQuestion.isCorrect(correctAnswer));
    }

    @Test
    public void ensureQuestionKnowsHowToCorrectItselfWithNullAnswer() {
        // Arrange
        MatchingQuestion matchingQuestion = QuestionDataSource.matchingQuestion1();

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                matchingQuestion.correctStudentAnswer(null));
    }


    @Test
    void ensureMatchingQuestionReturnsLeftOptions() {
        // Arrange
        MatchingQuestion matchingQuestion = QuestionDataSource.matchingQuestion1();
        List<String> expected = List.of("Portugal", "Spain", "France", "Germany", "Italy");

        // Act
        List<String> actual = matchingQuestion.leftOptions();

        // Assert
        Assertions.assertEquals(expected, actual);
    }


    @Test
    void ensureMatchingQuestionReturnsRightOptions() {
        // Arrange
        MatchingQuestion matchingQuestion = QuestionDataSource.matchingQuestion1();
        List<String> expected = List.of("Lisbon", "Madrid", "Paris", "Berlin", "Rome");

        // Act
        List<String> actual = matchingQuestion.rightOptions();

        // Assert
        Assertions.assertEquals(expected, actual);
    }


    @Test
    void ensureMatchingQuestionKnowsHowToCorrectItself() {
        // Arrange
        MatchingQuestion matchingQuestion = QuestionDataSource.matchingQuestion1();
        String correctAnswer = "Portugal-Lisbon,Spain-Madrid,France-Paris,Germany-Berlin,Italy-Rome";
        float expectedGrade = 50;

        // Act
        Float actual = matchingQuestion.correctStudentAnswer(correctAnswer);

        // Assert
        Assertions.assertEquals(expectedGrade, actual);
        Assertions.assertTrue(matchingQuestion.isCorrect(correctAnswer));
    }


    @Test
    void ensureMatchingQuestionKnowsHowToIncorrectItselfWithNullAnswer() {
        // Arrange
        MatchingQuestion matchingQuestion = QuestionDataSource.matchingQuestion1();

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                matchingQuestion.correctStudentAnswer(null));
    }


    @Test
    void ensureMatchingQuestionKnowsHowToIncorrectItselfWithEmptyAnswer() {
        // Arrange
        MatchingQuestion matchingQuestion = QuestionDataSource.matchingQuestion1();

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                matchingQuestion.correctStudentAnswer(""));
    }


    @Test
    void ensureMatchingQuestionKnowsHowToIncorrectItselfWithBlankAnswer() {
        // Arrange
        MatchingQuestion matchingQuestion = QuestionDataSource.matchingQuestion1();

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                matchingQuestion.correctStudentAnswer(""));
    }


    @Test
    void ensureMatchingQuestionKnowsHowToIncorrectItselfWithWrongAnswer() {
        // Arrange
        MatchingQuestion matchingQuestion = QuestionDataSource.matchingQuestion1();
        float expectedGrade = 40;

        // Act
        Float actual = matchingQuestion.
                correctStudentAnswer("Portugal-Lisbon,Spain-Madrid,France-Paris,Germany-Berlin,Italy-Berlin");

        // Assert
        Assertions.assertEquals(expectedGrade, actual);
    }


    @Test
    void ensureMatchingQuestionKnowsHowToIncorrectItselfIfAnswerHasMoreOptions() {
        // Arrange
        MatchingQuestion matchingQuestion = QuestionDataSource.matchingQuestion1();

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                matchingQuestion.correctStudentAnswer("Portugal-Lisbon,Spain-Madrid,France-Paris,Germany-Berlin,Italy-Rome,Spain-Barcelona"));
    }


    @Test
    void ensureQuestionStringToFileMethod() {
        // Arrange
        MatchingQuestion matchingQuestion = QuestionDataSource.matchingQuestion1();
        String expected = "MATCHING:Match the following countries with their capitals\n" +
                "MATCHES:Portugal-Lisbon,Spain-Madrid,France-Paris,Germany-Berlin,Italy-Rome\n" +
                "GRADE:10.0\n";

        // Act
        String actual = matchingQuestion.stringToFile();

        // Assert
        Assertions.assertEquals(expected, actual);
    }


    @Test
    void ensureQuestionsReturnsCorrectAnswerInString() {
        // Arrange
        MatchingQuestion matchingQuestion = QuestionDataSource.matchingQuestion1();
        String expected = "{ Portugal-Lisbon,Spain-Madrid,France-Paris,Germany-Berlin,Italy-Rome }";

        // Act
        String actual = matchingQuestion.correctAnswer();

        // Assert
        Assertions.assertEquals(expected, actual);
    }
}
