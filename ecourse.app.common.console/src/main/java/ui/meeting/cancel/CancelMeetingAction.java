package ui.meeting.cancel;

import eapli.framework.actions.Action;

public class CancelMeetingAction implements Action {
    @Override
    public boolean execute() {
        return new CancelMeetingUI().show();
    }
}

