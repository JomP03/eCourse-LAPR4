package ui.classes.scheduleextraclass;


import appsettings.Application;
import classmanagement.application.extra.ScheduleExtraClassController;
import classmanagement.domain.RecurrentClassWeekDay;
import coursemanagement.domain.Course;
import ecourseusermanagement.domain.ECourseUser;
import persistence.PersistenceContext;
import ui.components.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ExtraClassScheduleUI extends AbstractUI {

    private final ScheduleExtraClassController scheduleController = new ScheduleExtraClassController(
            PersistenceContext.repositories().classes(),
            PersistenceContext.repositories().eCourseUsers(),
            PersistenceContext.repositories().courses()
    );

    private final Integer SLOT_VALUE = Application.settings().getSlotDurationTime();

    private final Integer START_TIME_HOUR;

    private final Integer START_TIME_MINUTE;

    private final Integer END_TIME_HOUR;

    private final Integer END_TIME_MINUTE;

    private final Integer MAX_CLASS_DURATION = Application.settings().getMaxClassDuration();

    private final Integer ONE_SECOND = 1000;

    public ExtraClassScheduleUI() {
        // Get the start and end time from the config file
        String[] start = Application.settings().getStartTime().split(":");
        String[] end = Application.settings().getEndTime().split(":");
        this.START_TIME_HOUR = Integer.parseInt(start[0]);
        this.START_TIME_MINUTE = Integer.parseInt(start[1]);
        this.END_TIME_HOUR = Integer.parseInt(end[0]);
        this.END_TIME_MINUTE = Integer.parseInt(end[1]);
    }

    @Override
    protected boolean doShow() {

        List<Course> teacherCourses;
        Course selectedCourse;
        Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableSlots;
        List<LocalDateTime> chosenDates = new ArrayList<>();

        try {
            teacherCourses = (List<Course>) scheduleController.getAvailableTeacherCourses();

            if (teacherCourses.isEmpty()) {
                errorMessage("You don't have any courses available!");
                Sleeper.sleep(ONE_SECOND);
                return false;
            }

            selectedCourse = menuToSelectCourse(teacherCourses);
            if(selectedCourse == null) return false;

            availableSlots = selectWeekSlots(selectedCourse,teacherCourses,chosenDates);
            if(availableSlots == null) return false;

        } catch (Exception e) {
            errorMessage(e.getMessage());
            Sleeper.sleep(ONE_SECOND);
            return false;
        }

        try {

            RecurrentClassWeekDay day = menuToSelectDayOfWeek(availableSlots);
            if(day == null) return false;

            LocalTime startTime = menuToSelectStartTime(availableSlots.get(day));
            if(startTime == null) return false;

            Integer duration = menuToSelectAValidEndTime(availableSlots.get(day), startTime);
            if(duration == null) return false;

            System.out.println();
            String title = writeClassTitle();

            Iterable<ECourseUser> participants = selectClassParticipants(selectedCourse);
            if(participants == null) return false;


            boolean verify = scheduleController.scheduleExtraClass(teacherCourses,selectedCourse, chosenDates.get(0),
                    chosenDates.get(1), day, startTime, duration, title, participants
            );

            if (!verify) {
                errorMessage("There was an error scheduling the class!");
                Sleeper.sleep(ONE_SECOND);
                return false;
            }

            successMessage("Class scheduled successfully for " + day + " at " + startTime + " until " + startTime.plusMinutes(duration) + "!");
            Sleeper.sleep(ONE_SECOND);
            return true;

        } catch (Exception e) {
            errorMessage(e.getMessage());
            Sleeper.sleep(ONE_SECOND);
            return false;
        }

    }

    /**
     * Method that makes the user write the class title
     * @return the class title
     */
    private String writeClassTitle() {

        String title = Console.readLine("Please provide a " + ColorCode.BLUE.getValue() + "title for the class" +
                ColorCode.RESET.getValue() + ": ");

        while(!scheduleController.validateClassTitle(title)) {
            errorMessage("Invalid title!");
            Sleeper.sleep(ONE_SECOND);
            title = Console.readLine("Please provide a " + ColorCode.BLUE.getValue() + "title for the class" +
                    ColorCode.RESET.getValue() + ": ");
        }

        return title;
    }

    /**
     *
     * Method that makes the user select the students that will participate in the class
     *
     * @param selectedCourse the selected course
     * @return the list of selected students from the selected course
     */
    private Iterable<ECourseUser> selectClassParticipants(Course selectedCourse) {

        Iterable<ECourseUser> students = scheduleController.getStudentsFromCourse(selectedCourse.identity().toString());

        // Ask if teacher wants to add all course students to the class
        List<String> options = new ArrayList<>();
        options.add("Yes");
        options.add("No");

        System.out.println();
        ListSelector<String> selector = new ListSelector<>("Do you want to add  " + ColorCode.BLUE.getValue() +
                "all students" + ColorCode.RESET.getValue() + " as participants.", options);

        if (!selector.showAndSelectWithExit()) return null;
        if(selector.getSelectedElement().equals("Yes")){
            return students;
        }else {
            return selectStudents(students);
        }
    }

    /**
     *  Method that makes the user select the students that will participate in the class
     *
     * @param students the list of students
     * @return the list of selected students
     */
    private Iterable<ECourseUser> selectStudents(Iterable<ECourseUser> students) {

            List<ECourseUser> selectedStudents = new ArrayList<>();

            List<ECourseUser> allStudents = new ArrayList<>();
            students.forEach(allStudents::add);

            boolean again = true;

            while(again){
                System.out.println();
                ListSelector<ECourseUser> selector = new ListSelector<>("Select a " + ColorCode.BLUE.getValue() +
                        "student:" + ColorCode.RESET.getValue(), allStudents);

                if (!selector.showAndSelectWithExit()) return null;

                selectedStudents.add(selector.getSelectedElement());
                allStudents.remove(selector.getSelectedElement());

                List<String> options = new ArrayList<>();
                options.add("Yes");
                options.add("No");

                System.out.println();
                ListSelector<String> selector2 = new ListSelector<>("Do you want to add another student?", options);
                if (!selector2.showAndSelectWithExit()) return null;

                if(selector2.getSelectedElement().equals("No")){
                    again = false;
                }

            }



            return selectedStudents;
    }

    /**
     * Method that makes the user select a week to schedule the class
     *
     * @param selectedCourse the course selected by the user to schedule the class
     * @param teacherCourses the courses of the teacher
     * @return a map with the available slots for the selected week
     */
    private Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> selectWeekSlots(Course selectedCourse,
                                                                                  Iterable<Course> teacherCourses,
                                                                                  List<LocalDateTime> chosenDates) {

        LocalDateTime currentDate = LocalDateTime.now().withSecond(0).withNano(0);
        if(currentDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) || currentDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            currentDate = currentDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).withHour(0).withMinute(0).withSecond(0);
        }
        LocalDateTime nextSaturday = currentDate.with(TemporalAdjusters.next(DayOfWeek.SATURDAY)).withHour(0).withMinute(0).withSecond(0);
        ArrayList<LocalDateTime> previousCurrentDates = new ArrayList<>();

        ArrayList<String> options = new ArrayList<>();
        options.add("Schedule in this week");
        options.add("See next week");
        options.add("See past week");

        ListSelector<String> selector = new ListSelector<>("Select a " + ColorCode.BLUE.getValue() + "option:" +
                ColorCode.RESET.getValue(), options);

        return menuToChooseTheWeekToSchedule(
                selectedCourse, teacherCourses, currentDate, nextSaturday, selector, chosenDates, previousCurrentDates);
    }

    private Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> menuToChooseTheWeekToSchedule(Course selectedCourse,
                                                                                                Iterable<Course> teacherCourses,
                                                                                                LocalDateTime currentDate,
                                                                                                LocalDateTime nextSaturday,
                                                                                                ListSelector<String> selector,
                                                                                                List<LocalDateTime> chosenDates,
                                                                                                List<LocalDateTime> previousCurrentDates) {
        Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableSlots = null;
        boolean again = true;

        while(again) {

            System.out.println();
            if(currentDate.getDayOfWeek().equals(DayOfWeek.FRIDAY)){
                System.out.print("Week from " + currentDate.with(
                        TemporalAdjusters.previous(DayOfWeek.MONDAY)).toLocalDate().getDayOfMonth() + " to " +
                        currentDate.toLocalDate().getDayOfMonth() + " of " + currentDate.toLocalDate().getMonth() + " :");

            } else if(currentDate.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
                System.out.print("Week from "
                        + currentDate.toLocalDate().getDayOfMonth()
                        + " to " + currentDate.with(TemporalAdjusters.next(DayOfWeek.FRIDAY)).toLocalDate().getDayOfMonth()
                        + " of " + currentDate.toLocalDate().getMonth() + " :");
            } else {
                System.out.print("Week from " +
                        currentDate.with(TemporalAdjusters.previous(DayOfWeek.MONDAY)).toLocalDate().getDayOfMonth() +
                        " to " + currentDate.with(TemporalAdjusters.next(DayOfWeek.FRIDAY)).toLocalDate().getDayOfMonth()
                        + " of " + currentDate.toLocalDate().getMonth() + " :");
            }
            System.out.println();

            again = false;
            availableSlots = scheduleController.getAvailableExtraClassSlots(teacherCourses, selectedCourse, currentDate, nextSaturday);

            if (availableSlots.isEmpty()) {
                errorMessage("There are no available slots in this week for this course!");
                Sleeper.sleep(ONE_SECOND);
                System.out.println();
                if (!selector.showAndSelectWithExit()) return null;
                if(selector.getSelectedElement().equals("See next week")) {
                    again = true;
                    previousCurrentDates.add(currentDate);
                    currentDate = currentDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).withHour(0).withMinute(0).withSecond(0);
                    nextSaturday = nextSaturday.plusDays(7);
                }
                if(selector.getSelectedElement().equals("See past week")) {
                    again = true;
                    if(previousCurrentDates.size() == 0) {
                        errorMessage("There are no previous weeks!");
                        Sleeper.sleep(ONE_SECOND);
                    }else{
                        currentDate = previousCurrentDates.get(previousCurrentDates.size() - 1);
                        previousCurrentDates.remove(previousCurrentDates.size() - 1);
                        nextSaturday = nextSaturday.minusDays(7);
                    }
                }
                continue;
            }

            printAvailableSlots(availableSlots);

            System.out.println();
            if (!selector.showAndSelectWithExit()) return null;
            if(selector.getSelectedElement().equals("See next week")) {
                again = true;
                previousCurrentDates.add(currentDate);
                currentDate = currentDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).withHour(0).withMinute(0).withSecond(0);
                nextSaturday = nextSaturday.plusDays(7);
            }
            if(selector.getSelectedElement().equals("See past week")) {
                again = true;
                if(previousCurrentDates.size() == 0) {
                    errorMessage("There are no previous weeks!");
                    Sleeper.sleep(ONE_SECOND);
                }else {
                    currentDate = previousCurrentDates.get(previousCurrentDates.size() - 1);
                    previousCurrentDates.remove(previousCurrentDates.size() - 1);
                    nextSaturday = nextSaturday.minusDays(7);
                }
            }
        }
        chosenDates.add(currentDate);
        chosenDates.add(nextSaturday);
        return availableSlots;
    }


    /**
     * Shows a menu with the teacher courses, so the teacher can select where to schedule the class.
     * @param teacherCourses the teacher courses
     * @return the course selected
     */
    private Course menuToSelectCourse(Iterable<Course> teacherCourses) {

            //array with the available courses for the menu
            List<Course> options = new ArrayList<>();
            for(Course course : teacherCourses) {
                if(course.isInProgress()){
                    options.add(course);
                }
            }

            if(options.isEmpty()) {
                errorMessage("There are no courses in progress!");
                Sleeper.sleep(ONE_SECOND);
                return null;
            }

            System.out.println();
            // menu to select the course
            ListSelector<Course> selector = new ListSelector<>("Select a " + ColorCode.BLUE.getValue() +
                    "course:" + ColorCode.RESET.getValue(), options);

            if(!selector.showAndSelectWithExit()) return null;
            return selector.getSelectedElement();
    }

    /**
     * Shows a menu to select a valid day of the week for the class.
     * @param availableSlots the map of the available slots for the course
     * @return the day of the week selected
     */
    private RecurrentClassWeekDay menuToSelectDayOfWeek(Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableSlots) {

        //array with the available days of the week for the menu
        List<RecurrentClassWeekDay> options = new ArrayList<>(availableSlots.keySet());

        System.out.println();
        // menu to select the day of the week
        ListSelector<RecurrentClassWeekDay> selector = new ListSelector<>("Select a " + ColorCode.BLUE.getValue() +
                "day of the week" + ColorCode.RESET.getValue() +
                " for the class:", options);

        if(!selector.showAndSelectWithExit())return null;
        return selector.getSelectedElement();
    }

    /**
     * Shows a menu to select a valid start time for the class.
     @param localTimeLocalTimeMap the map of the available slots for the selected day
     @return the start time selected
     */
    private LocalTime menuToSelectStartTime(Map<LocalTime, LocalTime> localTimeLocalTimeMap) {

        //array with the available start times for the menu
        List<LocalTime> options = new ArrayList<>(localTimeLocalTimeMap.keySet());
        Collections.sort(options);

        System.out.println();
        // menu to select the start time
        ListSelector<LocalTime> selector = new ListSelector<>("Select a " + ColorCode.BLUE.getValue() + "Start Time" + ColorCode.RESET.getValue() + " for the class:", options);
        if(!selector.showAndSelectWithExit()) return null;
        return selector.getSelectedElement();
    }

    /**
     * Shows a menu to select a valid end time for the class.
     * Calculates the duration in minutes between the start time and the end time selected.
     @param localTimeLocalTimeMap the map of the available slots for the selected day
     @param startTime the start time selected
     @return the duration in minutes between the start time and the end time selected
     */
    private Integer menuToSelectAValidEndTime(Map<LocalTime, LocalTime> localTimeLocalTimeMap, LocalTime startTime) {
        int slotDuration = SLOT_VALUE;

        // arrange the available end times for the menu
        List<LocalTime> options = new ArrayList<>();
        LocalTime validEndTime = startTime.plusMinutes(slotDuration);

        // minutes is used to control the max duration of the class
        int minutes = slotDuration;

        for (LocalTime endTime : localTimeLocalTimeMap.values()) {
            if(validEndTime.equals(endTime) && endTime.isAfter(startTime) && minutes <= MAX_CLASS_DURATION ){
                options.add(endTime);
                slotDuration += SLOT_VALUE;
                minutes += SLOT_VALUE;
                validEndTime = startTime.plusMinutes(slotDuration);
            }
        }

        if(END_TIME_HOUR == 24 && validEndTime.equals(LocalTime.of(0,0)) && localTimeLocalTimeMap.containsValue(LocalTime.of(0,0))) options.add(LocalTime.of(0,0));

        System.out.println();
        // menu to select the end time
        Collections.sort(options);
        ListSelector<LocalTime> selector = new ListSelector<>("Select the " + ColorCode.BLUE.getValue() + "End time" + ColorCode.RESET.getValue() + " for the class:", options);
        if(!selector.showAndSelectWithExit()) return null;
        if(selector.getSelectedElement().equals(LocalTime.of(0,0))){
            return  (int) ChronoUnit.MINUTES.between(startTime, LocalTime.of(23,59)) + 1;
        }
        return  (int) ChronoUnit.MINUTES.between(startTime, selector.getSelectedElement());
    }

    /**
     * Prints the available slots for the course, for every available week day.
     * @param map the map of the available slots for the course
     */
    private void printAvailableSlots(Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> map) {
        int columnHeaderWidth;
        int columnCellWidth;
        if(Application.settings().isOperativeSystemLinux()){
            System.out.println();
            columnCellWidth = 30;
            columnHeaderWidth = columnCellWidth -9;
        } else{
            System.out.println();
            columnCellWidth = 20;
            columnHeaderWidth = columnCellWidth;
        }

        String columnHeaderFormat = "%-" + columnHeaderWidth + "s";
        String cellFormat = "%-" + columnCellWidth + "s";

        System.out.printf(columnHeaderFormat, "");
        for (RecurrentClassWeekDay day : map.keySet()) {
                System.out.printf(columnHeaderFormat, day);
        }
        System.out.println();

        printAvailableHourForEachDay(map, columnHeaderFormat, cellFormat);

    }

    /**
     * Prints the available slots for the course, for every available hour.
     * @param map the map of the available slots for the course
     * @param columnHeaderFormat the format of the column header
     * @param cellFormat the format of the cell
     */
    private void printAvailableHourForEachDay(Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> map, String columnHeaderFormat, String cellFormat) {

        Integer endHour = END_TIME_HOUR;
        Integer endMinute = END_TIME_MINUTE;

        if(END_TIME_HOUR == 24){
            endHour = 23;
            endMinute = 59;
        }

        for (LocalTime time = LocalTime.of(START_TIME_HOUR, START_TIME_MINUTE); time.isBefore(LocalTime.of(endHour, endMinute)); time = time.plusMinutes(SLOT_VALUE)) {
            System.out.printf(columnHeaderFormat, time);
            for (RecurrentClassWeekDay day : map.keySet()) {
                Map<LocalTime, LocalTime> times = map.get(day);
                if (times.containsKey(time)) {
                    if(day == RecurrentClassWeekDay.THURSDAY)System.out.printf(cellFormat, ColorCode.GREEN.getValue() + " [    ]" + ColorCode.RESET.getValue());
                    else if(day == RecurrentClassWeekDay.WEDNESDAY)System.out.printf(cellFormat, ColorCode.GREEN.getValue() + " [    ]" + ColorCode.RESET.getValue());
                    else System.out.printf(cellFormat, ColorCode.GREEN.getValue() + "[    ]" + ColorCode.RESET.getValue());

                } else {
                    if(day == RecurrentClassWeekDay.THURSDAY)System.out.printf(cellFormat, ColorCode.RED.getValue() + " [####]" + ColorCode.RESET.getValue());
                    else if(day == RecurrentClassWeekDay.WEDNESDAY)System.out.printf(cellFormat, ColorCode.RED.getValue() + " [####]" + ColorCode.RESET.getValue());
                    else System.out.printf(cellFormat, ColorCode.RED.getValue() + "[####]" + ColorCode.RESET.getValue());
                }
            }
            System.out.println();
            if(time.equals(LocalTime.of(23,59-SLOT_VALUE+1))) break; // if the end time is 24:00, the loop will continue until 23:59
        }

    }

    @Override
    public String headline() {
        return "Schedule a Class";
    }

}
