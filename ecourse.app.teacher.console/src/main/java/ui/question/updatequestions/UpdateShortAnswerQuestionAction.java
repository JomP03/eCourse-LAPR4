package ui.question.updatequestions;

import eapli.framework.actions.*;

public class UpdateShortAnswerQuestionAction implements Action  {
    @Override
    public boolean execute() {
        return new UpdateShortAnswerQuestionUI().show();
    }
}
