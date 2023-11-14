package ui.courses;

import coursemanagement.application.listcourses.StudentCoursesProvider;
import coursemanagement.application.listcourses.ListStudentCoursesController;
import coursemanagement.domain.Course;
import ecourseusermanagement.application.UserSessionService;
import persistence.PersistenceContext;
import ui.components.AbstractUI;
import ui.components.ListPrinter;
import ui.components.Sleeper;

import java.util.Collection;

public class ListStudentCoursesUI extends AbstractUI {

    private final ListStudentCoursesController listStudentCoursesController =
            new ListStudentCoursesController(new StudentCoursesProvider(PersistenceContext.repositories().courses()),
                    new UserSessionService(PersistenceContext.repositories().eCourseUsers()));

    @Override
    protected boolean doShow() {

        ListPrinter<Course> listPrinter =
                new ListPrinter<>("The following courses are either the courses you belong to or the courses you can request to enroll.",
                        (Collection<Course>) listStudentCoursesController.findCourses());

        if(listPrinter.isEmpty()) {
            infoMessage("There are no courses available.");
            return false;
        }

        listPrinter.showList();

        Sleeper.sleep(1500);

        return false;
    }

    @Override
    public String headline() {
        return "List Available Courses";
    }
}
