package boardmanagement.domain;

import boardlogmanagement.domain.*;
import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.domain.UserDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardLogTest {

        @Test
        public void ensureValidBoardLogIsAccepted(){
            // Arrange
            Board board = BoardDataSource.boardWithPostIts();
            BoardLogComponent component = BoardLogComponent.POST_IT;
            BoardOperationType operation = BoardOperationType.ARCHIVE_BOARD;
            ECourseUser user = UserDataSource.getTestStudent1();

            // Act
            BoardLog boardLog = new BoardLog(board, component, operation, user);

            // Assert
            assertEquals(user, boardLog.user());
        }

        @Test
        public void ensureNullBoardIsNotAccepted(){
            // Arrange
            BoardLogComponent component = BoardLogComponent.POST_IT;
            BoardOperationType operation = BoardOperationType.ARCHIVE_BOARD;
            ECourseUser user = UserDataSource.getTestStudent1();

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> new BoardLog(null, component,
                    operation, user));
        }

        @Test
        public void ensureNullComponentIsNotAccepted(){
            // Arrange
            Board board = BoardDataSource.boardWithPostIts();
            BoardOperationType operation = BoardOperationType.ARCHIVE_BOARD;
            ECourseUser user = UserDataSource.getTestStudent1();

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> new BoardLog(board, null,
                    operation, user));
        }

        @Test
        public void ensureNullOperationIsNotAccepted(){
            // Arrange
            Board board = BoardDataSource.boardWithPostIts();
            BoardLogComponent component = BoardLogComponent.POST_IT;
            ECourseUser user = UserDataSource.getTestStudent1();

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> new BoardLog(board, component,
                    null, user));
        }

        @Test
        public void ensureNullUserIsNotAccepted(){
            // Arrange
            Board board = BoardDataSource.boardWithPostIts();
            BoardLogComponent component = BoardLogComponent.POST_IT;
            BoardOperationType operation = BoardOperationType.ARCHIVE_BOARD;

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> new BoardLog(board, component,
                    operation, null));
        }

        @Test
        public void ensureValidUpdateBoardLogIsAccepted(){
            // Arrange
            Board board = BoardDataSource.boardWithPostIts();
            BoardLogComponent component = BoardLogComponent.POST_IT;
            BoardOperationType operation = BoardOperationType.UPDATE_POSTIT;
            ECourseUser user = UserDataSource.getTestStudent1();
            int oldColumn = 1;
            int oldRow = 1;
            int newColumn = 2;
            int newRow = 2;
            String oldContent = "Old content";
            String newContent = "New content";

            // Act
            BoardLog boardLog = new BoardLog(board, component, operation, user, oldColumn, oldRow,
                    newColumn, newRow, oldContent, newContent);


            // Assert
            assertEquals(user, boardLog.user());
            assertEquals(boardLog.identity(), boardLog.identity());
        }

        @Test
        public void ensureNullOldContentIsNotAccepted(){
            // Arrange
            Board board = BoardDataSource.boardWithPostIts();
            BoardLogComponent component = BoardLogComponent.POST_IT;
            BoardOperationType operation = BoardOperationType.UPDATE_POSTIT;
            ECourseUser user = UserDataSource.getTestStudent1();
            int oldColumn = 1;
            int oldRow = 1;
            int newColumn = 2;
            int newRow = 2;
            String newContent = "New content";

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> new BoardLog(board, component,
                    operation, user, oldColumn, oldRow, newColumn, newRow, null, newContent));
        }

        @Test
        public void ensureNullNewContentIsNotAccepted(){
            // Arrange
            Board board = BoardDataSource.boardWithPostIts();
            BoardLogComponent component = BoardLogComponent.POST_IT;
            BoardOperationType operation = BoardOperationType.UPDATE_POSTIT;
            ECourseUser user = UserDataSource.getTestStudent1();
            int oldColumn = 1;
            int oldRow = 1;
            int newColumn = 2;
            int newRow = 2;
            String oldContent = "Old content";

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> new BoardLog(board, component,
                    operation, user, oldColumn, oldRow, newColumn, newRow, oldContent, null));
        }

        @Test
        public void ensureNullBoardIsNotAcceptedInUpdateLog(){
            // Arrange
            BoardLogComponent component = BoardLogComponent.POST_IT;
            BoardOperationType operation = BoardOperationType.UPDATE_POSTIT;
            ECourseUser user = UserDataSource.getTestStudent1();
            int oldColumn = 1;
            int oldRow = 1;
            int newColumn = 2;
            int newRow = 2;
            String oldContent = "Old content";
            String newContent = "New content";

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> new BoardLog(null, component,
                    operation, user, oldColumn, oldRow, newColumn, newRow, oldContent, newContent));
        }

        @Test
        public void ensureNullComponentIsNotAcceptedInUpdateLog(){
            // Arrange
            Board board = BoardDataSource.boardWithPostIts();
            BoardOperationType operation = BoardOperationType.UPDATE_POSTIT;
            ECourseUser user = UserDataSource.getTestStudent1();
            int oldColumn = 1;
            int oldRow = 1;
            int newColumn = 2;
            int newRow = 2;
            String oldContent = "Old content";
            String newContent = "New content";

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> new BoardLog(board, null,
                    operation, user, oldColumn, oldRow, newColumn, newRow, oldContent, newContent));
        }

        @Test
        public void ensureNullOperationIsNotAcceptedInUpdateLog(){
            // Arrange
            Board board = BoardDataSource.boardWithPostIts();
            BoardLogComponent component = BoardLogComponent.POST_IT;
            ECourseUser user = UserDataSource.getTestStudent1();
            int oldColumn = 1;
            int oldRow = 1;
            int newColumn = 2;
            int newRow = 2;
            String oldContent = "Old content";
            String newContent = "New content";

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> new BoardLog(board, component,
                    null, user, oldColumn, oldRow, newColumn, newRow, oldContent, newContent));
        }

        @Test
        public void ensureNullUserIsNotAcceptedInUpdateLog(){
            // Arrange
            Board board = BoardDataSource.boardWithPostIts();
            BoardLogComponent component = BoardLogComponent.POST_IT;
            BoardOperationType operation = BoardOperationType.UPDATE_POSTIT;
            int oldColumn = 1;
            int oldRow = 1;
            int newColumn = 2;
            int newRow = 2;
            String oldContent = "Old content";
            String newContent = "New content";

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> new BoardLog(board, component,
                    operation, null, oldColumn, oldRow, newColumn, newRow, oldContent, newContent));
        }

        @Test
        public void ensureToStringIsWorking() {
            // Arrange
            Board board = BoardDataSource.boardWithPostIts();
            BoardLogComponent component = BoardLogComponent.POST_IT;
            BoardOperationType operation = BoardOperationType.ARCHIVE_BOARD;
            ECourseUser user = UserDataSource.getTestStudent1();

            BoardLog boardLog = new BoardLog(board, component, operation, user);

            // Act & Assert
            Assertions.assertDoesNotThrow(() -> {
                boardLog.toString();
            });

        }

        @Test
        public void ensureSameAsWorks() {
            // Arrange
            Board board = BoardDataSource.boardWithPostIts();
            BoardLogComponent component = BoardLogComponent.POST_IT;
            BoardOperationType operation = BoardOperationType.ARCHIVE_BOARD;
            ECourseUser user = UserDataSource.getTestStudent1();

            BoardLog boardLog = new BoardLog(board, component, operation, user);

            // Act & Assert
            Assertions.assertEquals(boardLog.sameAs(boardLog), true);

        }


}
