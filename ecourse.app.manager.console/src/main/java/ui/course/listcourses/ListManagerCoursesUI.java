package ui.course.listcourses;

import coursemanagement.application.listcourses.ListManagerCoursesController;
import coursemanagement.domain.Course;
import persistence.PersistenceContext;
import ui.components.AbstractUI;
import ui.components.ListPrinter;
import ui.components.Sleeper;

import java.util.Collection;

public class ListManagerCoursesUI extends AbstractUI {

    private final ListManagerCoursesController listManagerCoursesController = new ListManagerCoursesController(PersistenceContext.repositories().courses());

    @Override
    protected boolean doShow() {

        ListPrinter<Course> listPrinter = new ListPrinter<>("All existing courses in the system.",(Collection<Course>) listManagerCoursesController.findAllCourses());

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
        return "List All Courses";
    }
}

