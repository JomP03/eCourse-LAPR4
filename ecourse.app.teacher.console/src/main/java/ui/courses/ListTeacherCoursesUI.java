package ui.courses;

import coursemanagement.application.listcourses.ListTeacherCoursesController;
import coursemanagement.domain.Course;
import ecourseusermanagement.application.UserSessionService;
import persistence.PersistenceContext;
import ui.components.AbstractUI;
import ui.components.ListPrinter;
import ui.components.Sleeper;

import java.util.Collection;

public class ListTeacherCoursesUI extends AbstractUI {
    private final ListTeacherCoursesController listTeacherCoursesController = new ListTeacherCoursesController(PersistenceContext.repositories().courses(), new UserSessionService(PersistenceContext.repositories().eCourseUsers()));

    @Override
    protected boolean doShow() {

        ListPrinter<Course> listPrinter = new ListPrinter<>("The following courses, are the ones available to you.",(Collection<Course>) listTeacherCoursesController.findCourses());

        if(listPrinter.isEmpty()) {
            infoMessage("There are no courses available.");
            return false;
        }

        listPrinter.showList();

        Sleeper.sleep(1000);

        return false;
    }

    @Override
    public String headline() {
        return "List Available Courses";
    }
}