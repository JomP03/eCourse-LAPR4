package ui.enrollmentrequest;

import coursemanagement.domain.Course;
import enrollmentrequestmanagement.application.register.RegisterEnrollmentRequestController;
import persistence.PersistenceContext;
import ui.components.AbstractUI;
import ui.components.ListSelector;
import ui.components.Sleeper;

import java.util.Collection;

public class RegisterEnrollmentRequestUI extends AbstractUI {

    private final RegisterEnrollmentRequestController registerEnrollmentRequestController = new RegisterEnrollmentRequestController(PersistenceContext.repositories().enrollmentRequests());

    @Override
    protected boolean doShow() {
        // 1 second = 1000 milliseconds
        int ONE_SECOND = 1000;

        try {
            // Courses in "ENROLL" state, to select from
            ListSelector<Course> enrollStateCourses =
                    new ListSelector<>("Available Enroll State Courses:",
                            (Collection<Course>) registerEnrollmentRequestController.getCoursesOpenForEnrollment());

            if (enrollStateCourses.isEmpty()) {
                infoMessage("There are no courses in _enroll_ state");
                Sleeper.sleep(1000);
                return false;
            }

            if (!enrollStateCourses.showAndSelectWithExit()) return false;

            Course selectedCourse = enrollStateCourses.getSelectedElement();

            registerEnrollmentRequestController.registerEnrollmentRequest(selectedCourse);
        }
        catch (IllegalArgumentException e) {
            errorMessage(e.getMessage());
            Sleeper.sleep(ONE_SECOND);
            return false;
        }

        successMessage("Enrollment request registered successfully");
        Sleeper.sleep(ONE_SECOND);

        return false;

    }

    @Override
    public String headline() {
        return "Enrollment Request";
    }

}
