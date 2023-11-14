package takenexammanagement.application;

import coursemanagement.application.listcourses.StudentCoursesProvider;
import coursemanagement.domain.Course;
import eapli.framework.validations.Preconditions;
import ecourseusermanagement.application.UserSessionService;
import ecourseusermanagement.domain.ECourseUser;
import exammanagement.application.*;
import exammanagement.domain.*;
import takenexammanagement.application.service.*;
import takenexammanagement.domain.*;
import takenexammanagement.repository.*;

import java.io.*;
import java.util.*;

public class TakeFormativeExamController {

    private final UserSessionService userSessionService;
    private final StudentCoursesProvider courseProvider;
    private final CourseExamsProvider examsProvider;
    private final ExamFileParser fileParser;
    private final TakenExamRepository takenExamRepo;
    private final TakenExamHandler takenExamHandler;
    private ECourseUser student;


    /**
     * Constructor of the controller
     * @param userSessionService the user session service
     * @param courseProvider the course provider
     * @param examsProvider the exams provider
     * @param takenExamRepo the taken exam repository
     * @param takenExamHandler the taken exam handler
     */
    public TakeFormativeExamController(
            UserSessionService userSessionService, StudentCoursesProvider courseProvider,
            CourseExamsProvider examsProvider, ExamFileParser fileParser,
            TakenExamRepository takenExamRepo, TakenExamHandler takenExamHandler) {
        Preconditions.noneNull(userSessionService, courseProvider, examsProvider,
                fileParser, takenExamRepo, takenExamHandler);

        this.userSessionService = userSessionService;
        this.courseProvider = courseProvider;
        this.examsProvider = examsProvider;
        this.fileParser = fileParser;
        this.takenExamRepo = takenExamRepo;
        this.takenExamHandler = takenExamHandler;

        verifyUser();
    }

    private void verifyUser() {
        Optional<ECourseUser> eCourseUserOptional = userSessionService.getLoggedUser();
        eCourseUserOptional.ifPresentOrElse(eCourseUser -> this.student = eCourseUser, () -> {
            throw new IllegalStateException("eCourse User must be registered.");
        });
    }


    /**
     * Finds all courses a student is enrolled in that are in progress
     * @return the student
     */
    public Iterable<Course> listStudentInProgressCourses() {
        return courseProvider.provideStudentInProgressCourses(student);
    }


    /**
     * Lists all non taken formative exams of a course
     * @return the student
     */
    public List<FormativeExam> listStudentNonTakenFormativeExamsOfAcourse(Course course) {
        List<FormativeExam> formativeExams = (List<FormativeExam>) examsProvider.provideCourseFormativeExams(course);

        formativeExams.removeIf(this::isExamAlreadyTaken);

        return formativeExams;
    }

    private boolean isExamAlreadyTaken(FormativeExam selectedExam) {
        return takenExamRepo.isExamAlreadyTaken(selectedExam, student);
    }

    /**
     * Creates a taken exam from a file
     * @param path the path to the file
     * @param exam the exam to be taken
     * @return the created taken exam
     * @throws IOException if the file cannot be read
     */
    public TakenExam correctExam(String path, Exam exam) throws IOException {
        TakenExam takenExam = takenExamHandler.createTakenExam(path, exam, student);

        return takenExamRepo.save(takenExam);
    }

    public File createExamFile(Exam selectedExam, String path) {
        return fileParser.parse(selectedExam, path);
    }
}
