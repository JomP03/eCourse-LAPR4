package coursemanagement.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CourseDescriptionTest {

    @Test
    public void ensureValidCourseDescriptionIsAccepted() {
        // Arrange
        String courseDescriptionString = "This is a course";

        // Act
        CourseDescription courseDescription = new CourseDescription(courseDescriptionString);

        // Assert
        Assertions.assertEquals(courseDescription.toString(), courseDescriptionString);
    }

    @Test
    public void ensureEmptyCourseDescriptionIsRejected() {
        // Arrange
        String courseDescriptionString = "";

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CourseDescription(courseDescriptionString));
    }

    @Test
    public void ensureNullCourseDescriptionIsRejected() {

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CourseDescription(null));
    }

    @Test
    public void ensureCourseDescriptionToString() {
        // Arrange
        String courseDescriptionString = "This is a course";
        CourseDescription courseDescription = new CourseDescription(courseDescriptionString);

        // Assert
        Assertions.assertEquals(courseDescription.toString(), courseDescriptionString);
    }
}
