package ui.components;

/**
 * Class used just to perform a sleep.
 */
public class Sleeper {


    /**
     * Sleep.
     *
     * @param millis the time to sleep in milliseconds
     */
    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
