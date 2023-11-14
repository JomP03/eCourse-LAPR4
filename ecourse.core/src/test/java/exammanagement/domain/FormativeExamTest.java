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

public class FormativeExamTest {

    @Test
    public void ensureValidFormativeExamIsCreated() {
        // Arrange
        FormativeExam exam = ExamDataSource.formativeExamTest();
        String examTitle = "Exam Title";

        // Assert
        Assertions.assertEquals(examTitle, exam.title());

    }

    @Test
    public void ensureUpdateSectionQuestionsWorks() {
        // Arrange
        FormativeExam exam = ExamDataSource.formativeExamTest();

        // create ExamSection
        String description = "Description";
        List<Question> questions = new ArrayList<>();
        questions.add(new BooleanQuestion("Question", true, 0.5f, 1f));

        ExamSection examSection = new ExamSection(description, questions);

        // Act
        exam.updateSectionQuestions(examSection, questions);

        // Assert
        Assertions.assertEquals(questions, examSection.questions());

    }

    @Test
    public void ensureStringToFileWorks() {
        // Arrange
        FormativeExam exam = ExamDataSource.formativeExamTest();

        // Assert
        Assertions.assertDoesNotThrow(() -> {
            exam.stringToFile();
        });
    }


    @Test
    void ensureExamToString() {
        // Arrange
        FormativeExam exam = ExamDataSource.formativeExamTest();

        String examString = exam.title();

        // Assert
        Assertions.assertEquals(examString, exam.toString());
    }


    @Test
    void ensureExamPrintExam() {
        // Arrange
        FormativeExam exam = ExamDataSource.formativeExamTest();

        String examString = printExam(exam);

        // Assert
        Assertions.assertEquals(examString, exam.printExam());
    }


    private String printExam(FormativeExam exam) {
        StringBuilder sb = new StringBuilder();

        sb.append("===========\n|Formative|\n===========\n\n");

        String line = "__________________________________________________________________________\n";
        sb.append(line);

        Integer titleLength = exam.title().length();
        Integer lineLength = line.length();

        int spaces = (lineLength - titleLength) / 2;

        sb.append(" ".repeat(Math.max(0, spaces)));

        sb.append(exam.title()).append("\n\n");


        // Exam Header
        sb.append(exam.header().toString()).append("\n");

        sb.append(line);

        // Exam Sections
        int sectionNumber = 1;
        for (ExamSection section : exam.sections()) {
            sb.append("\n").append("Section ").append(sectionNumber).append(" : ");
            sb.append(section.toString()).append("\n");
            sb.append(line);
            sectionNumber++;
        }

        return sb.toString();
    }


    @Test
    void ensureFormativeExamIdentity() {
        // Arrange
        FormativeExam exam = ExamDataSource.formativeExamTest();

        // Assert
        Assertions.assertNull(exam.identity());
    }


    @Test
    void ensureFormativeExamReturnsItsCreator() {
        // Arrange
        FormativeExam exam = ExamDataSource.formativeExamTest();
        ECourseUser user = UserDataSource.getTestTeacher1();

        // Assert
        Assertions.assertEquals(user, exam.creator());
    }


    @Test
    void ensureFormativeExamReturnsItsCourse() {
        // Arrange
        FormativeExam exam = ExamDataSource.formativeExamTest();
        Course course = CourseDataSource.getTestCourse1();

        // Assert
        Assertions.assertEquals(course, exam.course());
    }
}
