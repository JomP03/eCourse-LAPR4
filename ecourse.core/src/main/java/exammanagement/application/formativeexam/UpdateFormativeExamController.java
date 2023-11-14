package exammanagement.application.formativeexam;

import eapli.framework.validations.*;
import ecourseusermanagement.application.*;
import ecourseusermanagement.domain.*;
import exammanagement.domain.*;
import exammanagement.repository.*;
import questionmanagment.application.IQuestionProvider;
import questionmanagment.application.QuestionProvider;
import questionmanagment.domain.*;

import java.util.*;

public class UpdateFormativeExamController {

    private final UserSessionService userSessionService;
    private final ExamRepository examRepo;
    private final IQuestionProvider questionProvider;
    private ECourseUser examOwner;
    private FormativeExam selectedFormativeExam;

    public UpdateFormativeExamController(UserSessionService userSessionService,
                                         ExamRepository examRepo, QuestionProvider questionProvider) {
        Preconditions.nonNull(userSessionService);
        this.userSessionService = userSessionService;

        Preconditions.nonNull(examRepo);
        this.examRepo = examRepo;

        Preconditions.nonNull(questionProvider);
        this.questionProvider = questionProvider;

        verifyUser();
    }

    public void verifyUser() {
        Optional<ECourseUser> eCourseUserOptional = userSessionService.getLoggedUser();
        eCourseUserOptional.ifPresentOrElse(eCourseUser -> this.examOwner = eCourseUser, () -> {
            throw new IllegalStateException("eCourse User must be registered.");
        });
    }

    public Iterable<FormativeExam> listCreatorFormativeExams() {
        return examRepo.findFormativeExamsByCreator(examOwner);
    }

    public void defineFormativeExam(FormativeExam selectedFormativeExam) {
        Preconditions.nonNull(selectedFormativeExam);

        this.selectedFormativeExam = selectedFormativeExam;
    }

    public boolean updateTitle(String newTitle) {

        if (examRepo.isTitleAlreadyAssigned(newTitle))
            return false;

        selectedFormativeExam.updateTitle(newTitle);

        submitExam();
        return true;
    }


    /**
     * Updates the header of the selected formative exam.
     * @param newHeader the new header
     * @param newGradingType the new grading type
     * @param newFeedBackType the new feedback type
     */
    public void updateHeader(String newHeader, GradingType newGradingType, FeedBackType newFeedBackType) {
        selectedFormativeExam.updateHeader(new ExamHeader(newHeader, newGradingType, newFeedBackType));

        submitExam();
    }


    /**
     * Updates the sections of the selected formative exam.
     */
    public List<ExamSection> examSections() {
        return selectedFormativeExam.sections();
    }


    /**
     * Updates the description of the selected section.
     * @param section the selected section
     * @param newDescription the new description
     */
    public void updateSectionDescription(ExamSection section, String newDescription) {
        selectedFormativeExam.updateSectionDescription(section, newDescription);

        submitExam();
    }


    /**
     * Updates the questions of the selected section.
     * @param section the selected section
     * @param questionIndex the position of the question to be updated
     */
    public void updateQuestion(ExamSection section, int questionIndex, QuestionType questionType) {
        questionProvider.updateSingleQuestion(questionType, section.questions(), questionIndex);
        selectedFormativeExam.updateSectionQuestions(section, section.questions());

        submitExam();
    }


    /**
     * Updates all questions of a selected section.
     * @param section the selected section
     */
    public void updateQuestions(ExamSection section) {
        questionProvider.updateAllSectionQuestions(section.questions().get(0).type(), section.questions());
        selectedFormativeExam.updateSectionQuestions(section, section.questions());

        submitExam();
    }


    public FormativeExam submitExam() {
        return examRepo.save(selectedFormativeExam);
    }


    /**
     * Gets updated formative exam.
     *
     * @return the updated formative exam
     */
    public FormativeExam getUpdatedFormativeExam() {
        return this.selectedFormativeExam;
    }
}
