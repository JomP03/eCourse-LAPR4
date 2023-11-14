import auth.AuthenticationCredentialHandler;
import authz.LoginUI;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.pubsub.EventDispatcher;
import persistence.PersistenceContext;
import ui.ManagerMenuUI;
import usermanagement.domain.ECoursePasswordPolicy;
import usermanagement.domain.ECourseRoles;

public class ECourseManagerApp extends ECourseBaseApp {

    private ECourseManagerApp() {
    }

    public static void main(String[] args) {
        new ECourseManagerApp().run(args);
    }


    @Override
    protected void configureAuthz() {
        AuthzRegistry.configure(PersistenceContext.repositories().users(), new ECoursePasswordPolicy(), new PlainTextEncoder());
    }

    @Override
    protected void doMain(String[] args) {
        boolean wasLoginSuccessful = new LoginUI(new AuthenticationCredentialHandler(), ECourseRoles.MANAGER).show();
        if (wasLoginSuccessful) {
            new ManagerMenuUI().mainLoop();
        }
    }

    @Override
    protected String appTitle() {
        return "eCourse - Manager";
    }

    @Override
    protected String appGoodbye() {
        return "Goodbye! Keep up with the good work!";
    }

    @Override
    protected void doSetupEventHandlers(EventDispatcher dispatcher) {

    }
}
