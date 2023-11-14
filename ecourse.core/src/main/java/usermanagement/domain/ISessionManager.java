package usermanagement.domain;

import java.util.Optional;

import auth.SessionToken;
import ecourseusermanagement.domain.ECourseUser;

/**
 * The interface for a Session manager.
 */
public interface ISessionManager {
    /**
     * Perform login.
     *
     * @param username the username
     * @param password the password
     * @return the optional with the session token if the login was successful, empty otherwise
     */
    Optional<SessionToken> login(String username, String password);

    /**
     * Perform logout.
     *
     * @param token the token
     */
    void logout(SessionToken token);

    /**
     * Check if a user is logged in.
     *
     * @param token the token
     * @return the boolean
     */
    boolean isUserLoggedIn(SessionToken token);

    /**
     * Gets logged in user.
     *
     * @param token the token
     * @return the loggedin user
     */
    Optional<ECourseUser> getLoggedInUser(SessionToken token);
}
