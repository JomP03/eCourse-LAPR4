package takenexammanagement.application.service;

import exammanagement.domain.Exam;
import questionmanagment.domain.Question;
import takenexammanagement.domain.AnsweredQuestion;
import takenexammanagement.domain.TakenExam;


public class TakenExamCorrector {

    public TakenExamCorrector() {
    }

    /**
     * Corrects the exam
     * @param takenExam the exam to correct
     * @return the corrected exam
     */
    public TakenExam correctExam(TakenExam takenExam) {

        Exam exam = takenExam.exam();

        for(AnsweredQuestion answeredQuestion : takenExam.answeredQuestions()) {

            int sectionIndex = answeredQuestion.sectionIndex();

            int questionIndex = answeredQuestion.questionIndex();

            Question question = exam.obtainQuestion(sectionIndex, questionIndex);

            float obtainedQuotation = question.correctStudentAnswer(answeredQuestion.answer());
            if(obtainedQuotation > 0) {
                takenExam.addQuotation(question.quotation());
                answeredQuestion.addFeedback("You got it right! Well done.\n");
            }
            else {
                answeredQuestion.addFeedback("You got it wrong, the correct answer was -> " + question.correctAnswer() + ".\n");
            }

        }

        takenExam.ajustGrade();

        return takenExam;
    }

}
