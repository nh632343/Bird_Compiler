package analyse_function;

import java.util.List;

import basic.StoneException;
import node.ASTList;
import node.ASTree;
import node.Environment;

public class PostfixList extends ASTList {

	public PostfixList(List<ASTree> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}

	public Object eval(Environment callerEnv,Object object){
		throw new StoneException("not a real");
	}
}
