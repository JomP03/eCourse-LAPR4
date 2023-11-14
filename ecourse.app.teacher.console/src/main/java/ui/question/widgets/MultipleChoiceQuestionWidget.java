package ui.question.widgets;

import ui.components.Console;

import java.util.*;

public class MultipleChoiceQuestionWidget extends QuestionWidget {

    public String question;
    public Float quotation;
    public Float penalty;
    public List<String> options;
    public int correctOption;

    @Override
    public void show() {
        question = Console.readNonBlankLine("Question: ", "Question can't be empty.");
        System.out.println();

        options = readOptions();

        correctOption = Console.readOption(1, options.size());
        System.out.println();

        penalty = Console.readPositiveFloat("Penalty: ");
        System.out.println();

        quotation = Console.readPositiveFloat("Quotation: ");
        System.out.println();
    }
}
