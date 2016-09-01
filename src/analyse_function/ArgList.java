package analyse_function;

import java.util.List;

import analyse.BasicParser;
import analyse.NameLeaf;
import basic.StoneException;
import nativefunc.NativeFunction;
import node.ASTree;
import node.Environment;

public class ArgList extends PostfixList {

	public ArgList(List<ASTree> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object eval(Environment callerEnv, Object object) {   //CallerEnv用于计算实参的值     object应为Function对象
		if(BasicParser.nativeFunction){
			if (object instanceof NativeFunction) {
				try {
					NativeFunction nativeFunction=(NativeFunction) object;
					if(numChildren()!=nativeFunction.getNumParams())
						throw new StoneException("wrong args number",this);
					
					Object[] args=new Object[numChildren()];
					for(int i=0;i<numChildren();++i){
						args[i]=child(i).eval(callerEnv);
					}
   
					return nativeFunction.invoke(args);
				} catch (Exception e) {
					throw new StoneException("invoke java function failed",this);
				} 
				
			    
			}
		}
		
		if(!(object instanceof Function)){
			throw new StoneException("not a function");
		}
		Function function=(Function) object;
		ParamList paramList=function.getParamList();
		if(numChildren()!=paramList.numChildren()){
			throw new StoneException("wrong args number");
		}
		
		//创建子函数内部环境变量
		Environment localEnv=function.makeEnv();
		//计算实参并加入环境变量
		for(int i=0;i<numChildren();++i){
			localEnv.putLocal(((NameLeaf)paramList.child(i)).name(), child(i).eval(callerEnv));
		}
		//执行函数
		return function.getBlockList().eval(localEnv);
	}



	
}
