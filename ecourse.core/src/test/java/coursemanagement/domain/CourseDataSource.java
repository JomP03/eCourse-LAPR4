package coursemanagement.domain;

public class CourseDataSource {

    /**
     * Returns a course for testing purposes.
     *
     * @return a course
     */
    public static Course getTestCourse1(){
        return new Course("EAPLI", "Application Engineering","Application Engineering Course",
                10, 20);
    }


    /**
     * Returns a course for testing purposes.
     *
     * @return a course
     */
    public static Course getTestCourse2() {
        return new Course("APROG", "Algoritmia e Programação", "Programming Basics",
                100, 300);
    }

    /**
     * Returns a course for testing purposes.
     *
     * @return a course
     */
    public static Course getTestCourse3() {
        return new Course("MDS", "Métodos de Desenvolvimento de Software",
                "Software Development Methods", 1000, 3000);
    }

}
