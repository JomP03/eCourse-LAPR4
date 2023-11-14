package ui.question.addquestions;

import eapli.framework.actions.*;

public class AddShortAnswerQuestionAction implements Action  {
    @Override
    public boolean execute() {
        return new AddShortAnswerQuestionUI().show();
    }
}
