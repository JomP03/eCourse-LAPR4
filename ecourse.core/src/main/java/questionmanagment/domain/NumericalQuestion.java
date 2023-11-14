package questionmanagment.domain;

import eapli.framework.domain.model.DomainEntities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class NumericalQuestion extends Question {

    @Column
    private String numericalAnswer;

    public NumericalQuestion(String question, String answer, Float penalty, Float quotation) {
        super(question, penalty, quotation, QuestionType.NUMERICAL);

        if(answer == null || answer.isEmpty() || answer.isBlank()){
            throw new IllegalArgumentException("Invalid answer");
        }

        this.numericalAnswer = answer;
    }


    /**
     * Updates the question
     * @param question the question
     * @param penalty the penalty
     * @param quotation the quotation
     * @param answer the answer
     * @throws IllegalArgumentException if the answer is null or if the answer is empty or if the answer is blank
     */
    public NumericalQuestion updateQuestion(String question, Float penalty, Float quotation, String answer) {
        super.updateQuestion(question, penalty, quotation);

        if(answer == null || answer.isEmpty() || answer.isBlank()){
            throw new IllegalArgumentException("Invalid answer");
        }

        this.numericalAnswer = answer;

        return this;
    }


    protected NumericalQuestion() {
        // for ORM
    }


    @Override
    public float correctStudentAnswer(String answer) {
        if (answer == null || answer.isEmpty() || answer.isBlank()) {
            throw new IllegalArgumentException("The answer cannot be null or empty");
        }

        Double answerDouble = Double.parseDouble(answer);
        Double numericalAnswerDouble = Double.parseDouble(this.numericalAnswer);

        return answerDouble.equals(numericalAnswerDouble) ? super.quotation() : 0;
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Numerical: ").append(super.question()).append("\n");

        return sb.toString();
    }

    @Override
    public String stringToFile() {
        StringBuilder sb = new StringBuilder();

        sb.append("NUMERICAL:").append(super.question()).append("\n");

        sb.append("ANSW:").append(this.numericalAnswer).append("\n");

        sb.append("GRADE:").append(super.quotation()).append("\n");

        return sb.toString();
    }

    @Override
    public Boolean isCorrect(String answer) {
        Double answerDouble = Double.parseDouble(answer);
        Double numericalAnswerDouble = Double.parseDouble(this.numericalAnswer);

        return answerDouble.equals(numericalAnswerDouble);
    }

    @Override
    public String correctAnswer() {
        return this.numericalAnswer;
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

        NumericalQuestion that = (NumericalQuestion) o;

        return super.equals(that)
                && numericalAnswer.equals(that.numericalAnswer);
    }
}
