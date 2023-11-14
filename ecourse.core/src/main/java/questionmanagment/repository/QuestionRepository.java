package questionmanagment.repository;

import eapli.framework.domain.repositories.*;
import questionmanagment.domain.Question;
import questionmanagment.domain.QuestionType;

import java.util.List;

public interface QuestionRepository extends DomainRepository<Long, Question> {

    /**
     * Returns all the questions of a certain type.
     * @param questionType the type of the question
     * @return all the questions of a certain type
     */
    Iterable<Question> findAllByType(QuestionType questionType);
}
