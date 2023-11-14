package coursemanagement.domain;

import coursemanagement.repository.CourseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CourseBuilderTest {

    CourseRepository courseRepo;

    @BeforeEach
    void setUp() {
        courseRepo = mock(CourseRepository.class);
    }

    @Test
    public void ensureCourseBuilderReturnsCorrectCourse() {
        // Arrange
        CourseBuilder courseBuilder = new CourseBuilder(courseRepo);
        String courseCode = "1234";
        String courseName = "Test Course";
        String courseDescription = "Test Course Description";
        int minNrEnrolledStudents = 10;
        int maxNrEnrolledStudents = 20;
        when(courseRepo.ofIdentity(CourseCode.valueOf(courseCode))).thenReturn(Optional.empty());

        // Act
        Course course = courseBuilder.
                withCourseCode(courseCode)
                .withCourseName(courseName)
                .withCourseDescription(courseDescription)
                .withMinNrEnrolledStudents(minNrEnrolledStudents)
                .withMaxNrEnrolledStudents(maxNrEnrolledStudents)
                .build();

        String courseString = "CourseCode: " + courseCode + " | CourseName: " + courseName + " | CourseState: " + "CLOSE";

        // Assert
        Assertions.assertEquals(course.toString(), courseString);
    }

    @Test
    public void ensureCourseBuilderThrowsExceptionWhenCourseCodeAlreadyExists() {
        // Arrange
        CourseBuilder courseBuilder = new CourseBuilder(courseRepo);
        String courseCode = "1234";
        String courseName = "Test Course";
        String courseDescription = "Test Course Description";
        int minNrEnrolledStudents = 10;
        int maxNrEnrolledStudents = 20;
        Course course = new Course(courseCode, courseName, courseDescription,
                minNrEnrolledStudents, maxNrEnrolledStudents);
        when(courseRepo.ofIdentity(CourseCode.valueOf(courseCode))).thenReturn(Optional.of(course));

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            courseBuilder
                    .withCourseCode(courseCode)
                    .withCourseName(courseName)
                    .withCourseDescription(courseDescription)
                    .withMinNrEnrolledStudents(minNrEnrolledStudents)
                    .withMaxNrEnrolledStudents(maxNrEnrolledStudents)
                    .build();
        });
    }
}
