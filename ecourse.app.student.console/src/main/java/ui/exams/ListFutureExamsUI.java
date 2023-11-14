package ui.exams;

import coursemanagement.application.StudentCoursesProvider;
import ecourseusermanagement.application.UserSessionService;
import exammanagement.application.CourseExamsProvider;
import exammanagement.application.listexams.ListFutureExamsController;
import exammanagement.domain.AutomatedExam;
import exammanagement.domain.Exam;
import exammanagement.domain.FormativeExam;
import persistence.PersistenceContext;
import ui.components.AbstractUI;
import ui.components.Sleeper;

import java.util.List;

public class ListFutureExamsUI extends AbstractUI {

    private final ListFutureExamsController listFutureExamsController =
            new ListFutureExamsController(new UserSessionService(PersistenceContext.repositories().eCourseUsers()),
                    new StudentCoursesProvider(PersistenceContext.repositories().enrolledStudents()),
                    new CourseExamsProvider(PersistenceContext.repositories().exams()));

    @Override
    protected boolean doShow() {

        List<Exam> listFutureExams = listFutureExamsController.futureExams();

        if (listFutureExams.isEmpty()) {
            infoMessage("No future exams found.");
            return false;
        }

        System.out.print("Your future exams:\n\n");

        for (Exam exam : listFutureExams) {
            if (exam instanceof FormativeExam)
                System.out.printf("- Formative Exam Title: %s | Course Code: %s\n",exam.title(), exam.course().identity().toString());

            else {
                AutomatedExam automatedExam = (AutomatedExam) exam;
                System.out.printf("- Automated Exam Title: %s | Course Code: %s | Exam Date: %s\n", automatedExam.title(),
                        automatedExam.course().identity().toString(),
                            automatedExam.openPeriod().openDate());
            }
        }
        System.out.println();

        Sleeper.sleep(1500);

        return false;
    }

    @Override
    public String headline() {
        return "View A List Of My Future Exams";
    }
}
