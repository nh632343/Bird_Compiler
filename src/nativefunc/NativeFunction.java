package nativefunc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NativeFunction {

	protected Method method;
	protected int numParams;
	
	public NativeFunction(Method method, int numParams) {
		this.method = method;
		this.numParams = numParams;
	}
	
	public Object invoke(Object[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		return method.invoke(null, args);
	}

	public int getNumParams() {
		return numParams;
	}
	
	
}
