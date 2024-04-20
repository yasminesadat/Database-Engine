// Generated from C:/Users/Zrafa/IdeaProjects/Database-Engine/dbms/src/main/java/antlr/SQL.g4 by ANTLR 4.13.1
package antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SQLParser}.
 */
public interface SQLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SQLParser#parse}.
	 * @param ctx the parse tree
	 */
	void enterParse(SQLParser.ParseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#parse}.
	 * @param ctx the parse tree
	 */
	void exitParse(SQLParser.ParseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(SQLParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(SQLParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#createTable}.
	 * @param ctx the parse tree
	 */
	void enterCreateTable(SQLParser.CreateTableContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#createTable}.
	 * @param ctx the parse tree
	 */
	void exitCreateTable(SQLParser.CreateTableContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#createIndex}.
	 * @param ctx the parse tree
	 */
	void enterCreateIndex(SQLParser.CreateIndexContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#createIndex}.
	 * @param ctx the parse tree
	 */
	void exitCreateIndex(SQLParser.CreateIndexContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#insertintoTable}.
	 * @param ctx the parse tree
	 */
	void enterInsertintoTable(SQLParser.InsertintoTableContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#insertintoTable}.
	 * @param ctx the parse tree
	 */
	void exitInsertintoTable(SQLParser.InsertintoTableContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#updateTable}.
	 * @param ctx the parse tree
	 */
	void enterUpdateTable(SQLParser.UpdateTableContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#updateTable}.
	 * @param ctx the parse tree
	 */
	void exitUpdateTable(SQLParser.UpdateTableContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#deleteFromTable}.
	 * @param ctx the parse tree
	 */
	void enterDeleteFromTable(SQLParser.DeleteFromTableContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#deleteFromTable}.
	 * @param ctx the parse tree
	 */
	void exitDeleteFromTable(SQLParser.DeleteFromTableContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#selectFromTable}.
	 * @param ctx the parse tree
	 */
	void enterSelectFromTable(SQLParser.SelectFromTableContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#selectFromTable}.
	 * @param ctx the parse tree
	 */
	void exitSelectFromTable(SQLParser.SelectFromTableContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#closerSelect}.
	 * @param ctx the parse tree
	 */
	void enterCloserSelect(SQLParser.CloserSelectContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#closerSelect}.
	 * @param ctx the parse tree
	 */
	void exitCloserSelect(SQLParser.CloserSelectContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#arithmeticOperator}.
	 * @param ctx the parse tree
	 */
	void enterArithmeticOperator(SQLParser.ArithmeticOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#arithmeticOperator}.
	 * @param ctx the parse tree
	 */
	void exitArithmeticOperator(SQLParser.ArithmeticOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#logicalOperator}.
	 * @param ctx the parse tree
	 */
	void enterLogicalOperator(SQLParser.LogicalOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#logicalOperator}.
	 * @param ctx the parse tree
	 */
	void exitLogicalOperator(SQLParser.LogicalOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#tableName}.
	 * @param ctx the parse tree
	 */
	void enterTableName(SQLParser.TableNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#tableName}.
	 * @param ctx the parse tree
	 */
	void exitTableName(SQLParser.TableNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#indexName}.
	 * @param ctx the parse tree
	 */
	void enterIndexName(SQLParser.IndexNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#indexName}.
	 * @param ctx the parse tree
	 */
	void exitIndexName(SQLParser.IndexNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#dataValue}.
	 * @param ctx the parse tree
	 */
	void enterDataValue(SQLParser.DataValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#dataValue}.
	 * @param ctx the parse tree
	 */
	void exitDataValue(SQLParser.DataValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#clusteringKeyColumn}.
	 * @param ctx the parse tree
	 */
	void enterClusteringKeyColumn(SQLParser.ClusteringKeyColumnContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#clusteringKeyColumn}.
	 * @param ctx the parse tree
	 */
	void exitClusteringKeyColumn(SQLParser.ClusteringKeyColumnContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#clusteringKeyValue}.
	 * @param ctx the parse tree
	 */
	void enterClusteringKeyValue(SQLParser.ClusteringKeyValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#clusteringKeyValue}.
	 * @param ctx the parse tree
	 */
	void exitClusteringKeyValue(SQLParser.ClusteringKeyValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#columnName}.
	 * @param ctx the parse tree
	 */
	void enterColumnName(SQLParser.ColumnNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#columnName}.
	 * @param ctx the parse tree
	 */
	void exitColumnName(SQLParser.ColumnNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#indexedColumnName}.
	 * @param ctx the parse tree
	 */
	void enterIndexedColumnName(SQLParser.IndexedColumnNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#indexedColumnName}.
	 * @param ctx the parse tree
	 */
	void exitIndexedColumnName(SQLParser.IndexedColumnNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#dataType}.
	 * @param ctx the parse tree
	 */
	void enterDataType(SQLParser.DataTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#dataType}.
	 * @param ctx the parse tree
	 */
	void exitDataType(SQLParser.DataTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#closerCreateTable}.
	 * @param ctx the parse tree
	 */
	void enterCloserCreateTable(SQLParser.CloserCreateTableContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#closerCreateTable}.
	 * @param ctx the parse tree
	 */
	void exitCloserCreateTable(SQLParser.CloserCreateTableContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#closerCreateIndex}.
	 * @param ctx the parse tree
	 */
	void enterCloserCreateIndex(SQLParser.CloserCreateIndexContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#closerCreateIndex}.
	 * @param ctx the parse tree
	 */
	void exitCloserCreateIndex(SQLParser.CloserCreateIndexContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#closerInsertIntoTable}.
	 * @param ctx the parse tree
	 */
	void enterCloserInsertIntoTable(SQLParser.CloserInsertIntoTableContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#closerInsertIntoTable}.
	 * @param ctx the parse tree
	 */
	void exitCloserInsertIntoTable(SQLParser.CloserInsertIntoTableContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#closerUpdate}.
	 * @param ctx the parse tree
	 */
	void enterCloserUpdate(SQLParser.CloserUpdateContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#closerUpdate}.
	 * @param ctx the parse tree
	 */
	void exitCloserUpdate(SQLParser.CloserUpdateContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#closerDelete}.
	 * @param ctx the parse tree
	 */
	void enterCloserDelete(SQLParser.CloserDeleteContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#closerDelete}.
	 * @param ctx the parse tree
	 */
	void exitCloserDelete(SQLParser.CloserDeleteContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStat(SQLParser.StatContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStat(SQLParser.StatContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(SQLParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(SQLParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#if_stat}.
	 * @param ctx the parse tree
	 */
	void enterIf_stat(SQLParser.If_statContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#if_stat}.
	 * @param ctx the parse tree
	 */
	void exitIf_stat(SQLParser.If_statContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#condition_block}.
	 * @param ctx the parse tree
	 */
	void enterCondition_block(SQLParser.Condition_blockContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#condition_block}.
	 * @param ctx the parse tree
	 */
	void exitCondition_block(SQLParser.Condition_blockContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#stat_block}.
	 * @param ctx the parse tree
	 */
	void enterStat_block(SQLParser.Stat_blockContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#stat_block}.
	 * @param ctx the parse tree
	 */
	void exitStat_block(SQLParser.Stat_blockContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#while_stat}.
	 * @param ctx the parse tree
	 */
	void enterWhile_stat(SQLParser.While_statContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#while_stat}.
	 * @param ctx the parse tree
	 */
	void exitWhile_stat(SQLParser.While_statContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#log}.
	 * @param ctx the parse tree
	 */
	void enterLog(SQLParser.LogContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#log}.
	 * @param ctx the parse tree
	 */
	void exitLog(SQLParser.LogContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link SQLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNotExpr(SQLParser.NotExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link SQLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNotExpr(SQLParser.NotExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryMinusExpr}
	 * labeled alternative in {@link SQLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryMinusExpr(SQLParser.UnaryMinusExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryMinusExpr}
	 * labeled alternative in {@link SQLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryMinusExpr(SQLParser.UnaryMinusExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multiplicationExpr}
	 * labeled alternative in {@link SQLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicationExpr(SQLParser.MultiplicationExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multiplicationExpr}
	 * labeled alternative in {@link SQLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicationExpr(SQLParser.MultiplicationExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code atomExpr}
	 * labeled alternative in {@link SQLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAtomExpr(SQLParser.AtomExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code atomExpr}
	 * labeled alternative in {@link SQLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAtomExpr(SQLParser.AtomExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orExpr}
	 * labeled alternative in {@link SQLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterOrExpr(SQLParser.OrExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orExpr}
	 * labeled alternative in {@link SQLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitOrExpr(SQLParser.OrExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code additiveExpr}
	 * labeled alternative in {@link SQLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpr(SQLParser.AdditiveExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code additiveExpr}
	 * labeled alternative in {@link SQLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpr(SQLParser.AdditiveExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code powExpr}
	 * labeled alternative in {@link SQLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPowExpr(SQLParser.PowExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code powExpr}
	 * labeled alternative in {@link SQLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPowExpr(SQLParser.PowExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relationalExpr}
	 * labeled alternative in {@link SQLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpr(SQLParser.RelationalExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relationalExpr}
	 * labeled alternative in {@link SQLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpr(SQLParser.RelationalExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code equalityExpr}
	 * labeled alternative in {@link SQLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpr(SQLParser.EqualityExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code equalityExpr}
	 * labeled alternative in {@link SQLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpr(SQLParser.EqualityExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link SQLParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(SQLParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link SQLParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(SQLParser.AndExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parExpr}
	 * labeled alternative in {@link SQLParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterParExpr(SQLParser.ParExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parExpr}
	 * labeled alternative in {@link SQLParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitParExpr(SQLParser.ParExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code numberAtom}
	 * labeled alternative in {@link SQLParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterNumberAtom(SQLParser.NumberAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code numberAtom}
	 * labeled alternative in {@link SQLParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitNumberAtom(SQLParser.NumberAtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code booleanAtom}
	 * labeled alternative in {@link SQLParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterBooleanAtom(SQLParser.BooleanAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code booleanAtom}
	 * labeled alternative in {@link SQLParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitBooleanAtom(SQLParser.BooleanAtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idAtom}
	 * labeled alternative in {@link SQLParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterIdAtom(SQLParser.IdAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idAtom}
	 * labeled alternative in {@link SQLParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitIdAtom(SQLParser.IdAtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringAtom}
	 * labeled alternative in {@link SQLParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterStringAtom(SQLParser.StringAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringAtom}
	 * labeled alternative in {@link SQLParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitStringAtom(SQLParser.StringAtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nilAtom}
	 * labeled alternative in {@link SQLParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterNilAtom(SQLParser.NilAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nilAtom}
	 * labeled alternative in {@link SQLParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitNilAtom(SQLParser.NilAtomContext ctx);
}