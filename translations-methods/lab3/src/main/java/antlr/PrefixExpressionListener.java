package antlr;// Generated from PrefixExpression.g4 by ANTLR 4.9
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link PrefixExpressionParser}.
 */
public interface PrefixExpressionListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(PrefixExpressionParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(PrefixExpressionParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(PrefixExpressionParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(PrefixExpressionParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#multiExpr}.
	 * @param ctx the parse tree
	 */
	void enterMultiExpr(PrefixExpressionParser.MultiExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#multiExpr}.
	 * @param ctx the parse tree
	 */
	void exitMultiExpr(PrefixExpressionParser.MultiExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#singleExpr}.
	 * @param ctx the parse tree
	 */
	void enterSingleExpr(PrefixExpressionParser.SingleExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#singleExpr}.
	 * @param ctx the parse tree
	 */
	void exitSingleExpr(PrefixExpressionParser.SingleExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#ifState}.
	 * @param ctx the parse tree
	 */
	void enterIfState(PrefixExpressionParser.IfStateContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#ifState}.
	 * @param ctx the parse tree
	 */
	void exitIfState(PrefixExpressionParser.IfStateContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#elseState}.
	 * @param ctx the parse tree
	 */
	void enterElseState(PrefixExpressionParser.ElseStateContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#elseState}.
	 * @param ctx the parse tree
	 */
	void exitElseState(PrefixExpressionParser.ElseStateContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#print}.
	 * @param ctx the parse tree
	 */
	void enterPrint(PrefixExpressionParser.PrintContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#print}.
	 * @param ctx the parse tree
	 */
	void exitPrint(PrefixExpressionParser.PrintContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#define}.
	 * @param ctx the parse tree
	 */
	void enterDefine(PrefixExpressionParser.DefineContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#define}.
	 * @param ctx the parse tree
	 */
	void exitDefine(PrefixExpressionParser.DefineContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#rightValue}.
	 * @param ctx the parse tree
	 */
	void enterRightValue(PrefixExpressionParser.RightValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#rightValue}.
	 * @param ctx the parse tree
	 */
	void exitRightValue(PrefixExpressionParser.RightValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#logic}.
	 * @param ctx the parse tree
	 */
	void enterLogic(PrefixExpressionParser.LogicContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#logic}.
	 * @param ctx the parse tree
	 */
	void exitLogic(PrefixExpressionParser.LogicContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#compareOperation}.
	 * @param ctx the parse tree
	 */
	void enterCompareOperation(PrefixExpressionParser.CompareOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#compareOperation}.
	 * @param ctx the parse tree
	 */
	void exitCompareOperation(PrefixExpressionParser.CompareOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#logicOperation}.
	 * @param ctx the parse tree
	 */
	void enterLogicOperation(PrefixExpressionParser.LogicOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#logicOperation}.
	 * @param ctx the parse tree
	 */
	void exitLogicOperation(PrefixExpressionParser.LogicOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#arithmetic}.
	 * @param ctx the parse tree
	 */
	void enterArithmetic(PrefixExpressionParser.ArithmeticContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#arithmetic}.
	 * @param ctx the parse tree
	 */
	void exitArithmetic(PrefixExpressionParser.ArithmeticContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#input}.
	 * @param ctx the parse tree
	 */
	void enterInput(PrefixExpressionParser.InputContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#input}.
	 * @param ctx the parse tree
	 */
	void exitInput(PrefixExpressionParser.InputContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#arithmeticOperation}.
	 * @param ctx the parse tree
	 */
	void enterArithmeticOperation(PrefixExpressionParser.ArithmeticOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#arithmeticOperation}.
	 * @param ctx the parse tree
	 */
	void exitArithmeticOperation(PrefixExpressionParser.ArithmeticOperationContext ctx);
}