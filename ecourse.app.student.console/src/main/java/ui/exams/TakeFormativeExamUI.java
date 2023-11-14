package ui.exams;

import appsettings.*;
import coursemanagement.application.listcourses.StudentCoursesProvider;
import coursemanagement.domain.Course;
import ecourseusermanagement.application.UserSessionService;
import exammanagement.application.*;
import exammanagement.domain.*;
import exammanagement.domain.Exam;
import persistence.PersistenceContext;
import ui.components.AbstractUI;
import ui.components.Console;
import takenexammanagement.application.*;
import takenexammanagement.application.service.*;
import takenexammanagement.domain.*;
import ui.components.ListSelector;
import ui.components.Sleeper;

import java.io.*;
import java.util.*;

public class TakeFormativeExamUI extends AbstractUI {

    private final TakeFormativeExamController controller = new TakeFormativeExamController(
            new UserSessionService(PersistenceContext.repositories().eCourseUsers()),
            new StudentCoursesProvider(PersistenceContext.repositories().courses()),
            new CourseExamsProvider(PersistenceContext.repositories().exams()),
            new ExamTxtParser(),
            PersistenceContext.repositories().takenExams(),
            new TakenExamHandler(new TakenExamCorrector())
    );

    @Override
    protected boolean doShow() {

        Course selectedCourse;
        Exam selectedExam;

        do {
            selectedCourse = menuToSelectCourse();

            // Means that the student has no courses
            if(selectedCourse == null)
                return false;

            selectedExam = menuToSelectExam(selectedCourse);

        } while (selectedExam == null);


        File file;
        File jsonFile = null;
        if (Application.settings().isOperativeSystemLinux()) {
            infoMessage("Warning");
            Console.readLine("A txt file will be generated with the selected exam.\n" +
                    "Once you have finished, submit the same file.\n\n" +
                    "Press enter to continue.");

            System.out.println("\nChoose the directory to store the exam file \n");
            File directory = Console.selectDirectoryWithPath();

            file = controller.createExamFile(selectedExam, directory.getPath() + "\\exam.txt");

            Console.readLine("\nPress enter once you have finished the exam");
            System.out.println();
        }
        else {

//            // Parse the exam to json
////            ExamJsonParser jsonParser = new ExamJsonParser();
////            jsonParser.parse(selectedExam, "examfiles/choosenExam.json");
////            file = new File("examfiles/takenExam.json");
////
////            // Initialize the JavaFX application
////            javafx.application.Application.launch(MainGUI.class);

            ExamJsonParser jsonParser = new ExamJsonParser();
            jsonFile = jsonParser.parse(selectedExam, "examsite/exam.json");

            infoMessage("Warning");
            System.out.println("A new browser window will open with the exam.\n" +
                    "Once you have finished, hit the submit the button and a file will be generated.\n" +
                    "Come back to this window and submit the file.\n\n");

            infoMessage("Press enter to open the browser and start the exam.");
            Console.readLine("");

            // Start server
            ExamRunner runner = new ExamRunner();

            try {
                runner.runServer();
            } catch (IOException | InterruptedException e) {
                errorMessage(e.getMessage());
            }

            infoMessage("Press enter once you have submitted the exam");
            Console.readLine("");

            try {
                file = Console.selectFileWithFileExplorer();
            } catch (IllegalArgumentException e) {
                errorMessage(e.getMessage());
                return false;
            }

            runner.destroyServer();
        }


        try {
            TakenExam takenExam = controller.correctExam(file.getPath(), selectedExam);
            successMessage("Exam submitted successfully");
            Sleeper.sleep(1500);

            displayExamResult(takenExam);

        }
        catch (IllegalArgumentException iae) {
            errorMessage("The exam is not valid");
            System.out.println(iae.getMessage());
            return false;
        }
        catch (IOException e) {
            errorMessage(e.getMessage());
        }
        finally {
            if (jsonFile != null)
                jsonFile.delete();
        }

        Sleeper.sleep(2000);

        return false;
    }

    /**
     * Asks the user to select a course from a list
     * @return The selected course
     */
    private Course menuToSelectCourse() {
        List<Course> courses = (List<Course>) controller.listStudentInProgressCourses();

        if(courses.isEmpty()) {
            infoMessage("You are not enrolled in any course");
            return null;
        }

        ListSelector<Course> courseSelector = new ListSelector<>("Select a course: ", courses);
        courseSelector.showAndSelectWithExit();

        return courseSelector.getSelectedElement();
    }


    /**
     * Asks the user to select an exam from a list
     * @param selectedCourse The course to select the exam from
     * @return The selected exam
     */
    private Exam menuToSelectExam(Course selectedCourse) {
        List<FormativeExam> exams = controller.listStudentNonTakenFormativeExamsOfAcourse(selectedCourse);

        if (exams.isEmpty()) {
            infoMessage("There are no exams available for this course");
            Sleeper.sleep(1500);
            return null;
        }

        List<String> examTitles = new ArrayList<>();
        for (FormativeExam exam : exams)
            examTitles.add(exam.title());


        ListSelector<String> printer = new ListSelector<>("Select a exam", examTitles);
        if (!printer.showAndSelectWithExit())
            return null;

        int option = printer.getSelectedOption();

        return exams.get(option - 1);
    }


    /**
     * Prints the result of the exam
     * @param takenExam The taken exam to print
     */
    private void displayExamResult(TakenExam takenExam) {

        if(takenExam.exam().header().gradingFeedBackType().equals(GradingType.ON_SUBMISSION)) {
            infoMessage("Exam Grade");
            System.out.println("Your grade on this exam was -> " + takenExam.grade() + "\n");
            Sleeper.sleep(1500);

        }
        if(takenExam.exam().header().feedbackType().equals(FeedBackType.ON_SUBMISSION)) {
            infoMessage("Exam Feedback");
            displayFeedBack(takenExam);
            Sleeper.sleep(1500);
        }
    }


    /**
     * Displays the feedback of the exam
     * @param takenExam The taken exam to display the feedback
     */
    private void displayFeedBack(TakenExam takenExam) {
        StringBuilder sb = new StringBuilder();

        for(AnsweredQuestion answeredQuestion : takenExam.answeredQuestions()) {
            sb.append("\n").append(answeredQuestion.question()).append("\n");
            sb.append("Your answer: ").append(answeredQuestion.answer()).append("\n");
            sb.append(answeredQuestion.feedback()).append("\n");
        }

        System.out.println(sb);
    }


    @Override
    public String headline() {
        return "Take Formative Exam";
    }
}