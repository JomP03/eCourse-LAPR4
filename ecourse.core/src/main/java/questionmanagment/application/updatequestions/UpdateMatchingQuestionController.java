package questionmanagment.application.updatequestions;

import eapli.framework.validations.*;
import questionmanagment.application.*;
import questionmanagment.domain.*;
import questionmanagment.repository.*;

import java.io.*;
import java.util.*;

public class UpdateMatchingQuestionController implements IUpdateQuestionController {

    private final QuestionRepository questionRepo;
    private final IQuestionFileGenerator fileGenerator;
    private final QuestionValidator validator;

    public UpdateMatchingQuestionController(
            QuestionRepository questionRepo, IQuestionFileGenerator fileGenerator, QuestionValidator validator) {
        Preconditions.noneNull(questionRepo, validator, fileGenerator);

        this.questionRepo = questionRepo;
        this.fileGenerator = fileGenerator;
        this.validator = validator;
    }

    @Override
    public Iterable<Question> listQuestions() {
        return questionRepo.findAllByType(QuestionType.MATCHING);
    }

    public Question updateQuestion(MatchingQuestion matchingQuestion, String question, Float quotation,
                                   Float penalty, List<String> leftOptions, List<String> rightOptions) {

        File file = fileGenerator.generateMatchingQuestion(question, quotation, penalty, leftOptions, rightOptions);

        if (validator.validate(file.getPath()) == null)
            throw new IllegalArgumentException("Invalid Question");

        MatchingQuestion newQuestion = matchingQuestion.updateQuestion(
                question, quotation, penalty, leftOptions, rightOptions);

        return questionRepo.save(newQuestion);
    }


}
