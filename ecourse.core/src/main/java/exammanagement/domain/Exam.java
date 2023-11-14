package exammanagement.domain;

import coursemanagement.domain.Course;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.*;
import ecourseusermanagement.domain.ECourseUser;
import questionmanagment.domain.*;

import javax.persistence.*;
import java.util.List;

@Entity
public abstract class Exam implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private ECourseUser creator;

    @OneToOne
    private Course course;

    @OneToMany (cascade = CascadeType.ALL)
    private List<ExamSection> sections;

    @Embedded
    private ExamHeader header;

    @Column (unique = true)
    private String title;


    public Exam(List<ExamSection> sections, ExamHeader header, String title, ECourseUser creator, Course course) {

        if(sections == null || header == null ||  title == null){
            throw new IllegalArgumentException("All fields must be filled when creating an exam.");
        }

        if(title.isEmpty() || title.isBlank()){
            throw new IllegalArgumentException("Title must not be empty.");
        }

        if(sections.isEmpty()){
            throw new IllegalArgumentException("An exam must have at least one section.");
        }

        if (creator == null) {
            throw new IllegalArgumentException("Creator must not be null.");
        }

        if (course == null) {
            throw new IllegalArgumentException("Course must not be null.");
        }

        this.sections = sections;
        this.header = header;
        this.title = title;
        this.creator = creator;
        this.course = course;
    }

    protected Exam() {
        // for ORM only
    }

    @Override
    public boolean sameAs(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Exam)) {
            return false;
        }
        Exam otherExam = (Exam) other;
        return this.id != null && this.id.equals(otherExam.id);
    }

    /**
     * The creator of the exam.
     *
     * @return the eCourse user
     */
    public ECourseUser creator() {
        return this.creator;
    }

    /**
     * The course of which the exam belongs to.
     *
     * @return the course
     */
    public Course course() {
        return this.course;
    }

    /**
     * Header exam header.
     *
     * @return the exam header
     */
    public String title() {
        return this.title;
    }

    /**
     * Header exam header.
     *
     * @return the exam header
     */
    public ExamHeader header() {
        return this.header;
    }

    /**
     * The Exam's section
     *
     * @return the exam's section
     */
    public List<ExamSection> sections() {
        return this.sections;
    }

    /**
     * Updates the title of the exam.
     *
     * @param newTitle the new title
     */
    public void updateTitle(String newTitle) {
        Preconditions.nonNull(newTitle, "Title must not be null.");
        Preconditions.nonEmpty(newTitle, "Title must not be empty.");

        this.title = newTitle;
    }

    /**
     * Updates the header of the exam.
     *
     * @param newHeader the new header
     */
    public void updateHeader(ExamHeader newHeader) {
        Preconditions.nonNull(newHeader, "Header must not be null.");

        this.header = newHeader;
    }

    public void updateSectionDescription(ExamSection section, String newDescription) {
        Preconditions.nonNull(section, "Section must not be null.");
        Preconditions.nonNull(newDescription, "Description must not be null.");
        Preconditions.nonEmpty(newDescription, "Description must not be empty.");

        section.updateDescription(newDescription);
    }


    /**
     * Updates the section's questions.
     *
     * @param section   the section
     * @param questions the questions
     */
    protected void updateSectionQuestions(ExamSection section, List<Question> questions) {
        Preconditions.nonNull(section, "Section must not be null.");
        Preconditions.nonNull(questions, "Questions must not be null.");
        Preconditions.nonEmpty(questions, "Questions must not be empty.");

        section.updateQuestions(questions);
    }


    public Question obtainQuestion(int sectionIndex, int questionIndex) {
        return sections.get(sectionIndex).questions().get(questionIndex);
    }


    /**
     * @return the exam's string to file
     */
    public abstract String stringToFile();


    @Override
    public Long identity() {
        return id;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        String line = "__________________________________________________________________________\n";
        sb.append(line);

        Integer titleLength = title.length();
        Integer lineLength = line.length();

        int spaces = (lineLength - titleLength) / 2;

        sb.append(" ".repeat(Math.max(0, spaces)));

        sb.append(title).append("\n\n");


        // Exam Header
        sb.append(header.toString()).append("\n");

        sb.append(line);

        // Exam Sections
        int sectionNumber = 1;
        for (ExamSection section : sections) {
            sb.append("\n").append("Section ").append(sectionNumber).append(" : ");
            sb.append(section.toString()).append("\n");
            sb.append(line);
            sectionNumber++;
        }

        return sb.toString();
    }

    public void updateAll(AutomatedExam automatedExam) {
        this.title = automatedExam.title();
        this.header = automatedExam.header();
        this.sections = automatedExam.sections();
    }

    public Integer numberOfQuestions() {
        int numberOfQuestions = 0;
        for (ExamSection section : sections) {
            numberOfQuestions += section.questions().size();
        }
        return numberOfQuestions;
    }

    /**
     * Calculates the maximum grade of the exam.
     * @return the maximum grade
     */
    public Float maxGrade() {
        Float maxGrade = 0f;
        for (ExamSection section : sections) {
            maxGrade += section.maxGrade();
        }
        return maxGrade;
    }
}
