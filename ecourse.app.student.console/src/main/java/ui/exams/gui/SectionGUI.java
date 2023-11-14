package ui.exams.gui;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class SectionGUI {
    private final String title;
    private final List<QuestionGUI> questions;

    public SectionGUI(String title) {
        this.title = title;
        this.questions = new ArrayList<>();
    }

    public void addQuestion(QuestionGUI question) {
        questions.add(question);
    }

    public VBox createSectionUI() {
        VBox sectionBox = new VBox();

        // Create the section title label
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("section-title");
        sectionBox.getChildren().add(titleLabel);

        // Create UI for each question in the section
        for (QuestionGUI question : questions) {
            VBox questionBox = question.createQuestionUI();
            sectionBox.getChildren().add(questionBox);
        }

        return sectionBox;
    }

    public List<QuestionGUI> questions() {
        return questions;
    }
}

