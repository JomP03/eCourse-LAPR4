package ui.question.widgets;

import ui.components.Console;

public class NumericalQuestionWidget extends QuestionWidget {

    public String question;
    public Float quotation;
    public Float penalty;
    public String correctOption;

    @Override
    public void show() {
        question = Console.readNonBlankLine("Question: ", "Question can't be empty.");
        System.out.println();

        quotation = Console.readFloat("Quotation: ");
        System.out.println();

        penalty = Console.readFloat("Penalty: ");
        System.out.println();

        correctOption = Console.readLine("Correct Answer: ");
        System.out.println();
    }
}
