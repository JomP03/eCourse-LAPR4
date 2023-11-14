import eapli.framework.infrastructure.pubsub.EventDispatcher;

import java.io.IOException;

public class ECourseBoardClientApp extends ECourseBaseApp {

    private ECourseBoardClientApp() {
    }

    public static void main(String[] args) {
        new ECourseBoardClientApp().run(args);
    }

    @Override
    protected void configureAuthz() {
        // Not needed for the client (server responsibility)
    }

    @Override
    protected void doMain(String[] args) {
        BoardClient client = new BoardClient();
        try {
            do {
                client.sendRequest();
            } while (true);
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

    @Override
    protected String appTitle() {
        return "eCourse - Board Client";
    }

    @Override
    protected String appGoodbye() {
        return "Goodbye! See you later!";
    }

    @Override
    protected void doSetupEventHandlers(EventDispatcher dispatcher) {
    }
}
