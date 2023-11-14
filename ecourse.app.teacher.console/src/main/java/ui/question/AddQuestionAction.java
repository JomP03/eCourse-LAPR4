package ui.question;

import eapli.framework.actions.*;
import ui.question.*;

public class AddQuestionAction implements Action {
    @Override
    public boolean execute() {
        return new AddQuestionMenu().show();
    }
}
