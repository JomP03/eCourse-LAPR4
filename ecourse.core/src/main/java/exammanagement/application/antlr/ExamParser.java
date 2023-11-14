// Generated from C:/Repo/sem4pi-22-23-16-1/ecourse.core/src/main/java/exammanagement/application/antlr\Exam.g4 by ANTLR 4.12.0
package exammanagement.application.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class ExamParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.12.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, TEXT=22, WS=23;
	public static final int
		RULE_exam = 0, RULE_title = 1, RULE_header = 2, RULE_feedbacktype = 3, 
		RULE_gradingtype = 4, RULE_openDate = 5, RULE_closeDate = 6, RULE_section = 7, 
		RULE_sectionheader = 8, RULE_question = 9, RULE_squest = 10, RULE_mcquest = 11, 
		RULE_mquest = 12, RULE_bquest = 13, RULE_nquest = 14, RULE_mwquest = 15, 
		RULE_mcanswers = 16, RULE_mwoptions = 17, RULE_mwoption = 18;
	private static String[] makeRuleNames() {
		return new String[] {
			"exam", "title", "header", "feedbacktype", "gradingtype", "openDate", 
			"closeDate", "section", "sectionheader", "question", "squest", "mcquest", 
			"mquest", "bquest", "nquest", "mwquest", "mcanswers", "mwoptions", "mwoption"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'TITLE:'", "'HEADER:'", "'FEEDBACK:'", "'GRADINGTYPE:'", "'OPENDATE:'", 
			"'CLOSEDATE:'", "'SECTIONHEADER:'", "'SHORT_ANSWER:'", "'ANSW:'", "'GRADE:'", 
			"'SHORTANSWER:'", "'POSITION:'", "'MULTIPLE_CHOICE:'", "'CORANSW:'", 
			"'MULTIPLECHOICE:'", "'MATCHING:'", "'MATCHES:'", "'BOOLEAN:'", "'NUMERICAL:'", 
			"'MISSING_WORD:'", "'MISSINGWORD:'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, "TEXT", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Exam.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ExamParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExamContext extends ParserRuleContext {
		public TitleContext title() {
			return getRuleContext(TitleContext.class,0);
		}
		public HeaderContext header() {
			return getRuleContext(HeaderContext.class,0);
		}
		public FeedbacktypeContext feedbacktype() {
			return getRuleContext(FeedbacktypeContext.class,0);
		}
		public GradingtypeContext gradingtype() {
			return getRuleContext(GradingtypeContext.class,0);
		}
		public OpenDateContext openDate() {
			return getRuleContext(OpenDateContext.class,0);
		}
		public CloseDateContext closeDate() {
			return getRuleContext(CloseDateContext.class,0);
		}
		public List<SectionContext> section() {
			return getRuleContexts(SectionContext.class);
		}
		public SectionContext section(int i) {
			return getRuleContext(SectionContext.class,i);
		}
		public List<QuestionContext> question() {
			return getRuleContexts(QuestionContext.class);
		}
		public QuestionContext question(int i) {
			return getRuleContext(QuestionContext.class,i);
		}
		public ExamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exam; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).enterExam(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).exitExam(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamVisitor ) return ((ExamVisitor<? extends T>)visitor).visitExam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExamContext exam() throws RecognitionException {
		ExamContext _localctx = new ExamContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_exam);
		int _la;
		try {
			setState(54);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				enterOuterAlt(_localctx, 1);
				{
				setState(38);
				title();
				setState(39);
				header();
				setState(40);
				feedbacktype();
				setState(41);
				gradingtype();
				setState(42);
				openDate();
				setState(43);
				closeDate();
				setState(45); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(44);
					section();
					}
					}
					setState(47); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__6 );
				}
				break;
			case T__7:
			case T__10:
			case T__12:
			case T__14:
			case T__15:
			case T__17:
			case T__18:
			case T__19:
			case T__20:
				enterOuterAlt(_localctx, 2);
				{
				setState(50); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(49);
					question();
					}
					}
					setState(52); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 4040960L) != 0) );
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TitleContext extends ParserRuleContext {
		public TerminalNode TEXT() { return getToken(ExamParser.TEXT, 0); }
		public TitleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_title; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).enterTitle(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).exitTitle(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamVisitor ) return ((ExamVisitor<? extends T>)visitor).visitTitle(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TitleContext title() throws RecognitionException {
		TitleContext _localctx = new TitleContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_title);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
			match(T__0);
			setState(57);
			match(TEXT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class HeaderContext extends ParserRuleContext {
		public TerminalNode TEXT() { return getToken(ExamParser.TEXT, 0); }
		public HeaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_header; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).enterHeader(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).exitHeader(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamVisitor ) return ((ExamVisitor<? extends T>)visitor).visitHeader(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HeaderContext header() throws RecognitionException {
		HeaderContext _localctx = new HeaderContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_header);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(59);
			match(T__1);
			setState(60);
			match(TEXT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FeedbacktypeContext extends ParserRuleContext {
		public TerminalNode TEXT() { return getToken(ExamParser.TEXT, 0); }
		public FeedbacktypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_feedbacktype; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).enterFeedbacktype(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).exitFeedbacktype(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamVisitor ) return ((ExamVisitor<? extends T>)visitor).visitFeedbacktype(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FeedbacktypeContext feedbacktype() throws RecognitionException {
		FeedbacktypeContext _localctx = new FeedbacktypeContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_feedbacktype);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			match(T__2);
			setState(63);
			match(TEXT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class GradingtypeContext extends ParserRuleContext {
		public TerminalNode TEXT() { return getToken(ExamParser.TEXT, 0); }
		public GradingtypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gradingtype; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).enterGradingtype(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).exitGradingtype(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamVisitor ) return ((ExamVisitor<? extends T>)visitor).visitGradingtype(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GradingtypeContext gradingtype() throws RecognitionException {
		GradingtypeContext _localctx = new GradingtypeContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_gradingtype);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(65);
			match(T__3);
			setState(66);
			match(TEXT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class OpenDateContext extends ParserRuleContext {
		public TerminalNode TEXT() { return getToken(ExamParser.TEXT, 0); }
		public OpenDateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_openDate; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).enterOpenDate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).exitOpenDate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamVisitor ) return ((ExamVisitor<? extends T>)visitor).visitOpenDate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OpenDateContext openDate() throws RecognitionException {
		OpenDateContext _localctx = new OpenDateContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_openDate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(68);
			match(T__4);
			setState(69);
			match(TEXT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CloseDateContext extends ParserRuleContext {
		public TerminalNode TEXT() { return getToken(ExamParser.TEXT, 0); }
		public CloseDateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_closeDate; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).enterCloseDate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).exitCloseDate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamVisitor ) return ((ExamVisitor<? extends T>)visitor).visitCloseDate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CloseDateContext closeDate() throws RecognitionException {
		CloseDateContext _localctx = new CloseDateContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_closeDate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			match(T__5);
			setState(72);
			match(TEXT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SectionContext extends ParserRuleContext {
		public SectionheaderContext sectionheader() {
			return getRuleContext(SectionheaderContext.class,0);
		}
		public List<QuestionContext> question() {
			return getRuleContexts(QuestionContext.class);
		}
		public QuestionContext question(int i) {
			return getRuleContext(QuestionContext.class,i);
		}
		public SectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_section; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).enterSection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).exitSection(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamVisitor ) return ((ExamVisitor<? extends T>)visitor).visitSection(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SectionContext section() throws RecognitionException {
		SectionContext _localctx = new SectionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_section);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			sectionheader();
			setState(76); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(75);
				question();
				}
				}
				setState(78); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 4040960L) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SectionheaderContext extends ParserRuleContext {
		public TerminalNode TEXT() { return getToken(ExamParser.TEXT, 0); }
		public SectionheaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sectionheader; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).enterSectionheader(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).exitSectionheader(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamVisitor ) return ((ExamVisitor<? extends T>)visitor).visitSectionheader(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SectionheaderContext sectionheader() throws RecognitionException {
		SectionheaderContext _localctx = new SectionheaderContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_sectionheader);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			match(T__6);
			setState(81);
			match(TEXT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QuestionContext extends ParserRuleContext {
		public SquestContext squest() {
			return getRuleContext(SquestContext.class,0);
		}
		public McquestContext mcquest() {
			return getRuleContext(McquestContext.class,0);
		}
		public MquestContext mquest() {
			return getRuleContext(MquestContext.class,0);
		}
		public BquestContext bquest() {
			return getRuleContext(BquestContext.class,0);
		}
		public NquestContext nquest() {
			return getRuleContext(NquestContext.class,0);
		}
		public MwquestContext mwquest() {
			return getRuleContext(MwquestContext.class,0);
		}
		public QuestionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_question; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).enterQuestion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).exitQuestion(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamVisitor ) return ((ExamVisitor<? extends T>)visitor).visitQuestion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuestionContext question() throws RecognitionException {
		QuestionContext _localctx = new QuestionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_question);
		try {
			setState(89);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__7:
			case T__10:
				enterOuterAlt(_localctx, 1);
				{
				setState(83);
				squest();
				}
				break;
			case T__12:
			case T__14:
				enterOuterAlt(_localctx, 2);
				{
				setState(84);
				mcquest();
				}
				break;
			case T__15:
				enterOuterAlt(_localctx, 3);
				{
				setState(85);
				mquest();
				}
				break;
			case T__17:
				enterOuterAlt(_localctx, 4);
				{
				setState(86);
				bquest();
				}
				break;
			case T__18:
				enterOuterAlt(_localctx, 5);
				{
				setState(87);
				nquest();
				}
				break;
			case T__19:
			case T__20:
				enterOuterAlt(_localctx, 6);
				{
				setState(88);
				mwquest();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SquestContext extends ParserRuleContext {
		public List<TerminalNode> TEXT() { return getTokens(ExamParser.TEXT); }
		public TerminalNode TEXT(int i) {
			return getToken(ExamParser.TEXT, i);
		}
		public SquestContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_squest; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).enterSquest(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).exitSquest(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamVisitor ) return ((ExamVisitor<? extends T>)visitor).visitSquest(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SquestContext squest() throws RecognitionException {
		SquestContext _localctx = new SquestContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_squest);
		try {
			setState(103);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__7:
				enterOuterAlt(_localctx, 1);
				{
				setState(91);
				match(T__7);
				setState(92);
				match(TEXT);
				setState(93);
				match(T__8);
				setState(94);
				match(TEXT);
				setState(95);
				match(T__9);
				setState(96);
				match(TEXT);
				}
				break;
			case T__10:
				enterOuterAlt(_localctx, 2);
				{
				setState(97);
				match(T__10);
				setState(98);
				match(TEXT);
				setState(99);
				match(T__8);
				setState(100);
				match(TEXT);
				setState(101);
				match(T__11);
				setState(102);
				match(TEXT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class McquestContext extends ParserRuleContext {
		public List<TerminalNode> TEXT() { return getTokens(ExamParser.TEXT); }
		public TerminalNode TEXT(int i) {
			return getToken(ExamParser.TEXT, i);
		}
		public List<McanswersContext> mcanswers() {
			return getRuleContexts(McanswersContext.class);
		}
		public McanswersContext mcanswers(int i) {
			return getRuleContext(McanswersContext.class,i);
		}
		public McquestContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mcquest; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).enterMcquest(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).exitMcquest(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamVisitor ) return ((ExamVisitor<? extends T>)visitor).visitMcquest(this);
			else return visitor.visitChildren(this);
		}
	}

	public final McquestContext mcquest() throws RecognitionException {
		McquestContext _localctx = new McquestContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_mcquest);
		int _la;
		try {
			setState(122);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__12:
				enterOuterAlt(_localctx, 1);
				{
				setState(105);
				match(T__12);
				setState(106);
				match(TEXT);
				setState(107);
				match(T__13);
				setState(108);
				match(TEXT);
				setState(109);
				match(T__9);
				setState(110);
				match(TEXT);
				setState(112); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(111);
					mcanswers();
					}
					}
					setState(114); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__8 );
				}
				break;
			case T__14:
				enterOuterAlt(_localctx, 2);
				{
				setState(116);
				match(T__14);
				setState(117);
				match(TEXT);
				setState(118);
				match(T__8);
				setState(119);
				match(TEXT);
				setState(120);
				match(T__11);
				setState(121);
				match(TEXT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MquestContext extends ParserRuleContext {
		public List<TerminalNode> TEXT() { return getTokens(ExamParser.TEXT); }
		public TerminalNode TEXT(int i) {
			return getToken(ExamParser.TEXT, i);
		}
		public MquestContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mquest; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).enterMquest(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).exitMquest(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamVisitor ) return ((ExamVisitor<? extends T>)visitor).visitMquest(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MquestContext mquest() throws RecognitionException {
		MquestContext _localctx = new MquestContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_mquest);
		try {
			setState(136);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(124);
				match(T__15);
				setState(125);
				match(TEXT);
				setState(126);
				match(T__16);
				setState(127);
				match(TEXT);
				setState(128);
				match(T__9);
				setState(129);
				match(TEXT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(130);
				match(T__15);
				setState(131);
				match(TEXT);
				setState(132);
				match(T__16);
				setState(133);
				match(TEXT);
				setState(134);
				match(T__11);
				setState(135);
				match(TEXT);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BquestContext extends ParserRuleContext {
		public List<TerminalNode> TEXT() { return getTokens(ExamParser.TEXT); }
		public TerminalNode TEXT(int i) {
			return getToken(ExamParser.TEXT, i);
		}
		public BquestContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bquest; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).enterBquest(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).exitBquest(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamVisitor ) return ((ExamVisitor<? extends T>)visitor).visitBquest(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BquestContext bquest() throws RecognitionException {
		BquestContext _localctx = new BquestContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_bquest);
		try {
			setState(150);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(138);
				match(T__17);
				setState(139);
				match(TEXT);
				setState(140);
				match(T__8);
				setState(141);
				match(TEXT);
				setState(142);
				match(T__9);
				setState(143);
				match(TEXT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(144);
				match(T__17);
				setState(145);
				match(TEXT);
				setState(146);
				match(T__8);
				setState(147);
				match(TEXT);
				setState(148);
				match(T__11);
				setState(149);
				match(TEXT);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NquestContext extends ParserRuleContext {
		public List<TerminalNode> TEXT() { return getTokens(ExamParser.TEXT); }
		public TerminalNode TEXT(int i) {
			return getToken(ExamParser.TEXT, i);
		}
		public NquestContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nquest; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).enterNquest(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).exitNquest(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamVisitor ) return ((ExamVisitor<? extends T>)visitor).visitNquest(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NquestContext nquest() throws RecognitionException {
		NquestContext _localctx = new NquestContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_nquest);
		try {
			setState(164);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(152);
				match(T__18);
				setState(153);
				match(TEXT);
				setState(154);
				match(T__8);
				setState(155);
				match(TEXT);
				setState(156);
				match(T__9);
				setState(157);
				match(TEXT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(158);
				match(T__18);
				setState(159);
				match(TEXT);
				setState(160);
				match(T__8);
				setState(161);
				match(TEXT);
				setState(162);
				match(T__11);
				setState(163);
				match(TEXT);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MwquestContext extends ParserRuleContext {
		public List<TerminalNode> TEXT() { return getTokens(ExamParser.TEXT); }
		public TerminalNode TEXT(int i) {
			return getToken(ExamParser.TEXT, i);
		}
		public List<MwoptionsContext> mwoptions() {
			return getRuleContexts(MwoptionsContext.class);
		}
		public MwoptionsContext mwoptions(int i) {
			return getRuleContext(MwoptionsContext.class,i);
		}
		public List<MwoptionContext> mwoption() {
			return getRuleContexts(MwoptionContext.class);
		}
		public MwoptionContext mwoption(int i) {
			return getRuleContext(MwoptionContext.class,i);
		}
		public MwquestContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mwquest; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).enterMwquest(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).exitMwquest(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamVisitor ) return ((ExamVisitor<? extends T>)visitor).visitMwquest(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MwquestContext mwquest() throws RecognitionException {
		MwquestContext _localctx = new MwquestContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_mwquest);
		int _la;
		try {
			setState(185);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__19:
				enterOuterAlt(_localctx, 1);
				{
				setState(166);
				match(T__19);
				setState(167);
				match(TEXT);
				setState(168);
				match(T__9);
				setState(169);
				match(TEXT);
				setState(171); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(170);
					mwoptions();
					}
					}
					setState(173); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__13 );
				}
				break;
			case T__20:
				enterOuterAlt(_localctx, 2);
				{
				setState(175);
				match(T__20);
				setState(176);
				match(TEXT);
				setState(178); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(177);
					mwoption();
					}
					}
					setState(180); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__8 );
				setState(182);
				match(T__11);
				setState(183);
				match(TEXT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class McanswersContext extends ParserRuleContext {
		public TerminalNode TEXT() { return getToken(ExamParser.TEXT, 0); }
		public McanswersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mcanswers; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).enterMcanswers(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).exitMcanswers(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamVisitor ) return ((ExamVisitor<? extends T>)visitor).visitMcanswers(this);
			else return visitor.visitChildren(this);
		}
	}

	public final McanswersContext mcanswers() throws RecognitionException {
		McanswersContext _localctx = new McanswersContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_mcanswers);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(187);
			match(T__8);
			setState(188);
			match(TEXT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MwoptionsContext extends ParserRuleContext {
		public TerminalNode TEXT() { return getToken(ExamParser.TEXT, 0); }
		public List<MwoptionContext> mwoption() {
			return getRuleContexts(MwoptionContext.class);
		}
		public MwoptionContext mwoption(int i) {
			return getRuleContext(MwoptionContext.class,i);
		}
		public MwoptionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mwoptions; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).enterMwoptions(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).exitMwoptions(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamVisitor ) return ((ExamVisitor<? extends T>)visitor).visitMwoptions(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MwoptionsContext mwoptions() throws RecognitionException {
		MwoptionsContext _localctx = new MwoptionsContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_mwoptions);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(190);
			match(T__13);
			setState(191);
			match(TEXT);
			setState(193); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(192);
				mwoption();
				}
				}
				setState(195); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__8 );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MwoptionContext extends ParserRuleContext {
		public TerminalNode TEXT() { return getToken(ExamParser.TEXT, 0); }
		public MwoptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mwoption; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).enterMwoption(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExamListener ) ((ExamListener)listener).exitMwoption(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExamVisitor ) return ((ExamVisitor<? extends T>)visitor).visitMwoption(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MwoptionContext mwoption() throws RecognitionException {
		MwoptionContext _localctx = new MwoptionContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_mwoption);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(197);
			match(T__8);
			setState(198);
			match(TEXT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u0017\u00c9\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"+
		"\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007"+
		"\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007"+
		"\u0012\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0004\u0000.\b\u0000\u000b\u0000\f\u0000/\u0001\u0000"+
		"\u0004\u00003\b\u0000\u000b\u0000\f\u00004\u0003\u00007\b\u0000\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0007\u0001\u0007\u0004\u0007M\b\u0007\u000b\u0007\f\u0007N\u0001\b\u0001"+
		"\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0003\tZ\b"+
		"\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0003\nh\b\n\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0004\u000bq\b"+
		"\u000b\u000b\u000b\f\u000br\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0003\u000b{\b\u000b\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0003\f\u0089\b\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0003\r\u0097\b\r\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e"+
		"\u00a5\b\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0004\u000f\u00ac\b\u000f\u000b\u000f\f\u000f\u00ad\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0004\u000f\u00b3\b\u000f\u000b\u000f\f\u000f\u00b4"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0003\u000f\u00ba\b\u000f\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0004\u0011"+
		"\u00c2\b\u0011\u000b\u0011\f\u0011\u00c3\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0000\u0000\u0013\u0000\u0002\u0004\u0006\b\n\f\u000e"+
		"\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$\u0000\u0000\u00c8"+
		"\u00006\u0001\u0000\u0000\u0000\u00028\u0001\u0000\u0000\u0000\u0004;"+
		"\u0001\u0000\u0000\u0000\u0006>\u0001\u0000\u0000\u0000\bA\u0001\u0000"+
		"\u0000\u0000\nD\u0001\u0000\u0000\u0000\fG\u0001\u0000\u0000\u0000\u000e"+
		"J\u0001\u0000\u0000\u0000\u0010P\u0001\u0000\u0000\u0000\u0012Y\u0001"+
		"\u0000\u0000\u0000\u0014g\u0001\u0000\u0000\u0000\u0016z\u0001\u0000\u0000"+
		"\u0000\u0018\u0088\u0001\u0000\u0000\u0000\u001a\u0096\u0001\u0000\u0000"+
		"\u0000\u001c\u00a4\u0001\u0000\u0000\u0000\u001e\u00b9\u0001\u0000\u0000"+
		"\u0000 \u00bb\u0001\u0000\u0000\u0000\"\u00be\u0001\u0000\u0000\u0000"+
		"$\u00c5\u0001\u0000\u0000\u0000&\'\u0003\u0002\u0001\u0000\'(\u0003\u0004"+
		"\u0002\u0000()\u0003\u0006\u0003\u0000)*\u0003\b\u0004\u0000*+\u0003\n"+
		"\u0005\u0000+-\u0003\f\u0006\u0000,.\u0003\u000e\u0007\u0000-,\u0001\u0000"+
		"\u0000\u0000./\u0001\u0000\u0000\u0000/-\u0001\u0000\u0000\u0000/0\u0001"+
		"\u0000\u0000\u000007\u0001\u0000\u0000\u000013\u0003\u0012\t\u000021\u0001"+
		"\u0000\u0000\u000034\u0001\u0000\u0000\u000042\u0001\u0000\u0000\u0000"+
		"45\u0001\u0000\u0000\u000057\u0001\u0000\u0000\u00006&\u0001\u0000\u0000"+
		"\u000062\u0001\u0000\u0000\u00007\u0001\u0001\u0000\u0000\u000089\u0005"+
		"\u0001\u0000\u00009:\u0005\u0016\u0000\u0000:\u0003\u0001\u0000\u0000"+
		"\u0000;<\u0005\u0002\u0000\u0000<=\u0005\u0016\u0000\u0000=\u0005\u0001"+
		"\u0000\u0000\u0000>?\u0005\u0003\u0000\u0000?@\u0005\u0016\u0000\u0000"+
		"@\u0007\u0001\u0000\u0000\u0000AB\u0005\u0004\u0000\u0000BC\u0005\u0016"+
		"\u0000\u0000C\t\u0001\u0000\u0000\u0000DE\u0005\u0005\u0000\u0000EF\u0005"+
		"\u0016\u0000\u0000F\u000b\u0001\u0000\u0000\u0000GH\u0005\u0006\u0000"+
		"\u0000HI\u0005\u0016\u0000\u0000I\r\u0001\u0000\u0000\u0000JL\u0003\u0010"+
		"\b\u0000KM\u0003\u0012\t\u0000LK\u0001\u0000\u0000\u0000MN\u0001\u0000"+
		"\u0000\u0000NL\u0001\u0000\u0000\u0000NO\u0001\u0000\u0000\u0000O\u000f"+
		"\u0001\u0000\u0000\u0000PQ\u0005\u0007\u0000\u0000QR\u0005\u0016\u0000"+
		"\u0000R\u0011\u0001\u0000\u0000\u0000SZ\u0003\u0014\n\u0000TZ\u0003\u0016"+
		"\u000b\u0000UZ\u0003\u0018\f\u0000VZ\u0003\u001a\r\u0000WZ\u0003\u001c"+
		"\u000e\u0000XZ\u0003\u001e\u000f\u0000YS\u0001\u0000\u0000\u0000YT\u0001"+
		"\u0000\u0000\u0000YU\u0001\u0000\u0000\u0000YV\u0001\u0000\u0000\u0000"+
		"YW\u0001\u0000\u0000\u0000YX\u0001\u0000\u0000\u0000Z\u0013\u0001\u0000"+
		"\u0000\u0000[\\\u0005\b\u0000\u0000\\]\u0005\u0016\u0000\u0000]^\u0005"+
		"\t\u0000\u0000^_\u0005\u0016\u0000\u0000_`\u0005\n\u0000\u0000`h\u0005"+
		"\u0016\u0000\u0000ab\u0005\u000b\u0000\u0000bc\u0005\u0016\u0000\u0000"+
		"cd\u0005\t\u0000\u0000de\u0005\u0016\u0000\u0000ef\u0005\f\u0000\u0000"+
		"fh\u0005\u0016\u0000\u0000g[\u0001\u0000\u0000\u0000ga\u0001\u0000\u0000"+
		"\u0000h\u0015\u0001\u0000\u0000\u0000ij\u0005\r\u0000\u0000jk\u0005\u0016"+
		"\u0000\u0000kl\u0005\u000e\u0000\u0000lm\u0005\u0016\u0000\u0000mn\u0005"+
		"\n\u0000\u0000np\u0005\u0016\u0000\u0000oq\u0003 \u0010\u0000po\u0001"+
		"\u0000\u0000\u0000qr\u0001\u0000\u0000\u0000rp\u0001\u0000\u0000\u0000"+
		"rs\u0001\u0000\u0000\u0000s{\u0001\u0000\u0000\u0000tu\u0005\u000f\u0000"+
		"\u0000uv\u0005\u0016\u0000\u0000vw\u0005\t\u0000\u0000wx\u0005\u0016\u0000"+
		"\u0000xy\u0005\f\u0000\u0000y{\u0005\u0016\u0000\u0000zi\u0001\u0000\u0000"+
		"\u0000zt\u0001\u0000\u0000\u0000{\u0017\u0001\u0000\u0000\u0000|}\u0005"+
		"\u0010\u0000\u0000}~\u0005\u0016\u0000\u0000~\u007f\u0005\u0011\u0000"+
		"\u0000\u007f\u0080\u0005\u0016\u0000\u0000\u0080\u0081\u0005\n\u0000\u0000"+
		"\u0081\u0089\u0005\u0016\u0000\u0000\u0082\u0083\u0005\u0010\u0000\u0000"+
		"\u0083\u0084\u0005\u0016\u0000\u0000\u0084\u0085\u0005\u0011\u0000\u0000"+
		"\u0085\u0086\u0005\u0016\u0000\u0000\u0086\u0087\u0005\f\u0000\u0000\u0087"+
		"\u0089\u0005\u0016\u0000\u0000\u0088|\u0001\u0000\u0000\u0000\u0088\u0082"+
		"\u0001\u0000\u0000\u0000\u0089\u0019\u0001\u0000\u0000\u0000\u008a\u008b"+
		"\u0005\u0012\u0000\u0000\u008b\u008c\u0005\u0016\u0000\u0000\u008c\u008d"+
		"\u0005\t\u0000\u0000\u008d\u008e\u0005\u0016\u0000\u0000\u008e\u008f\u0005"+
		"\n\u0000\u0000\u008f\u0097\u0005\u0016\u0000\u0000\u0090\u0091\u0005\u0012"+
		"\u0000\u0000\u0091\u0092\u0005\u0016\u0000\u0000\u0092\u0093\u0005\t\u0000"+
		"\u0000\u0093\u0094\u0005\u0016\u0000\u0000\u0094\u0095\u0005\f\u0000\u0000"+
		"\u0095\u0097\u0005\u0016\u0000\u0000\u0096\u008a\u0001\u0000\u0000\u0000"+
		"\u0096\u0090\u0001\u0000\u0000\u0000\u0097\u001b\u0001\u0000\u0000\u0000"+
		"\u0098\u0099\u0005\u0013\u0000\u0000\u0099\u009a\u0005\u0016\u0000\u0000"+
		"\u009a\u009b\u0005\t\u0000\u0000\u009b\u009c\u0005\u0016\u0000\u0000\u009c"+
		"\u009d\u0005\n\u0000\u0000\u009d\u00a5\u0005\u0016\u0000\u0000\u009e\u009f"+
		"\u0005\u0013\u0000\u0000\u009f\u00a0\u0005\u0016\u0000\u0000\u00a0\u00a1"+
		"\u0005\t\u0000\u0000\u00a1\u00a2\u0005\u0016\u0000\u0000\u00a2\u00a3\u0005"+
		"\f\u0000\u0000\u00a3\u00a5\u0005\u0016\u0000\u0000\u00a4\u0098\u0001\u0000"+
		"\u0000\u0000\u00a4\u009e\u0001\u0000\u0000\u0000\u00a5\u001d\u0001\u0000"+
		"\u0000\u0000\u00a6\u00a7\u0005\u0014\u0000\u0000\u00a7\u00a8\u0005\u0016"+
		"\u0000\u0000\u00a8\u00a9\u0005\n\u0000\u0000\u00a9\u00ab\u0005\u0016\u0000"+
		"\u0000\u00aa\u00ac\u0003\"\u0011\u0000\u00ab\u00aa\u0001\u0000\u0000\u0000"+
		"\u00ac\u00ad\u0001\u0000\u0000\u0000\u00ad\u00ab\u0001\u0000\u0000\u0000"+
		"\u00ad\u00ae\u0001\u0000\u0000\u0000\u00ae\u00ba\u0001\u0000\u0000\u0000"+
		"\u00af\u00b0\u0005\u0015\u0000\u0000\u00b0\u00b2\u0005\u0016\u0000\u0000"+
		"\u00b1\u00b3\u0003$\u0012\u0000\u00b2\u00b1\u0001\u0000\u0000\u0000\u00b3"+
		"\u00b4\u0001\u0000\u0000\u0000\u00b4\u00b2\u0001\u0000\u0000\u0000\u00b4"+
		"\u00b5\u0001\u0000\u0000\u0000\u00b5\u00b6\u0001\u0000\u0000\u0000\u00b6"+
		"\u00b7\u0005\f\u0000\u0000\u00b7\u00b8\u0005\u0016\u0000\u0000\u00b8\u00ba"+
		"\u0001\u0000\u0000\u0000\u00b9\u00a6\u0001\u0000\u0000\u0000\u00b9\u00af"+
		"\u0001\u0000\u0000\u0000\u00ba\u001f\u0001\u0000\u0000\u0000\u00bb\u00bc"+
		"\u0005\t\u0000\u0000\u00bc\u00bd\u0005\u0016\u0000\u0000\u00bd!\u0001"+
		"\u0000\u0000\u0000\u00be\u00bf\u0005\u000e\u0000\u0000\u00bf\u00c1\u0005"+
		"\u0016\u0000\u0000\u00c0\u00c2\u0003$\u0012\u0000\u00c1\u00c0\u0001\u0000"+
		"\u0000\u0000\u00c2\u00c3\u0001\u0000\u0000\u0000\u00c3\u00c1\u0001\u0000"+
		"\u0000\u0000\u00c3\u00c4\u0001\u0000\u0000\u0000\u00c4#\u0001\u0000\u0000"+
		"\u0000\u00c5\u00c6\u0005\t\u0000\u0000\u00c6\u00c7\u0005\u0016\u0000\u0000"+
		"\u00c7%\u0001\u0000\u0000\u0000\u000f/46NYgrz\u0088\u0096\u00a4\u00ad"+
		"\u00b4\u00b9\u00c3";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}