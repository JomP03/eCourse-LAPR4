package ui.question.addquestions;

import eapli.framework.actions.*;

public class AddNumericalQuestionAction implements Action  {
    @Override
    public boolean execute() {
        return new AddNumericalQuestionUI().show();
    }
}
