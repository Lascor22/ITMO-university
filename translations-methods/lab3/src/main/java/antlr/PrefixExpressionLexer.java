package antlr;// Generated from PrefixExpression.g4 by ANTLR 4.9
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PrefixExpressionLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WHITESPACE=1, NUMBER=2, PLUS=3, MINUS=4, MUL=5, DIV=6, DEF=7, EQUALS=8, 
		NOT_EQUAL=9, LOWER=10, LOWER_EQUAL=11, HIGHER=12, HIGHER_EQUAL=13, AND=14, 
		OR=15, NOT=16, TRUE=17, FALSE=18, IF=19, PRINT=20, INPUT=21, VARIABLE=22;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"WHITESPACE", "LETTER", "DIGIT", "NUMBER", "PLUS", "MINUS", "MUL", "DIV", 
			"DEF", "EQUALS", "NOT_EQUAL", "LOWER", "LOWER_EQUAL", "HIGHER", "HIGHER_EQUAL", 
			"AND", "OR", "NOT", "TRUE", "FALSE", "IF", "PRINT", "INPUT", "VARIABLE"
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


	public PrefixExpressionLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "PrefixExpression.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\30\u0090\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\3\2\6\2\65\n\2\r\2\16\2\66\3\2\3\2\3\3\3\3\3\4\3\4\3\5\5\5@\n\5"+
		"\3\5\3\5\7\5D\n\5\f\5\16\5G\13\5\3\5\5\5J\n\5\3\6\3\6\3\7\3\7\3\b\3\b"+
		"\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\16\3\16\3\16\3\17"+
		"\3\17\3\20\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\22\3\23\3\23\3\24\3\24"+
		"\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\27\3\27"+
		"\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31"+
		"\7\31\u008c\n\31\f\31\16\31\u008f\13\31\2\2\32\3\3\5\2\7\2\t\4\13\5\r"+
		"\6\17\7\21\b\23\t\25\n\27\13\31\f\33\r\35\16\37\17!\20#\21%\22\'\23)\24"+
		"+\25-\26/\27\61\30\3\2\7\5\2\13\f\17\17\"\"\4\2C\\c|\3\2\62;\3\2\63;\3"+
		"\2))\2\u0094\2\3\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2"+
		"\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2"+
		"\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2"+
		"\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2"+
		"\2\3\64\3\2\2\2\5:\3\2\2\2\7<\3\2\2\2\tI\3\2\2\2\13K\3\2\2\2\rM\3\2\2"+
		"\2\17O\3\2\2\2\21Q\3\2\2\2\23S\3\2\2\2\25U\3\2\2\2\27X\3\2\2\2\31[\3\2"+
		"\2\2\33]\3\2\2\2\35`\3\2\2\2\37b\3\2\2\2!e\3\2\2\2#h\3\2\2\2%k\3\2\2\2"+
		"\'m\3\2\2\2)r\3\2\2\2+x\3\2\2\2-{\3\2\2\2/\u0081\3\2\2\2\61\u0087\3\2"+
		"\2\2\63\65\t\2\2\2\64\63\3\2\2\2\65\66\3\2\2\2\66\64\3\2\2\2\66\67\3\2"+
		"\2\2\678\3\2\2\289\b\2\2\29\4\3\2\2\2:;\t\3\2\2;\6\3\2\2\2<=\t\4\2\2="+
		"\b\3\2\2\2>@\7/\2\2?>\3\2\2\2?@\3\2\2\2@A\3\2\2\2AE\t\5\2\2BD\5\7\4\2"+
		"CB\3\2\2\2DG\3\2\2\2EC\3\2\2\2EF\3\2\2\2FJ\3\2\2\2GE\3\2\2\2HJ\7\62\2"+
		"\2I?\3\2\2\2IH\3\2\2\2J\n\3\2\2\2KL\7-\2\2L\f\3\2\2\2MN\7/\2\2N\16\3\2"+
		"\2\2OP\7,\2\2P\20\3\2\2\2QR\7\61\2\2R\22\3\2\2\2ST\7?\2\2T\24\3\2\2\2"+
		"UV\7?\2\2VW\7?\2\2W\26\3\2\2\2XY\7#\2\2YZ\7?\2\2Z\30\3\2\2\2[\\\7>\2\2"+
		"\\\32\3\2\2\2]^\7>\2\2^_\7?\2\2_\34\3\2\2\2`a\7@\2\2a\36\3\2\2\2bc\7@"+
		"\2\2cd\7?\2\2d \3\2\2\2ef\7(\2\2fg\7(\2\2g\"\3\2\2\2hi\7~\2\2ij\7~\2\2"+
		"j$\3\2\2\2kl\7#\2\2l&\3\2\2\2mn\7v\2\2no\7t\2\2op\7w\2\2pq\7g\2\2q(\3"+
		"\2\2\2rs\7h\2\2st\7c\2\2tu\7n\2\2uv\7u\2\2vw\7g\2\2w*\3\2\2\2xy\7k\2\2"+
		"yz\7h\2\2z,\3\2\2\2{|\7r\2\2|}\7t\2\2}~\7k\2\2~\177\7p\2\2\177\u0080\7"+
		"v\2\2\u0080.\3\2\2\2\u0081\u0082\7k\2\2\u0082\u0083\7p\2\2\u0083\u0084"+
		"\7r\2\2\u0084\u0085\7w\2\2\u0085\u0086\7v\2\2\u0086\60\3\2\2\2\u0087\u008d"+
		"\5\5\3\2\u0088\u008c\5\5\3\2\u0089\u008c\5\7\4\2\u008a\u008c\t\6\2\2\u008b"+
		"\u0088\3\2\2\2\u008b\u0089\3\2\2\2\u008b\u008a\3\2\2\2\u008c\u008f\3\2"+
		"\2\2\u008d\u008b\3\2\2\2\u008d\u008e\3\2\2\2\u008e\62\3\2\2\2\u008f\u008d"+
		"\3\2\2\2\t\2\66?EI\u008b\u008d\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}