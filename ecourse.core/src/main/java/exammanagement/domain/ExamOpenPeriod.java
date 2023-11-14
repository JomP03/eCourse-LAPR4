package exammanagement.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Embeddable
public class ExamOpenPeriod {

    @Column
    private LocalDateTime openDate;

    @Column
    private LocalDateTime closeDate;

    public ExamOpenPeriod(LocalDateTime openDate, LocalDateTime closeDate) {

        if(openDate == null || closeDate == null){
            throw new IllegalArgumentException("open and close dates must not be null.");
        }

        if(openDate.isAfter(closeDate)){
            throw new IllegalArgumentException("open date must be before close date.");
        }

        if(closeDate.isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("close date must be in the future.");
        }

        this.openDate = openDate;
        this.closeDate = closeDate;
    }

    protected ExamOpenPeriod() {
        // for ORM only
    }

    public LocalDateTime openDate(){
        return this.openDate;
    }

    public LocalDateTime closeDate(){
        return this.closeDate;
    }

    @Override
    public String toString() {
        return "ExamOpenPeriod{" +
                "openDate=" + openDate +
                ", closeDate=" + closeDate +
                '}';
    }

    public String stringToFile() {
        StringBuilder sb = new StringBuilder();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH-mm");
        String formattedOpenDate = openDate.format(formatter);
        String formattedCloseDate = closeDate.format(formatter);


        sb.append("OPENDATE:").append(formattedOpenDate).append("\n").append("CLOSEDATE:").append(formattedCloseDate).append("\n");



        return sb.toString();
    }
}
