package classmanagement.domain.service;

import appsettings.Application;
import classmanagement.domain.ClassOccurrence;
import classmanagement.domain.ExtraClass;
import classmanagement.domain.RecurrentClass;
import classmanagement.domain.RecurrentClassWeekDay;
import classmanagement.repository.ClassRepository;
import coursemanagement.domain.*;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import ecourseusermanagement.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usermanagement.domain.ECourseRoles;
import usermanagement.domain.UserBuilderHelper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ClassSchedulerTest {

    private ClassRepository classRepository;
    private ClassScheduler classScheduler;

    private final Integer SLOT_DURATION = Application.settings().getSlotDurationTime();

    private Integer START_TIME_HOUR;

    private Integer END_TIME_HOUR;

    private Integer START_TIME_MINUTE;

    private Integer END_TIME_MINUTE;

    private Iterable<Course> validCourses;

    private ECourseUser validTeacher;

    private Course validCourse;

    private Course validCourse2;

    @BeforeEach
    public void setUp(){
        String[] startTime = Application.settings().getStartTime().split(":");
        String[] endTime = Application.settings().getEndTime().split(":");

        START_TIME_HOUR = Integer.parseInt(startTime[0]);
        START_TIME_MINUTE = Integer.parseInt(startTime[1]);
        END_TIME_HOUR = Integer.parseInt(endTime[0]);
        END_TIME_MINUTE = Integer.parseInt(endTime[1]);

        classRepository = mock(ClassRepository.class);
        classScheduler = new ClassScheduler(classRepository);

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
        validCourse2 = CourseDataSource.getTestCourse2();
        Course validCourse3 = CourseDataSource.getTestCourse3();

        validCourses = new ArrayList<>();
        ((ArrayList<Course>) validCourses).add(validCourse);
        ((ArrayList<Course>) validCourses).add(validCourse2);
        ((ArrayList<Course>) validCourses).add(validCourse3);
    }

    @Test
    public void ensureGetAvailableCourseTeacherIntersectionReturnNoOccupiedSlots(){

        // Arrange
        List<RecurrentClass> validRecurrentClasses = new ArrayList<>();
        when(classRepository.findCourseRecurrentClasses("EAPLI")).thenReturn(validRecurrentClasses);
        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("EAPLI", validTeacher)).thenReturn(validRecurrentClasses);
        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("APROG", validTeacher)).thenReturn(validRecurrentClasses);
        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("MDS", validTeacher)).thenReturn(validRecurrentClasses);

        //Act
        Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableRecurrentClassSlots = classScheduler.getAvailableCourseTeacherIntersectionRecurrentSlots(validCourses, validTeacher, validCourse);

        //Assert
        Assertions.assertEquals(availableRecurrentClassSlots.get(RecurrentClassWeekDay.MONDAY).get(LocalTime.of(8,0)), LocalTime.of(8,SLOT_DURATION));

        Assertions.assertEquals(availableRecurrentClassSlots.get(RecurrentClassWeekDay.TUESDAY).get(LocalTime.of(11,0)), LocalTime.of(11,SLOT_DURATION));

    }

    @Test
    public void ensureGetAvailableCourseTeacherIntersectionReturnsCorrectOccupiedSlots(){

        // get string of the start time
        String hour1 = START_TIME_HOUR+ ":" + START_TIME_MINUTE;

        String hour2 = (START_TIME_HOUR + (SLOT_DURATION/60)) + ":" + START_TIME_MINUTE;

        // Arrange
        // for teacher course where teacher will schedule
        RecurrentClass class1 = new RecurrentClass("ok", SLOT_DURATION, hour1, RecurrentClassWeekDay.MONDAY, validCourse,validTeacher);

        List<RecurrentClass> validRecurrentClasses = new ArrayList<>();
        validRecurrentClasses.add(class1);

        when(classRepository.findCourseRecurrentClasses("Course Title")).thenReturn(validRecurrentClasses);
        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("EAPLI", validTeacher)).thenReturn(validRecurrentClasses);

        // for the other courses where teacher is unavailable

        RecurrentClass class2 = new RecurrentClass("yeet", SLOT_DURATION*2, hour2, RecurrentClassWeekDay.MONDAY, validCourse2,validTeacher);
        RecurrentClass class3 = new RecurrentClass("yeet", SLOT_DURATION, hour1, RecurrentClassWeekDay.THURSDAY, validCourse2,validTeacher);

        List<RecurrentClass> validRecurrentClasses2 = new ArrayList<>();
        validRecurrentClasses2.add(class2);
        validRecurrentClasses2.add(class3);

        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("APROG", validTeacher)).thenReturn(validRecurrentClasses2);
        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("MDS", validTeacher)).thenReturn(null);



        //Act
        Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableRecurrentClassSlots = classScheduler.getAvailableCourseTeacherIntersectionRecurrentSlots(validCourses, validTeacher, validCourse);

        //Assert
        // Null because slot occupied in course where the teacher will schedule
        Assertions.assertNull(availableRecurrentClassSlots.get(RecurrentClassWeekDay.MONDAY).get(LocalTime.of(START_TIME_HOUR,START_TIME_MINUTE)));

        // Null because slot occupied in another course where the teacher is involved
        Assertions.assertNull(availableRecurrentClassSlots.get(RecurrentClassWeekDay.MONDAY).get(LocalTime.of(START_TIME_HOUR,SLOT_DURATION)));

        // Null because slot occupied in another course where the teacher is involved
        Assertions.assertNull(availableRecurrentClassSlots.get(RecurrentClassWeekDay.THURSDAY).get(LocalTime.of(START_TIME_HOUR,START_TIME_MINUTE)));
    }

    @Test
    public void ensureGetAvailableCourseTeacherIntersectionReturnsNullWhenNullComesFromRepo(){
        // Arrange
        when(classRepository.findCourseRecurrentClasses("EAPLI")).thenReturn(null);
        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("EAPLI", validTeacher)).thenReturn(null);
        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("APROG", validTeacher)).thenReturn(null);
        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("MDS", validTeacher)).thenReturn(null);

        //Act
        Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableRecurrentClassSlots = classScheduler.getAvailableCourseTeacherIntersectionRecurrentSlots(validCourses, validTeacher, validCourse);

        //Assert
        Assertions.assertNull(availableRecurrentClassSlots);
    }

    @Test
    public void ensureGetAvailableCourseTeacherIntersectionReturnsNullWhenTeacherNull(){
        //Act
        Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableRecurrentClassSlots = classScheduler.getAvailableCourseTeacherIntersectionRecurrentSlots(validCourses, null, validCourse);

        //Assert
        Assertions.assertNull(availableRecurrentClassSlots);
    }

    @Test
    public void ensureGetAvailableCourseTeacherIntersectionReturnsNullWhenCourseNull(){
        //Act
        Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableRecurrentClassSlots = classScheduler.getAvailableCourseTeacherIntersectionRecurrentSlots(validCourses, validTeacher, null);

        //Assert
        Assertions.assertNull(availableRecurrentClassSlots);
    }

    @Test
    public void ensureGetAvailableCourseTeacherIntersectionReturnsNullWhenCoursesNull(){
        //Act
        Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableRecurrentClassSlots = classScheduler.getAvailableCourseTeacherIntersectionRecurrentSlots(null, validTeacher, validCourse);

        //Assert
        Assertions.assertNull(availableRecurrentClassSlots);
    }

    @Test
    public void ensureGetAvailableRecurrentClassSlotsReturnsCorrectOccupiedSlots() {
        // Arrange
        // get string of the start time
        String hour1 = START_TIME_HOUR+ ":" + START_TIME_MINUTE;

        Integer h2 = START_TIME_HOUR + 4;
        String hour2 = h2 + ":" + START_TIME_MINUTE;

        RecurrentClass class1 = new RecurrentClass("ok", SLOT_DURATION*2, hour1, RecurrentClassWeekDay.MONDAY, CourseDataSource.getTestCourse1(), validTeacher);
        RecurrentClass class2 = new RecurrentClass("yeet", SLOT_DURATION*5, hour2, RecurrentClassWeekDay.TUESDAY, CourseDataSource.getTestCourse1(), validTeacher);

        List<RecurrentClass> validRecurrentClasses = new ArrayList<>();
        validRecurrentClasses.add(class1);
        validRecurrentClasses.add(class2);

        when(classRepository.findCourseRecurrentClasses("EAPLI")).thenReturn(validRecurrentClasses);

        //Act
        Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableRecurrentClassSlots = classScheduler.getAvailableRecurrentClassSlots("EAPLI");

        //Assert

        //Null because the slot is occupied
        Assertions.assertNull(availableRecurrentClassSlots.get(RecurrentClassWeekDay.MONDAY).get(LocalTime.of(START_TIME_HOUR,START_TIME_MINUTE)));

        //Null because the slot is occupied
        Assertions.assertNull(availableRecurrentClassSlots.get(RecurrentClassWeekDay.TUESDAY).get(LocalTime.of(h2,0)));

        //Null because the slot is occupied
        Assertions.assertNull(availableRecurrentClassSlots.get(RecurrentClassWeekDay.TUESDAY).get(LocalTime.of(h2,SLOT_DURATION)));


    }

    @Test
    public void ensureGetAvailableRecurrentClassSlotsReturnsNoOccupiedSlots() {

        List<RecurrentClass> validRecurrentClasses = new ArrayList<>();
        when(classRepository.findCourseRecurrentClasses("EAPLI")).thenReturn(validRecurrentClasses);

        //Act
        Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableRecurrentClassSlots = classScheduler.getAvailableRecurrentClassSlots("EAPLI");

        //Assert
        Assertions.assertEquals(availableRecurrentClassSlots.get(RecurrentClassWeekDay.MONDAY).get(LocalTime.of(START_TIME_HOUR,0)), LocalTime.of(START_TIME_HOUR,SLOT_DURATION));

        Assertions.assertEquals(availableRecurrentClassSlots.get(RecurrentClassWeekDay.TUESDAY).get(LocalTime.of(START_TIME_HOUR+2,0)), LocalTime.of(START_TIME_HOUR+2,SLOT_DURATION));

    }

    @Test
    public void ensureGetAvailableRecurrentClassSlotsReturnsCorrectAvailableSlots() {
        // Arrange
        // get string of the start time
        String hour1 = START_TIME_HOUR+ ":" + START_TIME_MINUTE;

        Integer h2 = START_TIME_HOUR + 4;
        String hour2 = h2 + ":" + START_TIME_MINUTE;

        RecurrentClass class1 = new RecurrentClass("ok", SLOT_DURATION*2, hour1, RecurrentClassWeekDay.MONDAY, CourseDataSource.getTestCourse1(), validTeacher);
        RecurrentClass class2 = new RecurrentClass("yeet", SLOT_DURATION*5, hour2, RecurrentClassWeekDay.TUESDAY, CourseDataSource.getTestCourse1(), validTeacher);

        List<RecurrentClass> validRecurrentClasses = new ArrayList<>();
        validRecurrentClasses.add(class1);
        validRecurrentClasses.add(class2);

        when(classRepository.findCourseRecurrentClasses("EAPLI")).thenReturn(validRecurrentClasses);

        //Act
        Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableRecurrentClassSlots = classScheduler.getAvailableRecurrentClassSlots("EAPLI");

        //Assert

        // if the slot was occupied, it would be null
        Assertions.assertNotNull(availableRecurrentClassSlots.get(RecurrentClassWeekDay.MONDAY).get(LocalTime.of(START_TIME_HOUR+5,0)));

        // if the slot was occupied, it would be null
        Assertions.assertNotNull(availableRecurrentClassSlots.get(RecurrentClassWeekDay.TUESDAY).get(LocalTime.of(h2-1,0)));

    }

    @Test
    public void ensureGetAvailableRecurrentClassSlotsReturnsEmptyListWhenNullComesFromRepo() {
        // Arrange
        when(classRepository.findCourseRecurrentClasses("EAPLI")).thenReturn(null);
        //Act
        Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableRecurrentClassSlots = classScheduler.getAvailableRecurrentClassSlots("EAPLI");

        //Assert
        Assertions.assertNull(availableRecurrentClassSlots);
    }

    @Test
    public void ensureGetAvailableRecurrentClassSlotsReturnsNullWhenCourseNull() {
        // Arrange
        when(classRepository.findCourseRecurrentClasses(null)).thenReturn(null);
        //Act
        Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableRecurrentClassSlots = classScheduler.getAvailableRecurrentClassSlots(null);

        //Assert
        Assertions.assertNull(availableRecurrentClassSlots);
    }

    @Test
    public void ensureScheduleRecurrentClassWhenTeacherUnavailableInAnotherCourseIsRejected(){
        // get string of the start time
        String hour1 = START_TIME_HOUR+ ":" + START_TIME_MINUTE;
        String hour2 = (START_TIME_HOUR +1) + ":" + START_TIME_MINUTE;

        // Arrange

        // for teacher course where teacher will schedule
        RecurrentClass class1 = new RecurrentClass("ok", SLOT_DURATION, hour1, RecurrentClassWeekDay.MONDAY, validCourse,validTeacher);

        List<RecurrentClass> validRecurrentClasses = new ArrayList<>();
        validRecurrentClasses.add(class1);

        when(classRepository.findCourseRecurrentClasses("EAPLI")).thenReturn(validRecurrentClasses);
        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("EAPLI", validTeacher)).thenReturn(validRecurrentClasses);


        // for the other course where teacher is unavailable
        RecurrentClass class3 = new RecurrentClass("yeet", SLOT_DURATION, hour2, RecurrentClassWeekDay.THURSDAY, validCourse2,validTeacher);

        List<RecurrentClass> validRecurrentClasses2 = new ArrayList<>();
        validRecurrentClasses2.add(class3);

        when(classRepository.findCourseRecurrentClasses("APROG")).thenReturn(validRecurrentClasses2);
        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("APROG", validTeacher)).thenReturn(validRecurrentClasses2);


        //Act
        // trying to schedule in a time and day, where the teacher already has another class in another course at the same time

        boolean verify = classScheduler.scheduleRecurrentClass("valid title", SLOT_DURATION, hour2, RecurrentClassWeekDay.THURSDAY, validCourse,validTeacher, validCourses);

        Assertions.assertFalse(verify);
    }

    /*
    @Test
    public void ensureGetAvailableExtraClassSlotsReturnsNoOccupiedSlots(){

        LocalDateTime startDay = LocalDateTime.now();
        LocalDateTime endDay = LocalDateTime.now().plusDays(1);

        Integer numberOfDaysBetween = (int) ChronoUnit.DAYS.between(startDay, endDay);


        when(classRepository.findCourseRecurrentClasses("EAPLI")).thenReturn(new ArrayList<>());
        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("EAPLI", validTeacher)).thenReturn(new ArrayList<>());
        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("APROG", validTeacher)).thenReturn(new ArrayList<>());
        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("MDS", validTeacher)).thenReturn(new ArrayList<>());
        when(classRepository.findExtraClassByDateRangeAndTeacherWithNonMatchingCourse("EAPLI",startDay,endDay,validTeacher)).thenReturn(new ArrayList<>());
        when(classRepository.findExtraClassByCourseCodeAndDateRange("EAPLI",startDay,endDay)).thenReturn(new ArrayList<>());

        //Act
        Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableRecurrentClassSlots = classScheduler.getAvailableExtraClassSlots(validCourses,validCourse,startDay,endDay,validTeacher);

        //Assert
        Assertions.assertEquals(numberOfDaysBetween,availableRecurrentClassSlots.size());

    }*/

    @Test
    public void ensureGetAvailableExtraClassSlotsReturnsRightDaysOfWeek(){

        LocalDateTime startDay = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        LocalDateTime endDay = startDay.plusDays(1);

        DayOfWeek dayOfWeek = startDay.getDayOfWeek();
        DayOfWeek dayOfWeek2 = endDay.getDayOfWeek();

        List<RecurrentClass> validRecurrentClasses = new ArrayList<>();
        List<ExtraClass> validExtraClasses = new ArrayList<>();
        when(classRepository.findCourseRecurrentClasses("EAPLI")).thenReturn(validRecurrentClasses);
        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("EAPLI", validTeacher)).thenReturn(validRecurrentClasses);
        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("APROG", validTeacher)).thenReturn(validRecurrentClasses);
        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("MDS", validTeacher)).thenReturn(validRecurrentClasses);
        when(classRepository.findExtraClassByDateRangeAndTeacherWithNonMatchingCourse("EAPLI",startDay,endDay,validTeacher)).thenReturn(validExtraClasses);
        when(classRepository.findExtraClassByCourseCodeAndDateRange("EAPLI",startDay,endDay)).thenReturn(new ArrayList<>());

        //Act
        Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableRecurrentClassSlots = classScheduler.getAvailableExtraClassSlots(validCourses,validCourse,startDay,endDay,validTeacher);

        //Assert
        Assertions.assertTrue(availableRecurrentClassSlots.containsKey(RecurrentClassWeekDay.valueOf(dayOfWeek.toString())));
        Assertions.assertTrue(availableRecurrentClassSlots.containsKey(RecurrentClassWeekDay.valueOf(dayOfWeek2.toString())));
    }

    @Test
    public void ensureGetAvailableExtraClassSlotsReturnsNullWhenNullComesFromRepo(){
        LocalDateTime startDay = LocalDateTime.now();
        LocalDateTime endDay = LocalDateTime.now().plusDays(1);

        when(classRepository.findCourseRecurrentClasses("EAPLI")).thenReturn(null);
        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("EAPLI", validTeacher)).thenReturn(null);
        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("APROG", validTeacher)).thenReturn(null);
        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("MDS", validTeacher)).thenReturn(null);
        when(classRepository.findExtraClassByDateRangeAndTeacherWithNonMatchingCourse("EAPLI",startDay,endDay,validTeacher)).thenReturn(null);
        when(classRepository.findExtraClassByCourseCodeAndDateRange("EAPLI",startDay,endDay)).thenReturn(null);

        //Act
        Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableRecurrentClassSlots = classScheduler.getAvailableExtraClassSlots(validCourses,validCourse,startDay,endDay,validTeacher);

        //Assert
        Assertions.assertNull(availableRecurrentClassSlots);
    }

    @Test
    public void ensureGetAvailableExtraClassSlotsReturnsRightOccupiedSlots(){

            LocalDateTime startDay = LocalDateTime.now().plusDays(7).with(LocalTime.of(START_TIME_HOUR+1,0,0)).with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
            LocalDateTime endDay = startDay.with(LocalTime.of(END_TIME_HOUR-2,0,0));

            List<RecurrentClass> validRecurrentClasses = new ArrayList<>();
            List<ExtraClass> validExtraClasses = new ArrayList<>();
        List<ECourseUser> validParticipants = new ArrayList<>();
            validExtraClasses.add(new ExtraClass("title",SLOT_DURATION,startDay,validCourse,validTeacher,validParticipants));
            validExtraClasses.add(new ExtraClass("title",SLOT_DURATION,endDay,validCourse,validTeacher,validParticipants));
            when(classRepository.findCourseRecurrentClasses("EAPLI")).thenReturn(validRecurrentClasses);
            when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("EAPLI", validTeacher)).thenReturn(validRecurrentClasses);
            when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("APROG", validTeacher)).thenReturn(validRecurrentClasses);
            when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("MDS", validTeacher)).thenReturn(validRecurrentClasses);
            when(classRepository.findExtraClassByDateRangeAndTeacherWithNonMatchingCourse("EAPLI",startDay,endDay,validTeacher)).thenReturn(new ArrayList<>());
            when(classRepository.findExtraClassByCourseCodeAndDateRange("EAPLI",startDay,endDay)).thenReturn(validExtraClasses);

            LocalTime expectedAvailableEndTime = LocalTime.of(START_TIME_HOUR,0,0).plusMinutes(SLOT_DURATION);

            //Act
            Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableRecurrentClassSlots = classScheduler.getAvailableExtraClassSlots(validCourses,validCourse,startDay,endDay,validTeacher);

            //Assert

            Assertions.assertNull(availableRecurrentClassSlots.get(RecurrentClassWeekDay.valueOf(startDay.getDayOfWeek().toString())).get(startDay.toLocalTime()));

            Assertions.assertNull(availableRecurrentClassSlots.get(RecurrentClassWeekDay.valueOf(endDay.getDayOfWeek().toString())).get(endDay.toLocalTime()));

            Assertions.assertEquals(1,availableRecurrentClassSlots.size());

            Assertions.assertEquals(expectedAvailableEndTime,availableRecurrentClassSlots.get(RecurrentClassWeekDay.valueOf(startDay.getDayOfWeek().toString())).get(LocalTime.of(START_TIME_HOUR,0,0)));
    }

    @Test
    public void ensureGetAvailableExtraClassSlotsReturnsNullWhenExtrasComeNull(){
        LocalDateTime startDay = LocalDateTime.now();
        LocalDateTime endDay = LocalDateTime.now().plusDays(1);

        List<RecurrentClass> validRecurrentClasses = new ArrayList<>();

        when(classRepository.findCourseRecurrentClasses("EAPLI")).thenReturn(validRecurrentClasses);
        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("EAPLI", validTeacher)).thenReturn(validRecurrentClasses);
        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("APROG", validTeacher)).thenReturn(validRecurrentClasses);
        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("MDS", validTeacher)).thenReturn(validRecurrentClasses);
        when(classRepository.findExtraClassByDateRangeAndTeacherWithNonMatchingCourse("EAPLI",startDay,endDay,validTeacher)).thenReturn(null);
        when(classRepository.findExtraClassByCourseCodeAndDateRange("EAPLI",startDay,endDay)).thenReturn(null);

        //Act
        Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableRecurrentClassSlots = classScheduler.getAvailableExtraClassSlots(validCourses,validCourse,startDay,endDay,validTeacher);

        //Assert
        Assertions.assertNull(availableRecurrentClassSlots);
    }

    @Test
    public void ensureValidateClassTitleReturnsTrueWhenTitleIsNotEmpty(){
        //Arrange
        String title = "title";
        //Act
        boolean result = classScheduler.validateClassTitle(title);
        //Assert
        Assertions.assertTrue(result);
    }

    @Test
    public void ensureValidateClassTitleReturnsFalseWhenTitleIsEmpty(){
        //Arrange
        String title = "";
        //Act
        boolean result = classScheduler.validateClassTitle(title);
        //Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void ensureValidateClassTitleReturnsFalseWhenTitleIsNull(){
        //Arrange
        String title = null;
        //Act
        boolean result = classScheduler.validateClassTitle(title);
        //Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void ensureValidateClassTitleReturnsTrueWhenTitleIsUnique(){
        //Arrange
        String title = "title";
        when(classRepository.findRecurrentClassByTitle(title)).thenReturn(null);
        when(classRepository.findExtraClassByTitle(title)).thenReturn(null);
        //Act
        boolean result = classScheduler.validateClassTitle(title);
        //Assert
        Assertions.assertTrue(result);
    }

    @Test
    public void ensureValidateClassTitleReturnsFalseWhenTitleIsNotUnique(){
        //Arrange
        String title = "title";
        LocalDateTime startDay = LocalDateTime.now().plusDays(7).with(LocalTime.of(START_TIME_HOUR+1,0,0));
        LocalDateTime endDay = LocalDateTime.now().plusDays(7).with(LocalTime.of(END_TIME_HOUR-2,0,0));
        List<ECourseUser> validParticipants = new ArrayList<>();
        when(classRepository.findRecurrentClassByTitle(title)).thenReturn(null);
        when(classRepository.findExtraClassByTitle(title)).thenReturn(new ExtraClass("title",SLOT_DURATION,endDay,validCourse,validTeacher,validParticipants));
        //Act
        boolean result = classScheduler.validateClassTitle(title);
        //Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void ensureScheduleExtraClassIsRejectedWhenTeacherUnavailableInAnotherCourse(){
        // get string of the start time
        String hour1 = START_TIME_HOUR+ ":" + START_TIME_MINUTE;
        String hour2 = (START_TIME_HOUR +1) + ":" + START_TIME_MINUTE;

        // Arrange
        LocalDateTime startDay = LocalDateTime.now();
        LocalDateTime endDay = LocalDateTime.now().plusDays(1);
        List<ECourseUser> validParticipants = new ArrayList<>();

        // for teacher course where teacher will schedule
        when(classRepository.findCourseRecurrentClasses("EAPLI")).thenReturn(new ArrayList<>());
        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("EAPLI", validTeacher)).thenReturn(new ArrayList<>());
        when(classRepository.findExtraClassByDateRangeAndTeacherWithNonMatchingCourse("EAPLI",startDay,endDay,validTeacher)).thenReturn(new ArrayList<>());
        when(classRepository.findExtraClassByCourseCodeAndDateRange("EAPLI",startDay,endDay)).thenReturn(new ArrayList<>());


        // for the other course where teacher is unavailable
        RecurrentClass class3 = new RecurrentClass("yeet", SLOT_DURATION, hour2, RecurrentClassWeekDay.THURSDAY, validCourse2,validTeacher);

        List<RecurrentClass> validRecurrentClasses2 = new ArrayList<>();
        validRecurrentClasses2.add(class3);

        when(classRepository.findCourseRecurrentClasses("APROG")).thenReturn(validRecurrentClasses2);
        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("APROG", validTeacher)).thenReturn(validRecurrentClasses2);
        when(classRepository.findExtraClassByDateRangeAndTeacherWithNonMatchingCourse("EAPLI",startDay,endDay,validTeacher)).thenReturn(new ArrayList<>());
        when(classRepository.findExtraClassByCourseCodeAndDateRange("EAPLI",startDay,endDay)).thenReturn(new ArrayList<>());


        //Act
        // trying to schedule in a time and day, where the teacher already has another class in another course at the same time (class3)
        boolean verify = classScheduler.scheduleExtraClass(validCourses,validCourse,startDay,endDay,RecurrentClassWeekDay.THURSDAY,LocalTime.of(START_TIME_HOUR+1,START_TIME_MINUTE),SLOT_DURATION,"TITLE_TEST",validParticipants,validTeacher);

        Assertions.assertFalse(verify);
    }

    @Test
    public void ensureExtraSlotsAreRightWhenARecurrentOccurrenceWasUpdated(){

        LocalDateTime startDay = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).with(LocalTime.of(0,0,0));
        LocalDateTime endDay = startDay.with(TemporalAdjusters.next(DayOfWeek.SATURDAY)).with(LocalTime.of(0,0,0));

        // get string of the start time
        String hour1 = START_TIME_HOUR+ ":" + START_TIME_MINUTE;

        // Arrange

        // recurrent class with changed occurrence from tuesday to friday
        RecurrentClass class1 = new RecurrentClass("ok", SLOT_DURATION*2, hour1, RecurrentClassWeekDay.TUESDAY, validCourse,validTeacher);
        ClassOccurrence occurrence = new ClassOccurrence(startDay.with(TemporalAdjusters.next(DayOfWeek.FRIDAY)).with(LocalTime.of(START_TIME_HOUR,START_TIME_MINUTE)), startDay.with(TemporalAdjusters.next(DayOfWeek.TUESDAY)).with(LocalTime.of(START_TIME_HOUR,START_TIME_MINUTE)), SLOT_DURATION);
        class1.updateClassOccurrence(occurrence);

        List<RecurrentClass> validRecurrentClasses = new ArrayList<>();
        validRecurrentClasses.add(class1);

        // arranges
        List<ExtraClass> validExtraClasses = new ArrayList<>();
        List<ECourseUser> validParticipants = new ArrayList<>();
        validExtraClasses.add(new ExtraClass("title",SLOT_DURATION,startDay.plusHours(10),validCourse,validTeacher,validParticipants));
        validExtraClasses.add(new ExtraClass("title",SLOT_DURATION,startDay.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY)).with(LocalTime.of(START_TIME_HOUR,START_TIME_MINUTE)),validCourse,validTeacher,validParticipants));
        when(classRepository.findCourseRecurrentClasses("EAPLI")).thenReturn(validRecurrentClasses);
        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("EAPLI", validTeacher)).thenReturn(validRecurrentClasses);
        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("APROG", validTeacher)).thenReturn(validRecurrentClasses);
        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("MDS", validTeacher)).thenReturn(validRecurrentClasses);
        when(classRepository.findExtraClassByDateRangeAndTeacherWithNonMatchingCourse("EAPLI",startDay,endDay,validTeacher)).thenReturn(new ArrayList<>());
        when(classRepository.findExtraClassByCourseCodeAndDateRange("EAPLI",startDay,endDay)).thenReturn(validExtraClasses);

        LocalTime expectedAvailableEndTime = LocalTime.of(START_TIME_HOUR,0,0).plusMinutes(SLOT_DURATION);

        //Act
        Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableRecurrentClassSlots = classScheduler.getAvailableExtraClassSlots(validCourses,validCourse,startDay,endDay,validTeacher);

        //Assert
        // check if tuesday at time of the oldDate from the recurrent Class is available since it was updated to friday
        Assertions.assertEquals(expectedAvailableEndTime,availableRecurrentClassSlots.get(RecurrentClassWeekDay.TUESDAY).get(LocalTime.of(START_TIME_HOUR,0,0)));

    }

    @Test
    public void ensureGetAvailableUpdateSlotsReturnsRightOccurrences(){
        //Arrange
        LocalDateTime startDay = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).with(LocalTime.of(0,0,0));
        LocalDateTime endDay = startDay.with(TemporalAdjusters.next(DayOfWeek.SATURDAY)).with(LocalTime.of(0,0,0));

        // recurrent class with changed occurrence from tuesday to friday
        RecurrentClass class1 = new RecurrentClass("ok", SLOT_DURATION*2, START_TIME_HOUR+ ":" + START_TIME_MINUTE, RecurrentClassWeekDay.TUESDAY, validCourse,validTeacher);
        LocalDateTime newDate = startDay.with(TemporalAdjusters.next(DayOfWeek.FRIDAY)).with(LocalTime.of(START_TIME_HOUR,START_TIME_MINUTE));
        LocalDateTime oldDate = startDay.with(TemporalAdjusters.next(DayOfWeek.TUESDAY)).with(LocalTime.of(START_TIME_HOUR,START_TIME_MINUTE));
        ClassOccurrence occurrence = new ClassOccurrence(newDate, oldDate, SLOT_DURATION);
        class1.updateClassOccurrence(occurrence);

        List<RecurrentClass> validRecurrentClasses = new ArrayList<>();
        validRecurrentClasses.add(class1);

        when(classRepository.findCourseRecurrentClasses("EAPLI")).thenReturn(validRecurrentClasses);
        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("EAPLI", validTeacher)).thenReturn(validRecurrentClasses);

        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("APROG", validTeacher)).thenReturn(new ArrayList<>());
        when(classRepository.findCourseRecurrentClassesWhereTeacherInvolved("MDS", validTeacher)).thenReturn(new ArrayList<>());

        when(classRepository.findExtraClassByDateRangeAndTeacherWithNonMatchingCourse("EAPLI",startDay,endDay,validTeacher)).thenReturn(new ArrayList<>());
        when(classRepository.findExtraClassByCourseCodeAndDateRange("EAPLI",startDay,endDay)).thenReturn(new ArrayList<>());

        //Act
        Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableRecurrentClassSlots = classScheduler.getAvailableUpdateSlots(validCourses,class1,startDay,endDay,startDay.with(TemporalAdjusters.next(DayOfWeek.TUESDAY)).with(LocalTime.of(START_TIME_HOUR,START_TIME_MINUTE)),validTeacher);

        //Assert

        // check if tuesday at time of the oldDate from the recurrent Class is available since it was updated to friday
        Assertions.assertEquals(LocalTime.of(START_TIME_HOUR,0,0).plusMinutes(SLOT_DURATION),availableRecurrentClassSlots.get(RecurrentClassWeekDay.TUESDAY).get(LocalTime.of(START_TIME_HOUR,0,0)));

        // check if friday at time of the newDate is occupied (non-existent in the map)
        Assertions.assertNull(availableRecurrentClassSlots.get(RecurrentClassWeekDay.FRIDAY).get(LocalTime.of(START_TIME_HOUR,START_TIME_MINUTE,0)));

    }
}
