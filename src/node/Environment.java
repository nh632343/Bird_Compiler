package node;

import java.util.HashMap;



public class Environment {
    protected HashMap<String, Object> values;
    protected Environment outer;
    
    //�ж��õķ�����ֻ��Ϊ0����false����������true
    public static boolean judge(Object object){
    	if(object instanceof Number){
    		if(((Number) object).intValue()==0)
    			return false;
    	}
    	return true;
    }
    
    //���ֹ��췽��
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

	//ֻ�ڵ�ǰ���������в���
    public Object getLocal(String name){
    	return values.get(name);
    }
    
    //���ݼ���ȡֵ����ǰ�����������Ҳ������������
    public Object get(String name) {
		Object object=values.get(name);
    	if(object==null&&outer!=null){
    		object=outer.get(name);
    	}
    	return object;
	}
    
    //�Ѽ�ֵ��ӵ���ǰ������
    public Object putLocal(String name,Object object){
    	return values.put(name, object);
    }
    
    //���¼��Ķ�Ӧֵ
    public Object put(String name,Object object) {
		Environment environment=where(name);
		if(environment==null){
			environment=this;
		}
		return environment.putLocal(name, object);
	}
    
    //���Ҽ����ĸ�����������
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
