package ui.course;

import coursemanagement.application.*;
import persistence.*;
import ui.components.AbstractUI;
import ui.components.Console;
import ui.components.Sleeper;

public class CreateCourseUI extends AbstractUI {

    private final CreateCourseController controller = new CreateCourseController(PersistenceContext.repositories().courses());

    @Override
    protected boolean doShow() {

        System.out.println("Please provide the course details\n");

        final String courseCode = Console.readNonEmptyLine("Course Code: ",
                "Please provide a course code");
        final String courseName = Console.readNonEmptyLine("Course Name: ",
                "Please provide a course name");
        final String courseDescription = Console.readNonEmptyLine("Course Description: ",
                "Please provide a course description");

        final int minNrEnrolledStudents = Console.readPositiveInteger("Minimum number of enrolled students: ");

        int maxNrEnrolledStudents =  Console.readPositiveInteger("Maximum number of enrolled students: ");
        // Guarantee that the maximum number of enrolled students is greater than the minimum
        while (maxNrEnrolledStudents <= minNrEnrolledStudents) {
            infoMessage("The maximum number of enrolled students must be greater than the minimum number of enrolled students");
            maxNrEnrolledStudents = Console.readPositiveInteger("Maximum number of enrolled students: ");
        }
        System.out.println();

        try {
            controller.createCourse(courseCode.replaceAll(" ", ""),
                    courseName, courseDescription, minNrEnrolledStudents, maxNrEnrolledStudents);
        } catch (IllegalArgumentException e) {
            errorMessage(e.getMessage());
            Sleeper.sleep(1000);
            return false;
        }


        successMessage("Course created successfully");
        Sleeper.sleep(1000);

        return false;
    }

    @Override
    public String headline() {
        return "Create Course";
    }
}
