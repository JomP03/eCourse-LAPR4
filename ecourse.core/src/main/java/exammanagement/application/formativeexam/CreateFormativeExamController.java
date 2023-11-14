package exammanagement.application.formativeexam;

import coursemanagement.application.TeacherCoursesProvider;
import coursemanagement.domain.Course;
import ecourseusermanagement.application.UserSessionService;
import ecourseusermanagement.domain.ECourseUser;
import exammanagement.domain.*;
import exammanagement.repository.ExamRepository;
import questionmanagment.application.IQuestionProvider;
import questionmanagment.domain.Question;
import questionmanagment.domain.QuestionType;

import java.util.List;
import java.util.Optional;

/**
 * The constructor for the CreateFormativeExamController.
 */
public class CreateFormativeExamController implements ICreateFormativeExamController {

    private ECourseUser examCreator;

    private final UserSessionService userSessionService;

    private final TeacherCoursesProvider teacherCoursesProvider;

    private final IQuestionProvider questionProvider;

    private final FormativeExamBuilder formativeExamBuilder;

    private final ExamRepository examRepository;

    /**
     * Instantiates a new CreateFormativeExamController.
     *
     * @param userSessionService     the user session service
     * @param teacherCoursesProvider the teacher courses provider
     * @param questionProvider       the question provider
     * @param formativeExamBuilder   the formative exam builder
     */
    public CreateFormativeExamController(final UserSessionService userSessionService,
                                         final TeacherCoursesProvider teacherCoursesProvider,
                                         final IQuestionProvider questionProvider,
                                         final FormativeExamBuilder formativeExamBuilder,
                                         final ExamRepository examRepository) {
        // Verify if UserSessionService is not null
        if (userSessionService == null) {
            throw new IllegalArgumentException("UserSessionService cannot be null.");
        }

        this.userSessionService = userSessionService;

        // Verify if the logged user is valid
        verifyUserIsTeacher();

        // Verify if IUserCoursesProvider is not null
        if (teacherCoursesProvider == null) {
            throw new IllegalArgumentException("IUserCoursesProvider cannot be null.");
        }

        this.teacherCoursesProvider = teacherCoursesProvider;

        // Verify if IQuestionProvider is not null
        if (questionProvider == null) {
            throw new IllegalArgumentException("IQuestionProvider cannot be null.");
        }

        this.questionProvider = questionProvider;

        // Verify if FormativeExamBuilder is not null
        if (formativeExamBuilder == null) {
            throw new IllegalArgumentException("FormativeExamBuilder cannot be null.");
        }

        this.formativeExamBuilder = formativeExamBuilder;

        // Verify if ExamRepository is not null
        if (examRepository == null) {
            throw new IllegalArgumentException("ExamRepository cannot be null.");
        }

        this.examRepository = examRepository;
    }

    @Override
    public void submitCourse(Course course) {
        formativeExamBuilder.withCourse(course);
        formativeExamBuilder.withCreator(examCreator);
    }

    @Override
    public void submitTopSection(String examTitle, String examHeaderDescription, FeedBackType examFeedBackType,
                                 GradingType examGradingType) {
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

    private void verifyUserIsTeacher() {
        Optional<ECourseUser> eCourseUserOptional = userSessionService.getLoggedUser();

        eCourseUserOptional.ifPresentOrElse(
                eCourseUser -> this.examCreator = eCourseUser,
                () -> {
                    throw new IllegalStateException("No eCourse user found. Make sure you are registered.");
                }
        );

        if (!examCreator.isTeacher()) {
            throw new IllegalStateException("Only teachers can create exams.");
        }
    }

    /**
     * This method returns the list of courses that the teacher is lecturing, that are not in closed state.
     *
     * @return the iterable containing the courses
     */
    public Iterable<Course> listTeacherActiveCourses() {
        return teacherCoursesProvider.provideUserActiveCourses(examCreator);
    }

    /**
     * This method returns true if the question provider has enough questions of the given type.
     *
     * @return the boolean
     */
    public boolean isThereEnoughQuestionsForType(QuestionType questionType, int questionAmount) {
        return !questionProvider.isThereEnoughQuestionsForType(questionType, questionAmount);
    }

    public void saveExam(FormativeExam exam) {
        examRepository.save(exam);
    }
}
