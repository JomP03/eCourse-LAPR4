package boardlogmanagement.domain;

import eapli.framework.domain.model.*;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class BoardLogTimestamp implements ValueObject {

    private LocalDateTime timestamp;

    public BoardLogTimestamp() {
        this.timestamp = LocalDateTime.now();
    }

    public String toString() {
        return this.timestamp.toString();
    }
}
