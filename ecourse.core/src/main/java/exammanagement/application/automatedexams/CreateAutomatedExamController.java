package exammanagement.application.automatedexams;

import coursemanagement.application.listcourses.ListTeacherCoursesController;
import coursemanagement.domain.Course;
import ecourseusermanagement.application.UserSessionService;
import ecourseusermanagement.domain.ECourseUser;
import exammanagement.application.service.ExamHandler;
import exammanagement.domain.AutomatedExam;
import exammanagement.repository.ExamRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class CreateAutomatedExamController {

    private final ListTeacherCoursesController listTeacherCoursesController;

    private final ExamRepository examRepository;

    private final ExamHandler examHandler;

    private ECourseUser examCreator;

    private final UserSessionService userSessionService;

    public CreateAutomatedExamController(ListTeacherCoursesController listTeacherCoursesController, ExamRepository examRepository, ExamHandler automatedExamHandler, UserSessionService userSessionService) {
        this.listTeacherCoursesController = listTeacherCoursesController;

        if(examRepository == null)
            throw new IllegalArgumentException("The exam repository can not be null.");
        this.examRepository = examRepository;

        if(automatedExamHandler == null)
            throw new IllegalArgumentException("The automated exam handler can not be null.");
        this.examHandler = automatedExamHandler;

        if(userSessionService == null)
            throw new IllegalArgumentException("The User must be logged in the system.");
        this.userSessionService = userSessionService;

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
     * Returns all available courses for the logged teacher.
     *
     * @return all available courses for the logged teacher.
     */
    public List<Course> teacherCourses(){
        return (List<Course>) listTeacherCoursesController.findCourses();
    }

    /** Creates an automated exam.
     *
     * @param filePath the file path.
     * @param selectedCourse the selected course.
     * @throws IOException if an error occurs.
     */
    public AutomatedExam createAutomatedExam(String filePath, Course selectedCourse) throws IOException {
        AutomatedExam automatedExam = examHandler.parseAutomatedExam(filePath, selectedCourse, examCreator);
        if(!examHandler.validateExamTitle(automatedExam.title())){
            throw new IllegalArgumentException("The exam title already exists.");
        }
        examRepository.save(automatedExam);
        return automatedExam;
    }
}