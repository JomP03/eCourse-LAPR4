package ui.exams.formativeexam;

import ecourseusermanagement.application.*;
import exammanagement.application.formativeexam.UpdateFormativeExamController;
import exammanagement.domain.*;
import persistence.*;
import questionmanagment.application.*;
import questionmanagment.domain.*;
import ui.components.AbstractUI;
import ui.components.Console;
import ui.components.ListSelector;
import ui.components.Sleeper;

import java.util.*;

public class UpdateFormativeExamUI extends AbstractUI {

    private final UpdateFormativeExamController controller = new UpdateFormativeExamController(
            new UserSessionService(PersistenceContext.repositories().eCourseUsers()),
            PersistenceContext.repositories().exams(),
            new QuestionProvider(PersistenceContext.repositories().questions())
    );


    @Override
    protected boolean doShow() {

        // Get the formative exams created by the user and request him to select one
        Collection<FormativeExam> formativeExams = (Collection<FormativeExam>) controller.listCreatorFormativeExams();
        if (formativeExams.isEmpty()) {
            infoMessage("There are no formative exams.");
            return false;
        }
        FormativeExam selectedFormativeExam = defineFormativeExam(formativeExams);
        if (selectedFormativeExam == null)
            return false;

        // Aks the user if he wants to view the exam
        viewFormativeExam(selectedFormativeExam);

        // Ask the user to select an option to update the exam
        List<String> updateOptions = Arrays.asList("Title", "Header", "Sections");
        ListSelector<String> examUpdateSelector = new ListSelector<>("Select an option to update", updateOptions);
        examUpdateSelector.showAndSelect();
        String updateOption = examUpdateSelector.getSelectedElement();

        examUpdateFactory(updateOption);

        return false;
    }


    /**
     * Requests the user to select a formative exam from a collection of formative exams.
     * @param formativeExams the collection of formative exams
     * @return the selected formative exam
     */
    private FormativeExam defineFormativeExam(Collection<FormativeExam> formativeExams) {

        ListSelector<FormativeExam> selector = new ListSelector<>("Select a Formative Exam", formativeExams);
        if (!selector.showAndSelectWithExit())
            return null;

        List<FormativeExam> formativeExamList = new ArrayList<>(formativeExams);
        FormativeExam selectedFormativeExam = formativeExamList.get(selector.getSelectedOption() - 1);

        controller.defineFormativeExam(selectedFormativeExam);
        return selectedFormativeExam;
    }


    /**
     * Asks the user if he wants to view the exam.
     * @param selectedFormativeExam the selected formative exam
     */
    private void viewFormativeExam(FormativeExam selectedFormativeExam) {
        ListSelector<String> viewExamSelector =
                new ListSelector<>("Do you want to view the exam?", Arrays.asList("Yes", "No"));
        viewExamSelector.showAndSelectWithExit();
        String viewExam = viewExamSelector.getSelectedElement();
        if (viewExam.equals("Yes"))
            System.out.println(selectedFormativeExam.printExam());
    }


    /**
     * Acts upon the user's selection of the update option.
     * @param updateOption the update option
     */
    private void examUpdateFactory(String updateOption) {
        switch (updateOption) {
            case "Title":

                String newTitle;

                do {

                    newTitle = Console.readLine("Enter the new title: ");

                    if (!controller.updateTitle(newTitle)) {
                        errorMessage("The title is already in use. Please try again.");
                    }

                    else break;

                } while (true);

                System.out.println();
                successMessage("Title updated successfully!");
                Sleeper.sleep(1000);
                break;

            case "Header":
                String newHeader = Console.readLine("Enter the new header: ");

                // Select the grading type
                ListSelector<GradingType> gradingTypeSelector =
                        new ListSelector<>("Select a grading type", Arrays.asList(GradingType.values()));
                gradingTypeSelector.showAndSelect();

                // Select the feedback type
                ListSelector<FeedBackType> feedBackTypeSelector =
                        new ListSelector<>("Select a feedback type", Arrays.asList(FeedBackType.values()));
                feedBackTypeSelector.showAndSelect();

                controller.updateHeader(
                        newHeader, gradingTypeSelector.getSelectedElement(), feedBackTypeSelector.getSelectedElement());

                successMessage("Header updated successfully!");
                Sleeper.sleep(1000);
                break;

            case "Sections":
                ListSelector<ExamSection> sectionSelector =
                        new ListSelector<>("Select a section to update", controller.examSections());
                sectionSelector.showAndSelect();
                ExamSection selectedSection = sectionSelector.getSelectedElement();
                updateSections(selectedSection);
                break;
        }
    }


    /**
     * Requests the user to select an update option for a section
     */
    private void updateSections(ExamSection section) {
        List<String> updateOptions = Arrays.asList("Section Description", "Individual Question", "All Questions");
        ListSelector<String> sectionUpdateSelector = new ListSelector<>("Select an option to update", updateOptions);
        sectionUpdateSelector.showAndSelect();
        String updateOption = sectionUpdateSelector.getSelectedElement();
        sectionUpdateFactory(section, updateOption);
    }


    /**
     * Acts upon the user's selection of the update option for a section
     * @param updateOption the update option
     */
    private void sectionUpdateFactory(ExamSection section, String updateOption) {
        switch (updateOption) {
            case "Section Description":
                String newDescription = Console.readLine("Enter the new description: ");
                controller.updateSectionDescription(section, newDescription);

                System.out.println();
                successMessage("Section description updated successfully!");
                Sleeper.sleep(1000);
                break;

            case "Individual Question":
                ListSelector<Question> questions = new ListSelector<>(
                        "Select a question to update", section.questions());
                questions.showAndSelect();
                Question selectedQuestion = questions.getSelectedElement();

                try {

                    controller.updateQuestion(
                            section, questions.getSelectedOption() - 1, selectedQuestion.type());

                } catch (IllegalArgumentException e) {
                    errorMessage(e.getMessage());
                    Sleeper.sleep(1000);
                    break;
                }

                System.out.println();
                successMessage("Question updated successfully!");
                Sleeper.sleep(1000);
                break;

            case "All Questions":

                try {
                    controller.updateQuestions(section);
                } catch (IllegalArgumentException e) {
                    errorMessage(e.getMessage());
                    Sleeper.sleep(1000);
                    break;
                }

                System.out.println();
                successMessage("All Section's Questions updated successfully!");
                Sleeper.sleep(1000);
                break;
        }
    }

    @Override
    public String headline() {
        return "Update Formative Exam";
    }
}
