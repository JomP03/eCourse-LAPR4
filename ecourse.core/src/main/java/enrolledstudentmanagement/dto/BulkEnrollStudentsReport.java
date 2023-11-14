package enrolledstudentmanagement.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Dto used to report the result of a bulk enrollment of students.
 */
public class BulkEnrollStudentsReport {

    public int numberOfTotalLines;
    public int numberOfEnrollmentsCreated;
    public int numberOfStudentsAlreadyEnrolled;
    public final List<String> notImportedLines = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("##################### ").append("REPORT").append(" #####################\n")
                .append("Nº of lines: ").append(numberOfTotalLines).append("\n")
                .append("Nº of enrollments created: ").append(numberOfEnrollmentsCreated).append("\n")
                .append("Nº of students already enrolled: ").append(numberOfStudentsAlreadyEnrolled).append("\n")
                .append("Nº of lines not imported: ").append(notImportedLines.size()).append("\n");
        if (!notImportedLines.isEmpty()) {
            for (String line : notImportedLines) {
                sb.append("    -").append(line).append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Tells if the report has errors.
     *
     * @return true if the report has errors, false otherwise
     */
    public boolean hasErrors() {
        return !notImportedLines.isEmpty();
    }

    /**
     * Add not imported line.
     *
     * @param reason the reason why the line was not imported
     */
    public void addNotImportedLine(String reason) {
        notImportedLines.add("Line " + numberOfTotalLines + ": " + reason);
    }
}
