package ui.exams;

import appsettings.Application;
import coursemanagement.domain.Course;
import ecourseusermanagement.application.UserSessionService;
import exammanagement.application.ExamTxtParser;
import exammanagement.domain.AutomatedExam;
import exammanagement.domain.Exam;
import exammanagement.domain.FeedBackType;
import exammanagement.domain.GradingType;
import persistence.PersistenceContext;
import ui.components.AbstractUI;
import ui.components.Console;
import ui.components.ListSelector;
import ui.components.Sleeper;
import exammanagement.application.ExamJsonParser;
import takenexammanagement.application.TakeAutomatedExamController;
import takenexammanagement.application.service.TakenExamCorrector;
import takenexammanagement.domain.AnsweredQuestion;
import takenexammanagement.domain.TakenExam;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TakeAutomatedExamUI extends AbstractUI {

    private final TakeAutomatedExamController controller = new TakeAutomatedExamController(
            new UserSessionService(PersistenceContext.repositories().eCourseUsers()),
            PersistenceContext.repositories().exams(),
            PersistenceContext.repositories().courses(),
            PersistenceContext.repositories().enrolledStudents(),
            PersistenceContext.repositories().takenExams(),
            new TakenExamCorrector(),
            new ExamTxtParser()
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


        File selectedFile;
        File jsonFile = null;
        if (Application.settings().isOperativeSystemLinux()) {
            infoMessage("Warning");
            Console.readLine("A txt file will be generated with the selected exam.\n" +
                    "Once you have finished, submit the same file.\n\n" +
                    "Press enter to start.");

            System.out.println("\nChoose the directory to store the exam file \n");
            File directory = Console.selectDirectoryWithPath();

            selectedFile = controller.createExamFile(selectedExam, directory.getPath() + "\\exam.txt");

            Console.readLine("\nPress enter once you have finished the exam");
            System.out.println();
        }
        else {
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

                selectedFile = Console.selectFileWithFileExplorer();

            } catch (IllegalArgumentException e) {
                errorMessage(e.getMessage());
                return false;
            }

            runner.destroyServer();
        }

        try {
            TakenExam takenExam = controller.correctExam(selectedFile.getPath(), selectedExam);
            displayExamResult(takenExam);
            successMessage("Exam submitted successfully");
            Sleeper.sleep(1500);

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

        return true;
    }

    /**
     * Prints the result of the exam
     * @param takenExam The taken exam to print
     */
    private void displayExamResult(TakenExam takenExam) {

        if(takenExam.exam().header().gradingFeedBackType().equals(GradingType.ON_SUBMISSION) && takenExam.exam().header().feedbackType().equals(FeedBackType.ON_SUBMISSION)){

            infoMessage("Exam Grade");
            displayGrade(takenExam);
            infoMessage("Exam Feedback");
            displayFeedBack(takenExam);

        } else if(takenExam.exam().header().gradingFeedBackType().equals(GradingType.ON_SUBMISSION)){

            infoMessage("Exam Grade");
            displayGrade(takenExam);

        } else if(takenExam.exam().header().feedbackType().equals(FeedBackType.ON_SUBMISSION)){

            infoMessage("Exam Feedback");
            displayFeedBack(takenExam);

        }

    }

    /**
     * Displays the feedback of the exam
     * @param takenExam The taken exam to display the feedback
     * @return The feedback of the exam
     */
    private void displayFeedBack(TakenExam takenExam) {
        StringBuilder sb = new StringBuilder();

        for(AnsweredQuestion answeredQuestion : takenExam.answeredQuestions()) {
            sb.append("\n" + answeredQuestion.question() + "\n");
            sb.append("Your answer: " + answeredQuestion.answer() + "\n");
            sb.append(answeredQuestion.feedback() + "\n");
        }

        System.out.println(sb);
    }

    private void displayGrade(TakenExam takenExam) {
        Float grade = takenExam.grade();
        // 2 decimal places
        grade = (float) (Math.round(grade * 100.0) / 100.0);
        System.out.println("Your grade on this exam was -> " + grade + "\n");
    }


    /**
     * Asks the user to select a course from a list
     * @return The selected course
     */
    private Course menuToSelectCourse() {
        List<Course> courses = (List<Course>) controller.studentActiveCourses();

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
        List<AutomatedExam> automatedExams = (List<AutomatedExam>) controller.availableAutomatedExams(selectedCourse);

        if (automatedExams.isEmpty()) {
            infoMessage("There are no active automated exams for this course.");
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
        return "Take Automated Exam";
    }
}