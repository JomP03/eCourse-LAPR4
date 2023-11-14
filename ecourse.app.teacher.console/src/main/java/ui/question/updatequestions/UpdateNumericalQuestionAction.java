package ui.question.updatequestions;

import eapli.framework.actions.*;

public class UpdateNumericalQuestionAction implements Action  {
    @Override
    public boolean execute() {
        return new UpdateNumericalQuestionUI().show();
    }
}
