package questionmanagment.domain;

import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;
import java.util.*;

@Entity
public class MatchingQuestion extends Question {

    @ElementCollection
    private List<String> leftOptions;

    @ElementCollection
    private List<String> rightOptions;

    /**
     * Instantiates a new Matching question
     *
     * @param question     the question
     * @param penalty      the penalty
     * @param quotation    the quotation
     * @param leftOptions  the left options
     * @param rightOptions the right options
     * @throws IllegalArgumentException if the matchingQuestionMap is null or if the matchingQuestionMap is empty or if the matchingQuestionMap contains null or empty values
     */
    public MatchingQuestion(String question, Float penalty, Float quotation, List<String> leftOptions, List<String> rightOptions) {
        super(question, penalty, quotation, QuestionType.MATCHING);

        if (leftOptions == null || rightOptions == null) {
            throw new IllegalArgumentException("The matchingQuestion options cannot be null");
        }

        if (leftOptions.isEmpty() || rightOptions.isEmpty()) {
            throw new IllegalArgumentException("The matchingQuestion options cannot be empty");
        }

        for (String leftOption : leftOptions) {
            if (leftOption == null || leftOption.isEmpty() || leftOption.isBlank()) {
                throw new IllegalArgumentException("The matchingQuestion options cannot contain null or empty values");
            }
        }

        for (String rightOption : rightOptions) {
            if (rightOption == null || rightOption.isEmpty() || rightOption.isBlank()) {
                throw new IllegalArgumentException("The matchingQuestion options cannot contain null or empty values");
            }
        }

        if (leftOptions.size() != rightOptions.size()) {
            throw new IllegalArgumentException("The matchingQuestion options must have the same size");
        }

        this.leftOptions = leftOptions;
        this.rightOptions = rightOptions;

    }

    protected MatchingQuestion() {
        // for ORM
    }


    /**
     * Updates the question
     *
     * @param question     the question
     * @param penalty      the penalty
     * @param quotation    the quotation
     * @param leftOptions  the left options
     * @param rightOptions the right options
     * @throws IllegalArgumentException if the matchingQuestionMap is null or if the matchingQuestionMap is
     *                                  empty or if the matchingQuestionMap contains null or empty values
     */
    public MatchingQuestion updateQuestion(String question, Float penalty, Float quotation, List<String> leftOptions, List<String> rightOptions) {
        super.updateQuestion(question, penalty, quotation);

        if (leftOptions == null || rightOptions == null) {
            throw new IllegalArgumentException("The matchingQuestion options cannot be null");
        }

        if (leftOptions.isEmpty() || rightOptions.isEmpty()) {
            throw new IllegalArgumentException("The matchingQuestion options cannot be empty");
        }

        for (String leftOption : leftOptions) {
            if (leftOption == null || leftOption.isEmpty() || leftOption.isBlank()) {
                throw new IllegalArgumentException("The matchingQuestion options cannot contain null or empty values");
            }
        }

        for (String rightOption : rightOptions) {
            if (rightOption == null || rightOption.isEmpty() || rightOption.isBlank()) {
                throw new IllegalArgumentException("The matchingQuestion options cannot contain null or empty values");
            }
        }

        if (leftOptions.size() != rightOptions.size()) {
            throw new IllegalArgumentException("The matchingQuestion options must have the same size");
        }

        this.leftOptions = leftOptions;
        this.rightOptions = rightOptions;

        return this;
    }


    /**
     * Returns the left options
     * @return the left options
     */
    public List<String> leftOptions() {
        return Collections.unmodifiableList(leftOptions);
    }

    /**
     * Returns the right options
     * @return the right options
     */
    public List<String> rightOptions() {
        return Collections.unmodifiableList(rightOptions);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Matching Question: ").append(super.question()).append("\n");

        // Create a copy of the left and right options lists
        List<String> randomizedLeftOptions = new ArrayList<>(leftOptions);
        List<String> randomizedRightOptions = new ArrayList<>(rightOptions);

        // Randomize the order of the left and right options
        Collections.shuffle(randomizedLeftOptions);
        Collections.shuffle(randomizedRightOptions);

        for (int i = 0; i < randomizedLeftOptions.size(); i++) {
            String leftOption = randomizedLeftOptions.get(i);
            String rightOption = randomizedRightOptions.get(i);
            sb.append("\n\t LEFT =").append(leftOption).append("     RIGHT = ").append(rightOption).append("\n");
        }

        return sb.toString();
    }

