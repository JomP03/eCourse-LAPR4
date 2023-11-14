package jpa;

import coursemanagement.domain.Course;
import ecourseusermanagement.domain.*;
import exammanagement.domain.*;
import exammanagement.repository.ExamRepository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class JpaExamRepository extends eCourseJpaRepositoryBase<Exam, Long, Long> implements ExamRepository {

    public JpaExamRepository() {
            super("id");
        }
    @Override
    public Exam findExamByTitle(String examTitle) {
        final TypedQuery<Exam> query = entityManager().createQuery("SELECT ex " +
                        "FROM Exam ex WHERE ex.title = :examTitle ",
                Exam.class);
        query.setParameter("examTitle", examTitle);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Iterable<FormativeExam> findFormativeExamByCourse(Course course) {
        final TypedQuery<FormativeExam> query = entityManager().createQuery("SELECT ex " +
                        "FROM FormativeExam ex WHERE ex.course.courseCode = :courseCode ",
                FormativeExam.class);
        query.setParameter("courseCode", course.identity());

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Iterable<AutomatedExam> findAutomatedExamByCourse(Course course) {
        final TypedQuery<AutomatedExam> query = entityManager().createQuery("SELECT ex " +
                        "FROM AutomatedExam ex WHERE ex.course.courseCode = :courseCode ",
                AutomatedExam.class);
        query.setParameter("courseCode", course.identity());

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Iterable<AutomatedExam> findFutureAutomatedExamsByCourse(Course course) {
        final TypedQuery<AutomatedExam> query = entityManager().createQuery("SELECT ex " +
                        "FROM AutomatedExam ex WHERE ex.course.courseCode = :courseCode AND ex.openPeriod.closeDate > CURRENT_DATE ",
                AutomatedExam.class);
        query.setParameter("courseCode", course.identity());

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Iterable<FormativeExam> findFormativeExamsByCreator(ECourseUser eCourseUser) {
        final TypedQuery<FormativeExam> query = entityManager().createQuery("SELECT ex " +
                        "FROM FormativeExam ex WHERE ex.creator.id = :creatorId ",
                FormativeExam.class);
        query.setParameter("creatorId", eCourseUser.identity());

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Exam findExamByTitleIgnoringExamBeingUpdated(Exam title, Exam ignore) {
        final TypedQuery<Exam> query = entityManager().createQuery("SELECT ex " +
                        "FROM Exam ex WHERE ex.title = :title AND ex.title <> :ignore ",
                Exam.class);
        query.setParameter("title", title.title());
        query.setParameter("ignore", ignore.title());

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Iterable<AutomatedExam> findTeacherAutomatedExams(ECourseUser examCreator) {
        final TypedQuery<AutomatedExam> query = entityManager().createQuery("SELECT ex " +
                        "FROM AutomatedExam ex WHERE ex.creator.id = :creatorId ",
                AutomatedExam.class);
        query.setParameter("creatorId", examCreator.identity());

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public boolean isTitleAlreadyAssigned(String toCheckTitle) {
        return findExamByTitle(toCheckTitle) != null;
    }

    @Override
    public Iterable<AutomatedExam> findActiveAutomatedExamsByCourse(Course course) {
        final TypedQuery<AutomatedExam> query = entityManager().createQuery("SELECT ex " +
                        "FROM AutomatedExam ex WHERE ex.course.courseCode = :courseCode AND ex.openPeriod.openDate <= CURRENT_DATE AND ex.openPeriod.closeDate >= CURRENT_DATE ",
                AutomatedExam.class);
        query.setParameter("courseCode", course.identity());

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
