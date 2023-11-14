package ui.question;

import ui.components.AbstractUI;
import ui.menu.Menu;
import ui.menu.MenuRenderer;
import ui.question.updatequestions.*;

public class UpdateQuestionMenu extends AbstractUI {

    @Override
    protected boolean doShow() {
        final Menu menu = buildTeacherMenu();
        final var renderer = new MenuRenderer(menu);
        return renderer.render(MenuRenderer.PullOutActions.EXIT.message());
    }

    private Menu buildTeacherMenu() {
        final Menu addQuestionMenu = new Menu();

        addQuestionMenu.addItem(1, "Matching Question", new UpdateMatchingQuestionAction());
        addQuestionMenu.addItem(2, "Multiple Choice Question", new UpdateMultipleChoiceQuestionAction());
        addQuestionMenu.addItem(3, "Short Answer Question", new UpdateShortAnswerQuestionAction());
        addQuestionMenu.addItem(4, "Numerical Question", new UpdateNumericalQuestionAction());
        addQuestionMenu.addItem(5, "Missing Word Question", new UpdateMissingWordQuestionAction());
        addQuestionMenu.addItem(6, "Boolean Question", new UpdateBooleanQuestionAction());


        return addQuestionMenu;
    }

    @Override
    public String headline() {
        return "Add New Question";
    }
}
