import coursemanagement.application.*;
import coursemanagement.application.coursestate.*;
import coursemanagement.domain.*;
import eapli.framework.actions.*;
import persistence.*;

public class CourseBootstrapper implements Action {

    private final CreateCourseController createCourseController =
            new CreateCourseController(PersistenceContext.repositories().courses());
    private final OpenCourseController openCourseController =
            new OpenCourseController(PersistenceContext.repositories().courses(),
                    new ListCourseByStateService(PersistenceContext.repositories().courses()));
    private final OpenEnrollmentsController openEnrollmentsController =
            new OpenEnrollmentsController(PersistenceContext.repositories().courses(),
                    new ListCourseByStateService(PersistenceContext.repositories().courses()));
    private final CloseEnrollmentsController closeEnrollmentsController =
            new CloseEnrollmentsController(PersistenceContext.repositories().courses(),
                    PersistenceContext.repositories().enrolledStudents(),
                    new ListCourseByStateService(PersistenceContext.repositories().courses()));
    private final CloseCourseController closeCourseController =
            new CloseCourseController(PersistenceContext.repositories().courses(),
                    new ListCourseByStateService(PersistenceContext.repositories().courses()));

    @Override
    public boolean execute() {

        // ---- Close Courses ----
        createCourseController.createCourse("C++",
                "C++ Course",
                "Don't Learn C++",
                0,
                80);

        createCourseController.createCourse("C#",
                "C# Course",
                "Learn C# - For Dummies",
                10,
                20);



        // ---- Open Courses ----
        Course open1 = createCourseController.createCourse("Data_Structures",
                "Data Structures and Algorithms",
                "Learn the fundamentals.",
                5,
                10);
        openCourseController.changeCourseState(open1);

        Course open2 = createCourseController.createCourse("DevOps",
                "DevOps Fundamentals",
                "Implementing DevOps.",
                30,
                60);
        openCourseController.changeCourseState(open2);


        // ---- Enroll Courses ----
        Course enroll1 = createCourseController.createCourse("PYTHON",
                "Python Course",
                "Learn Python - Get a Job",
                100,
                300);
        openCourseController.changeCourseState(enroll1);
        openEnrollmentsController.changeCourseState(enroll1);

        Course enroll2 = createCourseController.createCourse("JAVA",
                "Java Course",
                "Learn Java - The Hard Way",
                1 ,
                100);
        openCourseController.changeCourseState(enroll2);
        openEnrollmentsController.changeCourseState(enroll2);


        // ---- In Progress ----
        Course inProgress1 = createCourseController.createCourse("SQL",
                "SQL Fundamentals",
                "Learn the basics of SQL for database management.",
                0,
                50);
        openCourseController.changeCourseState(inProgress1);
        openEnrollmentsController.changeCourseState(inProgress1);
        closeEnrollmentsController.changeCourseState(inProgress1);

        Course inProgress2 = createCourseController.createCourse("Rust",
                "Rust Course",
                "UnRust Yourself",
                0,
                10);
        openCourseController.changeCourseState(inProgress2);
        openEnrollmentsController.changeCourseState(inProgress2);
        closeEnrollmentsController.changeCourseState(inProgress2);


        // ---- Closed Enrollments ----
        Course Closed1 = createCourseController.createCourse("WEB",
                "HTML/CSS Course",
                "Create your own website", 0,
                400);
        openCourseController.changeCourseState(Closed1);
        openEnrollmentsController.changeCourseState(Closed1);
        closeEnrollmentsController.changeCourseState(Closed1);
        closeCourseController.changeCourseState(Closed1);

        Course Closed2 = createCourseController.createCourse("Swift",
                "Swift for iOS Development",
                "Learn how to build iOS apps.",
                0,
                100);
        openCourseController.changeCourseState(Closed2);
        openEnrollmentsController.changeCourseState(Closed2);
        closeEnrollmentsController.changeCourseState(Closed2);
        closeCourseController.changeCourseState(Closed2);



        return true;
    }
}
