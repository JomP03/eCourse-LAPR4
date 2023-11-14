package ui.question.updatequestions;

import exammanagement.application.service.*;
import persistence.*;
import questionmanagment.application.*;
import questionmanagment.application.updatequestions.UpdateMultipleChoiceQuestionController;
import questionmanagment.domain.MultipleChoiceQuestion;
import questionmanagment.domain.Question;
import ui.components.AbstractUI;
import ui.components.ListSelector;
import ui.question.widgets.*;

import java.util.Collection;

public class UpdateMultipleChoiceQuestionUI extends AbstractUI {

    private final UpdateMultipleChoiceQuestionController controller = new UpdateMultipleChoiceQuestionController(
            PersistenceContext.repositories().questions(),
            new QuestionTxtGenerator(),
            new QuestionValidator(new ExamHandler(PersistenceContext.repositories().exams()))
    );

    @Override
    protected boolean doShow() {

        Collection<Question> questions = (Collection<Question>) controller.listQuestions();
        ListSelector<Question> selector = new ListSelector<>("Select a question", questions);
        selector.showAndSelectWithExit();
        MultipleChoiceQuestion question = (MultipleChoiceQuestion) selector.getSelectedElement();

        MultipleChoiceQuestionWidget widget = new MultipleChoiceQuestionWidget();
        widget.show();

        try {
            controller.updateQuestion(question, widget.question, widget.quotation, widget.penalty,
                    widget.options, widget.options.get(widget.correctOption));
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
