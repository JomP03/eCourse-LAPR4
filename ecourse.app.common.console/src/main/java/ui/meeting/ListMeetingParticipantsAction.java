package ui.meeting;

import eapli.framework.actions.Action;

public class ListMeetingParticipantsAction implements Action {

        @Override
        public boolean execute() {
            return new ListMeetingParticipantsUI().show();
        }
}
