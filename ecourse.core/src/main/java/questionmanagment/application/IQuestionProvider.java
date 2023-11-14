package questionmanagment.application;

import questionmanagment.domain.*;
import java.util.List;

public interface IQuestionProvider {


    /**
     * Gets a list of questions by type
     * @param questionType the question type
     * @param numberOfQuestions the number of questions
     * @return an iterable of questions
     */
    Iterable<Question> listQuestionsByType(QuestionType questionType, int numberOfQuestions);


    /**
     * Updates a single question
     * @param questionType the question type
     * @param questions the list of questions
     * @param questionIndex the question index
     */
    void updateSingleQuestion(QuestionType questionType, List<Question> questions, int questionIndex);


    /**
     * Updates all questions of a section
     * @param questionType the question type
     * @param questions the list of questions
     */
    void updateAllSectionQuestions(QuestionType questionType, List<Question> questions);


    /**
     * Verifies if there are enough questions to satisfy the number of questions
     *
     * @param questionType      the question type
     * @param numberOfQuestions the number of questions
     * @return the boolean
     */
    boolean isThereEnoughQuestionsForType(QuestionType questionType, int numberOfQuestions);
}
