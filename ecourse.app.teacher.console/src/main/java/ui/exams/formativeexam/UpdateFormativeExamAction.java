package ui.exams.formativeexam;

import eapli.framework.actions.*;

public class UpdateFormativeExamAction implements Action {
    @Override
    public boolean execute() {
        return new UpdateFormativeExamUI().show();
    }
}
