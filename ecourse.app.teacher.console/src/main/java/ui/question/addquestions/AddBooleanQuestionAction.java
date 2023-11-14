package ui.question.addquestions;

import eapli.framework.actions.*;

public class AddBooleanQuestionAction implements Action  {
    @Override
    public boolean execute() {
        return new AddBooleanQuestionUI().show();
    }
}
