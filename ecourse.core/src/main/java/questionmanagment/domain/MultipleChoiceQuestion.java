package questionmanagment.domain;

import eapli.framework.domain.model.DomainEntities;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.List;

@Entity
public class MultipleChoiceQuestion extends Question {

    @ElementCollection
    private List<String> options;

    @Column
    private String correctOption;


    public MultipleChoiceQuestion(String question, List<String> options, String correctOption,Float penalty, Float quotation) {
        super(question, penalty, quotation, QuestionType.MULTIPLE_CHOICE);

        if(options == null || options.isEmpty()) {
            throw new IllegalArgumentException("A multiple choice question mustn't have null options or feedbacks");
        }


        if(options.size() < 2) {
            throw new IllegalArgumentException("A multiple choice question must have at least 2 options");
        }

        if(correctOption == null || correctOption.isEmpty()) {
            throw new IllegalArgumentException("A multiple choice question must have a correct option");
        }

        if(!options.contains(correctOption)) {
            throw new IllegalArgumentException("A multiple choice question must have a correct option");
        }

        this.options = options;
        this.correctOption = correctOption;
    }

    protected MultipleChoiceQuestion() {
        // for ORM
    }


    /**
     * Updates the question
     * @param question the question
     * @param penalty the penalty
     * @param quotation the quotation
     * @param options the options
     * @param correctOption the correct option
     * @throws IllegalArgumentException if the options is null or if the options is empty or
     * if the options contains null or empty values
     */
    public MultipleChoiceQuestion updateQuestion(String question, Float penalty, Float quotation, List<String> options, String correctOption) {
        super.updateQuestion(question, penalty, quotation);

        if (options == null || options.isEmpty()) {
            throw new IllegalArgumentException("Options cannot be null or empty");
        }

        if(options.size() < 2) {
            throw new IllegalArgumentException("A multiple choice question must have at least 2 options");
        }

        if(correctOption == null || correctOption.isEmpty()) {
            throw new IllegalArgumentException("A multiple choice question must have a correct option");
        }

        if(!options.contains(correctOption)) {
            throw new IllegalArgumentException("A multiple choice question must have a correct option");
        }

        this.options = options;
        this.correctOption = correctOption;

        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Multiple Choice: ").append(super.question()).append("\n");

        // Display the options with their corresponding numbers
        List<String> options = this.options;
        for (int i = 0; i < options.size(); i++) {
            sb.append("\t" + (char) ('a' + i)).append(") ").append(options.get(i)).append("\n");
        }

        return sb.toString();
    }


    /**
     * @return the options
     */
    public List<String> options() {
        return options;
    }

    @Override
    public String stringToFile() {
        StringBuilder sb = new StringBuilder();

        sb.append("MULTIPLE_CHOICE:").append(super.question()).append("\n");

        sb.append("CORANSW:").append(correctOption).append("\n");

        sb.append("GRADE:").append(super.quotation()).append("\n");

        for (String option : options) {
            sb.append("ANSW:").append(option).append("\n");
        }

        return sb.toString();
    }

    @Override
    public Boolean isCorrect(String answer) {
        return correctOption.equalsIgnoreCase(answer.trim());
    }

    @Override
    public String correctAnswer() {
        return correctOption;
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this, other);
    }

    @Override
    public float correctStudentAnswer(String answer) {
        if (answer == null || answer.isEmpty() || answer.isBlank()) {
            throw new IllegalArgumentException("The answer cannot be null or empty");
        }

        return answer.equalsIgnoreCase(correctOption) ? super.quotation() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        MultipleChoiceQuestion other = (MultipleChoiceQuestion) o;

        return super.equals(other)
                && options.equals(other.options)
                && correctOption.equals(other.correctOption);
    }

    /**
     * Returns the question in a txt format
     * @return the question in a txt format
     */
    public String questionToTxt() {
        return super.question() + options;
    }
}
