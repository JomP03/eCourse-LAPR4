package ui.exams.formativeexam;

import eapli.framework.actions.Action;

public class CreateFormativeExamAction implements Action {
    @Override
    public boolean execute() {
        return new CreateFormativeExamUI().show();
    }
}
