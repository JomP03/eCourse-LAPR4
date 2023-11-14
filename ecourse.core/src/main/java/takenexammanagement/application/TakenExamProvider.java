package takenexammanagement.application;

import coursemanagement.domain.*;
import eapli.framework.validations.Preconditions;
import ecourseusermanagement.domain.*;
import exammanagement.domain.AutomatedExam;
import exammanagement.domain.Exam;
import exammanagement.domain.GradingType;
import takenexammanagement.domain.TakenExam;
import takenexammanagement.repository.TakenExamRepository;

import java.time.LocalDateTime;
import java.util.List;

public class TakenExamProvider implements ITakenExamProvider {

    private final TakenExamRepository takenExamRepository;

    public TakenExamProvider(TakenExamRepository takenExamRepository) {
        Preconditions.noneNull(takenExamRepository);
        this.takenExamRepository = takenExamRepository;
    }

    @Override
    public Iterable<TakenExam> provideTakenExams(Exam exam) {
        return takenExamRepository.findByExam(exam);
    }

    @Override
    public List<TakenExam> provideUserGradedTakenExamsFromCourse(Course course, ECourseUser user) {
        List<TakenExam> takenExams = (List<TakenExam>) takenExamRepository.findByCourseAndStudent(course, user);

        // removes the taken exams where the grading type is None
        takenExams.removeIf(takenExam -> takenExam.exam().header().gradingFeedBackType().equals(GradingType.NONE));

        // removes the taken exams where the grade type is after closing and the exam is not closed yet
        takenExams.removeIf(takenExam -> {
            if(takenExam.exam().header().gradingFeedBackType().equals(GradingType.AFTER_CLOSING)) {
                if(!(takenExam.exam() instanceof AutomatedExam))
                    return false;
                AutomatedExam automatedExam = (AutomatedExam) takenExam.exam();
                return automatedExam.openPeriod().closeDate().isAfter(LocalDateTime.now());
            }
            return false;
        });

        return takenExams;
    }
}
