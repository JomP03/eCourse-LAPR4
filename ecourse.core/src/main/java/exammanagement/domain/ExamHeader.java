package exammanagement.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class ExamHeader {

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    private GradingType gradingFeedBackType;

    @Enumerated(EnumType.STRING)
    private FeedBackType feedbackType;

    protected ExamHeader() {
        // for ORM only
    }

    public ExamHeader(String description, GradingType gradingFeedBackType, FeedBackType feedbackType) {

        if(description == null)
            throw new IllegalArgumentException("description cannot be null");

        if(gradingFeedBackType == null)
            throw new IllegalArgumentException("gradingFeedBackType cannot be null");

        if(feedbackType == null)
            throw new IllegalArgumentException("feedbackType cannot be null");


        this.description = description;
        this.gradingFeedBackType = gradingFeedBackType;
        this.feedbackType = feedbackType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Description: ").append(description).append("\n");
        sb.append("Grading Feedback Type: ").append(gradingFeedBackType).append("\n");
        sb.append("Feedback Type: ").append(feedbackType);

        return sb.toString();
    }

    /**
     * @return a string representation of the exam header for a exam file
     */
    public String stringToFile() {

        StringBuilder sb = new StringBuilder();

        sb.append("HEADER:").append(description).append("\n");
        sb.append("FEEDBACK:").append(feedbackType).append("\n");
        sb.append("GRADINGTYPE:").append(gradingFeedBackType);

        return sb.toString();
    }

    /**
     * @return the feedbacktype
     */
    public FeedBackType feedbackType() {
        return feedbackType;
    }

    /**
     * @return the gradingFeedBackType
     */
    public GradingType gradingFeedBackType() {
        return gradingFeedBackType;
    }
}
