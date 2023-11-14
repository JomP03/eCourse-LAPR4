package meetingmanagement.domain;

import eapli.framework.domain.model.ValueObject;

public enum MeetingStatus implements ValueObject {

    /**
     * The meeting is scheduled.
     */
    SCHEDULED,

    /**
     * The meeting is in progress.
     */
    IN_PROGRESS,

    /**
     * The meeting is finished.
     */
    FINISHED,

    /**
     * The meeting is cancelled.
     */
    CANCELLED

}
