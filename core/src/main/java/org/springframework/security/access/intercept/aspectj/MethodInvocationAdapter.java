package org.springframework.security.access.intercept.aspectj;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.CodeSignature;

import org.springframework.util.Assert;

/**
 * Decorates a JoinPoint to allow it to be used with method-security infrastructure
 * classes which support {@code MethodInvocation} instances.
 *
 * @author Luke Taylor
 * @since 3.0.3
 */
public final class MethodInvocationAdapter implements MethodInvocation {

	private final ProceedingJoinPoint jp;

	private final Method method;

	private final Object target;

	MethodInvocationAdapter(JoinPoint jp) {
		this.jp = (ProceedingJoinPoint) jp;
		if (jp.getTarget() != null) {
			this.target = jp.getTarget();
		}
		else {
			// SEC-1295: target may be null if an ITD is in use
			this.target = jp.getSignature().getDeclaringType();
		}
		String targetMethodName = jp.getStaticPart().getSignature().getName();
		Class<?>[] types = ((CodeSignature) jp.getStaticPart().getSignature()).getParameterTypes();
		Class<?> declaringType = jp.getStaticPart().getSignature().getDeclaringType();
		this.method = findMethod(targetMethodName, declaringType, types);
		Assert.notNull(this.method, () -> "Could not obtain target method from JoinPoint: '" + jp + "'");
	}

	private Method findMethod(String name, Class<?> declaringType, Class<?>[] params) {
		Method method = null;
		try {
			method = declaringType.getMethod(name, params);
		}
		catch (NoSuchMethodException ignored) {
		}
		if (method == null) {
			try {
				method = declaringType.getDeclaredMethod(name, params);
			}
			catch (NoSuchMethodException ignored) {
			}
		}
		return method;
	}

	@Override
	public Method getMethod() {
		return this.method;
	}

	@Override
	public Object[] getArguments() {
		return this.jp.getArgs();
	}

	@Override
	public AccessibleObject getStaticPart() {
		return this.method;
	}

	@Override
	public Object getThis() {
		return this.target;
	}

	@Override
	public Object proceed() throws Throwable {
		return this.jp.proceed();
	}

}
