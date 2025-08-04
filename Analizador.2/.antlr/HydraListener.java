// Generated from /home/josed/Descargas/Proyecto/Analizador.2/Hydra.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link HydraParser}.
 */
public interface HydraListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link HydraParser#programa}.
	 * @param ctx the parse tree
	 */
	void enterPrograma(HydraParser.ProgramaContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#programa}.
	 * @param ctx the parse tree
	 */
	void exitPrograma(HydraParser.ProgramaContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#importList}.
	 * @param ctx the parse tree
	 */
	void enterImportList(HydraParser.ImportListContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#importList}.
	 * @param ctx the parse tree
	 */
	void exitImportList(HydraParser.ImportListContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#importStatement}.
	 * @param ctx the parse tree
	 */
	void enterImportStatement(HydraParser.ImportStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#importStatement}.
	 * @param ctx the parse tree
	 */
	void exitImportStatement(HydraParser.ImportStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#cuerpo}.
	 * @param ctx the parse tree
	 */
	void enterCuerpo(HydraParser.CuerpoContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#cuerpo}.
	 * @param ctx the parse tree
	 */
	void exitCuerpo(HydraParser.CuerpoContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#elementocuerpo}.
	 * @param ctx the parse tree
	 */
	void enterElementocuerpo(HydraParser.ElementocuerpoContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#elementocuerpo}.
	 * @param ctx the parse tree
	 */
	void exitElementocuerpo(HydraParser.ElementocuerpoContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#interfaceDefinicion}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceDefinicion(HydraParser.InterfaceDefinicionContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#interfaceDefinicion}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceDefinicion(HydraParser.InterfaceDefinicionContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#interfaceMiembro}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceMiembro(HydraParser.InterfaceMiembroContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#interfaceMiembro}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceMiembro(HydraParser.InterfaceMiembroContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#definicionClase}.
	 * @param ctx the parse tree
	 */
	void enterDefinicionClase(HydraParser.DefinicionClaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#definicionClase}.
	 * @param ctx the parse tree
	 */
	void exitDefinicionClase(HydraParser.DefinicionClaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#miembro}.
	 * @param ctx the parse tree
	 */
	void enterMiembro(HydraParser.MiembroContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#miembro}.
	 * @param ctx the parse tree
	 */
	void exitMiembro(HydraParser.MiembroContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#constructor}.
	 * @param ctx the parse tree
	 */
	void enterConstructor(HydraParser.ConstructorContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#constructor}.
	 * @param ctx the parse tree
	 */
	void exitConstructor(HydraParser.ConstructorContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#accessModifier}.
	 * @param ctx the parse tree
	 */
	void enterAccessModifier(HydraParser.AccessModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#accessModifier}.
	 * @param ctx the parse tree
	 */
	void exitAccessModifier(HydraParser.AccessModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#funcion}.
	 * @param ctx the parse tree
	 */
	void enterFuncion(HydraParser.FuncionContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#funcion}.
	 * @param ctx the parse tree
	 */
	void exitFuncion(HydraParser.FuncionContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#paramList}.
	 * @param ctx the parse tree
	 */
	void enterParamList(HydraParser.ParamListContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#paramList}.
	 * @param ctx the parse tree
	 */
	void exitParamList(HydraParser.ParamListContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#parametro}.
	 * @param ctx the parse tree
	 */
	void enterParametro(HydraParser.ParametroContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#parametro}.
	 * @param ctx the parse tree
	 */
	void exitParametro(HydraParser.ParametroContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#bloque}.
	 * @param ctx the parse tree
	 */
	void enterBloque(HydraParser.BloqueContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#bloque}.
	 * @param ctx the parse tree
	 */
	void exitBloque(HydraParser.BloqueContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#cuerpoGeneral}.
	 * @param ctx the parse tree
	 */
	void enterCuerpoGeneral(HydraParser.CuerpoGeneralContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#cuerpoGeneral}.
	 * @param ctx the parse tree
	 */
	void exitCuerpoGeneral(HydraParser.CuerpoGeneralContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#declaracion}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracion(HydraParser.DeclaracionContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#declaracion}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracion(HydraParser.DeclaracionContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#declaratorList}.
	 * @param ctx the parse tree
	 */
	void enterDeclaratorList(HydraParser.DeclaratorListContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#declaratorList}.
	 * @param ctx the parse tree
	 */
	void exitDeclaratorList(HydraParser.DeclaratorListContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#declarator}.
	 * @param ctx the parse tree
	 */
	void enterDeclarator(HydraParser.DeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#declarator}.
	 * @param ctx the parse tree
	 */
	void exitDeclarator(HydraParser.DeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#tipo}.
	 * @param ctx the parse tree
	 */
	void enterTipo(HydraParser.TipoContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#tipo}.
	 * @param ctx the parse tree
	 */
	void exitTipo(HydraParser.TipoContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#arrayDimensions}.
	 * @param ctx the parse tree
	 */
	void enterArrayDimensions(HydraParser.ArrayDimensionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#arrayDimensions}.
	 * @param ctx the parse tree
	 */
	void exitArrayDimensions(HydraParser.ArrayDimensionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#sentencia}.
	 * @param ctx the parse tree
	 */
	void enterSentencia(HydraParser.SentenciaContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#sentencia}.
	 * @param ctx the parse tree
	 */
	void exitSentencia(HydraParser.SentenciaContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#tryStatement}.
	 * @param ctx the parse tree
	 */
	void enterTryStatement(HydraParser.TryStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#tryStatement}.
	 * @param ctx the parse tree
	 */
	void exitTryStatement(HydraParser.TryStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#catchBlock}.
	 * @param ctx the parse tree
	 */
	void enterCatchBlock(HydraParser.CatchBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#catchBlock}.
	 * @param ctx the parse tree
	 */
	void exitCatchBlock(HydraParser.CatchBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#finallyBlock}.
	 * @param ctx the parse tree
	 */
	void enterFinallyBlock(HydraParser.FinallyBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#finallyBlock}.
	 * @param ctx the parse tree
	 */
	void exitFinallyBlock(HydraParser.FinallyBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#ioStatement}.
	 * @param ctx the parse tree
	 */
	void enterIoStatement(HydraParser.IoStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#ioStatement}.
	 * @param ctx the parse tree
	 */
	void exitIoStatement(HydraParser.IoStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(HydraParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(HydraParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(HydraParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(HydraParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#doWhileStatement}.
	 * @param ctx the parse tree
	 */
	void enterDoWhileStatement(HydraParser.DoWhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#doWhileStatement}.
	 * @param ctx the parse tree
	 */
	void exitDoWhileStatement(HydraParser.DoWhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#switchStatement}.
	 * @param ctx the parse tree
	 */
	void enterSwitchStatement(HydraParser.SwitchStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#switchStatement}.
	 * @param ctx the parse tree
	 */
	void exitSwitchStatement(HydraParser.SwitchStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#caseBlock}.
	 * @param ctx the parse tree
	 */
	void enterCaseBlock(HydraParser.CaseBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#caseBlock}.
	 * @param ctx the parse tree
	 */
	void exitCaseBlock(HydraParser.CaseBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#defaultBlock}.
	 * @param ctx the parse tree
	 */
	void enterDefaultBlock(HydraParser.DefaultBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#defaultBlock}.
	 * @param ctx the parse tree
	 */
	void exitDefaultBlock(HydraParser.DefaultBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(HydraParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(HydraParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#forInitDeclaracion}.
	 * @param ctx the parse tree
	 */
	void enterForInitDeclaracion(HydraParser.ForInitDeclaracionContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#forInitDeclaracion}.
	 * @param ctx the parse tree
	 */
	void exitForInitDeclaracion(HydraParser.ForInitDeclaracionContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#forInit}.
	 * @param ctx the parse tree
	 */
	void enterForInit(HydraParser.ForInitContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#forInit}.
	 * @param ctx the parse tree
	 */
	void exitForInit(HydraParser.ForInitContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#forUpdate}.
	 * @param ctx the parse tree
	 */
	void enterForUpdate(HydraParser.ForUpdateContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#forUpdate}.
	 * @param ctx the parse tree
	 */
	void exitForUpdate(HydraParser.ForUpdateContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(HydraParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(HydraParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#assignmentExpr}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentExpr(HydraParser.AssignmentExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#assignmentExpr}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentExpr(HydraParser.AssignmentExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#logicalOrExpr}.
	 * @param ctx the parse tree
	 */
	void enterLogicalOrExpr(HydraParser.LogicalOrExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#logicalOrExpr}.
	 * @param ctx the parse tree
	 */
	void exitLogicalOrExpr(HydraParser.LogicalOrExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#logicalAndExpr}.
	 * @param ctx the parse tree
	 */
	void enterLogicalAndExpr(HydraParser.LogicalAndExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#logicalAndExpr}.
	 * @param ctx the parse tree
	 */
	void exitLogicalAndExpr(HydraParser.LogicalAndExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#equalityExpr}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpr(HydraParser.EqualityExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#equalityExpr}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpr(HydraParser.EqualityExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#relationalExpr}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpr(HydraParser.RelationalExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#relationalExpr}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpr(HydraParser.RelationalExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#additiveExpr}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpr(HydraParser.AdditiveExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#additiveExpr}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpr(HydraParser.AdditiveExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpr(HydraParser.MultiplicativeExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpr(HydraParser.MultiplicativeExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#exponentialExpr}.
	 * @param ctx the parse tree
	 */
	void enterExponentialExpr(HydraParser.ExponentialExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#exponentialExpr}.
	 * @param ctx the parse tree
	 */
	void exitExponentialExpr(HydraParser.ExponentialExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpr(HydraParser.UnaryExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpr(HydraParser.UnaryExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#postfixExpr}.
	 * @param ctx the parse tree
	 */
	void enterPostfixExpr(HydraParser.PostfixExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#postfixExpr}.
	 * @param ctx the parse tree
	 */
	void exitPostfixExpr(HydraParser.PostfixExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExpr(HydraParser.PrimaryExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExpr(HydraParser.PrimaryExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#arrayInitializer}.
	 * @param ctx the parse tree
	 */
	void enterArrayInitializer(HydraParser.ArrayInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#arrayInitializer}.
	 * @param ctx the parse tree
	 */
	void exitArrayInitializer(HydraParser.ArrayInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(HydraParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(HydraParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link HydraParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(HydraParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link HydraParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(HydraParser.ExpressionListContext ctx);
}