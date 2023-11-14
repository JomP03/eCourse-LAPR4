package classmanagement.domain.service;

import appsettings.Application;
import classmanagement.domain.ClassOccurrence;
import classmanagement.domain.ExtraClass;
import classmanagement.domain.RecurrentClass;
import classmanagement.domain.RecurrentClassWeekDay;
import classmanagement.domain.builder.ExtraClassBuilder;
import classmanagement.domain.builder.RecurrentClassBuilder;
import classmanagement.repository.ClassRepository;
import coursemanagement.domain.Course;
import ecourseusermanagement.domain.ECourseUser;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class ClassScheduler {

    private final ClassRepository classRepository;

    private final Integer SLOT_DURATION = Application.settings().getSlotDurationTime();

    private final Integer START_TIME_HOUR;

    private final Integer START_TIME_MINUTE;

    private final Integer END_TIME_HOUR;

    private final Integer END_TIME_MINUTE;

    private final Integer NUMBER_OF_WEEKS_FROM_NOW;

    public ClassScheduler(ClassRepository classRepository) {
        if (classRepository == null)
            throw new IllegalArgumentException("The class repository can not be null.");

        this.classRepository = classRepository;

        // Get the start and end time from the config file
        String[] start = Application.settings().getStartTime().split(":");
        String[] end = Application.settings().getEndTime().split(":");
        this.START_TIME_HOUR = Integer.parseInt(start[0]);
        this.START_TIME_MINUTE = Integer.parseInt(start[1]);
        this.END_TIME_HOUR = Integer.parseInt(end[0]);
        this.END_TIME_MINUTE = Integer.parseInt(end[1]);
        this.NUMBER_OF_WEEKS_FROM_NOW = Integer.parseInt(Application.settings().getNumberOfWeeksFromNow());
    }

    /**
     * Calculates the available recurrence class slots for the given course + the given teacher.
     * After calculating the available slots for the given course, it then removes from it the slots where the teacher is involved in other classes from other courses.
     *
     * @param courses the courses to retrieve the available recurrence class slots for
     * @param user the teacher to retrieve the available recurrence class slots for
     * @param course the course to schedule the recurrent class for
     * @return a Map containing the available recurrence class slots for each day of the week
     */
    public Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> getAvailableCourseTeacherIntersectionRecurrentSlots(
            Iterable<Course> courses, ECourseUser user, Course course){

            if(courses == null || user == null || course == null)
                return null;
            // map of the available slots of the course the class will be scheduled for each da (eg. Monday: 10:00 - 11:00, 11:00 - 12:00)
            Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableSlots = getAvailableRecurrentClassSlots(course.identity().toString());

            // for each course of the teacher removed the available slots where the teacher is involved in other classes from other courses
            for (Course c : courses) {
                // if the course is the same as the one the class will be scheduled for, skip it
                if(c.equals(course))
                    continue;

                // map of the available slots for the teacher (eg. Monday: 10:00 - 11:00, 11:00 - 12:00)
                Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> teacherAvailableSlots = getAvailableTeacherRecurrentSlotsForCourse(user,c.identity().toString());

                if(teacherAvailableSlots == null)
                    continue;

                if(availableSlots.isEmpty())
                    availableSlots = teacherAvailableSlots;
                else
                    // remove the slots where the teacher is involved in other classes from other courses
                    removeSlotsIntersection(availableSlots, teacherAvailableSlots);
            }

            return availableSlots;
    }

    /**
     * This method checks in which classes the teacher is involved and then calculates the available slots for each day of the week
     *
     * @param user the teacher to retrieve the available recurrence class slots for
     * @param courseCode the course to schedule the recurrent class for
     * @return a Map containing the available recurrence class slots for each day of the week of the given teacher
     */
    private Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> getAvailableTeacherRecurrentSlotsForCourse(ECourseUser user, String courseCode) {
        Iterable<RecurrentClass> classes;

        // GET THE TIMES OF THE TEACHER COURSE CLASSES
        try{
            classes = classRepository.findCourseRecurrentClassesWhereTeacherInvolved(courseCode, user);
        } catch (Exception e){
            return null;
        }

        if(classes == null)
            return null;

        // map of the used slots for each day (eg. Monday: 10:00 - 11:00, 11:00 - 12:00)
        Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> usedSlots = new LinkedHashMap<>();

        // GET THE USED SLOTS FOR EACH DAY
        fillUsedRecurrentClassSlots(classes,usedSlots);

        // map of the available slots for each day (eg. Monday: 10:00 - 11:00, 11:00 - 12:00)
        Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableSlots = new LinkedHashMap<>();

        // GET THE AVAILABLE SLOTS FOR EACH DAY
        getAvailableSlots(usedSlots, availableSlots);

        return availableSlots;
    }

    /**
     *
     * @param availableSlots the available slots
     * @param checkAvailableSlots the slots to remove from the available slots
     */
    private void removeSlotsIntersection(Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableSlots,
                                         Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> checkAvailableSlots){
        for (RecurrentClassWeekDay day : availableSlots.keySet()) {
            Map<LocalTime, LocalTime> daySlots = availableSlots.get(day);
            Map<LocalTime, LocalTime> courseDaySlots = checkAvailableSlots.get(day);

            // if the course has no classes for the day (means the whole day is occupied), remove the day from the available slots
            // else does the same for all the times in the day
            if(courseDaySlots == null)
                availableSlots.remove(day);
            else{
                List<LocalTime> keysToRemove = new ArrayList<>();
                for (LocalTime time : daySlots.keySet()) {
                    if(!courseDaySlots.containsKey(time))
                        keysToRemove.add(time);
                }
                for (LocalTime key : keysToRemove) {
                    daySlots.remove(key);
                }
            }
        }
    }

    /**
     Calculates the available recurrence class slots for a given course.
     The method retrieves the recurrent classes for the course from the class repository and then
     calculates the used slots for each day of the week based on the recurrent classes' start and end times.
     It then initializes the available slots with all the slots for each day of the week and removes the used slots
     from the available slots, resulting in the available recurrent class slots for the course.
     @param classCourse the name of the course to retrieve the available recurrence class slots for
     @return a Map containing the available recurrence class slots for each day of the week, or null if an exception is thrown while retrieving the recurrent classes from the repository
     */
    public Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> getAvailableRecurrentClassSlots(String classCourse) {
        Iterable<RecurrentClass> classes;

        // GET THE TIMES OF THE COURSE CLASSES
        try{
            classes = classRepository.findCourseRecurrentClasses(classCourse);
        } catch (Exception e){
            return null;
        }

        if(classes == null)
            return null;

        // map of the used slots for each day (eg. Monday: 10:00 - 11:00, 11:00 - 12:00)
        Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> usedSlots = new LinkedHashMap<>();

        // GET THE USED SLOTS FOR EACH DAY
        fillUsedRecurrentClassSlots(classes,usedSlots);

        // map of the available slots for each day (eg. Monday: 10:00 - 11:00, 11:00 - 12:00)
        Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableSlots = new LinkedHashMap<>();

        // GET THE AVAILABLE SLOTS FOR EACH DAY
        getAvailableSlots(usedSlots, availableSlots);

        return availableSlots;
    }

    /**
     * Fills the usedSlots map with the time slots of the given classes.
     *
     * @param classes    Iterable of RecurrentClass instances.
     * @param usedSlots  Map of the used time slots for each day of the week.
     */
    private void fillUsedRecurrentClassSlots(Iterable<RecurrentClass> classes, Map<RecurrentClassWeekDay,
            Map<LocalTime, LocalTime>> usedSlots) {
        for (RecurrentClass recurrentClass : classes) {
            // get the weekday of the class
            RecurrentClassWeekDay classWeekDay = recurrentClass.getWeekDay();
            // get the start and end time of the class
            LocalTime classStartTime = recurrentClass.getClassTime();
            Integer classDuration = recurrentClass.getClassDuration();
            LocalTime classEndTime = classStartTime.plusMinutes(classDuration);

            // check if the weekday is already in the map
            if (usedSlots.containsKey(classWeekDay)) {
                Map<LocalTime, LocalTime> usedSlotsForDay = usedSlots.get(classWeekDay);

                // check if the class start time is already in the map
                if (usedSlotsForDay.containsKey(classStartTime)) {
                    LocalTime usedClassEndTime = usedSlotsForDay.get(classStartTime);

                    // check if the end time of the class that is already in the map, is before the end time of the class we are checking
                    if (usedClassEndTime.isBefore(classEndTime)) {
                        usedSlotsForDay.replace(classStartTime, classEndTime);
                    }
                } else {
                    usedSlotsForDay.put(classStartTime, classEndTime);
                }
            } else {
                usedSlots.put(classWeekDay, new LinkedHashMap<>());
                usedSlots.get(classWeekDay).put(classStartTime, classEndTime);
            }
        }
    }

    /**
     Calculates the available recurrent class slots based on the used slots and duration.
     The method initializes the available slots with all the slots for each day of the week.
     It then removes the used slots from the available slots and updates the available slots map accordingly.
     @param usedSlots a Map containing the used slots for each day of the week
     @param availableSlots a Map containing the available slots for each day of the week
     */
    private void getAvailableSlots(Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> usedSlots,
                                   Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableSlots) {
        // initialize the available slots with all the slots
        initializeAvailableSlots(availableSlots);

        // if there are no used slots, return the slots as all available
        if (usedSlots.isEmpty()) {
            return;
        }

        // remove the used slots from the available slots
        removeUsedSlots(usedSlots, availableSlots);
    }

    /**
     * Initializes the available slots map with all possible slots based on a start time, end time, and duration.
     *
     * @param availableSlots  The map of available slots to initialize.
     */
    private void initializeAvailableSlots(Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableSlots) {
        for (RecurrentClassWeekDay recurrentClassWeekDay : RecurrentClassWeekDay.values()) {
            availableSlots.put(recurrentClassWeekDay, new LinkedHashMap<>());
            // fill with all the slots available
            LocalTime startTime = LocalTime.of(START_TIME_HOUR, START_TIME_MINUTE);

            // this is done because if the end time is 24:00, because the LocalTime class only goes up to 23:59
            LocalTime endTime;
            if (END_TIME_HOUR == 24) {
                endTime = LocalTime.of(23, 59);
            } else {
                endTime = LocalTime.of(END_TIME_HOUR, END_TIME_MINUTE);
            }

            while (startTime.isBefore(endTime)) {
                LocalTime availableStartTime = startTime;
                LocalTime availableEndTime = startTime.plusMinutes(SLOT_DURATION);
                if(availableEndTime.isAfter(endTime)) break;
                availableSlots.get(recurrentClassWeekDay).put(availableStartTime, availableEndTime);
                if(availableEndTime.isBefore(LocalTime.of(START_TIME_HOUR, START_TIME_MINUTE+SLOT_DURATION))) break;
                startTime = availableEndTime;
            }
        }
    }

    /**
     * Removes the used slots from the available slots.
     *
     * @param usedSlots       The map of used slots.
     * @param availableSlots  The map of available slots.
     */
    private void removeUsedSlots(Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> usedSlots,
                                 Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableSlots) {
        for (RecurrentClassWeekDay recurrentClassWeekDay : usedSlots.keySet()) {
            Map<LocalTime, LocalTime> usedSlotsForDay = usedSlots.get(recurrentClassWeekDay);
            Map<LocalTime, LocalTime> availableSlotsForDay = availableSlots.get(recurrentClassWeekDay);

            // Use an iterator to safely remove elements from the map while iterating over it
            Iterator<Map.Entry<LocalTime, LocalTime>> iterator = availableSlotsForDay.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<LocalTime, LocalTime> entry = iterator.next();
                LocalTime availableStartTime = entry.getKey();
                LocalTime availableEndTime = entry.getValue();

                for (LocalTime usedStartTime : usedSlotsForDay.keySet()) {
                    LocalTime usedEndTime = usedSlotsForDay.get(usedStartTime);

                    // Handle time ranges that end at midnight
                    if (usedEndTime.equals(LocalTime.MIDNIGHT)) {
                        if (availableEndTime.equals(LocalTime.MIDNIGHT)) {
                            iterator.remove();
                        }
                    } else {
                        if (availableStartTime.isAfter(usedStartTime) && availableStartTime.isBefore(usedEndTime)) {
                            iterator.remove();
                        } else if (availableEndTime.isAfter(usedStartTime) && availableEndTime.isBefore(usedEndTime)) {
                            iterator.remove();
                        } else if (availableStartTime.isBefore(usedStartTime) && availableEndTime.isAfter(usedEndTime)) {
                            iterator.remove();
                        } else if (availableStartTime.equals(usedStartTime) && availableEndTime.equals(usedEndTime)) {
                            iterator.remove();
                        }
                    }
                }
            }
        }
    }

    /**
     * Schedule a Recurrent Class using Recurrent Class Builder.
     * @param classTitle the title of the class
     * @param classDuration the duration of the class
     * @param recurrentClassTime the time of the class (hour)
     * @param recurrentClassWeekDay the day of the week of the class
     * @param classCourse the course of the class
     * @param teacher the teacher of the class
     * @return true if the class was scheduled successfully, false otherwise
     */
    public boolean scheduleRecurrentClass(String classTitle, Integer classDuration, String recurrentClassTime,
                                          RecurrentClassWeekDay recurrentClassWeekDay, Course classCourse,
                                          ECourseUser teacher, Iterable<Course> courses){
            boolean verify = validateRecurrentClass(recurrentClassTime, recurrentClassWeekDay, classCourse, teacher, courses);

            if(!verify) return false;

            RecurrentClassBuilder builder = new RecurrentClassBuilder(classRepository);
            RecurrentClass recurrentClass;
            try {
                recurrentClass = builder
                        .withCourse(classCourse)
                        .withClassTitle(classTitle)
                        .withClassDuration(classDuration)
                        .withRecurrentClassTime(recurrentClassTime)
                        .withRecurrentClassWeekDay(recurrentClassWeekDay)
                        .withTeacher(teacher)
                        .build();
            }catch (Exception e){
                return false;
            }

            classRepository.save(recurrentClass);

            return true;
    }

    /**
     * Validates a Recurrent Class.
     * @param recurrentClassTime the time of the class (hour)
     * @param recurrentClassWeekDay the day of the week of the class
     * @param classCourse the course of the class
     * @param teacher the teacher of the class
     * @return true if the class is valid, false otherwise
     */
    private boolean validateRecurrentClass(String recurrentClassTime, RecurrentClassWeekDay recurrentClassWeekDay,
                                           Course classCourse, ECourseUser teacher, Iterable<Course> courses) {
        Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableSlots = getAvailableCourseTeacherIntersectionRecurrentSlots(courses, teacher,classCourse);
        LocalTime time;
        if(recurrentClassTime.length() != 5){
            Integer hour = Integer.parseInt(recurrentClassTime.split(":")[0]);
            Integer minute = Integer.parseInt(recurrentClassTime.split(":")[1]);
            time = LocalTime.of(hour, minute);
        }else{
            time = LocalTime.parse(recurrentClassTime);
        }

        // null means the slot is not available
        if(availableSlots.get(recurrentClassWeekDay) == null || availableSlots.get(recurrentClassWeekDay).get(time) == null) return false;

        return true;
    }

    /**
     * Gets the available slots for an extra class in a time period.
     *
     * @param courses the teacher's courses
     * @param chosenCourse the chosen course
     * @param startDay the start day of the interval
     * @param endDay the end day of the interval
     * @return a map with the available slots
     */
    public Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> getAvailableExtraClassSlots(Iterable<Course> courses,
                                                                                             Course chosenCourse,
                                                                                             LocalDateTime startDay,
                                                                                             LocalDateTime endDay,
                                                                                             ECourseUser teacher) {

            List<DayOfWeek> daysOfWeek = new ArrayList<>();
            getDaysOfWeekBetween(startDay, endDay, daysOfWeek);

            Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableSlots = getAvailableCourseTeacherIntersectionRecurrentSlots(courses, teacher, chosenCourse);
            removeOccurrencesThatWereUpdated(availableSlots,courses, teacher, chosenCourse, startDay, endDay);

            Iterable<ExtraClass> extraClasses = getAllExtraClassesFromCourseAndTeacher(chosenCourse, teacher, startDay, endDay);

            Map<RecurrentClassWeekDay, Map<LocalTime,LocalTime>> availableExtraSlotsForDay = getAvailableExtraSlots(extraClasses);

            if(availableExtraSlotsForDay == null) return null;

            removeSlotsIntersection(availableSlots, availableExtraSlotsForDay);
            removeUnnecessaryDaySlots(availableSlots, daysOfWeek, startDay);


            return availableSlots;
    }

    private void removeOccurrencesThatWereUpdated(Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableSlots,
                                                  Iterable<Course> courses, ECourseUser teacher, Course chosenCourse,
                                                  LocalDateTime startDay, LocalDateTime endDay) {

        // get all recurrent classes
        List<RecurrentClass> allClasses = getAllRecurrentClassesFromCourseAndTeacher(chosenCourse, teacher, courses);

        Map<RecurrentClassWeekDay,Map<LocalTime, LocalTime>> usedSlots = new LinkedHashMap<>();

        // for every class, check for occurrences from the start day to the end day, and remove them from the available slots
        for(RecurrentClass recurrent : allClasses){
            List<ClassOccurrence> occurrences = recurrent.getUpdatedOccurrences();

            if(occurrences.isEmpty()) continue;

            for(ClassOccurrence occurrence : occurrences) {
                RecurrentClassWeekDay day = getRecurrentWeekDayFromDayOfWeek(occurrence.getOldDate().getDayOfWeek());
                if(day == null) continue;
                LocalTime startTime = occurrence.getOldDate().toLocalTime();

                if (occurrence.getOldDate().isAfter(startDay) && occurrence.getOldDate().isBefore(endDay)) {
                    // add the available slot back

                    if (availableSlots.get(day) == null) availableSlots.put(day, new LinkedHashMap<>());
                    while (!startTime.isAfter(occurrence.getOldDate().toLocalTime().plusMinutes(recurrent.getClassDuration()))) {
                        availableSlots.get(day).put(startTime, startTime.plusMinutes(SLOT_DURATION));
                        startTime = startTime.plusMinutes(SLOT_DURATION);
                    }
                }

                // add slot if in the current week
                if (occurrence.getNewDate().isAfter(startDay) && occurrence.getNewDate().isBefore(endDay)) {


                    // add the new used slot to the map
                    RecurrentClassWeekDay classWeekDay = getRecurrentWeekDayFromDayOfWeek(occurrence.getNewDate().getDayOfWeek());
                    if(classWeekDay == null) continue;
                    // get the start and end time of the class
                    LocalTime classStartTime = occurrence.getNewDate().toLocalTime();
                    Integer classDuration = occurrence.getNewDuration();
                    LocalTime classEndTime = classStartTime.plusMinutes(classDuration);

                    // check if the weekday is already in the map
                    if (usedSlots.containsKey(classWeekDay)) {
                        Map<LocalTime, LocalTime> usedSlotsForDay = usedSlots.get(classWeekDay);

                        // check if the class start time is already in the map
                        if (usedSlotsForDay.containsKey(classStartTime)) {
                            LocalTime usedClassEndTime = usedSlotsForDay.get(classStartTime);

                            // check if the end time of the class that is already in the map, is before the end time of the class we are checking
                            if (usedClassEndTime.isBefore(classEndTime)) {
                                usedSlotsForDay.replace(classStartTime, classEndTime);
                            }
                        } else {
                            usedSlotsForDay.put(classStartTime, classEndTime);
                        }
                    } else {
                        usedSlots.put(classWeekDay, new LinkedHashMap<>());
                        usedSlots.get(classWeekDay).put(classStartTime, classEndTime);
                    }

                }
            }

        }

        // remove the used slots from the available slots
        removeUsedSlots(usedSlots, availableSlots);

    }

    private List<RecurrentClass> getAllRecurrentClassesFromCourseAndTeacher(Course chosenCourse, ECourseUser teacher,
                                                                            Iterable<Course> courses) {
        // add all recurrent classes from the chosen course
        Iterable<RecurrentClass> cc = classRepository.findCourseRecurrentClasses(chosenCourse.identity().toString());
        List<RecurrentClass> courseClasses = new ArrayList<>();
        if(cc != null) {
            for (RecurrentClass teacherClass : cc){
                courseClasses.add(teacherClass);
            }
        }


        // add all recurrent classes from other courses where the teacher is involved
        List<RecurrentClass> teacherClasses = new ArrayList<>();
        for (Course course : courses){
            if(!course.identity().equals(chosenCourse.identity())){
                Iterable<RecurrentClass> tc = classRepository.findCourseRecurrentClassesWhereTeacherInvolved(course.identity().toString(), teacher);
                if(tc == null) continue;
                for (RecurrentClass teacherClass : tc){
                    teacherClasses.add(teacherClass);
                }
            }
        }

        // add all classes to a list
        List<RecurrentClass> allClasses = new ArrayList<>();
        allClasses.addAll(courseClasses);
        allClasses.addAll(teacherClasses);

        return allClasses;
    }

    /**
     * Gets all the extra class from a course, and the extra classes where the teacher is involved
     *
     * @param chosenCourse the chosen course
     * @param teacher the teacher that is scheduling
     */
    private Iterable<ExtraClass> getAllExtraClassesFromCourseAndTeacher(Course chosenCourse, ECourseUser teacher,
                                                                        LocalDateTime startDay, LocalDateTime endDay) {
        Iterable<ExtraClass> classes1;
        Iterable<ExtraClass> classes2;

        // GET THE TIMES OF THE TEACHER COURSE CLASSES
        try{
            classes1 = classRepository.findExtraClassByDateRangeAndTeacherWithNonMatchingCourse(chosenCourse.identity().toString(), startDay, endDay, teacher);
            classes2 = classRepository.findExtraClassByCourseCodeAndDateRange(chosenCourse.identity().toString(), startDay, endDay);
        } catch (Exception e){
            return null;
        }

        if(classes1 == null || classes2 == null) return null;

        List<ExtraClass> classes = new ArrayList<>();
        classes1.forEach(classes::add);
        classes2.forEach(classes::add);

        return classes;
    }

    /**
     * Removes the Days of the Week that are not in the interval.
     *
     * @param availableSlots the available slots
     * @param daysOfWeek the list of days of the week that should be in the slots
     */
    private void removeUnnecessaryDaySlots(Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableSlots,
                                           List<DayOfWeek> daysOfWeek, LocalDateTime startDay) {
        Set<RecurrentClassWeekDay> keysToRemove = new HashSet<>();

        for (RecurrentClassWeekDay day : availableSlots.keySet()) {
            if (!daysOfWeek.contains(getDayOfWeekFromRecurrentClassWeekDay(day))) {
                keysToRemove.add(day);
            }
            if(startDay.isBefore(LocalDateTime.now())){
                if (getDayOfWeekFromRecurrentClassWeekDay(day) == LocalDateTime.now().getDayOfWeek()) {
                    // remove the slots that are before the time now
                    Map<LocalTime, LocalTime> slots = availableSlots.get(day);
                    Set<LocalTime> slotsToRemove = new HashSet<>();
                    for (LocalTime slot : slots.keySet()) {
                        if (slot.isBefore(LocalTime.now())) {
                            slotsToRemove.add(slot);
                        }
                    }
                    for (LocalTime slot : slotsToRemove) {
                        slots.remove(slot);
                    }
                }
            }
        }

        for (RecurrentClassWeekDay key : keysToRemove) {
            availableSlots.remove(key);
        }
    }

    /**
     * Get Day Of Week From Recurrent Class Week Day.
     *
     * @param day the day of the week
     * @return the day of the week
     */
    private DayOfWeek getDayOfWeekFromRecurrentClassWeekDay(RecurrentClassWeekDay day) {
        switch (day) {
            case MONDAY:
                return DayOfWeek.MONDAY;
            case TUESDAY:
                return DayOfWeek.TUESDAY;
            case WEDNESDAY:
                return DayOfWeek.WEDNESDAY;
            case THURSDAY:
                return DayOfWeek.THURSDAY;
            case FRIDAY:
                return DayOfWeek.FRIDAY;
            default:
                return null;
        }
    }

    /**
     * Gets the days of the week between two dates.
     *
     * @param startDay the start day
     * @param endDay the end day
     * @param daysOfWeek the list of days of the week
     */
    private void getDaysOfWeekBetween(LocalDateTime startDay, LocalDateTime endDay, List<DayOfWeek> daysOfWeek) {
        LocalDateTime currentDay = startDay;
        while (currentDay.isEqual(endDay) || currentDay.isBefore(endDay)) {
            daysOfWeek.add(currentDay.getDayOfWeek());
            currentDay = currentDay.plusDays(1);
        }
    }

    /**
     *  Gets the times of the teacher and course classes for a week day.
     *
     * @param classes the classes of the teacher and course
     * @return a map with the available slots
     */
    private Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> getAvailableExtraSlots(Iterable<ExtraClass> classes){

        if(classes == null) return null;

        // map of the used slots for each day (eg. Monday: 10:00 - 11:00, 11:00 - 12:00)
        Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> usedSlots = new LinkedHashMap<>();

        // GET THE USED SLOTS FOR EACH DAY
        fillUsedExtraClassSlots(classes,usedSlots);

        // map of the available slots for each day (eg. Monday: 10:00 - 11:00, 11:00 - 12:00)
        Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableSlots = new LinkedHashMap<>();

        // GET THE AVAILABLE SLOTS FOR EACH DAY
        getAvailableSlots(usedSlots, availableSlots);

        return availableSlots;
    }

    /**
     * Gets the equivalent recurrent class week day from a day of the week.
     *
     * @param dayOfWeek the day of the week
     * @return the equivalent recurrent class week day
     */
    private RecurrentClassWeekDay getRecurrentWeekDayFromDayOfWeek(DayOfWeek dayOfWeek){
        switch (dayOfWeek){
            case MONDAY:
                return RecurrentClassWeekDay.MONDAY;
            case TUESDAY:
                return RecurrentClassWeekDay.TUESDAY;
            case WEDNESDAY:
                return RecurrentClassWeekDay.WEDNESDAY;
            case THURSDAY:
                return RecurrentClassWeekDay.THURSDAY;
            case FRIDAY:
                return RecurrentClassWeekDay.FRIDAY;
            default:
                return null;
        }
    }

    /**
     * Fills the usedSlots map with the time slots of the given classes.
     *
     * @param classes    Iterable of ExtraClass instances.
     * @param usedSlots  Map of the used time slots for each day of the week.
     */
    private void fillUsedExtraClassSlots(Iterable<ExtraClass> classes, Map<RecurrentClassWeekDay,
            Map<LocalTime, LocalTime>> usedSlots) {
        for (ExtraClass extraClass : classes) {
            // get the weekday of the class
            RecurrentClassWeekDay classWeekDay = getRecurrentWeekDayFromDayOfWeek(extraClass.getsDate().getDayOfWeek());
            if(classWeekDay == null) continue;
            // get the start and end time of the class
            LocalTime classStartTime = extraClass.getsDate().toLocalTime();
            Integer classDuration = extraClass.getClassDuration();
            LocalTime classEndTime = classStartTime.plusMinutes(classDuration);

            // check if the weekday is already in the map
            if (usedSlots.containsKey(classWeekDay)) {
                Map<LocalTime, LocalTime> usedSlotsForDay = usedSlots.get(classWeekDay);

                // check if the class start time is already in the map
                if (usedSlotsForDay.containsKey(classStartTime)) {
                    LocalTime usedClassEndTime = usedSlotsForDay.get(classStartTime);

                    // check if the end time of the class that is already in the map, is before the end time of the class we are checking
                    if (usedClassEndTime.isBefore(classEndTime)) {
                        usedSlotsForDay.replace(classStartTime, classEndTime);
                    }
                } else {
                    usedSlotsForDay.put(classStartTime, classEndTime);
                }
            } else {
                usedSlots.put(classWeekDay, new LinkedHashMap<>());
                usedSlots.get(classWeekDay).put(classStartTime, classEndTime);
            }
        }
    }

    /**
     * Schedule extra class with extra class builder.
     * @param courses the teacher courses
     * @param chosenCourse the chosen course
     * @param startWeekDay the start week day of the chosen week
     * @param endWeekDay the end week day of the chosen week
     * @param weekDay the chosen week day
     * @param startTime the chosen start time
     * @param duration the chosen duration
     * @param title the chosen title
     * @param participants the chosen participants
     * @param eCourseUser the chosen teacher
     * @return true if the extra class was scheduled, false otherwise
     */
    public boolean scheduleExtraClass(Iterable<Course> courses, Course chosenCourse, LocalDateTime startWeekDay,
                                      LocalDateTime endWeekDay, RecurrentClassWeekDay weekDay, LocalTime startTime,
                                      Integer duration, String title, Iterable<ECourseUser> participants,
                                      ECourseUser eCourseUser) {
        boolean verify = validateExtraClass(startTime,weekDay,chosenCourse,eCourseUser,courses,startWeekDay,endWeekDay);

        if(!verify) return false;

        LocalDateTime classDate = getLocalDateTimeFromRecurrentClassWeekDayAndTime(weekDay,startTime, startWeekDay, endWeekDay);

        List<ECourseUser> participantsList = new ArrayList<>();
        participants.forEach(participantsList::add);

        ExtraClassBuilder extraClassBuilder = new ExtraClassBuilder(classRepository);
        ExtraClass extraClass;
        try{
            extraClass = extraClassBuilder.withCourse(chosenCourse)
                    .withClassTitle(title)
                    .withClassDuration(duration)
                    .withTeacher(eCourseUser)
                    .withExtraClassParticipants(participantsList)
                    .withExtraClassDate(classDate)
                    .build();
        } catch (Exception e) {
            return false;
        }

        classRepository.save(extraClass);

        return true;
    }

    /**
     * Gets the LocalDateTime from a week, giving the week, the week day and the time.
     * @param weekDay the chosen week day
     * @param startTime the start time of the class
     * @param startWeekDay the start week day of the chosen week
     * @param endWeekDay the end week day of the chosen week
     * @return the LocalDateTime of the class
     */
    private LocalDateTime getLocalDateTimeFromRecurrentClassWeekDayAndTime(RecurrentClassWeekDay weekDay,
                                                                           LocalTime startTime,
                                                                           LocalDateTime startWeekDay,
                                                                           LocalDateTime endWeekDay) {
        LocalDateTime classDate = startWeekDay;
        while (classDate.isEqual(endWeekDay) || classDate.isBefore(endWeekDay)) {
            if (classDate.getDayOfWeek().equals(getDayOfWeekFromRecurrentClassWeekDay(weekDay))) {
                classDate = classDate.withHour(startTime.getHour());
                classDate = classDate.withMinute(startTime.getMinute());
                classDate = classDate.withSecond(0);
                break;
            }
            classDate = classDate.plusDays(1);
        }
        return classDate;
    }

    /**
     * Validates the title of the class.
     *
     * @param title the title of the class
     * @return true if the title is valid, false otherwise
     */
    public boolean validateClassTitle(String title) {
        if (title == null || title.isEmpty()) {
            return false;
        }

        try {
            RecurrentClass rc = classRepository.findRecurrentClassByTitle(title);
            ExtraClass ec = classRepository.findExtraClassByTitle(title);

            if (rc != null || ec != null) {
                return false;
            }

        }catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Validates a Extra CLass
     * @param extraClassTime the time of the extra class
     * @param extraClassWeekDay the day of the extra class
     * @param classCourse the course of the extra class
     * @param teacher the teacher of the extra class
     * @param courses the courses of the teacher
     * @param startDay the start day of the extra class
     * @param endDay the end day of the extra class
     * @return true if the extra class is valid, false otherwise
     */
    public boolean validateExtraClass(LocalTime extraClassTime, RecurrentClassWeekDay extraClassWeekDay,
                                      Course classCourse, ECourseUser teacher, Iterable<Course> courses,
                                      LocalDateTime startDay,LocalDateTime endDay){

        Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableSlots = getAvailableExtraClassSlots(courses, classCourse,startDay,endDay,teacher);

        if(availableSlots == null) return false;

        // null means the slot is not available
        if(availableSlots.get(extraClassWeekDay) == null || availableSlots.get(extraClassWeekDay).get(extraClassTime) == null) return false;

        return true;
    }

    /**
     * Updates an Occurrence of a recurrent class
     * @param teacherCourses the teacher courses
     * @param selectedClass the chosen recurrent class
     * @param startDay the start day of the chosen week
     * @param endDay the end day of the chosen week
     * @param day the chosen week day
     * @param startTime the chosen start time
     * @param classOldDate the old date of the class
     * @param teacher the chosen teacher
     * @return  true if the class was updated, false otherwise
     */
    public boolean updateClass(Iterable<Course> teacherCourses, RecurrentClass selectedClass, LocalDateTime startDay,
                               LocalDateTime endDay, RecurrentClassWeekDay day, LocalTime startTime,
                               LocalDateTime classOldDate, ECourseUser teacher, Integer duration) {

        boolean verify = validateExtraClass(startTime, day, selectedClass.getClassCourse(),teacher,teacherCourses,startDay,endDay);

        if(!verify) return false;

        LocalDateTime classDate = getLocalDateTimeFromRecurrentClassWeekDayAndTime(day,startTime,startDay,endDay);

        updateClassOccurrence(selectedClass, classOldDate, classDate, duration);

        classRepository.save(selectedClass);

        return true;
    }

    /**
     * Updates the occurrences of a recurrent class.
     * @param selectedClass the chosen recurrent class
     * @param classOldDate the old date of the class
     * @param classDate the new date of the class
     */
    private void updateClassOccurrence(RecurrentClass selectedClass, LocalDateTime classOldDate,
                                       LocalDateTime classDate,
                                       Integer duration) {

        List<ClassOccurrence> updatedOccurrences = selectedClass.getUpdatedOccurrences();

        for (ClassOccurrence occurrence : updatedOccurrences) {
            if (occurrence.getNewDate().equals(classOldDate)) {
                occurrence.update(classDate, duration);
                return;
            }
        }

        ClassOccurrence occurrence = new ClassOccurrence(classDate, classOldDate, duration);
        updatedOccurrences.add(occurrence);
    }

    /**
     * Gets the occurrences of a recurrent class.
     * @param selectedClass the chosen recurrent class
     * @return the occurrences of the recurrent class
     */
    public List<LocalDateTime> getClassOccurrences(RecurrentClass selectedClass) {

        List<LocalDateTime> classOccurrences = getDatesForDayOfWeekAndTime(getDayOfWeekFromRecurrentClassWeekDay(selectedClass.getWeekDay()), selectedClass.getClassTime());

        // remove occurrences that were changed
        List<ClassOccurrence> updatedOccurrences = selectedClass.getUpdatedOccurrences();

        for (ClassOccurrence occurrence : updatedOccurrences) {
            classOccurrences.remove(occurrence.getOldDate());
            classOccurrences.add(occurrence.getNewDate());
        }

        return classOccurrences;
    }

    /**
     * Gets all the days that match the dayOfWeek and time, from now to a number of weeks from now (defined by config file).
     *
     * @param dayOfWeek the day of the week
     * @param time the time
     * @return the list of dates
     */
    private List<LocalDateTime> getDatesForDayOfWeekAndTime(DayOfWeek dayOfWeek, LocalTime time) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = now.plusWeeks(NUMBER_OF_WEEKS_FROM_NOW);

        List<LocalDateTime> dates = new ArrayList<>();
        LocalDateTime current = now.with(TemporalAdjusters.nextOrSame(dayOfWeek)).with(time);

        while (!current.isAfter(end)) {
            dates.add(current);
            current = current.plusWeeks(1);
        }

        return dates;
    }

    /**
     * Gets the available slots to update an occurrence of a recurrent class to.
     * @param courses the courses of the teacher
     * @param selectedClass the selected recurrent class
     * @param startDay the start of week
     * @param endDay the end of week
     * @param classOldDate the old date of the class
     * @return the available slots
     */
    public Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> getAvailableUpdateSlots(Iterable<Course> courses,
                                                                                         RecurrentClass selectedClass,
                                                                                         LocalDateTime startDay,
                                                                                         LocalDateTime endDay,
                                                                                         LocalDateTime classOldDate,
                                                                                         ECourseUser teacher) {

            Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableSlots = getAvailableExtraClassSlots(courses, selectedClass.getClassCourse(),startDay,endDay,teacher);

            if(availableSlots == null) return null;

            // remove the slots occupied by the selected occurrence, since it is being updated
            if(!(classOldDate.isBefore(startDay) || classOldDate.isAfter(endDay))){
                 AddOccupiedClassSlotsToAvailableSlots(availableSlots, selectedClass, classOldDate);
            }

            return availableSlots;

    }

    /**
     * Adds the occupied slots of a recurrent class to the available slots.
     * @param availableSlots the available slots
     * @param selectedClass the selected recurrent class
     * @param classOldDate the old date of the class
     */
    private void AddOccupiedClassSlotsToAvailableSlots(Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableSlots,
                                                       RecurrentClass selectedClass, LocalDateTime classOldDate) {

        Iterable<ClassOccurrence> occurrences = selectedClass.getUpdatedOccurrences();

        ClassOccurrence selectedOccurrence = null;
        for(ClassOccurrence occurrence : occurrences){
            LocalDateTime occurrenceDate = occurrence.getNewDate().withSecond(0).withNano(0);
            LocalDateTime oldDate = classOldDate.withSecond(0).withNano(0);
            if(occurrenceDate.equals(oldDate)){
                selectedOccurrence = occurrence;
                break;
            }
        }

        LocalTime endTime;
        LocalTime classTime;
        DayOfWeek dayOfWeek;

        if(selectedOccurrence == null){
            classTime = selectedClass.getClassTime();
            endTime = classOldDate.toLocalTime().plusMinutes(selectedClass.getClassDuration());
            dayOfWeek = getDayOfWeekFromRecurrentClassWeekDay(selectedClass.getWeekDay());
        }
        else{
            classTime = selectedOccurrence.getNewDate().toLocalTime();
            endTime = selectedOccurrence.getNewDate().toLocalTime().plusMinutes(selectedOccurrence.getNewDuration());
            dayOfWeek = selectedOccurrence.getNewDate().getDayOfWeek();
        }


        // ADD THE USED SLOTS TO THE AVAILABLE SLOTS AGAIN
        if(availableSlots.containsKey(getRecurrentWeekDayFromDayOfWeek(dayOfWeek))){
            while(classTime.getHour() != endTime.getHour() || classTime.getMinute() != endTime.getMinute()){

                availableSlots.get(getRecurrentWeekDayFromDayOfWeek(dayOfWeek))
                        .put(
                            classTime,
                            classTime.plusMinutes(SLOT_DURATION)
                        );
                classTime = classTime.plusMinutes(SLOT_DURATION);
            }
        }

    }
}
