package ui.question;

import eapli.framework.actions.*;

public class ManageQuestionsMenuAction implements Action {
    @Override
    public boolean execute() {
        return new ManageQuestionsMenuUI().show();
    }
}
