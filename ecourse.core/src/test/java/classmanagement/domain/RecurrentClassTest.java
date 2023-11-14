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
import java.util.HashSet;
import java.util.Set;

public class RecurrentClassTest {

    private ECourseUser validTeacher;

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
    }

    @Test
    public void ensureValidWeekDayIsAccepted(){
        // arrange
        final RecurrentClassWeekDay validWeekDay = RecurrentClassWeekDay.MONDAY;
        final String validTime = "10:00";
        final String validTitle = "Class Title";
        final Integer validDuration = 10;
        final Course validCourse = CourseDataSource.getTestCourse1();

        // act
        final RecurrentClass recurrentClass = new RecurrentClass(validTitle, validDuration, validTime, validWeekDay, validCourse,validTeacher);

        // assert
        Assertions.assertEquals(validWeekDay, recurrentClass.getWeekDay());
    }

    @Test
    public void ensureInvalidWeekDayIsRejected(){
        // arrange
        final RecurrentClassWeekDay invalidWeekDay = null;
        final String validTime = "10:00";
        final String validTitle = "Class Title";
        final Integer validDuration = 10;
        final Course validCourse = CourseDataSource.getTestCourse1();

        // act
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RecurrentClass(validTitle, validDuration, validTime, invalidWeekDay, validCourse,validTeacher));
    }

    @Test
    public void ensureValidTimeIsAccepted(){
        // arrange
        final RecurrentClassWeekDay validWeekDay = RecurrentClassWeekDay.MONDAY;
        final String validTime = "10:00";
        final String validTitle = "Class Title";
        final Integer validDuration = 10;
        final Course validCourse = CourseDataSource.getTestCourse1();

        // act
        final RecurrentClass recurrentClass = new RecurrentClass(validTitle, validDuration, validTime, validWeekDay, validCourse,validTeacher);

        // assert
        Assertions.assertEquals(validTitle, recurrentClass.getClassTitle());
    }

    @Test
    public void ensureInvalidTimeIsRejected(){
        // arrange
        final RecurrentClassWeekDay validWeekDay = RecurrentClassWeekDay.MONDAY;
        final String invalidTime = null;
        final String validTitle = "Class Title";
        final Integer validDuration = 10;
        final Course validCourse = CourseDataSource.getTestCourse1();

        // act
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RecurrentClass(validTitle, validDuration, invalidTime, validWeekDay, validCourse,validTeacher));
    }

    @Test
    public void ensureValidTitleIsAccepted(){
        // arrange
        final RecurrentClassWeekDay validWeekDay = RecurrentClassWeekDay.MONDAY;
        final String validTime = "10:00";
        final String validTitle = "Class Title";
        final Integer validDuration = 10;
        final Course validCourse = CourseDataSource.getTestCourse1();

        // act
        final RecurrentClass recurrentClass = new RecurrentClass(validTitle, validDuration, validTime, validWeekDay, validCourse,validTeacher);

        // assert
        Assertions.assertEquals(validTitle, recurrentClass.getClassTitle());
    }

    @Test
    public void ensureInvalidTitleIsRejected(){
        // arrange
        final RecurrentClassWeekDay validWeekDay = RecurrentClassWeekDay.MONDAY;
        final String validTime = "10:00";
        final String invalidTitle = null;
        final Integer validDuration = 10;
        final Course validCourse = CourseDataSource.getTestCourse1();

        // act
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RecurrentClass(invalidTitle, validDuration, validTime, validWeekDay, validCourse,validTeacher));
    }

    @Test
    public void ensureValidDurationIsAccepted(){
        // arrange
        final RecurrentClassWeekDay validWeekDay = RecurrentClassWeekDay.MONDAY;
        final String validTime = "10:00";
        final String validTitle = "Class Title";
        final Integer validDuration = 10;
        final Course validCourse = CourseDataSource.getTestCourse1();

        // act
        final RecurrentClass recurrentClass = new RecurrentClass(validTitle, validDuration, validTime, validWeekDay, validCourse,validTeacher);

        // assert
        Assertions.assertEquals(validTitle, recurrentClass.getClassTitle());
    }

    @Test
    public void ensureInvalidDurationIsRejected(){
        // arrange
        final RecurrentClassWeekDay validWeekDay = RecurrentClassWeekDay.MONDAY;
        final String validTime = "10:00";
        final String validTitle = "Class Title";
        final Integer invalidDuration = -1;
        final Course validCourse = CourseDataSource.getTestCourse1();

        // act
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RecurrentClass(validTitle, invalidDuration, validTime, validWeekDay, validCourse,validTeacher));
    }

    @Test
    public void ensureValidCourseIsAccepted(){
        // arrange
        final RecurrentClassWeekDay validWeekDay = RecurrentClassWeekDay.MONDAY;
        final String validTime = "10:00";
        final String validTitle = "Class Title";
        final Integer validDuration = 10;
        final Course validCourse = CourseDataSource.getTestCourse1();

        // act
        final RecurrentClass recurrentClass = new RecurrentClass(validTitle, validDuration, validTime, validWeekDay, validCourse,validTeacher);

        // assert
        Assertions.assertEquals(validTitle, recurrentClass.getClassTitle());
    }

    @Test
    public void ensureInvalidCourseIsRejected(){
        // arrange
        final RecurrentClassWeekDay validWeekDay = RecurrentClassWeekDay.MONDAY;
        final String validTime = "10:00";
        final String validTitle = "Class Title";
        final Integer validDuration = 10;
        final Course invalidCourse = null;

        // act
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RecurrentClass(validTitle, validDuration, validTime, validWeekDay, invalidCourse,validTeacher));
    }

    @Test
    public void ensureValidTeacherIsAccepted(){
        // arrange
        final RecurrentClassWeekDay validWeekDay = RecurrentClassWeekDay.MONDAY;
        final String validTime = "10:00";
        final String validTitle = "Class Title";
        final Integer validDuration = 10;
        final Course validCourse = CourseDataSource.getTestCourse1();
        // act
        final RecurrentClass recurrentClass = new RecurrentClass(validTitle, validDuration, validTime, validWeekDay, validCourse,validTeacher);

        // assert no exception thrown
        Assertions.assertEquals(validTitle, recurrentClass.getClassTitle());

    }

    @Test
    public void ensureInvalidTeacherIsRejected(){
        // arrange
        final RecurrentClassWeekDay validWeekDay = RecurrentClassWeekDay.MONDAY;
        final String validTime = "10:00";
        final String validTitle = "Class Title";
        final Integer validDuration = 10;
        final Course validCourse = CourseDataSource.getTestCourse1();
        final ECourseUser invalidTeacher = null;

        // act
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RecurrentClass(validTitle, validDuration, validTime, validWeekDay, validCourse,invalidTeacher));
    }

}
