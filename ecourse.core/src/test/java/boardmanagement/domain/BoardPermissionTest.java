package boardmanagement.domain;

import ecourseusermanagement.domain.UserDataSource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardPermissionTest {
    @Test
    public void ensureBoardPermissionIsNotCreatedWithNullUser() {
        // Arrange
        BoardPermissionType permissionType = BoardPermissionType.OWNER;

        // Assert
        assertThrows(IllegalArgumentException.class, () -> new BoardPermission(null, permissionType));
    }

    @Test
    public void ensureBoardPermissionIsCreatedWithValidInformation(){
        // Arrange
        BoardPermission permission = BoardDataSource.boardPermissionTest();

        // Act & Assert
        assertNotNull(permission);
    }

    @Test
    public void ensureBoardPermissionUserIsCorrect(){
        // Arrange
        BoardPermission permission = BoardDataSource.boardPermissionTest();

        // Assert
        assertEquals(permission.owner(), UserDataSource.getTestStudent1());
    }


    @Test
    void ensureBoardPermissionHasWritePermission() {
        // Arrange
        BoardPermission permission = BoardDataSource.boardPermissionTest();

        // Assert
        assertTrue(permission.hasWritePermission());
    }
}
