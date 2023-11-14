package postitmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import javax.persistence.Column;

public class PostItPreviousContent implements ValueObject {

    private static final long serialVersionUID = 1L;

    @Column
    private String boardPostItPreviousContent;

    protected PostItPreviousContent() {
        // For ORM
    }

    /**
     * Instantiates a new Board post it previous content.
     */
    public PostItPreviousContent(String boardPostItPreviousContent) {
        this.boardPostItPreviousContent = boardPostItPreviousContent;
    }

    /**
     * Previous content in the post-it.
     */
    public String previousContent() {
        return this.boardPostItPreviousContent;
    }
}
