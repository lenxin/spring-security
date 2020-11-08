package org.springframework.security.util;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

/**
 * Represents the AOP Alliance <code>MethodInvocation</code>.
 *
 * @author Ben Alex
 */
public class SimpleMethodInvocation implements MethodInvocation {

	private Method method;

	private Object[] arguments;

	private Object targetObject;

	public SimpleMethodInvocation(Object targetObject, Method method, Object... arguments) {
		this.targetObject = targetObject;
		this.method = method;
		this.arguments = (arguments != null) ? arguments : new Object[0];
	}

	public SimpleMethodInvocation() {
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
		throw new UnsupportedOperationException("mock method not implemented");
	}

	@Override
	public Object getThis() {
		return this.targetObject;
	}

	@Override
	public Object proceed() {
		throw new UnsupportedOperationException("mock method not implemented");
	}

	@Override
	public String toString() {
		return "method invocation [" + this.method + "]";
	}

}
