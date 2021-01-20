package antlr;// Generated from PrefixExpression.g4 by ANTLR 4.9
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PrefixExpressionParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WHITESPACE=1, NUMBER=2, PLUS=3, MINUS=4, MUL=5, DIV=6, DEF=7, EQUALS=8, 
		NOT_EQUAL=9, LOWER=10, LOWER_EQUAL=11, HIGHER=12, HIGHER_EQUAL=13, AND=14, 
		OR=15, NOT=16, TRUE=17, FALSE=18, IF=19, PRINT=20, INPUT=21, VARIABLE=22;
	public static final int
		RULE_expression = 0, RULE_expr = 1, RULE_multiExpr = 2, RULE_singleExpr = 3, 
		RULE_ifState = 4, RULE_elseState = 5, RULE_print = 6, RULE_define = 7, 
		RULE_rightValue = 8, RULE_logic = 9, RULE_compareOperation = 10, RULE_logicOperation = 11, 
		RULE_arithmetic = 12, RULE_input = 13, RULE_arithmeticOperation = 14;
	private static String[] makeRuleNames() {
		return new String[] {
			"expression", "expr", "multiExpr", "singleExpr", "ifState", "elseState", 
			"print", "define", "rightValue", "logic", "compareOperation", "logicOperation", 
			"arithmetic", "input", "arithmeticOperation"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, "'+'", "'-'", "'*'", "'/'", "'='", "'=='", "'!='", 
			"'<'", "'<='", "'>'", "'>='", "'&&'", "'||'", "'!'", "'true'", "'false'", 
			"'if'", "'print'", "'input'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WHITESPACE", "NUMBER", "PLUS", "MINUS", "MUL", "DIV", "DEF", "EQUALS", 
			"NOT_EQUAL", "LOWER", "LOWER_EQUAL", "HIGHER", "HIGHER_EQUAL", "AND", 
			"OR", "NOT", "TRUE", "FALSE", "IF", "PRINT", "INPUT", "VARIABLE"
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
	public String getGrammarFileName() { return "PrefixExpression.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public PrefixExpressionParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ExpressionContext extends ParserRuleContext {
		public String val;
		public ExprContext ex;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitExpression(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			((ExpressionContext)_localctx).ex = expr(1);
			((ExpressionContext)_localctx).val =  String.format("if __name__ == '__main__':\n%s", ((ExpressionContext)_localctx).ex.val);
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

	public static class ExprContext extends ParserRuleContext {
		public int tab;
		public String val;
		public SingleExprContext single;
		public MultiExprContext multi;
		public SingleExprContext singleExpr() {
			return getRuleContext(SingleExprContext.class,0);
		}
		public MultiExprContext multiExpr() {
			return getRuleContext(MultiExprContext.class,0);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public ExprContext(ParserRuleContext parent, int invokingState, int tab) {
			super(parent, invokingState);
			this.tab = tab;
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitExpr(this);
		}
	}

	public final ExprContext expr(int tab) throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState(), tab);
		enterRule(_localctx, 2, RULE_expr);
		try {
			setState(39);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(33);
				((ExprContext)_localctx).single = singleExpr(tab);
				((ExprContext)_localctx).val =  ((ExprContext)_localctx).single.val;
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(36);
				((ExprContext)_localctx).multi = multiExpr(tab);
				((ExprContext)_localctx).val =  ((ExprContext)_localctx).multi.val;
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

	public static class MultiExprContext extends ParserRuleContext {
		public int tab;
		public String val;
		public SingleExprContext ex;
		public MultiExprContext multi;
		public SingleExprContext singleExpr() {
			return getRuleContext(SingleExprContext.class,0);
		}
		public MultiExprContext multiExpr() {
			return getRuleContext(MultiExprContext.class,0);
		}
		public MultiExprContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public MultiExprContext(ParserRuleContext parent, int invokingState, int tab) {
			super(parent, invokingState);
			this.tab = tab;
		}
		@Override public int getRuleIndex() { return RULE_multiExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterMultiExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitMultiExpr(this);
		}
	}

	public final MultiExprContext multiExpr(int tab) throws RecognitionException {
		MultiExprContext _localctx = new MultiExprContext(_ctx, getState(), tab);
		enterRule(_localctx, 4, RULE_multiExpr);
		try {
			setState(48);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(41);
				((MultiExprContext)_localctx).ex = singleExpr(tab);
				((MultiExprContext)_localctx).val =  ((MultiExprContext)_localctx).ex.val;
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(44);
				((MultiExprContext)_localctx).ex = singleExpr(tab);
				setState(45);
				((MultiExprContext)_localctx).multi = multiExpr(tab);
				((MultiExprContext)_localctx).val =  ((MultiExprContext)_localctx).ex.val + ((MultiExprContext)_localctx).multi.val;
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

	public static class SingleExprContext extends ParserRuleContext {
		public int tab;
		public String val;
		public IfStateContext ex;
		public PrintContext pr;
		public DefineContext def;
		public IfStateContext ifState() {
			return getRuleContext(IfStateContext.class,0);
		}
		public PrintContext print() {
			return getRuleContext(PrintContext.class,0);
		}
		public DefineContext define() {
			return getRuleContext(DefineContext.class,0);
		}
		public SingleExprContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public SingleExprContext(ParserRuleContext parent, int invokingState, int tab) {
			super(parent, invokingState);
			this.tab = tab;
		}
		@Override public int getRuleIndex() { return RULE_singleExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterSingleExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitSingleExpr(this);
		}
	}

	public final SingleExprContext singleExpr(int tab) throws RecognitionException {
		SingleExprContext _localctx = new SingleExprContext(_ctx, getState(), tab);
		enterRule(_localctx, 6, RULE_singleExpr);
		try {
			setState(59);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IF:
				enterOuterAlt(_localctx, 1);
				{
				setState(50);
				((SingleExprContext)_localctx).ex = ifState(tab);
				((SingleExprContext)_localctx).val =  ((SingleExprContext)_localctx).ex.val;
				}
				break;
			case PRINT:
				enterOuterAlt(_localctx, 2);
				{
				setState(53);
				((SingleExprContext)_localctx).pr = print(tab);
				((SingleExprContext)_localctx).val =  ((SingleExprContext)_localctx).pr.val + "\n";
				}
				break;
			case DEF:
				enterOuterAlt(_localctx, 3);
				{
				setState(56);
				((SingleExprContext)_localctx).def = define(tab);
				((SingleExprContext)_localctx).val =  ((SingleExprContext)_localctx).def.val + "\n";
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

	public static class IfStateContext extends ParserRuleContext {
		public int tab;
		public String val;
		public LogicContext l;
		public ExprContext ex;
		public ElseStateContext el;
		public TerminalNode IF() { return getToken(PrefixExpressionParser.IF, 0); }
		public LogicContext logic() {
			return getRuleContext(LogicContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ElseStateContext elseState() {
			return getRuleContext(ElseStateContext.class,0);
		}
		public IfStateContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public IfStateContext(ParserRuleContext parent, int invokingState, int tab) {
			super(parent, invokingState);
			this.tab = tab;
		}
		@Override public int getRuleIndex() { return RULE_ifState; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterIfState(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitIfState(this);
		}
	}

	public final IfStateContext ifState(int tab) throws RecognitionException {
		IfStateContext _localctx = new IfStateContext(_ctx, getState(), tab);
		enterRule(_localctx, 8, RULE_ifState);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(61);
			match(IF);
			setState(62);
			((IfStateContext)_localctx).l = logic();
			setState(63);
			((IfStateContext)_localctx).ex = expr(tab+1);
			setState(64);
			((IfStateContext)_localctx).el = elseState(tab);
			((IfStateContext)_localctx).val =  String.format("%sif %s:\n%s%s", "    ".repeat(_localctx.tab), ((IfStateContext)_localctx).l.val, ((IfStateContext)_localctx).ex.val, ((IfStateContext)_localctx).el.val);
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

	public static class ElseStateContext extends ParserRuleContext {
		public int tab;
		public String val;
		public ExprContext ex;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ElseStateContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public ElseStateContext(ParserRuleContext parent, int invokingState, int tab) {
			super(parent, invokingState);
			this.tab = tab;
		}
		@Override public int getRuleIndex() { return RULE_elseState; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterElseState(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitElseState(this);
		}
	}

	public final ElseStateContext elseState(int tab) throws RecognitionException {
		ElseStateContext _localctx = new ElseStateContext(_ctx, getState(), tab);
		enterRule(_localctx, 10, RULE_elseState);
		try {
			setState(71);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(67);
				((ElseStateContext)_localctx).ex = expr(tab+1);
				((ElseStateContext)_localctx).val =  String.format("%selse:\n%s", "    ".repeat(_localctx.tab), ((ElseStateContext)_localctx).ex.val);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				((ElseStateContext)_localctx).val =  "";
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

	public static class PrintContext extends ParserRuleContext {
		public int tab;
		public String val;
		public RightValueContext v;
		public TerminalNode PRINT() { return getToken(PrefixExpressionParser.PRINT, 0); }
		public RightValueContext rightValue() {
			return getRuleContext(RightValueContext.class,0);
		}
		public PrintContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public PrintContext(ParserRuleContext parent, int invokingState, int tab) {
			super(parent, invokingState);
			this.tab = tab;
		}
		@Override public int getRuleIndex() { return RULE_print; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterPrint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitPrint(this);
		}
	}

	public final PrintContext print(int tab) throws RecognitionException {
		PrintContext _localctx = new PrintContext(_ctx, getState(), tab);
		enterRule(_localctx, 12, RULE_print);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			match(PRINT);
			setState(74);
			((PrintContext)_localctx).v = rightValue();
			((PrintContext)_localctx).val =  String.format("%sprint(%s)", "    ".repeat(_localctx.tab), ((PrintContext)_localctx).v.val);
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

	public static class DefineContext extends ParserRuleContext {
		public int tab;
		public String val;
		public Token var;
		public RightValueContext v;
		public TerminalNode DEF() { return getToken(PrefixExpressionParser.DEF, 0); }
		public TerminalNode VARIABLE() { return getToken(PrefixExpressionParser.VARIABLE, 0); }
		public RightValueContext rightValue() {
			return getRuleContext(RightValueContext.class,0);
		}
		public DefineContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public DefineContext(ParserRuleContext parent, int invokingState, int tab) {
			super(parent, invokingState);
			this.tab = tab;
		}
		@Override public int getRuleIndex() { return RULE_define; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterDefine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitDefine(this);
		}
	}

	public final DefineContext define(int tab) throws RecognitionException {
		DefineContext _localctx = new DefineContext(_ctx, getState(), tab);
		enterRule(_localctx, 14, RULE_define);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			match(DEF);
			setState(78);
			((DefineContext)_localctx).var = match(VARIABLE);
			setState(79);
			((DefineContext)_localctx).v = rightValue();
			((DefineContext)_localctx).val =  String.format("%s%s = %s", "    ".repeat(_localctx.tab), (((DefineContext)_localctx).var!=null?((DefineContext)_localctx).var.getText():null), ((DefineContext)_localctx).v.val);
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

	public static class RightValueContext extends ParserRuleContext {
		public String val;
		public ArithmeticContext ar;
		public LogicContext l;
		public InputContext inp;
		public Token var;
		public ArithmeticContext arithmetic() {
			return getRuleContext(ArithmeticContext.class,0);
		}
		public LogicContext logic() {
			return getRuleContext(LogicContext.class,0);
		}
		public InputContext input() {
			return getRuleContext(InputContext.class,0);
		}
		public TerminalNode VARIABLE() { return getToken(PrefixExpressionParser.VARIABLE, 0); }
		public RightValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rightValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterRightValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitRightValue(this);
		}
	}

	public final RightValueContext rightValue() throws RecognitionException {
		RightValueContext _localctx = new RightValueContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_rightValue);
		try {
			setState(93);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(82);
				((RightValueContext)_localctx).ar = arithmetic();
				((RightValueContext)_localctx).val =  ((RightValueContext)_localctx).ar.val;
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(85);
				((RightValueContext)_localctx).l = logic();
				((RightValueContext)_localctx).val =  ((RightValueContext)_localctx).l.val;
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(88);
				((RightValueContext)_localctx).inp = input();
				((RightValueContext)_localctx).val =  ((RightValueContext)_localctx).inp.val;
				setState(90);
				((RightValueContext)_localctx).var = match(VARIABLE);
				((RightValueContext)_localctx).val =  (((RightValueContext)_localctx).var!=null?((RightValueContext)_localctx).var.getText():null);
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

	public static class LogicContext extends ParserRuleContext {
		public String val;
		public CompareOperationContext op;
		public ArithmeticContext left;
		public ArithmeticContext right;
		public LogicContext value;
		public LogicOperationContext op_l;
		public LogicContext left_l;
		public LogicContext right_l;
		public CompareOperationContext compareOperation() {
			return getRuleContext(CompareOperationContext.class,0);
		}
		public List<ArithmeticContext> arithmetic() {
			return getRuleContexts(ArithmeticContext.class);
		}
		public ArithmeticContext arithmetic(int i) {
			return getRuleContext(ArithmeticContext.class,i);
		}
		public TerminalNode NOT() { return getToken(PrefixExpressionParser.NOT, 0); }
		public List<LogicContext> logic() {
			return getRuleContexts(LogicContext.class);
		}
		public LogicContext logic(int i) {
			return getRuleContext(LogicContext.class,i);
		}
		public TerminalNode TRUE() { return getToken(PrefixExpressionParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(PrefixExpressionParser.FALSE, 0); }
		public LogicOperationContext logicOperation() {
			return getRuleContext(LogicOperationContext.class,0);
		}
		public LogicContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logic; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterLogic(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitLogic(this);
		}
	}

	public final LogicContext logic() throws RecognitionException {
		LogicContext _localctx = new LogicContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_logic);
		try {
			setState(113);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case EQUALS:
			case NOT_EQUAL:
			case LOWER:
			case LOWER_EQUAL:
			case HIGHER:
			case HIGHER_EQUAL:
				enterOuterAlt(_localctx, 1);
				{
				setState(95);
				((LogicContext)_localctx).op = compareOperation();
				setState(96);
				((LogicContext)_localctx).left = arithmetic();
				setState(97);
				((LogicContext)_localctx).right = arithmetic();
				((LogicContext)_localctx).val =  String.format("(%s %s %s)", ((LogicContext)_localctx).left.val, ((LogicContext)_localctx).op.op, ((LogicContext)_localctx).right.val);
				}
				break;
			case NOT:
				enterOuterAlt(_localctx, 2);
				{
				setState(100);
				match(NOT);
				setState(101);
				((LogicContext)_localctx).value = logic();
				((LogicContext)_localctx).val =  String.format("not %s", ((LogicContext)_localctx).value.val);
				}
				break;
			case TRUE:
				enterOuterAlt(_localctx, 3);
				{
				setState(104);
				match(TRUE);
				((LogicContext)_localctx).val =  "True";
				}
				break;
			case FALSE:
				enterOuterAlt(_localctx, 4);
				{
				setState(106);
				match(FALSE);
				((LogicContext)_localctx).val =  "False";
				}
				break;
			case AND:
			case OR:
				enterOuterAlt(_localctx, 5);
				{
				setState(108);
				((LogicContext)_localctx).op_l = logicOperation();
				setState(109);
				((LogicContext)_localctx).left_l = logic();
				setState(110);
				((LogicContext)_localctx).right_l = logic();
				((LogicContext)_localctx).val =  String.format("(%s %s %s)", ((LogicContext)_localctx).left_l.val, ((LogicContext)_localctx).op_l.op, ((LogicContext)_localctx).right_l.val);
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

	public static class CompareOperationContext extends ParserRuleContext {
		public String op;
		public TerminalNode EQUALS() { return getToken(PrefixExpressionParser.EQUALS, 0); }
		public TerminalNode NOT_EQUAL() { return getToken(PrefixExpressionParser.NOT_EQUAL, 0); }
		public TerminalNode LOWER() { return getToken(PrefixExpressionParser.LOWER, 0); }
		public TerminalNode LOWER_EQUAL() { return getToken(PrefixExpressionParser.LOWER_EQUAL, 0); }
		public TerminalNode HIGHER() { return getToken(PrefixExpressionParser.HIGHER, 0); }
		public TerminalNode HIGHER_EQUAL() { return getToken(PrefixExpressionParser.HIGHER_EQUAL, 0); }
		public CompareOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compareOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterCompareOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitCompareOperation(this);
		}
	}

	public final CompareOperationContext compareOperation() throws RecognitionException {
		CompareOperationContext _localctx = new CompareOperationContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_compareOperation);
		try {
			setState(127);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case EQUALS:
				enterOuterAlt(_localctx, 1);
				{
				setState(115);
				match(EQUALS);
				((CompareOperationContext)_localctx).op =  "==";
				}
				break;
			case NOT_EQUAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(117);
				match(NOT_EQUAL);
				((CompareOperationContext)_localctx).op =  "!=";
				}
				break;
			case LOWER:
				enterOuterAlt(_localctx, 3);
				{
				setState(119);
				match(LOWER);
				((CompareOperationContext)_localctx).op =  "<";
				}
				break;
			case LOWER_EQUAL:
				enterOuterAlt(_localctx, 4);
				{
				setState(121);
				match(LOWER_EQUAL);
				((CompareOperationContext)_localctx).op =  "<=";
				}
				break;
			case HIGHER:
				enterOuterAlt(_localctx, 5);
				{
				setState(123);
				match(HIGHER);
				((CompareOperationContext)_localctx).op =  ">";
				}
				break;
			case HIGHER_EQUAL:
				enterOuterAlt(_localctx, 6);
				{
				setState(125);
				match(HIGHER_EQUAL);
				((CompareOperationContext)_localctx).op =  ">=";
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

	public static class LogicOperationContext extends ParserRuleContext {
		public String op;
		public TerminalNode AND() { return getToken(PrefixExpressionParser.AND, 0); }
		public TerminalNode OR() { return getToken(PrefixExpressionParser.OR, 0); }
		public LogicOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterLogicOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitLogicOperation(this);
		}
	}

	public final LogicOperationContext logicOperation() throws RecognitionException {
		LogicOperationContext _localctx = new LogicOperationContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_logicOperation);
		try {
			setState(133);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AND:
				enterOuterAlt(_localctx, 1);
				{
				setState(129);
				match(AND);
				((LogicOperationContext)_localctx).op =  "and";
				}
				break;
			case OR:
				enterOuterAlt(_localctx, 2);
				{
				setState(131);
				match(OR);
				((LogicOperationContext)_localctx).op =  "or";
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

	public static class ArithmeticContext extends ParserRuleContext {
		public String val;
		public ArithmeticOperationContext op;
		public ArithmeticContext left;
		public ArithmeticContext right;
		public Token num;
		public InputContext inp;
		public Token var;
		public ArithmeticOperationContext arithmeticOperation() {
			return getRuleContext(ArithmeticOperationContext.class,0);
		}
		public List<ArithmeticContext> arithmetic() {
			return getRuleContexts(ArithmeticContext.class);
		}
		public ArithmeticContext arithmetic(int i) {
			return getRuleContext(ArithmeticContext.class,i);
		}
		public TerminalNode NUMBER() { return getToken(PrefixExpressionParser.NUMBER, 0); }
		public InputContext input() {
			return getRuleContext(InputContext.class,0);
		}
		public TerminalNode VARIABLE() { return getToken(PrefixExpressionParser.VARIABLE, 0); }
		public ArithmeticContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithmetic; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterArithmetic(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitArithmetic(this);
		}
	}

	public final ArithmeticContext arithmetic() throws RecognitionException {
		ArithmeticContext _localctx = new ArithmeticContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_arithmetic);
		try {
			setState(147);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PLUS:
			case MINUS:
			case MUL:
			case DIV:
				enterOuterAlt(_localctx, 1);
				{
				setState(135);
				((ArithmeticContext)_localctx).op = arithmeticOperation();
				setState(136);
				((ArithmeticContext)_localctx).left = arithmetic();
				setState(137);
				((ArithmeticContext)_localctx).right = arithmetic();
				((ArithmeticContext)_localctx).val =  String.format("(%s %s %s)", ((ArithmeticContext)_localctx).left.val, ((ArithmeticContext)_localctx).op.op, ((ArithmeticContext)_localctx).right.val);
				}
				break;
			case NUMBER:
				enterOuterAlt(_localctx, 2);
				{
				setState(140);
				((ArithmeticContext)_localctx).num = match(NUMBER);
				((ArithmeticContext)_localctx).val =  (((ArithmeticContext)_localctx).num!=null?((ArithmeticContext)_localctx).num.getText():null);
				}
				break;
			case INPUT:
				enterOuterAlt(_localctx, 3);
				{
				setState(142);
				((ArithmeticContext)_localctx).inp = input();
				((ArithmeticContext)_localctx).val =  ((ArithmeticContext)_localctx).inp.val;
				}
				break;
			case VARIABLE:
				enterOuterAlt(_localctx, 4);
				{
				setState(145);
				((ArithmeticContext)_localctx).var = match(VARIABLE);
				((ArithmeticContext)_localctx).val =  (((ArithmeticContext)_localctx).var!=null?((ArithmeticContext)_localctx).var.getText():null);
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

	public static class InputContext extends ParserRuleContext {
		public String val;
		public Token inp;
		public TerminalNode INPUT() { return getToken(PrefixExpressionParser.INPUT, 0); }
		public InputContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_input; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterInput(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitInput(this);
		}
	}

	public final InputContext input() throws RecognitionException {
		InputContext _localctx = new InputContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_input);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			((InputContext)_localctx).inp = match(INPUT);
			((InputContext)_localctx).val = "input()";
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

	public static class ArithmeticOperationContext extends ParserRuleContext {
		public String op;
		public TerminalNode PLUS() { return getToken(PrefixExpressionParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(PrefixExpressionParser.MINUS, 0); }
		public TerminalNode MUL() { return getToken(PrefixExpressionParser.MUL, 0); }
		public TerminalNode DIV() { return getToken(PrefixExpressionParser.DIV, 0); }
		public ArithmeticOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithmeticOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterArithmeticOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitArithmeticOperation(this);
		}
	}

	public final ArithmeticOperationContext arithmeticOperation() throws RecognitionException {
		ArithmeticOperationContext _localctx = new ArithmeticOperationContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_arithmeticOperation);
		try {
			setState(160);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PLUS:
				enterOuterAlt(_localctx, 1);
				{
				setState(152);
				match(PLUS);
				((ArithmeticOperationContext)_localctx).op =  "+";
				}
				break;
			case MINUS:
				enterOuterAlt(_localctx, 2);
				{
				setState(154);
				match(MINUS);
				((ArithmeticOperationContext)_localctx).op =  "-";
				}
				break;
			case MUL:
				enterOuterAlt(_localctx, 3);
				{
				setState(156);
				match(MUL);
				((ArithmeticOperationContext)_localctx).op =  "*";
				}
				break;
			case DIV:
				enterOuterAlt(_localctx, 4);
				{
				setState(158);
				match(DIV);
				((ArithmeticOperationContext)_localctx).op =  "/";
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\30\u00a5\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\3\2\3\2\3\2\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\5\3*\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4\63\n\4\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5>\n\5\3\6\3\6\3\6\3\6\3\6\3\6\3\7"+
		"\3\7\3\7\3\7\5\7J\n\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n`\n\n\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13t\n\13"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u0082\n\f\3\r\3\r"+
		"\3\r\3\r\5\r\u0088\n\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3"+
		"\16\3\16\3\16\5\16\u0096\n\16\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\5\20\u00a3\n\20\3\20\2\2\21\2\4\6\b\n\f\16\20\22\24\26"+
		"\30\32\34\36\2\2\2\u00ac\2 \3\2\2\2\4)\3\2\2\2\6\62\3\2\2\2\b=\3\2\2\2"+
		"\n?\3\2\2\2\fI\3\2\2\2\16K\3\2\2\2\20O\3\2\2\2\22_\3\2\2\2\24s\3\2\2\2"+
		"\26\u0081\3\2\2\2\30\u0087\3\2\2\2\32\u0095\3\2\2\2\34\u0097\3\2\2\2\36"+
		"\u00a2\3\2\2\2 !\5\4\3\2!\"\b\2\1\2\"\3\3\2\2\2#$\5\b\5\2$%\b\3\1\2%*"+
		"\3\2\2\2&\'\5\6\4\2\'(\b\3\1\2(*\3\2\2\2)#\3\2\2\2)&\3\2\2\2*\5\3\2\2"+
		"\2+,\5\b\5\2,-\b\4\1\2-\63\3\2\2\2./\5\b\5\2/\60\5\6\4\2\60\61\b\4\1\2"+
		"\61\63\3\2\2\2\62+\3\2\2\2\62.\3\2\2\2\63\7\3\2\2\2\64\65\5\n\6\2\65\66"+
		"\b\5\1\2\66>\3\2\2\2\678\5\16\b\289\b\5\1\29>\3\2\2\2:;\5\20\t\2;<\b\5"+
		"\1\2<>\3\2\2\2=\64\3\2\2\2=\67\3\2\2\2=:\3\2\2\2>\t\3\2\2\2?@\7\25\2\2"+
		"@A\5\24\13\2AB\5\4\3\2BC\5\f\7\2CD\b\6\1\2D\13\3\2\2\2EF\5\4\3\2FG\b\7"+
		"\1\2GJ\3\2\2\2HJ\b\7\1\2IE\3\2\2\2IH\3\2\2\2J\r\3\2\2\2KL\7\26\2\2LM\5"+
		"\22\n\2MN\b\b\1\2N\17\3\2\2\2OP\7\t\2\2PQ\7\30\2\2QR\5\22\n\2RS\b\t\1"+
		"\2S\21\3\2\2\2TU\5\32\16\2UV\b\n\1\2V`\3\2\2\2WX\5\24\13\2XY\b\n\1\2Y"+
		"`\3\2\2\2Z[\5\34\17\2[\\\b\n\1\2\\]\7\30\2\2]^\b\n\1\2^`\3\2\2\2_T\3\2"+
		"\2\2_W\3\2\2\2_Z\3\2\2\2`\23\3\2\2\2ab\5\26\f\2bc\5\32\16\2cd\5\32\16"+
		"\2de\b\13\1\2et\3\2\2\2fg\7\22\2\2gh\5\24\13\2hi\b\13\1\2it\3\2\2\2jk"+
		"\7\23\2\2kt\b\13\1\2lm\7\24\2\2mt\b\13\1\2no\5\30\r\2op\5\24\13\2pq\5"+
		"\24\13\2qr\b\13\1\2rt\3\2\2\2sa\3\2\2\2sf\3\2\2\2sj\3\2\2\2sl\3\2\2\2"+
		"sn\3\2\2\2t\25\3\2\2\2uv\7\n\2\2v\u0082\b\f\1\2wx\7\13\2\2x\u0082\b\f"+
		"\1\2yz\7\f\2\2z\u0082\b\f\1\2{|\7\r\2\2|\u0082\b\f\1\2}~\7\16\2\2~\u0082"+
		"\b\f\1\2\177\u0080\7\17\2\2\u0080\u0082\b\f\1\2\u0081u\3\2\2\2\u0081w"+
		"\3\2\2\2\u0081y\3\2\2\2\u0081{\3\2\2\2\u0081}\3\2\2\2\u0081\177\3\2\2"+
		"\2\u0082\27\3\2\2\2\u0083\u0084\7\20\2\2\u0084\u0088\b\r\1\2\u0085\u0086"+
		"\7\21\2\2\u0086\u0088\b\r\1\2\u0087\u0083\3\2\2\2\u0087\u0085\3\2\2\2"+
		"\u0088\31\3\2\2\2\u0089\u008a\5\36\20\2\u008a\u008b\5\32\16\2\u008b\u008c"+
		"\5\32\16\2\u008c\u008d\b\16\1\2\u008d\u0096\3\2\2\2\u008e\u008f\7\4\2"+
		"\2\u008f\u0096\b\16\1\2\u0090\u0091\5\34\17\2\u0091\u0092\b\16\1\2\u0092"+
		"\u0096\3\2\2\2\u0093\u0094\7\30\2\2\u0094\u0096\b\16\1\2\u0095\u0089\3"+
		"\2\2\2\u0095\u008e\3\2\2\2\u0095\u0090\3\2\2\2\u0095\u0093\3\2\2\2\u0096"+
		"\33\3\2\2\2\u0097\u0098\7\27\2\2\u0098\u0099\b\17\1\2\u0099\35\3\2\2\2"+
		"\u009a\u009b\7\5\2\2\u009b\u00a3\b\20\1\2\u009c\u009d\7\6\2\2\u009d\u00a3"+
		"\b\20\1\2\u009e\u009f\7\7\2\2\u009f\u00a3\b\20\1\2\u00a0\u00a1\7\b\2\2"+
		"\u00a1\u00a3\b\20\1\2\u00a2\u009a\3\2\2\2\u00a2\u009c\3\2\2\2\u00a2\u009e"+
		"\3\2\2\2\u00a2\u00a0\3\2\2\2\u00a3\37\3\2\2\2\f)\62=I_s\u0081\u0087\u0095"+
		"\u00a2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}