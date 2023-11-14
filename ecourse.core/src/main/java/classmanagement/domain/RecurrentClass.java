package classmanagement.domain;

import coursemanagement.domain.Course;
import eapli.framework.domain.model.DomainEntities;
import ecourseusermanagement.domain.ECourseUser;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class RecurrentClass extends Class{

    @Enumerated(EnumType.STRING)
    private RecurrentClassWeekDay recurrentClassWeekDay;

    @Embedded
    private RecurrentClassTime recurrentClassTime;

    @OneToMany (cascade = CascadeType.ALL)
    private List<ClassOccurrence> classOccurrences;

    /**
     * Instantiates a new Recurrent class.
     *
     * @param classTitle            the class title
     * @param classDuration         the class duration
     * @param recurrentClassTime    the recurrent class time
     * @param recurrentClassWeekDay the recurrent class week day
     * @param classCourse           the class course
     * @throws IllegalArgumentException if the recurrentClassWeekDay is null
     */
    public RecurrentClass(String classTitle, Integer classDuration, String recurrentClassTime, RecurrentClassWeekDay recurrentClassWeekDay, Course classCourse, ECourseUser classTeacher) {
        super(classTitle, classDuration, classCourse, classTeacher);
        if(recurrentClassWeekDay == null)
            throw new IllegalArgumentException("Invalid recurrent Class Week Day");
        this.recurrentClassWeekDay = recurrentClassWeekDay;
        this.recurrentClassTime = new RecurrentClassTime(recurrentClassTime);
        this.classOccurrences = new ArrayList<>();
    }

    protected RecurrentClass() {
        // for ORM
    }

    /**
     * Gets recurrent class week day.
     *
     * @return the recurrent class week day
     */
    public RecurrentClassWeekDay getWeekDay() {
        return recurrentClassWeekDay;
    }

    /**
     * Updates a ClassOccurrence.
     */
    public void updateClassOccurrence(ClassOccurrence classOccurrence) {
        classOccurrences.add(classOccurrence);
    }

    @Override
    public boolean sameAs(Object other) {return DomainEntities.areEqual(this, other);}

    public LocalTime getClassTime() {
        return recurrentClassTime.getTime();
    }

    public List<ClassOccurrence> getUpdatedOccurrences() {
        return classOccurrences;
    }

    /**
     * Gets the updated occurrences from a week.
     * @param startDay the start day of the week
     * @param endDay the end day of the week
     * @return the updated occurrences from a week
     */
    public List<ClassOccurrence> getUpdatedOccurrences(LocalDateTime startDay, LocalDateTime endDay) {
        List<ClassOccurrence> occurrences = new ArrayList<>();
        for(ClassOccurrence occurrence : classOccurrences) {
            if(occurrence.getOldDate().isAfter(startDay) && occurrence.getOldDate().isBefore(endDay))
                occurrences.add(occurrence);
        }
        return occurrences;
    }
}
