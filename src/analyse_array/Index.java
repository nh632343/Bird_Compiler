package analyse_array;

import java.util.List;

import analyse_function.PostfixList;
import basic.StoneException;
import node.ASTree;
import node.Environment;

public class Index extends PostfixList {

	public Index(List<ASTree> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object eval(Environment callerEnv, Object object) {
		// TODO Auto-generated method stub
		if(!(object instanceof Object[]))
			throw new StoneException("not an array", this);
		Object[] array=(Object[])object;
		Object indexObject=child(0).eval(callerEnv);
		if(!(indexObject instanceof Integer))
			throw new StoneException("not an index",this);
		int index=((Integer)indexObject).intValue();
		if(index>=array.length)
			throw new StoneException("out of boundary",this);
		return array[index];
	}

}
