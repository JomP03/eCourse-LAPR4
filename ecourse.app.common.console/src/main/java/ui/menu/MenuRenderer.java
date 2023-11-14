package ui.menu;

import eapli.framework.validations.Preconditions;
import ui.components.Console;

import java.util.Map;

public class MenuRenderer {

    public enum PullOutActions {
        EXIT ("Exit"),
        BACK ("Back");

        private final String message;

        PullOutActions(String message) {
            this.message = message;
        }

        public String message() {
            return message;
        }
    }

    private final Menu menu;
    private MenuItem exitItem;

    public MenuRenderer(final Menu menu) {
        Preconditions.nonNull(menu);
        this.menu = menu;
    }


    /**
     * Presents the menu to the user, lets him select an option and executes the action associated with that option.
     */
    public boolean render(String pullOutMessage) {
        doRender(pullOutMessage);
        MenuItem item = readOption();
        item.action().execute();

        return (item.action() instanceof PullOutAction);
    }


    private void doRender(String pullOutMessage) {
        for (Map.Entry<Integer, MenuItem> item : this.menu.itemsMap().entrySet()) {
            System.out.println(item.getKey() + " - " + item.getValue().message());
        }

        exitItem = new MenuItem(pullOutMessage, new PullOutAction());
        System.out.printf("\n0 - %s\n\n", exitItem.message());
    }


    private MenuItem readOption() {
        int option = Console.readOption(0, menu.itemsCollection().size());
        if (option == 0) {
            return exitItem;
        }
        System.out.println();

        return menu.item(option);
    }

}
