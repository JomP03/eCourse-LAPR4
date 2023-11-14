package ui.classes.scheduleextraclass;

import eapli.framework.actions.Action;

public class ExtraClassScheduleAction implements Action {

    @Override
    public boolean execute() {
        return new ExtraClassScheduleUI().show();
    }
}
