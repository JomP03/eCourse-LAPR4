package postitmanagement.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Column;

public class PostItContent implements ValueObject {

    private static final long serialVersionUID = 1L;

    @Column
    private String content;

    protected PostItContent() {
        // for ORM
    }

    /**
     * Instantiates a new Board post it content.
     *
     * @param content the content
     *
     * @throws IllegalArgumentException if the content is null
     * @throws IllegalArgumentException if the content is empty
     */
    public PostItContent(String content) {
        // Verify if the content is null
        if (content == null) {
            throw new IllegalArgumentException("A valid content must be provided");
        }

        // Verify if the content is empty
        if (content.isEmpty()) {
            throw new IllegalArgumentException("A valid content must be provided");
        }

        this.content = content;
    }

    /**
     * Post-it content value.
     */
    public String content() {
        return this.content;
    }
}