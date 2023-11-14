package coursemanagement.application.listcourses;

import coursemanagement.domain.Course;
import coursemanagement.domain.CourseState;
import coursemanagement.repository.CourseRepository;
import eapli.framework.validations.*;
import ecourseusermanagement.domain.ECourseUser;

import java.util.Collections;
import java.util.List;

public class StudentCoursesProvider implements IStudentCoursesProvider {

    private final CourseRepository courseRepository;

    public StudentCoursesProvider(CourseRepository courseRepository){
        Preconditions.noneNull(courseRepository);
        this.courseRepository = courseRepository;
    }


    /**
     * Finds all courses a student is enrolled in
     *
     * @param eCourseUser - Session user
     * @return all courses a student is enrolled in
     */
    public Iterable<Course> provideStudentCourses(ECourseUser eCourseUser) {
        return courseRepository.findStudentCourses(eCourseUser);
    }


    /**
     * Finds all courses a student is enrolled in that are in progress
     *
     * @param eCourseUser - Session user
     * @return all courses a student is enrolled in that are active
     */
    public Iterable<Course> provideStudentInProgressCourses(ECourseUser eCourseUser) {
        List<Course> inProgressCourses = (List<Course>) courseRepository.findCoursesByState(CourseState.IN_PROGRESS);
        List<Course> studentCourses = (List<Course>) provideStudentCourses(eCourseUser);

        studentCourses.retainAll(inProgressCourses);
        return inProgressCourses;
    }


    /**
     * Finds all courses a student can enroll in
     *
     * @return all courses a student can enroll in
     */
    public Iterable<Course> provideCoursesToEnroll() {
        return courseRepository.findCoursesByState(CourseState.ENROLL);
    }

    @Override
    public Iterable<Course> provideStudentAvailableCourses(ECourseUser eCourseUser) {
        List<Course> studentCourses = (List<Course>) provideStudentCourses(eCourseUser);
        List<Course> coursesToEnroll = (List<Course>) provideCoursesToEnroll();
        return filterCourses(studentCourses, coursesToEnroll);
    }

    private List<Course> filterCourses(List<Course> studentCourses, List<Course> coursesToEnroll) {
        coursesToEnroll.removeAll(studentCourses);
        studentCourses.addAll(coursesToEnroll);
        return studentCourses;
    }


}
