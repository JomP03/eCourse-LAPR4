package jpa;

import classmanagement.domain.Class;
import classmanagement.domain.ExtraClass;
import classmanagement.domain.RecurrentClass;
import classmanagement.repository.ClassRepository;
import coursemanagement.domain.Course;
import ecourseusermanagement.domain.ECourseUser;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;

public class JpaClassRepository extends eCourseJpaRepositoryBase<Class, Long, Long> implements ClassRepository {

    public JpaClassRepository() {
        super("id");
    }

    @Override
    public Iterable<RecurrentClass> findCourseRecurrentClasses(String courseCode) {
        final TypedQuery<RecurrentClass> query = entityManager().createQuery(
                "SELECT c FROM RecurrentClass c WHERE c.classCourse.courseCode.courseCode = :code", RecurrentClass.class);
        query.setParameter("code", courseCode);
        return query.getResultList();
    }


    @Override
    public RecurrentClass findRecurrentClassByTitle(String classTitle) {
        final TypedQuery<RecurrentClass> query = entityManager().createQuery(
                "SELECT c FROM RecurrentClass c WHERE c.classTitle.title = :title", RecurrentClass.class);
        query.setParameter("title", classTitle);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Iterable<RecurrentClass> findCourseRecurrentClassesWhereTeacherInvolved(String courseCode, ECourseUser user) {
        final TypedQuery<RecurrentClass> query = entityManager().createQuery(
                "SELECT c FROM RecurrentClass c JOIN c.classCourse co " +
                        "WHERE co.courseCode.courseCode = :code " +
                        "AND c.classTeacher = :user " +
                        "AND co.courseState != 'Closed'",
                RecurrentClass.class);

        query.setParameter("code", courseCode);
        query.setParameter("user", user);
        return query.getResultList();
    }

    @Override
    public Iterable<ExtraClass> findExtraClassByCourseCodeAndDateRange(String courseCode, LocalDateTime startDay, LocalDateTime endDay) {
        TypedQuery<ExtraClass> query = entityManager().createQuery(
                "SELECT c FROM ExtraClass c " +
                        "WHERE c.classCourse.courseCode.courseCode = :courseCode " +
                        "AND c.extraClassDate.extraClassDate >= :startDay " +
                        "AND c.extraClassDate.extraClassDate <= :endDay", ExtraClass.class);
        query.setParameter("courseCode", courseCode);
        query.setParameter("startDay", startDay);
        query.setParameter("endDay", endDay);
        return query.getResultList();
    }

    @Override
    public Iterable<ExtraClass> findExtraClassByDateRangeAndTeacherWithNonMatchingCourse(String courseCode, LocalDateTime startDay, LocalDateTime endDay, ECourseUser teacher) {
        TypedQuery<ExtraClass> query = entityManager().createQuery(
                "SELECT c FROM ExtraClass c " +
                        "WHERE c.classCourse.courseCode.courseCode <> :courseCode " +
                        "AND c.extraClassDate.extraClassDate >= :startDay " +
                        "AND c.extraClassDate.extraClassDate <= :endDay " +
                        "AND c.classTeacher = :teacher", ExtraClass.class);
        query.setParameter("courseCode", courseCode);
        query.setParameter("startDay", startDay);
        query.setParameter("endDay", endDay);
        query.setParameter("teacher", teacher);
        return query.getResultList();
    }

    @Override
    public Iterable<RecurrentClass> findRecurrentClassesForStudent(Course course) {
        final TypedQuery<RecurrentClass> query =
                entityManager().createQuery("SELECT r FROM RecurrentClass r " +
                        "WHERE r.classCourse.courseCode = :course",
                        RecurrentClass.class);

        query.setParameter("course", course.identity());

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Iterable<RecurrentClass> findRecurrentClassesForTeacher(Course course, ECourseUser teacher) {
        final TypedQuery<RecurrentClass> query =
                entityManager().createQuery("SELECT r FROM RecurrentClass r " +
                                "WHERE r.classCourse.courseCode = :course " +
                                "AND r.classTeacher.id = :teacher",
                        RecurrentClass.class);

        query.setParameter("course", course.identity());
        query.setParameter("teacher", teacher.identity());

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Iterable<ExtraClass> findExtraClassesForTeacher(Course course, ECourseUser teacher) {
        final TypedQuery<ExtraClass> query =
                entityManager().createQuery("SELECT e FROM ExtraClass e " +
                                "WHERE e.classCourse.courseCode = :course " +
                                "AND e.classTeacher.id = :teacher",
                        ExtraClass.class);

        query.setParameter("course", course.identity());
        query.setParameter("teacher", teacher.identity());

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public ExtraClass findExtraClassByTitle(String title) {
        final TypedQuery<ExtraClass> query = entityManager().createQuery(
                "SELECT c FROM ExtraClass c WHERE c.classTitle.title = :title", ExtraClass.class);
        query.setParameter("title", title);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Iterable<RecurrentClass> findTeacherRecurrentClasses(ECourseUser eCourseUser) {
        final TypedQuery<RecurrentClass> query =
                entityManager().createQuery("SELECT r FROM RecurrentClass r " +
                                "WHERE r.classTeacher.id = :teacher",
                        RecurrentClass.class);

        query.setParameter("teacher", eCourseUser.identity());

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Iterable<ExtraClass> findExtraClassesForStudent(Course course, ECourseUser student) {
        final TypedQuery<ExtraClass> query =
                entityManager().createQuery("SELECT e FROM ExtraClass e " +
                        "JOIN e.extraClassParticipants p " +
                        "WHERE e.classCourse.courseCode = :course " +
                        "AND p.id = :student",
                        ExtraClass.class);

        query.setParameter("course", course.identity());
        query.setParameter("student", student.identity());

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }


}
