package ui.question;

import eapli.framework.actions.*;

public class UpdateQuestionAction implements Action {
    @Override
    public boolean execute() {
        return new UpdateQuestionMenu().show();
    }
}
