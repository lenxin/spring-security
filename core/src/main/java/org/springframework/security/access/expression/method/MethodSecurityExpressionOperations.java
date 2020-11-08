package org.springframework.security.access.expression.method;

import org.springframework.security.access.expression.SecurityExpressionOperations;

/**
 * Interface which must be implemented if you want to use filtering in method security
 * expressions.
 *
 * @author Luke Taylor
 * @since 3.1.1
 */
public interface MethodSecurityExpressionOperations extends SecurityExpressionOperations {

	void setFilterObject(Object filterObject);

	Object getFilterObject();

	void setReturnObject(Object returnObject);

	Object getReturnObject();

	Object getThis();

}
