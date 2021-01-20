// Generated from ..\..\src\main\java\antlr\Generator.g4 by ANTLR 4.9

package antlr;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GeneratorParser}.
 */
public interface GeneratorListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GeneratorParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(GeneratorParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeneratorParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(GeneratorParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeneratorParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(GeneratorParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeneratorParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(GeneratorParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeneratorParser#header}.
	 * @param ctx the parse tree
	 */
	void enterHeader(GeneratorParser.HeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeneratorParser#header}.
	 * @param ctx the parse tree
	 */
	void exitHeader(GeneratorParser.HeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeneratorParser#imports}.
	 * @param ctx the parse tree
	 */
	void enterImports(GeneratorParser.ImportsContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeneratorParser#imports}.
	 * @param ctx the parse tree
	 */
	void exitImports(GeneratorParser.ImportsContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeneratorParser#import_line}.
	 * @param ctx the parse tree
	 */
	void enterImport_line(GeneratorParser.Import_lineContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeneratorParser#import_line}.
	 * @param ctx the parse tree
	 */
	void exitImport_line(GeneratorParser.Import_lineContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeneratorParser#import_name}.
	 * @param ctx the parse tree
	 */
	void enterImport_name(GeneratorParser.Import_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeneratorParser#import_name}.
	 * @param ctx the parse tree
	 */
	void exitImport_name(GeneratorParser.Import_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeneratorParser#tokens}.
	 * @param ctx the parse tree
	 */
	void enterTokens(GeneratorParser.TokensContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeneratorParser#tokens}.
	 * @param ctx the parse tree
	 */
	void exitTokens(GeneratorParser.TokensContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeneratorParser#token_line}.
	 * @param ctx the parse tree
	 */
	void enterToken_line(GeneratorParser.Token_lineContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeneratorParser#token_line}.
	 * @param ctx the parse tree
	 */
	void exitToken_line(GeneratorParser.Token_lineContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeneratorParser#startState}.
	 * @param ctx the parse tree
	 */
	void enterStartState(GeneratorParser.StartStateContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeneratorParser#startState}.
	 * @param ctx the parse tree
	 */
	void exitStartState(GeneratorParser.StartStateContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeneratorParser#skip_attr}.
	 * @param ctx the parse tree
	 */
	void enterSkip_attr(GeneratorParser.Skip_attrContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeneratorParser#skip_attr}.
	 * @param ctx the parse tree
	 */
	void exitSkip_attr(GeneratorParser.Skip_attrContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeneratorParser#states}.
	 * @param ctx the parse tree
	 */
	void enterStates(GeneratorParser.StatesContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeneratorParser#states}.
	 * @param ctx the parse tree
	 */
	void exitStates(GeneratorParser.StatesContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeneratorParser#state_line}.
	 * @param ctx the parse tree
	 */
	void enterState_line(GeneratorParser.State_lineContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeneratorParser#state_line}.
	 * @param ctx the parse tree
	 */
	void exitState_line(GeneratorParser.State_lineContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeneratorParser#parameters_state}.
	 * @param ctx the parse tree
	 */
	void enterParameters_state(GeneratorParser.Parameters_stateContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeneratorParser#parameters_state}.
	 * @param ctx the parse tree
	 */
	void exitParameters_state(GeneratorParser.Parameters_stateContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeneratorParser#returns_state}.
	 * @param ctx the parse tree
	 */
	void enterReturns_state(GeneratorParser.Returns_stateContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeneratorParser#returns_state}.
	 * @param ctx the parse tree
	 */
	void exitReturns_state(GeneratorParser.Returns_stateContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeneratorParser#rule_line}.
	 * @param ctx the parse tree
	 */
	void enterRule_line(GeneratorParser.Rule_lineContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeneratorParser#rule_line}.
	 * @param ctx the parse tree
	 */
	void exitRule_line(GeneratorParser.Rule_lineContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeneratorParser#parameters_rule}.
	 * @param ctx the parse tree
	 */
	void enterParameters_rule(GeneratorParser.Parameters_ruleContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeneratorParser#parameters_rule}.
	 * @param ctx the parse tree
	 */
	void exitParameters_rule(GeneratorParser.Parameters_ruleContext ctx);
	/**
	 * Enter a parse tree produced by {@link GeneratorParser#code_block}.
	 * @param ctx the parse tree
	 */
	void enterCode_block(GeneratorParser.Code_blockContext ctx);
	/**
	 * Exit a parse tree produced by {@link GeneratorParser#code_block}.
	 * @param ctx the parse tree
	 */
	void exitCode_block(GeneratorParser.Code_blockContext ctx);
}