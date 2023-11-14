package postitmanagement.domain;

import boardmanagement.domain.*;
import ecourseusermanagement.domain.*;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PostItTest {

    @Test
    void createValidPostIt() {
        // Arrange
        BoardCell boardCell = BoardDataSource.boardCell();
        String boardPostItContent = "This is a valid post-it content";
        String boardPostItColor = "#FFFFFF";

        // Act
        PostIt postIt = new PostIt(boardCell, boardPostItContent, boardPostItColor, UserDataSource.getTestManager1());

        // Assert
        assertNotNull(postIt);
    }

    @Test
    void ensurePostItIsCreatedWithExpectedContent() {
        // Arrange
        BoardCell boardCell = BoardDataSource.boardCell();
        String boardPostItContent = "This is a valid post-it content";
        String boardPostItColor = "#FFFFFF";

        // Act
        PostIt postIt = new PostIt(boardCell, boardPostItContent, boardPostItColor, UserDataSource.getTestManager1());

        // Assert
        assertEquals(boardPostItContent, postIt.currentContent().content());
    }

    @Test
    void ensurePostItIsCreatedWithEmptyPreviousContent() {
        // Arrange
        BoardCell boardCell = BoardDataSource.boardCell();
        String boardPostItContent = "This is a valid post-it content";
        String boardPostItColor = "#FFFFFF";

        // Act
        PostIt postIt = new PostIt(boardCell, boardPostItContent, boardPostItColor, UserDataSource.getTestManager1());

        // Assert
        assertEquals(Optional.empty(), postIt.previousContent());
    }


    @Test
    void ensurePostItIsNotCreatedWithNullBoardCell() {
        // Arrange
        String boardPostItContent = "This is a valid post-it content";
        String boardPostItColor = "#FFFFFF";

        // Assert
        assertThrows(IllegalArgumentException.class, () ->
                new PostIt(null, boardPostItContent, boardPostItColor, UserDataSource.getTestManager1()));
    }

    @Test
    void ensurePostItIsNotCreatedWithEmptyContent() {
        // Arrange
        BoardCell boardCell = BoardDataSource.boardCell();
        String boardPostItContent = "";
        String boardPostItColor = "#FFFFFF";

        // Assert
        assertThrows(IllegalArgumentException.class, () ->
                new PostIt(boardCell, boardPostItContent, boardPostItColor, UserDataSource.getTestManager1()));
    }

    @Test
    void ensurePostItIsNotCreatedWithNullContent() {
        // Arrange
        BoardCell boardCell = BoardDataSource.boardCell();
        String boardPostItColor = "#FFFFFF";

        // Assert
        assertThrows(IllegalArgumentException.class, () ->
                new PostIt(boardCell, null, boardPostItColor, UserDataSource.getTestManager1()));
    }

    @Test
    void ensurePostItIsNotCreatedWithEmptyColor() {
        // Arrange
        BoardCell boardCell = BoardDataSource.boardCell();
        String boardPostItContent = "This is a valid post-it content";
        String boardPostItColor = "";

        // Assert
        assertThrows(IllegalArgumentException.class, () ->
                new PostIt(boardCell, boardPostItContent, boardPostItColor, UserDataSource.getTestManager1()));
    }

    @Test
    void ensurePostItIsNotCreatedWithNullColor() {
        // Arrange
        BoardCell boardCell = BoardDataSource.boardCell();
        String boardPostItContent = "This is a valid post-it content";

        // Assert
        assertThrows(IllegalArgumentException.class, () ->
                new PostIt(boardCell, boardPostItContent, null, UserDataSource.getTestManager1()));
    }

    @Test
    void ensurePostItIsNotCreatedWithNullCreator() {
        // Arrange
        BoardCell boardCell = BoardDataSource.boardCell();
        String boardPostItContent = "This is a valid post-it content";
        String boardPostItColor = "#FFFFFF";

        // Assert
        assertThrows(IllegalArgumentException.class, () ->
                new PostIt(boardCell, boardPostItContent, boardPostItColor, null));
    }

    @Test
    void ensurePostItIsChanged() {
        // Arrange
        BoardCell boardCell = BoardDataSource.boardCell();
        String boardPostItContent = "This is a valid post-it content";
        String boardPostItColor = "#FFFFFF";
        PostIt postIt = new PostIt(boardCell, boardPostItContent,
                boardPostItColor, UserDataSource.getTestStudent1());

        // Act
        String newBoardPostItContent = "This is a new valid post-it content";
        postIt.changeContent(UserDataSource.getTestStudent1(), boardPostItContent,
                newBoardPostItContent, boardPostItColor);

        // Assert
        assertEquals(newBoardPostItContent, postIt.currentContent().content());
    }

    @Test
    void ensurePostItIsNotChangeIfUserIsNotOnwer() {
        // Arrange
        BoardCell boardCell = BoardDataSource.boardCell();
        String boardPostItContent = "This is a valid post-it content";
        String boardPostItColor = "#FFFFFF";
        PostIt postIt = new PostIt(boardCell, boardPostItContent,
                boardPostItColor, UserDataSource.getTestStudent1());

        // Act
        String newBoardPostItContent = "This is a new valid post-it content";
        assertThrows(IllegalStateException.class, () ->
                postIt.changeContent(UserDataSource.getTestStudent2(), boardPostItContent,
                newBoardPostItContent, boardPostItColor));
    }


    @Test
    void ensurePostItIsNotChangedIfContentIsNull() {
        // Arrange
        BoardCell boardCell = BoardDataSource.boardCell();
        String boardPostItContent = "This is a valid post-it content";
        PostIt postIt = new PostIt(boardCell, boardPostItContent,
                "#FFFFFF", UserDataSource.getTestStudent1());

        // Act
        assertThrows(IllegalArgumentException.class, () ->
                postIt.changeContent(UserDataSource.getTestStudent1(), boardPostItContent,
                        null, "#FFFFFF"));
    }


    @Test
    void ensurePostItIsNotChangedIfContentIsEmpty() {
        // Arrange
        BoardCell boardCell = BoardDataSource.boardCell();
        String boardPostItContent = "This is a valid post-it content";
        PostIt postIt = new PostIt(boardCell, boardPostItContent,
                "#FFFFFF", UserDataSource.getTestStudent1());

        // Act
        assertThrows(IllegalArgumentException.class, () ->
                postIt.changeContent(UserDataSource.getTestStudent1(), boardPostItContent,
                        "", "#FFFFFF"));
    }


    @Test
    void ensurePostItReplacesItsCell() {
        // Arrange
        BoardCell boardCell = BoardDataSource.boardCell();
        String boardPostItContent = "This is a valid post-it content";
        String boardPostItColor = "#FFFFFF";
        ECourseUser creator = UserDataSource.getTestStudent1();

        PostIt postIt = new PostIt(boardCell, boardPostItContent,
                boardPostItColor, creator);
        BoardCell newBoardCell = BoardDataSource.boardCell();

        // Act
        postIt.replaceCell(creator, newBoardCell);

        // Assert
        assertEquals(newBoardCell, postIt.cell());
    }


    @Test
    void ensurePostItsDoesNotReplaceItsCellIfUserIsNotOnwer() {
        // Arrange
        BoardCell boardCell = BoardDataSource.boardCell();
        String boardPostItContent = "This is a valid post-it content";
        String boardPostItColor = "#FFFFFF";
        ECourseUser creator = UserDataSource.getTestStudent1();

        PostIt postIt = new PostIt(boardCell, boardPostItContent,
                boardPostItColor, creator);
        BoardCell newBoardCell = BoardDataSource.boardCell();

        // Assert
        assertThrows(IllegalStateException.class, () ->
                postIt.replaceCell(UserDataSource.getTestStudent2(), newBoardCell));
    }

    @Test
    void ensurePostItEqualsWithDifferentInformation() {
        // Arrange
        BoardCell boardCell = BoardDataSource.boardCell();
        String boardPostItContent = "This is a valid post-it content 1";
        String color = "#FFFFFF";
        ECourseUser creator = UserDataSource.getTestStudent1();

        PostIt postIt = new PostIt(boardCell, boardPostItContent,
                color, creator);

        // Act
        PostIt postIt2 = new PostIt(boardCell, "This is a valid post-it content 2",
                color, creator);

        // Assert
        assertNotEquals(postIt, postIt2);
    }


    @Test
    void ensurePostItEqualsWithDifferentObjects() {
        // Arrange
        BoardCell boardCell = BoardDataSource.boardCell();
        String boardPostItContent = "This is a valid post-it content 1";
        String color = "#FFFFFF";
        ECourseUser creator = UserDataSource.getTestStudent1();

        PostIt postIt = new PostIt(boardCell, boardPostItContent,
                color, creator);

        // Act
        String postIt2 = "This is a valid post-it content 2";

        // Assert
        assertNotEquals(postIt, postIt2);
    }


    @Test
    void ensurePostItSameAsMethod() {
        // Arrange
        BoardCell boardCell = BoardDataSource.boardCell();
        String boardPostItContent = "This is a valid post-it content 1";
        String color = "#FFFFFF";
        ECourseUser creator = UserDataSource.getTestStudent1();

        PostIt postIt = new PostIt(boardCell, boardPostItContent,
                color, creator);

        // Act
        PostIt postIt2 = new PostIt(boardCell, "This is a valid post-it content 2",
                color, creator);

        // Assert
        assertFalse(postIt.sameAs(postIt2));
    }


    @Test
    void ensurePostItReturnsItIdentity() {
        // Arrange
        BoardCell boardCell = BoardDataSource.boardCell();
        String boardPostItContent = "This is a valid post-it content 1";
        String color = "#FFFFFF";
        ECourseUser creator = UserDataSource.getTestStudent1();

        PostIt postIt = new PostIt(boardCell, boardPostItContent,
                color, creator);

        // Act
        PostIt postIt2 = new PostIt(boardCell, "This is a valid post-it content 2",
                color, creator);

        // Assert
        assertEquals(postIt.identity(), postIt2.identity());
    }

    @Test
    void undoPostItWithPreviousContent() {
        // Arrange
        BoardCell boardCell = BoardDataSource.boardCell();
        String boardPostItContent = "This is a valid post-it content 1";
        String color = "#FFFFFF";
        ECourseUser creator = UserDataSource.getTestStudent1();

        PostIt postIt = new PostIt(boardCell, boardPostItContent,
                color, creator);

        postIt.changeContent(creator, "This is a valid post-it content 1", "teste", "#FFFFFF");

        postIt.undoLastChange(creator);

        // Assert
        assertEquals("This is a valid post-it content 1", postIt.currentContent().content());
    }

    @Test
    void undoPostItWithNoPreviousContent() {
        // Arrange
        BoardCell boardCell = BoardDataSource.boardCell();
        String boardPostItContent = "This is a valid post-it content 1";
        String color = "#FFFFFF";
        ECourseUser creator = UserDataSource.getTestStudent1();

        PostIt postIt = new PostIt(boardCell, boardPostItContent,
                color, creator);

        // Assert
        assertThrows(IllegalStateException.class,() -> postIt.undoLastChange(creator));
    }

    @Test
    void undoPostItWithInvalidBoardUser() {
        // Arrange
        BoardCell boardCell = BoardDataSource.boardCell();
        String boardPostItContent = "This is a valid post-it content 1";
        String color = "#FFFFFF";
        ECourseUser creator = UserDataSource.getTestStudent1();

        PostIt postIt = new PostIt(boardCell, boardPostItContent,
                color, creator);

        postIt.changeContent(creator, "This is a valid post-it content 1", "teste", "#FFFFFF");

        // Assert
        assertThrows(IllegalStateException.class,() -> postIt.undoLastChange(UserDataSource.getTestStudent2()));
    }
}