package usermanagement.domain;

import eapli.framework.infrastructure.authz.application.PasswordPolicy;
import eapli.framework.strings.util.StringPredicates;

public class ECoursePasswordPolicy implements PasswordPolicy {
    private static final int MINIMUM_PASSWORD_LENGTH = 6;

    @Override
    public boolean isSatisfiedBy(final String rawPassword) {

        // Check if password is null or empty
        if (StringPredicates.isNullOrEmpty(rawPassword)) {
            return false;
        }

        // Check if password has the minimum length
        if (rawPassword.length() < MINIMUM_PASSWORD_LENGTH) {
            return false;
        }

        // Check if password has at least one digit
        if (!StringPredicates.containsDigit(rawPassword)) {
            return false;
        }

        // Check if password has at least one capital letter
        return StringPredicates.containsCapital(rawPassword);
    }
}
