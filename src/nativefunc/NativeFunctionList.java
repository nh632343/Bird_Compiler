package nativefunc;

import java.lang.reflect.Method;

import basic.StoneException;
import node.Environment;

public class NativeFunctionList {

	public static long startTime=System.currentTimeMillis();
	
	//the java function supported
	public static int print(Object object){
		System.out.println(object);
		return 1;
	}
	
	public static int parseInt(String string) {
		return Integer.parseInt(string);
	}
	
	public static String parseString(int a) {
		return String.valueOf(a);
	}
	
	public static int length(Object object) {
		if (object instanceof String) {
			return ((String)object).length();
		}
		if (object instanceof Object[]) {
			return ((Object[])object).length;
		}
		throw new StoneException("no length");
	}
	
	public static int currentTime() {
		return (int)(System.currentTimeMillis()-startTime);
	}
	
	//append single to env
	private static void append(Environment environment,String name,Class[] parameterTypes,int numParams) 
			throws NoSuchMethodException, SecurityException {
		
		Method method=NativeFunctionList.class.getMethod(name, parameterTypes);
		environment.put(name, new NativeFunction(method, numParams));
	}
	
	public static void append(Environment environment) 
			throws NoSuchMethodException, SecurityException{
		
		append(environment, "print", new Class[]{Object.class}, 1);
		append(environment, "parseInt", new Class[]{String.class}, 1);
		append(environment, "parseString", new Class[]{int.class}, 1);
		append(environment, "length", new Class[]{Object.class}, 1);
		append(environment, "currentTime", null, 0);
	}
}
