package ui.question.widgets;

import ui.components.Console;

import java.util.*;

public abstract class QuestionWidget {

    public String question;
    public Float quotation;
    public Float penalty;

    public abstract void show();

    /**
     * Reads a list of options
     * @return the options
     */
    protected List<String> readOptions() {
        List<String> options = new ArrayList<>();
        String currentOption;
        do {
            currentOption = Console.readLine("Option (blank to end): ");

            if(!currentOption.trim().isEmpty()) {
                options.add(currentOption);
            }


            else if (options.size() < 1)
                System.out.println("There must be at least one option.\n");

            else {
                System.out.println();
                break;
            }

        } while(true);

        return options;
    }
}
