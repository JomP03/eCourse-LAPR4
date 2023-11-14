package ui.exams.gui;

import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.*;
import java.util.*;

public class ExamGUI {
    private final String title;
    private final List<SectionGUI> sections;
    private final Map<QuestionGUI, Object> userAnswers = new HashMap<>();

    public ExamGUI(String title) {
        this.title = title;
        this.sections = new ArrayList<>();
    }

    public void addSection(SectionGUI section) {
        sections.add(section);
    }


    public VBox createExamUI() {
        VBox examBox = new VBox();
        examBox.setMinWidth(580);

        // Create the exam title label
        HBox titleBox = new HBox();
        titleBox.setAlignment(Pos.CENTER);
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("exam-title");
        titleBox.getChildren().add(titleLabel);
        examBox.getChildren().add(titleBox);

        // Create UI for each section in the exam
        for (SectionGUI section : sections) {
            VBox sectionBox = section.createSectionUI();
            examBox.getChildren().add(sectionBox);
        }

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setAlignment(Pos.CENTER);

        // Create the submit button
        Button submitButton = new Button("Submit");
        submitButton.getStyleClass().add("submit-button");

        // Attach a listener to the submit button
        submitButton.setOnAction(event -> {
            // Handle the button click event here
            handleSubmission();
        });

        // Add the submit button to the exam UI
        hbox.getChildren().add(submitButton);
        examBox.getChildren().add(hbox);

        return examBox;
    }

    private void handleSubmission() {

        for (SectionGUI section : sections) {
            for (QuestionGUI question : section.questions()) {
                Object answer = question.getUserData();
                userAnswers.put(question, answer);
            }
        }
        try {
            generateTxtFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateTxtFile() throws IOException {
        String filepath = "examfiles/takenExam.txt";
        FileWriter writer = new FileWriter(filepath);
        for (SectionGUI section : sections) {
            for (QuestionGUI question : section.questions()) {
                writer.write(question.getQuestionText() + "\n");
            }
        }
        writer.close();
        System.out.println("Exam file generated successfully!");
        Platform.exit();
    }

}

