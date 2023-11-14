package ui.question.updatequestions;

import eapli.framework.actions.*;
import ui.question.updatequestions.*;

public class UpdateMultipleChoiceQuestionAction implements Action  {
    @Override
    public boolean execute() {
        return new UpdateMultipleChoiceQuestionUI().show();
    }
}
