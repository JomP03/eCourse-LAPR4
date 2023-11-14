package takenexammanagement.application.listgrades;



import coursemanagement.application.StudentCoursesProvider;
import coursemanagement.domain.Course;
import ecourseusermanagement.application.UserSessionService;
import ecourseusermanagement.domain.ECourseUser;
import takenexammanagement.application.TakenExamProvider;
import takenexammanagement.domain.TakenExam;

import java.util.List;
import java.util.Optional;

public class ListStudentGradesController {

    private final UserSessionService userSessionService;
    private final StudentCoursesProvider studentCoursesProvider;
    private final TakenExamProvider takenExamProvider;
    private ECourseUser student;

    public ListStudentGradesController(UserSessionService userSessionService, StudentCoursesProvider studentCoursesProvider, TakenExamProvider takenExamProvider){
        if(userSessionService == null)
            throw new IllegalStateException("eCourse User must be registered.");

        this.userSessionService = userSessionService;

        verifyUser();

        if(studentCoursesProvider == null)
            throw new IllegalArgumentException("studentCoursesProvider cannot be null.");
        this.studentCoursesProvider = studentCoursesProvider;

        if (takenExamProvider == null)
            throw new IllegalArgumentException("takenExamProvider cannot be null.");

        this.takenExamProvider = takenExamProvider;

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
     * Lists student's active and closed courses.
     *
     * @return an Iterable the student's active courses
     */
    public Iterable<Course> studentCourses(){
        return studentCoursesProvider.provideStudentInProgressAndClosedCourses(student);
    }

    /**
     * List the students taken exams for a given course.
     * @param course the course
     * @return an Iterable of the student's taken exams
     */
    public List<TakenExam> takenExamsFromCourse(Course course) {
        List<TakenExam> takenExams = takenExamProvider.provideUserGradedTakenExamsFromCourse(course, student);

        return takenExams;
    }

}
