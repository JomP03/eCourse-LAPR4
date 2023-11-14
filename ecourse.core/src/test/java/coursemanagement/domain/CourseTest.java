package coursemanagement.domain;

import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.domain.UserDataSource;
import org.junit.Assert;
import org.junit.Test;

public class CourseTest {

    @Test
    public void ensureCourseIsCreatedWithValidInformation() {
        // Arrange
        String courseCode = "LAPR4";
        String courseName = "LAPR4";
        String courseDescription = "This is a course";
        int minNrEnrolledStudents = 10;
        int maxNrEnrolledStudents = 20;
        Course course = new Course(courseCode, courseName, courseDescription,
                minNrEnrolledStudents, maxNrEnrolledStudents);

        String stringCourse = "CourseCode: " + courseCode + " | CourseName: " + courseName
                + " | CourseState: " + "CLOSE";

        // Assert
        Assert.assertEquals(course.toString(), stringCourse);
    }


    @Test
    public void ensureCourseIsNotCreateWithEmptyCourseCode() {
        // Arrange
        String courseCode = "";
        String courseName = "LAPR4";
        String courseDescription = "This is a course";
        int minNrEnrolledStudents = 10;
        int maxNrEnrolledStudents = 20;

        // Assert
        Assert.assertThrows(IllegalArgumentException.class, () -> new Course(
                courseCode, courseName, courseDescription, minNrEnrolledStudents, maxNrEnrolledStudents));
    }


    @Test
    public void ensureCourseIsNotCreatedWithEmptyName() {
        // Arrange
        String courseCode = "LAPR4";
        String courseName = "";
        String courseDescription = "This is a course";
        int minNrEnrolledStudents = 10;
        int maxNrEnrolledStudents = 20;

        // Assert
        Assert.assertThrows(IllegalArgumentException.class, () -> new Course(
                courseCode, courseName, courseDescription, minNrEnrolledStudents, maxNrEnrolledStudents));
    }


    @Test
    public void ensureCourseIsNotCreatedWithEmptyDescription() {
        // Arrange
        String courseCode = "LAPR4";
        String courseName = "LAPR4";
        String courseDescription = "";
        int minNrEnrolledStudents = 10;
        int maxNrEnrolledStudents = 20;

        // Assert
        Assert.assertThrows(IllegalArgumentException.class, () -> new Course(
                courseCode, courseName, courseDescription, minNrEnrolledStudents, maxNrEnrolledStudents));
    }


    @Test
    public void ensureCourseIsNotCreatedWithInvalidDescription() {
        // Arrange
        String courseCode = "LAPR4";
        String courseName = "LAPR4";
        String courseDescription = "Word";  // Needs to be a sentence
        int minNrEnrolledStudents = 10;
        int maxNrEnrolledStudents = 20;

        // Assert
        Assert.assertThrows(IllegalArgumentException.class, () -> new Course(
                courseCode, courseName, courseDescription, minNrEnrolledStudents, maxNrEnrolledStudents));
    }


    @Test
    public void ensureCourseIsNotCreatedWithNegativeMinLimit() {
        // Arrange
        String courseCode = "LAPR4";
        String courseName = "LAPR4";
        String courseDescription = "This is a course";
        int minNrEnrolledStudents = -10;
        int maxNrEnrolledStudents = 20;

        // Assert
        Assert.assertThrows(IllegalArgumentException.class, () -> new Course(
                courseCode, courseName, courseDescription, minNrEnrolledStudents, maxNrEnrolledStudents));
    }


    @Test
    public void ensureCourseIsNotCreatedWithNegativeMaxLimit() {
        // Arrange
        String courseCode = "LAPR4";
        String courseName = "LAPR4";
        String courseDescription = "This is a course";
        int minNrEnrolledStudents = 10;
        int maxNrEnrolledStudents = -20;

        // Assert
        Assert.assertThrows(IllegalArgumentException.class, () -> new Course(
                courseCode, courseName, courseDescription, minNrEnrolledStudents, maxNrEnrolledStudents));
    }


    @Test
    public void ensureCourseIsNotCreatedWithMaxLimitLowerThenMinLimit() {
        // Arrange
        String courseCode = "LAPR4";
        String courseName = "LAPR4";
        String courseDescription = "This is a course";
        int minNrEnrolledStudents = 20;
        int maxNrEnrolledStudents = 10;

        // Assert
        Assert.assertThrows(IllegalArgumentException.class, () -> new Course(
                courseCode, courseName, courseDescription, minNrEnrolledStudents, maxNrEnrolledStudents));
    }


