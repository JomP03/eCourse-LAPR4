import coursemanagement.application.listcourses.StudentCoursesProvider;
import coursemanagement.domain.Course;
import eapli.framework.actions.*;
import ecourseusermanagement.application.UserSessionService;
import exammanagement.application.CourseExamsProvider;
import exammanagement.application.ExamTxtParser;
import exammanagement.domain.AutomatedExam;
import exammanagement.domain.FormativeExam;
import persistence.PersistenceContext;
import takenexammanagement.application.TakeFormativeExamController;
import takenexammanagement.application.TakeAutomatedExamController;
import takenexammanagement.application.service.TakenExamCorrector;
import takenexammanagement.application.service.TakenExamHandler;
import takenexammanagement.application.service.TakenExamVisitor;
import usermanagement.domain.SessionManager;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class TakenExamBootstrapper implements Action {

    TakeFormativeExamController formativeController;
    TakeAutomatedExamController automatedController;
    FormativeExam formativeExam;
    AutomatedExam automatedExam;

    @Override
    public boolean execute() {
        authenticate("mariaferreira", "MariaFerreira1");
        initialControllers();
        initialData();

        File formativeFile = new File("ecourse.bootstrappers/src/main/resources/formative.txt");

        File automatedFile = new File("ecourse.bootstrappers/src/main/resources/automated.txt");

        try {
            formativeController.correctExam(formativeFile.getPath(), formativeExam);
            automatedController.correctExam(automatedFile.getPath(), automatedExam);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    private void initialControllers() {
        formativeController = new TakeFormativeExamController(
                new UserSessionService(PersistenceContext.repositories().eCourseUsers()),
                new StudentCoursesProvider(PersistenceContext.repositories().courses()),
                new CourseExamsProvider(PersistenceContext.repositories().exams()),
                new ExamTxtParser(),
                PersistenceContext.repositories().takenExams(),
                new TakenExamHandler(new TakenExamCorrector()));

        automatedController = new TakeAutomatedExamController(
                new UserSessionService(PersistenceContext.repositories().eCourseUsers()),
                PersistenceContext.repositories().exams(),
                PersistenceContext.repositories().courses(),
                PersistenceContext.repositories().enrolledStudents(),
                PersistenceContext.repositories().takenExams(),
                new TakenExamCorrector(),
                new ExamTxtParser()
        );
    }

    private void authenticate(String username, String password) {
        // Use the SessionManager to authenticate the user
        SessionManager sessionManager = SessionManager.getInstance();
        sessionManager.login(username, password);
    }

    private void initialData() {
        Course rustCourse = PersistenceContext.repositories().courses().findByCode("Rust");
        Course pythonCourse = PersistenceContext.repositories().courses().findByCode("Python");
        Iterable<FormativeExam> formatives = PersistenceContext.repositories().exams()
                .findFormativeExamByCourse(rustCourse);

        Iterable<AutomatedExam> automatedExams = PersistenceContext.repositories().exams()
                .findAutomatedExamByCourse(pythonCourse);

        formativeExam = formatives.iterator().next();

        automatedExam = automatedExams.iterator().next();
    }
}
