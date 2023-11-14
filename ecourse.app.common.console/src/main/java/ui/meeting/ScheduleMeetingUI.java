package ui.meeting;

import classmanagement.application.UserClassesProviderFactory;
import coursemanagement.application.UserCoursesProviderFactory;
import ecourseusermanagement.application.UserSessionService;
import ecourseusermanagement.application.ListUsersController;
import ecourseusermanagement.domain.ECourseUser;
import meetinginvitationmanagement.application.MeetingInvitationProvider;
import meetinginvitationmanagement.application.MeetingInvitationSender;
import meetingmanagement.application.service.MeetingAvailabilityService;
import meetingmanagement.application.service.MeetingManager;
import meetingmanagement.application.ScheduleMeetingController;
import meetingmanagement.application.service.UserAvailabilityService;
import persistence.PersistenceContext;
import ui.components.AbstractUI;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import ui.components.Console;
import ui.components.ListSelector;
import ui.components.Sleeper;

public class ScheduleMeetingUI extends AbstractUI {

    private final ScheduleMeetingController scheduleMeetingController =
            new ScheduleMeetingController(new UserSessionService(PersistenceContext.repositories().eCourseUsers()),
                    new ListUsersController(PersistenceContext.repositories().eCourseUsers()),
                    new MeetingManager(
                            new UserAvailabilityService(
                                    new MeetingAvailabilityService(
                                            PersistenceContext.repositories().meetings(),
                                            PersistenceContext.repositories().meetingInvitations()),
                                    new UserClassesProviderFactory(),
                                    new UserCoursesProviderFactory()),
                            PersistenceContext.repositories().meetings(), PersistenceContext.repositories().meetingInvitations(),
                            new MeetingInvitationSender(PersistenceContext.repositories().meetingInvitations()),
                            new MeetingInvitationProvider(PersistenceContext.repositories().meetingInvitations()
                            )));


    @Override
    protected boolean doShow() {
        System.out.println("Please provide the meeting details\n");

        LocalDate dateToSchedule = Console.readDate("Meeting Date (dd/mm/yyyy): ", "dd/MM/yyyy");
        System.out.println();

        LocalTime meetingStartTime = Console.readTime("Meeting Start Time (hh:mm): ");
        System.out.println();

        LocalDateTime meetingStartDateTime;

        try {
            meetingStartDateTime = LocalDateTime.of(dateToSchedule, meetingStartTime);
        } catch (Exception e) {
            errorMessage("Invalid meeting start time");
            Sleeper.sleep(1000);
            return false;
        }

        Integer meetingDuration = Console.readPositiveInteger("Meeting Duration (minutes): ");
        System.out.println();

        int wantsToSelectUserToInvite;

        List<ECourseUser> availableUsersToInvite = (List<ECourseUser>) scheduleMeetingController.listInvitableUsers();

        List<String> availableUsersToInviteEmail = new ArrayList<>();

        for (ECourseUser user : availableUsersToInvite) {
            availableUsersToInviteEmail.add(user.email());
        }

        List<ECourseUser> usersToInvite = new ArrayList<>();

        if (availableUsersToInvite.isEmpty()) {
            infoMessage("There are no more users available to invite to the meeting.");
            Sleeper.sleep(1000);
            return false;
        }

        do {

            try {
                // Select the user to invite
                ListSelector<String> userToInviteSelector = new ListSelector<>("Available Users:",
                        availableUsersToInviteEmail);

                if (!userToInviteSelector.showAndSelectWithExit()) return false;

                // Remove the selected user from the list of available users with only email
                // and add it to the list of users to invite
                availableUsersToInviteEmail.remove(userToInviteSelector.getSelectedElement());

                usersToInvite.add(availableUsersToInvite.get(userToInviteSelector.getSelectedOption() - 1));

                // Remove the selected user from the list of available users
                availableUsersToInvite.remove(userToInviteSelector.getSelectedOption() - 1);

                if (availableUsersToInviteEmail.isEmpty()) {
                    infoMessage("There are no more users available to invite to the meeting.");
                    Sleeper.sleep(1000);
                    break;
                }

            } catch (Exception e) {
                errorMessage(e.getMessage());
                Sleeper.sleep(1000);
            }

            // Ask if the manager wants to add more teachers
            System.out.printf("Do you want to invite more users to the meeting?%n%n1 - Keep Selecting%n0 - Schedule Meeting%n%n");
            wantsToSelectUserToInvite = Console.readOption(0, 1);

        } while (wantsToSelectUserToInvite == 1);

        try {

            if (usersToInvite.isEmpty()) {
                infoMessage("No users to invite to the meeting.");
                Sleeper.sleep(1000);
                return false;
            }

            System.out.println();

            System.out.print(scheduleMeetingController.scheduleMeeting(
                    meetingStartDateTime,
                    meetingDuration,
                    usersToInvite));

            System.out.println();
            successMessage("Meeting scheduled successfully!");
            Sleeper.sleep(1500);

        } catch (Exception e) {
            System.out.println();
            errorMessage(e.getMessage());
            Sleeper.sleep(1000);
        }

        return false;
    }

    @Override
    public String headline() {
        return "Schedule Meeting";
    }
}
