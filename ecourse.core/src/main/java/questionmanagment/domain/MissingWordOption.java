package questionmanagment.domain;


import eapli.framework.domain.model.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class MissingWordOption implements ValueObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String answer;

    @ElementCollection
    private List<String> optionAnswers;

    /**
     *
     * @param optionAnswers the list of answers for the option
     * @throws IllegalArgumentException if any of the arguments is invalid
     */
    public MissingWordOption(List<String> optionAnswers, String answer) {

        if(optionAnswers == null || optionAnswers.isEmpty() || optionAnswers.size() < 2)
            throw new IllegalArgumentException("Invalid option answers");

        for (String optionAnswer : optionAnswers) {
            if(optionAnswer == null || optionAnswer.isEmpty() || optionAnswer.isBlank())
                throw new IllegalArgumentException("Invalid option answer");
        }

        if(answer == null || answer.isEmpty() || answer.isBlank())
            throw new IllegalArgumentException("Invalid answer");

        this.answer = answer;

        this.optionAnswers = optionAnswers;

        if(!optionAnswers.contains(answer))
            throw new IllegalArgumentException("Answer must be one of the option answers");

    }

    protected MissingWordOption() {
        // for ORM
    }


    /**
     * Returns the answer
     * @return the answer
     */
    public String answer() {
        return answer;
    }

    /**
     * Returns the option answers
     * @return the option answers
     */
    public List<String> optionAnswers() {
        return optionAnswers;
    }


    /**
     * Checks if the answer is correct
     * @param answer the answer to check
     * @return true if the answer is correct, false otherwise
     * @throws IllegalArgumentException if the answer is null, empty or blank
     */
    public boolean correctAnswer(String answer) {
        if (answer == null || answer.isEmpty() || answer.isBlank()) {
            throw new IllegalArgumentException("The answer cannot be null or empty");
        }

        return this.answer.trim().equalsIgnoreCase(answer.trim());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < optionAnswers.size(); i++) {
            sb.append("\t").append((char) ('a' + i)).append(") ").append(optionAnswers.get(i)).append("\n");
        }
        return sb.toString();
    }

    public String stringToFile() {
        StringBuilder sb = new StringBuilder();

        sb.append("CORANSW:").append(answer).append("\n");

        for (String optionAnswer : optionAnswers) {
            sb.append("ANSW:").append(optionAnswer).append("\n");
        }

        return sb.toString();
    }

    /**
     * Checks if the answer is correct
     * @param s the answer
     * @return true if the answer is correct, false otherwise
     */
    public Boolean isCorrect(String s) {
        return answer.trim().equalsIgnoreCase(s.trim());
    }

    /**
     * Method that returns the options in a format that can be written to a txt file
     * @return the options in a format that can be written to a txt file
     */
    public String optionsToTxt() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (String optionAnswer : optionAnswers) {
            if(first) {
                sb.append(optionAnswer);
                first = false;
            } else {
                sb.append(",").append(optionAnswer);
            }
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        MissingWordOption that = (MissingWordOption) o;

        return answer.equals(that.answer)
                && optionAnswers.equals(that.optionAnswers);
    }
}
