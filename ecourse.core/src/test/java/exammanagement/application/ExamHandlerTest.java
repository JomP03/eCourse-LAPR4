package exammanagement.application;

import coursemanagement.domain.Course;
import coursemanagement.domain.CourseDataSource;
import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.domain.UserDataSource;
import exammanagement.application.service.ExamHandler;
import exammanagement.domain.AutomatedExam;
import exammanagement.repository.ExamRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.Mockito.mock;

public class ExamHandlerTest {

    private ExamRepository examRepository;

    private ExamHandler examHandler;

    private ECourseUser validTeacher;

    private Course validCourse;

    @BeforeEach
    public void setUp() {
        examRepository = mock(ExamRepository.class);
        examHandler = new ExamHandler(examRepository);

        validCourse = CourseDataSource.getTestCourse1();
        validTeacher = UserDataSource.getTestTeacher1();
    }

    @Test
    public void ensureExamHandlerReturnsAutomatedExamFromFile() throws IOException, URISyntaxException {
        // Arrange
        String filename = "examfiles/validexam.txt";
        Class<?> clazz = ExamHandlerTest.class; // Replace with your actual test class
        ClassLoader classLoader = clazz.getClassLoader();
        URL resourceUrl = classLoader.getResource(filename);
        Path filePath = Paths.get(resourceUrl.toURI());

        // Act

        AutomatedExam exam = examHandler.parseAutomatedExam(filePath.toString(),validCourse, validTeacher);

        // Assert
        Assertions.assertTrue(exam.toString().contains("Teste de Perguntas"));
    }

    @Test
    public void ensureExamHandlerRejectsInvalidHeader() throws URISyntaxException {
        // Arrange
        String filename = "examfiles/invalidexamheader.txt";
        Class<?> clazz = ExamHandlerTest.class; // Replace with your actual test class
        ClassLoader classLoader = clazz.getClassLoader();
        URL resourceUrl = classLoader.getResource(filename);
        Path filePath = Paths.get(resourceUrl.toURI());

        // Act & Assert
        Assertions.assertThrows(Exception.class, () -> {
            AutomatedExam exam = examHandler.parseAutomatedExam(filePath.toString(),validCourse, validTeacher);
        });

    }

    @Test
    public void ensureExamHandlerRejectsInvalidFeedBack() throws URISyntaxException {
        // Arrange
        String filename = "examfiles/invalidexamfeedback.txt";
        Class<?> clazz = ExamHandlerTest.class; // Replace with your actual test class
        ClassLoader classLoader = clazz.getClassLoader();
        URL resourceUrl = classLoader.getResource(filename);
        Path filePath = Paths.get(resourceUrl.toURI());

        // Act & Assert
        Assertions.assertThrows(Exception.class, () -> {
            AutomatedExam exam = examHandler.parseAutomatedExam(filePath.toString(),validCourse, validTeacher);
        });

    }

    @Test
    public void ensureExamHandlerRejectsInvalidSectionHeader() throws URISyntaxException {
        // Arrange
        String filename = "examfiles/invalidexamsectionheader.txt";
        Class<?> clazz = ExamHandlerTest.class; // Replace with your actual test class
        ClassLoader classLoader = clazz.getClassLoader();
        URL resourceUrl = classLoader.getResource(filename);
        Path filePath = Paths.get(resourceUrl.toURI());

        // Act & Assert
        Assertions.assertThrows(Exception.class, () -> {
            AutomatedExam exam = examHandler.parseAutomatedExam(filePath.toString(),validCourse, validTeacher);
        });

    }

    @Test
    public void ensureExamHandlerRejectsInvalidSection() throws URISyntaxException {
        // Arrange
        String filename = "examfiles/invalidexamsection.txt";
        Class<?> clazz = ExamHandlerTest.class; // Replace with your actual test class
        ClassLoader classLoader = clazz.getClassLoader();
        URL resourceUrl = classLoader.getResource(filename);
        Path filePath = Paths.get(resourceUrl.toURI());

        // Act & Assert
        Assertions.assertThrows(Exception.class, () -> {
            AutomatedExam exam = examHandler.parseAutomatedExam(filePath.toString(),validCourse, validTeacher);
        });

    }

