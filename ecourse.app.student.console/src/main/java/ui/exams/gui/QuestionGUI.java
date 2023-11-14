package ui.exams.gui;

import javafx.scene.layout.VBox;

public abstract class QuestionGUI {
    private final String questionText;
    private final String position;

    public QuestionGUI(String questionText, String position) {
        this.questionText = questionText;
        this.position = position;
    }

    public String questionText() {
        return questionText;
    }

    public String questionTextForUI() {
        String[] positionParts = position.split("-");
        return positionParts[1] + "." + questionText;
    }

    protected String position() { return position; }
    public abstract VBox createQuestionUI();

    public abstract Object getUserData();

    public abstract String getQuestionText();
}
