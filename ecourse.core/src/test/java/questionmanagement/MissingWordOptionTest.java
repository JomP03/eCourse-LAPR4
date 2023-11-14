package questionmanagement;

import questionmanagment.domain.MissingWordOption;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MissingWordOptionTest {

    @Test
    public void ensureValidMissingWordOptionIsAccepted() {

        // Arrange
        List<String> optionAnswers = List.of("Answer", "Answer2");

        // Act
        MissingWordOption missingWordOption = new MissingWordOption(optionAnswers, "Answer");

        // Assert
        Assertions.assertNotNull(missingWordOption);
    }

    @Test
    public void ensureNullOptionAnswersIsRejected() {

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new MissingWordOption(null, "Answer"));

    }

    @Test
    public void ensureEmptyOptionAnswersIsRejected() {

        // Arrange
        List<String> optionAnswers = List.of();

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () -> new MissingWordOption(optionAnswers, "Answer"));

    }

    @Test
    public void ensureOneEmptyOptionAnswerIsRejected() {

        // Arrange
        List<String> optionAnswers = List.of("Answer", "");

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
            new MissingWordOption(optionAnswers, "Answer"));

    }

    @Test
    public void ensureOneBlankOptionAnswerIsRejected() {

        // Arrange
        List<String> optionAnswers = List.of("Answer", "Answer2");

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
            new MissingWordOption(optionAnswers, "  "));

    }


    @Test
    public void ensureOptionIsNotCreateWithAOptionListSmallerThan2() {
        // Arrange
        List<String> optionAnswers = List.of("Answer");

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
            new MissingWordOption(optionAnswers, "Answer"));

    }


    @Test
    public void ensureOptionIsNotCreatedIfAnwerIsNotOnList() {
        // Arrange
        List<String> optionAnswers = List.of("Answer", "Answer2");

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
            new MissingWordOption(optionAnswers, "Answer3"));
    }


    @Test
    public void ensureReturnsOptions() {
        // Arrange
        List<String> optionAnswers = List.of("Answer", "Answer2");
        MissingWordOption missingWordOption = new MissingWordOption(optionAnswers, "Answer");

        // Act
        List<String> options = missingWordOption.optionAnswers();

        // Assert
        Assertions.assertEquals(optionAnswers, options);
    }


    @Test
    public void ensureReturnsCorrectAnswer() {
        // Arrange
        List<String> optionAnswers = List.of("Answer", "Answer2");
        MissingWordOption missingWordOption = new MissingWordOption(optionAnswers, "Answer");

        // Act
        String correctAnswer = missingWordOption.answer();

        // Assert
        Assertions.assertEquals("Answer", correctAnswer);
    }

    @Test
    public void ensureCorrectAnswer() {
        // Arrange
        List<String> optionAnswers = List.of("Answer", "Answer2");
        MissingWordOption missingWordOption = new MissingWordOption(optionAnswers, "Answer");

        // Act
        boolean isCorrect = missingWordOption.isCorrect("Answer");

        // Assert
        Assertions.assertTrue(isCorrect);
    }

    @Test
    public void ensureIncorrectAnswer() {
        // Arrange
        List<String> optionAnswers = List.of("Answer", "Answer2");
        MissingWordOption missingWordOption = new MissingWordOption(optionAnswers, "Answer");

        // Act
        boolean isCorrect = missingWordOption.isCorrect("Answer2");

        // Assert
        Assertions.assertFalse(isCorrect);
    }


    @Test
    void ensureQuestionKnowsHowToCorrectItself() {
        // Arrange
        List<String> optionAnswers = List.of("Answer", "Answer2");
        MissingWordOption missingWordOption = new MissingWordOption(optionAnswers, "Answer");

        // Act
        boolean isCorrect = missingWordOption.isCorrect("Answer");

        // Assert
        Assertions.assertTrue(missingWordOption.correctAnswer("Answer"));
        Assertions.assertTrue(isCorrect);
    }


    @Test
    void ensureQuestionKnowsHowToIncorrectItselfWithNullAnswer() {
        // Arrange
        List<String> optionAnswers = List.of("Answer", "Answer2");
        MissingWordOption missingWordOption = new MissingWordOption(optionAnswers, "Answer");

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
            missingWordOption.correctAnswer(null));
    }


    @Test
    void ensureQuestionKnowsHowToIncorrectItselfWithEmptyAnswer() {
        // Arrange
        List<String> optionAnswers = List.of("Answer", "Answer2");
        MissingWordOption missingWordOption = new MissingWordOption(optionAnswers, "Answer");

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                missingWordOption.correctAnswer(""));
    }


    @Test
    void ensureQuestionKnowsHowToIncorrectItselfWithBlankAnswer() {
        // Arrange
        List<String> optionAnswers = List.of("Answer", "Answer2");
        MissingWordOption missingWordOption = new MissingWordOption(optionAnswers, "Answer");

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                missingWordOption.correctAnswer(""));
    }


    @Test
    void ensureMissingWordOptionsStringToFileMethod() {
        // Arrange
        List<String> optionAnswers = List.of("Answer", "Answer2");
        MissingWordOption missingWordOption = new MissingWordOption(optionAnswers, "Answer");

        StringBuilder sb = new StringBuilder();

        sb.append("CORANSW:").append(missingWordOption.answer()).append("\n");

        for (String optionAnswer : optionAnswers) {
            sb.append("ANSW:").append(optionAnswer).append("\n");
        }


        // Act
        String stringToFIle = missingWordOption.stringToFile();

        // Assert
        Assertions.assertEquals(sb.toString(), stringToFIle);
    }


    @Test
    void ensureMissingWordOptionsOptionsToTxtMethod() {
        // Arrange
        List<String> optionAnswers = List.of("Answer", "Answer2");
        MissingWordOption missingWordOption = new MissingWordOption(optionAnswers, "Answer");

        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (String optionAnswer : optionAnswers) {
            if(first) {
                sb.append(optionAnswer);
                first = false;
            } else {
                sb.append(",").append(optionAnswer);
            }
        }

        // Act
        String optionsToTxt = missingWordOption.optionsToTxt();

        // Assert
        Assertions.assertEquals(sb.toString(), optionsToTxt);
    }



}




