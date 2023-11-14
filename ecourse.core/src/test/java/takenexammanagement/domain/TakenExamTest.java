package takenexammanagement.domain;

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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TakenExamTest {

    Exam validExam;

    Exam validExam2;

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


        // create valid Exam 2

        String description3 = "Description";
        List<Question> questions2 = new ArrayList<>();
        questions2.add(new BooleanQuestion("Question", true, 0.5f, 1f));

        ExamSection examSection2 = new ExamSection(description3, questions2);
        List<ExamSection> sections2 = new ArrayList<>();
        sections2.add(examSection2);

        ExamSection examSection3 = new ExamSection(description3, questions2);
        sections2.add(examSection3);

        ECourseUser teacher2 = UserDataSource.getTestTeacher1();
        Course course2 = CourseDataSource.getTestCourse1();

        // create Open Period
        LocalDateTime openDate2 = LocalDateTime.now().plusDays(1);
        LocalDateTime closeDate2 = LocalDateTime.now().plusDays(2);

        // create ExamHeader
        String description4 = "Exam Description";
        GradingType gradingFeedBackType2 = GradingType.NONE;
        FeedBackType feedbackType2 = FeedBackType.NONE;

        ExamHeader examHeader2 = new ExamHeader(description4, gradingFeedBackType2, feedbackType2);

        String examTitle2 = "Exam Title2";

        // Act
        validExam2 = new AutomatedExam(sections2,examHeader2,openDate2, closeDate2, examTitle2, teacher2, course2);

    }

    @Test
    public void ensureTakenExamIsCreatedWithValidParameters(){
        // Arrange
        List<AnsweredQuestion> answeredQuestions = new ArrayList<>();
        AnsweredQuestion answeredQuestion = new AnsweredQuestion("question", "true",1,1);
        answeredQuestions.add(answeredQuestion);

        // Act
        TakenExam takenExam = new TakenExam(answeredQuestions, student, validExam);

        // Assert
        Assertions.assertEquals(answeredQuestions, takenExam.answeredQuestions());
        Assertions.assertEquals(student, takenExam.student());
        Assertions.assertEquals(validExam, takenExam.exam());
        Assertions.assertEquals(0f, takenExam.grade());
    }

    @Test
    public void ensureTakenExamIsNotCreatedWithNullParameters(){
        // Arrange
        List<AnsweredQuestion> answeredQuestions = new ArrayList<>();
        AnsweredQuestion answeredQuestion = new AnsweredQuestion("question", "true",1,1);
        answeredQuestions.add(answeredQuestion);

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new TakenExam(null, student, validExam);
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new TakenExam(answeredQuestions, null, validExam);
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new TakenExam(answeredQuestions, student, null);
        });
    }

    @Test
    public void ensureDifferentNumberOfQuestionsIsRejected(){
        // Arrange
        List<AnsweredQuestion> answeredQuestions = new ArrayList<>();
        AnsweredQuestion answeredQuestion = new AnsweredQuestion("question", "true",1,1);
        AnsweredQuestion answeredQuestion2 = new AnsweredQuestion("question", "true",1,1);
        answeredQuestions.add(answeredQuestion);
        answeredQuestions.add(answeredQuestion2);

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new TakenExam(answeredQuestions, student, validExam);
        });

    }

    @Test
    public void ensureAddQuotationIsWorking(){
        // Arrange
        List<AnsweredQuestion> answeredQuestions = new ArrayList<>();
        AnsweredQuestion answeredQuestion = new AnsweredQuestion("question", "true",1,1);
        answeredQuestions.add(answeredQuestion);
        TakenExam takenExam = new TakenExam(answeredQuestions, student, validExam);

        Float quotation = 0.5f;
        // Act
        takenExam.addQuotation(quotation);

        // Assert
        Assertions.assertEquals(quotation, takenExam.grade());
    }

    @Test
    public void ensureAdjustGradeTo20IsWorking(){
        // Arrange
        List<AnsweredQuestion> answeredQuestions = new ArrayList<>();
        AnsweredQuestion answeredQuestion = new AnsweredQuestion("question", "true",1,1);
        answeredQuestions.add(answeredQuestion);
        TakenExam takenExam = new TakenExam(answeredQuestions, student, validExam);

        Float quotation = 0.5f;
        takenExam.addQuotation(quotation);

        // Act
        takenExam.ajustGrade();

        // Assert
        Assertions.assertEquals(10f, takenExam.grade());
    }

    @Test
    public void ensureToStringIsWorking(){
        // Arrange
        List<AnsweredQuestion> answeredQuestions = new ArrayList<>();
        AnsweredQuestion answeredQuestion = new AnsweredQuestion("question", "true",1,1);
        answeredQuestions.add(answeredQuestion);
        TakenExam takenExam = new TakenExam(answeredQuestions, student, validExam);

        Float quotation = 0.5f;
        takenExam.addQuotation(quotation);

        // Act
        String result = takenExam.toString();

        // Assert
        Assertions.assertTrue(result.contains(validExam.title()) && result.contains(student.email()) && result.contains(quotation.toString()));
    }

    @Test
    public void ensureSameAsIsWorking(){
        // Arrange
        List<AnsweredQuestion> answeredQuestions = new ArrayList<>();
        AnsweredQuestion answeredQuestion = new AnsweredQuestion("question", "true",1,1);
        answeredQuestions.add(answeredQuestion);
        TakenExam takenExam = new TakenExam(answeredQuestions, student, validExam);

        Float quotation = 0.5f;
        takenExam.addQuotation(quotation);

        // Act
        boolean result = takenExam.sameAs(takenExam);

        // Assert
        Assertions.assertTrue(result);
    }

    @Test
    public void ensureSameAsReturnsFalseWhenDifferent(){
        // Arrange
        List<AnsweredQuestion> answeredQuestions = new ArrayList<>();
        AnsweredQuestion answeredQuestion = new AnsweredQuestion("question", "true",1,1);
        answeredQuestions.add(answeredQuestion);
        TakenExam takenExam = new TakenExam(answeredQuestions, student, validExam);

        Float quotation = 0.5f;
        takenExam.addQuotation(quotation);

        // Act
        boolean result = takenExam.sameAs(null);

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void ensureSameAsReturnsFalseWhenDifferent2(){
        // Arrange
        List<AnsweredQuestion> answeredQuestions = new ArrayList<>();
        AnsweredQuestion answeredQuestion = new AnsweredQuestion("question", "true",1,1);
        answeredQuestions.add(answeredQuestion);
        TakenExam takenExam = new TakenExam(answeredQuestions, student, validExam);

        Float quotation = 0.5f;
        takenExam.addQuotation(quotation);

        // Act
        boolean result = takenExam.sameAs(new TakenExam(answeredQuestions, student, validExam));

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void ensureOrderAnsweredQuestionsIsWorking(){
        // Arrange
        List<AnsweredQuestion> answeredQuestions = new ArrayList<>();
        AnsweredQuestion answeredQuestion = new AnsweredQuestion("question", "true",2,1);
        AnsweredQuestion answeredQuestion2 = new AnsweredQuestion("question", "true",1,1);
        answeredQuestions.add(answeredQuestion);
        answeredQuestions.add(answeredQuestion2);
        TakenExam takenExam = new TakenExam(answeredQuestions, student, validExam2);

        // Assert
        Assertions.assertEquals(answeredQuestion2, takenExam.answeredQuestions().get(0));
        Assertions.assertEquals(answeredQuestion, takenExam.answeredQuestions().get(1));
    }

    @Test
    public void ensureIdentityIsWorking(){
        // Arrange
        List<AnsweredQuestion> answeredQuestions = new ArrayList<>();
        AnsweredQuestion answeredQuestion = new AnsweredQuestion("question", "true",2,1);
        AnsweredQuestion answeredQuestion2 = new AnsweredQuestion("question", "true",1,1);
        answeredQuestions.add(answeredQuestion);
        answeredQuestions.add(answeredQuestion2);
        TakenExam takenExam = new TakenExam(answeredQuestions, student, validExam2);

        // Assert
        Assertions.assertNull(takenExam.identity());
    }
}
