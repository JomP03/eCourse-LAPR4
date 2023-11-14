package questionmanagment.application;

import questionmanagment.domain.*;

public interface IQuestionValidator {

    /**
     * Validates a question
     * @param path the path of the file containing the question
     * @return question
     */
    Question validate(String path);
}
