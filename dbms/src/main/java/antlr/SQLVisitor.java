// Generated from C:/Users/Zrafa/IdeaProjects/Database-Engine/dbms/src/main/java/antlr/SQL.g4 by ANTLR 4.13.1
package antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SQLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SQLVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SQLParser#parse}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParse(SQLParser.ParseContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(SQLParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#createTable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateTable(SQLParser.CreateTableContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#createIndex}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateIndex(SQLParser.CreateIndexContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#insertintoTable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsertintoTable(SQLParser.InsertintoTableContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#updateTable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUpdateTable(SQLParser.UpdateTableContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#deleteFromTable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeleteFromTable(SQLParser.DeleteFromTableContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#selectFromTable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectFromTable(SQLParser.SelectFromTableContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#closerSelect}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCloserSelect(SQLParser.CloserSelectContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#arithmeticOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmeticOperator(SQLParser.ArithmeticOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#logicalOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalOperator(SQLParser.LogicalOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#tableName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableName(SQLParser.TableNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#indexName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexName(SQLParser.IndexNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#dataValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDataValue(SQLParser.DataValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#clusteringKeyColumn}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClusteringKeyColumn(SQLParser.ClusteringKeyColumnContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#clusteringKeyValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClusteringKeyValue(SQLParser.ClusteringKeyValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#columnName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnName(SQLParser.ColumnNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#indexedColumnName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexedColumnName(SQLParser.IndexedColumnNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#dataType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDataType(SQLParser.DataTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#closerCreateTable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCloserCreateTable(SQLParser.CloserCreateTableContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#closerCreateIndex}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCloserCreateIndex(SQLParser.CloserCreateIndexContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#closerInsertIntoTable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCloserInsertIntoTable(SQLParser.CloserInsertIntoTableContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#closerUpdate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCloserUpdate(SQLParser.CloserUpdateContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#closerDelete}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCloserDelete(SQLParser.CloserDeleteContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat(SQLParser.StatContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(SQLParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#if_stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_stat(SQLParser.If_statContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#condition_block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition_block(SQLParser.Condition_blockContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#stat_block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat_block(SQLParser.Stat_blockContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#while_stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile_stat(SQLParser.While_statContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#log}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLog(SQLParser.LogContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link SQLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExpr(SQLParser.NotExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryMinusExpr}
	 * labeled alternative in {@link SQLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryMinusExpr(SQLParser.UnaryMinusExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code multiplicationExpr}
	 * labeled alternative in {@link SQLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicationExpr(SQLParser.MultiplicationExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code atomExpr}
	 * labeled alternative in {@link SQLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomExpr(SQLParser.AtomExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orExpr}
	 * labeled alternative in {@link SQLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpr(SQLParser.OrExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code additiveExpr}
	 * labeled alternative in {@link SQLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpr(SQLParser.AdditiveExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code powExpr}
	 * labeled alternative in {@link SQLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPowExpr(SQLParser.PowExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code relationalExpr}
	 * labeled alternative in {@link SQLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalExpr(SQLParser.RelationalExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code equalityExpr}
	 * labeled alternative in {@link SQLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpr(SQLParser.EqualityExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link SQLParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpr(SQLParser.AndExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parExpr}
	 * labeled alternative in {@link SQLParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParExpr(SQLParser.ParExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code numberAtom}
	 * labeled alternative in {@link SQLParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberAtom(SQLParser.NumberAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code booleanAtom}
	 * labeled alternative in {@link SQLParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanAtom(SQLParser.BooleanAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code idAtom}
	 * labeled alternative in {@link SQLParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdAtom(SQLParser.IdAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringAtom}
	 * labeled alternative in {@link SQLParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringAtom(SQLParser.StringAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nilAtom}
	 * labeled alternative in {@link SQLParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNilAtom(SQLParser.NilAtomContext ctx);
}