package ui.course.bulkenroll;

import appsettings.Application;
import coursemanagement.application.coursestate.ListCourseByStateService;
import coursemanagement.domain.Course;
import enrolledstudentmanagement.application.BulkEnrollStudentsCsvController;
import enrolledstudentmanagement.application.CsvBulkStudentsEnroller;
import enrolledstudentmanagement.dto.BulkEnrollStudentsReport;
import persistence.PersistenceContext;
import ui.components.AbstractUI;
import ui.components.Console;
import ui.components.ListSelector;
import ui.components.Sleeper;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BulkEnrollStudentsCsvUI extends AbstractUI {
    private final BulkEnrollStudentsCsvController controller =
            new BulkEnrollStudentsCsvController(
                    new ListCourseByStateService(PersistenceContext.repositories().courses()),
                    new CsvBulkStudentsEnroller(PersistenceContext.repositories().eCourseUsers(),
                            PersistenceContext.repositories().enrolledStudents()));

    @Override
    protected boolean doShow() {
        // Select the course
        List<Course> courses;
        try {
            courses = (List<Course>) controller.listEnrollableCourses();
            if (courses.isEmpty()) {
                infoMessage("There are no courses available to enroll students.");
                Sleeper.sleep(1500);
                return false;
            }
        } catch (Exception e) {
            infoMessage("There are no courses available to enroll students.");
            Sleeper.sleep(1500);
            return false;
        }
        ListSelector<Course> courseSelector = new ListSelector<>("Courses in the enroll state:", courses);
        if (!courseSelector.showAndSelectWithExit()) return false;
        Course course = courseSelector.getSelectedElement();

        boolean wantToTryAgain;
        // Upload the CSV file
        File selectedFile;
        System.out.print("| Upload the CSV file |\n\n");
        do {
            if (!Application.settings().isOperativeSystemLinux()) {
                selectedFile = selectFileWithFileExplorer();
                Sleeper.sleep(1300);
            } else {
                selectedFile = selectFileWithPath();
                Sleeper.sleep(1300);
            }
            if (selectedFile == null) {
                System.out.printf("Do you want to try again?%n%n1 - Yes%n0 - No%n%n");
                wantToTryAgain = Console.readOption(0, 1) == 1;
                System.out.println();
            } else {
                wantToTryAgain = false;
                BulkEnrollStudentsReport report;
                try {
                    report = controller.bulkEnrollStudents(course, selectedFile);
                    if (report.hasErrors()) {
                        infoMessage("Some students could not be enrolled. Please check the report.");
                        Sleeper.sleep(1000);
                    } else {
                        successMessage("Students enrolled successfully!");
                        Sleeper.sleep(1200);
                    }
                } catch (Exception e) {
                    errorMessage(e.getMessage());
                    Sleeper.sleep(1500);
                    return false;
                }
                System.out.println(report);
                Sleeper.sleep(2200);
                if (wantsToExportReport())
                    exportReport(report);
            }
        } while (wantToTryAgain);
        return false;
    }


    private File selectFileWithPath() {
        String path = Console.readNonEmptyLine("Insert the path to the CSV file: ",
                "A path must be provided");
        System.out.println();
        File selectedFile = new File(path);
        if (selectedFile.exists()) {
            if (selectedFile.getName().toLowerCase().endsWith(".csv"))
                return selectedFile;
            else {
                errorMessage("The selected file is not a CSV file.");
                return null;
            }
        } else {
            errorMessage("The selected file does not exist.");
            return null;
        }
    }

    private File selectFileWithFileExplorer() {
        FileDialog fileDialog = new FileDialog((Frame) null, "Select CSV file", FileDialog.LOAD);
        fileDialog.setFile("*.csv");
        fileDialog.setDirectory(System.getProperty("user.dir"));
        fileDialog.setVisible(true);

        String filename = fileDialog.getFile();
        if (filename != null) {
            File selectedFile = new File(fileDialog.getDirectory(), filename);
            if (selectedFile.getName().toLowerCase().endsWith(".csv")) {
                return selectedFile;
            } else {
                errorMessage("The selected file is not a CSV file.");
                return null;
            }
        } else {
            infoMessage("No file selected.");
            return null;
        }
    }

    private boolean wantsToExportReport() {
        System.out.printf("Do you want to export the report?%n%n1 - Yes%n0 - No%n%n");
        boolean response = Console.readOption(0, 1) == 1;
        System.out.println();
        return response;
    }

    private void exportReport(BulkEnrollStudentsReport report) {
        String fileName = Console.readNonEmptyLine(
                "Provide the name of the file to export the report to: ",
                "A file name is required.");
        System.out.println();
        try {
            // Check if the folder exists
            File folder = new File("reports");
            if (!folder.exists()) {
                if (!folder.mkdir()) {
                    errorMessage("There was a problem exporting the report.");
                    Sleeper.sleep(1500);
                    return;
                }
            }
            fileName = adaptExportedFileName(fileName);
            String filePath = "reports/" + fileName;
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(report.toString());
            writer.close();
        } catch (IOException e) {
            errorMessage("There was a problem exporting the report.");
            Sleeper.sleep(1500);
            return;
        }
        successMessage("Report exported successfully.");
        Sleeper.sleep(1500);
    }

    private String adaptExportedFileName(String fileName) {
        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();
        // Define the format for the file name
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");
        // Format the current date and time using the formatter
        String formattedDateTime = currentDateTime.format(formatter);
        // Add the date and time to the file name
        fileName = fileName + "_" + formattedDateTime;
        fileName = fileName.endsWith(".txt") ? fileName : fileName + ".txt";
        return fileName;
    }

    @Override
    public String headline() {
        return "Bulk Enroll Students from CSV";
    }
}
