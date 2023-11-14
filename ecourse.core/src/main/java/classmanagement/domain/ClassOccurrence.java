package classmanagement.domain;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;

@Entity
public class ClassOccurrence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime newDate;

    private LocalDateTime oldDate;

    private Integer newDuration;

    /**
     * Instantiates a new Class occurrence.
     *
     */
    public ClassOccurrence(LocalDateTime newDate, LocalDateTime oldDate, Integer newDuration) {
        if(newDate == null)
            throw new IllegalArgumentException("The new date cannot be null.");

        if(oldDate == null)
            throw new IllegalArgumentException("The old date cannot be null.");

        if(newDate.isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("The new date cannot be before the current date");

        if(newDuration == null)
            throw new IllegalArgumentException("The new duration cannot be null.");

        if(newDuration <= 0)
            throw new IllegalArgumentException("The new duration cannot be negative or zero.");

        this.newDate = newDate;
        this.oldDate = oldDate;
        this.newDuration = newDuration;
    }

    protected ClassOccurrence() {
        // for ORM
    }

    public void update(LocalDateTime newDate, Integer newDuration) {
        if(newDate == null)
            throw new IllegalArgumentException("The new date cannot be null.");

        if(newDate.isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("The new date cannot be before the current date.");

        if(newDuration == null)
            throw new IllegalArgumentException("The new duration cannot be null.");

        if(newDuration <= 0)
            throw new IllegalArgumentException("The new duration cannot be negative or zero.");

        this.newDate = newDate;
        this.newDuration = newDuration;
    }

    public LocalDateTime getNewDate() {
        return newDate;
    }

    public LocalDateTime getOldDate() {
        return oldDate;
    }

    public Integer getNewDuration() {
        return newDuration;
    }

    @Override
    public String toString() {
        return "Class Occurrence From " + newDate;
    }

}
