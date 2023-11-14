package classmanagement.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClassTitleTest {

    @Test
    public void ensureValidClassTitleIsAccepted() {

        // Arrange
        String classTitle = "Class Title";

        // Act
        ClassTitle classTitleObject = new ClassTitle(classTitle);

        // Assert
        Assertions.assertEquals(classTitleObject.getClassTitle(), classTitle);

    }

    @Test
    public void ensureNullClassTitleIsRejected() {

        // Arrange
        String classTitle = null;

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ClassTitle(classTitle);
        });

    }

    @Test
    public void ensureEmptyClassTitleIsRejected() {

        // Arrange
        String classTitle = "";

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ClassTitle(classTitle);
        });

    }
}
