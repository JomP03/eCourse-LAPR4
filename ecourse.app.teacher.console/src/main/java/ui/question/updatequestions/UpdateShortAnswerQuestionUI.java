package ui.question.updatequestions;

import exammanagement.application.service.*;
import persistence.*;
import questionmanagment.application.*;
import questionmanagment.application.updatequestions.UpdateShortAnswerQuestionController;
import questionmanagment.domain.Question;
import questionmanagment.domain.ShortAnswerQuestion;
import ui.components.AbstractUI;
import ui.components.ListSelector;
import ui.question.widgets.*;

import java.util.Collection;

public class UpdateShortAnswerQuestionUI extends AbstractUI {

    private final UpdateShortAnswerQuestionController controller = new UpdateShortAnswerQuestionController(
            PersistenceContext.repositories().questions(),
            new QuestionTxtGenerator(),
            new QuestionValidator(new ExamHandler(PersistenceContext.repositories().exams()))
    );

    @Override
    protected boolean doShow() {

        Collection<Question> questions = (Collection<Question>) controller.listQuestions();
        ListSelector<Question> selector = new ListSelector<>("Select a question", questions);
        selector.showAndSelectWithExit();
        ShortAnswerQuestion question = (ShortAnswerQuestion) selector.getSelectedElement();

        ShortAnswerQuestionWidget widget = new ShortAnswerQuestionWidget();
        widget.show();

        try {
            controller.updateQuestion(question, widget.question,
                    widget.quotation, widget.penalty, widget.correctOption);
        } catch (IllegalArgumentException e) {
            errorMessage(e.getMessage());
            return false;
        }

        successMessage("Question successfully updated.");

        return false;
    }

    @Override
    public String headline() {
        return "Add Multiple Choice Question";
    }
}
