package takenexammanagement.application;

import coursemanagement.application.TeacherCoursesProvider;
import coursemanagement.domain.Course;
import eapli.framework.validations.Preconditions;
import ecourseusermanagement.application.UserSessionService;
import ecourseusermanagement.domain.ECourseUser;
import exammanagement.application.CourseExamsProvider;
import exammanagement.domain.Exam;
import takenexammanagement.domain.TakenExam;
import java.util.Optional;

public class ViewExamGradesController {

    private final UserSessionService userSessionService;
    private final TeacherCoursesProvider courseProvider;
    private final CourseExamsProvider examsProvider;
    private final ITakenExamProvider takenExamProvider;

    public ViewExamGradesController(UserSessionService userSessionService, TeacherCoursesProvider courseProvider,
                                    CourseExamsProvider examsProvider, ITakenExamProvider takenExamProvider) {
        Preconditions.noneNull(userSessionService, courseProvider, examsProvider, takenExamProvider);

        this.userSessionService = userSessionService;
        this.courseProvider = courseProvider;
        this.examsProvider = examsProvider;
        this.takenExamProvider = takenExamProvider;

        verifyUserIsTeacher();
    }

    private void verifyUserIsTeacher() {
        ECourseUser user;
        Optional<ECourseUser> eCourseUserOptional = userSessionService.getLoggedUser();

        if (eCourseUserOptional.isPresent())
            user = eCourseUserOptional.get();
        else
            throw new IllegalStateException("No eCourse user found. Make sure you are registered.");

        if (!user.isTeacher())
            throw new IllegalStateException("Only teachers can create exams.");
    }

    /**
     * List teacher courses.
     *
     * @return the iterable containing the courses
     */
    public Iterable<Course> listTeacherCourses() {
        return courseProvider.provideTeacherGradedCourses(userSessionService.getLoggedUser().get());
    }

    public Iterable<Exam> listCourseExams(Course course) {
        return examsProvider.provideSolvedExams(course);
    }

    public Iterable<TakenExam> listTakenExams(Exam exam) {
        return takenExamProvider.provideTakenExams(exam);
    }
}
