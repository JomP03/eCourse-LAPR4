package questionmanagment.application.addquestions;

import questionmanagment.domain.*;
import questionmanagment.domain.factory.QuestionFactory;
import questionmanagment.repository.*;

public class AddBooleanQuestionController {

    private final QuestionRepository questionRepo;
    private final QuestionFactory factory;

    public AddBooleanQuestionController(QuestionRepository questionRepo, QuestionFactory factory) {
        this.questionRepo = questionRepo;
        this.factory = factory;
    }

    public Question addQuestion(String question, Float quotation, Float penalty, boolean correctAnswer) {
        BooleanQuestion newQuestion = factory.createBooleanQuestion(question, quotation, penalty, correctAnswer);

        return questionRepo.save(newQuestion);
    }
}
