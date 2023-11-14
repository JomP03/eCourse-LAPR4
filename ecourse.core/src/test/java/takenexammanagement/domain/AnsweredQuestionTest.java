package takenexammanagement.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class AnsweredQuestionTest {

    @Test
    public void ensureValidAnsweredQuestionIsCreated(){
        // arrange
        final String validQuestion = "Question";
        final String validStudentAnswer = "Student Answer";
        final Integer validSectionIndex = 1;
        final Integer validQuestionIndex = 1;

        // act
        final AnsweredQuestion answeredQuestion = new AnsweredQuestion(validQuestion, validStudentAnswer, validSectionIndex, validQuestionIndex);

        // assert
        Assertions.assertEquals(validQuestion, answeredQuestion.question());
        Assertions.assertEquals(validStudentAnswer, answeredQuestion.answer());
        Assertions.assertEquals(validSectionIndex-1, answeredQuestion.sectionIndex());
        Assertions.assertEquals(validQuestionIndex-1, answeredQuestion.questionIndex());
    }

    @Test
    public void ensureInvalidQuestionIsRejected(){
        // arrange
        final String invalidQuestion = null;
        final String validStudentAnswer = "Student Answer";
        final Integer validSection = 1;
        final Integer validQuestionNumber = 1;

        // act
        Assertions.assertThrows(IllegalArgumentException.class, () -> new AnsweredQuestion(invalidQuestion, validStudentAnswer, validSection, validQuestionNumber));
    }

    @Test
    public void ensureInvalidStudentAnswerIsRejected(){
        // arrange
        final String validQuestion = "Question";
        final String invalidStudentAnswer = null;
        final Integer validSection = 1;
        final Integer validQuestionNumber = 1;

        // act
        Assertions.assertThrows(IllegalArgumentException.class, () -> new AnsweredQuestion(validQuestion, invalidStudentAnswer, validSection, validQuestionNumber));
    }

    @Test
    public void ensureInvalidSectionIsRejected(){
        // arrange
        final String validQuestion = "Question";
        final String validStudentAnswer = "Student Answer";
        final Integer invalidSection = -1;
        final Integer validQuestionNumber = 1;

        // act
        Assertions.assertThrows(IllegalArgumentException.class, () -> new AnsweredQuestion(validQuestion, validStudentAnswer, invalidSection, validQuestionNumber));
    }

    @Test
    public void ensureInvalidQuestionNumberIsRejected(){
        // arrange
        final String validQuestion = "Question";
        final String validStudentAnswer = "Student Answer";
        final Integer validSection = 1;
        final Integer invalidQuestionNumber = -1;

        // act
        Assertions.assertThrows(IllegalArgumentException.class, () -> new AnsweredQuestion(validQuestion, validStudentAnswer, validSection, invalidQuestionNumber));
    }

    @Test
    public void ensureFeedbackIsAdded(){
        // arrange
        final String validQuestion = "Question";
        final String validStudentAnswer = "Student Answer";
        final Integer validSection = 1;
        final Integer validQuestionNumber = 1;
        final String validFeedback = "Feedback";

        // act
        final AnsweredQuestion answeredQuestion = new AnsweredQuestion(validQuestion, validStudentAnswer, validSection, validQuestionNumber);
        answeredQuestion.addFeedback(validFeedback);

        // assert
        Assertions.assertEquals(validFeedback, answeredQuestion.feedback());
    }

    @Test
    public void ensureIdentityIsReturned(){
        // arrange
        final String validQuestion = "Question";
        final String validStudentAnswer = "Student Answer";
        final Integer validSection = 1;
        final Integer validQuestionNumber = 1;

        // act
        final AnsweredQuestion answeredQuestion = new AnsweredQuestion(validQuestion, validStudentAnswer, validSection, validQuestionNumber);

        // assert
        Assertions.assertNull(answeredQuestion.identity());
    }

}
