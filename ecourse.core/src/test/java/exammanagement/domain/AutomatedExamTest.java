package exammanagement.domain;

import coursemanagement.domain.Course;
import coursemanagement.domain.CourseDataSource;
import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.domain.UserDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testng.asserts.Assertion;
import questionmanagment.domain.BooleanQuestion;
import questionmanagment.domain.Question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AutomatedExamTest {

    @Test
    public void ensureValidAutomatedExamIsAccepted() {
        // Arrange

        // create ExamSection
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
        AutomatedExam exam = new AutomatedExam(sections,examHeader,openDate, closeDate, examTitle, teacher, course);

        // Assert
        Assertions.assertEquals(openDate, exam.openPeriod().openDate());

    }

    @Test
    public void ensureUpdateAllIsWorking() {

        // Arrange

        // create ExamSection
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

        AutomatedExam exam = new AutomatedExam(sections,examHeader,openDate, closeDate, examTitle, teacher, course);

        // ---------------------------------------------------------------------------------------------------------

        // create new ExamSection
        String description3 = "New Description";
        List<Question> questions2 = new ArrayList<>();
        questions2.add(new BooleanQuestion("Question", true, 0.5f, 1f));

        ExamSection newSection = new ExamSection(description3, questions2);

        List<ExamSection> newSections = new ArrayList<>();
        newSections.add(newSection);

        // create new ExamHeader
        String description4 = "New Description";
        GradingType gradingFeedBackType2 = GradingType.NONE;
        FeedBackType feedbackType2 = FeedBackType.NONE;
        ExamHeader newHeader = new ExamHeader(description4, gradingFeedBackType2, feedbackType2);

        // create new Open Period
        LocalDateTime openDate2 = LocalDateTime.now().plusDays(3);
        LocalDateTime closeDate2 = LocalDateTime.now().plusDays(4);

        // create new ExamTitle
        String examTitle2 = "New Exam Title";

        // create new Teacher
        ECourseUser teacher2 = UserDataSource.getTestTeacher2();

        // create new Course
        Course course2 = CourseDataSource.getTestCourse2();

        // Act
        Assertions.assertDoesNotThrow(() -> {
            exam.updateAll(new AutomatedExam(newSections, newHeader, openDate2, closeDate2, examTitle2, teacher2, course2));
        });
    }

    @Test
    public void ensureStringToFileIsWorking() {
        // Arrange

        // create ExamSection
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

        AutomatedExam exam = new AutomatedExam(sections,examHeader,openDate, closeDate, examTitle, teacher, course);

        // Act
        Assertions.assertDoesNotThrow(() -> {
            exam.stringToFile();
        });
    }

    @Test
    void ensureExamReturnsTheNumberOfQuestions() {
        // Arrange
        AutomatedExam exam = ExamDataSource.automatedExamText();

        // Act
        int numberOfQuestions = exam.numberOfQuestions();

        // Assert
        Assertions.assertEquals(6, numberOfQuestions);
    }


    @Test
    void ensureExamReturnsTheMaxGrade() {
        // Arrange
        AutomatedExam exam = ExamDataSource.automatedExamText();

        // Act
        float maxGrade = exam.maxGrade();

        // Assert
        Assertions.assertEquals(6.0, maxGrade);
    }


    @Test
    void ensureExamReturnsQuestion() {
        // Arrange
        Exam exam = ExamDataSource.automatedExamText();

        // Act
        Question question = exam.obtainQuestion(0, 0);

        // Assert
        Assertions.assertEquals("Question 1", question.question());
    }



    @Test
    void ensureExamIdentity() {
        // Ararnge
        AutomatedExam exam = ExamDataSource.automatedExamText();

        // Assert
        Assertions.assertNull(exam.identity());
    }
}
