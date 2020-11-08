package org.springframework.security.access.expression.method;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;

/**
 * Extended expression root object which contains extra method-specific functionality.
 *
 * @author Luke Taylor
 * @since 3.0
 */
class MethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

	private Object filterObject;

	private Object returnObject;

	private Object target;

	MethodSecurityExpressionRoot(Authentication a) {
		super(a);
	}

	@Override
	public void setFilterObject(Object filterObject) {
		this.filterObject = filterObject;
	}

	@Override
	public Object getFilterObject() {
		return this.filterObject;
	}

	@Override
	public void setReturnObject(Object returnObject) {
		this.returnObject = returnObject;
	}

	@Override
	public Object getReturnObject() {
		return this.returnObject;
	}

	/**
	 * Sets the "this" property for use in expressions. Typically this will be the "this"
	 * property of the {@code JoinPoint} representing the method invocation which is being
	 * protected.
	 * @param target the target object on which the method in is being invoked.
	 */
	void setThis(Object target) {
		this.target = target;
	}

	@Override
	public Object getThis() {
		return this.target;
	}

}
