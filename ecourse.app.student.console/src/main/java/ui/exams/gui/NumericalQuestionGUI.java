package ui.exams.gui;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class NumericalQuestionGUI extends QuestionGUI {
    private final TextField answerField;

    public NumericalQuestionGUI(String questionText , String postion) {
        super(questionText, postion);
        this.answerField = new TextField();
    }

    @Override
    public VBox createQuestionUI() {
        VBox questionBox = new VBox();

        // Create a label to display the question text
        Label questionLabel = new Label(super.questionTextForUI());
        questionLabel.getStyleClass().add("question-text");
        questionBox.getChildren().add(questionLabel);

        // Add the answer field to the question box
        questionBox.getChildren().add(answerField);

        // Set the NumericalQuestion instance as the userData of the questionBox
        questionBox.setUserData(this);

        return questionBox;
    }

    @Override
    public Object getUserData() {
        return answerField.getText();
    }

    @Override
    public String getQuestionText() {
        return "NUMERICAL:" + super.questionText() + "\n" +
                "ANSW:" + answerField.getText() + "\n" +
                "POSITION:" + super.position() + "\n";
    }
}

