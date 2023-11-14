package ui.question.addquestions;

import eapli.framework.actions.*;

public class AddMatchingQuestionAction implements Action  {
    @Override
    public boolean execute() {
        return new AddMatchingQuestionUI().show();
    }
}
