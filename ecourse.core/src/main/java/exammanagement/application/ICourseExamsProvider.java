package exammanagement.application;

import coursemanagement.domain.Course;
import exammanagement.domain.AutomatedExam;
import exammanagement.domain.Exam;
import exammanagement.domain.FormativeExam;

import java.util.List;

public interface ICourseExamsProvider {

    /**
     * List course formative exams iterable.
     *
     * @param course the course
     * @return the iterable
     */
    Iterable<FormativeExam> provideCourseFormativeExams(Course course);

    /**
     * List course automated exams iterable.
     *
     * @param course the course
     * @return the iterable
     */
    Iterable<AutomatedExam> provideCourseAutomatedExams(Course course);

    /**
     * List future automated exams iterable.
     *
     * @param course the course
     * @return the iterable
     */
    Iterable<AutomatedExam> listFutureAutomatedExams(Course course);

    /**
     * Provides the future exams for a specified course.
     *
     * @param course the specified course
     * @return the iterable of the exams for the specified course
     */
    List<Exam> provideFutureCourseExams(Course course);

    /**
     * Provides the active automated exams for a specified course.
     *
     * @param course the specified course
     * @return the iterable of the active automated exams for the specified course
     */
    Iterable<AutomatedExam> provideActiveCourseAutomatedExams(Course course);

    /**
     * Provides the exams (formative and automated) that have or could have been solved by a student in a course.
     *
     * @param course the specified course
     * @return the iterable of the solved exams for the specified course
     */
    Iterable<Exam> provideSolvedExams(Course course);
}
