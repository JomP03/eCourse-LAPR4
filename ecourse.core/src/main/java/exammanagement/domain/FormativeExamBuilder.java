package exammanagement.domain;

import coursemanagement.domain.Course;
import ecourseusermanagement.domain.ECourseUser;
import questionmanagment.domain.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Formative exam builder.
 */
public class FormativeExamBuilder {

    private Course examCourse;

    private ECourseUser examCreator;

    private String examTitle;

    private String examHeaderDescription;

    private FeedBackType examFeedbackType;

    private GradingType examGradingType;

    private List<ExamSection> examSections;


    /**
     * Instantiates a new Formative exam builder.
     * Initializes the list of sections for the exam
     */
    public FormativeExamBuilder() {
        this.examSections = new ArrayList<>();
    }

    /**
     * Add the creator to the formative exam builder
     *
     * @param examCreator the exam creator
     */
    public void withCreator(ECourseUser examCreator) {
        this.examCreator = examCreator;
    }

    /**
     * Add the course to the formative exam builder
     *
     * @param examCourse the exam course
     */
    public void withCourse(Course examCourse) {
        this.examCourse = examCourse;
    }

    /**
     * Add the title to the formative exam builder
     *
     * @param examTitle the exam title
     */
    public void withTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    /**
     * Add the header to the formative exam builder
     *
     * @param examHeaderDescription       the exam header
     * @param examFeedbackType the exam feedback type
     * @param examGradingType  the exam grading type
     */
    public void withHeader(String examHeaderDescription, FeedBackType examFeedbackType,
                                           GradingType examGradingType) {
        this.examHeaderDescription = examHeaderDescription;
        this.examFeedbackType = examFeedbackType;
        this.examGradingType = examGradingType;
    }

    /**
     * Add the section to the formative exam builder
     *
     * @param sectionDescription the section description
     * @param sectionQuestions   the section questions
     */
    public void withSection(String sectionDescription, List<Question> sectionQuestions) {
        this.examSections.add(new ExamSection(sectionDescription, sectionQuestions));
    }

    /**
     * Remove all the current sections from the formative exam builder
     */
    public void removeSections() {
        this.examSections = new ArrayList<>();
    }

    /**
     * Build formative exam.
     *
     * @return the formative exam
     */
    public FormativeExam build() {
        return new FormativeExam(examSections, new ExamHeader(examHeaderDescription, examGradingType, examFeedbackType),
                examTitle, examCreator, examCourse);
    }
}
