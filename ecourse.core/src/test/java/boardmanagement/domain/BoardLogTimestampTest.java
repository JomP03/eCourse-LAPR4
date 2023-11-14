package boardmanagement.domain;

import boardlogmanagement.domain.BoardLogTimestamp;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardLogTimestampTest {

    @Test
    public void ensureValidTimeStampIsCreated() {
        BoardLogTimestamp timestamp = new BoardLogTimestamp();
        assertNotNull(timestamp);
    }

    @Test
    public void ensureToStringReturnsTimestamp() {
        BoardLogTimestamp timestamp = new BoardLogTimestamp();
        String timestampString = timestamp.toString();
        assertNotNull(timestampString);
    }
}
