package visitor;

import symboltable.Method;
import symboltable.Class;
import symboltable.SymbolTable;
import symboltable.Variable;
import ast.And;
import ast.ArrayAssign;
import ast.ArrayLength;
import ast.ArrayLookup;
import ast.Assign;
import ast.Block;
import ast.BooleanType;
import ast.Call;
import ast.ClassDeclExtends;
import ast.ClassDeclSimple;
import ast.False;
import ast.Formal;
import ast.Identifier;
import ast.IdentifierExp;
import ast.IdentifierType;
import ast.If;
import ast.IntArrayType;
import ast.IntegerLiteral;
import ast.IntegerType;
import ast.LessThan;
import ast.MainClass;
import ast.MethodDecl;
import ast.Minus;
import ast.NewArray;
import ast.NewObject;
import ast.Not;
import ast.Plus;
import ast.Print;
import ast.Program;
import ast.This;
import ast.Times;
import ast.True;
import ast.Type;
import ast.VarDecl;
import ast.While;

public class TypeCheckVisitor implements TypeVisitor {

	private SymbolTable symbolTable;
	private Class currClass;
	private Method currMethod;

	public TypeCheckVisitor(SymbolTable st) {
		symbolTable = st;
	}

	// MainClass m;
	// ClassDeclList cl;
	public Type visit(Program n) {
		n.m.accept(this);
		for (int i = 0; i < n.cl.size(); i++) {
			n.cl.elementAt(i).accept(this);
		}
		return null;
	}

	// Identifier i1,i2;
	// Statement s;
	public Type visit(MainClass n) {
		n.i1.accept(this);
		n.i2.accept(this);
		n.s.accept(this);
		return null;
	}

	// Identifier i;
	// VarDeclList vl;
	// MethodDeclList ml;
	public Type visit(ClassDeclSimple n) {
		currMethod = null;
		currClass = symbolTable.getClass(n.i.s);
		n.i.accept(this);
		for (int i = 0; i < n.vl.size(); i++) {
			n.vl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.ml.size(); i++) {
			n.ml.elementAt(i).accept(this);
		}
		return null;
	}

	// Identifier i;
	// Identifier j;
	// VarDeclList vl;
	// MethodDeclList ml;
	public Type visit(ClassDeclExtends n) {
		currMethod = null;
		currClass = symbolTable.getClass(n.i.s);
		n.i.accept(this);
		n.j.accept(this);
		for (int i = 0; i < n.vl.size(); i++) {
			n.vl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.ml.size(); i++) {
			n.ml.elementAt(i).accept(this);
		}
		return null;
	}

	// Type t;
	// Identifier i;
	public Type visit(VarDecl n) {
		if (currClass == null) {
			System.out.println("Vari�vel fora de uma Classe");
			System.exit(0);
		}
		Type t = symbolTable.getVarType(currMethod, currClass, n.i.s);
		if (n.t != t) {
			System.out.println("Tipos de vari�veis incompat�veis");
			System.exit(0);
		}
		n.t.accept(this);
		n.i.accept(this);
		return null;
	}

	// Type t;
	// Identifier i;
	// FormalList fl;
	// VarDeclList vl;
	// StatementList sl;
	// Exp e;
	public Type visit(MethodDecl n) {
		currMethod = symbolTable.getMethod(n.i.s, currClass.getId());
		//currMethod = currClass.getMethod(n.i.s);
		n.t.accept(this);
		n.i.accept(this);
		for (int i = 0; i < n.fl.size(); i++) {
			n.fl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.vl.size(); i++) {
			n.vl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.sl.size(); i++) {
			n.sl.elementAt(i).accept(this);
		}
		n.e.accept(this);
		return null;
	}

	// Type t;
	// Identifier i;
	public Type visit(Formal n) {
		n.t.accept(this);
		n.i.accept(this);
		return null;
	}

	public Type visit(IntArrayType n) {
		return null;
	}

	public Type visit(BooleanType n) {
		return null;
	}

	public Type visit(IntegerType n) {
		return null;
	}

	// String s;
	public Type visit(IdentifierType n) {
		return null;
	}

	// StatementList sl;
	public Type visit(Block n) {
		for (int i = 0; i < n.sl.size(); i++) {
			n.sl.elementAt(i).accept(this);
		}
		return null;
	}

	// Exp e;
	// Statement s1,s2;
	public Type visit(If n) {		
		Type t = n.e.accept(this);
		if (!(t instanceof BooleanType)) {
			System.out.println("If com express�o n�o booleana");
			System.exit(0);
		}
		n.s1.accept(this);
		n.s2.accept(this);
		return null;
	}

