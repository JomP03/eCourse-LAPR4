package auth;

import java.util.Objects;

/**
 * Class that represents a session token.
 */
public class SessionToken {
    private final String token;
    private static final int TOKEN_LENGTH = 15;

    /**
     * Instantiates a new Session token.
     *
     * @param token the token
     */
    public SessionToken(String token) {
        // Rule 1: Token length must be 15 characters long
        if (token.length() != TOKEN_LENGTH) {
            throw new IllegalArgumentException("Token must be 15 characters long");
        }

        // Rule 2: Token must start with "st_"
        if (!token.startsWith("st_")) {
            throw new IllegalArgumentException("Token must start with \"st_\"");
        }

        this.token = token;
    }

    @Override
    public String toString() {
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionToken that = (SessionToken) o;
        return Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }
}
