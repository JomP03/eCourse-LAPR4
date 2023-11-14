package questionmanagment.application.updatequestions;

import eapli.framework.validations.*;
import questionmanagment.application.*;
import questionmanagment.domain.*;
import questionmanagment.repository.*;

import java.io.*;

public class UpdateShortAnswerQuestionController implements IUpdateQuestionController {

    private final QuestionRepository questionRepo;
    private final IQuestionFileGenerator fileGenerator;
    private final QuestionValidator validator;

    public UpdateShortAnswerQuestionController(
            QuestionRepository questionRepo, IQuestionFileGenerator fileGenerator, QuestionValidator validator) {
        Preconditions.noneNull(questionRepo, validator, fileGenerator);

        this.questionRepo = questionRepo;
        this.fileGenerator = fileGenerator;
        this.validator = validator;
    }

    @Override
    public Iterable<Question> listQuestions() {
        return questionRepo.findAllByType(QuestionType.SHORT_ANSWER);
    }

    public Question updateQuestion(ShortAnswerQuestion shortAnswerQuestion, String question,
                                Float quotation, Float penalty, String correctAnswer) {

        File file = fileGenerator.generateShortAnswerQuestion(question, quotation, penalty, correctAnswer);

        if (validator.validate(file.getPath()) == null)
            throw new IllegalArgumentException("Invalid Question");

        ShortAnswerQuestion newQuestion = shortAnswerQuestion.updateQuestion(
                question, quotation, penalty, correctAnswer);

        return questionRepo.save(newQuestion);
    }
}
