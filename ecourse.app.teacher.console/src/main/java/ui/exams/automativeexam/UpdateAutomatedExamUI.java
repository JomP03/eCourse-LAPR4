package ui.exams.automativeexam;


import appsettings.Application;
import coursemanagement.application.TeacherCoursesProvider;
import ecourseusermanagement.application.UserSessionService;
import exammanagement.application.CourseExamsProvider;
import exammanagement.application.automatedexams.UpdateAutomatedExamController;
import exammanagement.domain.AutomatedExam;
import persistence.PersistenceContext;
import ui.components.AbstractUI;
import ui.components.ColorCode;
import ui.components.Console;
import ui.components.Sleeper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdateAutomatedExamUI extends AbstractUI {

    private final UpdateAutomatedExamController updateController = new UpdateAutomatedExamController(
            new UserSessionService(PersistenceContext.repositories().eCourseUsers()), new TeacherCoursesProvider(
            PersistenceContext.repositories().courses()), new CourseExamsProvider(
            PersistenceContext.repositories().exams()),
            PersistenceContext.repositories().courses(),
            PersistenceContext.repositories().exams()
    );

    @Override
    protected boolean doShow() {

        try {

            List<AutomatedExam> courseExams =  (List<AutomatedExam>) updateController.findAutomatedExams();

            AutomatedExam selectedExam = menuToSelectExam(courseExams);
            if(selectedExam == null) return false;

            File selectedFile;
            System.out.println("| Choose the directory to store the exam file |\n\n");

            try {
                if (!Application.settings().isOperativeSystemLinux()) {
                    selectedFile = Console.selectDirectoryWithFileExplorer();
                    Sleeper.sleep(1300);
                } else {
                    selectedFile = Console.selectDirectoryWithPath();
                    Sleeper.sleep(1300);
                }
            } catch (Exception e) {
                errorMessage("Could not find the directory");
                return false;
            }

            System.out.println();
            String fileName =Console.readLine("Please provide a " + ColorCode.BLUE.getValue() + "name for the file" +
                    ColorCode.RESET.getValue() + ": ");


            updateController.createExamFileFromAutomatedExam(selectedExam, selectedFile.getPath(), fileName);

            System.out.println("Selected path where the exam text file is: " + selectedFile + "\n");

            Console.readLine("Please edit the exam file with the updates and then press enter to continue...");

            System.out.println("\n");


            File newFile;
            System.out.println("| Upload the TXT file |\n\n");

            try{
                if (!Application.settings().isOperativeSystemLinux()) {
                    newFile = Console.selectFileWithFileExplorer();
                    Sleeper.sleep(1300);
                } else {
                    newFile = Console.selectFileWithPath();
                    Sleeper.sleep(1300);
                }
            } catch (Exception e) {
                errorMessage("Could not find the file");
                return false;
            }

            AutomatedExam updatedExam = updateController.updatedAutomatedExam(newFile.getPath(), selectedExam.course(), selectedExam);

            System.out.println(updatedExam.toString());


            successMessage("Exam updated successfully.");


        } catch (IllegalStateException | IOException e) {
            errorMessage(e.getMessage());
        }



        return true;

    }

    private AutomatedExam menuToSelectExam(List<AutomatedExam> courseExams) {
        List<AutomatedExam> automatedExams = new ArrayList<>();

        for (AutomatedExam exam : courseExams) {
            automatedExams.add(exam);
        }

        if (automatedExams.isEmpty()) {
            errorMessage("There are no exams to update.");
            return null;
        }

        System.out.println("Please select an exam:");
        for (int i = 0; i < automatedExams.size(); i++) {
            System.out.println((i+1) + " - " + automatedExams.get(i).title() + " - " + automatedExams.get(i).course().identity().toString());
        }

        System.out.println();

        int selection = -1;
        while (selection <= 0 || selection > automatedExams.size()) {
            selection = Console.readInteger("Selection: ");
            if (selection <= 0 || selection > automatedExams.size()) {
                errorMessage("Invalid selection.");
            }
        }

        return automatedExams.get(selection-1);
    }


    @Override
    public String headline() {
        return "Create Exam";
    }

}
