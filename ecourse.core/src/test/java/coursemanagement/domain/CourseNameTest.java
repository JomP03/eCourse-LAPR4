package coursemanagement.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CourseNameTest {

        @Test
        public void ensureValidCourseNameIsAccepted() {
            // Arrange
            String courseNameString = "LAPR4";

            // Act
            CourseName courseName = new CourseName(courseNameString);

            // Assert
            Assertions.assertEquals(courseName.toString(), courseNameString);
        }

        @Test
        public void ensureEmptyCourseNameIsRejected() {
            // Arrange
            String courseNameString = "";

            // Assert
            Assertions.assertThrows(IllegalArgumentException.class, () -> new CourseName(courseNameString));
        }

        @Test
        public void ensureNullCourseNameIsRejected() {

            // Assert
            Assertions.assertThrows(IllegalArgumentException.class, () -> new CourseName(null));
        }

        @Test
        public void ensureCourseNameToString() {
            // Arrange
            String courseNameString = "LAPR4";
            CourseName courseName = new CourseName(courseNameString);

            // Assert
            Assertions.assertEquals(courseName.toString(), courseNameString);
        }
}
