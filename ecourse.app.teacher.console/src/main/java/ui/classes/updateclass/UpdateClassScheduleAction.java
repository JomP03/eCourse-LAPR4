package ui.classes.updateclass;

import eapli.framework.actions.Action;

public class UpdateClassScheduleAction implements Action {

    @Override
    public boolean execute() {
        return new UpdateClassScheduleUI().show();
    }
}
