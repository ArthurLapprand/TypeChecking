package gramatica;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

import ast.*;
import gramatica.MiniJavaGrammarParser.*;

public class Semente {

	public Program getProgram(GoalContext g) {
		MainClass m = this.visitMain(g.mainClass());
		ClassDeclList l = this.visitClassDeclList(g.classDeclaration());
		Program p = new Program(m, l);
		return p;
	}

	public MainClass visitMain(MainClassContext mcc) {
		List<TerminalNode> tl = mcc.IDENTIFIER();
		TerminalNode ai1 = tl.get(0);
		TerminalNode ai2 = tl.get(1);
		Statement as = this.visitStatement(mcc.statement());
		MainClass mc = new MainClass(new Identifier(ai1.getText()), new Identifier(ai2.getText()), as);
		return mc;
	}

	public Statement visitStatement(StatementContext sc) {
		Statement s = null;
		List<StatementContext> scl = sc.statement();
		TerminalNode tn = sc.IDENTIFIER();
		List<ExpressionContext> ecl = sc.expression();
		
		if ((scl.size() == 1) && (ecl.size() == 1)) {
			// WHILE
			s = this.visitWhile(ecl.get(0), scl.get(0));

		} else if ((scl.size() == 2) && (ecl.size() == 1)) {
			// IF
			s = this.visitIf(ecl.get(0), scl.get(0), scl.get(1));

		} else if ((scl.size() == 0) && (ecl.size() == 1) && (tn == null)) {
			// SYSTEM.OUT.PRINTLN
			s = new Print(this.visitExpression(ecl.get(0)));

		} else if (tn != null && ecl.size() == 1) {
			// ATRIBUICAO
			s = new Assign(new Identifier(tn.getText()), this.visitExpression(ecl.get(0)));

		} else if (tn != null && ecl.size() == 2) {
			// ATRIBUICAO DE ARRAY
			s = new ArrayAssign(new Identifier(tn.getText()), this.visitExpression(ecl.get(0)), this.visitExpression(ecl.get(1)));
		
		} else s = new Block(this.visitStatementList(scl));
		
		return s;
		
	}
	
	public StatementList visitStatementList(List<StatementContext> sc) {
		StatementList sl = new StatementList();
		List<StatementContext> l = sc;
		for (int i = 0; i < l.size(); i++) {
			sl.addElement(this.visitStatement(l.get(i)));
		}
		return sl;
	}

	public While visitWhile(ExpressionContext ec, StatementContext sc) {
		Exp e = this.visitExpression(ec);
		Statement s = this.visitStatement(sc);
		return new While(e, s);
	}

	public If visitIf(ExpressionContext ec, StatementContext sc1, StatementContext sc2) {
		Exp e = this.visitExpression(ec);
		Statement s1 = this.visitStatement(sc1);
		Statement s2 = this.visitStatement(sc2);
		return new If(e, s1, s2);
	}

	public Exp visitExpression(ExpressionContext ec) {
		Exp e = null;
		List<ExpressionContext> ecl = ec.expression();
		TerminalNode operators = ec.OP();
		TerminalNode tn = ec.IDENTIFIER();
		TerminalNode integer = ec.INTEGER();
		String s = ec.getText();
		String op = null;
		if (operators != null) op = operators.getText();
		
		if (op != null) {
			if (op.equals("&&")) {
				
				e = new And(this.visitExpression(ecl.get(0)), this.visitExpression(ecl.get(1)));
				
			} else if (op.equals("<")) {
				
				e = new LessThan(this.visitExpression(ecl.get(0)), this.visitExpression(ecl.get(1)));
				
			} else if (op.equals("+")) {
				
				e = new Plus(this.visitExpression(ecl.get(0)), this.visitExpression(ecl.get(1)));
				
			} else if (op.equals("-")) {
				
				e = new Minus(this.visitExpression(ecl.get(0)), this.visitExpression(ecl.get(1)));
				
			} else if (op.equals("*")) {
				
				e = new Times(this.visitExpression(ecl.get(0)), this.visitExpression(ecl.get(1)));
				
			}
		} else if (ecl.size() == 2 && (tn == null)) {
			
			e = new ArrayLookup(this.visitExpression(ecl.get(0)), this.visitExpression(ecl.get(1)));
			
		} else if (s.startsWith("length")) {
			
			e = new ArrayLength(this.visitExpression(ecl.get(0)));
			
		} else if ((ecl.size() >= 1) && (tn != null)) {
			List<ExpressionContext> ecl2 = new ArrayList<ExpressionContext>();
			for (int i = 1; i < ecl.size(); i++) {
				ecl2.add(ecl.get(i));
			}
			e = new Call(this.visitExpression(ecl.get(0)), new Identifier(tn.getText()), this.visitExpressionList(ecl2));
			
		} else if (integer != null) {
			
			e = new IntegerLiteral(Integer.parseInt(integer.getText()));
			
		} else if (s.equals("true")) {
			
			e = new True();
			
		} else if (s.equals("false")) {
			
			e = new False();
			
		} else if (ecl.size() == 0 && tn != null && !s.contains("new") && !s.contains("(") && !s.contains(")")) {
			
			e = new IdentifierExp(tn.getText());
			
		} else if (s.equals("this")) {
			
			e = new This();
			
		} else if (s.startsWith("new")) {
			if (ecl.size() == 1) {
				
				e = new NewArray(this.visitExpression(ecl.get(0)));
				
			} else {
				
				e = new NewObject(new Identifier(tn.getText()));
				
			}
		} else if ((s.contains("!")) && (ecl.size() == 1)) {
			
			e = new Not(this.visitExpression(ecl.get(0)));
			
		} else {
			
			e = this.visitExpression(ecl.get(0));
			
		}
		
		return e;
	}
	
