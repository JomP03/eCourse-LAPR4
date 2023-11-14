package ecourseusermanagement.domain;

import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import usermanagement.domain.ECourseRoles;
import usermanagement.domain.UserBuilderHelper;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

class ECourseUserTest {

    @Nested
    class StudentTest {
        private SystemUser systemUser;
        private UserTaxNumber userTaxNumber;
        private UserBirthdate userBirthdate;
        private UserMechanographicNumber userMechanographicNumber;

        @BeforeEach
        void setUp() {
            // Create a SystemUser
            final Set<Role> roles = new HashSet<>();
            roles.add(ECourseRoles.STUDENT);
            SystemUserBuilder builder = UserBuilderHelper.builder();
            builder.withUsername("username")
                    .withPassword("pwdBigTest1")
                    .withEmail("stu@gmail.com")
                    .withName("stud", "test").withRoles(roles);
            systemUser = builder.build();

            // Create a UserBirthdate
            final var birth = LocalDate.of(2003, 3, 16);
            userBirthdate = new UserBirthdate(birth);

            userTaxNumber = new UserTaxNumber("243989890");

            // Create a UserMechanographicNumber
            final var currentYear = LocalDate.now().getYear();
            userMechanographicNumber = new UserMechanographicNumber(currentYear + "00001");
        }

        @Test
        void ensureValidStudentIsCreated() {
            // Act
            ECourseUser student = new ECourseUser(systemUser, userTaxNumber, userBirthdate, userMechanographicNumber);

            // Assert
            Assertions.assertNotNull(student);
        }

        @Test
        void ensureStudentWithNoSystemUserIsNotCreated() {
            // Act & Assert
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> new ECourseUser(null, userTaxNumber, userBirthdate, userMechanographicNumber));
        }

