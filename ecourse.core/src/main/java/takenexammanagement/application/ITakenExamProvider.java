package takenexammanagement.application;

import coursemanagement.domain.*;
import ecourseusermanagement.domain.*;
import exammanagement.domain.Exam;
import takenexammanagement.domain.TakenExam;

public interface ITakenExamProvider {

    /**
     * Provide all taken exams of a specified exam.
     *
     * @param exam the exam
     * @return the iterable
     */
    Iterable<TakenExam> provideTakenExams(Exam exam);

    Iterable<TakenExam> provideUserGradedTakenExamsFromCourse(Course course, ECourseUser user);
}
