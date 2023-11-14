package takenexammanagement.application;


import coursemanagement.application.StudentCoursesProvider;
import coursemanagement.domain.Course;
import coursemanagement.repository.CourseRepository;
import ecourseusermanagement.application.UserSessionService;
import ecourseusermanagement.domain.ECourseUser;
import enrolledstudentmanagement.repository.EnrolledStudentRepository;
import exammanagement.application.CourseExamsProvider;
import exammanagement.application.ExamFileParser;
import exammanagement.domain.AutomatedExam;
import exammanagement.domain.Exam;
import exammanagement.repository.ExamRepository;
import takenexammanagement.application.service.TakenExamCorrector;
import takenexammanagement.application.service.TakenExamHandler;
import takenexammanagement.application.service.TakenExamVisitor;
import takenexammanagement.domain.TakenExam;
import takenexammanagement.repository.TakenExamRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class TakeAutomatedExamController {

    UserSessionService userSessionService;

    StudentCoursesProvider studentCoursesProvider;

    CourseExamsProvider courseExamsProvider;

    TakenExamHandler takenExamHandler;

    TakenExamRepository takenExamRepository;

    TakenExamCorrector takenExamCorrector;

    ExamFileParser fileParser;

    ECourseUser student;

    public TakeAutomatedExamController(UserSessionService userSessionService, ExamRepository examRepository,
                                       CourseRepository courseRepository, EnrolledStudentRepository enrolledStudentRepository,
                                       TakenExamRepository takenExamRepository, TakenExamCorrector takenExamCorrector, ExamFileParser fileParser) {

        if(userSessionService == null)
            throw new IllegalArgumentException("User session service cannot be null.");
        this.userSessionService = userSessionService;

        if(examRepository == null)
            throw new IllegalArgumentException("Exam repository cannot be null.");

        if(courseRepository == null)
            throw new IllegalArgumentException("Course repository cannot be null.");

        if(enrolledStudentRepository == null)
            throw new IllegalArgumentException("Enrolled student repository cannot be null.");

        if(takenExamRepository == null)
            throw new IllegalArgumentException("Taken exam repository cannot be null.");
        this.takenExamRepository = takenExamRepository;

        if(takenExamCorrector == null)
            throw new IllegalArgumentException("Taken exam corrector cannot be null.");
        this.takenExamCorrector = takenExamCorrector;

        if(fileParser == null)
            throw new IllegalArgumentException("File parser cannot be null.");
        this.fileParser = fileParser;

        verifyUser();

        this.takenExamHandler = new TakenExamHandler(new TakenExamCorrector());

        this.studentCoursesProvider = new StudentCoursesProvider(enrolledStudentRepository);

        this.courseExamsProvider = new CourseExamsProvider(examRepository);



    }

    private void verifyUser() {
        Optional<ECourseUser> eCourseUserOptional = this.userSessionService.getLoggedUser();

        eCourseUserOptional.ifPresentOrElse(
                eCourseUser -> this.student = eCourseUser,
                () -> {
                    throw new IllegalStateException("No eCourse user found. Make sure you are registered.");
                }
        );
    }

    /**
     * Corrects a taken exam
     * @param path the path to the file containing the taken exam
     * @param exam the exam that was taken
     * @return the taken exam with grade and feedback
     * @throws IOException if the file cannot be read
     */
    public TakenExam correctExam(String path, Exam exam) throws IOException {
        TakenExam takenExam = takenExamHandler.createTakenExam(path, exam, student);



        return takenExamRepository.save(takenExam);
    }

    /**
     * Finds all courses a student is enrolled in that are in progress
     * @return the student courses
     */
    public Iterable<Course> studentActiveCourses() {
        return studentCoursesProvider.provideUserActiveCourses(student);

    }

    /**
     * Finds all available automated exams of a course, that a student has not taken yet
     * @return the course automated exams
     */
    public Iterable<AutomatedExam> availableAutomatedExams(Course course) {
        List<AutomatedExam> allexams =(List<AutomatedExam>) courseExamsProvider.provideActiveCourseAutomatedExams(course);

        allexams.removeIf(exam -> isExamAlreadyTaken(exam));

        return allexams;
    }

    /**
     * Checks if a student has already taken an exam
     */
    private boolean isExamAlreadyTaken(AutomatedExam selectedExam) {
        return takenExamRepository.isExamAlreadyTaken(selectedExam, student);
    }

    /**
     * Creates a file containing an exam
     * @param selectedExam the exam to be written in the file
     * @param path the path to the file
     * @return the file containing the exam
     */
    public File createExamFile(Exam selectedExam, String path) {
        return fileParser.parse(selectedExam, path);
    }
}
