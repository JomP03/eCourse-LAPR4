package boardcommunication.http.helpers;

import auth.SessionToken;
import boardcommunication.http.HTTPmessage;
import ecourseusermanagement.domain.ECourseUser;
import usermanagement.domain.SessionManager;

import java.util.Optional;

public class RequestUserProvider {
    private final SessionManager sessionManager = SessionManager.getInstance();

    public Optional<ECourseUser> getUserFromRequest(HTTPmessage request) {
        SessionToken sessionToken;
        // Get the Session token from the request header
        try {
            sessionToken = new SessionToken(request.getAuthorization());
        } catch (Exception e) {
            return Optional.empty();
        }
        // Get the user from the session manager
        return sessionManager.getLoggedInUser(sessionToken);
    }
}
