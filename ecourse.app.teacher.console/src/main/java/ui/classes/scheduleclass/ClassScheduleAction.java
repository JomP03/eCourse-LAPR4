package ui.classes.scheduleclass;

import eapli.framework.actions.Action;

public class ClassScheduleAction implements Action {

    @Override
    public boolean execute() {
        return new ClassScheduleUI().show();
    }
}
