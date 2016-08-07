package analyse;

import java.util.List;

import node.ASTList;
import node.ASTree;
import node.Environment;

public class IfList extends ASTList {

	public IfList(List<ASTree> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object eval(Environment env) {
		//���Ϊ��
		if(Environment.judge(child(0).eval(env))){
			return child(1).eval(env);
		}
		//���Ϊ��
		else{
			//����� else
			if(numChildren()==3){
				return child(2).eval(env);
			}
		}
		return null;
	}

}
