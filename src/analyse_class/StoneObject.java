package analyse_class;

import basic.StoneException;
import node.Environment;

public class StoneObject {

	Environment objectEnv;
	StoneObject superStoneObject;
	public StoneObject(Environment objectEnv) {
		
		this.objectEnv = objectEnv;
	}
	public StoneObject(Environment objectEnv, StoneObject superStoneObject) {
		objectEnv.setOuter(superStoneObject.objectEnv);
		this.objectEnv = objectEnv;
		this.superStoneObject = superStoneObject;
		
	}
	
	public Object get(String string){
		Object result=objectEnv.getLocal(string);
		if(result==null&&superStoneObject!=null){
			result=superStoneObject.get(string);
		}
		return result;
	}
	
	protected Object putLocal(String string,Object object){
		return objectEnv.putLocal(string, object);
	}
	
	protected StoneObject where(String string){
		Object result=objectEnv.getLocal(string);
		if (result==null) {
			if(superStoneObject!=null)
				return superStoneObject.where(string);
			else
				return null;
		}
		return this;
		
	}
	
	public Object put(String string,Object object){
		StoneObject stoneObject=where(string);
		if(stoneObject==null)
			throw new StoneException(string+" doesn't exist");
		return stoneObject.putLocal(string, object);
	}
}
