package analyse_function;

import java.util.List;

import analyse.BlockList;
import analyse.NameLeaf;
import basic.StoneException;
import node.ASTList;
import node.ASTree;
import node.Environment;


public class DefList extends ASTList {

	public DefList(List<ASTree> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}

	public ParamList getParamList() {
		return (ParamList)child(1);
	}
	
	public BlockList getBlockList(){
		return (BlockList) child(2);
	}
	
	@Override
	public Object eval(Environment env) {
		//如果当前环境已存在函数名，报错
		if(env.getLocal(((NameLeaf)child(0)).name())!=null){
			throw new StoneException("function "+((NameLeaf)child(0)).name()+" already existed");
		}
		return env.putLocal(((NameLeaf)child(0)).name(), 
				new Function(getParamList(), getBlockList(), env));
	}
 
	
}
