package questionmanagment.application;

import questionmanagment.domain.*;
import java.io.*;
import java.util.*;

public class QuestionTxtGenerator implements IQuestionFileGenerator {
    @Override
    public File generateMatchingQuestion(String question, Float quotation, Float penalty, List<String> leftOptions, List<String> rightOptions) {
        File file = createDirectory("questions/matching_question.txt");
        try {
            FileWriter writer = new FileWriter(file);
            writer.write("MATCHING:" + question + "\n");

            writer.write("MATCHES: ");
            for (int i = 0; i < leftOptions.size(); i++) {
                writer.write(leftOptions.get(i) + "-" + rightOptions.get(i));
                if (i != leftOptions.size() - 1)
                    writer.write(" , ");
            }
            writer.write("\n");
            writer.write("GRADE:" + quotation + "\n");

            writer.close();
        } catch (IOException e) {
            throw new IllegalArgumentException("An error occurred while generating a multiple choice question file.");
        }
        return file;
    }

    @Override
    public File generateMultipleChoiceQuestion(String question, Float quotation, Float penalty, List<String> options, String correctOption) {
        File file = createDirectory("questions/multiple_choice_question.txt");
        try {
            FileWriter writer = new FileWriter(file);
            writer.write("MULTIPLE_CHOICE:" + question + "\n");
            writer.write("CORANSW:" + correctOption + "\n");
            writer.write("GRADE:" + quotation + "\n");
            for (String option : options) {
                writer.write("ANSW:" + option + "\n");
            }
            writer.close();

        } catch (IOException e) {
            throw new IllegalArgumentException("An error occurred while generating a Matching question file.");
        }
        return file;
    }

    @Override
    public File generateShortAnswerQuestion(String question, Float quotation, Float penalty, String correctAnswer) {
        File file = createDirectory("questions/short_answer_question.txt");
        try {
            FileWriter writer = new FileWriter(file);
            writer.write("SHORT_ANSWER:" + question + "\n");
            writer.write("ANSW:" + correctAnswer + "\n");
            writer.write("GRADE:" + quotation + "\n");
            writer.close();

        } catch (IOException e) {
            throw new IllegalArgumentException("An error occurred while generating a Short Answer question file.");
        }
        return file;
    }

    @Override
    public File generateNumericalQuestion(String question, Float quotation, Float penalty, String correctAnswer) {
        File file = createDirectory("questions/numerical_question.txt");
        try {
            FileWriter writer = new FileWriter(file);
            writer.write("NUMERICAL:" + question + "\n");
            writer.write("ANSW:" + correctAnswer + "\n");
            writer.write("GRADE:" + quotation + "\n");
            writer.close();

        } catch (IOException e) {
            throw new IllegalArgumentException("An error occurred while generating a Numerical question file.");
        }

        return file;
    }

    @Override
    public File generateMissingWordQuestion(String question, Float quotation, Float penalty, List<MissingWordOption> options) {
        File file = createDirectory("questions/missing_word_question.txt");
        try {
            FileWriter writer = new FileWriter(file);
            writer.write("MISSING_WORD:" + question + "\n");
            writer.write("GRADE:" + quotation + "\n");
            for (MissingWordOption missingOption : options) {
                writer.write("CORANSW:" + missingOption.answer() + "\n");
                for (String option : missingOption.optionAnswers()) {
                    writer.write("ANSW:" + option + "\n");
                }
            }
            writer.close();

        } catch (IOException e) {
            throw new IllegalArgumentException("An error occurred while generating a Missing Word question file.");
        }

        return file;
    }

    @Override
    public File generateBooleanQuestion(String question, Float quotation, Float penalty, boolean correctAnswer) {
        File file = createDirectory("questions/boolean_question.txt");
        try {
            FileWriter writer = new FileWriter(file);
            writer.write("BOOLEAN:" + question + "\n");
            writer.write("ANSW:" + correctAnswer + "\n");
            writer.write("GRADE:" + quotation + "\n");
            writer.close();

        } catch (IOException e) {
            throw new IllegalArgumentException("An error occurred while generating a Boolean question file.");
        }

        return file;
    }

    private File createDirectory(String path) {
        File file = new File(path);
        File directory = new File(file.getParent());
        if (!directory.exists()) {
            if (!directory.mkdir()) {
                throw new IllegalArgumentException("An error occurred while creating the directory.");
            }
        }
        return file;
    }
}
