package coursemanagement.application;

import coursemanagement.application.listcourses.StudentCoursesProvider;
import coursemanagement.domain.Course;
import coursemanagement.domain.CourseDataSource;
import coursemanagement.domain.CourseState;
import coursemanagement.repository.CourseRepository;
import ecourseusermanagement.domain.UserDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ListStudentCoursesTest {
    private StudentCoursesProvider studentCoursesProvider;
    private CourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
        courseRepository = mock(CourseRepository.class);
        studentCoursesProvider = new StudentCoursesProvider(courseRepository);
    }

    @Test
    public void ensureListStudentCoursesReturnsCorrectLists() {
        // Arrange
        ArrayList<Course> studentCourses = new ArrayList<>();
        studentCourses.add(CourseDataSource.getTestCourse1());
        studentCourses.add(CourseDataSource.getTestCourse2());
        ArrayList<Course> coursesToEnroll = new ArrayList<>();
        coursesToEnroll.add(CourseDataSource.getTestCourse2());
        coursesToEnroll.add(CourseDataSource.getTestCourse3());

        when(courseRepository.findStudentCourses(UserDataSource.getTestStudent1())).thenReturn(studentCourses);
        when(courseRepository.findCoursesByState(CourseState.ENROLL)).thenReturn(coursesToEnroll);

        // Act
        Iterable<Course> actualCourses = studentCoursesProvider.provideStudentAvailableCourses(UserDataSource.getTestStudent1());

        // Assert
        assertTrue(((ArrayList<Course>) actualCourses).containsAll(studentCourses));
        assertTrue(((ArrayList<Course>) actualCourses).containsAll(coursesToEnroll));
    }
}
