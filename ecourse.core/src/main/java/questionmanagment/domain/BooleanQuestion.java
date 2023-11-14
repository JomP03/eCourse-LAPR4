package questionmanagment.domain;

import eapli.framework.domain.model.DomainEntities;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.*;

@Entity
public class BooleanQuestion extends Question {

    @Column
    private Boolean booleanAnswer;

    /**
     * Instantiates a new Boolean question
     * @param question the question
     * @param correctAnswer the correct answer
     * @param penalty the penalty
     * @param quotation the quotation
     */
    public BooleanQuestion(String question, Boolean correctAnswer, Float penalty, Float quotation) {
        super(question, penalty, quotation, QuestionType.BOOLEAN);

        if(correctAnswer == null){
            throw new IllegalArgumentException("Invalid boolean correct answer");
        }

        this.booleanAnswer = correctAnswer;
    }

    protected BooleanQuestion() {
        // for ORM
    }


    /**
     * Gets the correct answer
     */
    public BooleanQuestion updateQuestion(String question, Float quotation, Float penalty, Boolean correctAnswer) {
        if(correctAnswer == null){
            throw new IllegalArgumentException("Invalid boolean correct answer");
        }

        super.updateQuestion(question, quotation, penalty);
        this.booleanAnswer = correctAnswer;
        return this;
    }


    @Override
    public float correctStudentAnswer(String answer) {
        if (answer == null || answer.isEmpty() || answer.isBlank()) {
            throw new IllegalArgumentException("The answer cannot be null or empty");
        }

        return answer.toLowerCase().trim().equals(booleanAnswer.toString().toLowerCase().trim()) ? super.quotation() : 0;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Boolean: ").append(super.question()).append("\n");

        return sb.toString();
    }

    @Override
    public String stringToFile() {
        StringBuilder sb = new StringBuilder();

        sb.append("BOOLEAN:").append(super.question()).append("\n");
        sb.append("ANSW:").append(booleanAnswer.toString().toUpperCase()).append("\n");
        sb.append("GRADE:").append(super.quotation()).append("\n");

        return sb.toString();
    }

    @Override
    public Boolean isCorrect(String answer) {
        if(answer == null){
            throw new IllegalArgumentException("Invalid boolean answer");
        }

        if(answer.toLowerCase().trim().equalsIgnoreCase(booleanAnswer.toString().toLowerCase().trim())){
            return true;
        }

        return false;
    }

    @Override
    public String correctAnswer() {
        return booleanAnswer.toString();
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this, other);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        BooleanQuestion that = (BooleanQuestion) o;
        return super.equals(that) && Objects.equals(booleanAnswer, that.booleanAnswer);
    }
}
