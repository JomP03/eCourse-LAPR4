package questionmanagement;

import questionmanagment.domain.MissingWordOption;
import questionmanagment.domain.MissingWordQuestion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MissingWordQuestionTest {

    @Test
    public void ensureValidMissingWordQuestionIsAccepted() {

        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<MissingWordOption> answerOptions = List.of(new MissingWordOption(List.of("Answer", "Answer2"), "Answer"));

        // Act
        MissingWordQuestion missingWordQuestion = new MissingWordQuestion(question, answerOptions, penalty, quotation);

        String expected = "Question";

        // Assert
        Assertions.assertTrue(missingWordQuestion.toString().contains(expected));
    }

    @Test
    public void ensureNullQuestionIsRejected() {

        // Arrange
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<MissingWordOption> answerOptions = List.of(new MissingWordOption(List.of("Answer", "Answer2"), "Answer"));

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new MissingWordQuestion(null, answerOptions, penalty, quotation));

    }

    @Test
    public void ensureNullPenaltyIsRejected() {

        // Arrange
        String question = "Question";
        Float quotation = 1f;
        List<MissingWordOption> answerOptions = List.of(new MissingWordOption(
                List.of("Answer", "Answer2"), "Answer"));

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new MissingWordQuestion(question, answerOptions, null, quotation));

    }

    @Test
    public void ensureNullQuotationIsRejected() {

        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        List<MissingWordOption> answerOptions = List.of(
                new MissingWordOption(List.of("Answer", "Answer2"), "Answer"));

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new MissingWordQuestion(question, answerOptions, penalty, null));

    }

    @Test
    public void ensureNullAnswerOptionsIsRejected() {

        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new MissingWordQuestion(question, null, penalty, quotation));

    }

    @Test
    public void ensureEmptyAnswerOptionsIsRejected() {

        // Arrange
        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<MissingWordOption> answerOptions = List.of();

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new MissingWordQuestion(question, answerOptions, penalty, quotation));

    }


    @Test
    public void ensureQuestionsIsUpdateWithValidInformation() {
        // Arrange
        MissingWordQuestion missingWordQuestion = QuestionDataSource.missingWordQuestion1();

        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;
        List<MissingWordOption> answerOptions = List.of(
                new MissingWordOption(List.of("Answer", "Answer2"), "Answer"));

        // Act
        MissingWordQuestion updatedQuestion = missingWordQuestion.updateQuestion(
                question, penalty, quotation, answerOptions);

        // Assert
        Assertions.assertTrue(missingWordQuestion.sameAs(updatedQuestion));
    }


    @Test
    public void ensureQuestionIsNotUpdatedWthInvalidQuestion() {
        // Arrange
        MissingWordQuestion missingWordQuestion = QuestionDataSource.missingWordQuestion1();

        Float penalty = 0.5f;
        Float quotation = 1f;
        List<MissingWordOption> answerOptions = List.of(
                new MissingWordOption(List.of("Answer", "Answer2"), "Answer"));

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                missingWordQuestion.updateQuestion(null, penalty, quotation, answerOptions));
    }


    @Test
    public void ensureQuestionIsNotUpdatedWthInvalidPenalty() {
        // Arrange
        MissingWordQuestion missingWordQuestion = QuestionDataSource.missingWordQuestion1();

        String question = "Question";
        Float quotation = 1f;
        List<MissingWordOption> answerOptions = List.of(
                new MissingWordOption(List.of("Answer", "Answer2"), "Answer"));

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                missingWordQuestion.updateQuestion(question, null, quotation, answerOptions));
    }


    @Test
    public void ensureQuestionIsNotUpdatedWthInvalidQuotation() {
        // Arrange
        MissingWordQuestion missingWordQuestion = QuestionDataSource.missingWordQuestion1();

        String question = "Question";
        Float penalty = 0.5f;
        List<MissingWordOption> answerOptions = List.of(
                new MissingWordOption(List.of("Answer", "Answer2"), "Answer"));

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                missingWordQuestion.updateQuestion(question, penalty, null, answerOptions));
    }


    @Test
    public void ensureQuestionIsNotUpdatedWthInvalidAnswerOptions() {
        // Arrange
        MissingWordQuestion missingWordQuestion = QuestionDataSource.missingWordQuestion1();

        String question = "Question";
        Float penalty = 0.5f;
        Float quotation = 1f;

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                missingWordQuestion.updateQuestion(question, penalty, quotation, null));
    }


    @Test
    public void ensureQuestionKnowsHowToCorrectItself() {
        // Arrange
        MissingWordQuestion missingWordQuestion = QuestionDataSource.missingWordQuestion1();
        float expected = 0.7f;

        // Act
        float actual = missingWordQuestion.correctStudentAnswer("skin");

        // Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void ensureQuestionKnowsHowToIncorrectItself() {
        // Arrange
        MissingWordQuestion missingWordQuestion = QuestionDataSource.missingWordQuestion1();
        float expected = 0f;

        // Act
        float actual = missingWordQuestion.correctStudentAnswer("wrong");

        // Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void ensureQuestionKnowsHowToIncorrectItselfWithNullAnswer() {
        // Arrange
        MissingWordQuestion missingWordQuestion = QuestionDataSource.missingWordQuestion1();

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                missingWordQuestion.correctStudentAnswer(null));
    }

    @Test
    public void ensureQuestionReturnsCorrectAnswer() {
        // Arrange
        MissingWordQuestion missingWordQuestion = QuestionDataSource.missingWordQuestion1();
        String expected = "\nFor 1 -> skin";

        // Act
        String actual = missingWordQuestion.correctAnswer();

        // Assert
        Assertions.assertEquals(expected, actual);
    }


    @Test
    void ensureQuestionReturnsMissingWordsOptions() {
        // Arrange
        MissingWordQuestion missingWordQuestion = QuestionDataSource.missingWordQuestion1();
        List<MissingWordOption> expected = List.of(
                new MissingWordOption(List.of("brain", "heart", "skin"), "skin"));

        // Act
        List<MissingWordOption> actual = missingWordQuestion.missingOptions();

        // Assert
        Assertions.assertEquals(expected, actual);

    }


    @Test
    void ensureQuestionToString() {
        // Arrange
        MissingWordQuestion missingWordQuestion = QuestionDataSource.missingWordQuestion1();

        String expected = "Missing Word: The [[n]] is the largest organ in the human body.\n" +
                "\n" +
                "\tFor 1 :\n" +
                "\ta) brain\n" +
                "\tb) heart\n" +
                "\tc) skin" +
                "\n";

        // Act
        String actual = missingWordQuestion.toString();

        // Assert
        Assertions.assertEquals(expected, actual);
    }


    @Test
    void ensureQuestionStringToFile() {
        // Arrange
        MissingWordQuestion missingWordQuestion = QuestionDataSource.missingWordQuestion1();

        String expected = "MISSING_WORD:The [[n]] is the largest organ in the human body.\n" +
                "GRADE:0.7\n" +
                "CORANSW:skin\n" +
                "ANSW:brain\n" +
                "ANSW:heart\n" +
                "ANSW:skin\n" +
                "\n\n";

        // Act
        String actual = missingWordQuestion.stringToFile();

        // Assert
        Assertions.assertEquals(expected, actual);
    }


    @Test
    void ensureMissingWordQuestionToTextMethod() {
        // Arrange
        MissingWordQuestion missingWordQuestion = QuestionDataSource.missingWordQuestion1();

        String expected = "The [[n]] is the largest organ in the human body. { For 1 -> brain,heart,skin }";

        // Act
        String actual = missingWordQuestion.questionToTxt();

        // Assert
        Assertions.assertEquals(expected, actual);
    }


    @Test
    void ensureQuestionsKnowsHowToCorrectItself() {
        // Arrange
        MissingWordQuestion missingWordQuestion = QuestionDataSource.missingWordQuestion1();

        // Act
        boolean actual = missingWordQuestion.isCorrect("skin");

        // Assert
        Assertions.assertTrue(actual);
    }
}
