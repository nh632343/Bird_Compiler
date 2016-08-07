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
		//��ȡ�����
		String operator=((ASTLeaf)child(1)).token().getText();
		//�����Ϊ ��=�� �����      ��߱���ΪNameLeaf����
		if(operator.equals("=")){
			return child(0).fuzhi(env, child(2));
		}
		else {//��ȡ���������ֵ
			Object left=child(0).eval(env);
			Object right=child(2).eval(env);
			//�����Ϊ ��&&�� 
			if(operator.equals("&&")){
				left=Environment.judge(left);
				right=Environment.judge(right);
				return ((boolean)left&&(boolean)right)==true? 1:0;
			}
			//�����Ϊ ��||��
			if(operator.equals("||")){
				left=Environment.judge(left);
				right=Environment.judge(right);
				return ((boolean)left||(boolean)right)==true? 1:0;
			}
			//���߶�Ϊ����������������� &&  ||
			if(left instanceof Integer&&right instanceof Integer){
				return numberCompute(((Integer)left).intValue(), operator,((Integer)right).intValue());
			}
			//�����Ϊ + ʱ���ַ��������� �� ���������� �� �ַ������ַ��� �ǿ��е�
			//���������������ѿ���
			if(operator.equals("+")){
				if((left instanceof String && right instanceof String)
						||(left instanceof String && right instanceof Integer)
						||(left instanceof Integer && right instanceof String)
						){
					return String.valueOf(left)+String.valueOf(right);
				}
			}
			//�����Ϊ == ֻ������������ �� �ַ������ַ��� ����
			//���������������ѿ���
			if(operator.equals("==")){
				if(left instanceof String && right instanceof String){
					return ((String)left).equals((String)right);
				}
			}
			
			throw new StoneException("can not solve \""+operator+"\"");
		}
	}
	

}
