package exammanagement.application.listexams;

import coursemanagement.application.StudentCoursesProvider;
import coursemanagement.domain.Course;
import ecourseusermanagement.application.UserSessionService;
import ecourseusermanagement.domain.ECourseUser;
import exammanagement.application.CourseExamsProvider;
import exammanagement.domain.Exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListFutureExamsController {

    private final UserSessionService userSessionService;
    private final StudentCoursesProvider studentCoursesProvider;
    private final CourseExamsProvider courseExamsProvider;
    private ECourseUser student;

    public ListFutureExamsController(UserSessionService userSessionService, StudentCoursesProvider studentCoursesProvider, CourseExamsProvider courseExamsProvider) {
        if(userSessionService == null)
            throw new IllegalStateException("eCourse User must be registered.");

        this.userSessionService = userSessionService;

        verifyUser();

        if(studentCoursesProvider == null)
            throw new IllegalArgumentException("studentCoursesProvider cannot be null.");
        this.studentCoursesProvider = studentCoursesProvider;

        if(courseExamsProvider == null)
            throw new IllegalArgumentException("courseExamsProvider cannot be null.");

        this.courseExamsProvider = courseExamsProvider;
    }

    /**
     * Verify user.
     */
    private void verifyUser() {
        Optional<ECourseUser> eCourseUserOptional = userSessionService.getLoggedUser();

        eCourseUserOptional.ifPresentOrElse(
                eCourseUser -> this.student = eCourseUser,
                () -> {
                    throw new IllegalStateException("No eCourse user found. Make sure you are registered.");
                }
        );
    }


    /**
     * Receives student's active courses.
     *
     * @param user the student
     * @return an Iterable the student's active courses
     */
    private Iterable<Course> studentActiveCourses(ECourseUser user){
        return studentCoursesProvider.provideUserActiveCourses(user);
    }

    /** Listing of the student's future exams
     *
     * @return the list of the student future exams
     */
    public List<Exam> futureExams() {
        List<Exam> futureExams = new ArrayList<>();
        for (Course course : studentActiveCourses(student)) {
            futureExams.addAll(courseExamsProvider.provideFutureCourseExams(course));
        }
        return futureExams;
    }
}
