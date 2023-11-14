package exammanagement.domain;

import eapli.framework.validations.*;
import questionmanagment.domain.*;

import javax.persistence.*;
import java.util.List;

@Entity
public class ExamSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Question> questions;

    /**
     * @param description the description of the exam section
     * @param questions the questions that are part of this section
     */
    public ExamSection(String description, List<Question> questions) {

        if(description == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }

        this.description = description;

        if(questions == null || questions.isEmpty()) {
            throw new IllegalArgumentException("Questions cannot be null or empty");
        }

        for(Question question : questions) {
            validateQuestion(question);
        }

        this.questions = questions;
    }

    /**
     * Validates if the question is valid for this section
     *
     * @param question the question to validate
     */
    private void validateQuestion(Question question) {
        if(question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }

    }

    protected ExamSection() {
        // for ORM only
    }

    /**
     * The Section's questions
     * @return the questions
     */
    public List<Question> questions() {
        return questions;
    }

    /**
     * The Section's description
     * @return the description
     */
    public String description() {
        return description;
    }


    /**
     * Updates the description of the exam section
     * @param newDescription the new description
     */
    public void updateDescription(String newDescription) {
        Preconditions.nonNull(newDescription);
        Preconditions.nonEmpty(newDescription);

        this.description = newDescription;
    }


    /**
     * Updates the questions of the exam section
     * @param questions the new questions
     */
    public void updateQuestions(List<Question> questions) {
        Preconditions.nonNull(questions);
        Preconditions.nonEmpty(questions);

        this.questions = questions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Description: ").append(description).append("\n\n").append("__________________________________________________________________________\n\n");

        int number = 1;
        for (Question question : questions) {
            sb.append("\t").append(number).append("-").append(question.toString()).append("\n");
            number++;
        }

        return sb.toString();
    }


    public String stringToFile() {
        StringBuilder sb = new StringBuilder();

        sb.append("SECTIONHEADER:").append(description).append("\n");

        for (Question question : questions) {
            sb.append("\n").append(question.stringToFile()).append("\n");
        }

        return sb.toString();
    }

    /**
     * Returns the maximum grade of the section
     * @return the maximum grade of the section
     */
    public Float maxGrade() {
        Float maxGrade = 0f;

        for(Question question : questions) {
            maxGrade += question.quotation();
        }

        return maxGrade;
    }
}
