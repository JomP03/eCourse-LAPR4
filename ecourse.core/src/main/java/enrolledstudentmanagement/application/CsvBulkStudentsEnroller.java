package enrolledstudentmanagement.application;

import coursemanagement.domain.Course;
import ecourseusermanagement.domain.ECourseUser;
import ecourseusermanagement.repositories.IeCourseUserRepository;
import enrolledstudentmanagement.dto.BulkEnrollStudentsReport;
import enrolledstudentmanagement.repository.EnrolledStudentRepository;

import java.io.*;

public class CsvBulkStudentsEnroller implements IcsvBulkStudentsEnroller {
    private final String HEADER = "student";
    private final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private final IeCourseUserRepository eCourseUserRepository;
    private final RegisterEnrolledStudentController registerEnrolledStudentController;

    /**
     * Instantiates a service to bulk enroll students from a CSV file.
     *
     * @param eCourseUserRepository the eCourseUser repository
     */
    public CsvBulkStudentsEnroller(IeCourseUserRepository eCourseUserRepository,
                                   EnrolledStudentRepository enrolledStudentRepository) {
        // Assure that the eCourseUserRepository is not null
        if (eCourseUserRepository == null) {
            throw new IllegalArgumentException("A User repository must be provided.");
        }
        this.eCourseUserRepository = eCourseUserRepository;

        // Assure that the enrolledStudentRepository is not null
        if (enrolledStudentRepository == null) {
            throw new IllegalArgumentException("An EnrolledStudent repository must be provided.");
        }
        this.registerEnrolledStudentController = new RegisterEnrolledStudentController(enrolledStudentRepository);
    }


    @Override
    public BulkEnrollStudentsReport bulkEnrollStudents(Course course, File csvFile) {
        BulkEnrollStudentsReport report = new BulkEnrollStudentsReport();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(csvFile));
            assureTheFileIsValid(csvFile, reader);

            String line;
            while ((line = reader.readLine()) != null) {
                report.numberOfTotalLines++;
                if (!isLineContentAnEmail(line)) {
                    report.addNotImportedLine("Invalid email format.");
                    continue;
                }

                // Check if there is a student with the provided email
                ECourseUser student = eCourseUserRepository.findByEmail(line).orElse(null);
                if (student == null) {
                    report.addNotImportedLine("There is no student with the provided email.");
                    continue;
                }

                // Try to enroll the student
                try {
                    registerEnrolledStudentController.registerEnrolledStudent(course, student);
                    report.numberOfEnrollmentsCreated++;
                } catch (Exception e) {
                    if (e.getMessage().equals("The student is already enrolled in the course.")) {
                        report.numberOfStudentsAlreadyEnrolled++;
                    } else {
                        report.addNotImportedLine((e.getMessage()));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the provided file.");
        }

        return report;
    }

    private boolean isLineContentAnEmail(String line) {
        return line.matches(EMAIL_REGEX);
    }

    private void assureTheFileIsValid(File csvFile, BufferedReader reader) {
        if (!csvFile.getName().toLowerCase().endsWith(".csv")) {
            throw new RuntimeException("The provided file is not a CSV file.");
        }
        try {
            String header = reader.readLine();
            if (!header.equals(HEADER)) {
                throw new RuntimeException("The provided file is not valid.");
            }
        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the provided file.");
        }
    }
}
