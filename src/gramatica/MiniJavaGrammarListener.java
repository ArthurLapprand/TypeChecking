// Generated from MiniJavaGrammar.g4 by ANTLR 4.4
package gramatica;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MiniJavaGrammarParser}.
 */
public interface MiniJavaGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMethodDeclaration(@NotNull MiniJavaGrammarParser.MethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMethodDeclaration(@NotNull MiniJavaGrammarParser.MethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#goal}.
	 * @param ctx the parse tree
	 */
	void enterGoal(@NotNull MiniJavaGrammarParser.GoalContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#goal}.
	 * @param ctx the parse tree
	 */
	void exitGoal(@NotNull MiniJavaGrammarParser.GoalContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(@NotNull MiniJavaGrammarParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(@NotNull MiniJavaGrammarParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#mainClass}.
	 * @param ctx the parse tree
	 */
	void enterMainClass(@NotNull MiniJavaGrammarParser.MainClassContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#mainClass}.
	 * @param ctx the parse tree
	 */
	void exitMainClass(@NotNull MiniJavaGrammarParser.MainClassContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(@NotNull MiniJavaGrammarParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(@NotNull MiniJavaGrammarParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(@NotNull MiniJavaGrammarParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(@NotNull MiniJavaGrammarParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclaration(@NotNull MiniJavaGrammarParser.VarDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclaration(@NotNull MiniJavaGrammarParser.VarDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaGrammarParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(@NotNull MiniJavaGrammarParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaGrammarParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(@NotNull MiniJavaGrammarParser.ClassDeclarationContext ctx);
}