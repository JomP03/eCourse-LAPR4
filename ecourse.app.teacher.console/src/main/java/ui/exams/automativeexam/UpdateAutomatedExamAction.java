package ui.exams.automativeexam;

import eapli.framework.actions.Action;

public class UpdateAutomatedExamAction implements Action {

    @Override
    public boolean execute() {
        return new UpdateAutomatedExamUI().show();
    }
}
