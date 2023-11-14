package ui.question.addquestions;

import eapli.framework.actions.*;

public class AddMultipleChoiceQuestionAction implements Action  {
    @Override
    public boolean execute() {
        return new AddMultipleChoiceQuestionUI().show();
    }
}
