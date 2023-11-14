package questionmanagment.application;

import questionmanagment.domain.*;
import questionmanagment.repository.*;

import java.util.*;

public class QuestionProvider implements IQuestionProvider {

    private final QuestionRepository questionRepo;

    public QuestionProvider(QuestionRepository questionRepo) {
        this.questionRepo = questionRepo;
    }

    @Override
    public boolean isThereEnoughQuestionsForType(QuestionType questionType, int numberOfQuestions) {
        // Obtain the questions from the repository
        List<Question> questionsFromRepo = (List<Question>) questionRepo.findAllByType(questionType);

        // Check if there are enough questions to generate the questions for the section
        return questionsFromRepo.size() >= numberOfQuestions;
    }

    @Override
    public Iterable<Question> listQuestionsByType(QuestionType questionType, int numberOfQuestions) {
        // Obtain the questions from the repository
        List<Question> questionsFromRepo = (List<Question>) questionRepo.findAllByType(questionType);

        if (questionsFromRepo.size() == 0)
            throw new IllegalStateException("There are no questions of the type " + questionType + " in the repository.");

        // Check if there are enough questions to generate the questions for the section
        if (questionsFromRepo.size() < numberOfQuestions) {
            throw new
                    IllegalStateException("There are not enough questions to generate the questions for the section.");
        }

        // Generate the questions for the section
        List<Question> questionsForSection = new ArrayList<>();

        do {
            // Generate the random question
            Question randomQuest = randomQuestion(questionsFromRepo);

            if (!questionsForSection.contains(randomQuest)) {
                // Remove from the old question and add the newer one
                questionsFromRepo.remove(randomQuest);
                questionsForSection.add(randomQuest);
            } else questionsFromRepo.remove(randomQuest);

            numberOfQuestions--;

        } while (numberOfQuestions > 0);

        return questionsForSection;
    }

    @Override
    public void updateSingleQuestion(QuestionType questionType, List<Question> questions, int questionIndex) {
        List<Question> questionsByType = isThereEnoughQuestionsToReplaceSectionQuestions(questionType,
                questions.size(), questions.size() - 1);

        questionsByType.remove(questions.get(questionIndex));
        replaceQuestion(questionsByType, questions, questionIndex);
    }

    @Override
    public void updateAllSectionQuestions(QuestionType questionType, List<Question> questions) {
        List<Question> questionsByType =
                isThereEnoughQuestionsToReplaceSectionQuestions(questionType, questions.size(), 1);

        int size = questions.size();
        questionsByType.removeAll(questions);
        for (int questionIndex = 0; questionIndex < size; questionIndex++) {
            replaceQuestion(questionsByType, questions, 0);
        }
    }

    /**
     * Replaces the unwanted questions with a newer one
     *
     * @param questions the list of questions
     */
    private void replaceQuestion(List<Question> questionsFromRepo, List<Question> questions, int questionIndex) {
        // Generate the random question
        Question randomQuestion = randomQuestion(questionsFromRepo);

        // Remove old question and add the new question to the list of questions
        questions.remove(questionIndex);
        questions.add(randomQuestion);
        questionsFromRepo.remove(randomQuestion);
    }

    private List<Question> isThereEnoughQuestionsToReplaceSectionQuestions(
            QuestionType questionType, int numberOfSectionQuestions, int numberOfQuestionsToReplace) {
        List<Question> questionsFromRepo = (List<Question>) questionRepo.findAllByType(questionType);

        if (questionsFromRepo.size() - numberOfSectionQuestions <= Math.multiplyExact(numberOfQuestionsToReplace, 2)) {
            throw new IllegalStateException("There are not enough questions to replace the section questions.");
        }

        return questionsFromRepo;
    }

    /**
     * Returns a random question from the list of questions.
     *
     * @param questions the list of questions
     * @return a random question
     */
    private Question randomQuestion(List<Question> questions) {
        if (questions.size() == 1)
            return questions.get(0);

        Random intRand = new Random();
        return questions.get(intRand.nextInt(questions.size() - 1));
    }

}
