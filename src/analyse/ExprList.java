package analyse;

import java.util.List;



import basic.StoneException;
import node.ASTLeaf;
import node.ASTList;
import node.ASTree;
import node.Environment;

public class ExprList extends ASTList {

	public static ASTree create(List<ASTree> c){
		return c.size()==1? c.get(0):new ExprList(c);
	}
	public ExprList(List<ASTree> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}
	
	public int numberCompute(int a,String operator,int b) {
		if(operator.equals("+"))  return a+b;
		if(operator.equals("-"))  return a-b;
		if(operator.equals("*"))  return a*b;
		if(operator.equals("/"))  return a/b;
		if(operator.equals("%"))  return a%b;
		if(operator.equals(">"))  return a>b? 1:0;
		if(operator.equals(">="))  return a>=b? 1:0;
		if(operator.equals("<"))  return a<b? 1:0;
		if(operator.equals("<="))  return a<=b? 1:0;
		if(operator.equals("=="))  return a==b? 1:0;
		throw new StoneException("unknown operator \""+operator+"\"");
	}
	
	@Override
	public Object eval(Environment env) {
		//获取运算符
		String operator=((ASTLeaf)child(1)).token().getText();
		//运算符为 “=” 的情况      左边必须为NameLeaf类型
		if(operator.equals("=")){
			return child(0).fuzhi(env, child(2));
		}
		else {//获取左右两侧的值
			Object left=child(0).eval(env);
			Object right=child(2).eval(env);
			//运算符为 “&&” 
			if(operator.equals("&&")){
				left=Environment.judge(left);
				right=Environment.judge(right);
				return ((boolean)left&&(boolean)right)==true? 1:0;
			}
			//运算符为 “||”
			if(operator.equals("||")){
				left=Environment.judge(left);
				right=Environment.judge(right);
				return ((boolean)left||(boolean)right)==true? 1:0;
			}
			//两边都为整数，且运算符不是 &&  ||
			if(left instanceof Integer&&right instanceof Integer){
				return numberCompute(((Integer)left).intValue(), operator,((Integer)right).intValue());
			}
			//运算符为 + 时，字符串加整数 和 整数加整数 和 字符串加字符串 是可行的
			//整数加整数上面已考虑
			if(operator.equals("+")){
				if((left instanceof String && right instanceof String)
						||(left instanceof String && right instanceof Integer)
						||(left instanceof Integer && right instanceof String)
						){
					return String.valueOf(left)+String.valueOf(right);
				}
			}
			//运算符为 == 只有整数与整数 和 字符串与字符串 可行
			//整数与整数上面已考虑
			if(operator.equals("==")){
				if(left instanceof String && right instanceof String){
					return ((String)left).equals((String)right);
				}
			}
			
			throw new StoneException("can not solve \""+operator+"\"");
		}
	}
	

}
