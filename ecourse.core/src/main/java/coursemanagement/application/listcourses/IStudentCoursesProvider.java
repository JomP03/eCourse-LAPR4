package coursemanagement.application.listcourses;

import coursemanagement.domain.Course;
import ecourseusermanagement.domain.ECourseUser;

public interface IStudentCoursesProvider {
    Iterable<Course> provideStudentAvailableCourses(ECourseUser eCourseUser);
}
