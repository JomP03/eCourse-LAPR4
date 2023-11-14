package meetingmanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import ecourseusermanagement.domain.ECourseUser;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * The type Meeting.
 */
@Entity
public class Meeting implements AggregateRoot<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private MeetingDate meetingDate;

    @Embedded
    private MeetingDuration meetingDuration;

    @Enumerated(EnumType.STRING)
    private MeetingStatus meetingStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    private ECourseUser meetingOwner;

    protected Meeting() {
        // For ORM
    }

    /**
     * Instantiates a new Meeting.
     *
     * @param meetingDate     the meeting date
     * @param meetingDuration the meeting duration
     * @param meetingOwner    the meeting owner
     * @throws IllegalArgumentException the illegal argument exception
     */
    public Meeting(LocalDateTime meetingDate, Integer meetingDuration, ECourseUser meetingOwner) {
        // Verify if the eCourseUser is not null
        if (meetingOwner == null) {
            throw new IllegalArgumentException("Meeting owner cannot be null");
        }

        this.meetingOwner = meetingOwner;

        // Create the meeting date
        this.meetingDate = new MeetingDate(meetingDate);

        // Create the meeting duration
        this.meetingDuration = new MeetingDuration(meetingDuration);

        // Meeting status is set to schedule
        this.meetingStatus = MeetingStatus.SCHEDULED;
    }

    /**
     * Current status for the meeting.
     *
     * @return the current meeting status
     */
    public MeetingStatus currentStatus() {
        return this.meetingStatus;
    }

    /**
     * Cancel meeting.
     *
     * @throws IllegalStateException if the meeting is already canceled
     * @throws IllegalStateException if the meeting is already finished
     * @throws IllegalStateException if the meeting is in the past
     */
    public void cancelMeeting() {
        // Verify if the meeting is already canceled
        if (this.currentStatus() == MeetingStatus.CANCELLED) {
            throw new IllegalStateException("Can't cancel an already canceled Meeting!");
        }

        // Verify if the meeting is occurring
        else if (this.currentStatus() == MeetingStatus.IN_PROGRESS) {
            throw new IllegalStateException("Can't cancel an ongoing Meeting!");
        }

        // Verify if the meeting is already finished
        else if (this.currentStatus() == MeetingStatus.FINISHED) {
            throw new IllegalStateException("Can't cancel a finished Meeting!");
        }

        // Set the meeting status to canceled
        this.meetingStatus = MeetingStatus.CANCELLED;
    }

    /**
     * Finish meeting.
     *
     * @throws IllegalStateException if the meeting is already canceled
     * @throws IllegalStateException if the meeting is already finished
     */
    public void finishMeeting() {
        // Verify if the meeting is ongoing
        if (this.currentStatus() != MeetingStatus.IN_PROGRESS) {
            throw new IllegalStateException("A Meeting can only be finished if it is ongoing!");
        }

        // Set the meeting status to finished
        this.meetingStatus = MeetingStatus.FINISHED;
    }

    /**
     * Start meeting.
     *
     * @throws IllegalStateException if the meeting is already canceled
     * @throws IllegalStateException if the meeting is already finished
     * @throws IllegalStateException if the meeting has already started
     */
    public void startMeeting() {
        // Verify if the meeting is already canceled
        if (this.currentStatus() == MeetingStatus.CANCELLED) {
            throw new IllegalStateException("It is not possible to start a canceled meeting");
        }

        // Verify if the meeting is already finished
        else if (this.currentStatus() == MeetingStatus.FINISHED) {
            throw new IllegalStateException("It is not possible to start a finished meeting");
        }

        // Verify if the meeting has already started
        else if (this.currentStatus() == MeetingStatus.IN_PROGRESS) {
            throw new IllegalStateException("It is not possible to start a meeting that has already started");
        }

        // Set the meeting status to started
        this.meetingStatus = MeetingStatus.IN_PROGRESS;
    }

    /**
     * Retrieve the meeting owner.
     *
     * @return the meeting owner
     */
    public ECourseUser meetingOwner() {
        return this.meetingOwner;
    }

    /**
     * Retrieve the meeting date.
     *
     * @return the meeting date
     */
    public MeetingDate scheduledMeetingDate() {
        return this.meetingDate;
    }

    /**
     * Retrieve the meeting duration.
     *
     * @return the meeting duration
     */
    public MeetingDuration expectedDuration() {
        return this.meetingDuration;
    }

    @Override
    public String toString() {
        return "Date = " + meetingDate.toString() + "\n" +
                "Duration = " + meetingDuration.retrieveDuration() + " min" + "\n" +
                "Status = " + meetingStatus + "\n" +
                "Owner = " + meetingOwner.email() + "\n";
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof Meeting)) {
            return false;
        }

        final Meeting that = (Meeting) other;

        return this.meetingOwner.sameAs(that.meetingOwner) && this.meetingDate.equals(that.meetingDate) &&
                this.meetingDuration.equals(that.meetingDuration);
    }

    @Override
    public Long identity() {
        return this.id;
    }
}
