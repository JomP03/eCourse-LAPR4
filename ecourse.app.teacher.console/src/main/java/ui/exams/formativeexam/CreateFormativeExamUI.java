package ui.exams.formativeexam;

import coursemanagement.application.TeacherCoursesProvider;
import coursemanagement.domain.Course;
import ecourseusermanagement.application.UserSessionService;
import exammanagement.application.formativeexam.CreateFormativeExamController;
import exammanagement.domain.*;
import persistence.PersistenceContext;
import ui.components.AbstractUI;
import ui.components.Console;
import ui.components.ListSelector;
import ui.components.Sleeper;
import questionmanagment.application.QuestionProvider;
import questionmanagment.domain.QuestionType;

import java.util.Arrays;
import java.util.List;

public class CreateFormativeExamUI extends AbstractUI {

    private final CreateFormativeExamController controller = new CreateFormativeExamController(
                new UserSessionService(PersistenceContext.repositories().eCourseUsers()),
                    new TeacherCoursesProvider(PersistenceContext.repositories().courses()),
                        new QuestionProvider(PersistenceContext.repositories().questions()),
                            new FormativeExamBuilder(),
                                PersistenceContext.repositories().exams()
    );

    @Override
    protected boolean doShow() {
        try {

            if (!defineCourse()) return false;

            defineTopSection();
            int numberSections = defineSectionsNumber();
            return defineSection(numberSections);

        } catch (Exception e) {
            errorMessage(e.getMessage());
            return false;
        }
    }

    private boolean defineCourse() {
        List<Course> teacherCourses = (List<Course>) controller.listTeacherActiveCourses();

        if (teacherCourses.isEmpty()) {
            throw new IllegalStateException("You don't have any courses.");
        }

        ListSelector<Course> courseSelector = new ListSelector<>("Select a Course", teacherCourses);

        if (!courseSelector.showAndSelectWithExit()) return false;

        controller.submitCourse(courseSelector.getSelectedElement());
        return true;
    }

    private void defineTopSection() {

        // Define Exam Title
        String examTitle = Console.readNonEmptyLine("Exam Title: ", "Exam title can't be empty");
        System.out.println();

        // Define Exam Header Description
        String examHeaderDescription = Console.readNonEmptyLine("Exam Header Description: ",
                "Exam header description can't be empty");
        System.out.println();

        ListSelector<GradingType> gradingTypeSelector =
                new ListSelector<>("Select a grading type", Arrays.asList(GradingType.values()));
        gradingTypeSelector.showAndSelect();

        ListSelector<FeedBackType> feedBackTypeSelector =
                new ListSelector<>("Select a feedback type", Arrays.asList(FeedBackType.values()));
        feedBackTypeSelector.showAndSelect();

        controller.submitTopSection(examTitle, examHeaderDescription, feedBackTypeSelector.getSelectedElement(),
                gradingTypeSelector.getSelectedElement());
    }

    private int defineSectionsNumber() {
        return Console.readPositiveInteger("Number of sections: ");
    }

    private boolean defineSection(int numberSections) {
        // Sections requirements
        String sectionDescription;
        int numberQuestions;
        ListSelector<QuestionType> questionTypeSelector =
                new ListSelector<>("Select a question type", Arrays.asList(QuestionType.values()));
        FormativeExam exam;

        do {

            for (int section = 0; section < numberSections; section++) {
                System.out.println();
                sectionDescription = Console.readNonEmptyLine("Section " + Math.addExact(section, 1) +
                        " Description: ", "Section description can't be empty");
                System.out.println();

                questionTypeSelector.showAndSelect();

                if (controller.isThereEnoughQuestionsForType(questionTypeSelector.getSelectedElement(), 0)) {
                    errorMessage("There are no questions of the type " + questionTypeSelector.getSelectedElement());
                    Sleeper.sleep(1500);
                    return false;
                }

                do {
                    numberQuestions = Console.readPositiveInteger("Number of questions: ");
                } while (controller.isThereEnoughQuestionsForType(questionTypeSelector.getSelectedElement(), numberQuestions));

                controller.submitSection(sectionDescription, questionTypeSelector.getSelectedElement(), numberQuestions);
            }

            // Display the generated exam
            System.out.println();
            exam = controller.submitExam();
            System.out.println(exam.printExam());

            List<String> options = Arrays.asList("Generate new formative exam (unsatisfied with current one)", "Save");
            ListSelector<String> optionsSelector = new ListSelector<>("Select an option", options);
            optionsSelector.showAndSelect();

            if (optionsSelector.getSelectedElement().equals(options.get(1))) break;

            controller.removeSections();

        } while(true);

        createExam(exam);

        successMessage("Formative exam created successfully");
        Sleeper.sleep(1500);

        return true;
    }

    private void createExam(FormativeExam formativeExam) {
        controller.saveExam(formativeExam);
    }

    @Override
    public String headline() {
        return "Create Formative Exam";
    }
}
