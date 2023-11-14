package ui.meetinginvitation.manage;

import eapli.framework.actions.Action;

public class ManageMeetingInvitationAction implements Action {
    @Override
    public boolean execute() {
        return new ManageMeetingInvitationUI().show();
    }
}
