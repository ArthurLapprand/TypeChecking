package gramatica;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import ast.Program;
import visitor.BuildSymbolTableVisitor;
import visitor.PrettyPrintVisitor;
import visitor.TypeCheckVisitor;

public class Teste {

	public static void main(String[] args) throws IOException {
		File f1 = new File("C:\\Users\\Lapp\\git\\TypeChecking\\src\\testes\\Factorial.txt");
		FileInputStream f = new FileInputStream(f1);
		ANTLRInputStream in = new ANTLRInputStream(f);
		MiniJavaGrammarLexer lex = new MiniJavaGrammarLexer(in);
		CommonTokenStream tok = new CommonTokenStream(lex);
		MiniJavaGrammarParser pars = new MiniJavaGrammarParser(tok);
		//ParseTree tree = pars.goal();
		Semente s = new Semente();
		Program p = s.getProgram(pars.goal());
		BuildSymbolTableVisitor stVis = new BuildSymbolTableVisitor();
		//construindo tabela de símbolos
		p.accept(stVis);
		//fazendo a checagem de tipos
		p.accept(new TypeCheckVisitor(stVis.getSymbolTable()));
		System.out.println("TypeCheking passed");
		//PrettyPrintVisitor ppv = new PrettyPrintVisitor();
		//p.accept(ppv);
		
	}

}
