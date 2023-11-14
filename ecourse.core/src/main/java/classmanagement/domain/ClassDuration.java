package classmanagement.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ClassDuration {

    @Column
    private Integer duration;

    /**
     * Instantiates a new Class Duration
     *
     * @param duration
     * @throws IllegalArgumentException if the duration is less than or equal to 0
     */
    public ClassDuration(Integer duration) {
        if(duration == null)
            throw new IllegalArgumentException("Duration must not be null");
        if(duration <= 0)
            throw new IllegalArgumentException("Duration must be greater than 0");
        this.duration = duration;
    }

    protected ClassDuration() {
        // for ORM
    }


    /**
     * @return the duration
     */
    public Integer getClassDuration() {
        return duration;
    }

    /**
     * Updates the duration
     */
    public void updateDuration(Integer duration) {
        if (duration == null)
            throw new IllegalArgumentException("Duration must not be null");
        if (duration <= 0)
            throw new IllegalArgumentException("Duration must be greater than 0");
        this.duration = duration;
    }
}
