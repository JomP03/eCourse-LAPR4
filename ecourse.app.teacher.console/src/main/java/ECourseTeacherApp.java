import auth.AuthenticationCredentialHandler;
import authz.LoginUI;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.pubsub.EventDispatcher;
import persistence.PersistenceContext;
import ui.TeacherMenuUI;
import usermanagement.domain.ECoursePasswordPolicy;
import usermanagement.domain.ECourseRoles;

public class ECourseTeacherApp extends ECourseBaseApp {

    private ECourseTeacherApp() {
    }

    public static void main(String[] args) {
        new ECourseTeacherApp().run(args);
    }


    @Override
    protected void configureAuthz() {
        AuthzRegistry.configure(PersistenceContext.repositories().users(), new ECoursePasswordPolicy(), new PlainTextEncoder());
    }

    @Override
    protected void doMain(String[] args) {
        boolean wasLoginSuccessful = new LoginUI(new AuthenticationCredentialHandler(), ECourseRoles.TEACHER).show();
        if (wasLoginSuccessful) {
            new TeacherMenuUI().mainLoop();
        }
    }

    @Override
    protected String appTitle() {
        return "eCourse - Teacher";
    }

    @Override
    protected String appGoodbye() {
        return "Goodbye! Keep up with the good work!";
    }

    @Override
    protected void doSetupEventHandlers(EventDispatcher dispatcher) {

    }
}
