package ui.components;

public abstract class AbstractUI {


    /**
     * show the US process
     */
    protected abstract boolean doShow();

    /**
     * Title of the US
     *
     * @return the headline of the UI
     */
    public abstract String headline();

    public boolean show() {
        this.messageBox(headline(), ColorCode.BLUE);
        return this.doShow();  // Wants to exit
    }


    /**
     * Maintains the UI loop
     */
    public void mainLoop() {
        boolean wantsToExit;
        do {
            wantsToExit = this.show();
        } while(!wantsToExit);
    }

    public void errorMessage(String message) {
        messageBox("ERROR - " + message, ColorCode.RED);
    }


    /**
     * Shows a success message
     *
     * @param message - the message to show
     */
    public void successMessage(String message) {
        messageBox("SUCCESS - " + message, ColorCode.GREEN);
    }


    /**
     * Shows an error message
     *
     * @param message - the message to show
     */
    public void infoMessage(String message) {
        messageBox("INFO - " + message, ColorCode.YELLOW);
    }


    /**
     * Creates a box with a given headline
     */
    public void messageBox(String headline, ColorCode color) {
        int strLength = headline.length();
        int PADDING_SPACE = 20;
        int dashes = strLength + PADDING_SPACE;
        int totalSpaces = dashes - 2;
        int padding = (totalSpaces - strLength) / 2;

        dash(dashes);
        String paddedString = String.format("%" + padding + "s", "");
        String centeredString = String.format("%s" + color.getValue() + "%s" + ColorCode.RESET.getValue() + "%s" , paddedString, headline, paddedString);
        System.out.printf("|%s|\n", centeredString);
        dash(dashes);
        System.out.println();
    }


    /**
     * Dash for the menu
     */
    private void dash(int dashes) {
        for (int i = 0; i < dashes; i++) {
            System.out.print("=");
        }
        System.out.println();
    }
}
