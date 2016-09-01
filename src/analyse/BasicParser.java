package analyse;


import java.util.HashSet;

import analyse_array.Array;
import analyse_array.Index;
import analyse_class.AtList;
import analyse_class.ClassBody;
import analyse_class.DefClassList;
import analyse_class.DotList;
import analyse_function.ArgList;
import analyse_function.DefList;
import analyse_function.ParamList;
import basic.CodeDialog;
import basic.Lexer;
import basic.ParseException;
import basic.Parser;
import basic.Parser.Operators;
import basic.Token;
import nativefunc.NativeFunctionList;
import node.ASTree;
import node.Environment;


public class BasicParser {
	public static final boolean bibao=true;
    public static final boolean nativeFunction=true;
    public static final boolean classFunction=true;
	
	protected HashSet<String> reserved=new HashSet<String>();
     protected Operators operators=new Operators();
     protected Parser expr;
     protected Parser postfix;
     protected Parser primary;
    protected Parser factor;
    protected Parser simple;
    protected Parser block;
    protected Parser statement;
    protected Parser empty;
    protected Parser program;
    
    protected Parser array;
    
    protected Parser def;
    protected Parser param;
    protected Parser args;
    
    protected Parser member;
    protected Parser classBody;
    protected Parser defclass;
    
    protected Parser temp;
    
	public BasicParser() {
		reserved.add(";");
		reserved.add("}");
		reserved.add(")");
		reserved.add(Token.EOL);
		
		operators.add("=", 1, Operators.RIGHT);
		operators.add("&&", 2, Operators.LEFT);
		operators.add("||", 2, Operators.LEFT);
		operators.add("==", 3, Operators.LEFT);
		operators.add(">=", 3, Operators.LEFT);
		operators.add("<=", 3, Operators.LEFT);
		operators.add(">", 3, Operators.LEFT);
		operators.add("<", 3, Operators.LEFT);
		operators.add("+", 4, Operators.LEFT);
		operators.add("-", 4, Operators.LEFT);
		operators.add("*", 5, Operators.LEFT);
		operators.add("/", 5, Operators.LEFT);
		operators.add("%", 5, Operators.LEFT);
		
		expr=Parser.rule();
	   postfix=Parser.rule();
	   
	   array=Parser.rule().sep("{").maybe(Parser.rule(Array.class).ast(expr).repeat(Parser.rule().sep(",").ast(expr))).sep("}") ;
	   //BNF:  Primary: "(" expr ")" |number | string  | "@" identifier | identifier {postfix}
	   primary=Parser.rule().or(array,
			                    Parser.rule().sep("(").ast(expr).sep(")"),
	    		                Parser.rule().number(NumLeaf.class),
	    		                Parser.rule().string(StrLeaf.class),
	    		                Parser.rule(AtList.class).sep("@").identifier(NameLeaf.class, reserved),
	    		                Parser.rule(PrimaryList.class).identifier(NameLeaf.class, reserved).repeat(postfix));
	   // factor: primary | "-" primary
	   factor=Parser.rule().or(primary,
	    		                Parser.rule(NegativeList.class).sep("-").ast(primary));
	  // expr: factor {OP factor}
	   expr=expr.expression(ExprList.class,factor,operators);
	   // simple: expr
	   simple=expr;
	 
	   block=Parser.rule(BlockList.class);
	   // statement:  "while" expr block | "if" expr block ["else" block] | simple
	   statement=Parser.rule().or(Parser.rule(WhileList.class).sep("while").ast(expr).ast(block),
			                      Parser.rule(IfList.class).sep("if").ast(expr).ast(block).option(Parser.rule().sep("else").ast(block)),
			                      simple);

	   
	   // block: "{" [statement] {(;|EOL) [statement]} "}"
	   block=block.sep("{").option(statement).repeat(Parser.rule().sep(";",Token.EOL).option(statement)).sep("}");
	  
	   
	   //param: [ identifier {"," identifier} ]
	   param=Parser.rule(ParamList.class).identifier(NameLeaf.class, reserved).repeat(Parser.rule().sep(",").identifier(NameLeaf.class, reserved));
	   //def : "def" identifier "(" param ")" block
	   def=Parser.rule(DefList.class).sep("def").identifier(NameLeaf.class,reserved).sep("(").maybe(param).sep(")").ast(block);
	   //args: expr {"," expr}
	   args=Parser.rule(ArgList.class).ast(expr).repeat(Parser.rule().sep(",").ast(expr));
	   
	   // postfix: "(" [args] ")"
	   postfix=postfix.or(Parser.rule(Index.class).sep("[").ast(expr).sep("]")
			   ,Parser.rule().sep("(").maybe(args).sep(")"));
	   
	   empty=Parser.rule(EmptyList.class);
	   
	   // program: [statement| def] (;|EOL)
	   program=Parser.rule().option(Parser.rule().or(def,statement,empty)).sep(";",Token.EOL);
	   
	   if(bibao){
		  
		   statement.insertChoice(def);
	   }
	   
	   if (classFunction) {
			defclass=Parser.rule(DefClassList.class);
			//  member: def | defclass |statement
			member=Parser.rule().or(def,defclass,statement);
			//  classbody:  "{" [member] { ";|EOL" [member] }
			classBody=Parser.rule(ClassBody.class).sep("{").option(member).repeat(Parser.rule().sep(";"+Token.EOL,";",Token.EOL).option(member)).sep("}");
			//  defclass: "class" identifier ["extends" identifier ] classbody
			defclass=defclass.sep("class").identifier(NameLeaf.class, reserved)
					.option(Parser.rule().sep("extends").identifier(NameLeaf.class, reserved))
					.ast(classBody);
			//  postfix:  "(" [args] ")" | "." identifier [ "(" [args] ")" ] 
			postfix.insertChoice(Parser.rule(DotList.class).sep(".").identifier(NameLeaf.class,reserved).option(Parser.rule().sep("(").maybe(args).sep(")")));
		    program.insertChoice(defclass);
	   }
	}
    
	public ASTree parse(Lexer lexer) throws ParseException{
		return program.parse(lexer);
	}
	
	public static void main(String[] args) throws ParseException{
		Environment env=new Environment();
		BasicParser basicParser=new BasicParser();
		Lexer lexer=new Lexer(new CodeDialog());
	    
		if(nativeFunction){
			try {
				NativeFunctionList.append(env);
			} catch (Exception e) {
				System.out.println("add native java function failed!!");
				e.printStackTrace();
			} 
		}
		
		while(lexer.peek(0)!=Token.EOF){
				ASTree t= basicParser.parse(lexer);
				//t.display(0);                  //展示语法树
				Object object=t.eval(env);
			    System.out.println("=>> "+object);   //显示语句执行结果
	
        }
}
}
