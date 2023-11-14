import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.pubsub.EventDispatcher;
import persistence.PersistenceContext;
import usermanagement.domain.ECoursePasswordPolicy;

// Class used to bootstrap the necessary data for the application to run (add one manager so that users can be added)
public class ECourseBootstrap extends ECourseBaseApp {

    private ECourseBootstrap() {
    }

    public static void main(String[] args) {
        new ECourseBootstrap().run(args);
    }


    @Override
    protected void configureAuthz() {
        AuthzRegistry.configure(PersistenceContext.repositories().users(), new ECoursePasswordPolicy(), new PlainTextEncoder());
    }

    @Override
    protected void doMain(String[] args) {
        new ECourseBootstrapper().execute();
    }

    @Override
    protected String appTitle() {
        return "Bootstrapping eCourse data";
    }

    @Override
    protected String appGoodbye() {
        return "eCourse Bootstrapping data app has finished!";
    }

    @Override
    protected void doSetupEventHandlers(EventDispatcher dispatcher) {

    }
}
