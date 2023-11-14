package classmanagement.domain;

import coursemanagement.domain.Course;
import eapli.framework.domain.model.DomainEntities;
import ecourseusermanagement.domain.ECourseUser;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ExtraClass extends Class{

    @Embedded
    private ExtraClassDate extraClassDate;

    @OneToMany (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ECourseUser> extraClassParticipants;

    /**
     * Instantiates a new Extra class.
     *
     * @param classTitle the class title
     * @param classDuration the class duration
     * @param extraClassDate the extra class date
     * @param classCourse the class course
     * @param classTeacher the class teacher
     */
    public ExtraClass(String classTitle,Integer classDuration, LocalDateTime extraClassDate, Course classCourse, ECourseUser classTeacher, List<ECourseUser> extraClassParticipants) {
        super(classTitle, classDuration, classCourse, classTeacher);
        if(extraClassDate == null)
            throw new IllegalArgumentException("The extra class date cannot be null.");
        if(extraClassParticipants == null)
            throw new IllegalArgumentException("The extra class participants cannot be null.");
        this.extraClassDate = new ExtraClassDate(extraClassDate);
        this.extraClassParticipants = extraClassParticipants;
    }

    protected ExtraClass() {
        // for ORM
    }

    /**
     * Gets the extra class date.
     *
     * @return the extra class date
     */
    public LocalDateTime getsDate() {
        return extraClassDate.getDate();
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this, other);
    }
}
