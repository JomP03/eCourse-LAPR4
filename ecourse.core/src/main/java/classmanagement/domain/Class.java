package classmanagement.domain;

import coursemanagement.domain.Course;
import eapli.framework.domain.model.AggregateRoot;
import ecourseusermanagement.domain.ECourseUser;

import javax.persistence.*;

@Entity
public abstract class Class implements AggregateRoot<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ClassTitle classTitle;

    @Embedded
    private ClassDuration classDuration;

    @OneToOne(cascade = CascadeType.ALL)
    private Course classCourse;

    @OneToOne(cascade = CascadeType.ALL)
    private ECourseUser classTeacher;

    /**
     * Instantiates a new Class
     * @param classTitle the class title
     * @param classDuration the class duration
     * @param classCourse the class course
     * @param classTeacher the class teacher
     * @throws IllegalArgumentException if the classCourse is null
     */
    public Class(String classTitle, Integer classDuration, Course classCourse, ECourseUser classTeacher) {
        if(classCourse == null){
            throw new IllegalArgumentException("Invalid course");
        }

        if(classTeacher == null){
            throw new IllegalArgumentException("Invalid teacher");
        }

        this.classTitle = new ClassTitle(classTitle);
        this.classDuration = new ClassDuration(classDuration);
        this.classCourse = classCourse;
        this.classTeacher = classTeacher;
    }

    protected Class() {
        // for ORM
    }

    /**
     * Gets the class title
     * @return the class title
     */
    public Integer getClassDuration() {
        return classDuration.getClassDuration();
    }

    /**
     * Gets the class duration
     * @return the class duration
     */
    public String getClassTitle() {
        return classTitle.getClassTitle();
    }

    /**
     * Gets the class course
     * @return the class course
     */
    public Course getClassCourse() {
        return classCourse;
    }

    /**
     * Updates the class duration
     */
    public void updateClassDuration(Integer classDuration) {
        this.classDuration.updateDuration(classDuration);
    }

    /**
     * Gets the class identity
     * @return the id
     */
    public Long identity() {
        return id;
    }


    @Override
    public String toString() {
        return classTitle.getClassTitle() + " - " + classDuration.getClassDuration() + " minutes, from " + classCourse.identity().toString();
    }

}
