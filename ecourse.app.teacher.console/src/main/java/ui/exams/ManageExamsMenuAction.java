package ui.exams;

import eapli.framework.actions.*;

public class ManageExamsMenuAction implements Action {
    @Override
    public boolean execute() {
        return new ManageExamsMenuUI().show();
    }
}
