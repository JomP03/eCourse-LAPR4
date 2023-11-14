package ui.exams.gui;

import boardcommunication.http.helpers.*;
import com.google.gson.*;
import exammanagement.application.deserializers.*;
import exammanagement.domain.*;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import questionmanagment.application.deserializers.*;
import questionmanagment.domain.*;
import java.io.*;
import java.time.*;
import java.util.*;

public class MainGUI extends Application {
    private Exam exam;

    public void start(Stage primaryStage) {
        readExam();

        // Create a layout pane
        BorderPane root = new BorderPane();

        // Create a label
        Label label = new Label(exam.title());
        label.getStyleClass().add("exam-title");

        HBox labelBox = new HBox(label);
        labelBox.setPadding(new Insets(15, 12, 15, 12));
        labelBox.setAlignment(Pos.CENTER);

        // Create a button
        Button button = new Button("Take Exam");
        button.getStyleClass().add("submit-button");

        HBox buttonBox = new HBox(button);
        buttonBox.setPadding(new Insets(15, 12, 15, 12));
        buttonBox.setAlignment(Pos.CENTER);

        // Set the bottom of the layout pane to the horizontal box
        root.setTop(labelBox);
        root.setBottom(buttonBox);

        // Set the icon image
        Image iconImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/exam.png")));

        // Create a scene with the layout pane
        Scene scene = new Scene(root, 600, 700);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/index.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Take Exam");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(iconImage);
        primaryStage.show();

        button.setOnAction(event -> {
            // Set the second scene on the primary stage
            primaryStage.setScene(setScene());
        });
    }

    public Scene setScene() {

        // Create an exam
        ExamGUI examGUI = new ExamGUI(exam.title());

        int sectionIndex = 1;
        // Create UI for each section in the exam
        for (ExamSection section : exam.sections()) {
            int questionIndex = 1;
            SectionGUI sectionGUI = new SectionGUI(section.description());

            // Create UI for each question in the section
            for (Question question : section.questions()) {
                String position = determinePosition(sectionIndex, questionIndex);
                QuestionGUI questionGUI = questionGUIFactory(question, position);
                sectionGUI.addQuestion(questionGUI);
                questionIndex++;
            }
            sectionIndex++;
            examGUI.addSection(sectionGUI);
        }

        // Create the exam UI
        ScrollPane examUI = new ScrollPane();
        VBox content = examGUI.createExamUI();
        examUI.setContent(content);

        Scene scene = new Scene(examUI, 600, 700);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/index.css")).toExternalForm());
        return scene;
    }

    private String determinePosition(int sectionIndex, int questionIndex) {
        return "SEC" + sectionIndex + "-" + questionIndex;
    }


    /**
     * Read the exam from the JSON file
     */
    private void readExam() {
        try {
            // Read the exam from the JSON file
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .registerTypeAdapter(Exam.class, new FormativeExamDeserializer())
                    .registerTypeAdapter(Question.class, new QuestionDeserializer())
                    .setPrettyPrinting()
                    .create();
            BufferedReader bufferedReader = new BufferedReader(new FileReader("examfiles/choosenExam.json"));
            exam = gson.fromJson(bufferedReader, Exam.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * Factory method to create a QuestionGUI object based on the type of question
     *
     * @param question The question to create a QuestionGUI object for
     * @return A QuestionGUI object
     */
    private QuestionGUI questionGUIFactory(Question question, String position) {
        switch (question.type()) {
            case MULTIPLE_CHOICE:
                MultipleChoiceQuestion multipleChoiceQuestion = (MultipleChoiceQuestion) question;
                return new MultipleChoiceGUI(
                        multipleChoiceQuestion.question(), position, multipleChoiceQuestion.options());

            case SHORT_ANSWER:
                ShortAnswerQuestion shortAnswerQuestion = (ShortAnswerQuestion) question;
                return new ShortAnswerGUI(shortAnswerQuestion.question(), position);

            case BOOLEAN:
                BooleanQuestion booleanQuestion = (BooleanQuestion) question;
                return new BooleanQuestionGUI(booleanQuestion.question(), position);

            case NUMERICAL:
                NumericalQuestion numericalQuestion = (NumericalQuestion) question;
                return new NumericalQuestionGUI(numericalQuestion.question(), position);

            case MATCHING:
                MatchingQuestion matchingQuestion = (MatchingQuestion) question;
                return new MatchingQuestionGUI(matchingQuestion.question(), position,
                        matchingQuestion.leftOptions(), matchingQuestion.rightOptions());

            case MISSING_WORD:
                MissingWordQuestion missingWordQuestion = (MissingWordQuestion) question;
                int index = 1;
                List<MissingWordOptionGUI> missingWordOptionGUIList = new ArrayList<>();
                for (MissingWordOption option : missingWordQuestion.missingOptions()) {
                    missingWordOptionGUIList.add(new MissingWordOptionGUI(String.valueOf(index), option.optionAnswers()));
                    index++;
                }
                return new MissingWordQuestionGUI(missingWordQuestion.question(), position, missingWordOptionGUIList);

            default:
                throw new IllegalArgumentException("Invalid question type");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}


