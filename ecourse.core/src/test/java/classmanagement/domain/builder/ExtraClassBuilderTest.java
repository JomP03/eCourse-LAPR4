package classmanagement.domain.builder;

import classmanagement.domain.ExtraClass;
import classmanagement.domain.RecurrentClass;
import classmanagement.domain.RecurrentClassWeekDay;
import classmanagement.repository.ClassRepository;
import coursemanagement.domain.Course;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

public class ExtraClassBuilderTest {

    private RecurrentClassBuilder recurrentClassBuilder;

    private ExtraClassBuilder extraClassBuilder;

    private CourseRepository courseRepository;

    private ClassRepository classRepository;

    private ECourseUser validTeacher;

    @BeforeEach
    public void setUp() {
        classRepository = mock(ClassRepository.class);
        courseRepository = mock(CourseRepository.class);
        recurrentClassBuilder = new RecurrentClassBuilder(classRepository);
        extraClassBuilder = new ExtraClassBuilder(classRepository);

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
    public void ensureExtraClassBuilderIsNotNull(){Assertions.assertNotNull(recurrentClassBuilder);}

    @Test
    public void ensureBuilderDoesNotCreateClassWithSameTitleAsAnotherRecurrentClass(){
        Course validCourse = CourseDataSource.getTestCourse1();
        List<ECourseUser> validParticipants = new ArrayList<>();

        RecurrentClass recurrentClass = recurrentClassBuilder.withCourse(validCourse)
                .withClassTitle("Test")
                .withClassDuration(15)
                .withRecurrentClassTime("10:00")
                .withRecurrentClassWeekDay(RecurrentClassWeekDay.MONDAY)
                .withTeacher(validTeacher)
                .build();

        when(classRepository.findRecurrentClassByTitle("Test")).thenReturn(recurrentClass);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            extraClassBuilder.withCourse(validCourse)
                    .withClassTitle("Test")
                    .withClassDuration(15)
                    .withTeacher(validTeacher)
                    .withExtraClassParticipants(validParticipants)
                    .withExtraClassDate(LocalDateTime.now().plusDays(1))
                    .build();
        });
    }

    @Test
    public void ensureBuilderDoesNotCreateClassWithSameTitleAsAnotherExtraClass() {
        Course validCourse = CourseDataSource.getTestCourse1();
        List<ECourseUser> validParticipants = new ArrayList<>();

        ExtraClass extraClass = extraClassBuilder.withCourse(validCourse)
                .withClassTitle("Test")
                .withClassDuration(15)
                .withTeacher(validTeacher)
                .withExtraClassParticipants(validParticipants)
                .withExtraClassDate(LocalDateTime.now().plusDays(1))
                .build();

        when(classRepository.findExtraClassByTitle("Test")).thenReturn(extraClass);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            extraClassBuilder.withCourse(validCourse)
                    .withClassTitle("Test")
                    .withClassDuration(15)
                    .withTeacher(validTeacher)
                    .withExtraClassParticipants(validParticipants)
                    .withExtraClassDate(LocalDateTime.now().plusDays(1))
                    .build();
        });
    }

    @Test
    public void ensureBuilderReturnsValidExtraClass(){
        Course validCourse = CourseDataSource.getTestCourse1();
        List<ECourseUser> validParticipants = new ArrayList<>();

        ExtraClass extraClass = extraClassBuilder.withCourse(validCourse)
                .withClassTitle("Test")
                .withClassDuration(15)
                .withTeacher(validTeacher)
                .withExtraClassParticipants(validParticipants)
                .withExtraClassDate(LocalDateTime.now().plusDays(1))
                .build();

        when(classRepository.findExtraClassByTitle("Test")).thenReturn(null);

        Assertions.assertNotNull(extraClass);
    }

    @Test
    public void ensureBuilderDoesNotCreateClassWithNullCourse(){
        List<ECourseUser> validParticipants = new ArrayList<>();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            extraClassBuilder.withCourse(null)
                    .withClassTitle("Test")
                    .withClassDuration(15)
                    .withTeacher(validTeacher)
                    .withExtraClassParticipants(validParticipants)
                    .withExtraClassDate(LocalDateTime.now().plusDays(1))
                    .build();
        });
    }

    @Test
    public void ensureBuilderDoesNotCreateClassWithNullTitle(){
        Course validCourse = CourseDataSource.getTestCourse1();
        List<ECourseUser> validParticipants = new ArrayList<>();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            extraClassBuilder.withCourse(validCourse)
                    .withClassTitle(null)
                    .withClassDuration(15)
                    .withTeacher(validTeacher)
                    .withExtraClassParticipants(validParticipants)
                    .withExtraClassDate(LocalDateTime.now().plusDays(1))
                    .build();
        });
    }

    @Test
    public void ensureBuilderDoesNotCreateClassWithEmptyTitle(){
        Course validCourse = CourseDataSource.getTestCourse1();
        List<ECourseUser> validParticipants = new ArrayList<>();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            extraClassBuilder.withCourse(validCourse)
                    .withClassTitle("")
                    .withClassDuration(15)
                    .withTeacher(validTeacher)
                    .withExtraClassParticipants(validParticipants)
                    .withExtraClassDate(LocalDateTime.now().plusDays(1))
                    .build();
        });
    }

    @Test
    public void ensureBuilderDoesNotCreateClassWithNullDuration(){
        Course validCourse = CourseDataSource.getTestCourse1();
        List<ECourseUser> validParticipants = new ArrayList<>();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            extraClassBuilder.withCourse(validCourse)
                    .withClassTitle("Test")
                    .withClassDuration(null)
                    .withTeacher(validTeacher)
                    .withExtraClassParticipants(validParticipants)
                    .withExtraClassDate(LocalDateTime.now().plusDays(1))
                    .build();
        });
    }

    @Test
    public void ensureBuilderDoesNotCreateClassWithNegativeDuration(){
        Course validCourse = CourseDataSource.getTestCourse1();
        List<ECourseUser> validParticipants = new ArrayList<>();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            extraClassBuilder.withCourse(validCourse)
                    .withClassTitle("Test")
                    .withClassDuration(-1)
                    .withTeacher(validTeacher)
                    .withExtraClassParticipants(validParticipants)
                    .withExtraClassDate(LocalDateTime.now().plusDays(1))
                    .build();
        });
    }

    @Test
    public void ensureBuilderDoesNotCreateClassWithNullTeacher(){
        Course validCourse = CourseDataSource.getTestCourse1();
        List<ECourseUser> validParticipants = new ArrayList<>();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            extraClassBuilder.withCourse(validCourse)
                    .withClassTitle("Test")
                    .withClassDuration(15)
                    .withTeacher(null)
                    .withExtraClassParticipants(validParticipants)
                    .withExtraClassDate(LocalDateTime.now().plusDays(1))
                    .build();
        });
    }

    @Test
    public void ensureBuilderDoesNotCreateClassWithNullParticipants(){
        Course validCourse = CourseDataSource.getTestCourse1();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            extraClassBuilder.withCourse(validCourse)
                    .withClassTitle("Test")
                    .withClassDuration(15)
                    .withTeacher(validTeacher)
                    .withExtraClassParticipants(null)
                    .withExtraClassDate(LocalDateTime.now().plusDays(1))
                    .build();
        });
    }

    @Test
    public void ensureBuilderDoesNotCreateClassWithNullDate(){
        Course validCourse = CourseDataSource.getTestCourse1();
        List<ECourseUser> validParticipants = new ArrayList<>();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            extraClassBuilder.withCourse(validCourse)
                    .withClassTitle("Test")
                    .withClassDuration(15)
                    .withTeacher(validTeacher)
                    .withExtraClassParticipants(validParticipants)
                    .withExtraClassDate(null)
                    .build();
        });
    }

    @Test
    public void ensureBuilderDoesNotCreateClassWithPastDate(){
        Course validCourse = CourseDataSource.getTestCourse1();
        List<ECourseUser> validParticipants = new ArrayList<>();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            extraClassBuilder.withCourse(validCourse)
                    .withClassTitle("Test")
                    .withClassDuration(15)
                    .withTeacher(validTeacher)
                    .withExtraClassParticipants(validParticipants)
                    .withExtraClassDate(LocalDateTime.now().minusDays(1))
                    .build();
        });
    }
}
