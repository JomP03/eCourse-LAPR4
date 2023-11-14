package questionmanagment.application.updatequestions;

import questionmanagment.domain.*;

public interface IUpdateQuestionController {


    /**
     * Lists all questions byte type
     * @return Iterable of questions
     */
    Iterable<Question> listQuestions();
}
