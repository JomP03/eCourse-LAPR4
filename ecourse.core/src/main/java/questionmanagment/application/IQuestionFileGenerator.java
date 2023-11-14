package questionmanagment.application;

import questionmanagment.domain.*;

import java.io.*;
import java.util.*;

public interface IQuestionFileGenerator {


    /**
     * Generates a file for a Matching Question
     * @param question the question
     * @param penalty the penalty for wrong answer
     * @param leftOptions the left options
     * @param rightOptions the right options
     * @return a new matching question
     */
    File generateMatchingQuestion(String question, Float quotation, Float penalty, List<String> leftOptions, List<String> rightOptions);


    /**
     * Generates a file for a Multiple Choice Question
     * @param question the question
     * @param options the options
     * @param correctOption the correct option
     * @param penalty the penalty for wrong answer
     * @param quotation the quotation
     * @return a new multiple choice question
     */
    File generateMultipleChoiceQuestion(String question, Float quotation, Float penalty, List<String> options, String correctOption);


    /**
     * Generates a file for a Short Answer Question
     * @param question the question
     * @param correctAnswer the correct answer
     * @param penalty the penalty for wrong answer
     * @param quotation the quotation
     * @return a new short answer question
     */
    File generateShortAnswerQuestion(String question, Float quotation, Float penalty, String correctAnswer);


    /**
     * Generates a file for a Numeric Question
     * @param question the question
     * @param correctAnswer the correct answer
     * @param penalty the penalty for wrong answer
     * @param quotation the quotation
     * @return a new numeric question
     */
    File generateNumericalQuestion(String question, Float quotation, Float penalty, String correctAnswer);


    /**
     * Generates a file for a Missing Word Question
     * @param question the question
     * @param options the options for the missing word
     * @param penalty the penalty for wrong answer
     * @param quotation the quotation
     * @return a new missing word question
     */
    File generateMissingWordQuestion(String question, Float quotation, Float penalty, List<MissingWordOption> options);


    /**
     * Generates a file for a Boolean Question
     * @param question the question
     * @param correctAnswer the correct answer
     * @param penalty the penalty for wrong answer
     * @param quotation the quotation
     * @return a new true or false question
     */
    File generateBooleanQuestion(String question, Float quotation, Float penalty, boolean correctAnswer);
}
