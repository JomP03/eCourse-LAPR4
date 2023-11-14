package takenexammanagement.application.service;

import exammanagement.application.antlr.ExamBaseVisitor;
import exammanagement.application.antlr.ExamParser;
import exammanagement.domain.Exam;
import takenexammanagement.domain.AnsweredQuestion;
import takenexammanagement.domain.builder.TakenExamBuilder;

import java.util.*;

public class TakenExamVisitor extends ExamBaseVisitor<Void> {

    private Exam exam;

    private TakenExamBuilder takenExamBuilder;

    public TakenExamVisitor(Exam exam) {
        this.takenExamBuilder = new TakenExamBuilder();
        if(exam == null){
            throw new IllegalArgumentException("Exam must not be null.");
        }
        this.exam = exam;
    }

    public TakenExamBuilder takenExamBuilder() {
        return takenExamBuilder;
    }

    @Override
    public Void visitExam(ExamParser.ExamContext ctx) {

        //Visit the questions
        for(ExamParser.QuestionContext question : ctx.question()) {
            AnsweredQuestion answeredQuestion = visitAnsweredQuestion(question);
            takenExamBuilder.addAnsweredQuestion(answeredQuestion);
        }


        return null;
    }

    /**
     * Visit a answered question
     * @param question the answered question to visit
     * @return the answered question object
     */
    private AnsweredQuestion visitAnsweredQuestion(ExamParser.QuestionContext question) {

        if(question.squest() != null){
            return visitShortAnswerQuestion(question.squest());
        } else if(question.mcquest() != null){
            return visitMultipleChoiceQuestion(question.mcquest());
        } else if(question.nquest() != null){
            return visitNumericalQuestion(question.nquest());
        } else if(question.bquest() != null){
            return visitBooleanQuestion(question.bquest());
        } else if(question.mwquest() != null){
            return visitMissingWordQuestion(question.mwquest());
        } else if(question.mquest() != null){
            return visitMatchingQuestion(question.mquest());
        } else{
            throw new IllegalArgumentException("Question type not supported");
        }

    }

    private AnsweredQuestion visitMissingWordQuestion(ExamParser.MwquestContext mwquest) {

        //Get the question answer
        StringBuilder answer = null;
        boolean first = true;
        for(ExamParser.MwoptionContext option : mwquest.mwoption()){
            if(first){
                answer = new StringBuilder(option.TEXT().getText().trim());
                first = false;
                continue;
            }
            answer.append(",").append(option.TEXT().getText().trim());
        }

        if(answer == null)
            throw new IllegalArgumentException("Missing word question must have at least one answer");

        //Get the question position
        String position = mwquest.TEXT(1).getText();

        //Get section number
        Integer section = Integer.parseInt(String.valueOf(position.charAt(3)));

        //Get question number
        Integer questionNumber = Integer.parseInt(String.valueOf(position.charAt(5)));

        //Get the question entry
        String entry = "Section " + section + " | Question " + questionNumber + ": ";

        //Get the question text
        String questionText = entry.concat(exam.obtainQuestion(section-1, questionNumber-1).question());



        return new AnsweredQuestion(questionText, answer.toString(), section, questionNumber);
    }

    private AnsweredQuestion visitMatchingQuestion(ExamParser.MquestContext mquest) {
        //Get the question answer
        String answer = mquest.TEXT(1).getText().trim();

        //Get the question position
        String position = mquest.TEXT(2).getText();

        //Get section number
        Integer section = Integer.parseInt(String.valueOf(position.charAt(3)));

        //Get question number
        Integer questionNumber = Integer.parseInt(String.valueOf(position.charAt(5)));

        //Get the question entry
        String entry = "Section " + section + " | Question " + questionNumber + ": ";

        //Get the question text
        String questionText = entry.concat(exam.obtainQuestion(section-1, questionNumber-1).question());

        return new AnsweredQuestion(questionText, answer, section, questionNumber);
    }

    private AnsweredQuestion visitBooleanQuestion(ExamParser.BquestContext bquest) {

        //Get the question answer
        String answer = bquest.TEXT(1).getText().trim();

        //Get the question position
        String position = bquest.TEXT(2).getText();

        //Get section number
        Integer section = Integer.parseInt(String.valueOf(position.charAt(3)));

        //Get question number
        Integer questionNumber = Integer.parseInt(String.valueOf(position.charAt(5)));

        //Get the question entry
        String entry = "Section " + section + " | Question " + questionNumber + ": ";

        //Get the question text
        String questionText = entry.concat(exam.obtainQuestion(section-1, questionNumber-1).question());

        return new AnsweredQuestion(questionText, answer, section, questionNumber);
    }

    private AnsweredQuestion visitNumericalQuestion(ExamParser.NquestContext nquest) {

        //Get the question answer
        String answer = nquest.TEXT(1).getText().trim();

        //Get the question position
        String position = nquest.TEXT(2).getText();

        //Get section number
        Integer section = Integer.parseInt(String.valueOf(position.charAt(3)));

        //Get question number
        Integer questionNumber = Integer.parseInt(String.valueOf(position.charAt(5)));

        //Get the question entry
        String entry = "Section " + section + " | Question " + questionNumber + ": ";

        //Get the question text
        String questionText = entry.concat(exam.obtainQuestion(section-1, questionNumber-1).question());

        return new AnsweredQuestion(questionText, answer, section, questionNumber);
    }

    private AnsweredQuestion visitMultipleChoiceQuestion(ExamParser.McquestContext mcquest) {

        //Get the question answer
        String answer = mcquest.TEXT(1).getText().trim();

        //Get the question position
        String position = mcquest.TEXT(2).getText();

        //Get section number
        Integer section = Integer.parseInt(String.valueOf(position.charAt(3)));

        //Get question number
        Integer questionNumber = Integer.parseInt(String.valueOf(position.charAt(5)));

        //Get the question entry
        String entry = "Section " + section + " | Question " + questionNumber + ": ";

        //Get the question text
        String questionText = entry.concat(exam.obtainQuestion(section-1, questionNumber-1).question());

        return new AnsweredQuestion(questionText, answer, section, questionNumber);
    }

    private AnsweredQuestion visitShortAnswerQuestion(ExamParser.SquestContext squest) {

        //Get the question answer
        String answer = squest.TEXT(1).getText().trim();

        //Get the question position
        String position = squest.TEXT(2).getText();

        //Get section number
        Integer section = Integer.parseInt(String.valueOf(position.charAt(3)));

        //Get question number
        Integer questionNumber = Integer.parseInt(String.valueOf(position.charAt(5)));

        //Get the question entry
        String entry = "Section " + section + " | Question " + questionNumber + ": ";

        //Get the question text
        String questionText = entry.concat(exam.obtainQuestion(section-1, questionNumber-1).question());

        return new AnsweredQuestion(questionText, answer, section, questionNumber);
    }

}
