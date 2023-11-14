package takenexammanagement.domain.builder;

import ecourseusermanagement.domain.ECourseUser;
import exammanagement.domain.Exam;
import takenexammanagement.domain.AnsweredQuestion;
import takenexammanagement.domain.TakenExam;

import java.util.ArrayList;
import java.util.List;

public class TakenExamBuilder {

    private ECourseUser student;

    private Exam exam;

    private List<AnsweredQuestion> answeredQuestions;

    /**
     * Initializes the builder
     */
    public TakenExamBuilder() {
        answeredQuestions = new ArrayList<>();
    }

    /**
     * With the student
     * @param student the student
     */
    public TakenExamBuilder withStudent(ECourseUser student) {
        this.student = student;
        return this;
    }

    /**
     * With the exam
     * @param exam the exam
     */
    public TakenExamBuilder withExam(Exam exam) {
        this.exam = exam;
        return this;
    }

    /**
     * Adds an answered question
     * @param answeredQuestion the answered question
     */
    public TakenExamBuilder addAnsweredQuestion(AnsweredQuestion answeredQuestion) {
        answeredQuestions.add(answeredQuestion);
        return this;
    }

    /**
     * Builds the TakenExam
     * @return the TakenExam
     */
    public TakenExam build() {
        // Organize the questions (answeredQuestions is not ordered)
        return new TakenExam(answeredQuestions, student, exam);
    }


}
