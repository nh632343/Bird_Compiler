package analyse_class;

import java.util.List;

import analyse.NameLeaf;
import basic.StoneException;
import node.ASTList;
import node.ASTree;
import node.Environment;

public class AtList extends ASTList {

	public AtList(List<ASTree> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object eval(Environment env) {
		String name=((NameLeaf)child(0)).name();
		Object result=env.getLocal(name);
		if(result==null)
			throw new StoneException("local variable "+name+" doesn't exist" );
		return result;
	}

	@Override
	public Object fuzhi(Environment environment, ASTree asTree) {
		String name=((NameLeaf)child(0)).name();
		return environment.putLocal(name, asTree.eval(environment));
	}

}
