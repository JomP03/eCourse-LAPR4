package meetinginvitationmanagement.domain;

import eapli.framework.domain.model.ValueObject;

public enum InvitationStatus implements ValueObject {

    /**
     * The invitation is pending.
     */
    PENDING,

    /**
     * The invitation was accepted.
     */
    ACCEPTED,

    /**
     * The invitation was rejected.
     */
    REJECTED,

    /**
     * The invitation was canceled.
     */
    CANCELED,

}