        @Test
        void ensureStudentWithNoUserTaxNumberIsNotCreated() {
            // Act & Assert
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> new ECourseUser(systemUser, null, userBirthdate, userMechanographicNumber));
        }

        @Test
        void ensureStudentWithNoUserBirthdateIsNotCreated() {
            // Act & Assert
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> new ECourseUser(systemUser, userTaxNumber, null, userMechanographicNumber));
        }

        @Test
        void ensureStudentWithNoUserMechanographicNumberIsNotCreated() {
            // Arrange
            userMechanographicNumber = null;

            // Act & Assert
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> new ECourseUser(systemUser, userTaxNumber, userBirthdate, userMechanographicNumber));
        }

        @Test
        void ensureStudentWithInvalidSystemUserIsNotCreated() {
            // Arrange
            final Set<Role> roles = new HashSet<>();
            roles.add(ECourseRoles.TEACHER);
            roles.add(ECourseRoles.MANAGER);
            SystemUserBuilder builder = UserBuilderHelper.builder();
            builder.withUsername("username")
                    .withPassword("pwdBigTest1")
                    .withEmail("inv@gmail.com")
                    .withName("inv", "test").withRoles(roles);
            systemUser = builder.build();

            // Act & Assert
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> new ECourseUser(systemUser, userTaxNumber, userBirthdate, userMechanographicNumber));
        }

    }

    @Nested
    class TeacherTest {
        private SystemUser systemUser;
        private UserTaxNumber userTaxNumber;
        private UserBirthdate userBirthdate;
        private UserAcronym userAcronym;

        @BeforeEach
        void setUp() {
            // Create a SystemUser
            final Set<Role> roles = new HashSet<>();
            roles.add(ECourseRoles.TEACHER);
            SystemUserBuilder builder = UserBuilderHelper.builder();
            builder.withUsername("username")
                    .withPassword("pwdBigTest1")
                    .withEmail("tea@gmail.com")
                    .withName("teach", "test").withRoles(roles);
            systemUser = builder.build();

            // Create a UserBirthdate
            final var birth = LocalDate.of(2003, 3, 16);
            userBirthdate = new UserBirthdate(birth);

            userTaxNumber = new UserTaxNumber("243989890");
            userAcronym = new UserAcronym("TST");
        }

        @Test
        void ensureValidTeacherIsCreated() {
            // Act
            ECourseUser teacher = new ECourseUser(systemUser, userTaxNumber, userBirthdate, userAcronym);

            // Assert
            Assertions.assertNotNull(teacher);
        }

        @Test
        void ensureTeacherWithNoSystemUserIsNotCreated() {
            // Act & Assert
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> new ECourseUser(null, userTaxNumber, userBirthdate, userAcronym));
        }

        @Test
        void ensureTeacherWithNoUserTaxNumberIsNotCreated() {
            // Act & Assert
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> new ECourseUser(systemUser, null, userBirthdate, userAcronym));
        }

        @Test
        void ensureTeacherWithNoUserBirthdateIsNotCreated() {
            // Act & Assert
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> new ECourseUser(systemUser, userTaxNumber, null, userAcronym));
        }

        @Test
        void ensureTeacherWithNoUserAcronymIsNotCreated() {
            // Arrange
            userAcronym = null;

            // Act & Assert
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> new ECourseUser(systemUser, userTaxNumber, userBirthdate, userAcronym));
        }

        @Test
        void ensureTeacherWithInvalidSystemUserIsNotCreated() {
            // Arrange
            final Set<Role> roles = new HashSet<>();
            roles.add(ECourseRoles.STUDENT);
            roles.add(ECourseRoles.MANAGER);
            SystemUserBuilder builder = UserBuilderHelper.builder();
            builder.withUsername("username")
                    .withPassword("pwdBigTest1")
                    .withEmail("inv@gmail.com")
                    .withName("inv", "test").withRoles(roles);
            systemUser = builder.build();

            // Act & Assert
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> new ECourseUser(systemUser, userTaxNumber, userBirthdate, userAcronym));
        }

        @Test
        void ensureUserCreatedAsTeacherHasTeacherRole() {
            // Act
            ECourseUser teacher = new ECourseUser(systemUser, userTaxNumber, userBirthdate, userAcronym);

            // Assert
            Assertions.assertTrue(teacher.isTeacher());
        }

        @Test
        void ensureTeacherToStringReturnsString() {
            // Arrange
            ECourseUser teacher = new ECourseUser(systemUser, userTaxNumber, userBirthdate, userAcronym);

            // Act
            String result = teacher.toString();

            // Assert result is a string with content
            Assertions.assertTrue(result.length() > 0);
        }
    }

    @Nested
    class ManagerTest {

        private SystemUser systemUser;

        @BeforeEach
        void setUp() {
            // Create a SystemUser
            final Set<Role> roles = new HashSet<>();
            roles.add(ECourseRoles.MANAGER);
            SystemUserBuilder builder = UserBuilderHelper.builder();
            builder.withUsername("username")
                    .withPassword("pwdBigTest1")
                    .withEmail("man@gmail.com")
                    .withName("many", "test").withRoles(roles);
            systemUser = builder.build();
        }

        @Test
        void ensureValidManagerIsCreated() {
            // Act
            ECourseUser manager = new ECourseUser(systemUser);

            // Assert
            Assertions.assertNotNull(manager);
        }

        @Test
        void ensureManagerWithNoSystemUserIsNotCreated() {
            // Act & Assert
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> new ECourseUser(null));
        }

        @Test
        void ensureManagerWithInvalidSystemUserIsNotCreated() {
            // Arrange
            final Set<Role> roles = new HashSet<>();
            roles.add(ECourseRoles.STUDENT);
            roles.add(ECourseRoles.TEACHER);
            SystemUserBuilder builder = UserBuilderHelper.builder();
            builder.withUsername("username")
                    .withPassword("pwdBigTest1")
                    .withEmail("inv@gmail.com")
                    .withName("inv", "test").withRoles(roles);
            systemUser = builder.build();

            // Act & Assert
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> new ECourseUser(systemUser));
        }

        @Test
        void ensureManagerToStringReturnsString() {
            // Arrange
            ECourseUser manager = new ECourseUser(systemUser);

            // Act
            String result = manager.toString();

            // Assert result is a string with content
            Assertions.assertTrue(result.length() > 0);
        }

    }

    @Test
    void ensureEnabledUserIsDisabled() {
        // Arrange
        // Create a SystemUser
        final Set<Role> roles = new HashSet<>();
        roles.add(ECourseRoles.MANAGER);
        SystemUserBuilder builder = UserBuilderHelper.builder();
        builder.withUsername("username")
                .withPassword("pwdBigTest1")
                .withEmail("man@gmail.com")
                .withName("many", "test").withRoles(roles);
        var systemUser = builder.build();
        ECourseUser manager = new ECourseUser(systemUser);

        // Act
        manager.disable();

        // Assert
        Assertions.assertFalse(manager.isEnabled());
    }

    @Test
    void ensureDisabledUserIsEnabled() {
        // Arrange
        // Create a SystemUser
        final Set<Role> roles = new HashSet<>();
        roles.add(ECourseRoles.MANAGER);
        SystemUserBuilder builder = UserBuilderHelper.builder();
        builder.withUsername("username")
                .withPassword("pwdBigTest1")
                .withEmail("man@gmail.com")
                .withName("many", "test").withRoles(roles);
        var systemUser = builder.build();
        ECourseUser manager = new ECourseUser(systemUser);

        // Act
        manager.disable();
        manager.enable();

        // Assert
        Assertions.assertTrue(manager.isEnabled());
    }

    @Test
    void ensureEnablingEnableUserThrowsException() {
        // Arrange
        // Create a SystemUser
        final Set<Role> roles = new HashSet<>();
        roles.add(ECourseRoles.MANAGER);
        SystemUserBuilder builder = UserBuilderHelper.builder();
        builder.withUsername("username")
                .withPassword("pwdBigTest1")
                .withEmail("man@gmail.com")
                .withName("many", "test").withRoles(roles);
        var systemUser = builder.build();
        ECourseUser manager = new ECourseUser(systemUser);

        // Act & Assert
        Assertions.assertThrows(IllegalStateException.class, manager::enable);
    }

    @Test
    void ensureDisablingDisabledUserThrowsException() {
        // Arrange
        // Create a SystemUser
        final Set<Role> roles = new HashSet<>();
        roles.add(ECourseRoles.MANAGER);
        SystemUserBuilder builder = UserBuilderHelper.builder();
        builder.withUsername("username")
                .withPassword("pwdBigTest1")
                .withEmail("man@gmail.com")
                .withName("many", "test").withRoles(roles);
        var systemUser = builder.build();
        ECourseUser manager = new ECourseUser(systemUser);

        // Act
        manager.disable();

        // Assert
        Assertions.assertThrows(IllegalStateException.class, manager::disable);
    }

    @Test
    void ensureSameAsAndEqualsMethodsWorksProperly() {
        // Arrange
        ECourseUser user1 = UserDataSource.getTestStudent1();
        ECourseUser user2 = UserDataSource.getTestStudent1();

        // Act
        boolean resultSameAs = user1.sameAs(user2);
        boolean resultEquals = user1.equals(user2);

        // Assert
        Assertions.assertTrue(resultSameAs);
        Assertions.assertTrue(resultEquals);
    }


}