package ui.course.coursestate;

import coursemanagement.application.coursestate.*;
import coursemanagement.domain.*;
import persistence.*;
import ui.components.AbstractUI;
import ui.components.ListSelector;
import ui.components.Sleeper;

import java.util.*;

public class CloseCourseUI extends AbstractUI {

    private final CloseCourseController controller = new CloseCourseController(
            PersistenceContext.repositories().courses(),
            new ListCourseByStateService(PersistenceContext.repositories().courses()));

    @Override
    protected boolean doShow() {
        ListSelector<Course> listSelector =
                new ListSelector<>("In Progress State Courses", (Collection<Course>) controller.findCourses());

        if (listSelector.isEmpty()) {
            infoMessage("There are no courses in an in_progress state");
            Sleeper.sleep(1000);
            return false;
        }

        if (!listSelector.showAndSelectWithExit())
            return false;

        try {
            controller.changeCourseState(listSelector.getSelectedElement());
            System.out.println();
        } catch (IllegalArgumentException e) {
            errorMessage(e.getMessage());
            Sleeper.sleep(1000);
            return false;
        }

        successMessage("Course state changed successfully");
        Sleeper.sleep(1000);

        return false;
    }

    @Override
    public String headline() {
        return "Close Course";
    }
}
