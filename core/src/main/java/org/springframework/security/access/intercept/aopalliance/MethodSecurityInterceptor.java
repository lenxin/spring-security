package org.springframework.security.access.intercept.aopalliance;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.access.method.MethodSecurityMetadataSource;

/**
 * Provides security interception of AOP Alliance based method invocations.
 * <p>
 * The <code>SecurityMetadataSource</code> required by this security interceptor is of
 * type {@link MethodSecurityMetadataSource}. This is shared with the AspectJ based
 * security interceptor (<code>AspectJSecurityInterceptor</code>), since both work with
 * Java <code>Method</code>s.
 * <p>
 * Refer to {@link AbstractSecurityInterceptor} for details on the workflow.
 *
 * @author Ben Alex
 * @author Rob Winch
 */
public class MethodSecurityInterceptor extends AbstractSecurityInterceptor implements MethodInterceptor {

	private MethodSecurityMetadataSource securityMetadataSource;

	@Override
	public Class<?> getSecureObjectClass() {
		return MethodInvocation.class;
	}

	/**
	 * This method should be used to enforce security on a <code>MethodInvocation</code>.
	 * @param mi The method being invoked which requires a security decision
	 * @return The returned value from the method invocation (possibly modified by the
	 * {@code AfterInvocationManager}).
	 * @throws Throwable if any error occurs
	 */
	@Override
	public Object invoke(MethodInvocation mi) throws Throwable {
		InterceptorStatusToken token = super.beforeInvocation(mi);
		Object result;
		try {
			result = mi.proceed();
		}
		finally {
			super.finallyInvocation(token);
		}
		return super.afterInvocation(token, result);
	}

	public MethodSecurityMetadataSource getSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	public void setSecurityMetadataSource(MethodSecurityMetadataSource newSource) {
		this.securityMetadataSource = newSource;
	}

}
