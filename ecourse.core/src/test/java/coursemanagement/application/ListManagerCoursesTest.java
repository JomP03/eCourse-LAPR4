package coursemanagement.application;

import coursemanagement.domain.Course;
import coursemanagement.domain.CourseDataSource;
import coursemanagement.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ListManagerCoursesTest {
    private CourseRepository courseRepository;

    @BeforeEach
    void setUp() {
        courseRepository = mock(CourseRepository.class);
    }

    @Test
    void ensureListManagerCoursesControllerReturnsCorrectCourses() {

        // Arrange
        ArrayList<Course> expectedCourses = new ArrayList<>();
        expectedCourses.add(CourseDataSource.getTestCourse1());
        expectedCourses.add(CourseDataSource.getTestCourse2());
        expectedCourses.add(CourseDataSource.getTestCourse3());

        when(courseRepository.findAll()).thenReturn(expectedCourses);

        // Act
        Iterable<Course> actualCourses = courseRepository.findAll();

        // Assert
        assertTrue(((ArrayList<Course>) actualCourses).containsAll(expectedCourses));
    }
}