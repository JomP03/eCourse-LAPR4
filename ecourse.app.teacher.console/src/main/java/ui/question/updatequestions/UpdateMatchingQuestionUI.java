package ui.question.updatequestions;

import exammanagement.application.service.*;
import persistence.*;
import questionmanagment.application.*;
import questionmanagment.application.updatequestions.UpdateMatchingQuestionController;
import questionmanagment.domain.MatchingQuestion;
import questionmanagment.domain.Question;
import ui.components.AbstractUI;
import ui.components.ListSelector;
import ui.question.widgets.*;

import java.util.Collection;

public class UpdateMatchingQuestionUI extends AbstractUI {

    private final UpdateMatchingQuestionController controller = new UpdateMatchingQuestionController(
            PersistenceContext.repositories().questions(),
            new QuestionTxtGenerator(),
            new QuestionValidator(new ExamHandler(PersistenceContext.repositories().exams()))
    );

    @Override
    protected boolean doShow() {

        Collection<Question> questions = (Collection<Question>) controller.listQuestions();
        ListSelector<Question> selector = new ListSelector<>("Select a question", questions);
        selector.showAndSelectWithExit();
        MatchingQuestion question = (MatchingQuestion) selector.getSelectedElement();

        MatchingQuestionWidget widget = new MatchingQuestionWidget();
        widget.show();

        try {
            controller.updateQuestion(question, widget.question,
                    widget.quotation, widget.penalty, widget.leftOptions, widget.rightOptions);
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
