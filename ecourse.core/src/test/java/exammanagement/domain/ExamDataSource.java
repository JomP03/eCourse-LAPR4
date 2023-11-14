package exammanagement.domain;

import coursemanagement.domain.Course;
import coursemanagement.domain.CourseDataSource;
import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.domain.UserDataSource;
import org.junit.jupiter.api.Test;
import questionmanagment.domain.BooleanQuestion;
import questionmanagment.domain.Question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExamDataSource {

    public static AutomatedExam automatedExamText() {
        // create ExamSection
        String description = "Description";
        List<Question> questions = new ArrayList<>();
        questions.add(new BooleanQuestion("Question 1", true, 0.5f, 1f));
        questions.add(new BooleanQuestion("Question 2", true, 0.5f, 1f));
        questions.add(new BooleanQuestion("Question 3", true, 0.5f, 1f));
        questions.add(new BooleanQuestion("Question 4", true, 0.5f, 1f));
        questions.add(new BooleanQuestion("Question 5", true, 0.5f, 1f));
        questions.add(new BooleanQuestion("Question 6", true, 0.5f, 1f));

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
        return new AutomatedExam(sections, examHeader, openDate, closeDate, examTitle, teacher, course);
    }


    public static FormativeExam formativeExamTest() {
        // create ExamSection
        String description = "Description";
        List<Question> questions = new ArrayList<>();
        questions.add(new BooleanQuestion("Question 1", true, 0.5f, 1f));
        questions.add(new BooleanQuestion("Question 2", true, 0.5f, 1f));
        questions.add(new BooleanQuestion("Question 3", true, 0.5f, 1f));
        questions.add(new BooleanQuestion("Question 4", true, 0.5f, 1f));
        questions.add(new BooleanQuestion("Question 5", true, 0.5f, 1f));
        questions.add(new BooleanQuestion("Question 6", true, 0.5f, 1f));

        ExamSection examSection = new ExamSection(description, questions);
        List<ExamSection> sections = new ArrayList<>();
        sections.add(examSection);

        ECourseUser teacher = UserDataSource.getTestTeacher1();
        Course course = CourseDataSource.getTestCourse1();

        // create ExamHeader
        String description2 = "Exam Description";
        GradingType gradingFeedBackType = GradingType.NONE;
        FeedBackType feedbackType = FeedBackType.NONE;
        ExamHeader examHeader = new ExamHeader(description2, gradingFeedBackType, feedbackType);

        String examTitle = "Exam Title";

        // Act
        return new FormativeExam(sections, examHeader, examTitle, teacher, course);
    }

    public static ExamSection sectionTest() {
        // create ExamSection
        String description = "Description";
        List<Question> questions = new ArrayList<>();
        questions.add(new BooleanQuestion("Question 1", true, 0.5f, 1f));
        questions.add(new BooleanQuestion("Question 2", true, 0.5f, 1f));
        questions.add(new BooleanQuestion("Question 3", true, 0.5f, 1f));
        questions.add(new BooleanQuestion("Question 4", true, 0.5f, 1f));
        questions.add(new BooleanQuestion("Question 5", true, 0.5f, 1f));
        questions.add(new BooleanQuestion("Question 6", true, 0.5f, 1f));

        return new ExamSection(description, questions);
    }
}
