package ecourseusermanagement.domain.ecourseuserbuilders;

import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import ecourseusermanagement.domain.*;
import ecourseusermanagement.repositories.IeCourseUserRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import usermanagement.domain.ECourseRoles;
import usermanagement.domain.UserBuilderHelper;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StudentBuilderTest {

    private final IeCourseUserRepository eCourseUserRepository = mock(IeCourseUserRepository.class);
    private final MechanographicNumberAssigner mechanographicNumberAssigner =
            new MechanographicNumberAssigner(eCourseUserRepository);

    @Test
    public void ensureStudentIsBuiltWithValidSystemUser() {
        // Arrange
        StudentBuilder studentBuilder = new StudentBuilder(mechanographicNumberAssigner);
        String currentYear = String.valueOf(LocalDate.now().getYear());
        UserMechanographicNumber lastMechNumInRepo = new UserMechanographicNumber(currentYear + "00001");
        when(eCourseUserRepository.findLastMechanographicNumberInYear(currentYear)).thenReturn(Optional.of(lastMechNumInRepo));

        // Create a SystemUser
        final Set<Role> roles = new HashSet<>();
        roles.add(ECourseRoles.STUDENT);
        SystemUserBuilder builder = UserBuilderHelper.builder();
        builder.withUsername("username")
                .withPassword("pwdBigTest1")
                .withEmail("stu@gmail.com")
                .withName("stu", "test").withRoles(roles);
        SystemUser systemUser = builder.build();
        // Create a UserBirthdate
        final var birth = LocalDate.of(2003, 3, 16);
        UserBirthdate userBirthdate = new UserBirthdate(birth);
        UserTaxNumber userTaxNumber = new UserTaxNumber("243989890");

        // Act
        ECourseUser eCourseUser = studentBuilder.withSystemUser(systemUser)
                .withUserBirthDate(userBirthdate)
                .withUserTaxNumber(userTaxNumber)
                .build();

        // Assert
        assertNotNull(eCourseUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureStudentIsNotBuiltWithIncorrectRole() {
        // Arrange
        StudentBuilder studentBuilder = new StudentBuilder(mechanographicNumberAssigner);
        String currentYear = String.valueOf(LocalDate.now().getYear());
        UserMechanographicNumber lastMechNumInRepo = new UserMechanographicNumber(currentYear + "00001");
        when(eCourseUserRepository.findLastMechanographicNumberInYear(currentYear)).thenReturn(Optional.of(lastMechNumInRepo));
        // Create a SystemUser
        final Set<Role> roles = new HashSet<>();
        roles.add(ECourseRoles.MANAGER);
        SystemUserBuilder builder = UserBuilderHelper.builder();
        builder.withUsername("username")
                .withPassword("pwdBigTest1")
                .withEmail("inc@gmail.com")
                .withName("inc", "test").withRoles(roles);
        SystemUser systemUser = builder.build();
        final var birth = LocalDate.of(2003, 3, 16);
        UserBirthdate userBirthdate = new UserBirthdate(birth);
        UserTaxNumber userTaxNumber = new UserTaxNumber("243989890");

        // Act & Assert
        studentBuilder.withSystemUser(systemUser)
                .withUserBirthDate(userBirthdate)
                .withUserTaxNumber(userTaxNumber)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void assureExceptionIsThrownIfNoMechanographicNumberAssignerIsProvided() {
        // Act & Assert
        new StudentBuilder(null);
    }

}