package exammanagement.application;

import coursemanagement.domain.Course;
import eapli.framework.validations.*;
import exammanagement.domain.*;
import exammanagement.repository.ExamRepository;

import java.util.ArrayList;
import java.util.List;

public class CourseExamsProvider implements ICourseExamsProvider {

    private final ExamRepository examRepository;

    /**
     * Instantiates a new Course exams provider.
     *
     * @param examRepository the exam repository
     */
    public CourseExamsProvider(ExamRepository examRepository) {
        Preconditions.noneNull(examRepository);
        this.examRepository = examRepository;
    }

    @Override
    public Iterable<FormativeExam> provideCourseFormativeExams(Course course) {
        return examRepository.findFormativeExamByCourse(course);
    }

    @Override
    public Iterable<AutomatedExam> provideCourseAutomatedExams(Course course) {
        return examRepository.findAutomatedExamByCourse(course);
    }

    @Override
    public Iterable<AutomatedExam> listFutureAutomatedExams(Course course){
        return examRepository.findFutureAutomatedExamsByCourse(course);
    }

    @Override
    public List<Exam> provideFutureCourseExams(Course course) {
        List<AutomatedExam> automatedExams = (List<AutomatedExam>) examRepository.findFutureAutomatedExamsByCourse(course);
        List<FormativeExam> formativeExams = (List<FormativeExam>) examRepository.findFormativeExamByCourse(course);

        List<Exam> exams = new ArrayList<>();

        exams.addAll(automatedExams);
        exams.addAll(formativeExams);

        return exams;
    }

    @Override
    public Iterable<AutomatedExam> provideActiveCourseAutomatedExams(Course course) {
        return examRepository.findActiveAutomatedExamsByCourse(course);
    }

    @Override
    public Iterable<Exam> provideSolvedExams(Course course) {
        List<FormativeExam> formativeExams = (List<FormativeExam>) provideCourseFormativeExams(course);
        List<AutomatedExam> automatedExams = (List<AutomatedExam>) provideActiveCourseAutomatedExams(course);

        List<Exam> exams = new ArrayList<>();
        exams.addAll(formativeExams);
        exams.addAll(automatedExams);

        // Remove exams that don't have grading feedback
        exams.removeIf(exam -> exam.header().gradingFeedBackType().equals(GradingType.NONE));

        return exams;
    }
}
