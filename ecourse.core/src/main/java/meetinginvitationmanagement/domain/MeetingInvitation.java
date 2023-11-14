package meetinginvitationmanagement.domain;

import coursemanagement.domain.Course;
import eapli.framework.domain.model.AggregateRoot;
import ecourseusermanagement.domain.ECourseUser;
import meetingmanagement.domain.Meeting;
import meetingmanagement.domain.MeetingStatus;

import javax.persistence.*;
import java.util.Objects;

/**
 * The type Meeting invitation.
 */
@Entity
public class MeetingInvitation implements AggregateRoot<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private ECourseUser invitedParticipant;

    @ManyToOne
    private Meeting meeting;

    @Enumerated(EnumType.STRING)
    private InvitationStatus invitationStatus;

    /**
     * Instantiates a new meeting invitation.
     *
     * @param invitedParticipant the invited participant
     *
     * @throws IllegalArgumentException if the meeting is null
     * @throws IllegalArgumentException if the invitedParticipant is null
     */
    public MeetingInvitation(final ECourseUser invitedParticipant, final Meeting meeting) {
        // Verify if the invitedParticipant is not null
        if (invitedParticipant == null) {
            throw new IllegalArgumentException("Invited Participant can't be null");
        }

        this.invitedParticipant = invitedParticipant;

        // Verify if the meeting is not null
        if (meeting == null) {
            throw new IllegalArgumentException("Meeting can't be null");
        }

        this.meeting = meeting;

        // Default invitation status
        this.invitationStatus = InvitationStatus.PENDING;
    }

    public MeetingInvitation() {
        // For ORM
    }

    public ECourseUser invitedParticipant() {
        return this.invitedParticipant;
    }

    /**
     * Invitation status string.
     *
     * @return the string
     */
    public InvitationStatus invitationStatus() {
        return invitationStatus;
    }

    /**
     * Accept a meeting invitation.
     *
     * @throws IllegalStateException if the meeting invitation status is not set to scheduled
     */
    public void accept() {
        // Verify if the meeting invitation is already accepted
        if (invitationStatus == InvitationStatus.ACCEPTED) {
            throw new IllegalStateException("Meeting invitation already accepted");
        }

        // Verify if the meeting invitation is already canceled
        if (invitationStatus == InvitationStatus.CANCELED) {
            throw new IllegalStateException("Meeting invitation already canceled");
        }

        // Change the meeting invitation status to accepted
        invitationStatus = InvitationStatus.ACCEPTED;
    }

    /**
     * Reject a meeting invitation.
     *
     * @throws IllegalStateException if the meeting invitation status is not set to scheduled
     */
    public void reject() {
        // Verify if the meeting invitation is already rejected
        if (invitationStatus == InvitationStatus.REJECTED) {
            throw new IllegalStateException("Meeting invitation already rejected!");
        }

        // Verify if the meeting has been canceled
        if (invitationStatus == InvitationStatus.CANCELED) {
            throw new IllegalStateException("Meeting invitation already canceled!");
        }

        // Change the meeting invitation status to rejected
        invitationStatus = InvitationStatus.REJECTED;
    }

    /**
     * Cancel a meeting invitation.
     *
     * @throws IllegalStateException if the meeting invitation is already canceled
     */
    public void cancel(){
        // Verify if the meeting invitation is already canceled
        if (invitationStatus == InvitationStatus.CANCELED) {
            throw new IllegalStateException("Meeting invitation already canceled");
        }

        // Change the meeting invitation status to canceled
        invitationStatus = InvitationStatus.CANCELED;
    }

    @Override
    public String toString() {
        return "Meeting Owner: " + meeting.meetingOwner().email() + " | Meeting Date: " +
                meeting.scheduledMeetingDate().retrieveDate() + " | Invitation Status - " + invitationStatus;
    }

    @Override
    public boolean sameAs(Object o) {
        if (this == o) return true;

        if (!(o instanceof MeetingInvitation)) return false;

        MeetingInvitation course = (MeetingInvitation) o;

        return meeting.equals(course.meeting) &&
                invitedParticipant.equals(course.invitedParticipant) &&
                invitationStatus == course.invitationStatus;
    }

    @Override
    public Long identity() {
        return this.id;
    }
}
