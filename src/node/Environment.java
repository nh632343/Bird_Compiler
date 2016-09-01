package node;

import java.util.HashMap;



public class Environment {
    protected HashMap<String, Object> values;
    protected Environment outer;
    
    //判断用的方法，只有为0才是false，其他都是true
    public static boolean judge(Object object){
    	if(object instanceof Number){
    		if(((Number) object).intValue()==0)
    			return false;
    	}
    	return true;
    }
    
    //两种构造方法
    public Environment(Environment env){
    	outer=env;
    	values=new HashMap<String,Object>();
    }
    public Environment(){
    	values=new HashMap<String,Object>();
    }
    
    
    
    public void setOuter(Environment outer) {
		this.outer = outer;
	}

	//只在当前环境变量中查找
    public Object getLocal(String name){
    	return values.get(name);
    }
    
    //根据键获取值，当前环境变量中找不到就往外层找
    public Object get(String name) {
		Object object=values.get(name);
    	if(object==null&&outer!=null){
    		object=outer.get(name);
    	}
    	return object;
	}
    
    //把键值添加到当前环境中
    public Object putLocal(String name,Object object){
    	return values.put(name, object);
    }
    
    //更新键的对应值
    public Object put(String name,Object object) {
		Environment environment=where(name);
		if(environment==null){
			environment=this;
		}
		return environment.putLocal(name, object);
	}
    
    //查找键在哪个环境变量中
    public Environment where(String name){
    	if(values.get(name)!=null){
    		return this;
    	}
    	else if(outer==null){
    		return null;
    	}
    	else {
			return outer.where(name);
		}
    } 
}
