package questionmanagment.domain.factory;

import eapli.framework.validations.*;
import questionmanagment.application.IQuestionFileGenerator;
import questionmanagment.application.QuestionValidator;
import questionmanagment.domain.*;
import questionmanagment.domain.factory.IQuestionFactory;

import java.io.*;
import java.util.*;

public class QuestionFactory implements IQuestionFactory {

    private final QuestionValidator validator;
    private final IQuestionFileGenerator fileGenerator;

    public QuestionFactory(QuestionValidator validator, IQuestionFileGenerator fileGenerator) {
        Preconditions.noneNull(validator, fileGenerator);
        this.validator = validator;

        Preconditions.noneNull(validator, fileGenerator);
        this.fileGenerator = fileGenerator;
    }


    @Override
    public MatchingQuestion createMatchingQuestion(String question, Float quotation, Float penalty, List<String> leftOptions, List<String> rightOptions) {
        File file = fileGenerator.generateMatchingQuestion(question, quotation, penalty, leftOptions, rightOptions);

        MatchingQuestion newQuestion = (MatchingQuestion) validator.validate(file.getPath());
        if (newQuestion == null)
            throw new IllegalArgumentException("Invalid Question");

        return newQuestion;
    }


    @Override
    public MultipleChoiceQuestion createMultipleChoiceQuestion(String question, Float quotation, Float penalty, List<String> options, String correctOption) {
        File file = fileGenerator.generateMultipleChoiceQuestion(question, quotation, penalty, options, correctOption);

        MultipleChoiceQuestion newQuestion = (MultipleChoiceQuestion) validator.validate(file.getPath());
        if (newQuestion == null)
            throw new IllegalArgumentException("Invalid Question");

        return newQuestion;
    }


    @Override
    public ShortAnswerQuestion createShortAnswerQuestion(String question, Float quotation, Float penalty, String correctAnswer) {
        File file = fileGenerator.generateShortAnswerQuestion(question, quotation, penalty, correctAnswer);

        ShortAnswerQuestion newQuestion = (ShortAnswerQuestion) validator.validate(file.getPath());
        if (newQuestion == null)
            throw new IllegalArgumentException("Invalid Question");

        return newQuestion;
    }


    @Override
    public NumericalQuestion createNumericalQuestion(String question, Float quotation, Float penalty, String correctAnswer) {
        File file = fileGenerator.generateNumericalQuestion(question, quotation, penalty, correctAnswer);

        NumericalQuestion newQuestion = (NumericalQuestion) validator.validate(file.getPath());
        if (newQuestion == null)
            throw new IllegalArgumentException("Invalid Question");

        return newQuestion;
    }


    @Override
    public MissingWordQuestion createMissingWordQuestion(String question, Float quotation, Float penalty, List<MissingWordOption> options) {
        File file = fileGenerator.generateMissingWordQuestion(question, quotation, penalty, options);

        MissingWordQuestion newQuestion = (MissingWordQuestion) validator.validate(file.getPath());
        if (newQuestion == null)
            throw new IllegalArgumentException("Invalid Question");

        return newQuestion;
    }


    @Override
    public BooleanQuestion createBooleanQuestion(String question, Float quotation, Float penalty, boolean correctAnswer) {
        File file = fileGenerator.generateBooleanQuestion(question, quotation, penalty, correctAnswer);

        BooleanQuestion newQuestion = (BooleanQuestion) validator.validate(file.getPath());
        if (newQuestion == null)
            throw new IllegalArgumentException("Invalid Question");

        return newQuestion;
    }
}
