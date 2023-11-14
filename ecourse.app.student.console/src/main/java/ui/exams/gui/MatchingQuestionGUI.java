package ui.exams.gui;

import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.*;

public class MatchingQuestionGUI extends QuestionGUI {
    private final List<String> leftOptions;
    private final List<String> rightOptions;
    private final Map<String, String> userAnswers;

    public MatchingQuestionGUI(String questionText, String position,
                               List<String> leftOptions, List<String> rightOptions) {
        super(questionText, position);
        this.leftOptions = leftOptions;
        this.rightOptions = rightOptions;
        this.userAnswers = new HashMap<>();
    }

    @Override
    public VBox createQuestionUI() {
        VBox questionBox = new VBox();
        questionBox.setSpacing(10);
        questionBox.setAlignment(Pos.CENTER_LEFT);

        // Create and add the question label
        Label questionLabel = new Label(super.questionTextForUI());
        questionLabel.setWrapText(true);
        questionLabel.getStyleClass().add("question-text");
        questionBox.getChildren().add(questionLabel);

        for (String leftOption : leftOptions) {
            Label leftLabel = new Label(leftOption);
            leftLabel.getStyleClass().add("matching-missing-labels");
            ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList(rightOptions));

            // Add the label and combobox to an HBox
            HBox optionBox = new HBox(leftLabel, comboBox);
            optionBox.setSpacing(10);

            // Add the optionBox to the questionBox
            questionBox.getChildren().add(optionBox);

            // Update the userAnswers map when the combobox selection changes
            comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                userAnswers.put(leftOption, newValue);
            });

            // Add default option to the combobox
            comboBox.getItems().add(0, "-");
            comboBox.getSelectionModel().selectFirst();
        }

        // Set the MatchingQuestion instance as the user data of the question box
        questionBox.setUserData(this);

        return questionBox;
    }


    @Override
    public Object getUserData() {
        return userAnswers;
    }

    @Override
    public String getQuestionText() {
        StringBuilder s = new StringBuilder("MATCHING:" + super.questionText() + "\n" +
                "MATCHES:");

        int i = 0;
        for (Map.Entry<String, String> entry : userAnswers.entrySet()) {
            if (i == 0) {
                s.append(entry.getKey()).append("-").append(entry.getValue());
                i++;
                continue;
            }
            s.append(",").append(entry.getKey()).append("-").append(entry.getValue());
        }

        s.append("\nPOSITION:").append(super.position());
        return s.toString();
    }


}
