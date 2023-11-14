package exammanagement.application.service;

import exammanagement.application.antlr.ExamBaseVisitor;
import exammanagement.application.antlr.ExamParser;
import exammanagement.domain.*;
import exammanagement.repository.ExamRepository;
import questionmanagment.domain.BooleanQuestion;
import exammanagement.domain.builder.AutomatedExamBuilder;
import questionmanagment.domain.MatchingQuestion;
import questionmanagment.domain.MissingWordOption;
import questionmanagment.domain.MissingWordQuestion;
import questionmanagment.domain.MultipleChoiceQuestion;
import questionmanagment.domain.NumericalQuestion;
import questionmanagment.domain.ShortAnswerQuestion;
import questionmanagment.domain.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ExamParseTreeVisitor extends ExamBaseVisitor<Void> {

    private AutomatedExamBuilder examBuilder;

    private Question question;

    public ExamParseTreeVisitor(ExamRepository examRepository) {
        this.examBuilder = new AutomatedExamBuilder(examRepository);
    }

    public Question getQuestion() {
        return question;
    }


    public AutomatedExamBuilder getExamBuilder() {
        return examBuilder;
    }

    @Override
    public Void visitExam(ExamParser.ExamContext ctx) {
        //Visit title
        examBuilder.withExamTitle(ctx.title().TEXT().getText());

        // Visit header
        visitExamHeader(ctx.header(), ctx.feedbacktype(), ctx.gradingtype());

        // Visit openDate
        visitOpenPeriod(ctx.openDate(), ctx.closeDate());

        // Visit sections
        for (ExamParser.SectionContext section : ctx.section()) {
            examBuilder.withSection(visitExamSection(section));
        }

        return null;
    }


    public void visitExamHeader(ExamParser.HeaderContext ctx, ExamParser.FeedbacktypeContext ctx2, ExamParser.GradingtypeContext ctx3) {
        String description = ctx.TEXT().getText();
        FeedBackType feedbackType = FeedBackType.valueOf(ctx2.TEXT().getText());
        GradingType gradingType = GradingType.valueOf(ctx3.TEXT().getText());
        examBuilder.withHeader(description, gradingType, feedbackType);

    }

    public void visitOpenPeriod(ExamParser.OpenDateContext ctx, ExamParser.CloseDateContext ctx2) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH-mm");


        LocalDateTime openDate = LocalDateTime.parse(ctx.TEXT().getText(), formatter);
        LocalDateTime closeDate = LocalDateTime.parse(ctx2.TEXT().getText(), formatter);


        examBuilder.withOpenDate(openDate);
        examBuilder.withCloseDate(closeDate);

    }

    public ExamSection visitExamSection(ExamParser.SectionContext ctx) {

        // Visit Section Header
        String description = ctx.sectionheader().TEXT().getText();

        // Visit Questions
        List<Question> questions = new ArrayList<>();

        for (ExamParser.QuestionContext question : ctx.question()) {
            if(question.squest() != null) {
                ShortAnswerQuestion sa = visitShortAnswerQuestion(question.squest());
                questions.add(sa);
            } else if (question.mcquest() != null) {
                MultipleChoiceQuestion mc = visitMultipleChoiceQuestion(question.mcquest());
                questions.add(mc);
            } else if (question.mquest() != null) {
                MatchingQuestion m = visitMatchingQuestion(question.mquest());
                questions.add(m);
            } else if (question.bquest() != null) {
                BooleanQuestion b = visitBooleanQuestion(question.bquest());
                questions.add(b);
            } else if (question.nquest() != null) {
                NumericalQuestion n = visitNumericalQuestion(question.nquest());
                questions.add(n);
            } else if (question.mwquest() != null) {
                MissingWordQuestion mw = visitMissingWordQuestion(question.mwquest());
                questions.add(mw);
            } else {
                throw new IllegalArgumentException("Question type is not supported");
            }
        }

        return new ExamSection(description, questions);
    }

    /**
     * Visits a question
     * @param ctx the question
     * @return the question object that was visited
     */
    @Override
    public Void visitQuestion(ExamParser.QuestionContext ctx) {
        if(ctx.squest() != null) {
             question = visitShortAnswerQuestion(ctx.squest());
        } else if (ctx.mcquest() != null) {
            question = visitMultipleChoiceQuestion(ctx.mcquest());
        } else if (ctx.mquest() != null) {
            question = visitMatchingQuestion(ctx.mquest());
        } else if (ctx.bquest() != null) {
            question = visitBooleanQuestion(ctx.bquest());
        } else if (ctx.nquest() != null) {
            question = visitNumericalQuestion(ctx.nquest());
        } else if (ctx.mwquest() != null) {
            question = visitMissingWordQuestion(ctx.mwquest());
        } else {
            throw new IllegalArgumentException("Question type is not supported");
        }
        return null;
    }

    /**
     * Visit a missing word question
     * @param mwquest the missing word question
     * @return the missing word question
     */
    public MissingWordQuestion visitMissingWordQuestion(ExamParser.MwquestContext mwquest) {

            String question = mwquest.TEXT(0).getText();

            Float quotation = Float.parseFloat(mwquest.TEXT(1).getText());

            List<MissingWordOption> options = new ArrayList<>();

            for(int i = 0; i < mwquest.mwoptions().size(); i++){
                options.add(visitMissingWordOption(mwquest.mwoptions(i)));
            }

            return new MissingWordQuestion(question, options, 1.0f, quotation);
    }

    /**
     * Visits a missing word option
     * @param mwoptions the missing word option
     * @return the missing word option
     */
    private MissingWordOption visitMissingWordOption(ExamParser.MwoptionsContext mwoptions) {

            String answer = mwoptions.TEXT().getText().trim();

            List<String> options = new ArrayList<>();

            for(int i = 0; i < mwoptions.mwoption().size(); i++){
                options.add(mwoptions.mwoption(i).TEXT().getText().trim());
            }

            return new MissingWordOption(options, answer);
    }

    /**
     * Visit a numerical question
     * @param nquest the numerical question
     * @return the numerical question
     */
    public NumericalQuestion visitNumericalQuestion(ExamParser.NquestContext nquest) {

            String question = nquest.TEXT(0).getText();

            String answer = nquest.TEXT(1).getText().trim();

            Float quotation = Float.parseFloat(nquest.TEXT(2).getText());

            return new NumericalQuestion(question, answer,  1.0f, quotation);

    }

    /**
     * Visit a boolean question
     * @param bquest the boolean question
     * @return the boolean question
     */
    public BooleanQuestion visitBooleanQuestion(ExamParser.BquestContext bquest) {

        String question = bquest.TEXT(0).getText();

        Boolean answer = Boolean.parseBoolean(bquest.TEXT(1).getText().trim());

        Float quotation = Float.parseFloat(bquest.TEXT(2).getText());

        return new BooleanQuestion(question, answer, 1.0f, quotation);
    }

    /**
     * Visit a matching question
     * @param mquest the matching question
     * @return the matching question
     */
    public MatchingQuestion visitMatchingQuestion(ExamParser.MquestContext mquest) {

        List<String> leftOptions = new ArrayList<>();

        List<String> rightOptions = new ArrayList<>();

        String question = mquest.TEXT(0).toString();

        String matches = mquest.TEXT(1).toString();

        List<String> matchingwordsList = Arrays.asList(matches.split(","));

        for(String matchingword : matchingwordsList){
            String[] parts = matchingword.split("-");
            leftOptions.add(parts[0].trim());
            rightOptions.add(parts[1].trim());
        }

        Float quotation = Float.parseFloat(mquest.TEXT(2).toString());



        return new MatchingQuestion(question, 1.0f, quotation, leftOptions, rightOptions);
    }

    /**
     * Visit a multiple choice question
     * @param mcquest the multiple choice question
     * @return the multiple choice question
     */
    public MultipleChoiceQuestion visitMultipleChoiceQuestion(ExamParser.McquestContext mcquest) {

        String question = mcquest.TEXT(0).getText();

        String answer = mcquest.TEXT(1).getText().trim();

        Float quotation = Float.parseFloat(mcquest.TEXT(2).getText());

        List<String> options = new ArrayList<>();
        for (int i = 0; i < mcquest.mcanswers().size(); i++) {
            String option = mcquest.mcanswers(i).TEXT().getText().trim();
            options.add(option);
        }

        return new MultipleChoiceQuestion(question, options, answer, 1.0f, quotation);
    }

    /**
     * Visit a short answer question
     * @param squest the short answer question
     * @return the short answer question
     */
    public ShortAnswerQuestion visitShortAnswerQuestion(ExamParser.SquestContext squest) {

        String question = squest.TEXT(0).getText();

        String answer = squest.TEXT(1).getText().trim();

        Float quotation = Float.parseFloat(squest.TEXT(2).getText());

        return new ShortAnswerQuestion(question, answer, 1.0f, quotation);
    }


}
