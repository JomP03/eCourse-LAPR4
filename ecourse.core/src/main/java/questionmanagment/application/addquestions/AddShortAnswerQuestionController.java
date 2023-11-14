package questionmanagment.application.addquestions;

import questionmanagment.domain.*;
import questionmanagment.domain.factory.QuestionFactory;
import questionmanagment.repository.*;

public class AddShortAnswerQuestionController {

    private final QuestionRepository questionRepo;
    private final QuestionFactory factory;

    public AddShortAnswerQuestionController(QuestionRepository questionRepo, QuestionFactory factory) {
        this.questionRepo = questionRepo;
        this.factory = factory;
    }

    public Question addQuestion(String question, Float quotation, Float penalty, String correctAnswer) {
        ShortAnswerQuestion newQuestion = factory.createShortAnswerQuestion(question, quotation, penalty, correctAnswer);

        return questionRepo.save(newQuestion);
    }
}