	// Exp e;
	// Statement s;
	public Type visit(While n) {
		Type t = n.e.accept(this);
		if (!(t instanceof BooleanType)) {
			System.out.println("While com express�o n�o booleana");
			System.exit(0);
		}
		n.s.accept(this);
		return null;
	}

	// Exp e;
	public Type visit(Print n) {
		if (n.e.accept(this) == null) {
			System.out.println("Print com express�o nula");
			System.exit(0);
		}
		return null;
	}

	// Identifier i;
	// Exp e;
	public Type visit(Assign n) {
		Type t1 = n.i.accept(this);
		Type t2 = n.e.accept(this);
		if (t1 != t2) {
			System.out.println("Assign com tipos diferentes");
			System.exit(0);
		}
		return null;
	}

	// Identifier i;
	// Exp e1,e2;
	public Type visit(ArrayAssign n) {
		n.i.accept(this);
		if (n.e1.accept(this) instanceof IntegerType) {
			System.out.println("�ndice do array n�o � um inteiro");
			System.exit(0);
		}
		n.e2.accept(this);
		return null;
	}

	// Exp e1,e2;
	public Type visit(And n) {
		if (!symbolTable.compareTypes(n.e1.accept(this), n.e2.accept(this))) {
			System.out.println("Express�o AND com tipos incompat�veis");
			System.exit(0);
		};
		return new BooleanType();
	}

	// Exp e1,e2;
	public Type visit(LessThan n) {
		if (!symbolTable.compareTypes(n.e1.accept(this), n.e2.accept(this))) {
			System.out.println("Express�o LessThan com tipos incompat�veis");
			System.exit(0);
		};
		return new BooleanType();
	}

	// Exp e1,e2;
	public Type visit(Plus n) {
		if (!symbolTable.compareTypes(n.e1.accept(this), n.e2.accept(this))) {
			System.out.println("Express�o PLUS com tipos incompat�veis");
			System.exit(0);
		};
		return new IntegerType();
	}

	// Exp e1,e2;
	public Type visit(Minus n) {
		if (!symbolTable.compareTypes(n.e1.accept(this), n.e2.accept(this))) {
			System.out.println("Express�o MINUS com tipos incompat�veis");
			System.exit(0);
		};
		return new IntegerType();
	}

	// Exp e1,e2;
	public Type visit(Times n) {
		if (!symbolTable.compareTypes(n.e1.accept(this), n.e2.accept(this))) {
			System.out.println("Express�o TIMES com tipos incompat�veis");
			System.exit(0);
		};
		return new IntegerType();
	}

	// Exp e1,e2;
	public Type visit(ArrayLookup n) {
		n.e1.accept(this);
		n.e2.accept(this);
		return new IntegerType();
	}

	// Exp e;
	public Type visit(ArrayLength n) {
		n.e.accept(this);
		return new IntegerType();
	}

	// Exp e;
	// Identifier i;
	// ExpList el;
	public Type visit(Call n) {
		Class c = currClass;
		currClass = symbolTable.getClass(((IdentifierType) n.e.accept(this)).s);
		Type t = n.i.accept(this);
		currClass = c;
		for (int i = 0; i < n.el.size(); i++) {
			n.el.elementAt(i).accept(this);
		}
		return t;
	}

	// int i;
	public Type visit(IntegerLiteral n) {
		return new IntegerType();
	}

	public Type visit(True n) {
		return new BooleanType();
	}

	public Type visit(False n) {
		return new BooleanType();
	}

	// String s;
	public Type visit(IdentifierExp n) {
		return symbolTable.getVarType(currMethod, currClass, n.s);
	}

	public Type visit(This n) {
		return new IdentifierType(currClass.getId());
	}

	// Exp e;
	public Type visit(NewArray n) {
		n.e.accept(this);
		return new IntArrayType();
	}

	// Identifier i;
	public Type visit(NewObject n) {
		return new IdentifierType(n.i.s);
	}

	// Exp e;
	public Type visit(Not n) {
		if (!(n.e.accept(this) instanceof BooleanType)) {
			System.out.println("Express�o NOT com elemento n�o booleano");
			System.exit(0);
		}
		return new BooleanType();
	}

	// String s;
	public Type visit(Identifier n) {
		Type t = null;
		Variable v = currClass.getVar(n.s);
		if (currMethod != null) {
			v = currMethod.getVar(n.s);
			if (v == null) v = currMethod.getParam(n.s);
			else t = symbolTable.getVarType(currMethod, currClass, n.s);
		} else t = symbolTable.getMethodType(n.s, currClass.getId());
		return t;
	}
}
