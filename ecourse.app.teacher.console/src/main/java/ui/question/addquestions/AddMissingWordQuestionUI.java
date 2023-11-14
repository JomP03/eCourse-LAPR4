package ui.question.addquestions;

import exammanagement.application.service.*;
import persistence.*;
import questionmanagment.application.*;
import questionmanagment.application.addquestions.*;
import questionmanagment.domain.factory.QuestionFactory;
import ui.components.AbstractUI;
import ui.question.widgets.*;

public class AddMissingWordQuestionUI extends AbstractUI {

    private final AddMissingWordQuestionController controller = new AddMissingWordQuestionController(
            PersistenceContext.repositories().questions(),
            new QuestionFactory(
                    new QuestionValidator(
                            new ExamHandler(PersistenceContext.repositories().exams())),
                    new QuestionTxtGenerator()
            ));

    @Override
    protected boolean doShow() {

        MissingWordQuestionWidget widget = new MissingWordQuestionWidget();
        widget.show();

        try {
            controller.addQuestion(widget.question, widget.quotation, widget.penalty, widget.missingWordOptions);
        } catch (IllegalArgumentException e) {
            errorMessage(e.getMessage());
            return false;
        }

        successMessage("Question successfully added.");

        return false;
    }

    @Override
    public String headline() {
        return "Add Missing Word Question";
    }
}
