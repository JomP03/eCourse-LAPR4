package exammanagement.domain;

import coursemanagement.domain.Course;
import coursemanagement.domain.CourseDataSource;
import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.domain.UserDataSource;
import questionmanagment.domain.BooleanQuestion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import questionmanagment.domain.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExamTest {

    @Test
    public void ensureValidExamIsAccepted() {

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
        AutomatedExam exam = new AutomatedExam(sections, examHeader, openDate, closeDate, examTitle, teacher, course);

        String expected = "Exam Description";

        // Assert
        Assertions.assertTrue(exam.toString().contains(expected));

    }

    @Test
    public void ensureNullSectionsIsRejected() {
        // Arrange

        // create Open Period
        LocalDateTime openDate = LocalDateTime.now().plusDays(1);
        LocalDateTime closeDate = LocalDateTime.now().plusDays(2);

        // create ExamHeader
        String description2 = "Exam Description";
        GradingType gradingFeedBackType = GradingType.NONE;
        FeedBackType feedbackType = FeedBackType.NONE;
        ExamHeader examHeader = new ExamHeader(description2, gradingFeedBackType, feedbackType);

        String examTitle = "Exam Title";

        // Creator and Course
        ECourseUser teacher = UserDataSource.getTestTeacher1();
        Course course = CourseDataSource.getTestCourse1();

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new AutomatedExam(null, examHeader, openDate, closeDate, examTitle, teacher, course));

    }

    @Test
    public void ensureNullExamHeaderIsRejected() {

        // Arrange
        // create ExamSection
        String description = "Description";
        List<Question> questions = new ArrayList<>();
        questions.add(new BooleanQuestion("Question", true, 0.5f, 1f));

        ExamSection examSection = new ExamSection(description, questions);
        List<ExamSection> sections = new ArrayList<>();
        sections.add(examSection);

        // Creator and Course
        ECourseUser teacher = UserDataSource.getTestTeacher1();
        Course course = CourseDataSource.getTestCourse1();

        // create Open Period
        LocalDateTime openDate = LocalDateTime.now().plusDays(1);
        LocalDateTime closeDate = LocalDateTime.now().plusDays(2);

        String examTitle = "Exam Title";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new AutomatedExam(sections, null, openDate, closeDate, examTitle, teacher, course));

    }

    @Test
    public void ensureNullOpenDateIsRejected() {

        // Arrange
        // create ExamSection
        String description = "Description";
        List<Question> questions = new ArrayList<>();
        questions.add(new BooleanQuestion("Question", true, 0.5f, 1f));

        ExamSection examSection = new ExamSection(description, questions);
        List<ExamSection> sections = new ArrayList<>();
        sections.add(examSection);

        // create ExamHeader
        String description2 = "Exam Description";
        GradingType gradingFeedBackType = GradingType.NONE;
        FeedBackType feedbackType = FeedBackType.NONE;
        ExamHeader examHeader = new ExamHeader(description2, gradingFeedBackType, feedbackType);

        LocalDateTime closeDate = LocalDateTime.now().plusDays(2);

        String examTitle = "Exam Title";

        // Creator and Course
        ECourseUser teacher = UserDataSource.getTestTeacher1();
        Course course = CourseDataSource.getTestCourse1();

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new AutomatedExam(sections, examHeader, null, closeDate, examTitle, teacher, course));

    }

    @Test
    public void ensureNullCloseDateIsRejected() {

        // Arrange
        // create ExamSection
        String description = "Description";
        List<Question> questions = new ArrayList<>();
        questions.add(new BooleanQuestion("Question", true, 0.5f, 1f));

        ExamSection examSection = new ExamSection(description, questions);
        List<ExamSection> sections = new ArrayList<>();
        sections.add(examSection);

        // create ExamHeader
        String description2 = "Exam Description";
        GradingType gradingFeedBackType = GradingType.NONE;
        FeedBackType feedbackType = FeedBackType.NONE;
        ExamHeader examHeader = new ExamHeader(description2, gradingFeedBackType, feedbackType);

        LocalDateTime openDate = LocalDateTime.now().plusDays(1);

        String examTitle = "Exam Title";

        // Creator and Course
        ECourseUser teacher = UserDataSource.getTestTeacher1();
        Course course = CourseDataSource.getTestCourse1();

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new AutomatedExam(sections, examHeader, openDate, null, examTitle, teacher, course));

    }

    @Test
    public void ensureNullExamTitleIsRejected() {

        // Arrange
        // create ExamSection
        String description = "Description";
        List<Question> questions = new ArrayList<>();
        questions.add(new BooleanQuestion("Question", true, 0.5f, 1f));

        ExamSection examSection = new ExamSection(description, questions);
        List<ExamSection> sections = new ArrayList<>();
        sections.add(examSection);

        // create ExamHeader
        String description2 = "Exam Description";
        GradingType gradingFeedBackType = GradingType.NONE;
        FeedBackType feedbackType = FeedBackType.NONE;
        ExamHeader examHeader = new ExamHeader(description2, gradingFeedBackType, feedbackType);

        LocalDateTime openDate = LocalDateTime.now().plusDays(1);
        LocalDateTime closeDate = LocalDateTime.now().plusDays(2);

        // Creator and Course
        ECourseUser teacher = UserDataSource.getTestTeacher1();
        Course course = CourseDataSource.getTestCourse1();

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new AutomatedExam(sections, examHeader, openDate, closeDate, null, teacher, course));

    }

    @Test
    public void ensureEmptyExamTitleIsRejected() {

        // Arrange
        // create ExamSection
        String description = "Description";
        List<Question> questions = new ArrayList<>();
        questions.add(new BooleanQuestion("Question", true, 0.5f, 1f));

        ExamSection examSection = new ExamSection(description, questions);
        List<ExamSection> sections = new ArrayList<>();
        sections.add(examSection);

        // create ExamHeader
        String description2 = "Exam Description";
        GradingType gradingFeedBackType = GradingType.NONE;
        FeedBackType feedbackType = FeedBackType.NONE;
        ExamHeader examHeader = new ExamHeader(description2, gradingFeedBackType, feedbackType);

        LocalDateTime openDate = LocalDateTime.now().plusDays(1);
        LocalDateTime closeDate = LocalDateTime.now().plusDays(2);

        String examTitle = "";

        // Creator and Course
        ECourseUser teacher = UserDataSource.getTestTeacher1();
        Course course = CourseDataSource.getTestCourse1();

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new AutomatedExam(sections, examHeader, openDate, closeDate, examTitle, teacher, course));

    }

    @Test
    public void ensureExamTitleWithOnlySpacesIsRejected() {

        // Arrange
        // create ExamSection
        String description = "Description";
        List<Question> questions = new ArrayList<>();
        questions.add(new BooleanQuestion("Question", true, 0.5f, 1f));

        ExamSection examSection = new ExamSection(description, questions);
        List<ExamSection> sections = new ArrayList<>();
        sections.add(examSection);

        // create ExamHeader
        String description2 = "Exam Description";
        GradingType gradingFeedBackType = GradingType.NONE;
        FeedBackType feedbackType = FeedBackType.NONE;
        ExamHeader examHeader = new ExamHeader(description2, gradingFeedBackType, feedbackType);

        LocalDateTime openDate = LocalDateTime.now().plusDays(1);
        LocalDateTime closeDate = LocalDateTime.now().plusDays(2);

        String examTitle = "   ";

        // Creator and Course
        ECourseUser teacher = UserDataSource.getTestTeacher1();
        Course course = CourseDataSource.getTestCourse1();

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new AutomatedExam(sections, examHeader, openDate, closeDate, examTitle, teacher, course));

    }

    @Test
    public void ensureNullTeacherIsRejected() {

        // Arrange
        // create ExamSection
        String description = "Description";
        List<Question> questions = new ArrayList<>();
        questions.add(new BooleanQuestion("Question", true, 0.5f, 1f));

        ExamSection examSection = new ExamSection(description, questions);
        List<ExamSection> sections = new ArrayList<>();
        sections.add(examSection);

        // create ExamHeader
        String description2 = "Exam Description";
        GradingType gradingFeedBackType = GradingType.NONE;
        FeedBackType feedbackType = FeedBackType.NONE;
        ExamHeader examHeader = new ExamHeader(description2, gradingFeedBackType, feedbackType);

        LocalDateTime openDate = LocalDateTime.now().plusDays(1);
        LocalDateTime closeDate = LocalDateTime.now().plusDays(2);

        String examTitle = "Exam Title";

        // Creator and Course
        Course course = CourseDataSource.getTestCourse1();

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new AutomatedExam(sections, examHeader, openDate, closeDate, examTitle, null, course));

    }

    @Test
    public void ensureNullCourseIsRejected() {

        // Arrange
        // create ExamSection
        String description = "Description";
        List<Question> questions = new ArrayList<>();
        questions.add(new BooleanQuestion("Question", true, 0.5f, 1f));

        ExamSection examSection = new ExamSection(description, questions);
        List<ExamSection> sections = new ArrayList<>();
        sections.add(examSection);

        // create ExamHeader
        String description2 = "Exam Description";
        GradingType gradingFeedBackType = GradingType.NONE;
        FeedBackType feedbackType = FeedBackType.NONE;
        ExamHeader examHeader = new ExamHeader(description2, gradingFeedBackType, feedbackType);

        LocalDateTime openDate = LocalDateTime.now().plusDays(1);
        LocalDateTime closeDate = LocalDateTime.now().plusDays(2);

        String examTitle = "Exam Title";

        // Creator and Course
        ECourseUser teacher = UserDataSource.getTestTeacher1();

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new AutomatedExam(sections, examHeader, openDate, closeDate, examTitle, teacher, null));

    }

    @Test
    public void ensureUpdateTitleWithNullIsRejected() {
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

        AutomatedExam exam = new AutomatedExam(sections, examHeader, openDate, closeDate, examTitle, teacher, course);

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> exam.updateTitle(null));

    }

    @Test
    public void ensureUpdateTitleWithEmptyStringIsRejected() {
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

        AutomatedExam exam = new AutomatedExam(sections, examHeader, openDate, closeDate, examTitle, teacher, course);

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> exam.updateTitle(""));

    }

    @Test
    public void ensureUpdateTitleWithOnlySpacesIsRejected() {
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

        AutomatedExam exam = new AutomatedExam(sections, examHeader, openDate, closeDate, examTitle, teacher, course);

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> exam.updateTitle("   "));

    }

    @Test
    public void ensureUpdateTitleWithValidStringIsAccepted() {
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

        AutomatedExam exam = new AutomatedExam(sections, examHeader, openDate, closeDate, examTitle, teacher, course);

        // Assert
        Assertions.assertDoesNotThrow(() -> exam.updateTitle("New Title"));

    }

    @Test
    public void ensureUpdateHeaderWithNullIsRejected() {
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

        AutomatedExam exam = new AutomatedExam(sections, examHeader, openDate, closeDate, examTitle, teacher, course);

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                exam.updateHeader(null));

    }

    @Test
    public void ensureUpdateHeaderWithValidHeaderIsAccepted() {
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

        AutomatedExam exam = new AutomatedExam(sections, examHeader, openDate, closeDate, examTitle, teacher, course);

        ExamHeader newHeader = new ExamHeader("New Description", GradingType.NONE, FeedBackType.NONE);

        // Assert
        Assertions.assertDoesNotThrow(() ->
                exam.updateHeader(newHeader));

    }

    @Test
    public void ensureUpdateSectionDescriptionWithNullIsRejected() {
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

        AutomatedExam exam = new AutomatedExam(sections, examHeader, openDate, closeDate, examTitle, teacher, course);

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                exam.updateSectionDescription(examSection, null));

    }

    @Test
    public void ensureUpdateSectionDescriptionWithEmptyStringIsRejected() {
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

        AutomatedExam exam = new AutomatedExam(sections, examHeader, openDate, closeDate, examTitle, teacher, course);

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                exam.updateSectionDescription(examSection, "   "));

    }

    @Test
    public void ensureUpdateSectionQuestionsWithNullIsRejected() {
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

        AutomatedExam exam = new AutomatedExam(sections, examHeader, openDate, closeDate, examTitle, teacher, course);

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                exam.updateSectionQuestions(examSection, null));

    }

    @Test
    public void ensureUpdateAllWorks() {
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

        AutomatedExam exam = new AutomatedExam(sections, examHeader, openDate, closeDate, examTitle, teacher, course);

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
        Assertions.assertDoesNotThrow(() ->
                exam.updateAll(new AutomatedExam(newSections, newHeader, openDate2, closeDate2, examTitle2, teacher2, course2)));

    }

}
