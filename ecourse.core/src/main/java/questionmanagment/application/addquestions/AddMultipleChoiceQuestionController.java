package questionmanagment.application.addquestions;

import questionmanagment.domain.*;
import questionmanagment.domain.factory.QuestionFactory;
import questionmanagment.repository.*;

import java.util.*;

public class AddMultipleChoiceQuestionController {

    private final QuestionRepository questionRepo;
    private final QuestionFactory factory;

    public AddMultipleChoiceQuestionController(QuestionRepository questionRepo, QuestionFactory factory) {
        this.questionRepo = questionRepo;
        this.factory = factory;
    }

    public Question addQuestion(String question, Float quotation, Float penalty, List<String> options,
                                String correctOption) {
        MultipleChoiceQuestion newQuestion = factory.createMultipleChoiceQuestion(
                question, quotation, penalty, options, correctOption);

        return questionRepo.save(newQuestion);
    }
}
