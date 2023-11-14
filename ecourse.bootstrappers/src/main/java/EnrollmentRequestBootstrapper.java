import eapli.framework.actions.Action;
import enrollmentrequestmanagement.application.register.RegisterEnrollmentRequestController;
import enrollmentrequestmanagement.domain.EnrollmentRequest;
import persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentRequestBootstrapper implements Action {

    private final RegisterEnrollmentRequestController registerEnrollmentRequestController =
            new RegisterEnrollmentRequestController(PersistenceContext.repositories().enrollmentRequests());

    @Override
    public boolean execute() {

        // Iterable of Enrollment Requests
        List<EnrollmentRequest> enrollmentRequests = new ArrayList<>();

        // Pending Enrollment Request 1
        EnrollmentRequest enrollmentRequest = new EnrollmentRequest(
                PersistenceContext.repositories().courses().findByCode("PYTHON"),
                PersistenceContext.repositories().eCourseUsers().findByEmail("mariaferreira@gmail.com").get());
        enrollmentRequests.add(enrollmentRequest);

        // Pending Enrollment Request 2
        EnrollmentRequest enrollmentRequest2 = new EnrollmentRequest(
                PersistenceContext.repositories().courses().findByCode("PYTHON"),
                PersistenceContext.repositories().eCourseUsers().findByEmail("anapereira@gmail.com").get());
        enrollmentRequests.add(enrollmentRequest2);

        // Pending Enrollment Request 3
        EnrollmentRequest enrollmentRequest3 = new EnrollmentRequest(
                PersistenceContext.repositories().courses().findByCode("JAVA"),
                PersistenceContext.repositories().eCourseUsers().findByEmail("luissantos@gmail.com").get());
        enrollmentRequests.add(enrollmentRequest3);

        // Pending Enrollment Request 4
        EnrollmentRequest enrollmentRequest4 = new EnrollmentRequest(
                PersistenceContext.repositories().courses().findByCode("PYTHON"),
                PersistenceContext.repositories().eCourseUsers().findByEmail("filipemoreira@gmail.com").get());
        enrollmentRequests.add(enrollmentRequest4);

        // Pending Enrollment Request 5
        EnrollmentRequest enrollmentRequest5 = new EnrollmentRequest(
                PersistenceContext.repositories().courses().findByCode("JAVA"),
                PersistenceContext.repositories().eCourseUsers().findByEmail("filipemoreira@gmail.com").get());
        enrollmentRequests.add(enrollmentRequest5);

        // Pending Enrollment Request 6
        EnrollmentRequest enrollmentRequest6 = new EnrollmentRequest(
                PersistenceContext.repositories().courses().findByCode("PYTHON"),
                PersistenceContext.repositories().eCourseUsers().findByEmail("davidsilva@gmail.com").get());
        enrollmentRequests.add(enrollmentRequest6);

        // Pending Enrollment Request 7
        EnrollmentRequest enrollmentRequest7 = new EnrollmentRequest(
                PersistenceContext.repositories().courses().findByCode("JAVA"),
                PersistenceContext.repositories().eCourseUsers().findByEmail("karenlopes@gmail.com").get());
        enrollmentRequests.add(enrollmentRequest7);

        // Pending Enrollment Request 8
        EnrollmentRequest enrollmentRequest8 = new EnrollmentRequest(
                PersistenceContext.repositories().courses().findByCode("JAVA"),
                PersistenceContext.repositories().eCourseUsers().findByEmail("carlalopes@gmail.com").get());
        enrollmentRequests.add(enrollmentRequest8);

        // Save Enrollment Requests
        save(enrollmentRequests);

        return true;
    }

    private void save(Iterable<EnrollmentRequest> toSaveEnrollmentRequests) {
        for (EnrollmentRequest enrollmentRequest : toSaveEnrollmentRequests) {
            PersistenceContext.repositories().enrollmentRequests().save(enrollmentRequest);
        }
    }
}
