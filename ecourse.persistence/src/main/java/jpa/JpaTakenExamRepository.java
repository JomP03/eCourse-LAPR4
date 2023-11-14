package jpa;

import coursemanagement.domain.Course;
import ecourseusermanagement.domain.*;
import exammanagement.domain.*;
import takenexammanagement.domain.*;
import takenexammanagement.repository.*;

import javax.persistence.*;

public class JpaTakenExamRepository extends eCourseJpaRepositoryBase<TakenExam, Long, Long> implements TakenExamRepository {

    public JpaTakenExamRepository() {
        super("long");
    }

    @Override
    public boolean isExamAlreadyTaken(Exam selectedExam, ECourseUser student) {
        final TypedQuery<TakenExam> query = entityManager().createQuery("SELECT ex " +
                        "FROM TakenExam ex WHERE ex.student.email = :studentEmail AND ex.exam.id = :examId ",
                TakenExam.class);
        query.setParameter("studentEmail", student.email());
        query.setParameter("examId", selectedExam.identity());

        try {
            return query.getSingleResult() != null;
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public Iterable<TakenExam> findByCourseAndStudent(Course course, ECourseUser user) {
        final TypedQuery<TakenExam> query = entityManager().createQuery("SELECT ex " +
                        "FROM TakenExam ex WHERE ex.student.email = :studentEmail AND ex.exam.course.courseCode = :courseId ",
                TakenExam.class);
        query.setParameter("studentEmail", user.email());
        query.setParameter("courseId", course.identity());

        return query.getResultList();
    }

    @Override
    public Iterable<TakenExam> findByExam(Exam exam) {
        final TypedQuery<TakenExam> query = entityManager().createQuery("SELECT ex " +
                        "FROM TakenExam ex WHERE ex.exam.id = :examId ",
                TakenExam.class);
        query.setParameter("examId", exam.identity());

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
