package questionmanagment.application.updatequestions;

import eapli.framework.validations.*;
import questionmanagment.application.*;
import questionmanagment.domain.*;
import questionmanagment.repository.*;
import java.io.*;
import java.util.*;

public class UpdateMultipleChoiceQuestionController implements IUpdateQuestionController {

    private final QuestionRepository questionRepo;
    private final IQuestionFileGenerator fileGenerator;
    private final QuestionValidator validator;

    public UpdateMultipleChoiceQuestionController(
            QuestionRepository questionRepo, IQuestionFileGenerator fileGenerator, QuestionValidator validator) {
        Preconditions.noneNull(questionRepo, validator, fileGenerator);

        this.questionRepo = questionRepo;
        this.fileGenerator = fileGenerator;
        this.validator = validator;
    }

    @Override
    public Iterable<Question> listQuestions() {
        return questionRepo.findAllByType(QuestionType.MULTIPLE_CHOICE);
    }

    public Question updateQuestion(MultipleChoiceQuestion multipleChoiceQuestion, String question, Float quotation,
                                   Float penalty, List<String> options, String correctOption) {

        File file = fileGenerator.generateMultipleChoiceQuestion(question, quotation, penalty, options, correctOption);

        if (validator.validate(file.getPath()) == null)
            throw new IllegalArgumentException("Invalid Question");

        MultipleChoiceQuestion newQuestion = multipleChoiceQuestion.updateQuestion(
                question, quotation, penalty, options, correctOption);

        return questionRepo.save(newQuestion);
    }
}
