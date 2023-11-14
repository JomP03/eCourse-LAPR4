package ui.question.updatequestions;

import exammanagement.application.service.*;
import persistence.*;
import questionmanagment.application.*;
import questionmanagment.application.updatequestions.UpdateMissingWordQuestionController;
import questionmanagment.domain.MissingWordQuestion;
import questionmanagment.domain.Question;
import ui.components.AbstractUI;
import ui.components.ListSelector;
import ui.question.widgets.*;

import java.util.Collection;

public class UpdateMissingWordQuestionUI extends AbstractUI {

    private final UpdateMissingWordQuestionController controller = new UpdateMissingWordQuestionController(
            PersistenceContext.repositories().questions(),
            new QuestionTxtGenerator(),
            new QuestionValidator(new ExamHandler(PersistenceContext.repositories().exams()))
    );

    @Override
    protected boolean doShow() {

        Collection<Question> questions = (Collection<Question>) controller.listQuestions();
        ListSelector<Question> selector = new ListSelector<>("Select a question", questions);
        selector.showAndSelectWithExit();
        MissingWordQuestion question = (MissingWordQuestion) selector.getSelectedElement();

        MissingWordQuestionWidget widget = new MissingWordQuestionWidget();
        widget.show();

        try {
            controller.updateQuestion(question, widget.question, widget.quotation, widget.penalty, widget.missingWordOptions);
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
