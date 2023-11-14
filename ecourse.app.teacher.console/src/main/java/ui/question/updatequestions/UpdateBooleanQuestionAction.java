package ui.question.updatequestions;

import eapli.framework.actions.*;

public class UpdateBooleanQuestionAction implements Action  {
    @Override
    public boolean execute() {
        return new UpdateBooleanQuestionUI().show();
    }
}
