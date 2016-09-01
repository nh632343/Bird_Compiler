package analyse;

import java.util.List;

import basic.StoneException;
import node.ASTList;
import node.ASTree;
import node.Environment;

public class NegativeList extends ASTList {

	public NegativeList(List<ASTree> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object eval(Environment env) {
		Object result=child(0).eval(env);
		if(result instanceof Integer)
		  return -(((Integer)result).intValue());
		if(result instanceof Double)
			return -(((Double)result).doubleValue());
		throw new StoneException("can not convert to -", this);
	}
	

}
