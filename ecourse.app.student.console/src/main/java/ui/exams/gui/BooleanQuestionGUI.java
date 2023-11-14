package ui.exams.gui;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class BooleanQuestionGUI extends QuestionGUI {
    private final ToggleGroup toggleGroup;
    private String answer;

    public BooleanQuestionGUI(String questionText, String position) {
        super(questionText, position);
        this.toggleGroup = new ToggleGroup();
    }

    @Override
    public VBox createQuestionUI() {
        VBox questionBox = new VBox();

        // Create a label to display the question text
        Label questionLabel = new Label(super.questionTextForUI());
        questionLabel.getStyleClass().add("question-text");
        questionBox.getChildren().add(questionLabel);

        // Create radio buttons for true and false options
        RadioButton trueButton = new RadioButton("True");
        trueButton.setToggleGroup(toggleGroup);
        RadioButton falseButton = new RadioButton("False");
        falseButton.setToggleGroup(toggleGroup);

        questionBox.getChildren().addAll(trueButton, falseButton);

        // Set the BooleanQuestion instance as the userData of the questionBox
        questionBox.setUserData(this);

        return questionBox;
    }

    @Override
    public Object getUserData() {
        RadioButton selectedOption = (RadioButton) toggleGroup.getSelectedToggle();
        answer = selectedOption != null ? selectedOption.getText() : null;
        if (answer != null) {
            return answer.toLowerCase();
        }
        return "";
    }

    @Override
    public String getQuestionText() {
        return "BOOLEAN:" + super.questionText() + "\n" +
                "ANSW:" + answer + "\n" +
                "POSITION:" + super.position() + "\n";
    }
}

