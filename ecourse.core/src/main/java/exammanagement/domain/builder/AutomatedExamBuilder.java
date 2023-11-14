package exammanagement.domain.builder;

import coursemanagement.domain.Course;
import ecourseusermanagement.domain.ECourseUser;
import exammanagement.domain.*;
import exammanagement.repository.ExamRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AutomatedExamBuilder {

    private final ExamRepository examRepository;

    private Course course;

    private String examTitle;

    private List<ExamSection> sections = new ArrayList<>();

    private LocalDateTime openDate;

    private LocalDateTime closeDate;

    private ECourseUser creator;

    private ExamHeader header;

    public AutomatedExamBuilder(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    public void withCourse(Course course) {
        this.course = course;
    }

    public void withExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    public void withOpenDate(LocalDateTime openDate) {
        this.openDate = openDate;
    }

    public void withCloseDate(LocalDateTime closeDate) {
        this.closeDate = closeDate;
    }

    public void withCreator(ECourseUser creator) {
        this.creator = creator;
    }

    public void withHeader(String description, GradingType gradingFeedBackType, FeedBackType feedbackType) {
        this.header = new ExamHeader(description, gradingFeedBackType, feedbackType);
    }

    public void withSection(ExamSection section) {
        this.sections.add(section);
    }

    public AutomatedExam build() {
        return new AutomatedExam(sections, header, openDate, closeDate, examTitle, creator, course);
    }


}
