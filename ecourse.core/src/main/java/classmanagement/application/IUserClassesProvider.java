package classmanagement.application;

import classmanagement.domain.ExtraClass;
import classmanagement.domain.RecurrentClass;
import coursemanagement.domain.Course;
import ecourseusermanagement.domain.ECourseUser;

import java.time.LocalDateTime;

/**
 * The interface List user classes.
 */
public interface IUserClassesProvider {

    /**
     * List user recurrent classes.
     *
     * @param dateTime     the meeting to check date
     * @param user            the user to check
     * @return the iterable
     */
    Iterable<RecurrentClass> listUserRecurrentClasses(Course course, LocalDateTime dateTime,
                                                      ECourseUser user);

    /**
     * List user extra classes.
     *
     * @param dateTime     the meeting to check date
     * @param user            the user to check
     * @return the iterable
     */
    Iterable<ExtraClass> listUserExtraClasses(Course course, LocalDateTime dateTime,
                                              ECourseUser user);
}
