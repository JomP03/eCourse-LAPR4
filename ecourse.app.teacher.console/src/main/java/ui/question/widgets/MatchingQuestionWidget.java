package ui.question.widgets;

import ui.components.Console;

import java.util.*;

public class MatchingQuestionWidget extends QuestionWidget {

    public String question;
    public Float quotation;
    public Float penalty;
    public List<String> leftOptions;
    public List<String> rightOptions;

    @Override
    public void show() {
        question = Console.readNonBlankLine("Question: ", "Question can't be empty.");
        System.out.println();

        penalty = Console.readPositiveFloat("Penalty: ");
        System.out.println();

        quotation = Console.readPositiveFloat("Quotation: ");
        System.out.println();

        System.out.println("You will now input the keys and values for the matching question.");
        System.out.println("Please insert them in the same order.\n");
        System.out.println("Left Options - The keys to match");
        leftOptions = readOptions();
        System.out.println("Right Options - The Values to be matched");
        rightOptions = readOptions();
    }
}