    /* Courses's State Tests */

    @Test
    public void ensureCourseOpens() {
        // Arrange
        Course course = CourseDataSource.getTestCourse1();

        // Act
        course.openCourse();

        // Assert
        Assert.assertTrue(course.isOpen());
    }


    @Test
    public void ensureCourseOpensEnrollment() {
        // Arrange
        Course course = CourseDataSource.getTestCourse1();

        // Act
        course.openCourse();
        course.openEnrollments();

        // Assert
        Assert.assertTrue(course.areEnrollmentsOpen());
    }


    @Test
    public void ensureCourseClosesEnrollment() {
        // Arrange
        Course course = CourseDataSource.getTestCourse1();

        // Act
        course.openCourse();
        course.openEnrollments();
        course.closeEnrollments();

        // Assert
        Assert.assertTrue(course.isInProgress());
    }


    @Test
    public void ensureCourseCloses() {
        // Arrange
        Course course = CourseDataSource.getTestCourse1();

        // Act
        course.openCourse();
        course.openEnrollments();
        course.closeEnrollments();
        course.closeCourse();

        // Assert
        Assert.assertTrue(course.isClosed());
    }


    @Test
    public void ensureCourseIsCreatedInCloseState() {
        // Arrange
        Course course = CourseDataSource.getTestCourse1();

        // Assert
        Assert.assertTrue(course.isClose());
    }


    @Test
    public void ensureCourseDoesNotOpenInAOpenState() {
        // Arrange
        Course course = CourseDataSource.getTestCourse1();

        // Act
        course.openCourse();

        // Assert
        Assert.assertThrows(IllegalStateException.class, course::openCourse);
    }


    @Test
    public void ensureCourseDoesNotOpenInAEnrollState() {
        // Arrange
        Course course = CourseDataSource.getTestCourse1();

        // Act
        course.openCourse();
        course.openEnrollments();

        // Assert
        Assert.assertThrows(IllegalStateException.class, course::openCourse);
    }


    @Test
    public void ensureCourseDoesNotOpenInAInProgressState() {
        // Arrange
        Course course = CourseDataSource.getTestCourse1();

        // Act
        course.openCourse();
        course.openEnrollments();
        course.closeEnrollments();

        // Assert
        Assert.assertThrows(IllegalStateException.class, course::openCourse);
    }


    @Test
    public void ensureCourseDoesNotOpenInAClosedState() {
        // Arrange
        Course course = CourseDataSource.getTestCourse1();

        // Act
        course.openCourse();
        course.openEnrollments();
        course.closeEnrollments();
        course.closeCourse();

        // Assert
        Assert.assertThrows(IllegalStateException.class, course::openCourse);
    }


    @Test
    public void ensureCourseDoesNotOpenEnrollmentsInACloseState() {
        // Arrange
        Course course = CourseDataSource.getTestCourse1();

        // Assert
        Assert.assertThrows(IllegalStateException.class, course::openEnrollments);
    }


    @Test
    public void ensureCourseDoesNotOpenEnrollmentsInAEnrollState() {
        // Arrange
        Course course = CourseDataSource.getTestCourse1();

        // Act
        course.openCourse();
        course.openEnrollments();

        // Assert
        Assert.assertThrows(IllegalStateException.class, course::openEnrollments);
    }


    @Test
    public void ensureCourseDoesNotOpenEnrollmentsInAProgressState() {
        // Arrange
        Course course = CourseDataSource.getTestCourse1();

        // Act
        course.openCourse();
        course.openEnrollments();
        course.closeEnrollments();

        // Assert
        Assert.assertThrows(IllegalStateException.class, course::openEnrollments);
    }


    @Test
    public void ensureCourseDoesNotOpenEnrollmentsInAClosedState() {
        // Arrange
        Course course = CourseDataSource.getTestCourse1();

        // Act
        course.openCourse();
        course.openEnrollments();
        course.closeEnrollments();

        // Assert
        Assert.assertThrows(IllegalStateException.class, course::openEnrollments);
    }


    @Test
    public void ensureCourseDoesNotCloseEnrollmentsIfIsInCloseState() {
        // Arrange
        Course course = CourseDataSource.getTestCourse1();

        // Assert
        Assert.assertThrows(IllegalStateException.class, course::closeEnrollments);
    }


    @Test
    public void ensureCourseDoesNotCloseEnrollmentsInAOpenState() {
        // Arrange
        Course course = CourseDataSource.getTestCourse1();

        // Act
        course.openCourse();

        // Assert
        Assert.assertThrows(IllegalStateException.class, course::closeEnrollments);
    }


