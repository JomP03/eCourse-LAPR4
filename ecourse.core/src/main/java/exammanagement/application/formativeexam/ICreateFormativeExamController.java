package exammanagement.application.formativeexam;

import coursemanagement.domain.Course;
import exammanagement.domain.FeedBackType;
import exammanagement.domain.FormativeExam;
import exammanagement.domain.GradingType;
import questionmanagment.domain.QuestionType;

/**
 * The interface for the CreateFormativeExamController.
 * Allowing different implementations.
 */
public interface ICreateFormativeExamController {

    /**
     * Receives the course and will assign the course to the requiring class.
     *
     * @param course the course which the exam will be created for.
     */
    void submitCourse(Course course);

    /**
     * Receives the title and header of the exam and will assign the title and header to the requiring class.
     *
     * @param examTitle the title of the exam.
     * @param examHeaderDescription the header of the exam.
     * @param examFeedBackType the feedback type of the exam.
     * @param examGradingType the grading type of the exam.
     */
    void submitTopSection(String examTitle, String examHeaderDescription, FeedBackType examFeedBackType,
                          GradingType examGradingType);

    /**
     * Receives the section description, question type and question amount and will assign the values
     * to the requiring class.
     *
     * @param sectionDescription    the section description
     * @param sectionQuestionType   the section question type
     * @param sectionQuestionAmount the section question amount
     */
    void submitSection(String sectionDescription, QuestionType sectionQuestionType, int sectionQuestionAmount);

    /**
     * Removes all the current sections from the formative exam builder.
     */
    void removeSections();

    /**
     * If this method is called, the exam will be created and saved.
     * It does not possess any parameters because it serves as a trigger.
     *
     * @return the created exam
     */
    FormativeExam submitExam();

}
