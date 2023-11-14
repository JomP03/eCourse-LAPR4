package ui.exams.gui;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import java.util.List;

public class MultipleChoiceGUI extends QuestionGUI {
    private final List<String> options;
    private final ToggleGroup toggleGroup;
    private String answer;

    public MultipleChoiceGUI(String questionText, String position, List<String> options) {
        super(questionText, position);
        this.options = options;
        this.toggleGroup = new ToggleGroup();
    }

    @Override
    public VBox createQuestionUI() {
        VBox questionBox = new VBox();

        // Create a label to display the question text
        Label questionLabel = new Label(super.questionTextForUI());
        questionLabel.getStyleClass().add("question-text");
        questionBox.getChildren().add(questionLabel);

        // Create a radio button for each option
        for (String option : options) {
            RadioButton radioButton = new RadioButton(option);
            radioButton.setToggleGroup(toggleGroup);
            questionBox.getChildren().add(radioButton);
        }

        // Set the MultipleChoice instance as the userData of the questionBox
        questionBox.setUserData(this);

        return questionBox;
    }

    @Override
    public Object getUserData() {
        RadioButton selectedOption = (RadioButton) toggleGroup.getSelectedToggle();
        answer =  selectedOption != null ? selectedOption.getText() : null;
        return answer;
    }

    @Override
    public String getQuestionText() {
        return "MULTIPLECHOICE:" + super.questionText() + "\n" +
                "ANSW:" + answer + "\n" +
                "POSITION:" + super.position() + "\n";
    }
}
