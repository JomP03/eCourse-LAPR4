package exammanagement.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExamHeaderTest {

    @Test
    public void ensureValidExamHeaderIsAccepted() {

        // Arrange
        String description = "Exam Description";
        GradingType gradingFeedBackType = GradingType.NONE;
        FeedBackType feedbackType = FeedBackType.NONE;

        // Act
        ExamHeader examHeader = new ExamHeader(description, gradingFeedBackType, feedbackType);

        String expected = "Exam Description";

        // Assert
        Assertions.assertTrue(examHeader.toString().contains(expected));
    }

    @Test
    public void ensureNullDescriptionIsRejected() {
        // Arrange
        GradingType gradingFeedBackType = GradingType.NONE;
        FeedBackType feedbackType = FeedBackType.NONE;

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new ExamHeader(null, gradingFeedBackType, feedbackType));

    }

    @Test
    public void ensureNullGradingFeedBackTypeIsRejected() {
        // Arrange
        String description = "Exam Description";
        FeedBackType feedbackType = FeedBackType.NONE;

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new ExamHeader(description, null, feedbackType));

    }

    @Test
    public void ensureNullFeedbackTypeIsRejected() {
        // Arrange
        String description = "Exam Description";
        GradingType gradingFeedBackType = GradingType.NONE;

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new ExamHeader(description, gradingFeedBackType, null));
    }


    @Test
    void ensureExamHeaderReturnsGradingType() {
        // Arrange
        String description = "Exam Description";
        GradingType gradingFeedBackType = GradingType.NONE;
        FeedBackType feedbackType = FeedBackType.NONE;

        GradingType expected = GradingType.NONE;

        // Act
        ExamHeader examHeader = new ExamHeader(description, gradingFeedBackType, feedbackType);

        // Aseert
        Assertions.assertEquals(expected, examHeader.gradingFeedBackType());
    }


    @Test
    void ensureExamHeaderReturnsFeedbackType() {
        // Arrange
        String description = "Exam Description";
        GradingType gradingFeedBackType = GradingType.NONE;
        FeedBackType feedbackType = FeedBackType.NONE;

        FeedBackType expected = FeedBackType.NONE;

        // Act
        ExamHeader examHeader = new ExamHeader(description, gradingFeedBackType, feedbackType);

        // Aseert
        Assertions.assertEquals(expected, examHeader.feedbackType());
    }
}
