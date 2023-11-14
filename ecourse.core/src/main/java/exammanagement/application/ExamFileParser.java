package exammanagement.application;

import exammanagement.domain.*;
import java.io.*;

public interface ExamFileParser {


    /**
     * Creates a File from an exam object and stores it in the given filepath.
     * @param exam the exam to be parsed
     */
    File parse(Exam exam, String filepath);
}
