package ui.question.widgets;

import questionmanagment.domain.*;
import ui.components.Console;

import java.util.*;
import java.util.regex.*;

public class MissingWordQuestionWidget extends QuestionWidget {

    public String question;
    public Float quotation;
    public Float penalty;
    public List<MissingWordOption> missingWordOptions;

    @Override
    public void show() {
        System.out.println("Introduce the occurrence of a missing word between ordered brackets. " +
                "Example: \"The capital of Portugal is [[n]] and the capital of Spain is [[n]]\"\n");


        do {
            question = Console.readNonBlankLine("Question: ", "Question can't be empty.");
            System.out.println();

            missingWordOptions = readOptions(question);
            if (missingWordOptions == null)
                System.out.println("There are no missing words in the question. Please try again.");
            else
                break;
        } while(true);

        quotation = Console.readPositiveFloat("Quotation: ");
        System.out.println();

        penalty = Console.readPositiveFloat("Penalty: ");
        System.out.println();
    }


    private List<MissingWordOption> readOptions(String question) {
        // Detect the occurrences missing words
        Pattern pattern = Pattern.compile("\\[\\[(n)]]");
        Matcher matcher = pattern.matcher(question);

        // Read the options for each missing word
        List<MissingWordOption> missingWordOptions = new ArrayList<>();

        // If there are no missing words, return null

        boolean found = matcher.find();

        if (!found)
            return null;

        int index = 1;
        do {
            System.out.println("Missing word " + index++);
            List<String> options = readOptions();
            int correctOption = Console.readOption(1, options.size());
            System.out.println();
            missingWordOptions.add(new MissingWordOption(options, options.get(correctOption - 1)));

            found = matcher.find();
        } while (found);

        return missingWordOptions;
    }
}
