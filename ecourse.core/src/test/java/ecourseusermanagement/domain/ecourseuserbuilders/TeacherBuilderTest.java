package ecourseusermanagement.domain.ecourseuserbuilders;

import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.domain.UserAcronym;
import ecourseusermanagement.domain.UserBirthdate;
import ecourseusermanagement.domain.UserTaxNumber;
import org.junit.Test;
import usermanagement.domain.ECourseRoles;
import usermanagement.domain.UserBuilderHelper;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TeacherBuilderTest {

    @Test
    public void ensureTeacherIsBuiltWithValidSystemUser() {
        // Arrange
        TeacherBuilder teacherBuilder = new TeacherBuilder();
        // Create a SystemUser
        final Set<Role> roles = new HashSet<>();
        roles.add(ECourseRoles.TEACHER);
        SystemUserBuilder builder = UserBuilderHelper.builder();
        builder.withUsername("username")
                .withPassword("pwdBigTest1")
                .withEmail("tea@gmail.com")
                .withName("teach", "test").withRoles(roles);
        SystemUser systemUser = builder.build();
        // Create a UserBirthdate
        final var birth = LocalDate.of(2003, 3, 16);
        UserBirthdate userBirthdate = new UserBirthdate(birth);
        UserTaxNumber userTaxNumber = new UserTaxNumber("243989890");
        UserAcronym userAcronym = new UserAcronym("ANB");

        // Act
        ECourseUser user = teacherBuilder.withSystemUser(systemUser)
                .withUserAcronym(userAcronym)
                .withUserBirthDate(userBirthdate)
                .withUserTaxNumber(userTaxNumber)
                .build();

        // Assert
        assertNotNull(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureTeacherIsNotBuiltWithIncorrectRole() {
        // Arrange
        TeacherBuilder teacherBuilder = new TeacherBuilder();
        // Create a SystemUser
        final Set<Role> roles = new HashSet<>();
        roles.add(ECourseRoles.STUDENT);
        SystemUserBuilder builder = UserBuilderHelper.builder();
        builder.withUsername("username")
                .withPassword("pwdBigTest1")
                .withEmail("notTea@gmail.com")
                .withName("teach", "test").withRoles(roles);

        SystemUser systemUser = builder.build();

        // Act and Assert
        teacherBuilder.withSystemUser(systemUser).build();
    }
}
