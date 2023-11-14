package auth;

import java.util.Random;

/**
 * Class responsible for generating session tokens
 */
public class SessionTokenGenerator {

    private static final String PREFIX = "st_";
    private static final int TOKEN_LENGTH = 15;

    /**
     * Generates a random session token
     *
     * @return a random session token
     */
    public static SessionToken generateToken() {
        StringBuilder tokenBuilder = new StringBuilder(PREFIX);
        Random random = new Random();

        final int leftChars = TOKEN_LENGTH - PREFIX.length();

        // Generate random characters
        for (int i = 0; i < leftChars; i++) {
            int randomInt = random.nextInt(26);
            char randomChar = (char) ('a' + randomInt);
            tokenBuilder.append(randomChar);
        }

        return new SessionToken(tokenBuilder.toString());
    }
}
