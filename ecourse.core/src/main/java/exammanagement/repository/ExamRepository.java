package exammanagement.repository;



import coursemanagement.domain.Course;
import eapli.framework.domain.repositories.DomainRepository;
import ecourseusermanagement.domain.*;
import exammanagement.domain.*;


/**
 * The interface for an exam repository.
 */
public interface ExamRepository extends DomainRepository<Long, Exam> {

    /**
     * Finds an exam by its title.
     * @param examTitle the title of the exam
     * @return the exam
     */
    Exam findExamByTitle(String examTitle);

    /**
     * Find formative exams referring to a course.
     *
     * @param course the course which the exam refers to
     * @return the iterable containing the exams
     */
    Iterable<FormativeExam> findFormativeExamByCourse(Course course);

    /**
     * Find automated exams referring to a course.
     *
     * @param course the course which the exam refers to
     * @return the iterable containing the exams
     */
    Iterable<AutomatedExam> findAutomatedExamByCourse(Course course);

    /**
     * Find active exams referring to a course.
     *
     * @param course the course which the exam refers to
     * @return the iterable containing the exams
     */
    Iterable<AutomatedExam> findFutureAutomatedExamsByCourse(Course course);

    /**
     * Find formative exams created by a user.
     *
     * @param eCourseUser The user who created the exams
     * @return the iterable containing the exams
     */
    Iterable<FormativeExam> findFormativeExamsByCreator(ECourseUser eCourseUser);

    /**
     * Find automated exams created by title, ignoring the exam being updated, since it is allowed to have the same title.
     *
     * @param title the title of the exam
     * @param ignore the exam being updated
     * @return the exam
     */
    Exam findExamByTitleIgnoringExamBeingUpdated(Exam title, Exam ignore);

    /**
     * Find automated exams created by a user.
     * @param examCreator the user who created the exams
     * @return the iterable containing the exams
     */
    Iterable<AutomatedExam> findTeacherAutomatedExams(ECourseUser examCreator);

/**
     * Verify if the title is already assigned to an exam.
     * @param toCheckTitle the title to check
     * @return the true if the title is already assigned, false otherwise
     */
    boolean isTitleAlreadyAssigned(String toCheckTitle);

    /**
     * Provides the active automated exams for a specified course.
     *
     * @param course the specified course
     * @return the iterable of the active automated exams for the specified course
     */
    Iterable<AutomatedExam> findActiveAutomatedExamsByCourse(Course course);
}
