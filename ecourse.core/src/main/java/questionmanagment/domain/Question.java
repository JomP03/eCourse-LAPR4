package questionmanagment.domain;

import eapli.framework.domain.model.*;
import javax.persistence.*;
import java.util.*;

@Entity
public abstract class Question implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String question;

    @Column
    private Float penalty;

    @Column
    private Float quotation;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    public Question(String question, Float penalty, Float quotation, QuestionType questionType) {

        if(question == null || question.isEmpty() || question.isBlank()){
            throw new IllegalArgumentException("Invalid question");
        }

        if(penalty == null || penalty < 0){
            throw new IllegalArgumentException("Invalid penalty");
        }

        if(quotation == null || quotation < 0){
            throw new IllegalArgumentException("Invalid quotation");
        }

        if(questionType == null){
            throw new IllegalArgumentException("Invalid question type");
        }

        this.question = question;
        this.penalty = penalty;
        this.quotation = quotation;
        this.questionType = questionType;
    }

    protected Question() {
        // for ORM
    }

    /**
     * Gets the question
     * @return the question
     */
    public String question() {
        return question;
    }


    /**
     * Gets the question type
     * @return the question type
     */
    public QuestionType type() {
        return questionType;
    }

    /**
     * Gets the quotation
     * @return the quotation
     */
    public Float quotation() {
        return quotation;
    }

    public abstract String toString();


    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof Question)) {
            return false;
        }

        final Question that = (Question) other;
        if (this.equals(that)) {
            return true;
        }

        return identity().equals(that.identity());
    }

    @Override
    public Long identity() {
        return id;
    }


    /**
     * Update a question
     */
    protected void updateQuestion(String question, Float quotation, Float penalty) {
        if(question == null || question.isEmpty() || question.isBlank()){
            throw new IllegalArgumentException("Invalid question");
        }

        if(penalty == null || penalty < 0){
            throw new IllegalArgumentException("Invalid penalty");
        }

        if(quotation == null || quotation < 0){
            throw new IllegalArgumentException("Invalid quotation");
        }

        this.question = question;
        this.penalty = penalty;
        this.quotation = quotation;
    }

    /**
     * Gets a string to write to a file
     * @return the string to write to a file
     */


    /**
     * Corrects the answer
     * @param answer the answer to correct
     * @return the grade of the answer
     */
    public abstract float correctStudentAnswer(String answer);

    public abstract String stringToFile();

    /**
     * Checks if the answer is correct
     * @param answer the given answer
     * @return true if the answer is correct, false otherwise
     */
    public abstract Boolean isCorrect(String answer);

    /**
     * Gets the correct answer
     * @return the correct answer
     */
    public abstract String correctAnswer();


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Question that = (Question) o;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.question, that.question) &&
                Objects.equals(this.penalty, that.penalty) &&
                Objects.equals(this.quotation, that.quotation) &&
                Objects.equals(this.questionType, that.questionType);
    }
}
