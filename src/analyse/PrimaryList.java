package analyse;

import java.util.List;


import analyse_class.DotList;
import analyse_class.StoneObject;
import analyse_function.PostfixList;
import basic.StoneException;
import node.ASTList;
import node.ASTree;
import node.Environment;

public class PrimaryList extends ASTList {

	public static ASTree create(List<ASTree> c){
		return c.size()==1? c.get(0):new PrimaryList(c);
	}
	public PrimaryList(List<ASTree> list) {
		super(list);
	}

	@Override
	//调用本方法 说明跟有参数  因此调用postfixlist类的eval方法
	public Object eval(Environment env) {
		return ((PostfixList)child(1)).eval(env, child(0).eval(env));
	}
	
	@Override
	public Object fuzhi(Environment environment, ASTree asTree) {
		if (child(numChildren()-1) instanceof DotList
				&&child(numChildren()-1).numChildren()==1) {
			Object result=child(0).eval(environment);
			for(int i=1;i<numChildren()-1;++i){
				result=((PostfixList)child(i)).eval(environment, result);
			}
			if (!(result instanceof StoneObject)) 
				throw new StoneException("not an object");
			String name=((NameLeaf)child(numChildren()-1)).name();
			return ((StoneObject)result).put(name, asTree.eval(environment));
		}
		throw new StoneException("can not solve");
	}

	
}
