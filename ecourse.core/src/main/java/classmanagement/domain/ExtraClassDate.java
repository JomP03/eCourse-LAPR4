package classmanagement.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Embeddable
public class ExtraClassDate {

    @Column
    private LocalDateTime extraClassDate;


    /**
     * Instantiates a new Extra class date.
     *
     * @param date the start date
     */
    public ExtraClassDate(LocalDateTime date) {
        if (date == null)
            throw new IllegalArgumentException("Invalid date");

        if (date.isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("Invalid date, date is before today");

        this.extraClassDate = date;
    }

    protected ExtraClassDate() {
        // for ORM
    }

    /**
     * Gets the date.
     *
     * @return the start date
     */
    public LocalDateTime getDate() {
        return extraClassDate;
    }

}
