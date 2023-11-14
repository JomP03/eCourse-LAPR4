import appsettings.Application;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.pubsub.EventDispatcher;
import boardcommunication.http.HttpRequestHandler;
import persistence.PersistenceContext;
import usermanagement.domain.ECoursePasswordPolicy;
import usermanagement.domain.SessionManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class ECourseBoardServerApp extends ECourseBaseApp {
    static ServerSocket sock;
    private final int TCP_PORT = Application.settings().getTcpServerPort();
    private final int HTTP_PORT = Application.settings().getHttpServerPort();

    public static void main(String args[]) {
        new ECourseBoardServerApp().run(args);
    }

    @Override
    protected void configureAuthz() {
        AuthzRegistry.configure(
                PersistenceContext.repositories().users(),
                new ECoursePasswordPolicy(),
                new PlainTextEncoder());
    }

    @Override
    protected void doMain(String[] args) {
        Socket clientSocket;
        try {
            //  sock = new ServerSocket(TCP_PORT);
            sock = new ServerSocket(HTTP_PORT);
        } catch (IOException ex) {
            System.out.println("Failed to open server socket");
            System.exit(1);
        }

        //System.out.println("Listening for TCP requests (IPv6/IPv4). Use CTRL+C to terminate the server");
        System.out.println("Listening for HTTP requests");

        SessionManager sessionManager = SessionManager.getInstance();
        while (true) {
            try {
                clientSocket = sock.accept();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            new Thread(new HttpRequestHandler(clientSocket)).start();
            // new Thread(new TcpServerMessageHandler(clientSocket)).start();
        }
    }

    @Override
    protected String appTitle() {
        return "eCourse - Board Server";
    }

    @Override
    protected String appGoodbye() {
        return "Goodbye! See you later!";
    }

    @Override
    protected void doSetupEventHandlers(EventDispatcher dispatcher) {

    }
}
