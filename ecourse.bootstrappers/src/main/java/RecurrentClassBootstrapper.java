import classmanagement.domain.RecurrentClassWeekDay;
import classmanagement.domain.service.ClassScheduler;
import coursemanagement.domain.Course;
import eapli.framework.actions.*;
import ecourseusermanagement.domain.ECourseUser;
import persistence.*;

import java.util.ArrayList;
import java.util.List;

public class RecurrentClassBootstrapper implements Action {

    private final ClassScheduler classScheduler = new ClassScheduler(PersistenceContext.repositories().classes());

    @Override
    public boolean execute(){

        ECourseUser teacher = PersistenceContext.repositories().eCourseUsers().findByEmail("johndoe@gmail.com").orElse(null);

        ECourseUser teacher2 = PersistenceContext.repositories().eCourseUsers().findByEmail("janedoe@gmail.com").orElse(null);

        ECourseUser teacher3 = PersistenceContext.repositories().eCourseUsers().findByEmail("anasantos@gmail.com").orElse(null);

        Course course1 = PersistenceContext.repositories().courses().findByCode("C++");

        Course course2 = PersistenceContext.repositories().courses().findByCode("SQL");

        Course course3 = PersistenceContext.repositories().courses().findByCode("JAVA");

        if(teacher == null || teacher2 == null || teacher3 == null || course1 == null || course2 == null || course3 == null){
            System.out.println("Cant bootstrap recurrent classes. Please check if the used teacher emails and course codes exist in the other bootstraps.\n");
            return false;
        }

        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);

        // create recurrent classes for course 1
        classScheduler.scheduleRecurrentClass(
                "MondayC++",
                60,
                "14:00",
                 RecurrentClassWeekDay.MONDAY,
                 course1,
                 teacher,
                 courses
        );

        classScheduler.scheduleRecurrentClass(
                "ThursdayC++",
                90,
                "10:00",
                 RecurrentClassWeekDay.THURSDAY,
                 course1,
                 teacher,
                 courses
        );

        classScheduler.scheduleRecurrentClass(
                "FridayC++",
                60, "18:00",
                 RecurrentClassWeekDay.FRIDAY,
                 course1,
                 teacher2,
                 courses
        );

        classScheduler.scheduleRecurrentClass(
                "WednesdayC++",
                60,
                "12:00",
                 RecurrentClassWeekDay.WEDNESDAY,
                 course1,
                 teacher3,
                 courses
        );


        // create recurrent classes for course 2

        classScheduler.scheduleRecurrentClass(
                "SQLMonday",
                60,
                "8:30",
                 RecurrentClassWeekDay.MONDAY,
                 course2,
                 teacher2,
                 courses
        );

        classScheduler.scheduleRecurrentClass(
                "SQLTuesday",
                60,
                "19:00",
                 RecurrentClassWeekDay.TUESDAY,
                 course2,
                 teacher2,
                 courses
        );

        classScheduler.scheduleRecurrentClass(
                "SQLThursday",
                120,
                "12:00",
                 RecurrentClassWeekDay.THURSDAY,
                 course2,
                 teacher,
                 courses
        );

        classScheduler.scheduleRecurrentClass(
                "SQLFriday",
                60,
                "14:00",
                 RecurrentClassWeekDay.FRIDAY,
                 course2,
                 teacher2,
                 courses
        );

        // create recurrent classes for course 3

        classScheduler.scheduleRecurrentClass(
                "FridayJava",
                60,
                "14:00",
                 RecurrentClassWeekDay.FRIDAY,
                 course3,
                 teacher3,
                 courses
        );

        classScheduler.scheduleRecurrentClass(
                "TuesdayJava",
                90,
                "10:00",
                 RecurrentClassWeekDay.TUESDAY,
                 course3,
                 teacher3,
                 courses
        );

        classScheduler.scheduleRecurrentClass(
                "JavaWednesday",
                180,
                "14:00",
                 RecurrentClassWeekDay.WEDNESDAY,
                 course3,
                 teacher,
                 courses
        );

        return true;
    }

}
