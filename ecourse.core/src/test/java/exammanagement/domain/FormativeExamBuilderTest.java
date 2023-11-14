package exammanagement.domain;

import coursemanagement.domain.Course;
import coursemanagement.domain.CourseDataSource;
import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.domain.UserDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import questionmanagment.domain.BooleanQuestion;
import questionmanagment.domain.Question;

import java.util.ArrayList;
import java.util.List;

public class FormativeExamBuilderTest {

    @Test
    public void ensureValidFormativeExamIsCreated() {
        // Arrange

        // create ExamSection
        String description = "Description";
        List<Question> questions = new ArrayList<>();
        questions.add(new BooleanQuestion("Question", true, 0.5f, 1f));

        ECourseUser teacher = UserDataSource.getTestTeacher1();
        Course course = CourseDataSource.getTestCourse1();

        // create ExamHeader
        String description2 = "Exam Description";
        GradingType gradingFeedBackType = GradingType.NONE;
        FeedBackType feedbackType = FeedBackType.NONE;

        String examTitle = "Exam Title";

        // Act
        FormativeExamBuilder exam = new FormativeExamBuilder();

        exam.withTitle(examTitle);
        exam.withCourse(course);
        exam.withCreator(teacher);
        exam.withHeader(description2, feedbackType, gradingFeedBackType);
        exam.withSection(description, questions);

        // Create the exam
        FormativeExam formativeExam = exam.build();

        // Assert
        Assertions.assertNotNull(formativeExam);
    }


    @Test
    public void ensureFormativeExamsCanHaveSectionsRemovedWhileBeingCreated() {
        // Arrange

        // create ExamSection
        String description = "Description";
        List<Question> questions = new ArrayList<>();
        questions.add(new BooleanQuestion("Question", true, 0.5f, 1f));

        String description2 = "Description2";
        List<Question> questions2 = new ArrayList<>();
        questions2.add(new BooleanQuestion("Question", true, 0.5f, 1f));

        ECourseUser teacher = UserDataSource.getTestTeacher1();
        Course course = CourseDataSource.getTestCourse1();

        // create ExamHeader
        String examDescription = "Exam Description";
        GradingType gradingFeedBackType = GradingType.NONE;
        FeedBackType feedbackType = FeedBackType.NONE;

        String examTitle = "Exam Title";

        // Act
        FormativeExamBuilder exam = new FormativeExamBuilder();

        exam.withTitle(examTitle);
        exam.withCourse(course);
        exam.withCreator(teacher);
        exam.withHeader(examDescription, feedbackType, gradingFeedBackType);
        exam.withSection(description, questions);
        exam.withSection(description2, questions2);
        exam.removeSections();

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, exam::build);
    }


}
