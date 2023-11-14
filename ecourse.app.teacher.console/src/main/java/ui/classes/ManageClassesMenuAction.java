package ui.classes;

import eapli.framework.actions.*;

public class ManageClassesMenuAction implements Action {
    @Override
    public boolean execute() {
        return new ManageClassesMenuUI().show();
    }
}
