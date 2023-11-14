package questionmanagment.application.addquestions;

import questionmanagment.domain.*;
import questionmanagment.domain.factory.QuestionFactory;
import questionmanagment.repository.*;
import java.util.*;

public class AddMatchingQuestionController {

    private final QuestionRepository questionRepo;
    private final QuestionFactory factory;

    public AddMatchingQuestionController(QuestionRepository questionRepo, QuestionFactory factory) {
        this.questionRepo = questionRepo;
        this.factory = factory;
    }

    public Question addQuestion(String question, Float quotation, Float penalty, List<String> leftOptions, List<String> rightOptions) {
        MatchingQuestion newQuestion = factory.createMatchingQuestion(question, quotation, penalty, leftOptions, rightOptions);

        return questionRepo.save(newQuestion);
    }
}
