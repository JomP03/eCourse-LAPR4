package exammanagement.application.automatedexams;

import coursemanagement.application.TeacherCoursesProvider;
import coursemanagement.application.listcourses.ListTeacherCoursesController;
import coursemanagement.domain.Course;
import coursemanagement.repository.CourseRepository;
import ecourseusermanagement.application.UserSessionService;
import ecourseusermanagement.domain.ECourseUser;
import exammanagement.application.ICourseExamsProvider;
import exammanagement.application.ListCourseExamsController;
import exammanagement.application.service.ExamFileCreator;
import exammanagement.application.service.ExamHandler;
import exammanagement.domain.AutomatedExam;
import exammanagement.repository.ExamRepository;
import persistence.PersistenceContext;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;


import java.io.IOException;

import java.util.Optional;


public class UpdateAutomatedExamController {

    private EntityManager entityManager;

    private final ListCourseExamsController listCourseExamsController;

    private final ListTeacherCoursesController listTeacherCoursesController;

    private final ExamHandler examHandler;

    private final ExamRepository examRepository;

    private final UserSessionService userSessionService;

    private ECourseUser examCreator;

    public UpdateAutomatedExamController(UserSessionService userSessionService,
                                         TeacherCoursesProvider teacherCoursesProvider,
                                         ICourseExamsProvider courseExamsProvider,
                                         CourseRepository courseRepository,
                                         ExamRepository examRepository
                                        ) {

        // Verify if userSessionService is null
        if (userSessionService == null) {
            throw new IllegalArgumentException("userSessionService cannot be null.");
        }

        // Verify if teacherCoursesProvider is null
        if (teacherCoursesProvider == null) {
            throw new IllegalArgumentException("teacherCoursesProvider cannot be null.");
        }

        // Verify if courseExamsProvider is null
        if (courseExamsProvider == null) {
            throw new IllegalArgumentException("courseExamsProvider cannot be null.");
        }

        // Verify if courseRepository is null
        if (courseRepository == null) {
            throw new IllegalArgumentException("courseRepository cannot be null.");
        }

        // Verify if examRepository is null
        if (examRepository == null) {
            throw new IllegalArgumentException("examRepository cannot be null.");
        }

        this.userSessionService = userSessionService;

        this.examRepository = examRepository;

        this.examHandler = new ExamHandler(examRepository);

        this.listTeacherCoursesController = new ListTeacherCoursesController(courseRepository, userSessionService);

        this.listCourseExamsController = new ListCourseExamsController(userSessionService, teacherCoursesProvider, courseExamsProvider);

        verifyExamCreator();
    }

    /**
     * Verifies if the user is logged in the system.
     */
    private void verifyExamCreator() {
        Optional<ECourseUser> eCourseUserOptional = userSessionService.getLoggedUser();

        eCourseUserOptional.ifPresentOrElse(
                eCourseUser -> this.examCreator = eCourseUser,
                () -> {
                    throw new IllegalStateException("No eCourse user found. Make sure you are registered.");
                }
        );
    }

    /**
     * Creates an exam file from an automated exam.
     * @param selectedExam the selected exam
     * @param path the path
     * @param fileName the file name
     */
    public void createExamFileFromAutomatedExam(AutomatedExam selectedExam, String path, String fileName) {
        ExamFileCreator examFileCreator = new ExamFileCreator();
        examFileCreator.createExamFile(selectedExam, path, fileName);
    }

    /**
     * Updated an automated exam.
     *
     * @param filePath the file path
     * @param selectedCourse the selected course
     * @param exam the exam to update
     * @return the updated automated exam
     *
     */
    @Transactional
    public AutomatedExam updatedAutomatedExam(String filePath, Course selectedCourse, AutomatedExam exam) throws IOException {
        AutomatedExam automatedExam = examHandler.parseAutomatedExam(filePath, selectedCourse, examCreator);
        if(!examHandler.validateUpdateExamTitle(automatedExam, exam)){
            throw new IllegalArgumentException("Exam title already exists.");
        }

        exam.updateAll(automatedExam);
        examRepository.save(exam);

        return automatedExam;
    }

    /**
     * Gets the automated exams created by the user.
     * @return the automated exams created by the user
     */
    public Iterable<AutomatedExam> findAutomatedExams() {
        return examRepository.findTeacherAutomatedExams(examCreator);
    }
}
