import coursemanagement.domain.Course;
import eapli.framework.actions.Action;
import ecourseusermanagement.domain.ECourseUser;
import exammanagement.domain.*;
import exammanagement.repository.ExamRepository;
import persistence.PersistenceContext;
import questionmanagment.domain.*;

import javax.swing.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class AutomatedExamBootstrapper implements Action {

    @Override
    public boolean execute() {

        ExamRepository examRepository = PersistenceContext.repositories().exams();


        // Automated Exam 1
        String examTitle = "DevOps Automated Exam";
        String description = "DevsOps Automated Exam, get it done fast!";

        List<Question> questions = new ArrayList<>();
        questions.add(new NumericalQuestion("How many phases does DevOps have?", "3", 0.5f, 1f));
        questions.add(new BooleanQuestion("Is DevOps a culture?", true, 0.5f, 1f));

        List<String> options = new ArrayList<>();
        options.add("DevOps");
        options.add("Java");
        options.add("C#");
        options.add("C++");

        questions.add(new MultipleChoiceQuestion("What is the name of the language?", options, "DevOps", 1f, 10.0f));

        ExamSection examSection = new ExamSection(description, questions);
        List<ExamSection> sections = new ArrayList<>();
        sections.add(examSection);

        ECourseUser teacher = PersistenceContext.repositories().eCourseUsers().findByEmail("janedoe@gmail.com").get();
        Course course = PersistenceContext.repositories().courses().findByCode("PYTHON");

        // create Open Period
        LocalDateTime openDate = LocalDateTime.now().minusDays(1);
        LocalDateTime closeDate = LocalDateTime.now().plusWeeks(1);

        // create ExamHeader
        String description2 = "Please make sure you read the questions carefully and answer them correctly!";
        GradingType gradingFeedBackType = GradingType.NONE;
        FeedBackType feedbackType = FeedBackType.NONE;
        ExamHeader examHeader = new ExamHeader(description2, gradingFeedBackType, feedbackType);

        AutomatedExam exam = new AutomatedExam(sections,examHeader,openDate, closeDate, examTitle, teacher, course);
        examRepository.save(exam);
        // ---------------------------------------------------------------


        // Automated Exam 2

        String examTitle2 = "C# Automated Exam ";
        String description3 = "C# Automated Exam, get it done fast!";
        List<Question> questions2 = new ArrayList<>();
        questions2.add(new BooleanQuestion("Is this language object-oriented?", true, 0.5f, 1f));

        List<Question> questions22 = new ArrayList<>();
        questions22.add(new ShortAnswerQuestion("What is the name of the language?", "C#", 0.5f, 1f));

        ExamSection examSection2 = new ExamSection(description3, questions2);
        ExamSection examSection22 = new ExamSection(description3, questions22);
        List<ExamSection> sections2 = new ArrayList<>();
        sections2.add(examSection2);
        sections2.add(examSection22);


        ECourseUser teacher2 = PersistenceContext.repositories().eCourseUsers().findByEmail("paulsmith@gmail.com").get();
        Course course2 = PersistenceContext.repositories().courses().findByCode("C#");

        // create Open Period
        LocalDateTime openDate2 = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).withHour(10).withMinute(0).withSecond(0);
        LocalDateTime closeDate2 = LocalDateTime.now().plusWeeks(1).with(TemporalAdjusters.next(DayOfWeek.MONDAY)).withHour(16).withMinute(0).withSecond(0);

        // create ExamHeader
        String description4 = "Try to do your best!";
        GradingType gradingFeedBackType2 = GradingType.NONE;
        FeedBackType feedbackType2 = FeedBackType.NONE;
        ExamHeader examHeader2 = new ExamHeader(description4, gradingFeedBackType2, feedbackType2);

        AutomatedExam exam2 = new AutomatedExam(sections2,examHeader2,openDate2, closeDate2, examTitle2, teacher2, course2);
        examRepository.save(exam2);
        // ---------------------------------------------------------------


        // Automated Exam 3

        String examTitle3 = "Rust Automated Exam ";
        String description5 = "Rust Automated Exam, get it done fast!";

        List<Question> questions3 = new ArrayList<>();
        questions3.add(new BooleanQuestion("Does everyone like the teacher?", true, 0.5f, 1f));
        questions3.add(new ShortAnswerQuestion("Is Rust a good thing to learn?", "yes", 0.5f, 1f));

        ExamSection examSection3 = new ExamSection(description5, questions3);
        List<ExamSection> sections3 = new ArrayList<>();
        sections3.add(examSection3);

        ECourseUser teacher3 = PersistenceContext.repositories().eCourseUsers().findByEmail("janedoe@gmail.com").get();
        Course course3 = PersistenceContext.repositories().courses().findByCode("Rust");

        // create Open Period
        LocalDateTime openDate3 = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.THURSDAY)).withHour(10).withMinute(0).withSecond(0);
        LocalDateTime closeDate3 = LocalDateTime.now().plusWeeks(1).with(TemporalAdjusters.next(DayOfWeek.FRIDAY)).withHour(10).withMinute(0).withSecond(0);

        // create ExamHeader
        String description6 = "Do your best!";
        GradingType gradingFeedBackType3 = GradingType.NONE;
        FeedBackType feedbackType3 = FeedBackType.NONE;
        ExamHeader examHeader3 = new ExamHeader(description6, gradingFeedBackType3, feedbackType3);

        AutomatedExam exam3 = new AutomatedExam(sections3,examHeader3,openDate3, closeDate3, examTitle3, teacher3, course3);
        examRepository.save(exam3);
        // ---------------------------------------------------------------



        return true;
    }

}
