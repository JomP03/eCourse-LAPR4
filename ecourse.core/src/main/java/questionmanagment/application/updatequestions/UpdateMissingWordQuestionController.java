package questionmanagment.application.updatequestions;

import eapli.framework.validations.*;
import questionmanagment.application.*;
import questionmanagment.domain.*;
import questionmanagment.repository.*;

import java.io.*;
import java.util.*;

public class UpdateMissingWordQuestionController implements IUpdateQuestionController {

    private final QuestionRepository questionRepo;
    private final IQuestionFileGenerator fileGenerator;
    private final QuestionValidator validator;

    public UpdateMissingWordQuestionController(
            QuestionRepository questionRepo, IQuestionFileGenerator fileGenerator, QuestionValidator validator) {
        Preconditions.noneNull(questionRepo, validator, fileGenerator);

        this.questionRepo = questionRepo;
        this.fileGenerator = fileGenerator;
        this.validator = validator;
    }

    @Override
    public Iterable<Question> listQuestions() {
        return questionRepo.findAllByType(QuestionType.MISSING_WORD);
    }

    public Question updateQuestion(MissingWordQuestion missingWordQuestion, String question,
                                   Float quotation, Float penalty, List<MissingWordOption> options) {

        File file = fileGenerator.generateMissingWordQuestion(question, quotation, penalty, options);

        if (validator.validate(file.getPath()) == null)
            throw new IllegalArgumentException("Invalid Question");

        MissingWordQuestion newQuestion = missingWordQuestion.updateQuestion(
                question, quotation, penalty, options);

        return questionRepo.save(newQuestion);
    }
}
