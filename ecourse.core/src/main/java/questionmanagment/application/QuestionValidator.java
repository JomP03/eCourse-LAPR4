package questionmanagment.application;

import eapli.framework.validations.*;
import exammanagement.application.service.*;
import exammanagement.repository.*;
import persistence.*;
import questionmanagment.domain.*;

import java.io.*;

public class QuestionValidator implements IQuestionValidator {

    private final ExamHandler examHandler;

    public QuestionValidator(ExamHandler examHandler) {
        Preconditions.noneNull(examHandler);
        this.examHandler = examHandler;
    }

    public Question validate(String path) {
        try {
            return examHandler.parseExamQuestion(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
