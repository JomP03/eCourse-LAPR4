// Generated from C:/Repo/sem4pi-22-23-16-1/ecourse.core/src/main/java/exammanagement/application/antlr\Exam.g4 by ANTLR 4.12.0
package exammanagement.application.antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ExamParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ExamVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ExamParser#exam}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExam(ExamParser.ExamContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamParser#title}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTitle(ExamParser.TitleContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamParser#header}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHeader(ExamParser.HeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamParser#feedbacktype}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFeedbacktype(ExamParser.FeedbacktypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamParser#gradingtype}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGradingtype(ExamParser.GradingtypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamParser#openDate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpenDate(ExamParser.OpenDateContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamParser#closeDate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCloseDate(ExamParser.CloseDateContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamParser#section}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSection(ExamParser.SectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamParser#sectionheader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSectionheader(ExamParser.SectionheaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamParser#question}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuestion(ExamParser.QuestionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamParser#squest}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSquest(ExamParser.SquestContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamParser#mcquest}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMcquest(ExamParser.McquestContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamParser#mquest}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMquest(ExamParser.MquestContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamParser#bquest}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBquest(ExamParser.BquestContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamParser#nquest}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNquest(ExamParser.NquestContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamParser#mwquest}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMwquest(ExamParser.MwquestContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamParser#mcanswers}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMcanswers(ExamParser.McanswersContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamParser#mwoptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMwoptions(ExamParser.MwoptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExamParser#mwoption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMwoption(ExamParser.MwoptionContext ctx);
}