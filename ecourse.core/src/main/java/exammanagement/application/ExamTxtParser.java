package exammanagement.application;

import exammanagement.domain.*;
import questionmanagment.domain.*;
import java.io.*;

public class ExamTxtParser implements ExamFileParser {

    @Override
    public File parse(Exam exam, String filepath) {
        File file = new File(filepath);
        FileWriter writer;
        try {
            writer = new FileWriter(file);

            int sectionIndex = 1;
            for (ExamSection section : exam.sections()) {
                int questionIndex = 1;
                for (Question question : section.questions()) {
                    questionGenerationFactory(question, writer);

                    String position = "SEC" + sectionIndex + "-" + questionIndex;
                    writer.write("POSITION:" + position + "\n\n");

                    questionIndex++;
                }
                sectionIndex++;
            }


            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    private void questionGenerationFactory(Question question, FileWriter writer) throws IOException {
        switch (question.type()) {
            case MATCHING:
                MatchingQuestion matching = (MatchingQuestion) question;
                writer.write("MATCHING:" + matching.questionToTxt() + "\n");
                writer.write("MATCHES:\n");
                return;
            case MISSING_WORD:
                MissingWordQuestion missingWord = (MissingWordQuestion) question;
                writer.write("MISSINGWORD:" + missingWord.questionToTxt() + "\n");
                for (int i = 0; i < missingWord.missingOptions().size() ; i++) {
                    writer.write("ANSW:\n");
                }
                return;
            case MULTIPLE_CHOICE:
                MultipleChoiceQuestion multipleChoice = (MultipleChoiceQuestion) question;
                writer.write("MULTIPLECHOICE:" + multipleChoice.questionToTxt() + "\n");
                break;
            case NUMERICAL:
                writer.write("NUMERICAL:" + question.question() + "\n");
                break;
            case SHORT_ANSWER:
                writer.write("SHORTANSWER:" + question.question() + "\n");
                break;
            case BOOLEAN:
                writer.write("BOOLEAN:" + question.question() + "\n");
                break;
        }

        writer.write("ANSW:\n");
    }
}
