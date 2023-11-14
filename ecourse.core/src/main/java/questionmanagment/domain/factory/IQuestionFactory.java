package questionmanagment.domain.factory;

import questionmanagment.domain.*;

import java.util.*;

public interface IQuestionFactory {


    /**
     * Creates a Matching Question
     * @param question the question
     * @param penalty the penalty for wrong answer
     * @param leftOptions the left options
     * @param rightOptions the right options
     * @return a new matching question
     */
    MatchingQuestion createMatchingQuestion(String question, Float quotation, Float penalty, List<String> leftOptions, List<String> rightOptions);


    /**
     * Creates a Multiple Choice Question
     * @param question the question
     * @param options the options
     * @param correctOption the correct option
     * @param penalty the penalty for wrong answer
     * @param quotation the quotation
     * @return a new multiple choice question
     */
    MultipleChoiceQuestion createMultipleChoiceQuestion(String question, Float quotation, Float penalty, List<String> options, String correctOption);


    /**
     * Creates a Short Answer Question
     * @param question the question
     * @param correctAnswer the correct answer
     * @param penalty the penalty for wrong answer
     * @param quotation the quotation
     * @return a new short answer question
     */
    ShortAnswerQuestion createShortAnswerQuestion(String question, Float quotation, Float penalty, String correctAnswer);


    /**
     * Creates a Numeric Question
     * @param question the question
     * @param correctAnswer the correct answer
     * @param penalty the penalty for wrong answer
     * @param quotation the quotation
     * @return a new numeric question
     */
    NumericalQuestion createNumericalQuestion(String question, Float quotation, Float penalty, String correctAnswer);


    /**
     * Creates a Missing Word Question
     * @param question the question
     * @param options the options for the missing word
     * @param penalty the penalty for wrong answer
     * @param quotation the quotation
     * @return a new missing word question
     */
    MissingWordQuestion createMissingWordQuestion(String question, Float quotation, Float penalty, List<MissingWordOption> options);


    /**
     * Creates a True or False Question
     * @param question the question
     * @param correctAnswer the correct answer
     * @param penalty the penalty for wrong answer
     * @param quotation the quotation
     * @return a new true or false question
     */
    BooleanQuestion createBooleanQuestion(String question, Float quotation, Float penalty, boolean correctAnswer);
}
