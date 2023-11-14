package ui.classes.scheduleclass;


import appsettings.Application;
import classmanagement.application.recurrent.ScheduleRecurrentClassController;
import classmanagement.domain.RecurrentClassWeekDay;
import coursemanagement.domain.Course;
import persistence.PersistenceContext;
import ui.components.*;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ClassScheduleUI extends AbstractUI {

    private final ScheduleRecurrentClassController scheduleController = new ScheduleRecurrentClassController(
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

    public ClassScheduleUI() {
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

        int ONE_SECOND = 1000;
        List<Course> teacherCourses;
        Course selectedCourse;
        Map<RecurrentClassWeekDay, Map<LocalTime, LocalTime>> availableSlots;


        try {
            teacherCourses = (List<Course>) scheduleController.getAvailableTeacherCourses();

            if (teacherCourses.isEmpty()) {
                errorMessage("There are no available courses for this teacher!");
                Sleeper.sleep(ONE_SECOND);
                return false;
            }

            selectedCourse = menuToSelectCourse(teacherCourses);
            if(selectedCourse == null) return false;

            availableSlots = scheduleController.getAllAvailableRecurrentSlots(teacherCourses, selectedCourse);

        } catch (Exception e) {
            errorMessage(e.getMessage());
            Sleeper.sleep(ONE_SECOND);
            return false;
        }


        if (availableSlots.isEmpty()) {
            errorMessage("There are no available slots for this course!");
            Sleeper.sleep(ONE_SECOND);
            return false;
        } else {

            try {
                printAvailableSlots(availableSlots);

                RecurrentClassWeekDay day = menuToSelectDayOfWeek(availableSlots);
                if(day == null) return false;

                LocalTime startTime = menuToSelectStartTime(availableSlots.get(day));
                if(startTime == null) return false;

                Integer duration = menuToSelectAValidEndTime(availableSlots.get(day), startTime);
                if(duration == null) return false;

                System.out.println();
                String title = writeClassTitle();

                boolean verify = scheduleController.scheduleRecurrentClass(title,duration,startTime.toString(),day,selectedCourse,teacherCourses);

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
    }

    /**
     * Method that makes the user write the class title
     * @return the class title
     */
    private String writeClassTitle() {

        String title = Console.readLine("Please provide a " + ColorCode.BLUE.getValue() + "title for the class" + ColorCode.RESET.getValue() + ": ");

        while(!scheduleController.validateClassTitle(title)) {
            errorMessage("Invalid title!");
            int ONE_SECOND = 1000;
            Sleeper.sleep(ONE_SECOND);
            title = Console.readLine("Please provide a " + ColorCode.BLUE.getValue() + "title for the class" + ColorCode.RESET.getValue() + ": ");
        }

        return title;
    }

    /**
     * Shows a menu with the teacher courses, so the teacher can select where to schedule the class.
     * @param teacherCourses the teacher courses
     * @return the course selected
     */
    private Course menuToSelectCourse(List<Course> teacherCourses) {

            //array with the available courses for the menu
            List<Course> options = new ArrayList<>();
            for(Course course : teacherCourses) {
                if(course.isClose()){
                    options.add(course);
                }
            }

            if(options.isEmpty()) {
                errorMessage("There are no available courses for this teacher!");
                return null;
            }

            System.out.println();
            // menu to select the course
            ListSelector<Course> selector = new ListSelector<>("Select a " + ColorCode.BLUE.getValue() + "course:" + ColorCode.RESET.getValue(), options);
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
        ListSelector<RecurrentClassWeekDay> selector = new ListSelector<>("Select a " + ColorCode.BLUE.getValue() + "day of the week" + ColorCode.RESET.getValue() + " for the class:", options);
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
     * Prints the available slots for the course.
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
