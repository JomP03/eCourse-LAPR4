package questionmanagment.application.updatequestions;

import eapli.framework.validations.*;
import questionmanagment.application.*;
import questionmanagment.domain.*;
import questionmanagment.repository.*;

import java.io.*;

public class UpdateNumericalQuestionController implements IUpdateQuestionController {

    private final QuestionRepository questionRepo;
    private final IQuestionFileGenerator fileGenerator;
    private final QuestionValidator validator;

    public UpdateNumericalQuestionController(
            QuestionRepository questionRepo, IQuestionFileGenerator fileGenerator, QuestionValidator validator) {
        Preconditions.noneNull(questionRepo, validator, fileGenerator);

        this.questionRepo = questionRepo;
        this.fileGenerator = fileGenerator;
        this.validator = validator;
    }

    @Override
    public Iterable<Question> listQuestions() {
        return questionRepo.findAllByType(QuestionType.NUMERICAL);
    }

    public Question updateQuestion(NumericalQuestion numericalQuestion, String question,
                                Float quotation, Float penalty, String correctAnswer) {

        File file = fileGenerator.generateNumericalQuestion(question, quotation, penalty, correctAnswer);

        if (validator.validate(file.getPath()) == null)
            throw new IllegalArgumentException("Invalid Question");

        NumericalQuestion newQuestion = numericalQuestion.updateQuestion(
                question, quotation, penalty, correctAnswer);

        return questionRepo.save(newQuestion);
    }
}
