package org.springframework.security.access.expression.method;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.DefaultSecurityParameterNameDiscoverer;

/**
 * Internal security-specific EvaluationContext implementation which lazily adds the
 * method parameter values as variables (with the corresponding parameter names) if and
 * when they are required.
 *
 * @author Luke Taylor
 * @author Daniel Bustamante
 * @since 3.0
 */
class MethodSecurityEvaluationContext extends MethodBasedEvaluationContext {

	/**
	 * Intended for testing. Don't use in practice as it creates a new parameter resolver
	 * for each instance. Use the constructor which takes the resolver, as an argument
	 * thus allowing for caching.
	 */
	MethodSecurityEvaluationContext(Authentication user, MethodInvocation mi) {
		this(user, mi, new DefaultSecurityParameterNameDiscoverer());
	}

	MethodSecurityEvaluationContext(Authentication user, MethodInvocation mi,
			ParameterNameDiscoverer parameterNameDiscoverer) {
		super(mi.getThis(), getSpecificMethod(mi), mi.getArguments(), parameterNameDiscoverer);
	}

	private static Method getSpecificMethod(MethodInvocation mi) {
		return AopUtils.getMostSpecificMethod(mi.getMethod(), AopProxyUtils.ultimateTargetClass(mi.getThis()));
	}

}
