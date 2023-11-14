package ui.enrollmentrequest;

import coursemanagement.domain.Course;
import enrollmentrequestmanagement.application.manage.ListEnrollmentRequestController;
import enrollmentrequestmanagement.domain.EnrollmentRequest;
import persistence.PersistenceContext;
import ui.components.AbstractUI;
import ui.components.ColorCode;
import ui.components.ListSelector;
import ui.components.Sleeper;
import ui.menu.Menu;
import ui.menu.MenuRenderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListEnrollmentRequestUI extends AbstractUI {

    private final ListEnrollmentRequestController listEnrollmentRequestController =
            new ListEnrollmentRequestController(PersistenceContext.repositories().enrollmentRequests());

    @Override
    protected boolean doShow() {

        // Courses in "ENROLL" state, to select from
        ListSelector<Course> enrollStateCourses;

        try {
            enrollStateCourses = new ListSelector<>("Available Enroll State Courses:",
                    (Collection<Course>) listEnrollmentRequestController.getCoursesOpenForEnrollment());
        }
        catch (Exception e) {
            errorMessage(e.getMessage());
            Sleeper.sleep(1000);
            return false;
        }

        if (enrollStateCourses.isEmpty()) {
            errorMessage("There are no courses in _enroll_ state");
            Sleeper.sleep(1000);
            return false;
        }

        if (!enrollStateCourses.showAndSelectWithExit()) return false;

        do {
            List<EnrollmentRequest> pendingStateEnrollmentRequests = new ArrayList<>();

            try {
                // Select the teacher
                pendingStateEnrollmentRequests = (List<EnrollmentRequest>) listEnrollmentRequestController
                        .getEnrollmentRequestsForCourse(enrollStateCourses.getSelectedElement());

                if (pendingStateEnrollmentRequests.isEmpty()) {
                    infoMessage("There are no enrollment requests in pending state for the selected course.");
                    Sleeper.sleep(1000);
                    break;
                }

                ListSelector<EnrollmentRequest> enrollmentRequestSelector =
                        new ListSelector<>("Available Enrollment Requests:", pendingStateEnrollmentRequests);

                if (!enrollmentRequestSelector.showAndSelectWithExit()) return false;

                // Enrollment requests possible actions
                final Menu menu = buildActionSelectionMenu(enrollmentRequestSelector.getSelectedElement());
                final var renderer = new MenuRenderer(menu);
                if (!renderer.render(MenuRenderer.PullOutActions.BACK.message())) {
                    System.out.println();

                    pendingStateEnrollmentRequests.remove(enrollmentRequestSelector.getSelectedElement());
                }

                else {
                    System.out.println();
                }

            } catch (Exception e) {
                errorMessage(e.getMessage());
                Sleeper.sleep(1000);
            }

            if (pendingStateEnrollmentRequests.isEmpty()) {
                infoMessage("There are no more enrollment requests to be approved/rejected.");
                Sleeper.sleep(1000);
                break;
            }

        } while (true);

        return false;
    }

    private Menu buildActionSelectionMenu(EnrollmentRequest enrollmentRequest) {
        final Menu actionSelectionMenu = new Menu();

        // Add the action options to the menu
        actionSelectionMenu.addItem(1, ColorCode.GREEN.getValue() + "Approve" + ColorCode.RESET.getValue(),
                new ApproveEnrollmentRequestAction(enrollmentRequest));
        actionSelectionMenu.addItem(2, ColorCode.RED.getValue() + "Reject" + ColorCode.RESET.getValue(),
                new RejectEnrollmentRequestAction(enrollmentRequest));

        return actionSelectionMenu;
    }

    @Override
    public String headline() {
        return "Manage Enrollment Requests";
    }

}