	public ExpList visitExpressionList(List<ExpressionContext> ec) {
		ExpList expl = new ExpList();
		for (int i = 0; i < ec.size(); i++) {
			expl.addElement(this.visitExpression(ec.get(i)));
		}
		return expl;
	}

	public ClassDeclList visitClassDeclList(List<ClassDeclarationContext> cl) {
		ClassDeclList cdl = new ClassDeclList();
		for( int i = 0; i < cl.size(); i++) {
			cdl.addElement(this.visitClassDecl(cl.get(i)));
		}
		return cdl;
	}

	public ClassDecl visitClassDecl(ClassDeclarationContext cdc){
		List<TerminalNode> identificadores = cdc.IDENTIFIER();
		ClassDecl cd;
		//checando os tipo de implementacao se tem extends
		if (identificadores.size() < 2){
			cd = new ClassDeclSimple(new Identifier(identificadores.get(0).getText()), this.visitVarDeclList(cdc.varDeclaration()), this.visitMethodDeclarationList(cdc.methodDeclaration()));
		} else {
			cd = new ClassDeclExtends(new Identifier(identificadores.get(0).getText()), new Identifier(identificadores.get(3).getText()), this.visitVarDeclList(cdc.varDeclaration()),  this.visitMethodDeclarationList(cdc.methodDeclaration()));
		}
		return cd;
	}
	
	public VarDeclList visitVarDeclList(List<VarDeclarationContext> vdcl){
		VarDeclList novovdcl = new VarDeclList();
		//memo troco do de cima
		for(int i = 0; i < vdcl.size(); i++) {
			novovdcl.addElement(this.visitVarDecl(vdcl.get(i)));
		}
		return novovdcl;
	}

	public VarDecl visitVarDecl(VarDeclarationContext vdc){
		TerminalNode identificador = vdc.IDENTIFIER();
		//nem tem if grazadeus
		VarDecl vd = new VarDecl(this.visitType(vdc.type()), new Identifier(identificador.getText()));
		return vd;
	}
	
	public Type visitType(TypeContext typeContext){
		Type t = null;
		TerminalNode identificador = typeContext.IDENTIFIER();
		String aux = typeContext.getText();
		//fazemos type como sendo uma classe final, checar essa gambiarra depois 
		if (aux.equals("boolean")) {		
			t = new BooleanType();	
		} else if(aux.equals("int []")) {
			t = new IntArrayType();
		} else if(aux.equals("int")) {
			t = new IntegerType();
		} if (identificador != null) {
			t = new IdentifierType(identificador.getText());
		}
		return t;
	}
	
	public MethodDeclList visitMethodDeclarationList(List<MethodDeclarationContext> mdc){
		MethodDeclList novomdc = new MethodDeclList();
		//memo troco do de cima cima
		for(int i=0; i < mdc.size();i++){
			novomdc.addElement(this.visitMethodDecl(mdc.get(i)));
		}
		return novomdc;
	}



	public MethodDecl visitMethodDecl(MethodDeclarationContext mdc) {
			//ler as listas na ordem conhecida 
			List<TypeContext> listaTipos = mdc.type();
			List<TerminalNode> identificadores = mdc.IDENTIFIER();

			MethodDecl novoMd = null;
			//A gramatica nao tem a formallist vamos instanciar uma estaticamente e ver no que da
			FormalList formais = new FormalList();

			Type tipoMetodo = this.visitType(listaTipos.get(0));

			Identifier nomeMetodo = new Identifier(identificadores.get(0).getText());
			//formais estaticos, eles sao um tipo e um identificador
			for(int i = 1; i < listaTipos.size(); i++){
				formais.addElement(new Formal(this.visitType(listaTipos.get(i)),new Identifier(identificadores.get(i).getText())));

			}
			//pega as variaveis
			VarDeclList variaveis = this.visitVarDeclList(mdc.varDeclaration());
			//pega os statements
			StatementList statements = this.visitStatementList(mdc.statement());
			//pega a expression
			Exp exp = this.visitExpression(mdc.expression());
			//cria o retorno
			novoMd = new MethodDecl(tipoMetodo,nomeMetodo,formais,variaveis,statements,exp);

			return novoMd;

		}

}
