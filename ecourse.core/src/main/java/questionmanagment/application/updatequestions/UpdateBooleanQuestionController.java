package questionmanagment.application.updatequestions;

import eapli.framework.validations.*;
import questionmanagment.application.*;
import questionmanagment.domain.*;
import questionmanagment.repository.*;

import java.io.*;

public class UpdateBooleanQuestionController implements IUpdateQuestionController {

    private final QuestionRepository questionRepo;
    private final IQuestionFileGenerator fileGenerator;
    private final QuestionValidator validator;

    public UpdateBooleanQuestionController(
            QuestionRepository questionRepo, IQuestionFileGenerator fileGenerator, QuestionValidator validator) {
        Preconditions.noneNull(questionRepo, validator, fileGenerator);

        this.questionRepo = questionRepo;
        this.fileGenerator = fileGenerator;
        this.validator = validator;
    }


    @Override
    public Iterable<Question> listQuestions() {
        return questionRepo.findAllByType(QuestionType.BOOLEAN);
    }

    public Question updateQuestion(BooleanQuestion booleanQuestion, String question,
                                   Float quotation, Float penalty, boolean correctAnswer) {

        File file = fileGenerator.generateBooleanQuestion(question, quotation, penalty, correctAnswer);

        if (validator.validate(file.getPath()) == null)
            throw new IllegalArgumentException("Invalid Question");

        BooleanQuestion updatedQuestion = booleanQuestion.updateQuestion(question, quotation, penalty, correctAnswer);

        return questionRepo.save(updatedQuestion);
    }
}
