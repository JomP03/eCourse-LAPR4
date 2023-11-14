package coursemanagement.domain;

import eapli.framework.domain.model.*;

public enum CourseState implements ValueObject {

    // Course has been created but not yet opened
    CLOSE,
    // Course is open
    OPEN,
    // Course is open for enrollments
    ENROLL,
    // Course is closed for enrollments, so it is in progress
    IN_PROGRESS,
    // Course has ended
    CLOSED;
}
