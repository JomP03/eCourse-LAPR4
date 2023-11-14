package ui.question;

import ui.components.AbstractUI;
import ui.menu.*;

public class ManageQuestionsMenuUI extends AbstractUI {


    @Override
    protected boolean doShow() {
        final Menu menu = buildTeacherMenu();
        final var renderer = new MenuRenderer(menu);
        return renderer.render(MenuRenderer.PullOutActions.EXIT.message());
    }

    private Menu buildTeacherMenu() {
        final Menu questionMenu = new Menu();

        questionMenu.addItem(1, "Add Questions", new AddQuestionAction());
        questionMenu.addItem(2, "Update Questions", new UpdateQuestionAction());

        return questionMenu;
    }

    @Override
    public String headline() {
        return "Manage Questions Menu";
    }
}
