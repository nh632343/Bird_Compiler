package analyse;

import java.util.List;

import node.ASTList;
import node.ASTree;
import node.Environment;

public class WhileList extends ASTList {

	public WhileList(List<ASTree> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object eval(Environment env) {
		//先计算出判断条件
		Object condition=child(0).eval(env);
		Object result=null;
		while(Environment.judge(condition)){
			result=child(1).eval(env);
		}
		return result;
	}

}
