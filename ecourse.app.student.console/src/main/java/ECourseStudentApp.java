import auth.AuthenticationCredentialHandler;
import authz.LoginUI;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.pubsub.EventDispatcher;
import persistence.PersistenceContext;
import ui.StudentMenuUI;
import usermanagement.domain.ECoursePasswordPolicy;
import usermanagement.domain.ECourseRoles;

public class ECourseStudentApp extends ECourseBaseApp {

    private ECourseStudentApp() {
    }

    public static void main(String[] args) {
        new ECourseStudentApp().run(args);
    }


    @Override
    protected void configureAuthz() {
        AuthzRegistry.configure(PersistenceContext.repositories().users(), new ECoursePasswordPolicy(), new PlainTextEncoder());
    }

    @Override
    protected void doMain(String[] args) {
        boolean wasLoginSuccessful = new LoginUI(new AuthenticationCredentialHandler(), ECourseRoles.STUDENT).show();
        if (wasLoginSuccessful) {
            new StudentMenuUI().mainLoop();
        }
    }

    @Override
    protected String appTitle() {
        return "eCourse - Student";
    }

    @Override
    protected String appGoodbye() {
        return "Goodbye! Good luck with your studies!";
    }

    @Override
    protected void doSetupEventHandlers(EventDispatcher dispatcher) {

    }
}
