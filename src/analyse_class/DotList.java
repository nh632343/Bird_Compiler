package analyse_class;

import java.util.List;

import analyse.NameLeaf;

import analyse_function.PostfixList;
import basic.StoneException;
import node.ASTree;
import node.Environment;

public class DotList extends PostfixList {

	public DotList(List<ASTree> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object eval(Environment env,Object object) {
		String child0Name=((NameLeaf)child(0)).name();
		if(numChildren()==1){
			if(child0Name.equals("new")){
				if(!(object instanceof ClassInfo))
					throw new StoneException("can not new a object");
				ClassInfo classInfo=(ClassInfo) object;
				return classInfo.newObject();
			}
			else{
				if(!(object instanceof StoneObject))
					throw new StoneException(" not an object");
				StoneObject stoneObject=(StoneObject) object;
				Object result=stoneObject.get(child0Name);
				if(result==null)
					throw new StoneException(child0Name+" doesn't exist");
				return result;
			}
		}
		else{ //numchildren==2
			if(!(object instanceof StoneObject))
				throw new StoneException(" not an object");
			StoneObject stoneObject=(StoneObject) object;
			Object function=stoneObject.get(child0Name);
			
			return ((PostfixList)child(1)).eval(env, function);
		}
	}
	

}
