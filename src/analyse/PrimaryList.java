package analyse;

import java.util.List;

import analyse_array.Index;
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
		//对象操作
		if (child(numChildren()-1) instanceof DotList
				&&child(numChildren()-1).numChildren()==1) {
			Object result=child(0).eval(environment);
			for(int i=1;i<numChildren()-1;++i){
				result=((PostfixList)child(i)).eval(environment, result);
			}
			if (!(result instanceof StoneObject)) 
				throw new StoneException("not an object",this);
			String name=((NameLeaf)child(numChildren()-1).child(0)).name();
			return ((StoneObject)result).put(name, asTree.eval(environment));
		}
		
		//数组操作
		if(child(numChildren()-1) instanceof Index){
			Object result=child(0).eval(environment);
			for(int i=1;i<numChildren()-1;++i){
				result=((PostfixList)child(i)).eval(environment, result);
			}
			if (!(result instanceof Object[])) 
				throw new StoneException("not an array",this);
			Object[] array=(Object[])result;
			Object indexObject=child(numChildren()-1).child(0).eval(environment);
			if(!(indexObject instanceof Integer))
				throw new StoneException("not an index",this);
			int index=((Integer)indexObject).intValue();
			if(index>=array.length)
				throw new StoneException("out of boundary",this);
			array[index]=asTree.eval(environment);
			return null;
		}
		throw new StoneException("can not solve",this);
	}

	
}
