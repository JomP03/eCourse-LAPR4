package takenexammanagement.domain;

import javax.persistence.*;

@Entity
public class AnsweredQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String question;

    @Column
    private String answer;

    @Column
    private Integer section;

    @Column
    private Integer questionNumber;

    @Column
    private String feedback;

    protected AnsweredQuestion() {
        // For ORM
    }

    public AnsweredQuestion(String question, String answer, Integer section, Integer questionNumber) {
        if(question == null || question.isEmpty())
            throw new IllegalArgumentException("Question cannot be null or empty");

        if(answer == null)
            throw new IllegalArgumentException("Answer cannot be null");

        if(section == null || section < 0)
            throw new IllegalArgumentException("Section cannot be null or negative");

        if(questionNumber == null || questionNumber < 0)
            throw new IllegalArgumentException("Question number cannot be null or negative");

        this.question = question;
        this.answer = answer;
        this.section = section;
        this.questionNumber = questionNumber;
        this.feedback = "";
    }

    /**
     * Adds a feedback to the question
     * @param feedback the feedback to add
     */
    public void addFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * Return the question
     */
    public String question() {
        return question;
    }

    /**
     * Returns the answer
     */
    public String answer() {
        return answer;
    }

    /**
     * Returns the section index
     */
    public Integer sectionIndex() {
        return section-1;
    }

    /**
     * Returns the question number index in the section
     */
    public Integer questionIndex() {
        return questionNumber-1;
    }

    /**
     * Returns the feedback
     */
    public String feedback() {
        return feedback;
    }

    /**
     * Returns the identity of the AnsweredQuestion
     * @return the identity of the AnsweredQuestion
     */
    public Long identity() {
        return id;
    }

    @Override
    public String toString() {
        return  "Section: " + section + "\n" +
                question + '\n' +
                "Answer = " + answer + '\n' +
                "Feedback = " + feedback + '\n';
    }
}
