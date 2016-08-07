package analyse_class;

import java.util.List;

import node.ASTList;
import node.ASTree;
import node.Environment;

public class ClassBody extends ASTList {

	public ClassBody(List<ASTree> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}
	@Override
	public Object eval(Environment env) {
		Object result=null;
		//��˳��ִ��block�ڵĳ���  �������һ����ִ�н��
		for(int i=0;i<numChildren();++i){
			result=child(i).eval(env);
		}
		return result;
	}
}
