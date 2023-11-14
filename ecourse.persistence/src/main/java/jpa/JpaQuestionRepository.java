package jpa;

import classmanagement.domain.*;
import questionmanagment.domain.*;
import questionmanagment.repository.*;

import javax.persistence.*;
import java.util.List;

public class JpaQuestionRepository extends eCourseJpaRepositoryBase<Question, Long, Long> implements QuestionRepository {

    public JpaQuestionRepository() {
        super("id");
    }


    @Override
    public Iterable<Question> findAllByType(QuestionType questionType) {
        final TypedQuery<Question>  query = entityManager().createQuery(
                "SELECT q FROM Question q WHERE q.questionType = :questionType", Question.class);
        query.setParameter("questionType", questionType);

        return query.getResultList();
    }
}
