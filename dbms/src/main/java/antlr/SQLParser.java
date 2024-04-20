// Generated from C:/Users/Zrafa/IdeaProjects/Database-Engine/dbms/src/main/java/antlr/SQL.g4 by ANTLR 4.13.1
package antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class SQLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, COMMA=22, OR=23, AND=24, EQ=25, 
		NEQ=26, GT=27, LT=28, GTEQ=29, LTEQ=30, PLUS=31, MINUS=32, MULT=33, DIV=34, 
		MOD=35, POW=36, NOT=37, SCOL=38, ASSIGN=39, OPAR=40, CPAR=41, OBRACE=42, 
		CBRACE=43, TRUE=44, FALSE=45, NIL=46, IF=47, ELSE=48, WHILE=49, LOG=50, 
		ID=51, INT=52, DOUBLE=53, STRING=54, COMMENT=55, SPACE=56, OTHER=57;
	public static final int
		RULE_parse = 0, RULE_block = 1, RULE_createTable = 2, RULE_createIndex = 3, 
		RULE_insertintoTable = 4, RULE_updateTable = 5, RULE_deleteFromTable = 6, 
		RULE_selectFromTable = 7, RULE_closerSelect = 8, RULE_arithmeticOperator = 9, 
		RULE_logicalOperator = 10, RULE_tableName = 11, RULE_indexName = 12, RULE_dataValue = 13, 
		RULE_clusteringKeyColumn = 14, RULE_clusteringKeyValue = 15, RULE_columnName = 16, 
		RULE_indexedColumnName = 17, RULE_dataType = 18, RULE_closerCreateTable = 19, 
		RULE_closerCreateIndex = 20, RULE_closerInsertIntoTable = 21, RULE_closerUpdate = 22, 
		RULE_closerDelete = 23, RULE_stat = 24, RULE_assignment = 25, RULE_if_stat = 26, 
		RULE_condition_block = 27, RULE_stat_block = 28, RULE_while_stat = 29, 
		RULE_log = 30, RULE_expr = 31, RULE_atom = 32;
	private static String[] makeRuleNames() {
		return new String[] {
			"parse", "block", "createTable", "createIndex", "insertintoTable", "updateTable", 
			"deleteFromTable", "selectFromTable", "closerSelect", "arithmeticOperator", 
			"logicalOperator", "tableName", "indexName", "dataValue", "clusteringKeyColumn", 
			"clusteringKeyValue", "columnName", "indexedColumnName", "dataType", 
			"closerCreateTable", "closerCreateIndex", "closerInsertIntoTable", "closerUpdate", 
			"closerDelete", "stat", "assignment", "if_stat", "condition_block", "stat_block", 
			"while_stat", "log", "expr", "atom"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'CREATE TABLE'", "'PRIMARY KEY'", "'CREATE INDEX'", "'ON'", "'INSERT INTO'", 
			"'VALUES'", "'UPDATE'", "'SET'", "'WHERE'", "'DELETE'", "'FROM'", "'AND'", 
			"'SELECT'", "'OR'", "'XOR'", "'and'", "'or'", "'xor'", "'INT'", "'DOUBLE'", 
			"'STRING'", "','", "'||'", "'&&'", "'=='", "'!='", "'>'", "'<'", "'>='", 
			"'<='", "'+'", "'-'", "'*'", "'/'", "'%'", "'^'", "'!'", "';'", "'='", 
			"'('", "')'", "'{'", "'}'", "'true'", "'false'", "'nil'", "'if'", "'else'", 
			"'while'", "'log'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, "COMMA", 
			"OR", "AND", "EQ", "NEQ", "GT", "LT", "GTEQ", "LTEQ", "PLUS", "MINUS", 
			"MULT", "DIV", "MOD", "POW", "NOT", "SCOL", "ASSIGN", "OPAR", "CPAR", 
			"OBRACE", "CBRACE", "TRUE", "FALSE", "NIL", "IF", "ELSE", "WHILE", "LOG", 
			"ID", "INT", "DOUBLE", "STRING", "COMMENT", "SPACE", "OTHER"
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
	public String getGrammarFileName() { return "SQL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SQLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParseContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode EOF() { return getToken(SQLParser.EOF, 0); }
		public CreateTableContext createTable() {
			return getRuleContext(CreateTableContext.class,0);
		}
		public CreateIndexContext createIndex() {
			return getRuleContext(CreateIndexContext.class,0);
		}
		public InsertintoTableContext insertintoTable() {
			return getRuleContext(InsertintoTableContext.class,0);
		}
		public UpdateTableContext updateTable() {
			return getRuleContext(UpdateTableContext.class,0);
		}
		public DeleteFromTableContext deleteFromTable() {
			return getRuleContext(DeleteFromTableContext.class,0);
		}
		public SelectFromTableContext selectFromTable() {
			return getRuleContext(SelectFromTableContext.class,0);
		}
		public ParseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parse; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterParse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitParse(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitParse(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParseContext parse() throws RecognitionException {
		ParseContext _localctx = new ParseContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_parse);
		try {
			setState(87);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case EOF:
			case IF:
			case WHILE:
			case LOG:
			case ID:
			case OTHER:
				enterOuterAlt(_localctx, 1);
				{
				setState(66);
				block();
				setState(67);
				match(EOF);
				}
				break;
			case T__0:
				enterOuterAlt(_localctx, 2);
				{
				setState(69);
				createTable();
				setState(70);
				match(EOF);
				}
				break;
			case T__2:
				enterOuterAlt(_localctx, 3);
				{
				setState(72);
				createIndex();
				setState(73);
				match(EOF);
				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 4);
				{
				setState(75);
				insertintoTable();
				setState(76);
				match(EOF);
				}
				break;
			case T__6:
				enterOuterAlt(_localctx, 5);
				{
				setState(78);
				updateTable();
				setState(79);
				match(EOF);
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 6);
				{
				setState(81);
				deleteFromTable();
				setState(82);
				match(EOF);
				}
				break;
			case T__12:
				enterOuterAlt(_localctx, 7);
				{
				setState(84);
				selectFromTable();
				setState(85);
				match(EOF);
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
	public static class BlockContext extends ParserRuleContext {
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 148196575238160384L) != 0)) {
				{
				{
				setState(89);
				stat();
				}
				}
				setState(94);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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
	public static class CreateTableContext extends ParserRuleContext {
		public TableNameContext tableName() {
			return getRuleContext(TableNameContext.class,0);
		}
		public TerminalNode OPAR() { return getToken(SQLParser.OPAR, 0); }
		public ClusteringKeyColumnContext clusteringKeyColumn() {
			return getRuleContext(ClusteringKeyColumnContext.class,0);
		}
		public List<DataTypeContext> dataType() {
			return getRuleContexts(DataTypeContext.class);
		}
		public DataTypeContext dataType(int i) {
			return getRuleContext(DataTypeContext.class,i);
		}
		public TerminalNode CPAR() { return getToken(SQLParser.CPAR, 0); }
		public CloserCreateTableContext closerCreateTable() {
			return getRuleContext(CloserCreateTableContext.class,0);
		}
		public List<TerminalNode> COMMA() { return getTokens(SQLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(SQLParser.COMMA, i);
		}
		public List<ColumnNameContext> columnName() {
			return getRuleContexts(ColumnNameContext.class);
		}
		public ColumnNameContext columnName(int i) {
			return getRuleContext(ColumnNameContext.class,i);
		}
		public CreateTableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createTable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterCreateTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitCreateTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitCreateTable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateTableContext createTable() throws RecognitionException {
		CreateTableContext _localctx = new CreateTableContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_createTable);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95);
			match(T__0);
			setState(96);
			tableName();
			setState(97);
			match(OPAR);
			setState(98);
			clusteringKeyColumn();
			setState(99);
			dataType();
			setState(100);
			match(T__1);
			setState(107);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(101);
				match(COMMA);
				setState(102);
				columnName();
				setState(103);
				dataType();
				}
				}
				setState(109);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(110);
			match(CPAR);
			setState(111);
			closerCreateTable();
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
	public static class CreateIndexContext extends ParserRuleContext {
		public IndexNameContext indexName() {
			return getRuleContext(IndexNameContext.class,0);
		}
		public TableNameContext tableName() {
			return getRuleContext(TableNameContext.class,0);
		}
		public TerminalNode OPAR() { return getToken(SQLParser.OPAR, 0); }
		public IndexedColumnNameContext indexedColumnName() {
			return getRuleContext(IndexedColumnNameContext.class,0);
		}
		public TerminalNode CPAR() { return getToken(SQLParser.CPAR, 0); }
		public CloserCreateIndexContext closerCreateIndex() {
			return getRuleContext(CloserCreateIndexContext.class,0);
		}
		public CreateIndexContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createIndex; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterCreateIndex(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitCreateIndex(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitCreateIndex(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateIndexContext createIndex() throws RecognitionException {
		CreateIndexContext _localctx = new CreateIndexContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_createIndex);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			match(T__2);
			setState(114);
			indexName();
			setState(115);
			match(T__3);
			setState(116);
			tableName();
			setState(117);
			match(OPAR);
			setState(118);
			indexedColumnName();
			setState(119);
			match(CPAR);
			setState(120);
			closerCreateIndex();
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
	public static class InsertintoTableContext extends ParserRuleContext {
		public TableNameContext tableName() {
			return getRuleContext(TableNameContext.class,0);
		}
		public List<TerminalNode> OPAR() { return getTokens(SQLParser.OPAR); }
		public TerminalNode OPAR(int i) {
			return getToken(SQLParser.OPAR, i);
		}
		public List<ColumnNameContext> columnName() {
			return getRuleContexts(ColumnNameContext.class);
		}
		public ColumnNameContext columnName(int i) {
			return getRuleContext(ColumnNameContext.class,i);
		}
		public List<TerminalNode> CPAR() { return getTokens(SQLParser.CPAR); }
		public TerminalNode CPAR(int i) {
			return getToken(SQLParser.CPAR, i);
		}
		public List<DataValueContext> dataValue() {
			return getRuleContexts(DataValueContext.class);
		}
		public DataValueContext dataValue(int i) {
			return getRuleContext(DataValueContext.class,i);
		}
		public CloserInsertIntoTableContext closerInsertIntoTable() {
			return getRuleContext(CloserInsertIntoTableContext.class,0);
		}
		public List<TerminalNode> COMMA() { return getTokens(SQLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(SQLParser.COMMA, i);
		}
		public InsertintoTableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_insertintoTable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterInsertintoTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitInsertintoTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitInsertintoTable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InsertintoTableContext insertintoTable() throws RecognitionException {
		InsertintoTableContext _localctx = new InsertintoTableContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_insertintoTable);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			match(T__4);
			setState(123);
			tableName();
			setState(124);
			match(OPAR);
			setState(125);
			columnName();
			setState(130);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(126);
				match(COMMA);
				setState(127);
				columnName();
				}
				}
				setState(132);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(133);
			match(CPAR);
			setState(134);
			match(T__5);
			setState(135);
			match(OPAR);
			setState(136);
			dataValue();
			setState(141);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(137);
				match(COMMA);
				setState(138);
				dataValue();
				}
				}
				setState(143);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(144);
			match(CPAR);
			setState(145);
			closerInsertIntoTable();
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
	public static class UpdateTableContext extends ParserRuleContext {
		public TableNameContext tableName() {
			return getRuleContext(TableNameContext.class,0);
		}
		public List<ColumnNameContext> columnName() {
			return getRuleContexts(ColumnNameContext.class);
		}
		public ColumnNameContext columnName(int i) {
			return getRuleContext(ColumnNameContext.class,i);
		}
		public List<TerminalNode> ASSIGN() { return getTokens(SQLParser.ASSIGN); }
		public TerminalNode ASSIGN(int i) {
			return getToken(SQLParser.ASSIGN, i);
		}
		public List<DataValueContext> dataValue() {
			return getRuleContexts(DataValueContext.class);
		}
		public DataValueContext dataValue(int i) {
			return getRuleContext(DataValueContext.class,i);
		}
		public ClusteringKeyColumnContext clusteringKeyColumn() {
			return getRuleContext(ClusteringKeyColumnContext.class,0);
		}
		public ClusteringKeyValueContext clusteringKeyValue() {
			return getRuleContext(ClusteringKeyValueContext.class,0);
		}
		public CloserUpdateContext closerUpdate() {
			return getRuleContext(CloserUpdateContext.class,0);
		}
		public List<TerminalNode> COMMA() { return getTokens(SQLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(SQLParser.COMMA, i);
		}
		public UpdateTableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_updateTable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterUpdateTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitUpdateTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitUpdateTable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UpdateTableContext updateTable() throws RecognitionException {
		UpdateTableContext _localctx = new UpdateTableContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_updateTable);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(147);
			match(T__6);
			setState(148);
			tableName();
			setState(149);
			match(T__7);
			setState(150);
			columnName();
			setState(151);
			match(ASSIGN);
			setState(152);
			dataValue();
			setState(160);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(153);
				match(COMMA);
				setState(154);
				columnName();
				setState(155);
				match(ASSIGN);
				setState(156);
				dataValue();
				}
				}
				setState(162);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(163);
			match(T__8);
			setState(164);
			clusteringKeyColumn();
			setState(165);
			match(ASSIGN);
			setState(166);
			clusteringKeyValue();
			setState(167);
			closerUpdate();
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
	public static class DeleteFromTableContext extends ParserRuleContext {
		public TableNameContext tableName() {
			return getRuleContext(TableNameContext.class,0);
		}
		public List<ColumnNameContext> columnName() {
			return getRuleContexts(ColumnNameContext.class);
		}
		public ColumnNameContext columnName(int i) {
			return getRuleContext(ColumnNameContext.class,i);
		}
		public List<TerminalNode> ASSIGN() { return getTokens(SQLParser.ASSIGN); }
		public TerminalNode ASSIGN(int i) {
			return getToken(SQLParser.ASSIGN, i);
		}
		public List<DataValueContext> dataValue() {
			return getRuleContexts(DataValueContext.class);
		}
		public DataValueContext dataValue(int i) {
			return getRuleContext(DataValueContext.class,i);
		}
		public CloserDeleteContext closerDelete() {
			return getRuleContext(CloserDeleteContext.class,0);
		}
		public DeleteFromTableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_deleteFromTable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterDeleteFromTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitDeleteFromTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitDeleteFromTable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeleteFromTableContext deleteFromTable() throws RecognitionException {
		DeleteFromTableContext _localctx = new DeleteFromTableContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_deleteFromTable);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(169);
			match(T__9);
			setState(170);
			match(T__10);
			setState(171);
			tableName();
			setState(172);
			match(T__8);
			setState(173);
			columnName();
			setState(174);
			match(ASSIGN);
			setState(175);
			dataValue();
			setState(183);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__11) {
				{
				{
				setState(176);
				match(T__11);
				setState(177);
				columnName();
				setState(178);
				match(ASSIGN);
				setState(179);
				dataValue();
				}
				}
				setState(185);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(186);
			closerDelete();
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
	public static class SelectFromTableContext extends ParserRuleContext {
		public TerminalNode MULT() { return getToken(SQLParser.MULT, 0); }
		public TableNameContext tableName() {
			return getRuleContext(TableNameContext.class,0);
		}
		public List<ColumnNameContext> columnName() {
			return getRuleContexts(ColumnNameContext.class);
		}
		public ColumnNameContext columnName(int i) {
			return getRuleContext(ColumnNameContext.class,i);
		}
		public List<ArithmeticOperatorContext> arithmeticOperator() {
			return getRuleContexts(ArithmeticOperatorContext.class);
		}
		public ArithmeticOperatorContext arithmeticOperator(int i) {
			return getRuleContext(ArithmeticOperatorContext.class,i);
		}
		public List<DataValueContext> dataValue() {
			return getRuleContexts(DataValueContext.class);
		}
		public DataValueContext dataValue(int i) {
			return getRuleContext(DataValueContext.class,i);
		}
		public CloserSelectContext closerSelect() {
			return getRuleContext(CloserSelectContext.class,0);
		}
		public List<LogicalOperatorContext> logicalOperator() {
			return getRuleContexts(LogicalOperatorContext.class);
		}
		public LogicalOperatorContext logicalOperator(int i) {
			return getRuleContext(LogicalOperatorContext.class,i);
		}
		public SelectFromTableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectFromTable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterSelectFromTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitSelectFromTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitSelectFromTable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectFromTableContext selectFromTable() throws RecognitionException {
		SelectFromTableContext _localctx = new SelectFromTableContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_selectFromTable);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(188);
			match(T__12);
			setState(189);
			match(MULT);
			setState(190);
			match(T__10);
			setState(191);
			tableName();
			setState(192);
			match(T__8);
			setState(193);
			columnName();
			setState(194);
			arithmeticOperator();
			setState(195);
			dataValue();
			setState(203);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 512000L) != 0)) {
				{
				{
				setState(196);
				logicalOperator();
				setState(197);
				columnName();
				setState(198);
				arithmeticOperator();
				setState(199);
				dataValue();
				}
				}
				setState(205);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(206);
			closerSelect();
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
	public static class CloserSelectContext extends ParserRuleContext {
		public TerminalNode SCOL() { return getToken(SQLParser.SCOL, 0); }
		public CloserSelectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_closerSelect; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterCloserSelect(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitCloserSelect(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitCloserSelect(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CloserSelectContext closerSelect() throws RecognitionException {
		CloserSelectContext _localctx = new CloserSelectContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_closerSelect);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(208);
			match(SCOL);
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
	public static class ArithmeticOperatorContext extends ParserRuleContext {
		public TerminalNode GT() { return getToken(SQLParser.GT, 0); }
		public TerminalNode GTEQ() { return getToken(SQLParser.GTEQ, 0); }
		public TerminalNode LT() { return getToken(SQLParser.LT, 0); }
		public TerminalNode LTEQ() { return getToken(SQLParser.LTEQ, 0); }
		public TerminalNode ASSIGN() { return getToken(SQLParser.ASSIGN, 0); }
		public ArithmeticOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithmeticOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterArithmeticOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitArithmeticOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitArithmeticOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArithmeticOperatorContext arithmeticOperator() throws RecognitionException {
		ArithmeticOperatorContext _localctx = new ArithmeticOperatorContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_arithmeticOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(210);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 551769079808L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
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
	public static class LogicalOperatorContext extends ParserRuleContext {
		public LogicalOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterLogicalOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitLogicalOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitLogicalOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalOperatorContext logicalOperator() throws RecognitionException {
		LogicalOperatorContext _localctx = new LogicalOperatorContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_logicalOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(212);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 512000L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
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
	public static class TableNameContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(SQLParser.STRING, 0); }
		public TableNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterTableName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitTableName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitTableName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableNameContext tableName() throws RecognitionException {
		TableNameContext _localctx = new TableNameContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_tableName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(214);
			match(STRING);
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
	public static class IndexNameContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(SQLParser.STRING, 0); }
		public IndexNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_indexName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterIndexName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitIndexName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitIndexName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IndexNameContext indexName() throws RecognitionException {
		IndexNameContext _localctx = new IndexNameContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_indexName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(216);
			match(STRING);
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
	public static class DataValueContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(SQLParser.INT, 0); }
		public TerminalNode DOUBLE() { return getToken(SQLParser.DOUBLE, 0); }
		public TerminalNode STRING() { return getToken(SQLParser.STRING, 0); }
		public DataValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dataValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterDataValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitDataValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitDataValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DataValueContext dataValue() throws RecognitionException {
		DataValueContext _localctx = new DataValueContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_dataValue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(218);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 31525197391593472L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
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
	public static class ClusteringKeyColumnContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(SQLParser.STRING, 0); }
		public ClusteringKeyColumnContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_clusteringKeyColumn; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterClusteringKeyColumn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitClusteringKeyColumn(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitClusteringKeyColumn(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClusteringKeyColumnContext clusteringKeyColumn() throws RecognitionException {
		ClusteringKeyColumnContext _localctx = new ClusteringKeyColumnContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_clusteringKeyColumn);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(220);
			match(STRING);
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
	public static class ClusteringKeyValueContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(SQLParser.STRING, 0); }
		public TerminalNode INT() { return getToken(SQLParser.INT, 0); }
		public TerminalNode DOUBLE() { return getToken(SQLParser.DOUBLE, 0); }
		public ClusteringKeyValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_clusteringKeyValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterClusteringKeyValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitClusteringKeyValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitClusteringKeyValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClusteringKeyValueContext clusteringKeyValue() throws RecognitionException {
		ClusteringKeyValueContext _localctx = new ClusteringKeyValueContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_clusteringKeyValue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(222);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 31525197391593472L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
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
	public static class ColumnNameContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(SQLParser.STRING, 0); }
		public ColumnNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_columnName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterColumnName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitColumnName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitColumnName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ColumnNameContext columnName() throws RecognitionException {
		ColumnNameContext _localctx = new ColumnNameContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_columnName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(224);
			match(STRING);
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
	public static class IndexedColumnNameContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(SQLParser.STRING, 0); }
		public IndexedColumnNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_indexedColumnName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterIndexedColumnName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitIndexedColumnName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitIndexedColumnName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IndexedColumnNameContext indexedColumnName() throws RecognitionException {
		IndexedColumnNameContext _localctx = new IndexedColumnNameContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_indexedColumnName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(226);
			match(STRING);
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
	public static class DataTypeContext extends ParserRuleContext {
		public DataTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dataType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterDataType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitDataType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitDataType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DataTypeContext dataType() throws RecognitionException {
		DataTypeContext _localctx = new DataTypeContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_dataType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(228);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 3670016L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
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
	public static class CloserCreateTableContext extends ParserRuleContext {
		public TerminalNode SCOL() { return getToken(SQLParser.SCOL, 0); }
		public CloserCreateTableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_closerCreateTable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterCloserCreateTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitCloserCreateTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitCloserCreateTable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CloserCreateTableContext closerCreateTable() throws RecognitionException {
		CloserCreateTableContext _localctx = new CloserCreateTableContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_closerCreateTable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(230);
			match(SCOL);
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
	public static class CloserCreateIndexContext extends ParserRuleContext {
		public TerminalNode SCOL() { return getToken(SQLParser.SCOL, 0); }
		public CloserCreateIndexContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_closerCreateIndex; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterCloserCreateIndex(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitCloserCreateIndex(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitCloserCreateIndex(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CloserCreateIndexContext closerCreateIndex() throws RecognitionException {
		CloserCreateIndexContext _localctx = new CloserCreateIndexContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_closerCreateIndex);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(232);
			match(SCOL);
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
	public static class CloserInsertIntoTableContext extends ParserRuleContext {
		public TerminalNode SCOL() { return getToken(SQLParser.SCOL, 0); }
		public CloserInsertIntoTableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_closerInsertIntoTable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterCloserInsertIntoTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitCloserInsertIntoTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitCloserInsertIntoTable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CloserInsertIntoTableContext closerInsertIntoTable() throws RecognitionException {
		CloserInsertIntoTableContext _localctx = new CloserInsertIntoTableContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_closerInsertIntoTable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(234);
			match(SCOL);
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
	public static class CloserUpdateContext extends ParserRuleContext {
		public TerminalNode SCOL() { return getToken(SQLParser.SCOL, 0); }
		public CloserUpdateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_closerUpdate; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterCloserUpdate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitCloserUpdate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitCloserUpdate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CloserUpdateContext closerUpdate() throws RecognitionException {
		CloserUpdateContext _localctx = new CloserUpdateContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_closerUpdate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(236);
			match(SCOL);
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
	public static class CloserDeleteContext extends ParserRuleContext {
		public TerminalNode SCOL() { return getToken(SQLParser.SCOL, 0); }
		public CloserDeleteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_closerDelete; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterCloserDelete(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitCloserDelete(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitCloserDelete(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CloserDeleteContext closerDelete() throws RecognitionException {
		CloserDeleteContext _localctx = new CloserDeleteContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_closerDelete);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(238);
			match(SCOL);
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
	public static class StatContext extends ParserRuleContext {
		public Token OTHER;
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public If_statContext if_stat() {
			return getRuleContext(If_statContext.class,0);
		}
		public While_statContext while_stat() {
			return getRuleContext(While_statContext.class,0);
		}
		public LogContext log() {
			return getRuleContext(LogContext.class,0);
		}
		public TerminalNode OTHER() { return getToken(SQLParser.OTHER, 0); }
		public StatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatContext stat() throws RecognitionException {
		StatContext _localctx = new StatContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_stat);
		try {
			setState(246);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(240);
				assignment();
				}
				break;
			case IF:
				enterOuterAlt(_localctx, 2);
				{
				setState(241);
				if_stat();
				}
				break;
			case WHILE:
				enterOuterAlt(_localctx, 3);
				{
				setState(242);
				while_stat();
				}
				break;
			case LOG:
				enterOuterAlt(_localctx, 4);
				{
				setState(243);
				log();
				}
				break;
			case OTHER:
				enterOuterAlt(_localctx, 5);
				{
				setState(244);
				((StatContext)_localctx).OTHER = match(OTHER);
				System.err.println("unknown char: " + (((StatContext)_localctx).OTHER!=null?((StatContext)_localctx).OTHER.getText():null));
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
	public static class AssignmentContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(SQLParser.ID, 0); }
		public TerminalNode ASSIGN() { return getToken(SQLParser.ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode SCOL() { return getToken(SQLParser.SCOL, 0); }
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(248);
			match(ID);
			setState(249);
			match(ASSIGN);
			setState(250);
			expr(0);
			setState(251);
			match(SCOL);
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
	public static class If_statContext extends ParserRuleContext {
		public List<TerminalNode> IF() { return getTokens(SQLParser.IF); }
		public TerminalNode IF(int i) {
			return getToken(SQLParser.IF, i);
		}
		public List<Condition_blockContext> condition_block() {
			return getRuleContexts(Condition_blockContext.class);
		}
		public Condition_blockContext condition_block(int i) {
			return getRuleContext(Condition_blockContext.class,i);
		}
		public List<TerminalNode> ELSE() { return getTokens(SQLParser.ELSE); }
		public TerminalNode ELSE(int i) {
			return getToken(SQLParser.ELSE, i);
		}
		public Stat_blockContext stat_block() {
			return getRuleContext(Stat_blockContext.class,0);
		}
		public If_statContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_if_stat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterIf_stat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitIf_stat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitIf_stat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final If_statContext if_stat() throws RecognitionException {
		If_statContext _localctx = new If_statContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_if_stat);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(253);
			match(IF);
			setState(254);
			condition_block();
			setState(260);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(255);
					match(ELSE);
					setState(256);
					match(IF);
					setState(257);
					condition_block();
					}
					} 
				}
				setState(262);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			}
			setState(265);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				{
				setState(263);
				match(ELSE);
				setState(264);
				stat_block();
				}
				break;
			}
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
	public static class Condition_blockContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Stat_blockContext stat_block() {
			return getRuleContext(Stat_blockContext.class,0);
		}
		public Condition_blockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterCondition_block(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitCondition_block(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitCondition_block(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Condition_blockContext condition_block() throws RecognitionException {
		Condition_blockContext _localctx = new Condition_blockContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_condition_block);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(267);
			expr(0);
			setState(268);
			stat_block();
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
	public static class Stat_blockContext extends ParserRuleContext {
		public TerminalNode OBRACE() { return getToken(SQLParser.OBRACE, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode CBRACE() { return getToken(SQLParser.CBRACE, 0); }
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public Stat_blockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stat_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterStat_block(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitStat_block(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitStat_block(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Stat_blockContext stat_block() throws RecognitionException {
		Stat_blockContext _localctx = new Stat_blockContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_stat_block);
		try {
			setState(275);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OBRACE:
				enterOuterAlt(_localctx, 1);
				{
				setState(270);
				match(OBRACE);
				setState(271);
				block();
				setState(272);
				match(CBRACE);
				}
				break;
			case IF:
			case WHILE:
			case LOG:
			case ID:
			case OTHER:
				enterOuterAlt(_localctx, 2);
				{
				setState(274);
				stat();
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
	public static class While_statContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(SQLParser.WHILE, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Stat_blockContext stat_block() {
			return getRuleContext(Stat_blockContext.class,0);
		}
		public While_statContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_while_stat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterWhile_stat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitWhile_stat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitWhile_stat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final While_statContext while_stat() throws RecognitionException {
		While_statContext _localctx = new While_statContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_while_stat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(277);
			match(WHILE);
			setState(278);
			expr(0);
			setState(279);
			stat_block();
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
	public static class LogContext extends ParserRuleContext {
		public TerminalNode LOG() { return getToken(SQLParser.LOG, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode SCOL() { return getToken(SQLParser.SCOL, 0); }
		public LogContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_log; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterLog(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitLog(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitLog(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogContext log() throws RecognitionException {
		LogContext _localctx = new LogContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_log);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(281);
			match(LOG);
			setState(282);
			expr(0);
			setState(283);
			match(SCOL);
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
	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NotExprContext extends ExprContext {
		public TerminalNode NOT() { return getToken(SQLParser.NOT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public NotExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterNotExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitNotExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitNotExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UnaryMinusExprContext extends ExprContext {
		public TerminalNode MINUS() { return getToken(SQLParser.MINUS, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public UnaryMinusExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterUnaryMinusExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitUnaryMinusExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitUnaryMinusExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MultiplicationExprContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode MULT() { return getToken(SQLParser.MULT, 0); }
		public TerminalNode DIV() { return getToken(SQLParser.DIV, 0); }
		public TerminalNode MOD() { return getToken(SQLParser.MOD, 0); }
		public MultiplicationExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterMultiplicationExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitMultiplicationExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitMultiplicationExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AtomExprContext extends ExprContext {
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public AtomExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterAtomExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitAtomExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitAtomExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OrExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode OR() { return getToken(SQLParser.OR, 0); }
		public OrExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterOrExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitOrExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitOrExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AdditiveExprContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode PLUS() { return getToken(SQLParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(SQLParser.MINUS, 0); }
		public AdditiveExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterAdditiveExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitAdditiveExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitAdditiveExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PowExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode POW() { return getToken(SQLParser.POW, 0); }
		public PowExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterPowExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitPowExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitPowExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RelationalExprContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode LTEQ() { return getToken(SQLParser.LTEQ, 0); }
		public TerminalNode GTEQ() { return getToken(SQLParser.GTEQ, 0); }
		public TerminalNode LT() { return getToken(SQLParser.LT, 0); }
		public TerminalNode GT() { return getToken(SQLParser.GT, 0); }
		public RelationalExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterRelationalExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitRelationalExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitRelationalExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EqualityExprContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode EQ() { return getToken(SQLParser.EQ, 0); }
		public TerminalNode NEQ() { return getToken(SQLParser.NEQ, 0); }
		public EqualityExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterEqualityExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitEqualityExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitEqualityExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AndExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode AND() { return getToken(SQLParser.AND, 0); }
		public AndExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterAndExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitAndExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitAndExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 62;
		enterRecursionRule(_localctx, 62, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(291);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case MINUS:
				{
				_localctx = new UnaryMinusExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(286);
				match(MINUS);
				setState(287);
				expr(9);
				}
				break;
			case NOT:
				{
				_localctx = new NotExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(288);
				match(NOT);
				setState(289);
				expr(8);
				}
				break;
			case OPAR:
			case TRUE:
			case FALSE:
			case NIL:
			case ID:
			case INT:
			case DOUBLE:
			case STRING:
				{
				_localctx = new AtomExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(290);
				atom();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(316);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(314);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
					case 1:
						{
						_localctx = new PowExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(293);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(294);
						match(POW);
						setState(295);
						expr(11);
						}
						break;
					case 2:
						{
						_localctx = new MultiplicationExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(296);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(297);
						((MultiplicationExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 60129542144L) != 0)) ) {
							((MultiplicationExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(298);
						expr(8);
						}
						break;
					case 3:
						{
						_localctx = new AdditiveExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(299);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(300);
						((AdditiveExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==MINUS) ) {
							((AdditiveExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(301);
						expr(7);
						}
						break;
					case 4:
						{
						_localctx = new RelationalExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(302);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(303);
						((RelationalExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 2013265920L) != 0)) ) {
							((RelationalExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(304);
						expr(6);
						}
						break;
					case 5:
						{
						_localctx = new EqualityExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(305);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(306);
						((EqualityExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==EQ || _la==NEQ) ) {
							((EqualityExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(307);
						expr(5);
						}
						break;
					case 6:
						{
						_localctx = new AndExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(308);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(309);
						match(AND);
						setState(310);
						expr(4);
						}
						break;
					case 7:
						{
						_localctx = new OrExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(311);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(312);
						match(OR);
						setState(313);
						expr(3);
						}
						break;
					}
					} 
				}
				setState(318);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AtomContext extends ParserRuleContext {
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
	 
		public AtomContext() { }
		public void copyFrom(AtomContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParExprContext extends AtomContext {
		public TerminalNode OPAR() { return getToken(SQLParser.OPAR, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode CPAR() { return getToken(SQLParser.CPAR, 0); }
		public ParExprContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterParExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitParExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitParExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BooleanAtomContext extends AtomContext {
		public TerminalNode TRUE() { return getToken(SQLParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(SQLParser.FALSE, 0); }
		public BooleanAtomContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterBooleanAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitBooleanAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitBooleanAtom(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IdAtomContext extends AtomContext {
		public TerminalNode ID() { return getToken(SQLParser.ID, 0); }
		public IdAtomContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterIdAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitIdAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitIdAtom(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringAtomContext extends AtomContext {
		public TerminalNode STRING() { return getToken(SQLParser.STRING, 0); }
		public StringAtomContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterStringAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitStringAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitStringAtom(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NilAtomContext extends AtomContext {
		public TerminalNode NIL() { return getToken(SQLParser.NIL, 0); }
		public NilAtomContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterNilAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitNilAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitNilAtom(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NumberAtomContext extends AtomContext {
		public TerminalNode INT() { return getToken(SQLParser.INT, 0); }
		public TerminalNode DOUBLE() { return getToken(SQLParser.DOUBLE, 0); }
		public NumberAtomContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterNumberAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitNumberAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitNumberAtom(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_atom);
		int _la;
		try {
			setState(328);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OPAR:
				_localctx = new ParExprContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(319);
				match(OPAR);
				setState(320);
				expr(0);
				setState(321);
				match(CPAR);
				}
				break;
			case INT:
			case DOUBLE:
				_localctx = new NumberAtomContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(323);
				_la = _input.LA(1);
				if ( !(_la==INT || _la==DOUBLE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case TRUE:
			case FALSE:
				_localctx = new BooleanAtomContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(324);
				_la = _input.LA(1);
				if ( !(_la==TRUE || _la==FALSE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case ID:
				_localctx = new IdAtomContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(325);
				match(ID);
				}
				break;
			case STRING:
				_localctx = new StringAtomContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(326);
				match(STRING);
				}
				break;
			case NIL:
				_localctx = new NilAtomContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(327);
				match(NIL);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 31:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 10);
		case 1:
			return precpred(_ctx, 7);
		case 2:
			return precpred(_ctx, 6);
		case 3:
			return precpred(_ctx, 5);
		case 4:
			return precpred(_ctx, 4);
		case 5:
			return precpred(_ctx, 3);
		case 6:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u00019\u014b\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e"+
		"\u0002\u001f\u0007\u001f\u0002 \u0007 \u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0003\u0000X\b\u0000\u0001\u0001\u0005\u0001[\b\u0001\n\u0001\f\u0001"+
		"^\t\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u0002"+
		"j\b\u0002\n\u0002\f\u0002m\t\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0005\u0004\u0081\b\u0004\n\u0004"+
		"\f\u0004\u0084\t\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0005\u0004\u008c\b\u0004\n\u0004\f\u0004\u008f"+
		"\t\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0005\u0005\u009f\b\u0005\n\u0005\f\u0005"+
		"\u00a2\t\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0005\u0006\u00b6\b\u0006\n\u0006\f\u0006\u00b9\t\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0005\u0007\u00ca\b\u0007\n\u0007\f\u0007"+
		"\u00cd\t\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001\t"+
		"\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\r\u0001"+
		"\r\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010"+
		"\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013"+
		"\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016"+
		"\u0001\u0017\u0001\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0003\u0018\u00f7\b\u0018\u0001\u0019\u0001\u0019"+
		"\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u001a\u0001\u001a\u0001\u001a"+
		"\u0001\u001a\u0001\u001a\u0005\u001a\u0103\b\u001a\n\u001a\f\u001a\u0106"+
		"\t\u001a\u0001\u001a\u0001\u001a\u0003\u001a\u010a\b\u001a\u0001\u001b"+
		"\u0001\u001b\u0001\u001b\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c"+
		"\u0001\u001c\u0003\u001c\u0114\b\u001c\u0001\u001d\u0001\u001d\u0001\u001d"+
		"\u0001\u001d\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001f"+
		"\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0003\u001f"+
		"\u0124\b\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f"+
		"\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f"+
		"\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f"+
		"\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0005\u001f\u013b\b\u001f"+
		"\n\u001f\f\u001f\u013e\t\u001f\u0001 \u0001 \u0001 \u0001 \u0001 \u0001"+
		" \u0001 \u0001 \u0001 \u0003 \u0149\b \u0001 \u0000\u0001>!\u0000\u0002"+
		"\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e"+
		" \"$&(*,.02468:<>@\u0000\n\u0002\u0000\u001b\u001e\'\'\u0002\u0000\f\f"+
		"\u000e\u0012\u0001\u000046\u0001\u0000\u0013\u0015\u0001\u0000!#\u0001"+
		"\u0000\u001f \u0001\u0000\u001b\u001e\u0001\u0000\u0019\u001a\u0001\u0000"+
		"45\u0001\u0000,-\u014b\u0000W\u0001\u0000\u0000\u0000\u0002\\\u0001\u0000"+
		"\u0000\u0000\u0004_\u0001\u0000\u0000\u0000\u0006q\u0001\u0000\u0000\u0000"+
		"\bz\u0001\u0000\u0000\u0000\n\u0093\u0001\u0000\u0000\u0000\f\u00a9\u0001"+
		"\u0000\u0000\u0000\u000e\u00bc\u0001\u0000\u0000\u0000\u0010\u00d0\u0001"+
		"\u0000\u0000\u0000\u0012\u00d2\u0001\u0000\u0000\u0000\u0014\u00d4\u0001"+
		"\u0000\u0000\u0000\u0016\u00d6\u0001\u0000\u0000\u0000\u0018\u00d8\u0001"+
		"\u0000\u0000\u0000\u001a\u00da\u0001\u0000\u0000\u0000\u001c\u00dc\u0001"+
		"\u0000\u0000\u0000\u001e\u00de\u0001\u0000\u0000\u0000 \u00e0\u0001\u0000"+
		"\u0000\u0000\"\u00e2\u0001\u0000\u0000\u0000$\u00e4\u0001\u0000\u0000"+
		"\u0000&\u00e6\u0001\u0000\u0000\u0000(\u00e8\u0001\u0000\u0000\u0000*"+
		"\u00ea\u0001\u0000\u0000\u0000,\u00ec\u0001\u0000\u0000\u0000.\u00ee\u0001"+
		"\u0000\u0000\u00000\u00f6\u0001\u0000\u0000\u00002\u00f8\u0001\u0000\u0000"+
		"\u00004\u00fd\u0001\u0000\u0000\u00006\u010b\u0001\u0000\u0000\u00008"+
		"\u0113\u0001\u0000\u0000\u0000:\u0115\u0001\u0000\u0000\u0000<\u0119\u0001"+
		"\u0000\u0000\u0000>\u0123\u0001\u0000\u0000\u0000@\u0148\u0001\u0000\u0000"+
		"\u0000BC\u0003\u0002\u0001\u0000CD\u0005\u0000\u0000\u0001DX\u0001\u0000"+
		"\u0000\u0000EF\u0003\u0004\u0002\u0000FG\u0005\u0000\u0000\u0001GX\u0001"+
		"\u0000\u0000\u0000HI\u0003\u0006\u0003\u0000IJ\u0005\u0000\u0000\u0001"+
		"JX\u0001\u0000\u0000\u0000KL\u0003\b\u0004\u0000LM\u0005\u0000\u0000\u0001"+
		"MX\u0001\u0000\u0000\u0000NO\u0003\n\u0005\u0000OP\u0005\u0000\u0000\u0001"+
		"PX\u0001\u0000\u0000\u0000QR\u0003\f\u0006\u0000RS\u0005\u0000\u0000\u0001"+
		"SX\u0001\u0000\u0000\u0000TU\u0003\u000e\u0007\u0000UV\u0005\u0000\u0000"+
		"\u0001VX\u0001\u0000\u0000\u0000WB\u0001\u0000\u0000\u0000WE\u0001\u0000"+
		"\u0000\u0000WH\u0001\u0000\u0000\u0000WK\u0001\u0000\u0000\u0000WN\u0001"+
		"\u0000\u0000\u0000WQ\u0001\u0000\u0000\u0000WT\u0001\u0000\u0000\u0000"+
		"X\u0001\u0001\u0000\u0000\u0000Y[\u00030\u0018\u0000ZY\u0001\u0000\u0000"+
		"\u0000[^\u0001\u0000\u0000\u0000\\Z\u0001\u0000\u0000\u0000\\]\u0001\u0000"+
		"\u0000\u0000]\u0003\u0001\u0000\u0000\u0000^\\\u0001\u0000\u0000\u0000"+
		"_`\u0005\u0001\u0000\u0000`a\u0003\u0016\u000b\u0000ab\u0005(\u0000\u0000"+
		"bc\u0003\u001c\u000e\u0000cd\u0003$\u0012\u0000dk\u0005\u0002\u0000\u0000"+
		"ef\u0005\u0016\u0000\u0000fg\u0003 \u0010\u0000gh\u0003$\u0012\u0000h"+
		"j\u0001\u0000\u0000\u0000ie\u0001\u0000\u0000\u0000jm\u0001\u0000\u0000"+
		"\u0000ki\u0001\u0000\u0000\u0000kl\u0001\u0000\u0000\u0000ln\u0001\u0000"+
		"\u0000\u0000mk\u0001\u0000\u0000\u0000no\u0005)\u0000\u0000op\u0003&\u0013"+
		"\u0000p\u0005\u0001\u0000\u0000\u0000qr\u0005\u0003\u0000\u0000rs\u0003"+
		"\u0018\f\u0000st\u0005\u0004\u0000\u0000tu\u0003\u0016\u000b\u0000uv\u0005"+
		"(\u0000\u0000vw\u0003\"\u0011\u0000wx\u0005)\u0000\u0000xy\u0003(\u0014"+
		"\u0000y\u0007\u0001\u0000\u0000\u0000z{\u0005\u0005\u0000\u0000{|\u0003"+
		"\u0016\u000b\u0000|}\u0005(\u0000\u0000}\u0082\u0003 \u0010\u0000~\u007f"+
		"\u0005\u0016\u0000\u0000\u007f\u0081\u0003 \u0010\u0000\u0080~\u0001\u0000"+
		"\u0000\u0000\u0081\u0084\u0001\u0000\u0000\u0000\u0082\u0080\u0001\u0000"+
		"\u0000\u0000\u0082\u0083\u0001\u0000\u0000\u0000\u0083\u0085\u0001\u0000"+
		"\u0000\u0000\u0084\u0082\u0001\u0000\u0000\u0000\u0085\u0086\u0005)\u0000"+
		"\u0000\u0086\u0087\u0005\u0006\u0000\u0000\u0087\u0088\u0005(\u0000\u0000"+
		"\u0088\u008d\u0003\u001a\r\u0000\u0089\u008a\u0005\u0016\u0000\u0000\u008a"+
		"\u008c\u0003\u001a\r\u0000\u008b\u0089\u0001\u0000\u0000\u0000\u008c\u008f"+
		"\u0001\u0000\u0000\u0000\u008d\u008b\u0001\u0000\u0000\u0000\u008d\u008e"+
		"\u0001\u0000\u0000\u0000\u008e\u0090\u0001\u0000\u0000\u0000\u008f\u008d"+
		"\u0001\u0000\u0000\u0000\u0090\u0091\u0005)\u0000\u0000\u0091\u0092\u0003"+
		"*\u0015\u0000\u0092\t\u0001\u0000\u0000\u0000\u0093\u0094\u0005\u0007"+
		"\u0000\u0000\u0094\u0095\u0003\u0016\u000b\u0000\u0095\u0096\u0005\b\u0000"+
		"\u0000\u0096\u0097\u0003 \u0010\u0000\u0097\u0098\u0005\'\u0000\u0000"+
		"\u0098\u00a0\u0003\u001a\r\u0000\u0099\u009a\u0005\u0016\u0000\u0000\u009a"+
		"\u009b\u0003 \u0010\u0000\u009b\u009c\u0005\'\u0000\u0000\u009c\u009d"+
		"\u0003\u001a\r\u0000\u009d\u009f\u0001\u0000\u0000\u0000\u009e\u0099\u0001"+
		"\u0000\u0000\u0000\u009f\u00a2\u0001\u0000\u0000\u0000\u00a0\u009e\u0001"+
		"\u0000\u0000\u0000\u00a0\u00a1\u0001\u0000\u0000\u0000\u00a1\u00a3\u0001"+
		"\u0000\u0000\u0000\u00a2\u00a0\u0001\u0000\u0000\u0000\u00a3\u00a4\u0005"+
		"\t\u0000\u0000\u00a4\u00a5\u0003\u001c\u000e\u0000\u00a5\u00a6\u0005\'"+
		"\u0000\u0000\u00a6\u00a7\u0003\u001e\u000f\u0000\u00a7\u00a8\u0003,\u0016"+
		"\u0000\u00a8\u000b\u0001\u0000\u0000\u0000\u00a9\u00aa\u0005\n\u0000\u0000"+
		"\u00aa\u00ab\u0005\u000b\u0000\u0000\u00ab\u00ac\u0003\u0016\u000b\u0000"+
		"\u00ac\u00ad\u0005\t\u0000\u0000\u00ad\u00ae\u0003 \u0010\u0000\u00ae"+
		"\u00af\u0005\'\u0000\u0000\u00af\u00b7\u0003\u001a\r\u0000\u00b0\u00b1"+
		"\u0005\f\u0000\u0000\u00b1\u00b2\u0003 \u0010\u0000\u00b2\u00b3\u0005"+
		"\'\u0000\u0000\u00b3\u00b4\u0003\u001a\r\u0000\u00b4\u00b6\u0001\u0000"+
		"\u0000\u0000\u00b5\u00b0\u0001\u0000\u0000\u0000\u00b6\u00b9\u0001\u0000"+
		"\u0000\u0000\u00b7\u00b5\u0001\u0000\u0000\u0000\u00b7\u00b8\u0001\u0000"+
		"\u0000\u0000\u00b8\u00ba\u0001\u0000\u0000\u0000\u00b9\u00b7\u0001\u0000"+
		"\u0000\u0000\u00ba\u00bb\u0003.\u0017\u0000\u00bb\r\u0001\u0000\u0000"+
		"\u0000\u00bc\u00bd\u0005\r\u0000\u0000\u00bd\u00be\u0005!\u0000\u0000"+
		"\u00be\u00bf\u0005\u000b\u0000\u0000\u00bf\u00c0\u0003\u0016\u000b\u0000"+
		"\u00c0\u00c1\u0005\t\u0000\u0000\u00c1\u00c2\u0003 \u0010\u0000\u00c2"+
		"\u00c3\u0003\u0012\t\u0000\u00c3\u00cb\u0003\u001a\r\u0000\u00c4\u00c5"+
		"\u0003\u0014\n\u0000\u00c5\u00c6\u0003 \u0010\u0000\u00c6\u00c7\u0003"+
		"\u0012\t\u0000\u00c7\u00c8\u0003\u001a\r\u0000\u00c8\u00ca\u0001\u0000"+
		"\u0000\u0000\u00c9\u00c4\u0001\u0000\u0000\u0000\u00ca\u00cd\u0001\u0000"+
		"\u0000\u0000\u00cb\u00c9\u0001\u0000\u0000\u0000\u00cb\u00cc\u0001\u0000"+
		"\u0000\u0000\u00cc\u00ce\u0001\u0000\u0000\u0000\u00cd\u00cb\u0001\u0000"+
		"\u0000\u0000\u00ce\u00cf\u0003\u0010\b\u0000\u00cf\u000f\u0001\u0000\u0000"+
		"\u0000\u00d0\u00d1\u0005&\u0000\u0000\u00d1\u0011\u0001\u0000\u0000\u0000"+
		"\u00d2\u00d3\u0007\u0000\u0000\u0000\u00d3\u0013\u0001\u0000\u0000\u0000"+
		"\u00d4\u00d5\u0007\u0001\u0000\u0000\u00d5\u0015\u0001\u0000\u0000\u0000"+
		"\u00d6\u00d7\u00056\u0000\u0000\u00d7\u0017\u0001\u0000\u0000\u0000\u00d8"+
		"\u00d9\u00056\u0000\u0000\u00d9\u0019\u0001\u0000\u0000\u0000\u00da\u00db"+
		"\u0007\u0002\u0000\u0000\u00db\u001b\u0001\u0000\u0000\u0000\u00dc\u00dd"+
		"\u00056\u0000\u0000\u00dd\u001d\u0001\u0000\u0000\u0000\u00de\u00df\u0007"+
		"\u0002\u0000\u0000\u00df\u001f\u0001\u0000\u0000\u0000\u00e0\u00e1\u0005"+
		"6\u0000\u0000\u00e1!\u0001\u0000\u0000\u0000\u00e2\u00e3\u00056\u0000"+
		"\u0000\u00e3#\u0001\u0000\u0000\u0000\u00e4\u00e5\u0007\u0003\u0000\u0000"+
		"\u00e5%\u0001\u0000\u0000\u0000\u00e6\u00e7\u0005&\u0000\u0000\u00e7\'"+
		"\u0001\u0000\u0000\u0000\u00e8\u00e9\u0005&\u0000\u0000\u00e9)\u0001\u0000"+
		"\u0000\u0000\u00ea\u00eb\u0005&\u0000\u0000\u00eb+\u0001\u0000\u0000\u0000"+
		"\u00ec\u00ed\u0005&\u0000\u0000\u00ed-\u0001\u0000\u0000\u0000\u00ee\u00ef"+
		"\u0005&\u0000\u0000\u00ef/\u0001\u0000\u0000\u0000\u00f0\u00f7\u00032"+
		"\u0019\u0000\u00f1\u00f7\u00034\u001a\u0000\u00f2\u00f7\u0003:\u001d\u0000"+
		"\u00f3\u00f7\u0003<\u001e\u0000\u00f4\u00f5\u00059\u0000\u0000\u00f5\u00f7"+
		"\u0006\u0018\uffff\uffff\u0000\u00f6\u00f0\u0001\u0000\u0000\u0000\u00f6"+
		"\u00f1\u0001\u0000\u0000\u0000\u00f6\u00f2\u0001\u0000\u0000\u0000\u00f6"+
		"\u00f3\u0001\u0000\u0000\u0000\u00f6\u00f4\u0001\u0000\u0000\u0000\u00f7"+
		"1\u0001\u0000\u0000\u0000\u00f8\u00f9\u00053\u0000\u0000\u00f9\u00fa\u0005"+
		"\'\u0000\u0000\u00fa\u00fb\u0003>\u001f\u0000\u00fb\u00fc\u0005&\u0000"+
		"\u0000\u00fc3\u0001\u0000\u0000\u0000\u00fd\u00fe\u0005/\u0000\u0000\u00fe"+
		"\u0104\u00036\u001b\u0000\u00ff\u0100\u00050\u0000\u0000\u0100\u0101\u0005"+
		"/\u0000\u0000\u0101\u0103\u00036\u001b\u0000\u0102\u00ff\u0001\u0000\u0000"+
		"\u0000\u0103\u0106\u0001\u0000\u0000\u0000\u0104\u0102\u0001\u0000\u0000"+
		"\u0000\u0104\u0105\u0001\u0000\u0000\u0000\u0105\u0109\u0001\u0000\u0000"+
		"\u0000\u0106\u0104\u0001\u0000\u0000\u0000\u0107\u0108\u00050\u0000\u0000"+
		"\u0108\u010a\u00038\u001c\u0000\u0109\u0107\u0001\u0000\u0000\u0000\u0109"+
		"\u010a\u0001\u0000\u0000\u0000\u010a5\u0001\u0000\u0000\u0000\u010b\u010c"+
		"\u0003>\u001f\u0000\u010c\u010d\u00038\u001c\u0000\u010d7\u0001\u0000"+
		"\u0000\u0000\u010e\u010f\u0005*\u0000\u0000\u010f\u0110\u0003\u0002\u0001"+
		"\u0000\u0110\u0111\u0005+\u0000\u0000\u0111\u0114\u0001\u0000\u0000\u0000"+
		"\u0112\u0114\u00030\u0018\u0000\u0113\u010e\u0001\u0000\u0000\u0000\u0113"+
		"\u0112\u0001\u0000\u0000\u0000\u01149\u0001\u0000\u0000\u0000\u0115\u0116"+
		"\u00051\u0000\u0000\u0116\u0117\u0003>\u001f\u0000\u0117\u0118\u00038"+
		"\u001c\u0000\u0118;\u0001\u0000\u0000\u0000\u0119\u011a\u00052\u0000\u0000"+
		"\u011a\u011b\u0003>\u001f\u0000\u011b\u011c\u0005&\u0000\u0000\u011c="+
		"\u0001\u0000\u0000\u0000\u011d\u011e\u0006\u001f\uffff\uffff\u0000\u011e"+
		"\u011f\u0005 \u0000\u0000\u011f\u0124\u0003>\u001f\t\u0120\u0121\u0005"+
		"%\u0000\u0000\u0121\u0124\u0003>\u001f\b\u0122\u0124\u0003@ \u0000\u0123"+
		"\u011d\u0001\u0000\u0000\u0000\u0123\u0120\u0001\u0000\u0000\u0000\u0123"+
		"\u0122\u0001\u0000\u0000\u0000\u0124\u013c\u0001\u0000\u0000\u0000\u0125"+
		"\u0126\n\n\u0000\u0000\u0126\u0127\u0005$\u0000\u0000\u0127\u013b\u0003"+
		">\u001f\u000b\u0128\u0129\n\u0007\u0000\u0000\u0129\u012a\u0007\u0004"+
		"\u0000\u0000\u012a\u013b\u0003>\u001f\b\u012b\u012c\n\u0006\u0000\u0000"+
		"\u012c\u012d\u0007\u0005\u0000\u0000\u012d\u013b\u0003>\u001f\u0007\u012e"+
		"\u012f\n\u0005\u0000\u0000\u012f\u0130\u0007\u0006\u0000\u0000\u0130\u013b"+
		"\u0003>\u001f\u0006\u0131\u0132\n\u0004\u0000\u0000\u0132\u0133\u0007"+
		"\u0007\u0000\u0000\u0133\u013b\u0003>\u001f\u0005\u0134\u0135\n\u0003"+
		"\u0000\u0000\u0135\u0136\u0005\u0018\u0000\u0000\u0136\u013b\u0003>\u001f"+
		"\u0004\u0137\u0138\n\u0002\u0000\u0000\u0138\u0139\u0005\u0017\u0000\u0000"+
		"\u0139\u013b\u0003>\u001f\u0003\u013a\u0125\u0001\u0000\u0000\u0000\u013a"+
		"\u0128\u0001\u0000\u0000\u0000\u013a\u012b\u0001\u0000\u0000\u0000\u013a"+
		"\u012e\u0001\u0000\u0000\u0000\u013a\u0131\u0001\u0000\u0000\u0000\u013a"+
		"\u0134\u0001\u0000\u0000\u0000\u013a\u0137\u0001\u0000\u0000\u0000\u013b"+
		"\u013e\u0001\u0000\u0000\u0000\u013c\u013a\u0001\u0000\u0000\u0000\u013c"+
		"\u013d\u0001\u0000\u0000\u0000\u013d?\u0001\u0000\u0000\u0000\u013e\u013c"+
		"\u0001\u0000\u0000\u0000\u013f\u0140\u0005(\u0000\u0000\u0140\u0141\u0003"+
		">\u001f\u0000\u0141\u0142\u0005)\u0000\u0000\u0142\u0149\u0001\u0000\u0000"+
		"\u0000\u0143\u0149\u0007\b\u0000\u0000\u0144\u0149\u0007\t\u0000\u0000"+
		"\u0145\u0149\u00053\u0000\u0000\u0146\u0149\u00056\u0000\u0000\u0147\u0149"+
		"\u0005.\u0000\u0000\u0148\u013f\u0001\u0000\u0000\u0000\u0148\u0143\u0001"+
		"\u0000\u0000\u0000\u0148\u0144\u0001\u0000\u0000\u0000\u0148\u0145\u0001"+
		"\u0000\u0000\u0000\u0148\u0146\u0001\u0000\u0000\u0000\u0148\u0147\u0001"+
		"\u0000\u0000\u0000\u0149A\u0001\u0000\u0000\u0000\u0010W\\k\u0082\u008d"+
		"\u00a0\u00b7\u00cb\u00f6\u0104\u0109\u0113\u0123\u013a\u013c\u0148";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}