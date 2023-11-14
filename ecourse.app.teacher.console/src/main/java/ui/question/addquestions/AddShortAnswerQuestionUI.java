package ui.question.addquestions;

import exammanagement.application.service.*;
import persistence.*;
import questionmanagment.application.*;
import questionmanagment.application.addquestions.*;
import questionmanagment.domain.factory.QuestionFactory;
import ui.components.AbstractUI;
import ui.question.widgets.*;

public class AddShortAnswerQuestionUI extends AbstractUI {

    private final AddShortAnswerQuestionController controller = new AddShortAnswerQuestionController(
            PersistenceContext.repositories().questions(),
            new QuestionFactory(
                    new QuestionValidator(
                            new ExamHandler(PersistenceContext.repositories().exams())),
                    new QuestionTxtGenerator()
            ));

    @Override
    protected boolean doShow() {

        ShortAnswerQuestionWidget widget = new ShortAnswerQuestionWidget();
        widget.show();

        try {
            controller.addQuestion(widget.question, widget.quotation, widget.penalty, widget.correctOption);
        } catch (IllegalArgumentException e) {
            errorMessage(e.getMessage());
            return false;
        }

        successMessage("Question successfully added.");

        return false;
    }

    @Override
    public String headline() {
        return "Add Short Answer Question";
    }
}
