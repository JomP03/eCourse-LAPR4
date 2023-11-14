import eapli.framework.actions.Action;
import meetinginvitationmanagement.domain.MeetingInvitation;
import meetinginvitationmanagement.repository.IMeetingInvitationRepository;
import meetingmanagement.application.ScheduleMeetingController;
import meetingmanagement.domain.Meeting;
import meetingmanagement.repository.IMeetingRepository;
import persistence.PersistenceContext;

import java.io.Console;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MeetingBootstrapper implements Action {

    private final IMeetingRepository meetingRepository = PersistenceContext.repositories().meetings();

    private final IMeetingInvitationRepository meetingInvitationRepository =
            PersistenceContext.repositories().meetingInvitations();

    @Override
    public boolean execute() {
        // Create Meeting 1
        Meeting meeting1 = new Meeting(LocalDateTime.of(2023, 12, 12, 12, 12),
                120,
                    PersistenceContext.repositories().eCourseUsers().findByEmail("mariaferreira@gmail.com").get());

        List<MeetingInvitation> meetingInvitations_meeting1 = new ArrayList<>();
        Meeting meeting1_saved = meetingRepository.save(meeting1);

        // Create Meeting Invitations 1
        MeetingInvitation meetingInvitation1_meeting1 = new MeetingInvitation(
                PersistenceContext.repositories().eCourseUsers().findByEmail("pedroalves@gmail.com").get(),
                meeting1_saved);
        meetingInvitations_meeting1.add(meetingInvitation1_meeting1);

        MeetingInvitation meetingInvitation2_meeting1 = new MeetingInvitation(
                PersistenceContext.repositories().eCourseUsers().findByEmail("helenapereira@gmail.com").get(),
                meeting1_saved);
        meetingInvitations_meeting1.add(meetingInvitation2_meeting1);

        for (MeetingInvitation meetingInvitation : meetingInvitations_meeting1) {
            meetingInvitationRepository.save(meetingInvitation);
        }

        // Create Meeting 2
        Meeting meeting2 = new Meeting(LocalDateTime.of(2023, 10, 25, 23, 12),
                15,
                    PersistenceContext.repositories().eCourseUsers().findByEmail("anasantos@gmail.com").get());

        List<MeetingInvitation> meetingInvitations_meeting2 = new ArrayList<>();
        Meeting meeting2_saved = meetingRepository.save(meeting2);

        // Create Meeting Invitations 2
        MeetingInvitation meetingInvitation1_meeting2 = new MeetingInvitation(
                PersistenceContext.repositories().eCourseUsers().findByEmail("elizabethjohnson@gmail.com").get(),
                meeting2_saved);
        meetingInvitations_meeting2.add(meetingInvitation1_meeting2);

        MeetingInvitation meetingInvitation2_meeting2 = new MeetingInvitation(
                PersistenceContext.repositories().eCourseUsers().findByEmail("samanthasmith@gmail.com").get(),
                meeting2_saved);
        meetingInvitations_meeting2.add(meetingInvitation2_meeting2);

        MeetingInvitation meetingInvitation3_meeting2 = new MeetingInvitation(
                PersistenceContext.repositories().eCourseUsers().findByEmail("victorialee@gmail.com").get(),
                meeting2_saved);
        meetingInvitations_meeting2.add(meetingInvitation3_meeting2);

        MeetingInvitation meetingInvitation4_meeting2 = new MeetingInvitation(
                PersistenceContext.repositories().eCourseUsers().findByEmail("davidwilliams@gmail.com").get(),
                meeting2_saved);
        meetingInvitations_meeting2.add(meetingInvitation4_meeting2);

        for (MeetingInvitation meetingInvitation : meetingInvitations_meeting2) {
            meetingInvitationRepository.save(meetingInvitation);
        }

        // Create Meeting 3
        Meeting meeting3 = new Meeting(LocalDateTime.of(2023, 8, 1, 8, 0),
                150,
                    PersistenceContext.repositories().eCourseUsers().findByEmail("peterdavis@gmail.com").get());

        List<MeetingInvitation> meetingInvitations_meeting3 = new ArrayList<>();
        Meeting meeting3_saved = meetingRepository.save(meeting3);

        // Create Meeting Invitations 3
        MeetingInvitation meetingInvitation1_meeting3 = new MeetingInvitation(
                PersistenceContext.repositories().eCourseUsers().findByEmail("josealmeida@gmail.com").get(),
                meeting3_saved);
        meetingInvitations_meeting3.add(meetingInvitation1_meeting3);

        MeetingInvitation meetingInvitation2_meeting3 = new MeetingInvitation(
                PersistenceContext.repositories().eCourseUsers().findByEmail("mariagarcia@gmail.com").get(),
                meeting3_saved);
        meetingInvitations_meeting3.add(meetingInvitation2_meeting3);

        MeetingInvitation meetingInvitation3_meeting3 = new MeetingInvitation(
                PersistenceContext.repositories().eCourseUsers().findByEmail("isabelsilva@gmail.com").get(),
                meeting3_saved);
        meetingInvitations_meeting3.add(meetingInvitation3_meeting3);

        MeetingInvitation meetingInvitation4_meeting3 = new MeetingInvitation(
                PersistenceContext.repositories().eCourseUsers().findByEmail("paulsmith@gmail.com").get(),
                meeting3_saved);
        meetingInvitations_meeting3.add(meetingInvitation4_meeting3);

        MeetingInvitation meetingInvitation5_meeting3 = new MeetingInvitation(
                PersistenceContext.repositories().eCourseUsers().findByEmail("filipemoreira@gmail.com").get(),
                meeting3_saved);
        meetingInvitations_meeting3.add(meetingInvitation5_meeting3);

        MeetingInvitation meetingInvitation6_meeting3 = new MeetingInvitation(
                PersistenceContext.repositories().eCourseUsers().findByEmail("luissantos@gmail.com").get(),
                meeting3_saved);
        meetingInvitations_meeting3.add(meetingInvitation6_meeting3);

        for (MeetingInvitation meetingInvitation : meetingInvitations_meeting3) {
            meetingInvitationRepository.save(meetingInvitation);
        }

        return false;
    }
}
