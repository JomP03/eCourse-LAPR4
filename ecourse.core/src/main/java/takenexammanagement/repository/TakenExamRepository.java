package takenexammanagement.repository;

import coursemanagement.domain.*;
import eapli.framework.domain.repositories.DomainRepository;
import ecourseusermanagement.domain.*;
import exammanagement.domain.*;
import takenexammanagement.domain.TakenExam;

public interface TakenExamRepository extends DomainRepository<Long, TakenExam> {


    /**
     * Finds if a student has already taken an exam
     * @param student the student
     * @param selectedExam the course
     * @return true if the student has already taken the exam, false otherwise
     */
    boolean isExamAlreadyTaken(Exam selectedExam, ECourseUser student);

    /**
     * Finds the taken exam of a student from a course
     * @param course the course
     * @param user the student
     * @return the taken exam
     */
    Iterable<TakenExam> findByCourseAndStudent(Course course, ECourseUser user);

    /**
     * Finds all taken exams of an exam
     * @param exam the chosen exam
     * @return the iterable containing the taken exams
     */
    Iterable<TakenExam> findByExam(Exam exam);
}