    @Override
    public String stringToFile() {
        StringBuilder sb = new StringBuilder();

        sb.append("MATCHING:").append(super.question()).append("\n");

        sb.append("MATCHES:");

        for (int i = 0; i < leftOptions.size(); i++) {
            sb.append(leftOptions.get(i)).append("-").append(rightOptions.get(i));
            if (i != leftOptions.size() - 1) {
                sb.append(",");
            }
        }

        sb.append("\n").append("GRADE:").append(super.quotation()).append("\n");


        return sb.toString();
    }

    @Override
    public Boolean isCorrect(String answer) {
        if (answer == null) {
            return false;
        }

        List<String> givenAnswers = Arrays.asList(answer.split(","));

        List<String> givenLeftOptions = new ArrayList<>();
        List<String> givenRightOptions = new ArrayList<>();

        for (String givenAnswer : givenAnswers) {
            String[] split = givenAnswer.split("-");
            givenLeftOptions.add(split[0].trim());
            givenRightOptions.add(split[1].trim());
        }

        if (givenLeftOptions.size() != leftOptions.size() || givenRightOptions.size() != rightOptions.size()) {
            return false;
        }

        int correctAnswers = 0;


        for (int i = 0; i < givenLeftOptions.size(); i++) {

            if (!leftOptions.contains(givenLeftOptions.get(i)) || !rightOptions.contains(givenRightOptions.get(i))) {
                return false;
            }

            // since the answers can come in random order, we need to see where the left option is in the left options list

            if (rightOptions.get(leftOptions.indexOf(givenLeftOptions.get(i))).equalsIgnoreCase(givenRightOptions.get(i))) {
                correctAnswers++;
            }

        }

        return correctAnswers == leftOptions.size();
    }

    @Override
    public String correctAnswer() {
        StringBuilder sb = new StringBuilder();

        sb.append("{ ");

        for (int i = 0; i < leftOptions.size(); i++) {
            sb.append(leftOptions.get(i)).append("-").append(rightOptions.get(i));
            if (i != leftOptions.size() - 1) {
                sb.append(",");
            }
        }

        sb.append(" }");

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

        String[] answers = answer.split(",");

        if (answers.length != leftOptions.size()) {
            throw new IllegalArgumentException("The answer must have the same size as the matchingQuestion options");
        }

        float grade = 0;

        for (String match : answers) {
            String[] options = match.split("-");
            if (options.length != 2) {
                throw new IllegalArgumentException("The answer must be in the format leftOption-rightOption");
            }

            if (!leftOptions.contains(options[0].trim()) || !rightOptions.contains(options[1].trim())) {
                return 0;
            }

            if (rightOptions.indexOf(options[1].trim()) == leftOptions.indexOf(options[0].trim())) {
                grade += super.quotation();
            }
        }
        return grade;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        MatchingQuestion that = (MatchingQuestion) o;

        return super.equals(that)
                && Objects.equals(this.leftOptions, that.leftOptions)
                && Objects.equals(this.rightOptions, that.rightOptions);
    }

    /**
     * Method that returns the matching question in a format that can be written to a txt file
     * @return the matching question in a format that can be written to a txt file
     */
    public String questionToTxt() {
        StringBuilder sb = new StringBuilder();

        sb.append(super.question()).append(" { ");

        // Create a copy of the left and right options lists
        List<String> randomizedLeftOptions = new ArrayList<>(leftOptions);
        List<String> randomizedRightOptions = new ArrayList<>(rightOptions);

        // Randomize the order of the left and right options
        Collections.shuffle(randomizedLeftOptions);
        Collections.shuffle(randomizedRightOptions);

        sb.append(String.format("%-35s", "LEFT = " + randomizedLeftOptions)).append("RIGHT = ").append(randomizedRightOptions);

        sb.append(" }");


        return sb.toString();
    }
}
