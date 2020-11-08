package org.springframework.security.access.intercept.method;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

@SuppressWarnings("unchecked")
public class MockMethodInvocation implements MethodInvocation {

	private Method method;

	private Object targetObject;

	private Object[] arguments = new Object[0];

	public MockMethodInvocation(Object targetObject, Class clazz, String methodName, Class[] parameterTypes,
			Object[] arguments) throws NoSuchMethodException {
		this(targetObject, clazz, methodName, parameterTypes);
		this.arguments = arguments;
	}

	public MockMethodInvocation(Object targetObject, Class clazz, String methodName, Class... parameterTypes)
			throws NoSuchMethodException {
		this.method = clazz.getMethod(methodName, parameterTypes);
		this.targetObject = targetObject;
	}

	@Override
	public Object[] getArguments() {
		return this.arguments;
	}

	@Override
	public Method getMethod() {
		return this.method;
	}

	@Override
	public AccessibleObject getStaticPart() {
		return null;
	}

	@Override
	public Object getThis() {
		return this.targetObject;
	}

	@Override
	public Object proceed() {
		return null;
	}

}
