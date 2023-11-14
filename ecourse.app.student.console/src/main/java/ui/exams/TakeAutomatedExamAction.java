package ui.exams;

import eapli.framework.actions.Action;

public class TakeAutomatedExamAction implements Action {

    @Override
    public boolean execute() {
        return new TakeAutomatedExamUI().show();
    }

}
