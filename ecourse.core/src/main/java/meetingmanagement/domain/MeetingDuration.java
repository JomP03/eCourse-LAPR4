package meetingmanagement.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MeetingDuration implements ValueObject {

    private static final long serialVersionUID = 1L;

    @Column
    private Integer meetingDuration;

    protected MeetingDuration() {
        // for ORM
    }

    public MeetingDuration(Integer meetingDuration) {
        // Verify if the meeting duration is null
        if (meetingDuration == null) {
            throw new IllegalArgumentException("A meeting duration must be provided!");
        }

        // Verify if the meeting duration is valid
        if (meetingDuration <= 0) {
            throw new IllegalArgumentException("A meeting duration must be greater than 0!");
        }

        this.meetingDuration = meetingDuration;
    }

    /**
     * Retrieve duration integer.
     *
     * @return the integer
     */
    public Integer retrieveDuration() {
        return this.meetingDuration;
    }

}