    @Test
    public void ensureCourseDoesNotCloseEnrollmentInAIn_ProgressState() {
        // Arrange
        Course course = CourseDataSource.getTestCourse1();

        // Act
        course.openCourse();
        course.openEnrollments();
        course.closeEnrollments();

        // Assert
        Assert.assertThrows(IllegalStateException.class, course::closeEnrollments);
    }


    @Test
    public void ensureCourseDoesNotCloseEnrollmentInAClosedState() {
        // Arrange
        Course course = CourseDataSource.getTestCourse1();

        // Act
        course.openCourse();
        course.openEnrollments();
        course.closeEnrollments();

        // Assert
        Assert.assertThrows(IllegalStateException.class, course::closeEnrollments);
    }


    @Test
    public void ensureCourseDoesNotCloseInACloseState() {
        // Arrange
        Course course = CourseDataSource.getTestCourse1();

        // Assert
        Assert.assertThrows(IllegalStateException.class, course::closeCourse);
    }


    @Test
    public void ensureCourseDoesNotCloseInAOpenState() {
        // Arrange
        Course course = CourseDataSource.getTestCourse1();

        // Act
        course.openCourse();

        // Assert
        Assert.assertThrows(IllegalStateException.class, course::closeCourse);
    }


    @Test
    public void ensureCourseDoesNotCloseInAEnrollState() {
        // Arrange
        Course course = CourseDataSource.getTestCourse1();

        // Act
        course.openCourse();
        course.openEnrollments();

        // Assert
        Assert.assertThrows(IllegalStateException.class, course::closeCourse);
    }


    @Test
    public void ensureCourseDoesNotCloseInAClosedState() {
        // Arrange
        Course course = CourseDataSource.getTestCourse1();

        // Act
        course.openCourse();
        course.openEnrollments();
        course.closeEnrollments();
        course.closeCourse();

        // Assert
        Assert.assertThrows(IllegalStateException.class, course::closeCourse);
    }

    @Test
    public void ensureCoursesWithSameCodeAreEqual() {
        // Arrange
        String courseCode = "LAPR4";
        String courseName = "LAPR4";
        String courseDescription = "This is a course";
        int minNrEnrolledStudents = 10;
        int maxNrEnrolledStudents = 20;
        Course course1 = new Course(courseCode, courseName, courseDescription,
                minNrEnrolledStudents, maxNrEnrolledStudents);
        Course course2 = new Course(courseCode, courseName, courseDescription,
                minNrEnrolledStudents, maxNrEnrolledStudents);

        // Assert
        Assert.assertTrue(course1.sameAs(course2));
    }

    @Test
    public void ensureCoursesWithDifferentCodeButSameNameAndDescriptionAreNotEqual() {
        // Arrange
        String courseCode1 = "123";
        String courseName1 = "LAPR5";
        String courseDescription1 = "This is a course";
        int minNrEnrolledStudents1 = 10;
        int maxNrEnrolledStudents1 = 20;
        Course course1 = new Course(courseCode1, courseName1, courseDescription1,
                minNrEnrolledStudents1, maxNrEnrolledStudents1);

        String courseCode2 = "456";
        String courseName2 = "LAPR5";
        String courseDescription2 = "This is a course";
        int minNrEnrolledStudents2 = 10;
        int maxNrEnrolledStudents2 = 20;
        Course course2 = new Course(courseCode2, courseName2, courseDescription2,
                minNrEnrolledStudents2, maxNrEnrolledStudents2);

        // Assert
        Assert.assertFalse(course1.sameAs(course2));
    }

    @Test
    public void ensureCourseIsTheSame() {
        // Arrange
        Course course = CourseDataSource.getTestCourse1();

        // Assert
        Assert.assertTrue(course.sameAs(course));
    }

    @Test
    public void ensureCourseIsNotSameAsDifferentClass() {
        // Arrange
        Course course = CourseDataSource.getTestCourse1();

        // Assert
        Assert.assertFalse(course.sameAs(new Object()));
    }

    @Test
    public void ensureCourseIdentity() {
        // Arrange
        Course course = CourseDataSource.getTestCourse1();

        // Assert
        Assert.assertEquals(course.identity(), new CourseCode("EAPLI"));
    }

