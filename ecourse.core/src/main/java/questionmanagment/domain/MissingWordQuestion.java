package questionmanagment.domain;

import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;
import java.util.List;

@Entity
public class MissingWordQuestion extends Question {

    @OneToMany(cascade = CascadeType.ALL)
    private List<MissingWordOption> missingOptions;

    public MissingWordQuestion(String question, List<MissingWordOption> options,Float penalty, Float quotation) {
        super(question, penalty, quotation, QuestionType.MISSING_WORD);


        if(options == null || options.isEmpty()) {
            throw new IllegalArgumentException("Options cannot be null or empty");
        }


        this.missingOptions = options;
    }

    protected MissingWordQuestion() {
        // for ORM
    }


    /**
     * Updates the question
     * @param question the question
     * @param penalty the penalty
     * @param quotation the quotation
     * @param options the options
     * @throws IllegalArgumentException if the options is null or if the options is empty
     */
    public MissingWordQuestion updateQuestion(String question, Float penalty, Float quotation, List<MissingWordOption> options) {
        super.updateQuestion(question, penalty, quotation);

        if (options == null || options.isEmpty()) {
            throw new IllegalArgumentException("Options cannot be null or empty");
        }

        this.missingOptions = options;

        return this;
    }


    /**
     * Returns the missing options
     * @return the missingOptions
     */
    public List<MissingWordOption> missingOptions() {
        return missingOptions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Missing Word: ").append(super.question()).append("\n");

        int number = 1;
        for(MissingWordOption option : missingOptions) {
            sb.append("\n\tFor " + number + " :\n").append(option.toString());
            number++;
        }

        return sb.toString();
    }

    @Override
    public String stringToFile() {
        StringBuilder sb = new StringBuilder();

        sb.append("MISSING_WORD:").append(super.question()).append("\n");

        sb.append("GRADE:").append(super.quotation()).append("\n");


        for(MissingWordOption option : missingOptions) {
            sb.append(option.stringToFile()).append("\n");
        }

        sb.append("\n");

        return sb.toString();

    }

    @Override
    public Boolean isCorrect(String answer) {

        List<String> answers = List.of(answer.split(","));

        int correctAnswers = 0;

        for(MissingWordOption option : missingOptions) {
            if(option.isCorrect(answers.get(correctAnswers).trim())) {
                correctAnswers++;
            }
        }

        return correctAnswers == missingOptions.size();

    }

    @Override
    public String correctAnswer() {
        StringBuilder sb = new StringBuilder();

        int number = 1;

        for(MissingWordOption option : missingOptions) {
            sb.append("\nFor " + number + " -> ").append(option.answer());
            number++;
        }

        return sb.toString();
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

        List<String> answers = List.of(answer.split(","));

        int correctAnswers = 0;

        for(MissingWordOption option : missingOptions) {
            if(option.isCorrect(answers.get(correctAnswers).trim())) {
                correctAnswers++;
            }
        }

        if(correctAnswers == missingOptions.size()){
            return super.quotation();
        }

        return 0f;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        MissingWordQuestion that = (MissingWordQuestion) o;

        return super.equals(that)
                && missingOptions.equals(that.missingOptions);
    }

    /**
     * Method that returns the missing question in a format that can be written to a txt file
     * @return the missing question in a format that can be written to a txt file
     */
    public String questionToTxt() {
        StringBuilder sb = new StringBuilder();

        sb.append(super.question()).append(" { ");

        int index = 1;

        for(MissingWordOption option : missingOptions) {
            sb.append("For " + index + " -> ").append(option.optionsToTxt()).append("");
            index++;
        }

        sb.append(" }");

        return sb.toString();
    }
}
