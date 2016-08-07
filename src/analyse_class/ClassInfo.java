package analyse_class;

import node.Environment;

public class ClassInfo {

	protected Environment environment;
	protected ClassBody classBody;
	protected ClassInfo superClassInfo;
	
	public ClassInfo(Environment environment, ClassBody classBody) {
		
		this.environment = environment;
		this.classBody = classBody;
	}
	public ClassInfo(Environment environment, ClassBody classBody, ClassInfo superClassInfo) {
		
		this.environment = environment;
		this.classBody = classBody;
		this.superClassInfo = superClassInfo;
	}
	
	public StoneObject newObject(){
		Environment objectEnv=new Environment(environment);
		classBody.eval(objectEnv);
		if(superClassInfo==null){
			return new StoneObject(objectEnv);
		}
		else{
			return new StoneObject(objectEnv, superClassInfo.newObject());
		}
	}
}
