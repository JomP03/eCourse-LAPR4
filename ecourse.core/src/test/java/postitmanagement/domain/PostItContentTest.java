package postitmanagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostItContentTest {

    @Test
    void ensureValidPostItContentIsCreated() {
        PostItContent postItContent = new PostItContent("content");
        assertNotNull(postItContent);
    }

    @Test
    void ensureValidPostItContentIsCreatedWithExpectedContent() {
        String expectedContent = "content";
        PostItContent postItContent = new PostItContent(expectedContent);
        assertEquals(expectedContent, postItContent.content());
    }

}