package takenexammanagement.application.service;

import eapli.framework.validations.*;
import ecourseusermanagement.domain.ECourseUser;
import exammanagement.application.antlr.ExamLexer;
import exammanagement.application.antlr.ExamParser;
import exammanagement.domain.Exam;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import takenexammanagement.domain.TakenExam;
import takenexammanagement.domain.builder.TakenExamBuilder;

import java.io.IOException;
import java.nio.file.Paths;

public class TakenExamHandler {

    private final TakenExamCorrector corrector;

    public TakenExamHandler(TakenExamCorrector corrector) {
        Preconditions.noneNull( corrector);
        this.corrector = corrector;
    }

    /**
     * Parses a taken exam from a file
     *
     * @param path the path to the file
     * @return the parsed taken exam object
     * @throws IOException if the file cannot be read
     */
    public TakenExam createTakenExam(String path, Exam exam, ECourseUser student) throws IOException {

        TakenExamBuilder builder;

        try {
            // Create a CharStream from the file
            CharStream charStream = CharStreams.fromPath(Paths.get(path));

            // Create a lexer using the CharStream
            ExamLexer lexer = new ExamLexer(charStream);

            // Create a token stream from the lexer
            CommonTokenStream tokenStream = new CommonTokenStream(lexer);

            // Create a parser using the token stream
            ExamParser parser = new ExamParser(tokenStream);

            // Generate a parse tree by starting at the exam rule
            ParseTree tree = parser.exam();

            // Create a visitor
            TakenExamVisitor visitor = new TakenExamVisitor(exam);

            // Traverse the parse tree using your visitor
            visitor.visit(tree);

            // Get the builder from the visitor, which is missing the course and the teacher creating it
            builder = visitor.takenExamBuilder();
        } catch (IOException e) {
            throw new IOException("Could not read file");
        } catch (Exception e) {
            throw new IOException("Invalid Exam, please check the if it contains the correct format");
        }

        // Build the exam
        builder.withExam(exam);
        builder.withStudent(student);

        // Return the corrected exam
        return corrector.correctExam(builder.build());
    }

}
