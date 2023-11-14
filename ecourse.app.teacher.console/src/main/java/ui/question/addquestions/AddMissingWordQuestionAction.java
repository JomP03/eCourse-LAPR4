package ui.question.addquestions;

import eapli.framework.actions.*;

public class AddMissingWordQuestionAction implements Action  {
    @Override
    public boolean execute() {
        return new AddMissingWordQuestionUI().show();
    }
}
