import eapli.framework.actions.Action;
import exammanagement.application.formativeexam.BootstrapCreateFormativeExamController;
import exammanagement.domain.FeedBackType;
import exammanagement.domain.FormativeExamBuilder;
import exammanagement.domain.GradingType;
import persistence.PersistenceContext;
import questionmanagment.application.QuestionProvider;
import questionmanagment.domain.QuestionType;

public class FormativeExamBootstrapper implements Action {

    @Override
    public boolean execute() {
        BootstrapCreateFormativeExamController controller_1 = new BootstrapCreateFormativeExamController(
                new FormativeExamBuilder(),
                new QuestionProvider(PersistenceContext.repositories().questions()),
                PersistenceContext.repositories().exams());

        // Formative Exam 1
        controller_1.submitCreator(PersistenceContext.repositories().eCourseUsers().findByEmail("janedoe@gmail.com").get());
        controller_1.submitCourse(PersistenceContext.repositories().courses().findByCode("Python"));
        controller_1.submitTopSection("Formative 1", "This is the first formative exam",
                FeedBackType.ON_SUBMISSION, GradingType.ON_SUBMISSION);
        controller_1.submitSection("Section 1", QuestionType.BOOLEAN, 1);
        controller_1.submitSection("Section 2", QuestionType.MISSING_WORD, 1);
        controller_1.submitSection("Section 3", QuestionType.MULTIPLE_CHOICE, 1);
        controller_1.submitSection("Section 4", QuestionType.MATCHING, 1);
        controller_1.submitSection("Section 5", QuestionType.NUMERICAL, 1);
        controller_1.submitSection("Section 6", QuestionType.SHORT_ANSWER, 1);
        controller_1.saveExam(controller_1.submitExam());

        BootstrapCreateFormativeExamController controller_2 = new BootstrapCreateFormativeExamController(
                new FormativeExamBuilder(),
                new QuestionProvider(PersistenceContext.repositories().questions()),
                PersistenceContext.repositories().exams());

        // Formative Exam 2
        controller_2.submitCreator(PersistenceContext.repositories().eCourseUsers().findByEmail("johndoe@gmail.com").get());
        controller_2.submitCourse(PersistenceContext.repositories().courses().findByCode("Rust"));
        controller_2.submitTopSection("Formative 2", "This is the second formative exam",
                FeedBackType.AFTER_CLOSING, GradingType.ON_SUBMISSION);
        controller_2.submitSection("Section 1, exam 2", QuestionType.SHORT_ANSWER, 2);
        controller_2.submitSection("Section 2, exam 2", QuestionType.BOOLEAN, 1);
        controller_2.saveExam(controller_2.submitExam());

        BootstrapCreateFormativeExamController controller_3 = new BootstrapCreateFormativeExamController(
                new FormativeExamBuilder(),
                new QuestionProvider(PersistenceContext.repositories().questions()),
                PersistenceContext.repositories().exams());

        // Formative Exam 3
        controller_3.submitCreator(PersistenceContext.repositories().eCourseUsers().findByEmail("janedoe@gmail.com").get());
        controller_3.submitCourse(PersistenceContext.repositories().courses().findByCode("Rust"));
        controller_3.submitTopSection("Formative 3", "This is the third formative exam",
                FeedBackType.AFTER_CLOSING, GradingType.NONE);
        controller_3.submitSection("Section 1, exam 3", QuestionType.NUMERICAL, 1);
        controller_3.submitSection("Section 2, exam 3", QuestionType.BOOLEAN, 1);
        controller_3.submitSection("Section 3, exam 3", QuestionType.SHORT_ANSWER, 1);
        controller_3.submitSection("Section 4, exam 3", QuestionType.MISSING_WORD, 1);
        controller_3.submitSection("Section 5, exam 3", QuestionType.MULTIPLE_CHOICE, 1);
        controller_3.submitSection("Section 6, exam 3", QuestionType.MATCHING, 1);
        controller_3.saveExam(controller_3.submitExam());

        return true;
    }
}
