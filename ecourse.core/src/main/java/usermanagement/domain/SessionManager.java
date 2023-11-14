package usermanagement.domain;

import auth.AuthenticationCredentialHandler;
import auth.CredentialHandler;
import auth.SessionToken;
import auth.SessionTokenGenerator;
import ecourseusermanagement.application.UserSessionService;
import ecourseusermanagement.domain.ECourseUser;
import persistence.PersistenceContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SessionManager implements ISessionManager {
    private final CredentialHandler handler = new AuthenticationCredentialHandler();
    private final UserSessionService userSessionService =
            new UserSessionService(PersistenceContext.repositories().eCourseUsers());

    // Ensure that only one instance of SessionManager exists
    private static SessionManager instance = null;

    private SessionManager() {
    }

    private final Map<SessionToken, ECourseUser> loggedInUsers = new HashMap<>();

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    private final Object loginLock = new Object();

    @Override
    public Optional<SessionToken> login(String username, String password) {
        ECourseUser user;
        synchronized (loginLock) {
            if (!handler.authenticated(username, password, ECourseRoles.MANAGER, ECourseRoles.TEACHER, ECourseRoles.STUDENT)) {
                return Optional.empty();
            }
            // Get the logged user
            user = userSessionService.getLoggedUser().isPresent() ? userSessionService.getLoggedUser().get() : null;
        }

        if (user == null) {
            return Optional.empty();
        }
        // Generate a token for the user
        SessionToken token = SessionTokenGenerator.generateToken();
        while (loggedInUsers.containsKey(token)) {
            // Generate a new token if the token already exists
            token = SessionTokenGenerator.generateToken();
        }
        loggedInUsers.put(token, user);

        return Optional.of(token);
    }

    @Override
    public void logout(SessionToken token) {
        loggedInUsers.remove(token);
    }

    @Override
    public boolean isUserLoggedIn(SessionToken token) {
        return loggedInUsers.containsKey(token);
    }

    @Override
    public Optional<ECourseUser> getLoggedInUser(SessionToken token) {
        return Optional.ofNullable(loggedInUsers.get(token));
    }
}
