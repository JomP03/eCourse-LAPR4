package usermanagement.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import usermanagement.domain.ECoursePasswordPolicy;

public class ECoursePasswordPolicyTest {

    private ECoursePasswordPolicy passwordPolicy;

    @Before
    public void setUp() {
        passwordPolicy = new ECoursePasswordPolicy();
    }

    @Test
    public void isSatisfiedBy_NullPassword_ReturnsFalse() {
        // ARRANGE
        String password = null;

        // ACT
        boolean result = passwordPolicy.isSatisfiedBy(password);

        // ASSERT
        Assert.assertFalse(result);
    }

    @Test
    public void isSatisfiedBy_EmptyPassword_ReturnsFalse() {
        // ARRANGE
        String password = "";

        // ACT
        boolean result = passwordPolicy.isSatisfiedBy(password);

        // ASSERT
        Assert.assertFalse(result);
    }

    @Test
    public void isSatisfiedBy_ShortPassword_ReturnsFalse() {
        // ARRANGE
        String password = "abc";

        // ACT
        boolean result = passwordPolicy.isSatisfiedBy(password);

        // ASSERT
        Assert.assertFalse(result);
    }

    @Test
    public void isSatisfiedBy_PasswordWithoutDigit_ReturnsFalse() {
        // ARRANGE
        String password = "Abcdef";

        // ACT
        boolean result = passwordPolicy.isSatisfiedBy(password);

        // ASSERT
        Assert.assertFalse(result);
    }

    @Test
    public void isSatisfiedBy_PasswordWithoutCapitalLetter_ReturnsFalse() {
        // ARRANGE
        String password = "abcdefg1";

        // ACT
        boolean result = passwordPolicy.isSatisfiedBy(password);

        // ASSERT
        Assert.assertFalse(result);
    }

    @Test
    public void isSatisfiedBy_ValidPassword_ReturnsTrue() {
        // ARRANGE
        String password = "Abcdefg1";

        // ACT
        boolean result = passwordPolicy.isSatisfiedBy(password);

        // ASSERT
        Assert.assertTrue(result);
    }
}
