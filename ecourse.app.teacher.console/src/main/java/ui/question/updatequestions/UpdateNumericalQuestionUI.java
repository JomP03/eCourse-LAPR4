package ui.question.updatequestions;

import exammanagement.application.service.*;
import persistence.*;
import questionmanagment.application.*;
import questionmanagment.application.updatequestions.UpdateNumericalQuestionController;
import questionmanagment.domain.NumericalQuestion;
import questionmanagment.domain.Question;
import ui.components.AbstractUI;
import ui.components.ListSelector;
import ui.question.widgets.*;

import java.util.Collection;

public class UpdateNumericalQuestionUI extends AbstractUI {

    private final UpdateNumericalQuestionController controller = new UpdateNumericalQuestionController(
            PersistenceContext.repositories().questions(),
            new QuestionTxtGenerator(),
            new QuestionValidator(new ExamHandler(PersistenceContext.repositories().exams()))
    );

    @Override
    protected boolean doShow() {

        Collection<Question> questions = (Collection<Question>) controller.listQuestions();
        ListSelector<Question> selector = new ListSelector<>("Select a question", questions);
        selector.showAndSelectWithExit();
        NumericalQuestion question = (NumericalQuestion) selector.getSelectedElement();

        NumericalQuestionWidget widget = new NumericalQuestionWidget();
        widget.show();

        try {
            controller.updateQuestion(question, widget.question, widget.quotation,
                    widget.penalty, widget.correctOption);
        } catch (IllegalArgumentException e) {
            errorMessage(e.getMessage());
            return false;
        }

        successMessage("Question successfully updated.");

        return false;
    }

    @Override
    public String headline() {
        return "Add Numerical Question";
    }
}
