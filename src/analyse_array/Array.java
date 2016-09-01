package analyse_array;

import java.util.List;

import node.ASTList;
import node.ASTree;
import node.Environment;

public class Array extends ASTList {

	public Array(List<ASTree> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object eval(Environment env) {
		// TODO Auto-generated method stub
		Object[] array=new Object[numChildren()];
		for(int i=0;i<numChildren();++i){
			array[i]=child(i).eval(env);
			
		}
		return array;
	}

}
