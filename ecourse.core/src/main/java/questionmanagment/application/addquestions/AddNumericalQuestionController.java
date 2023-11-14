package questionmanagment.application.addquestions;

import questionmanagment.domain.*;
import questionmanagment.domain.factory.QuestionFactory;
import questionmanagment.repository.*;

public class AddNumericalQuestionController {

    private final QuestionRepository questionRepo;
    private final QuestionFactory factory;

    public AddNumericalQuestionController(QuestionRepository questionRepo, QuestionFactory factory) {
        this.questionRepo = questionRepo;
        this.factory = factory;
    }

    public Question addQuestion(String question, Float quotation, Float penalty, String correctAnswer) {
        NumericalQuestion newQuestion = factory.createNumericalQuestion(question, quotation, penalty, correctAnswer);

        return questionRepo.save(newQuestion);
    }
}
