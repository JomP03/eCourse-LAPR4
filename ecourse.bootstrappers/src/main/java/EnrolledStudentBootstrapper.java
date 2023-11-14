import coursemanagement.domain.Course;
import eapli.framework.actions.Action;
import enrolledstudentmanagement.application.RegisterEnrolledStudentController;
import enrollmentrequestmanagement.application.manage.ListEnrollmentRequestController;
import persistence.PersistenceContext;

import java.util.List;

public class EnrolledStudentBootstrapper implements Action {

    private final RegisterEnrolledStudentController registerEnrolledStudentController =
            new RegisterEnrolledStudentController(PersistenceContext.repositories().enrolledStudents());

    private final ListEnrollmentRequestController listEnrollmentRequestController =
            new ListEnrollmentRequestController(PersistenceContext.repositories().enrollmentRequests());

    private Iterable<Course> courses() {
        return PersistenceContext.repositories().courses().findAll();
    }

    @Override
    public boolean execute() {

        // Enroll state courses
        List<Course> courses = (List<Course>) courses();

        registerEnrolledStudentController.registerEnrolledStudent(courses.get(6), PersistenceContext.repositories().
                eCourseUsers().findByEmail("mariaferreira@gmail.com").orElse(null));

        registerEnrolledStudentController.registerEnrolledStudent(courses.get(7), PersistenceContext.repositories().
                eCourseUsers().findByEmail("mariaferreira@gmail.com").orElse(null));

        registerEnrolledStudentController.registerEnrolledStudent(courses.get(8), PersistenceContext.repositories().
                eCourseUsers().findByEmail("mariaferreira@gmail.com").orElse(null));

        registerEnrolledStudentController.registerEnrolledStudent(PersistenceContext.repositories().courses().findByCode("SQL"), PersistenceContext.repositories().
                eCourseUsers().findByEmail("mariaferreira@gmail.com").orElse(null));

        registerEnrolledStudentController.registerEnrolledStudent(courses.get(1), PersistenceContext.repositories().
                eCourseUsers().findByEmail("eduardomartins@gmail.com").orElse(null));

        registerEnrolledStudentController.registerEnrolledStudent(courses.get(0), PersistenceContext.repositories().
                eCourseUsers().findByEmail("isabelsilva@gmail.com").orElse(null));

        registerEnrolledStudentController.registerEnrolledStudent(courses.get(1), PersistenceContext.repositories().
                eCourseUsers().findByEmail("helenapereira@gmail.com").orElse(null));

        registerEnrolledStudentController.registerEnrolledStudent(courses.get(2), PersistenceContext.repositories().
                eCourseUsers().findByEmail("carlalopes@gmail.com").orElse(null));

        registerEnrolledStudentController.registerEnrolledStudent(courses.get(1), PersistenceContext.repositories().
                eCourseUsers().findByEmail("carlalopes@gmail.com").orElse(null));

        registerEnrolledStudentController.registerEnrolledStudent(courses.get(0), PersistenceContext.repositories().
                eCourseUsers().findByEmail("carlalopes@gmail.com").orElse(null));

        registerEnrolledStudentController.registerEnrolledStudent(courses.get(8), PersistenceContext.repositories().
                eCourseUsers().findByEmail("carlalopes@gmail.com").orElse(null));

        registerEnrolledStudentController.registerEnrolledStudent(courses.get(0), PersistenceContext.repositories().
                eCourseUsers().findByEmail("mariaferreira@gmail.com").orElse(null));

        registerEnrolledStudentController.registerEnrolledStudent(courses.get(5), PersistenceContext.repositories().
                eCourseUsers().findByEmail("mariaferreira@gmail.com").orElse(null));

        registerEnrolledStudentController.registerEnrolledStudent(courses.get(8), PersistenceContext.repositories().
                eCourseUsers().findByEmail("mariaferreira@gmail.com").orElse(null));

        registerEnrolledStudentController.registerEnrolledStudent(courses.get(1), PersistenceContext.repositories().
                eCourseUsers().findByEmail("mariaferreira@gmail.com").orElse(null));

        return true;
    }

}
