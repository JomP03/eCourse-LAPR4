package takenexammanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import ecourseusermanagement.domain.ECourseUser;
import exammanagement.domain.Exam;

import javax.persistence.*;
import java.util.List;

@Entity
public class TakenExam implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<AnsweredQuestion> answeredQuestions;

    @Column
    private Float grade;

    @OneToOne (cascade = CascadeType.ALL)
    private ECourseUser student;

    @OneToOne (cascade = CascadeType.ALL)
    private Exam exam;

    protected TakenExam() {
        // For ORM
    }

    /**
     * Creates a new TakenExam object
     * @param answeredQuestions the list of answered questions
     * @param student the student who took the exam
     * @param exam the exam that was taken
     */
    public TakenExam(List<AnsweredQuestion> answeredQuestions, ECourseUser student, Exam exam) {

        if (answeredQuestions == null || answeredQuestions.isEmpty())
            throw new IllegalArgumentException("Answered questions cannot be null or empty");

        if (student == null)
            throw new IllegalArgumentException("Student cannot be null");

        if (exam == null)
            throw new IllegalArgumentException("Exam cannot be null");

        if(answeredQuestions.size() != exam.numberOfQuestions())
            throw new IllegalArgumentException("Answered questions must be the same size as the exam questions");

        this.answeredQuestions = answeredQuestions;

        ordersAnsweredQuestions();

        this.student = student;
        this.exam = exam;
        this.grade = 0f;
    }

    /**
     * Orders the answered questions by section and question number
     */
    private void ordersAnsweredQuestions() {
        answeredQuestions.sort((o1, o2) -> {
            if(o1.sectionIndex() == o2.sectionIndex()) {
                return o1.questionIndex().compareTo(o2.questionIndex());
            }
            return o1.sectionIndex().compareTo(o2.sectionIndex());
        });
    }

    /**
     * adds a quotation to the grade
     */
    public void addQuotation(Float quotation) {
        this.grade += quotation;
    }


    /**
     * Adjusts the grade to a 20 point scale
     */
    public void ajustGrade() {
        Float maxGrade = exam.maxGrade();
        grade *= 20 / maxGrade;
    }

    /**
     * Returns the grade of the TakenExam
     * @return the grade of the TakenExam
     */
    public Float grade() {
        return grade;
    }

    /**
     * Returns the student of the TakenExam
     * @return the student of the TakenExam
     */
    public ECourseUser student() {
        return student;
    }

    /**
     * Returns the exam of the TakenExam
     * @return the exam of the TakenExam
     */
    public Exam exam() {
        return exam;
    }


    /**
     * Returns the user's answer
     * @return a list of the user's answers
     */
    public List<AnsweredQuestion> answeredQuestions() {
        return answeredQuestions;
    }

    /**
     * Returns the identity of the TakenExam
     * @return the identity of the TakenExam
     */
    public Long identity() {
        return id;
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof TakenExam)) {
            return false;
        }

        final TakenExam that = (TakenExam) other;
        if (this == that) {
            return true;
        }

        if(this.id != null && that.id != null)
            return this.id.equals(that.id);

        return false;
    }

    @Override
    public String toString() {
        return "Exam" + exam.title() + " - Taken by " + student.email() + '\n' +
                "Grade: " + grade + '\n' +
                "Answered Questions: " + answeredQuestions;
    }

}
