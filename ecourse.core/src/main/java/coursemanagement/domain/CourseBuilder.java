package coursemanagement.domain;

import coursemanagement.repository.*;

public class CourseBuilder {

    CourseRepository courseRepo;

    private String courseCode;
    private String courseName;
    private String courseDescription;
    private int minNrEnrolledStudents;
    private int maxNrEnrolledStudents;


    /**
     * @param courseRepo the repository to use
     */
    public CourseBuilder(CourseRepository courseRepo) {
        this.courseRepo = courseRepo;
    }


    /**
     * Adding a course code to the course
     *
     * @param courseCode the courseCode to set
     * @return the builder
     */
    public CourseBuilder withCourseCode(String courseCode) {
        this.courseCode = courseCode;
        return this;
    }


    /**
     * Adding a course name to the course
     *
     * @param courseName the courseName to set
     * @return the builder
     */
    public CourseBuilder withCourseName(String courseName) {
        this.courseName = courseName;
        return this;
    }


    /**
     * Adding a course description to the course
     *
     * @param courseDescription the courseDescription to set
     * @return the builder
     */
    public CourseBuilder withCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
        return this;
    }


    /**
     * Adding a minimum number of enrolled students to the course
     *
     * @param minNrEnrolledStudents the maxNrEnrolledStudents to set
     * @return the builder
     */
    public CourseBuilder withMinNrEnrolledStudents(int minNrEnrolledStudents) {
        this.minNrEnrolledStudents = minNrEnrolledStudents;
        return this;
    }


    /**
     * Adding a maximum number of enrolled students to the course
     *
     * @param maxNrEnrolledStudents the maxNrEnrolledStudents to set
     * @return the builder
     */
    public CourseBuilder withMaxNrEnrolledStudents(int maxNrEnrolledStudents) {
        this.maxNrEnrolledStudents = maxNrEnrolledStudents;
        return this;
    }


    /**
     * @return the built course
     */
    public Course build() {

        if (courseRepo.ofIdentity(CourseCode.valueOf(courseCode)).isPresent())
            throw new IllegalArgumentException("Course code already exists");

        return new Course(courseCode, courseName, courseDescription, minNrEnrolledStudents, maxNrEnrolledStudents);
    }
}
