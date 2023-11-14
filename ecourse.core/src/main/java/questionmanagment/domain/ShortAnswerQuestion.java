package questionmanagment.domain;

import eapli.framework.domain.model.DomainEntities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class ShortAnswerQuestion extends Question {

    @Column
    @Lob
    private String shortAnswer;


    public ShortAnswerQuestion(String question, String correctAnswer, Float penalty, Float quotation) {
        super(question, penalty, quotation, QuestionType.SHORT_ANSWER);

        if(correctAnswer == null || correctAnswer.isEmpty() || correctAnswer.isBlank()){
            throw new IllegalArgumentException("Invalid correct answer");
        }

        this.shortAnswer = correctAnswer;
    }

    protected ShortAnswerQuestion() {
        // for ORM
    }


    /**
     * Updates the question
     * @param question the question
     * @param penalty the penalty
     * @param quotation the quotation
     * @param correctAnswer the correct answer
     * @throws IllegalArgumentException if the correct answer is null or
     * if the correct answer is empty or if the correct answer is blank
     */
    public ShortAnswerQuestion updateQuestion(String question, Float penalty, Float quotation, String correctAnswer) {
        super.updateQuestion(question, penalty, quotation);

        if(correctAnswer == null || correctAnswer.isEmpty() || correctAnswer.isBlank()){
            throw new IllegalArgumentException("Invalid correct answer");
        }

        this.shortAnswer = correctAnswer;

        return this;
    }


    @Override
    public float correctStudentAnswer(String answer) {
        if (answer == null || answer.isEmpty() || answer.isBlank()) {
            throw new IllegalArgumentException("The answer cannot be null or empty");
        }

        return answer.trim().equalsIgnoreCase(shortAnswer.trim()) ? super.quotation() : 0;
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Short Answer: ").append(super.question()).append("\n");

        return sb.toString();
    }

    @Override
    public String stringToFile() {
        StringBuilder sb = new StringBuilder();

        sb.append("SHORT_ANSWER:").append(super.question()).append("\n");

        sb.append("ANSW:").append(this.shortAnswer).append("\n");

        sb.append("GRADE:").append(super.quotation()).append("\n");

        return sb.toString();
    }

    @Override
    public Boolean isCorrect(String answer) {
        return this.shortAnswer.equalsIgnoreCase(answer.trim());
    }

    @Override
    public String correctAnswer() {
        return this.shortAnswer;
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

        ShortAnswerQuestion question = (ShortAnswerQuestion) o;

        return super.equals(question)
                && shortAnswer.equals(question.shortAnswer);
    }
}
