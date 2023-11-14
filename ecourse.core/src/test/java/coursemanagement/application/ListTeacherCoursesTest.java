package coursemanagement.application;

import coursemanagement.domain.Course;
import coursemanagement.domain.CourseDataSource;
import coursemanagement.repository.CourseRepository;
import ecourseusermanagement.domain.UserDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ListTeacherCoursesTest {
    private CourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
        courseRepository = mock(CourseRepository.class);
    }

    @Test
    public void ensureListTeacherCoursesReturnsCorrectList(){
        // Arrange
        ArrayList<Course> expectedCourses = new ArrayList<>();
        expectedCourses.add(CourseDataSource.getTestCourse1());
        expectedCourses.add(CourseDataSource.getTestCourse2());
        expectedCourses.add(CourseDataSource.getTestCourse3());

        when(courseRepository.findTeacherCourses(UserDataSource.getTestTeacher1())).thenReturn(expectedCourses);

        // Act
        Iterable<Course> actualCourses = courseRepository.findTeacherCourses(UserDataSource.getTestTeacher1());

        // Assert
        assertTrue(((ArrayList<Course>) actualCourses).containsAll(expectedCourses));
    }
}
