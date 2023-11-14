package exammanagement.domain;

import coursemanagement.domain.Course;
import ecourseusermanagement.domain.ECourseUser;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class AutomatedExam extends Exam{

    @Embedded
    private ExamOpenPeriod openPeriod;

    public AutomatedExam(List<ExamSection> sections, ExamHeader header, LocalDateTime openDate, LocalDateTime closeDate, String title, ECourseUser creator, Course course) {
        super(sections, header, title, creator, course);

        if(openDate == null || closeDate == null){
            throw new IllegalArgumentException("Open and close dates must not be null.");
        }

        this.openPeriod = new ExamOpenPeriod(openDate, closeDate);
    }

    protected AutomatedExam() {
        // for ORM only
    }

    @Override
    public String stringToFile(){
        StringBuilder sb = new StringBuilder();


        // Exam Header
        sb.append("TITLE:" + super.title()).append("\n").append(super.header().stringToFile()).append("\n");

        // Open Period
        sb.append(openPeriod.stringToFile()).append("\n");


        // Exam Sections
        for (ExamSection section : super.sections()) {
            sb.append("\n").append(section.stringToFile()).append("\n");
        }

        return sb.toString();

    }

    @Override
    public void updateAll(AutomatedExam automatedExam) {
        super.updateAll(automatedExam);
        this.openPeriod = automatedExam.openPeriod;
    }


    /**
     * The period of time during which the exam is open.
     *
     * @return the exam open period
     */
    public ExamOpenPeriod openPeriod() {
        return openPeriod;
    }


}