    @Test
    public void ensureExamHandlerRejectsInvalidBooleanQuestion() throws URISyntaxException {
        // Arrange
        String filename = "examfiles/invalidexambquest.txt";
        Class<?> clazz = ExamHandlerTest.class; // Replace with your actual test class
        ClassLoader classLoader = clazz.getClassLoader();
        URL resourceUrl = classLoader.getResource(filename);
        Path filePath = Paths.get(resourceUrl.toURI());

        // Act & Assert
        Assertions.assertThrows(Exception.class, () -> {
            AutomatedExam exam = examHandler.parseAutomatedExam(filePath.toString(),validCourse, validTeacher);
        });

    }

    @Test
    public void ensureExamHandlerRejectsInvalidShortAnswerQuestion() throws URISyntaxException {
        // Arrange
        String filename = "examfiles/invalidexamsquest.txt";
        Class<?> clazz = ExamHandlerTest.class; // Replace with your actual test class
        ClassLoader classLoader = clazz.getClassLoader();
        URL resourceUrl = classLoader.getResource(filename);
        Path filePath = Paths.get(resourceUrl.toURI());

        // Act & Assert
        Assertions.assertThrows(Exception.class, () -> {
            AutomatedExam exam = examHandler.parseAutomatedExam(filePath.toString(),validCourse, validTeacher);
        });

    }

    @Test
    public void ensureExamHandlerRejectsInvalidMultipleChoiceQuestion() throws URISyntaxException {
        // Arrange
        String filename = "examfiles/invalidexammcquest.txt";
        Class<?> clazz = ExamHandlerTest.class; // Replace with your actual test class
        ClassLoader classLoader = clazz.getClassLoader();
        URL resourceUrl = classLoader.getResource(filename);
        Path filePath = Paths.get(resourceUrl.toURI());

        // Act & Assert
        Assertions.assertThrows(Exception.class, () -> {
            AutomatedExam exam = examHandler.parseAutomatedExam(filePath.toString(),validCourse, validTeacher);
        });

    }

    @Test
    public void ensureExamHandlerRejectsInvalidNumericalQuestion() throws URISyntaxException {
        // Arrange
        String filename = "examfiles/invalidexamnquest.txt";
        Class<?> clazz = ExamHandlerTest.class; // Replace with your actual test class
        ClassLoader classLoader = clazz.getClassLoader();
        URL resourceUrl = classLoader.getResource(filename);
        Path filePath = Paths.get(resourceUrl.toURI());

        // Act & Assert
        Assertions.assertThrows(Exception.class, () -> {
            AutomatedExam exam = examHandler.parseAutomatedExam(filePath.toString(),validCourse, validTeacher);
        });

    }

    @Test
    public void ensureExamHandlerRejectsInvalidMissingWordQuestion() throws URISyntaxException {
        // Arrange
        String filename = "examfiles/invalidexammwquest.txt";
        Class<?> clazz = ExamHandlerTest.class; // Replace with your actual test class
        ClassLoader classLoader = clazz.getClassLoader();
        URL resourceUrl = classLoader.getResource(filename);
        Path filePath = Paths.get(resourceUrl.toURI());

        // Act & Assert
        Assertions.assertThrows(Exception.class, () -> {
            AutomatedExam exam = examHandler.parseAutomatedExam(filePath.toString(),validCourse, validTeacher);
        });

    }

    @Test
    public void ensureExamHandlerRejectsInvalidMatchingQuestion() throws URISyntaxException {
        // Arrange
        String filename = "examfiles/invalidexammquest.txt";
        Class<?> clazz = ExamHandlerTest.class; // Replace with your actual test class
        ClassLoader classLoader = clazz.getClassLoader();
        URL resourceUrl = classLoader.getResource(filename);
        Path filePath = Paths.get(resourceUrl.toURI());

        // Act & Assert
        Assertions.assertThrows(Exception.class, () -> {
            AutomatedExam exam = examHandler.parseAutomatedExam(filePath.toString(),validCourse, validTeacher);
        });

    }


}
