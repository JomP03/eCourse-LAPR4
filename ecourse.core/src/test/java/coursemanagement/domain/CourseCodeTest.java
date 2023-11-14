package coursemanagement.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CourseCodeTest {

    @Test
    public void ensureValidCourseCodeIsAccepted() {
        // Arrange
        String courseCodeString = "LAPR4";

        // Act
        CourseCode courseCode = new CourseCode(courseCodeString);

        // Assert
        Assertions.assertEquals(courseCode.toString(), courseCodeString);
    }

    @Test
    public void ensureEmptyCourseCodeIsRejected() {
        // Arrange
        String courseCodeString = "";

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CourseCode(courseCodeString));
    }

    @Test
    public void ensureNullCourseCodeIsRejected() {

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CourseCode(null));
    }

    @Test
    public void ensureCourseCodeEqualsSameCode() {
        // Arrange
        String courseCodeString = "LAPR4";
        CourseCode courseCode = new CourseCode(courseCodeString);
        CourseCode courseCode2 = new CourseCode(courseCodeString);

        // Assert
        Assertions.assertEquals(courseCode, courseCode2);
    }

    @Test
    public void ensureCourseCodeIsTheSame() {
        // Arrange
        String courseCodeString = "LAPR4";
        CourseCode courseCode = new CourseCode(courseCodeString);

        // Assert
        Assertions.assertEquals(courseCode, courseCode);
    }

    @Test
    public void ensureCourseCodeEqualsDifferentClass() {
        // Arrange
        String courseCodeString = "LAPR4";
        CourseCode courseCode = new CourseCode(courseCodeString);
        CourseName courseName = new CourseName(courseCodeString);

        // Assert
        Assertions.assertNotEquals(courseCode, courseName);
    }

    @Test
    public void ensureCourseCodeEqualsDifferentCode() {
        // Arrange
        String courseCodeString = "LAPR4";
        String courseCodeString2 = "LAPR5";
        CourseCode courseCode = new CourseCode(courseCodeString);
        CourseCode courseCode2 = new CourseCode(courseCodeString2);

        // Assert
        Assertions.assertNotEquals(courseCode, courseCode2);
    }

    @Test
    public void ensureCompareTo() {
        // Arrange
        String courseCodeString = "LAPR4";
        String courseCodeString2 = "LAPR5";
        CourseCode courseCode = new CourseCode(courseCodeString);
        CourseCode courseCode2 = new CourseCode(courseCodeString2);

        // Assert
        Assertions.assertEquals(-1, courseCode.compareTo(courseCode2));
    }
}
