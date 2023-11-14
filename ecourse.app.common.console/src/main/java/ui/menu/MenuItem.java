package ui.menu;

import eapli.framework.actions.Action;

public class MenuItem {

    private final String message;
    private final Action action;

    public MenuItem(final String message, final Action action) {
        this.message = message;
        this.action = action;
    }


    /**
     * Message of the Menu Item
     *
     * @return - message of the Menu Item
     */
    public String message() {
        return message;
    }


    /**
     * Action of the Menu Item
     *
     * @return - action of the Menu Item
     */
    public Action action() {
        return action;
    }


    /**
     * Shows the Menu Item
     *
     * @return - message of the Menu Item
     */
    @Override
    public String toString() {
        return message;
    }
}
