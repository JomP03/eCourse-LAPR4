package ui.exams.listers;

import eapli.framework.actions.Action;

public class ViewExamGradesAction implements Action {

    @Override
    public boolean execute() {
        return new ViewExamGradesUI().show();
    }
}
