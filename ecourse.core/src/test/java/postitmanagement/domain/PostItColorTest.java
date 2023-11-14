package postitmanagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostItColorTest {

    @Test
    void ensureValidColorIsCreated() {
        PostItColor color = new PostItColor("red");
        assertNotNull(color);
    }

    @Test
    void ensureEmptyColorIsNotCreated() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PostItColor("");
        });
    }

    @Test
    void ensureNullColorIsNotCreated() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PostItColor(null);
        });
    }



}