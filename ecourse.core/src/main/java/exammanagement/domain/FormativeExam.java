package exammanagement.domain;

import coursemanagement.domain.*;
import ecourseusermanagement.domain.*;
import questionmanagment.domain.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class FormativeExam extends Exam {

    public FormativeExam(List<ExamSection> sections, ExamHeader header, String title,
                         ECourseUser creator, Course course) {
        super(sections, header, title, creator, course);
    }

    protected FormativeExam() {
        // for ORM
    }


    /**
     * Updates the questions of the selected section.
     * @param section the section to update
     * @param questions the new questions
     */
    public void updateSectionQuestions(ExamSection section, List<Question> questions) {
        super.updateSectionQuestions(section, questions);
    }


    @Override
    public String stringToFile() {
        StringBuilder sb = new StringBuilder();


        // Exam Header
        sb.append("TITLE:" + super.title()).append("\n").append(super.header().stringToFile()).append("\n");


        // Exam Sections
        for (ExamSection section : super.sections()) {
            sb.append("\n").append(section.stringToFile()).append("\n");
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return super.title();
    }

    public String printExam() {
        return "===========\n|Formative|\n===========\n\n" + super.toString();
    }
}
