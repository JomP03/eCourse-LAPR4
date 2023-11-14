package ui.question.updatequestions;

import eapli.framework.actions.*;
import ui.question.updatequestions.*;

public class UpdateMissingWordQuestionAction implements Action  {
    @Override
    public boolean execute() {
        return new UpdateMissingWordQuestionUI().show();
    }
}
