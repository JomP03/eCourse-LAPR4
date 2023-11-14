package postitmanagement.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Column;
import java.time.LocalDateTime;

public class PostItTimestamp implements ValueObject {

    private static final long serialVersionUID = 1L;

    @Column
    private LocalDateTime timestamp;

    protected PostItTimestamp() {
        // For ORM
    }

    /**
     * Instantiates a new Board post it timestamp.
     */
    public PostItTimestamp(LocalDateTime timestamp) {
        // Make sure that the timestamp is not null
        if (timestamp == null) {
            throw new IllegalArgumentException("The timestamp cannot be null.");
        }

        // Make sure that the timestamp is not in the future
        if (timestamp.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("The timestamp cannot be in the future.");
        }

        this.timestamp = timestamp;
    }
}