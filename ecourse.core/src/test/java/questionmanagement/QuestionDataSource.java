package questionmanagement;

import questionmanagment.domain.*;

import java.io.*;
import java.util.*;

public class QuestionDataSource {

    public final static File multipleChoiceFile =
            new File("src/test/java/questionmanagement/multiple_choice_question.txt");
    public final static File shortAnswerFile =
            new File("src/test/java/questionmanagement/short_answer_question.txt");
    public final static File matchingFile =
            new File("src/test/java/questionmanagement/matching_question.txt");
    public final static File numericalFile =
            new File("src/test/java/questionmanagement/numerical_question.txt");
    public final static File booleanFile =
            new File("src/test/java/questionmanagement/boolean_question.txt");
    public final static File missingWordFile =
            new File("src/test/java/questionmanagement/missing_word_question.txt");

    /**
     * Returns a Multiple Choice question for testing purposes.
     *
     * @return a multiple choice quesiton
     */
    public static MultipleChoiceQuestion multipleChoiceQuestion1() {
        return new MultipleChoiceQuestion("What is the capital of Portugal?",
                List.of("Lisbon", "Madrid", "Paris", "Berlin", "Rome"),
                "Listbon", 10f, 10f);
    }

    /**
     * Returns a Multiple Choice question for testing purposes.
     *
     * @return a multiple choice quesiton
     */
    public static MultipleChoiceQuestion multipleChoiceQuestion2() {
        return new MultipleChoiceQuestion("What is the capital of Spain?",
                List.of("Lisbon", "Madrid", "Paris", "Berlin", "Rome"),
                "Madrid", 10f, 10f);
    }


    /**
     * Returns a Short Answwer question for testing purposes.
     *
     * @return a short answer quesiton
     */
    public static ShortAnswerQuestion shortAnswerQuestion1() {
        return new ShortAnswerQuestion("Which country has the largest population in the world?",
                "China", 10f, 10f );
    }

    /**
     * Returns a Short Answwer question for testing purposes.
     *
     * @return a short answer quesiton
     */
    public static ShortAnswerQuestion shortAnswerQuestion2() {
        return new ShortAnswerQuestion("Which country has the largest area in the world?",
                "Russia", 10f, 10f );
    }


    /**
     * Returns a Matching question for testing purposes.
     *
     * @return a matching quesiton
     */
    public static MatchingQuestion matchingQuestion1() {
        return new MatchingQuestion("Match the following countries with their capitals",
                10f, 10f,
                List.of("Portugal", "Spain", "France", "Germany", "Italy"),
                List.of("Lisbon", "Madrid", "Paris", "Berlin", "Rome"));
    }

    /**
     * Returns a Matching question for testing purposes.
     *
     * @return a matching quesiton
     */
    public static MatchingQuestion matchingQuestion2() {
        return new MatchingQuestion("Match the following football clubes " +
                "with their respective league",
                10f, 10f,
                List.of("FC Porto", "Manchester City", "Juventus", "Bayern Munich", "Paris Saint-Germain"),
                List.of("Liga Nos", "Premier League", "Serie A", "Bundesliga", "Ligue UberEats"));
    }


    /**
     * Returns a Numerical question for testing purposes.
     *
     * @return a numerical quesiton
     */
    public static NumericalQuestion numericalQuestion1() {
        return new NumericalQuestion("What is the value of PI?",
                "3.14", 10f, 10f);
    }

    /**
     * Returns a Numerical question for testing purposes.
     *
     * @return a numerical quesiton
     */
    public static NumericalQuestion numericalQuestion2() {
        return new NumericalQuestion("What is the value of the square root of 2?",
                "1.41", 10f, 10f);
    }


    /**
     * Returns a Missing Word question for testing purposes.
     *
     * @return a missing word quesiton
     */
    public static MissingWordQuestion missingWordQuestion1() {
        return new MissingWordQuestion("The [[n]] is the largest organ in the human body.",
                List.of(
                new MissingWordOption(Arrays.asList("brain", "heart", "skin"), "skin")),
                2.2f, 0.7f);
    }

    /**
     * Returns a Missing Word question for testing purposes.
     *
     * @return a missing word quesiton
     */
    public static MissingWordQuestion missingWordQuestion2() {
        return new MissingWordQuestion("An apple a day keeps the [[n]] away." +
        "Having [[n]] of the dark is a common phobia.",
                Arrays.asList(
                        new MissingWordOption(Arrays.asList("teacher", "dentist", "police"), "doctor"),
                        new MissingWordOption(Arrays.asList("happiness", "anger", "sadness"), "fear" )),
                2.2f, 0.7f);
    }


    /**
     * Returns a Boolean question for testing purposes.
     *
     * @return a boolean quesiton
     */
    public static BooleanQuestion booleanQuestion1() {
        return new BooleanQuestion("The capital of Portugal is Lisbon.",
                true, 10f, 10f);
    }

    /**
     * Returns a Boolean question for testing purposes.
     *
     * @return a boolean quesiton
     */
    public static BooleanQuestion booleanQuestion2() {
        return new BooleanQuestion("The capital of Spain is Madrid.",
                true, 10f, 10f);
    }
}
