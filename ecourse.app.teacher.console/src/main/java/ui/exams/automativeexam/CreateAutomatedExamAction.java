package ui.exams.automativeexam;

import eapli.framework.actions.Action;

public class CreateAutomatedExamAction implements Action {

    @Override
    public boolean execute() {
        return new CreateAutomatedExamUI().show();
    }
}