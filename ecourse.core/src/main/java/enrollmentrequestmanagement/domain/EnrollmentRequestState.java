package enrollmentrequestmanagement.domain;

public enum EnrollmentRequestState {

    // Enrollment request has been created but not yet approved
    PENDING,
    // Enrollment request has been approved
    APPROVED,
    // Enrollment request has been rejected
    REJECTED

}