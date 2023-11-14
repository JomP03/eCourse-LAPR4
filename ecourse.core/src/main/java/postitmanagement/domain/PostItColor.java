package postitmanagement.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Column;

public class PostItColor implements ValueObject {

    private static final long serialVersionUID = 1L;

    @Column
    private String color;

    protected PostItColor() {
        // for ORM
    }

    /**
     * Instantiates a new Board post it color.
     *
     * @param color the color
     *
     * @throws IllegalArgumentException if the color is null
     * @throws IllegalArgumentException if the color is empty
     */
    public PostItColor(String color) {
        // Verify if the color is null
        if (color == null) {
            throw new IllegalArgumentException("A valid color must be provided");
        }

        // Verify if the color is empty
        if (color.isEmpty()) {
            throw new IllegalArgumentException("A valid color must be provided");
        }

        this.color = color;
    }
}