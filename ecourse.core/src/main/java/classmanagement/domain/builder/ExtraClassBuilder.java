package classmanagement.domain.builder;

import classmanagement.domain.ExtraClass;
import classmanagement.domain.RecurrentClass;
import classmanagement.repository.ClassRepository;
import coursemanagement.domain.Course;
import ecourseusermanagement.domain.ECourseUser;

import java.time.LocalDateTime;
import java.util.List;

public class ExtraClassBuilder {

    private final ClassRepository classRepository;

    private String classTitle;

    private Integer classDuration;

    private ECourseUser teacher;

    private Course course;

    private LocalDateTime extraClassDate;

    private List<ECourseUser> extraClassParticipants;

    public ExtraClassBuilder(ClassRepository classRepository) {

        // check if the class repository is null
        if(classRepository == null)
            throw new IllegalArgumentException("The classRepository cannot be null.");

        this.classRepository = classRepository;
    }


    /**
     * With class title recurrent class builder.
     * @param classTitle the class title
     * @return the extra class builder
     */
    public ExtraClassBuilder withClassTitle(String classTitle) {
        if(classTitle == null || classTitle.isBlank())
            throw new IllegalArgumentException("The class title cannot be null or empty.");
        this.classTitle = classTitle;
        return this;
    }

    /**
     * With class duration extra class builder.
     * @param classDuration the class duration
     * @return the extra class builder
     */
    public ExtraClassBuilder withClassDuration(Integer classDuration) {
        if(classDuration == null || classDuration <= 0)
            throw new IllegalArgumentException("The class duration cannot be null or empty.");
        this.classDuration = classDuration;
        return this;
    }

    /**
     * With teacher extra class builder.
     * @param teacher the teacher
     * @return the extra class builder
     */
    public ExtraClassBuilder withTeacher(ECourseUser teacher) {
        if(teacher == null)
            throw new IllegalArgumentException("The teacher cannot be null.");
        this.teacher = teacher;
        return this;
    }

    /**
     * With course extra class builder.
     * @param course the course
     * @return the extra class builder
     */
    public ExtraClassBuilder withCourse(Course course) {
        if(course == null)
            throw new IllegalArgumentException("The course cannot be null.");
        this.course = course;
        return this;
    }

    /**
     * With extra class date extra class builder.
     * @param extraClassDate the extra class date
     * @return the extra class builder
     */
    public ExtraClassBuilder withExtraClassDate(LocalDateTime extraClassDate) {
        if(extraClassDate == null)
            throw new IllegalArgumentException("The extra class date cannot be null.");
        if(extraClassDate.isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("The extra class date cannot be in the past.");
        this.extraClassDate = extraClassDate;
        return this;
    }

    /**
     * With the class participants with extra class builder.
     * @param extraClassParticipants the extra class week day
     * @return the extra class builder
     */
    public ExtraClassBuilder withExtraClassParticipants(List<ECourseUser> extraClassParticipants) {
        if(extraClassParticipants == null)
            throw new IllegalArgumentException("The extra class participants cannot be null or empty.");
        this.extraClassParticipants = extraClassParticipants;
        return this;
    }

    /**
     * Build extra class.
     * @return the extra class
     */
    public ExtraClass build() {
        RecurrentClass recurrentClass = classRepository.findRecurrentClassByTitle(this.classTitle);
        ExtraClass extraClass = classRepository.findExtraClassByTitle(this.classTitle);
        if (recurrentClass != null || extraClass != null)
            throw new IllegalArgumentException("Class title must be unique");

        return new ExtraClass(classTitle, classDuration, extraClassDate, course, teacher, extraClassParticipants);
    }

}
