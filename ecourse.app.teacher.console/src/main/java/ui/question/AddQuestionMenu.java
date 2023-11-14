package ui.question;

import ui.components.AbstractUI;
import ui.menu.*;
import ui.question.addquestions.*;

public class AddQuestionMenu extends AbstractUI {

    @Override
    protected boolean doShow() {
        final Menu menu = buildTeacherMenu();
        final var renderer = new MenuRenderer(menu);
        return renderer.render(MenuRenderer.PullOutActions.EXIT.message());
    }

    private Menu buildTeacherMenu() {
        final Menu addQuestionMenu = new Menu();

        addQuestionMenu.addItem(1, "Matching Question", new AddMatchingQuestionAction());
        addQuestionMenu.addItem(2, "Multiple Choice Question", new AddMultipleChoiceQuestionAction());
        addQuestionMenu.addItem(3, "Short Answer Question", new AddShortAnswerQuestionAction());
        addQuestionMenu.addItem(4, "Numerical Question", new AddNumericalQuestionAction());
        addQuestionMenu.addItem(5, "Missing Word Question", new AddMissingWordQuestionAction());
        addQuestionMenu.addItem(6, "Boolean Question", new AddBooleanQuestionAction());


        return addQuestionMenu;
    }

    @Override
    public String headline() {
        return "Add New Question";
    }
}
