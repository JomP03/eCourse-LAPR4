package questionmanagement;


import org.junit.jupiter.api.*;
import questionmanagment.application.*;
import questionmanagment.domain.*;
import questionmanagment.domain.factory.*;

import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuestionFactoryTest {

    QuestionFactory questionFactory;
    QuestionTxtGenerator generator;
    QuestionValidator validator;

    @BeforeEach
    void setUp() {
        generator = mock(QuestionTxtGenerator.class);
        validator = mock(QuestionValidator.class);
        questionFactory = new QuestionFactory(validator, generator);
    }

    @Test
    public void ensureQuestionFactoryCreatesMultipleChoiceWithValidInformation() {
        // Arrange
        String question = "Test Question";
        Float quotation = 1.0f;
        Float penalty = 0.5f;
        List<String> options = List.of("Option 1", "Option 2", "Option 3");
        MultipleChoiceQuestion expectedQuestion = new MultipleChoiceQuestion(
                question, options, "Option 1", quotation, penalty);

        // Act
        when(generator.generateMultipleChoiceQuestion(
                question, quotation, penalty, options, "Option 1"))
                .thenReturn(QuestionDataSource.multipleChoiceFile);
        when(validator.validate(QuestionDataSource.multipleChoiceFile.getPath()))
                .thenReturn(expectedQuestion);
        MultipleChoiceQuestion actualAnswer = questionFactory.createMultipleChoiceQuestion(
                question, quotation, penalty, options, "Option 1");

        // Assert
        Assertions.assertEquals(expectedQuestion, actualAnswer);
    }

    @Test
    public void ensureQuestionFactoryDoesNotCreateMultipleChoiceWithInvalidInformation() {
        // Arrange
        String question = "";
        Float quotation = 1.0f;
        Float penalty = 0.5f;
        List<String> options = List.of("Option 1", "Option 2", "Option 3");

        // Act
        when(generator.generateMultipleChoiceQuestion(
                question, quotation, penalty, options, "Option 1"))
                .thenReturn(QuestionDataSource.multipleChoiceFile);
        when(validator.validate(QuestionDataSource.multipleChoiceFile.getPath()))
                .thenReturn(null);

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            questionFactory.createMultipleChoiceQuestion(
                    question, quotation, penalty, options, "Option 1");
        });
    }

    @Test
    public void ensureQuestionFactoryCreatesMatchingWithValidInformation() {
        // Arrange
        String question = "Test Question";
        Float quotation = 1.0f;
        Float penalty = 0.5f;
        List<String> leftOptions = List.of("Option 1", "Option 2", "Option 3");
        List<String> rightOptions = List.of("Option 1", "Option 2", "Option 3");
        MatchingQuestion expectedQuestion = new MatchingQuestion(
                question, quotation, penalty, leftOptions, rightOptions);

        // Act
        when(generator.generateMatchingQuestion(
                question, quotation, penalty, leftOptions, rightOptions))
                .thenReturn(QuestionDataSource.matchingFile);
        when(validator.validate(QuestionDataSource.matchingFile.getPath()))
                .thenReturn(expectedQuestion);
        MatchingQuestion actualAnswer = questionFactory.createMatchingQuestion(
                question, quotation, penalty, leftOptions, rightOptions);

        // Assert
        Assertions.assertEquals(expectedQuestion, actualAnswer);
    }

    @Test
    public void ensureQuestionFactoryDoesNotCreateMatchingWithInvalidInformation() {
        // Arrange
        String question = "";
        Float quotation = 1.0f;
        Float penalty = 0.5f;
        List<String> leftOptions = List.of("Option 1", "Option 2", "Option 3");
        List<String> rightOptions = List.of("Option 1", "Option 2", "Option 3");

        // Act
        when(generator.generateMatchingQuestion(
                question, quotation, penalty, leftOptions, rightOptions))
                .thenReturn(QuestionDataSource.matchingFile);
        when(validator.validate(QuestionDataSource.matchingFile.getPath()))
                .thenReturn(null);

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            questionFactory.createMatchingQuestion(
                    question, quotation, penalty, leftOptions, rightOptions);
        });
    }

    @Test
    public void ensureQuestionFactoryCreatesNumericWithValidInformation() {
        // Arrange
        String question = "Test Question";
        Float quotation = 1.0f;
        Float penalty = 0.5f;
        String answer = "1";
        NumericalQuestion expectedQuestion = new NumericalQuestion(
                question, answer, quotation, penalty);

        // Act
        when(generator.generateNumericalQuestion(
                question, quotation, penalty, answer))
                .thenReturn(QuestionDataSource.numericalFile);
        when(validator.validate(QuestionDataSource.numericalFile.getPath()))
                .thenReturn(expectedQuestion);
        NumericalQuestion actualAnswer = questionFactory.createNumericalQuestion(
                question, quotation, penalty, answer);

        // Assert
        Assertions.assertEquals(expectedQuestion, actualAnswer);
    }

    @Test
    public void ensureQuestionFactoryDoesNotCreateNumericWithInvalidInformation() {
        // Arrange
        String question = "";
        Float quotation = 1.0f;
        Float penalty = 0.5f;
        String answer = "1";

        // Act
        when(generator.generateNumericalQuestion(
                question, quotation, penalty, answer))
                .thenReturn(QuestionDataSource.numericalFile);
        when(validator.validate(QuestionDataSource.numericalFile.getPath()))
                .thenReturn(null);

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            questionFactory.createNumericalQuestion(
                    question, quotation, penalty, answer);
        });
    }

    @Test
    public void ensureQuestionFactoryCreatesTrueFalseWithValidInformation() {
        // Arrange
        String question = "Test Question";
        Float quotation = 1.0f;
        Float penalty = 0.5f;
        boolean answer = true;
        BooleanQuestion expectedQuestion = new BooleanQuestion(
                question, answer, quotation, penalty);

        // Act
        when(generator.generateBooleanQuestion(
                question, quotation, penalty, answer))
                .thenReturn(QuestionDataSource.booleanFile);
        when(validator.validate(QuestionDataSource.booleanFile.getPath()))
                .thenReturn(expectedQuestion);
        BooleanQuestion actualAnswer = questionFactory.createBooleanQuestion(
                question, quotation, penalty, answer);

        // Assert
        Assertions.assertEquals(expectedQuestion, actualAnswer);
    }

    @Test
    public void ensureQuestionFactoryDoesNotCreateTrueFalseWithInvalidInformation() {
        // Arrange
        String question = "";
        Float quotation = 1.0f;
        Float penalty = 0.5f;
        boolean answer = true;

        // Act
        when(generator.generateBooleanQuestion(
                question, quotation, penalty, answer))
                .thenReturn(QuestionDataSource.booleanFile);
        when(validator.validate(QuestionDataSource.booleanFile.getPath()))
                .thenReturn(null);

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            questionFactory.createBooleanQuestion(
                    question, quotation, penalty, answer);
        });
    }

    @Test
    public void ensureQuestionFactoryCreatesShortAnswerWithValidInformation() {
        // Arrange
        String question = "Test Question";
        Float quotation = 1.0f;
        Float penalty = 0.5f;
        String answer = "1";
        ShortAnswerQuestion expectedQuestion = new ShortAnswerQuestion(
                question, answer, quotation, penalty);

        // Act
        when(generator.generateShortAnswerQuestion(
                question, quotation, penalty, answer))
                .thenReturn(QuestionDataSource.shortAnswerFile);
        when(validator.validate(QuestionDataSource.shortAnswerFile.getPath()))
                .thenReturn(expectedQuestion);
        ShortAnswerQuestion actualAnswer = questionFactory.createShortAnswerQuestion(
                question, quotation, penalty, answer);

        // Assert
        Assertions.assertEquals(expectedQuestion, actualAnswer);
    }

    @Test
    public void ensureQuestionFactoryDoesNotCreateShortAnswerWithInvalidInformation() {
        // Arrange
        String question = "";
        Float quotation = 1.0f;
        Float penalty = 0.5f;
        String answer = "1";

        // Act
        when(generator.generateShortAnswerQuestion(
                question, quotation, penalty, answer))
                .thenReturn(QuestionDataSource.shortAnswerFile);
        when(validator.validate(QuestionDataSource.shortAnswerFile.getPath()))
                .thenReturn(null);

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            questionFactory.createShortAnswerQuestion(
                    question, quotation, penalty, answer);
        });
    }

    @Test
    public void ensureQuestionFactoryCreatesMissingWordWithValidInformation() {
        // Arrange
        String question = "Test Question";
        Float quotation = 1.0f;
        Float penalty = 0.5f;
        List<String> options = List.of("Option1", "Option2", "Option3");
        List<MissingWordOption> answer = List.of(
                new MissingWordOption(options, "Option1"),
                new MissingWordOption(options, "Option2"),
                new MissingWordOption(options, "Option3"));
        MissingWordQuestion expectedQuestion = new MissingWordQuestion(
                question, answer, quotation, penalty);

        // Act
        when(generator.generateMissingWordQuestion(
                question, quotation, penalty, answer))
                .thenReturn(QuestionDataSource.missingWordFile);
        when(validator.validate(QuestionDataSource.missingWordFile.getPath()))
                .thenReturn(expectedQuestion);
        MissingWordQuestion actualAnswer = questionFactory.createMissingWordQuestion(
                question, quotation, penalty, answer);

        // Assert
        Assertions.assertEquals(expectedQuestion, actualAnswer);
    }

    @Test
    public void ensureQuestionFactoryDoesNotCreateMissingWordWithInvalidInformation() {
        // Arrange
        String question = "";
        Float quotation = 1.0f;
        Float penalty = 0.5f;
        List<String> options = List.of("Option1", "Option2", "Option3");
        List<MissingWordOption> answer = List.of(
                new MissingWordOption(options, "Option1"),
                new MissingWordOption(options, "Option2"),
                new MissingWordOption(options, "Option3"));

        // Act
        when(generator.generateMissingWordQuestion(
                question, quotation, penalty, answer))
                .thenReturn(QuestionDataSource.missingWordFile);
        when(validator.validate(QuestionDataSource.missingWordFile.getPath()))
                .thenReturn(null);

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            questionFactory.createMissingWordQuestion(
                    question, quotation, penalty, answer);
        });
    }
}
