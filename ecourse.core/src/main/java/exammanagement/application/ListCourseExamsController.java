package exammanagement.application;

import coursemanagement.application.TeacherCoursesProvider;
import coursemanagement.domain.Course;
import ecourseusermanagement.application.UserSessionService;
import ecourseusermanagement.domain.ECourseUser;
import exammanagement.domain.AutomatedExam;
import exammanagement.domain.Exam;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ListCourseExamsController {

    private final UserSessionService userSessionService;

    private final TeacherCoursesProvider teacherCoursesProvider;

    private final ICourseExamsProvider courseExamsProvider;

    private ECourseUser loggedTeacher;

    /**
     * Instantiates a new List courses exams controller.
     *
     * @param teacherCoursesProvider the teacher courses provider
     * @param courseExamsProvider    the course exams provider
     */
    public ListCourseExamsController(UserSessionService userSessionService,
                                     TeacherCoursesProvider teacherCoursesProvider,
                                     ICourseExamsProvider courseExamsProvider) {
        // Verify if userSessionService is null
        if (userSessionService == null) {
            throw new IllegalArgumentException("userSessionService cannot be null.");
        }

        this.userSessionService = userSessionService;

        verifyLoggedUser();

        // Verify if teacherCoursesProvider is null
        if (teacherCoursesProvider == null) {
            throw new IllegalArgumentException("teacherCoursesProvider cannot be null.");
        }

        this.teacherCoursesProvider = teacherCoursesProvider;

        // Verify if courseExamsProvider is null
        if (courseExamsProvider == null) {
            throw new IllegalArgumentException("courseExamsProvider cannot be null.");
        }

        this.courseExamsProvider = courseExamsProvider;
    }

    private void verifyLoggedUser() {
        Optional<ECourseUser> eCourseUserOptional = userSessionService.getLoggedUser();

        eCourseUserOptional.ifPresentOrElse(
                eCourseUser -> this.loggedTeacher = eCourseUser,
                () -> {
                    throw new IllegalStateException("No eCourse user found. Make sure you are registered.");
                }
        );

        if (!loggedTeacher.isTeacher()) {
            throw new IllegalStateException("Logged user is not a teacher.");
        }
    }

    /**
     * Teacher active courses iterable.
     *
     * @return the iterable with the teacher active courses
     */
    public Iterable<Course> teacherCourses() {
        return teacherCoursesProvider.provideUserCourses(loggedTeacher);
    }

    /**
     * Course exams iterable.
     *
     * @param course the course
     * @return the iterable
     */
    public Iterable<Exam> courseExams(Course course) {
        // List to return
        List<Exam> courseExams = new ArrayList<>();
        courseExams.addAll((Collection<? extends Exam>) courseExamsProvider.provideCourseFormativeExams(course));
        courseExams.addAll((Collection<? extends Exam>) courseExamsProvider.provideCourseAutomatedExams(course));

        return courseExams;
    }

    /**
     * Course formative exams iterable.
     *
     * @param course the course
     * @return the iterable
     */
    public Iterable<AutomatedExam> courseAutomatedExams(Course course) {
        return courseExamsProvider.provideCourseAutomatedExams(course);
    }

}
