package org.springframework.security.access.intercept;

import java.util.Collection;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.log.LogMessage;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;

/**
 * Allows users to determine whether they have "before invocation" privileges for a given
 * method invocation.
 * <p>
 * Of course, if an
 * {@link org.springframework.security.access.intercept.AfterInvocationManager} is used to
 * authorize the <em>result</em> of a method invocation, this class cannot assist
 * determine whether or not the <code>AfterInvocationManager</code> will enable access.
 * Instead this class aims to allow applications to determine whether or not the current
 * principal would be allowed to at least attempt to invoke the method, irrespective of
 * the "after" invocation handling.
 * </p>
 *
 * @author Ben Alex
 */
public class MethodInvocationPrivilegeEvaluator implements InitializingBean {

	protected static final Log logger = LogFactory.getLog(MethodInvocationPrivilegeEvaluator.class);

	private AbstractSecurityInterceptor securityInterceptor;

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(this.securityInterceptor, "SecurityInterceptor required");
	}

	public boolean isAllowed(MethodInvocation invocation, Authentication authentication) {
		Assert.notNull(invocation, "MethodInvocation required");
		Assert.notNull(invocation.getMethod(), "MethodInvocation must provide a non-null getMethod()");
		Collection<ConfigAttribute> attrs = this.securityInterceptor.obtainSecurityMetadataSource()
				.getAttributes(invocation);
		if (attrs == null) {
			return !this.securityInterceptor.isRejectPublicInvocations();
		}
		if (authentication == null || authentication.getAuthorities().isEmpty()) {
			return false;
		}
		try {
			this.securityInterceptor.getAccessDecisionManager().decide(authentication, invocation, attrs);
			return true;
		}
		catch (AccessDeniedException unauthorized) {
			logger.debug(LogMessage.format("%s denied for %s", invocation, authentication), unauthorized);
			return false;
		}
	}

	public void setSecurityInterceptor(AbstractSecurityInterceptor securityInterceptor) {
		Assert.notNull(securityInterceptor, "AbstractSecurityInterceptor cannot be null");
		Assert.isTrue(MethodInvocation.class.equals(securityInterceptor.getSecureObjectClass()),
				"AbstractSecurityInterceptor does not support MethodInvocations");
		Assert.notNull(securityInterceptor.getAccessDecisionManager(),
				"AbstractSecurityInterceptor must provide a non-null AccessDecisionManager");
		this.securityInterceptor = securityInterceptor;
	}

}
