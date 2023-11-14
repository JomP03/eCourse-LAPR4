package ui.exams.gui;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MissingWordQuestionGUI extends QuestionGUI {
    private final List<MissingWordOptionGUI> missingWordOptionGUIS;
    private final List<ComboBox<String>> comboBoxes;
    private Label updatedQuestionLabel;
    private final Map<String, String> userAnswers = new HashMap<>();

    public MissingWordQuestionGUI(String questionText, String position,
                                  List<MissingWordOptionGUI> missingWordOptionGUIS) {
        super(questionText, position);
        this.missingWordOptionGUIS = missingWordOptionGUIS;
        this.comboBoxes = new ArrayList<>();
    }

    @Override
    public VBox createQuestionUI() {
        VBox questionBox = new VBox();

        // Create the updated question label
        updatedQuestionLabel = new Label();
        updatedQuestionLabel.getStyleClass().add("question-text");
        updateQuestionLabel();
        questionBox.getChildren().add(updatedQuestionLabel);

        // Create a combo box for each missing word option
        for (MissingWordOptionGUI option : missingWordOptionGUIS) {
            Label leftOptionLabel = new Label(option.getMissingWord());
            leftOptionLabel.getStyleClass().add("matching-missing-labels");
            ComboBox<String> comboBox = new ComboBox<>();

            // Add a listener to update the question text when an option is selected
            comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                    updateQuestionLabel());

            // Add the default option to the combo box
            comboBox.getItems().add("-"); // Add a default option
            comboBox.getItems().addAll(option.getOptions());

            HBox optionBox = new HBox(leftOptionLabel, comboBox);
            optionBox.setSpacing(10);
            questionBox.getChildren().add(optionBox);
            comboBoxes.add(comboBox);
        }

        // Set the user data of the question box to the MissingWordQuestion instance itself
        questionBox.setUserData(this);

        return questionBox;
    }

    private void updateQuestionLabel() {
        String updatedText = super.questionTextForUI();
        for (ComboBox<String> comboBox : comboBoxes) {
            String selectedOption = comboBox.getValue();
            if (selectedOption != null && !selectedOption.isEmpty()) {
                updatedText = updatedText.replaceFirst("\\[\\[n]]", selectedOption);
            }
        }
        updatedQuestionLabel.setText(updatedText);
    }

    @Override
    public Object getUserData() {
        for (ComboBox<String> comboBox : comboBoxes) {
            String leftOption = ((Label) ((HBox) comboBox.getParent()).getChildren().get(0)).getText();
            String selectedOption = comboBox.getValue();
            userAnswers.put(leftOption, selectedOption);
        }
        return userAnswers;
    }

    @Override
    public String getQuestionText() {
        StringBuilder s = new StringBuilder();
        s.append("MISSINGWORD:").append(super.questionText()).append("\n");

        for (ComboBox<String> comboBox : comboBoxes) {
            String selectedOption = comboBox.getValue();
            s.append("ANSW:").append(selectedOption).append("\n");
        }

        s.append("POSITION:").append(super.position()).append("\n");
        return s.toString();
    }
}
