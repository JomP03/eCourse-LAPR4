package ui.question.addquestions;

import exammanagement.application.service.*;
import persistence.*;
import questionmanagment.application.*;
import questionmanagment.application.addquestions.*;
import questionmanagment.domain.factory.QuestionFactory;
import ui.components.AbstractUI;
import ui.question.widgets.*;

public class AddMatchingQuestionUI extends AbstractUI {

    private final AddMatchingQuestionController theController = new AddMatchingQuestionController(
            PersistenceContext.repositories().questions(),
            new QuestionFactory(
                    new QuestionValidator(
                            new ExamHandler(PersistenceContext.repositories().exams())),
                    new QuestionTxtGenerator()
            ));

    @Override
    protected boolean doShow() {

        MatchingQuestionWidget widget = new MatchingQuestionWidget();
        widget.show();

        try {
            theController.addQuestion(widget.question, widget.quotation, widget.penalty,
                    widget.leftOptions, widget.rightOptions);
        } catch (IllegalArgumentException e) {
            errorMessage(e.getMessage());
            return false;
        }

        successMessage("Question successfully added.");

        return false;
    }

    @Override
    public String headline() {
        return "Add Matching Question";
    }
}
