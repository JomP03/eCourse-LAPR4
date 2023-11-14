package exammanagement.application.service;

import exammanagement.domain.Exam;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExamFileCreator {

    /**
     * Creates the exam file.
     *
     * @param exam the exam to be parsed to a file
     * @param path the path where the exam file will be created
     */
    public void createExamFile(Exam exam, String path, String fileName) {
        String content = exam.stringToFile();

        try {
            File file = new File(path, fileName + ".txt");
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating the exam file");
        }
    }

}
