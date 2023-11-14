package exammanagement.application.service;

import coursemanagement.domain.Course;
import ecourseusermanagement.domain.ECourseUser;
import exammanagement.application.antlr.ExamLexer;
import exammanagement.application.antlr.ExamParser;
import exammanagement.domain.AutomatedExam;
import exammanagement.domain.Exam;
import exammanagement.domain.builder.AutomatedExamBuilder;
import exammanagement.repository.ExamRepository;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import questionmanagment.domain.Question;

import java.io.IOException;
import java.nio.file.Paths;

public class ExamHandler {

    private final ExamParseTreeVisitor visitor;

    private final ExamRepository examRepository;

    public ExamHandler(ExamRepository examRepository) {

        if(examRepository == null) {
            throw new IllegalArgumentException("Exam repository cannot be null");
        }

        this.examRepository = examRepository;

        this.visitor = new ExamParseTreeVisitor(examRepository);
    }

    /**
     * Parses an automated exam from a file
     *
     * @param path the path to the file
     * @return the parsed exam to an AutomatedExam object
     * @throws IOException if the file cannot be read
     */
    public AutomatedExam parseAutomatedExam(String path, Course selectedCourse, ECourseUser teacher) throws IOException {

        AutomatedExamBuilder builder;

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

            // Traverse the parse tree using your visitor
            visitor.visit(tree);

            // Get the builder from the visitor, which is missing the course and the teacher creating it
            builder = visitor.getExamBuilder();
        } catch (IOException e) {
            throw new IOException("Could not read file");
        } catch (Exception e) {
            throw new IOException("Invalid Exam, please check the if it contains the correct format");
        }

        // Build the exam
        builder.withCreator(teacher);
        builder.withCourse(selectedCourse);
        AutomatedExam exam = builder.build();

        return exam;

    }


    /**
     * Parses a single exam question from a file
     *
     * @param path the path to the file
     * @return the parsed question
     * @throws IOException if the file cannot be read
     */
    public Question parseExamQuestion(String path) throws IOException {
        Question question;

        try {
            // Create a CharStream from the file
            CharStream charStream = CharStreams.fromPath(Paths.get(path));

            // Create a lexer using the CharStream
            ExamLexer lexer = new ExamLexer(charStream);

            // Create a token stream from the lexer
            CommonTokenStream tokenStream = new CommonTokenStream(lexer);

            // Create a parser using the token stream
            ExamParser parser = new ExamParser(tokenStream);

            // Generate a parse tree by starting at Question rule
            ExamParser.QuestionContext tree = parser.question();

            // Traverse the parse tree using your visitor
            visitor.visitQuestion(tree);

            // Get question from visitor
            question = visitor.getQuestion();
        } catch (IOException e) {
            throw new IOException("Could not read file");
        } catch (Exception e) {
            throw new IOException("Invalid Exam, please check the if it contains the correct format");
        }

        return question;

    }

    /**
     * Validates the title of an exam
     * @param title the title to validate
     * @return true if the title is valid
     */
    public boolean validateExamTitle(String title) {
        if(examRepository.findExamByTitle(title) != null){
            throw new IllegalStateException("Exam with this title already exists.");
        }
        return true;
    }


    /**
     * Validates the title of an exam
     * @param newExam the newExam to validate
     * @param ignore the exam to ignore
     * @return true if the title is valid
     */
    public boolean validateUpdateExamTitle(Exam newExam, Exam ignore) {
        if(examRepository.findExamByTitleIgnoringExamBeingUpdated(newExam,ignore) != null){
            return false;
        }
        return true;
    }

}
