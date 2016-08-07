package analyse;

import java.util.List;

import node.ASTList;
import node.ASTree;
import node.Environment;

public class EmptyList extends ASTList {

	public EmptyList(List<ASTree> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object eval(Environment env) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
