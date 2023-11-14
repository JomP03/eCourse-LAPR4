package ecourseusermanagement.domain;

import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import usermanagement.domain.ECourseRoles;
import usermanagement.domain.UserBuilderHelper;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class UserDataSource {

    public static ECourseUser getTestStudent1() {
        // parameters
        SystemUser systemUser;
        UserBirthdate userBirthdate;
        UserMechanographicNumber userMechanographicNumber;
        UserTaxNumber userTaxNumber;

        // Create a SystemUser
        final Set<Role> roles = new HashSet<>();
        roles.add(ECourseRoles.STUDENT);
        SystemUserBuilder builder = UserBuilderHelper.builder();
        builder.withUsername("student1").withPassword("pwdBigStud1").withEmail("stu1@gmail.com")
                .withName("Johny", "Test").withRoles(roles);
        systemUser = builder.build();

        // Create a UserBirthdate
        final var birth = LocalDate.of(2003, 3, 16);
        userBirthdate = new UserBirthdate(birth);

        userTaxNumber = new UserTaxNumber("243989890");

        // Create a UserMechanographicNumber
        final var currentYear = LocalDate.now().getYear();
        userMechanographicNumber = new UserMechanographicNumber(currentYear + "00001");

        return new ECourseUser(systemUser, userTaxNumber, userBirthdate, userMechanographicNumber);
    }

    public static ECourseUser getTestStudent2() {
        // parameters
        SystemUser systemUser;
        UserBirthdate userBirthdate;
        UserMechanographicNumber userMechanographicNumber;
        UserTaxNumber userTaxNumber;

        // Create a SystemUser
        final Set<Role> roles = new HashSet<>();
        roles.add(ECourseRoles.STUDENT);
        SystemUserBuilder builder = UserBuilderHelper.builder();
        builder.withUsername("student2").withPassword("pwdBigStud2").withEmail("stu2@gmail.com")
                .withName("Marie", "Test").withRoles(roles);
        systemUser = builder.build();

        // Create a UserBirthdate
        final var birth = LocalDate.of(2003, 12, 30);
        userBirthdate = new UserBirthdate(birth);

        userTaxNumber = new UserTaxNumber("258826517");

        // Create a UserMechanographicNumber
        final var currentYear = LocalDate.now().getYear();
        userMechanographicNumber = new UserMechanographicNumber(currentYear + "00001");

        return new ECourseUser(systemUser, userTaxNumber, userBirthdate, userMechanographicNumber);
    }

    public static ECourseUser getTestTeacher1() {
        // parameters
        SystemUser systemUser;
        UserBirthdate userBirthdate;
        UserAcronym userAcronym;
        UserTaxNumber userTaxNumber;

        // Create a SystemUser
        final Set<Role> roles = new HashSet<>();
        roles.add(ECourseRoles.TEACHER);
        SystemUserBuilder builder = UserBuilderHelper.builder();
        builder.withUsername("teacher1")
                .withPassword("pwdBigTest1")
                .withEmail("tea1@gmail.com")
                .withName("Chris", "Test").withRoles(roles);
        systemUser = builder.build();

        // Create a UserBirthdate
        final var birth = LocalDate.of(2003, 3, 16);
        userBirthdate = new UserBirthdate(birth);

        userTaxNumber = new UserTaxNumber("243989890");
        userAcronym = new UserAcronym("TST");

        return new ECourseUser(systemUser, userTaxNumber, userBirthdate, userAcronym);
    }

    public static ECourseUser getTestTeacher2() {
        // parameters
        SystemUser systemUser;
        UserBirthdate userBirthdate;
        UserAcronym userAcronym;
        UserTaxNumber userTaxNumber;

        // Create a SystemUser
        final Set<Role> roles = new HashSet<>();
        roles.add(ECourseRoles.TEACHER);
        SystemUserBuilder builder = UserBuilderHelper.builder();
        builder.withUsername("teacher2")
                .withPassword("pwdBigTest2")
                .withEmail("tea2@gmail.com")
                .withName("Krissie", "Test").withRoles(roles);
        systemUser = builder.build();

        // Create a UserBirthdate
        final var birth = LocalDate.of(2003, 12, 30);
        userBirthdate = new UserBirthdate(birth);

        userTaxNumber = new UserTaxNumber("258826517");
        userAcronym = new UserAcronym("PDF");

        return new ECourseUser(systemUser, userTaxNumber, userBirthdate, userAcronym);
    }

    public static ECourseUser getTestManager1() {
        // parameters
        SystemUser systemUser;

        // Create a SystemUser
        final Set<Role> roles = new HashSet<>();
        roles.add(ECourseRoles.MANAGER);
        SystemUserBuilder builder = UserBuilderHelper.builder();
        builder.withUsername("man1")
                .withPassword("pwdBigTest1")
                .withEmail("man1@gmail.com")
                .withName("Joe", "Mama").withRoles(roles);
        systemUser = builder.build();

        return new ECourseUser(systemUser);
    }

    public static ECourseUser getTestManager2() {
        // parameters
        SystemUser systemUser;

        // Create a SystemUser
        final Set<Role> roles = new HashSet<>();
        roles.add(ECourseRoles.MANAGER);
        SystemUserBuilder builder = UserBuilderHelper.builder();
        builder.withUsername("man2")
                .withPassword("pwdBigTest2")
                .withEmail("man2@gmail.com")
                .withName("Harry", "Potter").withRoles(roles);
        systemUser = builder.build();

        return new ECourseUser(systemUser);
    }

}
