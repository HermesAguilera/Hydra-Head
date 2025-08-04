// Generated from /home/josed/Documentos/Analizador/AnalizadorLexer.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class AnalizadorLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		INICIO=1, FIN=2, DEFINIR=3, SI=4, SINO=5, ESCRIBIR=6, ENTERO=7, REAL=8, 
		ID=9, NUMERO=10, REALNUM=11, IGUAL=12, MAS=13, MENOS=14, POR=15, ENTRE=16, 
		MAYORQUE=17, MENORQUE=18, IGUALQUE=19, DIFERENTE=20, PUNTOYCOMA=21, PARENIZQ=22, 
		PARENDER=23, LLAVEIZQ=24, LLAVEDER=25, CADENA=26, COMENTARIO=27, ESPACIOS=28, 
		ERROR=29;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"INICIO", "FIN", "DEFINIR", "SI", "SINO", "ESCRIBIR", "ENTERO", "REAL", 
			"ID", "NUMERO", "REALNUM", "IGUAL", "MAS", "MENOS", "POR", "ENTRE", "MAYORQUE", 
			"MENORQUE", "IGUALQUE", "DIFERENTE", "PUNTOYCOMA", "PARENIZQ", "PARENDER", 
			"LLAVEIZQ", "LLAVEDER", "CADENA", "COMENTARIO", "ESPACIOS", "ERROR"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'inicio'", "'fin'", "'definir'", "'si'", "'sino'", "'escribir'", 
			"'entero'", "'real'", null, null, null, "'='", "'+'", "'-'", "'*'", "'/'", 
			"'>'", "'<'", "'=='", "'!='", "';'", "'('", "')'", "'{'", "'}'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "INICIO", "FIN", "DEFINIR", "SI", "SINO", "ESCRIBIR", "ENTERO", 
			"REAL", "ID", "NUMERO", "REALNUM", "IGUAL", "MAS", "MENOS", "POR", "ENTRE", 
			"MAYORQUE", "MENORQUE", "IGUALQUE", "DIFERENTE", "PUNTOYCOMA", "PARENIZQ", 
			"PARENDER", "LLAVEIZQ", "LLAVEDER", "CADENA", "COMENTARIO", "ESPACIOS", 
			"ERROR"
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


	public AnalizadorLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "AnalizadorLexer.g4"; }

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
		"\u0004\u0000\u001d\u00b8\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014"+
		"\u0002\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017"+
		"\u0002\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a"+
		"\u0002\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0005"+
		"\bn\b\b\n\b\f\bq\t\b\u0001\t\u0004\tt\b\t\u000b\t\f\tu\u0001\n\u0004\n"+
		"y\b\n\u000b\n\f\nz\u0001\n\u0001\n\u0004\n\u007f\b\n\u000b\n\f\n\u0080"+
		"\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\r\u0001\r\u0001\u000e\u0001"+
		"\u000e\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0011\u0001"+
		"\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001"+
		"\u0013\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0016\u0001"+
		"\u0016\u0001\u0017\u0001\u0017\u0001\u0018\u0001\u0018\u0001\u0019\u0001"+
		"\u0019\u0005\u0019\u00a3\b\u0019\n\u0019\f\u0019\u00a6\t\u0019\u0001\u0019"+
		"\u0001\u0019\u0001\u001a\u0001\u001a\u0005\u001a\u00ac\b\u001a\n\u001a"+
		"\f\u001a\u00af\t\u001a\u0001\u001a\u0001\u001a\u0001\u001b\u0001\u001b"+
		"\u0001\u001b\u0001\u001b\u0001\u001c\u0001\u001c\u0000\u0000\u001d\u0001"+
		"\u0001\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007"+
		"\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e\u001d"+
		"\u000f\u001f\u0010!\u0011#\u0012%\u0013\'\u0014)\u0015+\u0016-\u0017/"+
		"\u00181\u00193\u001a5\u001b7\u001c9\u001d\u0001\u0000\u0006\u0003\u0000"+
		"AZ__az\u0004\u000009AZ__az\u0001\u000009\u0001\u0000\"\"\u0001\u0000\n"+
		"\n\u0002\u0000\t\n  \u00bd\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003"+
		"\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007"+
		"\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001"+
		"\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000"+
		"\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000"+
		"\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000"+
		"\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000"+
		"\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000"+
		"\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000"+
		"\u0000%\u0001\u0000\u0000\u0000\u0000\'\u0001\u0000\u0000\u0000\u0000"+
		")\u0001\u0000\u0000\u0000\u0000+\u0001\u0000\u0000\u0000\u0000-\u0001"+
		"\u0000\u0000\u0000\u0000/\u0001\u0000\u0000\u0000\u00001\u0001\u0000\u0000"+
		"\u0000\u00003\u0001\u0000\u0000\u0000\u00005\u0001\u0000\u0000\u0000\u0000"+
		"7\u0001\u0000\u0000\u0000\u00009\u0001\u0000\u0000\u0000\u0001;\u0001"+
		"\u0000\u0000\u0000\u0003B\u0001\u0000\u0000\u0000\u0005F\u0001\u0000\u0000"+
		"\u0000\u0007N\u0001\u0000\u0000\u0000\tQ\u0001\u0000\u0000\u0000\u000b"+
		"V\u0001\u0000\u0000\u0000\r_\u0001\u0000\u0000\u0000\u000ff\u0001\u0000"+
		"\u0000\u0000\u0011k\u0001\u0000\u0000\u0000\u0013s\u0001\u0000\u0000\u0000"+
		"\u0015x\u0001\u0000\u0000\u0000\u0017\u0082\u0001\u0000\u0000\u0000\u0019"+
		"\u0084\u0001\u0000\u0000\u0000\u001b\u0086\u0001\u0000\u0000\u0000\u001d"+
		"\u0088\u0001\u0000\u0000\u0000\u001f\u008a\u0001\u0000\u0000\u0000!\u008c"+
		"\u0001\u0000\u0000\u0000#\u008e\u0001\u0000\u0000\u0000%\u0090\u0001\u0000"+
		"\u0000\u0000\'\u0093\u0001\u0000\u0000\u0000)\u0096\u0001\u0000\u0000"+
		"\u0000+\u0098\u0001\u0000\u0000\u0000-\u009a\u0001\u0000\u0000\u0000/"+
		"\u009c\u0001\u0000\u0000\u00001\u009e\u0001\u0000\u0000\u00003\u00a0\u0001"+
		"\u0000\u0000\u00005\u00a9\u0001\u0000\u0000\u00007\u00b2\u0001\u0000\u0000"+
		"\u00009\u00b6\u0001\u0000\u0000\u0000;<\u0005i\u0000\u0000<=\u0005n\u0000"+
		"\u0000=>\u0005i\u0000\u0000>?\u0005c\u0000\u0000?@\u0005i\u0000\u0000"+
		"@A\u0005o\u0000\u0000A\u0002\u0001\u0000\u0000\u0000BC\u0005f\u0000\u0000"+
		"CD\u0005i\u0000\u0000DE\u0005n\u0000\u0000E\u0004\u0001\u0000\u0000\u0000"+
		"FG\u0005d\u0000\u0000GH\u0005e\u0000\u0000HI\u0005f\u0000\u0000IJ\u0005"+
		"i\u0000\u0000JK\u0005n\u0000\u0000KL\u0005i\u0000\u0000LM\u0005r\u0000"+
		"\u0000M\u0006\u0001\u0000\u0000\u0000NO\u0005s\u0000\u0000OP\u0005i\u0000"+
		"\u0000P\b\u0001\u0000\u0000\u0000QR\u0005s\u0000\u0000RS\u0005i\u0000"+
		"\u0000ST\u0005n\u0000\u0000TU\u0005o\u0000\u0000U\n\u0001\u0000\u0000"+
		"\u0000VW\u0005e\u0000\u0000WX\u0005s\u0000\u0000XY\u0005c\u0000\u0000"+
		"YZ\u0005r\u0000\u0000Z[\u0005i\u0000\u0000[\\\u0005b\u0000\u0000\\]\u0005"+
		"i\u0000\u0000]^\u0005r\u0000\u0000^\f\u0001\u0000\u0000\u0000_`\u0005"+
		"e\u0000\u0000`a\u0005n\u0000\u0000ab\u0005t\u0000\u0000bc\u0005e\u0000"+
		"\u0000cd\u0005r\u0000\u0000de\u0005o\u0000\u0000e\u000e\u0001\u0000\u0000"+
		"\u0000fg\u0005r\u0000\u0000gh\u0005e\u0000\u0000hi\u0005a\u0000\u0000"+
		"ij\u0005l\u0000\u0000j\u0010\u0001\u0000\u0000\u0000ko\u0007\u0000\u0000"+
		"\u0000ln\u0007\u0001\u0000\u0000ml\u0001\u0000\u0000\u0000nq\u0001\u0000"+
		"\u0000\u0000om\u0001\u0000\u0000\u0000op\u0001\u0000\u0000\u0000p\u0012"+
		"\u0001\u0000\u0000\u0000qo\u0001\u0000\u0000\u0000rt\u0007\u0002\u0000"+
		"\u0000sr\u0001\u0000\u0000\u0000tu\u0001\u0000\u0000\u0000us\u0001\u0000"+
		"\u0000\u0000uv\u0001\u0000\u0000\u0000v\u0014\u0001\u0000\u0000\u0000"+
		"wy\u0007\u0002\u0000\u0000xw\u0001\u0000\u0000\u0000yz\u0001\u0000\u0000"+
		"\u0000zx\u0001\u0000\u0000\u0000z{\u0001\u0000\u0000\u0000{|\u0001\u0000"+
		"\u0000\u0000|~\u0005.\u0000\u0000}\u007f\u0007\u0002\u0000\u0000~}\u0001"+
		"\u0000\u0000\u0000\u007f\u0080\u0001\u0000\u0000\u0000\u0080~\u0001\u0000"+
		"\u0000\u0000\u0080\u0081\u0001\u0000\u0000\u0000\u0081\u0016\u0001\u0000"+
		"\u0000\u0000\u0082\u0083\u0005=\u0000\u0000\u0083\u0018\u0001\u0000\u0000"+
		"\u0000\u0084\u0085\u0005+\u0000\u0000\u0085\u001a\u0001\u0000\u0000\u0000"+
		"\u0086\u0087\u0005-\u0000\u0000\u0087\u001c\u0001\u0000\u0000\u0000\u0088"+
		"\u0089\u0005*\u0000\u0000\u0089\u001e\u0001\u0000\u0000\u0000\u008a\u008b"+
		"\u0005/\u0000\u0000\u008b \u0001\u0000\u0000\u0000\u008c\u008d\u0005>"+
		"\u0000\u0000\u008d\"\u0001\u0000\u0000\u0000\u008e\u008f\u0005<\u0000"+
		"\u0000\u008f$\u0001\u0000\u0000\u0000\u0090\u0091\u0005=\u0000\u0000\u0091"+
		"\u0092\u0005=\u0000\u0000\u0092&\u0001\u0000\u0000\u0000\u0093\u0094\u0005"+
		"!\u0000\u0000\u0094\u0095\u0005=\u0000\u0000\u0095(\u0001\u0000\u0000"+
		"\u0000\u0096\u0097\u0005;\u0000\u0000\u0097*\u0001\u0000\u0000\u0000\u0098"+
		"\u0099\u0005(\u0000\u0000\u0099,\u0001\u0000\u0000\u0000\u009a\u009b\u0005"+
		")\u0000\u0000\u009b.\u0001\u0000\u0000\u0000\u009c\u009d\u0005{\u0000"+
		"\u0000\u009d0\u0001\u0000\u0000\u0000\u009e\u009f\u0005}\u0000\u0000\u009f"+
		"2\u0001\u0000\u0000\u0000\u00a0\u00a4\u0005\"\u0000\u0000\u00a1\u00a3"+
		"\b\u0003\u0000\u0000\u00a2\u00a1\u0001\u0000\u0000\u0000\u00a3\u00a6\u0001"+
		"\u0000\u0000\u0000\u00a4\u00a2\u0001\u0000\u0000\u0000\u00a4\u00a5\u0001"+
		"\u0000\u0000\u0000\u00a5\u00a7\u0001\u0000\u0000\u0000\u00a6\u00a4\u0001"+
		"\u0000\u0000\u0000\u00a7\u00a8\u0005\"\u0000\u0000\u00a84\u0001\u0000"+
		"\u0000\u0000\u00a9\u00ad\u0005#\u0000\u0000\u00aa\u00ac\b\u0004\u0000"+
		"\u0000\u00ab\u00aa\u0001\u0000\u0000\u0000\u00ac\u00af\u0001\u0000\u0000"+
		"\u0000\u00ad\u00ab\u0001\u0000\u0000\u0000\u00ad\u00ae\u0001\u0000\u0000"+
		"\u0000\u00ae\u00b0\u0001\u0000\u0000\u0000\u00af\u00ad\u0001\u0000\u0000"+
		"\u0000\u00b0\u00b1\u0006\u001a\u0000\u0000\u00b16\u0001\u0000\u0000\u0000"+
		"\u00b2\u00b3\u0007\u0005\u0000\u0000\u00b3\u00b4\u0001\u0000\u0000\u0000"+
		"\u00b4\u00b5\u0006\u001b\u0000\u0000\u00b58\u0001\u0000\u0000\u0000\u00b6"+
		"\u00b7\t\u0000\u0000\u0000\u00b7:\u0001\u0000\u0000\u0000\u0007\u0000"+
		"ouz\u0080\u00a4\u00ad\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}