// Generated from Hydra.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link HydraParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface HydraVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link HydraParser#programa}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrograma(HydraParser.ProgramaContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#importList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportList(HydraParser.ImportListContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#importStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportStatement(HydraParser.ImportStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#cuerpo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCuerpo(HydraParser.CuerpoContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#elementocuerpo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementocuerpo(HydraParser.ElementocuerpoContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#interfaceDefinicion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceDefinicion(HydraParser.InterfaceDefinicionContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#interfaceMiembro}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceMiembro(HydraParser.InterfaceMiembroContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#definicionClase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefinicionClase(HydraParser.DefinicionClaseContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#miembro}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMiembro(HydraParser.MiembroContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#constructor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructor(HydraParser.ConstructorContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#accessModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAccessModifier(HydraParser.AccessModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#funcion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncion(HydraParser.FuncionContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#paramList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamList(HydraParser.ParamListContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#parametro}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParametro(HydraParser.ParametroContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#bloque}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBloque(HydraParser.BloqueContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#cuerpoGeneral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCuerpoGeneral(HydraParser.CuerpoGeneralContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#declaracion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaracion(HydraParser.DeclaracionContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#declaratorList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaratorList(HydraParser.DeclaratorListContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#declarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarator(HydraParser.DeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#tipo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTipo(HydraParser.TipoContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#arrayDimensions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayDimensions(HydraParser.ArrayDimensionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#sentencia}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSentencia(HydraParser.SentenciaContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#tryStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTryStatement(HydraParser.TryStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#catchBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCatchBlock(HydraParser.CatchBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#finallyBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFinallyBlock(HydraParser.FinallyBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#ioStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIoStatement(HydraParser.IoStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(HydraParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(HydraParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#doWhileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoWhileStatement(HydraParser.DoWhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#switchStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchStatement(HydraParser.SwitchStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#caseBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCaseBlock(HydraParser.CaseBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#defaultBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefaultBlock(HydraParser.DefaultBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(HydraParser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#forInitDeclaracion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInitDeclaracion(HydraParser.ForInitDeclaracionContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#forInit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInit(HydraParser.ForInitContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#forUpdate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForUpdate(HydraParser.ForUpdateContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(HydraParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#assignmentExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentExpr(HydraParser.AssignmentExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#logicalOrExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalOrExpr(HydraParser.LogicalOrExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#logicalAndExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalAndExpr(HydraParser.LogicalAndExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#equalityExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpr(HydraParser.EqualityExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#relationalExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalExpr(HydraParser.RelationalExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#additiveExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpr(HydraParser.AdditiveExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpr(HydraParser.MultiplicativeExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#exponentialExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExponentialExpr(HydraParser.ExponentialExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#unaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpr(HydraParser.UnaryExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#postfixExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixExpr(HydraParser.PostfixExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#primaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryExpr(HydraParser.PrimaryExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#arrayInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayInitializer(HydraParser.ArrayInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(HydraParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link HydraParser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(HydraParser.ExpressionListContext ctx);
}