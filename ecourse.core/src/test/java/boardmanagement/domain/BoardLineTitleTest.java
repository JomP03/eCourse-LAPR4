package boardmanagement.domain;

import boardmanagement.domain.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardLineTitleTest {

    @Test
    public void ensureValidBoardLineTitleIsAccepted(){
        // Arrange
        String boardLineTitleString = "Board Line Test Title";

        // Act
        BoardLineTitle boardLineTitle = new BoardLineTitle(boardLineTitleString);

        // Assert
       assertEquals(boardLineTitle.toString(), boardLineTitleString);
    }

    @Test
    public void ensureNullBoardLineTitleIsRejected(){
        // Arrange
        String boardLineTitleString = null;

        // Assert
        assertThrows(NullPointerException.class, () -> new BoardLineTitle(boardLineTitleString));
    }
}
