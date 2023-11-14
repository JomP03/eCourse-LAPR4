package ui.meeting;

import ui.components.AbstractUI;
import ui.meeting.cancel.CancelMeetingAction;
import ui.meetinginvitation.manage.ManageMeetingInvitationAction;
import ui.menu.*;

public class ManageMeetingsMenuUI extends AbstractUI {


    @Override
    protected boolean doShow() {
        final Menu menu = buildTeacherMenu();
        final var renderer = new MenuRenderer(menu);
        return renderer.render(MenuRenderer.PullOutActions.EXIT.message());
    }

    private Menu buildTeacherMenu() {
        final Menu meetingMenu = new Menu();

        meetingMenu.addItem(1, "Schedule Meeting", new ScheduleMeetingAction());
        meetingMenu.addItem(2, "Manage Meeting Invitations", new ManageMeetingInvitationAction());
        meetingMenu.addItem(3, "View A List Of Participants And Their Status", new ListMeetingParticipantsAction());
        meetingMenu.addItem(4, "Cancel a Meeting", new CancelMeetingAction());

        return meetingMenu;
    }

    @Override
    public String headline() {
        return "Manage Meeting Menu";
    }
}
