package ui.question.updatequestions;

import eapli.framework.actions.*;
import ui.question.updatequestions.*;

public class UpdateMatchingQuestionAction implements Action  {
    @Override
    public boolean execute() {
        return new UpdateMatchingQuestionUI().show();
    }
}
