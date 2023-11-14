package ui.components;

import java.util.*;

public class ListSelector<T> extends ListPrinter<T> {

    private int selectedOption;
    private T selectedElement;

    public ListSelector(String message, Collection<T> iterable) {
        super(message, iterable);
    }


    /**
     * Shows the list and asks the user to select an option
     * If the user selects 0, returns false (back option)
     */
    public boolean showAndSelectWithExit() {
        super.printNumeratedList();
        System.out.println("0 - Back\n");
        selectedOption = Console.readOption(1, collection.size(), 0);
        System.out.println();


        if (selectedOption != 0) {
            determineSelectedElement();
            return true;
        }

        return false;
    }


    /**
     * Shows the list and asks the user to select an option
     */
    public void showAndSelect() {
        super.printNumeratedList();
        selectedOption = Console.readOption(1, collection.size());
        System.out.println();
        determineSelectedElement();
    }


    /**
     * Returns the selected option
     *
     * @return the selected option
     */
    public int getSelectedOption() {
        return selectedOption;
    }


    /**
     * Calculates the selected element
     */
    private void determineSelectedElement() {
        if (selectedOption == 0) {
            return;
        }
        int index = 1;
        for (T item : collection) {
            if (index == selectedOption) {
                selectedElement = item;
            }
            index++;
        }
    }


    /**
     * Returns the selected element
     *
     * @return the selected element
     */
    public T getSelectedElement() {
        return selectedElement;
    }
}
