package coursemanagement.application.listcourses;

import coursemanagement.domain.Course;
import coursemanagement.repository.CourseRepository;

public class ListManagerCoursesController {

    private final CourseRepository courseRepository;

    /*
     * Instantiates a new ListManagerAvailableCoursesController.
     *
     * @param courseRepository represents the course repository
     */
    public ListManagerCoursesController(CourseRepository courseRepository){
        if(courseRepository == null){
            throw new IllegalArgumentException("The Course Repository cannot be null.");
        }
        this.courseRepository = courseRepository;
    }

    /**
     * Lists all courses.
     *
     * @return the iterable with the courses
     */
    public Iterable<Course> findAllCourses(){
        return courseRepository.findAll();
    }


}