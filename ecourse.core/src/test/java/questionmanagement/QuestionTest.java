package questionmanagement;

import coursemanagement.domain.*;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import questionmanagment.domain.*;

public class QuestionTest {

    @Test
    public void ensureQuestionsAreDifferenciable1() {
        // Arrange
        Question question1 = QuestionDataSource.booleanQuestion2();
        Question question2 = QuestionDataSource.matchingQuestion2();

        // Assert
        Assertions.assertFalse(question1.sameAs(question2));
    }


    @Test
    public void ensureQuestionsAreDifferenciable2() {
        // Arrange
        Question question = QuestionDataSource.booleanQuestion2();
        Course course = CourseDataSource.getTestCourse1();

        // Assert
        Assertions.assertFalse(question.sameAs(course));
    }


    @Test
    public void ensureEqualQuestionsAreEqual() {
        // Arrange
        Question question1 = QuestionDataSource.booleanQuestion1();
        Question question2 = QuestionDataSource.booleanQuestion1();

        // Assert
        Assertions.assertTrue(question1.sameAs(question2));
    }


    @Test
    public void ensureIndentity() {
        // Arrange
        Question question1 = QuestionDataSource.numericalQuestion2();
        Question question2 = QuestionDataSource.numericalQuestion2();

        // Assert
        Assertions.assertEquals(question1.identity(), question2.identity());
    }

    @Test
    public void ensureQuestionWithNoQuestionTypeThrowsException() {
        // AAA
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new Question("NoType Question", 0.0f, 0.0f, null) {
                    @Override
                    public boolean sameAs(Object other) {
                        return false;
                    }

                    @Override
                    public float correctStudentAnswer(String answer) {
                        return quotation();
                    }

                    @Override
                    public String stringToFile() {
                        return null;
                    }

                    @Override
                    public Boolean isCorrect(String answer) {
                        return null;
                    }

                    @Override
                    public String correctAnswer() {
                        return null;
                    }

                    @Override
                    public String toString() {
                        return null;
                    }
                });

    }

    @Test
    void ensureQuotationIsReturned() {
        // Arrange
        Question question = QuestionDataSource.booleanQuestion1();
        float expected = 10f;

        // Act
        Float quotation = question.quotation();

        // Assert
        Assertions.assertEquals(expected, quotation, 0.0001);
    }

    @Test
    void ensureTypeIsReturned() {
        // Arrange
        Question question = QuestionDataSource.booleanQuestion1();
        QuestionType expected = QuestionType.BOOLEAN;

        // Act
        QuestionType type = question.type();

        // Assert
        Assertions.assertEquals(expected, type);
    }
}
