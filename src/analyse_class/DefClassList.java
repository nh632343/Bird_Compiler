package analyse_class;

import java.util.List;


import analyse.NameLeaf;
import basic.StoneException;
import node.ASTList;
import node.ASTree;
import node.Environment;

public class DefClassList extends ASTList {

	public DefClassList(List<ASTree> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object eval(Environment env) {
		String className=((NameLeaf)child(0)).name();
		if(env.getLocal(className)!=null)
			throw new StoneException(className+" already existed");
		if(numChildren()==2){
			return env.putLocal(className, new ClassInfo(env, (ClassBody) child(1)));
		}
		else{
			String extendName=((NameLeaf)child(1)).name();
			Object superClassInfo=env.get(extendName);
			if(!(superClassInfo instanceof ClassInfo))
			       throw new StoneException("can not find "+extendName);
			return env.putLocal(className, new ClassInfo(env, (ClassBody)child(2), (ClassInfo)superClassInfo));
		}
	}

	
}
