package ui.meeting;

import eapli.framework.actions.*;

public class ManageMeetingsMenuAction implements Action {
    @Override
    public boolean execute() {
        return new ManageMeetingsMenuUI().show();
    }
}
