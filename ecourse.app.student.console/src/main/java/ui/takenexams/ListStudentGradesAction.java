package ui.takenexams;


import eapli.framework.actions.Action;
import ui.exams.ListFutureExamsUI;

public class ListStudentGradesAction implements Action {

    @Override
    public boolean execute() {
        return new ListStudentGradesUI().show();
    }
}
