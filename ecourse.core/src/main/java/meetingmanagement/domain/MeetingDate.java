package meetingmanagement.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class MeetingDate implements ValueObject {

    private static final long serialVersionUID = 1L;

    @Column
    private LocalDateTime meetingDate;

    protected MeetingDate() {
        // for ORM
    }

    /**
     * Instantiates a new Meeting date.
     *
     * @param meetingDate the date
     *
     * @throws IllegalArgumentException if the date is null
     * @throws IllegalArgumentException if the date is in the past
     */
    public MeetingDate(LocalDateTime meetingDate) {
        // Verify if the date is null
        if (meetingDate == null) {
            throw new IllegalArgumentException("A valid date must be provided");
        }

        // Verify if the date is in the past
        if (meetingDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("A meeting can not be scheduled in the past");
        }

        this.meetingDate = meetingDate;
    }

    /**
     * Retrieve date local date time.
     *
     * @return the local date time
     */
    public LocalDateTime retrieveDate() {
        return this.meetingDate;
    }

    @Override
    public String toString() {
        return this.meetingDate.getDayOfMonth() + " of " + this.meetingDate.getMonth().toString() +
                " of " + this.meetingDate.getYear() + " at " + this.meetingDate.getHour() +
                    ":" + this.meetingDate.getMinute();
    }

}
