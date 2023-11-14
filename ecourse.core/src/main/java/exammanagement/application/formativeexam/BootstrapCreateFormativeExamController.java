package exammanagement.application.formativeexam;

import coursemanagement.domain.Course;
import ecourseusermanagement.domain.ECourseUser;
import exammanagement.domain.FeedBackType;
import exammanagement.domain.FormativeExam;
import exammanagement.domain.FormativeExamBuilder;
import exammanagement.domain.GradingType;
import exammanagement.repository.ExamRepository;
import questionmanagment.application.IQuestionProvider;
import questionmanagment.domain.Question;
import questionmanagment.domain.QuestionType;

import java.util.List;

public class BootstrapCreateFormativeExamController implements ICreateFormativeExamController{

    private final FormativeExamBuilder formativeExamBuilder;

    private final IQuestionProvider questionProvider;

    private final ExamRepository examRepository;

    private ECourseUser examCreator;

    public BootstrapCreateFormativeExamController(FormativeExamBuilder formativeExamBuilder,
                                                  IQuestionProvider questionProvider,
                                                  ExamRepository examRepository) {
        // Verify if FormativeExamBuilder is not null
        if (formativeExamBuilder == null) {
            throw new IllegalArgumentException("FormativeExamBuilder cannot be null.");
        }

        this.formativeExamBuilder = formativeExamBuilder;

        // Verify if IQuestionProvider is not null
        if (questionProvider == null) {
            throw new IllegalArgumentException("IQuestionProvider cannot be null.");
        }

        this.questionProvider = questionProvider;

        // Verify if ExamRepository is not null
        if (examRepository == null) {
            throw new IllegalArgumentException("ExamRepository cannot be null.");
        }

        this.examRepository = examRepository;
    }

    /**
     * Exam creator setter.
     */
    public void submitCreator(ECourseUser examCreator) {
        // Verify if ECourseUser is not null
        if (examCreator == null) {
            throw new IllegalArgumentException("ECourseUser cannot be null.");
        }

        this.examCreator = examCreator;
    }

    @Override
    public void submitCourse(Course course) {
        formativeExamBuilder.withCourse(course);
        formativeExamBuilder.withCreator(this.examCreator);
    }

    @Override
    public void submitTopSection(String examTitle, String examHeaderDescription, FeedBackType examFeedBackType, GradingType examGradingType) {
        formativeExamBuilder.withTitle(examTitle);
        formativeExamBuilder.withHeader(examHeaderDescription, examFeedBackType, examGradingType);
    }

    @Override
    public void submitSection(String sectionDescription, QuestionType sectionQuestionType, int sectionQuestionAmount) {
        // Random questions generated for the exam section
        List<Question> randomSectionQuestions =
                (List<Question>) questionProvider.listQuestionsByType(sectionQuestionType, sectionQuestionAmount);

        formativeExamBuilder.withSection(sectionDescription, randomSectionQuestions);
    }

    @Override
    public void removeSections() {
        formativeExamBuilder.removeSections();
    }

    @Override
    public FormativeExam submitExam() {
        return formativeExamBuilder.build();
    }

    public void saveExam(FormativeExam exam) {
        examRepository.save(exam);
    }
}
