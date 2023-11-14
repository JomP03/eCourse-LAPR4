package takenexammanagement.domain.builder;

import coursemanagement.domain.Course;
import coursemanagement.domain.CourseDataSource;
import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.domain.UserDataSource;
import exammanagement.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questionmanagment.domain.BooleanQuestion;
import questionmanagment.domain.Question;
import takenexammanagement.domain.AnsweredQuestion;
import takenexammanagement.domain.TakenExam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TakenExamBuilderTest {

    Exam validExam;

    ECourseUser student;

    @BeforeEach
    public void setUp(){
        String description = "Description";
        List<Question> questions = new ArrayList<>();
        questions.add(new BooleanQuestion("Question", true, 0.5f, 1f));

        ExamSection examSection = new ExamSection(description, questions);
        List<ExamSection> sections = new ArrayList<>();
        sections.add(examSection);

        ECourseUser teacher = UserDataSource.getTestTeacher1();
        Course course = CourseDataSource.getTestCourse1();

        // create Open Period
        LocalDateTime openDate = LocalDateTime.now().plusDays(1);
        LocalDateTime closeDate = LocalDateTime.now().plusDays(2);

        // create ExamHeader
        String description2 = "Exam Description";
        GradingType gradingFeedBackType = GradingType.NONE;
        FeedBackType feedbackType = FeedBackType.NONE;
        ExamHeader examHeader = new ExamHeader(description2, gradingFeedBackType, feedbackType);

        String examTitle = "Exam Title";

        // Act
        validExam = new AutomatedExam(sections,examHeader,openDate, closeDate, examTitle, teacher, course);
        student = UserDataSource.getTestStudent1();

    }

    @Test
    public void ensureBuilderReturnsValidTakenExam(){
        // arrange
        final String validStudentAnswer = "Student Answer";
        final Integer validSectionIndex = 1;
        final Integer validQuestionIndex = 1;

        // act
        final TakenExam takenExam = new TakenExamBuilder()
                .addAnsweredQuestion(new AnsweredQuestion("Question",validStudentAnswer, validSectionIndex, validQuestionIndex))
                .withExam(validExam)
                .withStudent(student)
                .build();

        // assert
        Assertions.assertEquals(validExam, takenExam.exam());
        Assertions.assertEquals(student, takenExam.student());
        Assertions.assertEquals(1, takenExam.answeredQuestions().size());
        Assertions.assertEquals(validStudentAnswer, takenExam.answeredQuestions().get(0).answer());
        Assertions.assertEquals(validSectionIndex-1, takenExam.answeredQuestions().get(0).sectionIndex());
        Assertions.assertEquals(validQuestionIndex-1, takenExam.answeredQuestions().get(0).questionIndex());
    }

}
