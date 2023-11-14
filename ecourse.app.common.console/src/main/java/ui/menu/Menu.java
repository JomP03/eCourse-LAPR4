package ui.menu;

import eapli.framework.actions.Action;
import eapli.framework.validations.Preconditions;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Menu {

    private final Map<Integer, MenuItem> items;

    public Menu() {
        items = new HashMap<>();
    }


    /**
     * Adds a new Menu Item on the menu
     *
     * @param item - new Menu Item
     */
    public void addItem(final int position, final MenuItem item) {
        Preconditions.nonNull(item);

        // Check if the Menu Item is already in the menu
        if (isPositionInUse(position)) {
            throw new IllegalStateException("Menu item already in the Menu");
        }

        // Check if the position is already in use
        if (isItemInMenu(item)) {
            throw new IllegalStateException("Menu item index already in use");
        }

        // Add the Menu Item to the menu
        else {
            items.put(position, item);
        }
    }


    /**
     * Adds a new Menu Item on the menu
     *
     * @param position - position of the new Menu Item
     * @param message - message of the new Menu Item
     * @param action - action of the new Menu Item
     */
    public void addItem(final int position, final String message, final Action action) {
        Preconditions.nonEmpty(message);
        Preconditions.nonNull(message);

        Preconditions.nonNull(action);

        MenuItem item = new MenuItem(message, action);


        // Check if the Menu Item is already in the menu
        if (isPositionInUse(position)) {
            throw new IllegalStateException("Menu item already in the Menu");
        }

        // Check if the position is already in use
        if (isItemInMenu(item)) {
            throw new IllegalStateException("Menu item index already in use");
        }

        // Add the Menu Item to the menu
        else {
            items.put(position, item);
        }
    }


    private boolean isPositionInUse(int position) {
        return items.containsKey(position);
    }


    private boolean isItemInMenu(MenuItem item) {
        return items.containsValue(item);
    }


    /**
     * Menu itens of the menu
     *
     * @return - collection of menu itens
     */
    public Collection<MenuItem> itemsCollection() {
        return items.values();
    }


    /**
     * Menu itens of the menu
     *
     * @return - map of menu itens (position, menu item)
     */
    public Map<Integer, MenuItem> itemsMap() { return items; }


    /**
     * Returns the menu item of the given position
     *
     * @param position - position of the menu item
     * @return - menu item
     */
    public MenuItem item(int position) {
        return items.get(position);
    }

}
