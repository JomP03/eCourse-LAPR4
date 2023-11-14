package enrollmentrequestmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.util.StringPredicates;

import javax.persistence.Column;

public class EnrollmentDecisionReason implements ValueObject {

    private static final long serialVersionUID = 1L;

    @Column
    private String reason;

    /**
     * Instantiates a new Enrollment decision reason.
     *
     * @param reason the reason
     */
    public EnrollmentDecisionReason(final String reason) {
        // Verify if the reason is not null
        if (StringPredicates.isNullOrEmpty(reason)) {
            this.reason = "No reason given!";
        }

        else this.reason = reason;
    }

    protected EnrollmentDecisionReason() {
        // for ORM
    }

    @Override
    public String toString() {
        return this.reason;
    }

}
