package classmanagement.domain.builder;

import classmanagement.domain.RecurrentClass;
import classmanagement.domain.RecurrentClassWeekDay;
import classmanagement.repository.ClassRepository;
import coursemanagement.domain.Course;
import coursemanagement.domain.CourseBuilder;
import coursemanagement.domain.CourseDataSource;
import coursemanagement.repository.CourseRepository;
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
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RecurrentClassBuilderTest {

    private RecurrentClassBuilder recurrentClassBuilder;

    private CourseRepository courseRepository;

    private ClassRepository classRepository;

    private ECourseUser validTeacher;

    @BeforeEach
    public void setUp() {
        classRepository = mock(ClassRepository.class);
        courseRepository = mock(CourseRepository.class);
        recurrentClassBuilder = new RecurrentClassBuilder(classRepository);

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
    public void ensureRecurrentClassBuilderIsNotNull() {
        Assertions.assertNotNull(recurrentClassBuilder);
    }

    @Test
    public void ensureBuilderReturnsValidRecurrentClass() {
        //Arrange
        Course course = CourseDataSource.getTestCourse1();


        // Act
        RecurrentClass recurrentClass = recurrentClassBuilder.withCourse(course)
                .withClassTitle("Test")
                .withClassDuration(15)
                .withRecurrentClassTime("10:00")
                .withRecurrentClassWeekDay(RecurrentClassWeekDay.MONDAY)
                .withTeacher(validTeacher)
                .build();


        // Assert
        Assertions.assertEquals("Test", recurrentClass.getClassTitle());
    }

    @Test
    public void ensureBuilderDoesNotCreateClassWithSameTitle() {
        //Arrange
        Course course = CourseDataSource.getTestCourse1();
        RecurrentClass recurrentClass = recurrentClassBuilder.withCourse(course)
                .withClassTitle("Test")
                .withClassDuration(15)
                .withRecurrentClassTime("10:00")
                .withRecurrentClassWeekDay(RecurrentClassWeekDay.MONDAY)
                .withTeacher(validTeacher)
                .build();

        when(classRepository.findRecurrentClassByTitle("Test")).thenReturn(recurrentClass);

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            recurrentClassBuilder.withCourse(course)
                    .withClassTitle("Test")
                    .withClassDuration(15)
                    .withRecurrentClassTime("10:00")
                    .withRecurrentClassWeekDay(RecurrentClassWeekDay.MONDAY)
                    .withTeacher(validTeacher)
                    .build();
        });
    }

    @Test
    public void ensureInvalidClassTitleisRejected(){
        //Arrange
        Course course = CourseDataSource.getTestCourse1();

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            recurrentClassBuilder.withCourse(course)
                    .withClassTitle("")
                    .withClassDuration(15)
                    .withRecurrentClassTime("10:00")
                    .withRecurrentClassWeekDay(RecurrentClassWeekDay.MONDAY)
                    .withTeacher(validTeacher)
                    .build();
        });

    }

    @Test
    public void ensureInvalidClassDurationisRejected(){
        //Arrange
        Course course = CourseDataSource.getTestCourse1();

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            recurrentClassBuilder.withCourse(course)
                    .withClassTitle("Test")
                    .withClassDuration(0)
                    .withRecurrentClassTime("10:00")
                    .withRecurrentClassWeekDay(RecurrentClassWeekDay.MONDAY)
                    .withTeacher(validTeacher)
                    .build();
        });

    }

    @Test
    public void ensureInvalidClassTimeisRejected(){
        //Arrange
        Course course = CourseDataSource.getTestCourse1();

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            recurrentClassBuilder.withCourse(course)
                    .withClassTitle("Test")
                    .withClassDuration(15)
                    .withRecurrentClassTime("-1")
                    .withRecurrentClassWeekDay(RecurrentClassWeekDay.MONDAY)
                    .withTeacher(validTeacher)
                    .build();
        });

    }

    @Test
    public void ensureInvalidClassWeekDayisRejected(){
        //Arrange
        Course course = CourseDataSource.getTestCourse1();

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            recurrentClassBuilder.withCourse(course)
                    .withClassTitle("Test")
                    .withClassDuration(15)
                    .withRecurrentClassTime("10:00")
                    .withRecurrentClassWeekDay(null)
                    .withTeacher(validTeacher)
                    .build();
        });

    }

    @Test
    public void ensureInvalidClassCourseisRejected(){
        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            recurrentClassBuilder.withCourse(null)
                    .withClassTitle("Test")
                    .withClassDuration(15)
                    .withRecurrentClassTime("10:00")
                    .withRecurrentClassWeekDay(RecurrentClassWeekDay.MONDAY)
                    .withTeacher(validTeacher)
                    .build();
        });

    }

    @Test
    public void ensureInvalidClassTeacherisRejected(){
        //Arrange
        Course course = CourseDataSource.getTestCourse1();

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            recurrentClassBuilder.withCourse(course)
                    .withClassTitle("Test")
                    .withClassDuration(15)
                    .withRecurrentClassTime("10:00")
                    .withRecurrentClassWeekDay(RecurrentClassWeekDay.MONDAY)
                    .withTeacher(null)
                    .build();
        });

    }



}
