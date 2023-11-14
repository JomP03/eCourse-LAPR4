package ui.question.updatequestions;

import exammanagement.application.service.*;
import persistence.*;
import questionmanagment.application.*;
import questionmanagment.application.updatequestions.*;
import questionmanagment.domain.*;
import ui.components.AbstractUI;
import ui.components.ListSelector;
import ui.question.widgets.*;

import java.util.*;

public class UpdateBooleanQuestionUI extends AbstractUI {

    private final UpdateBooleanQuestionController controller = new UpdateBooleanQuestionController(
            PersistenceContext.repositories().questions(),
            new QuestionTxtGenerator(),
            new QuestionValidator(new ExamHandler(PersistenceContext.repositories().exams()))
    );

    @Override
    protected boolean doShow() {

        Collection<Question> questions = (Collection<Question>) controller.listQuestions();
        ListSelector<Question> selector = new ListSelector<>("Select a question", questions);
        selector.showAndSelectWithExit();
        BooleanQuestion question = (BooleanQuestion) selector.getSelectedElement();

        BooleanQuestionWidget widget = new BooleanQuestionWidget();
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
        return "Add Matching Question";
    }
}
