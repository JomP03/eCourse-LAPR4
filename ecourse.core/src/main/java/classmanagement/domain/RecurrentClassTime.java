package classmanagement.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalTime;

@Embeddable
public class RecurrentClassTime {

    @Column
    private LocalTime recurrentClassStartTime;


    /**
     * Instantiates a new Recurrent class time.
     *
     * @param startTime the start time
     * @throws IllegalArgumentException if the startTime is null or is not between 0 and 24
     */
    public RecurrentClassTime(String startTime) {
        if(startTime == null || startTime.isEmpty())
            throw new IllegalArgumentException("Invalid start time");

        // check if eg: ab:it is passed
        if(!startTime.matches("\\d{1,2}:\\d{1,2}"))
            throw new IllegalArgumentException("Invalid start time");


        // if eg: 9:15 is passed, get 9 and the 15
        String[] time = startTime.split(":");
        int hour = Integer.parseInt(time[0]);
        int minutes;

        if(hour < 0 || hour > 24)
            throw new IllegalArgumentException("Invalid start time");

        minutes = Integer.parseInt(time[1]);

        this.recurrentClassStartTime = LocalTime.of(hour, minutes);
    }

    protected RecurrentClassTime() {
        // for ORM
    }

    /**
     * Gets start time.
     *
     * @return the start time
     */
    public LocalTime getTime() {
        return recurrentClassStartTime;
    }

}
