// Generated from C:/Repo/sem4pi-22-23-16-1/ecourse.core/src/main/java/exammanagement/application/antlr\Exam.g4 by ANTLR 4.12.0
package exammanagement.application.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ExamParser}.
 */
public interface ExamListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ExamParser#exam}.
	 * @param ctx the parse tree
	 */
	void enterExam(ExamParser.ExamContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamParser#exam}.
	 * @param ctx the parse tree
	 */
	void exitExam(ExamParser.ExamContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamParser#title}.
	 * @param ctx the parse tree
	 */
	void enterTitle(ExamParser.TitleContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamParser#title}.
	 * @param ctx the parse tree
	 */
	void exitTitle(ExamParser.TitleContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamParser#header}.
	 * @param ctx the parse tree
	 */
	void enterHeader(ExamParser.HeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamParser#header}.
	 * @param ctx the parse tree
	 */
	void exitHeader(ExamParser.HeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamParser#feedbacktype}.
	 * @param ctx the parse tree
	 */
	void enterFeedbacktype(ExamParser.FeedbacktypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamParser#feedbacktype}.
	 * @param ctx the parse tree
	 */
	void exitFeedbacktype(ExamParser.FeedbacktypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamParser#gradingtype}.
	 * @param ctx the parse tree
	 */
	void enterGradingtype(ExamParser.GradingtypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamParser#gradingtype}.
	 * @param ctx the parse tree
	 */
	void exitGradingtype(ExamParser.GradingtypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamParser#openDate}.
	 * @param ctx the parse tree
	 */
	void enterOpenDate(ExamParser.OpenDateContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamParser#openDate}.
	 * @param ctx the parse tree
	 */
	void exitOpenDate(ExamParser.OpenDateContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamParser#closeDate}.
	 * @param ctx the parse tree
	 */
	void enterCloseDate(ExamParser.CloseDateContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamParser#closeDate}.
	 * @param ctx the parse tree
	 */
	void exitCloseDate(ExamParser.CloseDateContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamParser#section}.
	 * @param ctx the parse tree
	 */
	void enterSection(ExamParser.SectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamParser#section}.
	 * @param ctx the parse tree
	 */
	void exitSection(ExamParser.SectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamParser#sectionheader}.
	 * @param ctx the parse tree
	 */
	void enterSectionheader(ExamParser.SectionheaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamParser#sectionheader}.
	 * @param ctx the parse tree
	 */
	void exitSectionheader(ExamParser.SectionheaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamParser#question}.
	 * @param ctx the parse tree
	 */
	void enterQuestion(ExamParser.QuestionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamParser#question}.
	 * @param ctx the parse tree
	 */
	void exitQuestion(ExamParser.QuestionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamParser#squest}.
	 * @param ctx the parse tree
	 */
	void enterSquest(ExamParser.SquestContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamParser#squest}.
	 * @param ctx the parse tree
	 */
	void exitSquest(ExamParser.SquestContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamParser#mcquest}.
	 * @param ctx the parse tree
	 */
	void enterMcquest(ExamParser.McquestContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamParser#mcquest}.
	 * @param ctx the parse tree
	 */
	void exitMcquest(ExamParser.McquestContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamParser#mquest}.
	 * @param ctx the parse tree
	 */
	void enterMquest(ExamParser.MquestContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamParser#mquest}.
	 * @param ctx the parse tree
	 */
	void exitMquest(ExamParser.MquestContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamParser#bquest}.
	 * @param ctx the parse tree
	 */
	void enterBquest(ExamParser.BquestContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamParser#bquest}.
	 * @param ctx the parse tree
	 */
	void exitBquest(ExamParser.BquestContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamParser#nquest}.
	 * @param ctx the parse tree
	 */
	void enterNquest(ExamParser.NquestContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamParser#nquest}.
	 * @param ctx the parse tree
	 */
	void exitNquest(ExamParser.NquestContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamParser#mwquest}.
	 * @param ctx the parse tree
	 */
	void enterMwquest(ExamParser.MwquestContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamParser#mwquest}.
	 * @param ctx the parse tree
	 */
	void exitMwquest(ExamParser.MwquestContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamParser#mcanswers}.
	 * @param ctx the parse tree
	 */
	void enterMcanswers(ExamParser.McanswersContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamParser#mcanswers}.
	 * @param ctx the parse tree
	 */
	void exitMcanswers(ExamParser.McanswersContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamParser#mwoptions}.
	 * @param ctx the parse tree
	 */
	void enterMwoptions(ExamParser.MwoptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamParser#mwoptions}.
	 * @param ctx the parse tree
	 */
	void exitMwoptions(ExamParser.MwoptionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExamParser#mwoption}.
	 * @param ctx the parse tree
	 */
	void enterMwoption(ExamParser.MwoptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExamParser#mwoption}.
	 * @param ctx the parse tree
	 */
	void exitMwoption(ExamParser.MwoptionContext ctx);
}