    @Test
    public void ensureTeacherInChargeIsDefined() {
        // ARRANGE
        // Create a course
        Course course = CourseDataSource.getTestCourse1();
        // Create a teacher
        ECourseUser teacher = UserDataSource.getTestTeacher1();

        // ACT
        course.defineTeacherInCharge(teacher);

        // ASSERT
        Assert.assertEquals(course.teacherInCharge(), teacher);
    }

    @Test
    public void ensureDefiningAnotherTeacherInChargeThrowsException() {
        // ARRANGE
        // Create a course
        Course course = CourseDataSource.getTestCourse1();
        // Create a teacher
        ECourseUser teacher = UserDataSource.getTestTeacher1();
        ECourseUser teacher2 = UserDataSource.getTestTeacher2();

        // ACT
        course.defineTeacherInCharge(teacher);

        // ASSERT
        Assert.assertThrows(IllegalArgumentException.class, () -> course.defineTeacherInCharge(teacher2));
    }

    @Test
    public void ensureDefiningTeacherInChargeWithNullTeacherThrowsException() {
        // ARRANGE
        // Create a course
        Course course = CourseDataSource.getTestCourse1();

        // ACT & ASSERT
        Assert.assertThrows(IllegalArgumentException.class, () -> course.defineTeacherInCharge(null));
        Assert.assertNull(course.teacherInCharge());
    }

    @Test
    public void ensureDefiningTeacherInChargeWithNonTeacherThrowsException() {
        // ARRANGE
        // Create a course
        Course course = CourseDataSource.getTestCourse1();
        // Create a teacher
        ECourseUser student = UserDataSource.getTestStudent1();

        // ACT & ASSERT
        Assert.assertThrows(IllegalArgumentException.class, () -> course.defineTeacherInCharge(student));
        Assert.assertNull(course.teacherInCharge());
    }

    @Test
    public void ensureTeacherIsAddedToCourseTeachers() {
        // ARRANGE
        // Create a course
        Course course = CourseDataSource.getTestCourse1();
        // Create a teacher
        ECourseUser teacher = UserDataSource.getTestTeacher1();

        // ACT
        course.addTeacher(teacher);

        // ASSERT
        Assert.assertTrue(course.teachers().contains(teacher));
    }

    @Test
    public void ensureTeacherIsNotAddedToCourseIfItIsAlreadyThere() {
        // ARRANGE
        // Create a course
        Course course = CourseDataSource.getTestCourse1();
        // Create a teacher
        ECourseUser teacher = UserDataSource.getTestTeacher1();

        // ACT
        course.addTeacher(teacher);

        // ASSERT
        Assert.assertThrows(IllegalArgumentException.class, () -> course.addTeacher(teacher));
        Assert.assertEquals(course.teachers().size(), 1);
    }

    @Test
    public void ensureTeacherIsNotAddedToCourseIfItIsTheTeacherInCharge() {
        // ARRANGE
        // Create a course
        Course course = CourseDataSource.getTestCourse1();
        // Create a teacher
        ECourseUser teacher = UserDataSource.getTestTeacher1();

        // ACT
        course.defineTeacherInCharge(teacher);

        // ASSERT
        Assert.assertThrows(IllegalArgumentException.class, () -> course.addTeacher(teacher));
        Assert.assertEquals(course.teachers().size(), 1);
    }

    @Test
    public void ensureNullTeacherIsNotAddedToCourse() {
        // ARRANGE
        // Create a course
        Course course = CourseDataSource.getTestCourse1();

        // ACT & ASSERT
        Assert.assertThrows(IllegalArgumentException.class, () -> course.addTeacher(null));
        Assert.assertEquals(course.teachers().size(), 0);
    }

    @Test
    public void ensureNonTeacherIsNotAddedToCourse() {
        // ARRANGE
        // Create a course
        Course course = CourseDataSource.getTestCourse1();
        // Create a teacher
        ECourseUser student = UserDataSource.getTestStudent1();

        // ACT & ASSERT
        Assert.assertThrows(IllegalArgumentException.class, () -> course.addTeacher(student));
        Assert.assertEquals(course.teachers().size(), 0);
    }

    @Test
    public void ensureTeacherInCourseCanNotBeDefinedAsTeacherInCharge() {
        // ARRANGE
        // Create a course
        Course course = CourseDataSource.getTestCourse1();
        // Create a teacher
        ECourseUser teacher = UserDataSource.getTestTeacher1();

        // ACT
        course.addTeacher(teacher);

        // ASSERT
        Assert.assertThrows(IllegalArgumentException.class, () -> course.defineTeacherInCharge(teacher));
        Assert.assertNull(course.teacherInCharge());
    }




}
