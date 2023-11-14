package ui.components;

import appsettings.Application;

import java.util.*;

public class ListPrinter<T> {


    private final String message;
    protected final Collection<T> collection;


    public ListPrinter(String message, Collection<T> collection) {
        this.message = message;
        this.collection = collection;
    }


    /**
     * Show the list with numbers
     */
    public void printNumeratedList() {
        System.out.printf("%s\n\n", message);

        int index = 1;
        for (T item : collection) {
            System.out.println(ColorCode.BLUE.getValue() + index + ColorCode.RESET.getValue() + " - " + item);
            index++;
        }
        System.out.println();
    }


    /**
     * Shows the list
     */
    public void showList() {
        System.out.printf("%s\n\n", message);

        for (T item : collection) {
            if (Application.settings().isOperativeSystemLinux())
                System.out.printf("• %s\n", item);
            else
                System.out.printf("- %s\n", item);
        }
        System.out.println();
    }


    public boolean isEmpty() {
        return collection.isEmpty();
    }

}


