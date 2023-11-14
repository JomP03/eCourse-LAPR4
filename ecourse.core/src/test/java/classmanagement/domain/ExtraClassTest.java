package classmanagement.domain;

import coursemanagement.domain.*;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.domain.UserAcronym;
import ecourseusermanagement.domain.UserBirthdate;
import ecourseusermanagement.domain.UserTaxNumber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usermanagement.domain.ECourseRoles;
import usermanagement.domain.UserBuilderHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExtraClassTest {


    private ECourseUser validTeacher;

    private Course validCourse;

    @BeforeEach
    public void setUp(){
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
        UserAcronym userAcronym = new UserAcronym("TST");

        validTeacher = new ECourseUser(systemUser, userTaxNumber, userBirthdate, userAcronym);


        validCourse = CourseDataSource.getTestCourse1();
    }

    @Test
    public void ensureValidExtraClassIsCreated(){

        LocalDateTime validDate = LocalDateTime.now().plusDays(1);

        String validTitle = "Extra Class";
        Integer validDuration = 60;
        List<ECourseUser> validParticipants = new ArrayList<>();

        ExtraClass extraClass = new ExtraClass(validTitle, validDuration, validDate, validCourse, validTeacher,validParticipants);

        Assertions.assertEquals(validDate, extraClass.getsDate());

    }

    @Test
    public void ensureExtraClassIsNotCreatedWithDateBeforeToday(){

        LocalDateTime invalidDate = LocalDateTime.now().minusDays(1);
        List<ECourseUser> validParticipants = new ArrayList<>();
        String validTitle = "Extra Class";
        Integer validDuration = 60;

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ExtraClass(validTitle, validDuration, invalidDate, validCourse, validTeacher,validParticipants);
        });

    }

    @Test
    public void ensureExtraClassIsNotCreatedWithInvalidDate(){

        String validTitle = "Extra Class";
        Integer validDuration = 60;
        List<ECourseUser> validParticipants = new ArrayList<>();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ExtraClass(validTitle, validDuration, null, validCourse, validTeacher,validParticipants);
        });

    }

    @Test
    public void ensureExtraClassIsNotCreatedWithInvalidTitle(){

        LocalDateTime validDate = LocalDateTime.now().plusDays(1);
        List<ECourseUser> validParticipants = new ArrayList<>();

        Integer validDuration = 60;

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ExtraClass(null, validDuration, validDate, validCourse, validTeacher,validParticipants);
        });

    }

    @Test
    public void ensureExtraClassIsNotCreatedWithInvalidDuration(){

        LocalDateTime validDate = LocalDateTime.now().plusDays(1);
        List<ECourseUser> validParticipants = new ArrayList<>();

        String validTitle = "Extra Class";

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ExtraClass(validTitle, null, validDate, validCourse, validTeacher,validParticipants);
        });

    }

    @Test
    public void ensureExtraClassIsNotCreatedWithInvalidCourse(){

        LocalDateTime validDate = LocalDateTime.now().plusDays(1);
        List<ECourseUser> validParticipants = new ArrayList<>();

        String validTitle = "Extra Class";
        Integer validDuration = 60;

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ExtraClass(validTitle, validDuration, validDate, null, validTeacher,validParticipants);
        });

    }

    @Test
    public void ensureExtraClassIsNotCreatedWithInvalidTeacher(){

        LocalDateTime validDate = LocalDateTime.now().plusDays(1);
        List<ECourseUser> validParticipants = new ArrayList<>();

        String validTitle = "Extra Class";
        Integer validDuration = 60;

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ExtraClass(validTitle, validDuration, validDate, validCourse, null,validParticipants);
        });

    }

}
