package questionmanagment.application.addquestions;

import questionmanagment.domain.*;
import questionmanagment.domain.factory.QuestionFactory;
import questionmanagment.repository.*;

import java.util.*;

public class AddMissingWordQuestionController {

    private final QuestionRepository questionRepo;
    private final QuestionFactory factory;

    public AddMissingWordQuestionController(QuestionRepository questionRepo, QuestionFactory factory) {
        this.questionRepo = questionRepo;
        this.factory = factory;
    }

    public Question addQuestion(String question, Float quotation, Float penalty, List<MissingWordOption> options) {
        MissingWordQuestion newQuestion = factory.createMissingWordQuestion(question, quotation, penalty, options);

        return questionRepo.save(newQuestion);
    }
}
