package ui.exams.automativeexam;

import appsettings.Application;
import coursemanagement.application.listcourses.ListTeacherCoursesController;
import coursemanagement.domain.Course;
import ecourseusermanagement.application.UserSessionService;
import exammanagement.application.automatedexams.CreateAutomatedExamController;
import exammanagement.application.service.ExamHandler;
import exammanagement.domain.AutomatedExam;
import persistence.*;
import ui.components.AbstractUI;
import ui.components.Console;
import ui.components.ListSelector;
import ui.components.Sleeper;

import java.io.File;
import java.util.List;

public class CreateAutomatedExamUI extends AbstractUI {

    private final CreateAutomatedExamController createAutomatedExamController =
            new CreateAutomatedExamController(
                    new ListTeacherCoursesController(PersistenceContext.repositories().courses(),
                    new UserSessionService(PersistenceContext.repositories().eCourseUsers())),
                    PersistenceContext.repositories().exams(),
                    new ExamHandler(PersistenceContext.repositories().exams()),
                    new UserSessionService(PersistenceContext.repositories().eCourseUsers()));


    @Override
    protected boolean doShow() {
        List<Course> courses = createAutomatedExamController.teacherCourses();

        if(courses.isEmpty()){
            errorMessage("You don't have any courses");
            return false;
        }

        ListSelector<Course> courseListSelector = new ListSelector<>("Courses", courses);

        courseListSelector.showAndSelect();

        File selectedFile;
        System.out.println("| Upload the TXT file |\n\n");

        try{
            if (!Application.settings().isOperativeSystemLinux()) {
                selectedFile = Console.selectFileWithFileExplorer();
                Sleeper.sleep(1300);
            } else {
                selectedFile = Console.selectFileWithPath();
                Sleeper.sleep(1300);
            }
        } catch (Exception e) {
            errorMessage("Could not find the file");
            return false;
        }

        AutomatedExam exam;

        try {
            exam = createAutomatedExamController.createAutomatedExam(selectedFile.getPath(), courseListSelector.getSelectedElement());
        } catch (Exception e) {
            errorMessage(e.getMessage());
            return false;
        }

        System.out.println(exam);
        Sleeper.sleep(1000);
        successMessage("Exam created successfully");

        return false;
    }

    @Override
    public String headline() {
        return "Create Exam";
    }

}