package ecourseusermanagement.domain.ecourseuserbuilders;

import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import ecourseusermanagement.domain.ECourseUser;
import org.junit.Test;
import usermanagement.domain.ECourseRoles;
import usermanagement.domain.UserBuilderHelper;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ManagerBuilderTest {

    @Test
    public void ensureManagerIsBuiltWithValidSystemUser() {
        // Arrange
        ManagerBuilder managerBuilder = new ManagerBuilder();
        // Create a SystemUser
        final Set<Role> roles = new HashSet<>();
        roles.add(ECourseRoles.MANAGER);
        SystemUserBuilder builder = UserBuilderHelper.builder();
        builder.withUsername("username")
                .withPassword("pwdBigTest1")
                .withEmail("man@gmail.com")
                .withName("mann", "test").withRoles(roles);
        SystemUser systemUser = builder.build();

        // Act
        ECourseUser eCourseUser = managerBuilder.withSystemUser(systemUser).build();

        // Assert
        assertNotNull(eCourseUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureManagerIsNotBuiltWithIncorrectRole() {
        // Arrange
        ManagerBuilder managerBuilder = new ManagerBuilder();
        // Create a SystemUser
        final Set<Role> roles = new HashSet<>();
        roles.add(ECourseRoles.STUDENT);
        SystemUserBuilder builder = UserBuilderHelper.builder();
        builder.withUsername("username")
                .withPassword("pwdBigTest1")
                .withEmail("notman@gmail.com")
                .withName("mann", "test").withRoles(roles);
        SystemUser systemUser = builder.build();

        // Act and Assert
        managerBuilder.withSystemUser(systemUser).build();
    }
}